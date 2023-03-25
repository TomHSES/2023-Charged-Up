package frc.robot.commands;

import frc.robot.subsystems.TankDriveSystem;
import frc.robot.LaunchConstants;
import frc.robot.Utils.MathUtils;
import frc.robot.subsystems.GyroscopeSystem;
import frc.robot.subsystems.LimelightSystem;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;

public class AlignRobotWithLimelight extends CommandBase 
{
    public final LimelightSystem LimelightSystem;

    public final PIDController AngleCorrectionController;

    public final TankDriveSystem DriveSystem;

    public final GyroscopeSystem GyroscopeSystem;

    public final double MaxSpeed;

    public boolean Initialised;

    public double InitialAngle;

    public double TargetXPose;

    // The maximum angular velocity threshold that constitutes a 'still robot'
    public double Threshold_StillAngle = 1.0;

    // The maximum change in xPose that constitues a target 'flicker'
    public double Threshold_StillTarget = 3.0;

    public double Last_TargetXPose;

    public double Last_Angle;

    private AlignRobotWithLimelight(LimelightSystem limelightSystem, TankDriveSystem driveSystem, GyroscopeSystem gyroSystem,
    double maxSpeed, double kp, double ki, double kd)
    {
        LimelightSystem = limelightSystem;
        AngleCorrectionController = new PIDController(kp, ki, kd);
        DriveSystem = driveSystem;
        GyroscopeSystem = gyroSystem;

        addRequirements(LimelightSystem);
        addRequirements(DriveSystem);
        addRequirements(GyroscopeSystem);

        MaxSpeed = maxSpeed;
        Last_TargetXPose = Double.NaN;
        Last_Angle = Double.NaN;
        Initialised = false;
    }

    public double GetGyroscopeAngle()
    {
        return GyroscopeSystem.Gyroscope.getAngle();
    }

    public static CommandBase ScheduleCommand(LimelightSystem limelightSystem, TankDriveSystem driveSystem, GyroscopeSystem gyroSystem,
    double maxSpeed, double kp, double ki, double kd)
    {
        return Commands.sequence(new CancelAllCommands(), new AlignRobotWithLimelight(limelightSystem, driveSystem, gyroSystem, maxSpeed, kp, ki, kd));
    }

    // Alternative initialisation method respecting the ideal call order
    public void Init()
    {
        InitialAngle = GetGyroscopeAngle();
    }

    // Attempts to see whether or not the limelight is picking up a valid target
    // Returns false to prevent accumulation, history, and movement code from running
    public boolean HasValidTarget()
    {
        double xPose = LimelightSystem.GetLimelightValue("tx", Double.NaN).doubleValue();
        if (Double.isNaN(xPose))
        {
            // If our xPose isn't valid, something catastrophic has happened
            // and we don't want this code to run as is
            return false;
        }

        // Calculates the change in xPose and robot rotation in one call
        double targetChange = Math.abs(Last_TargetXPose - xPose);
        double angleChange = Math.abs(Last_Angle - GetGyroscopeAngle());

        if (LaunchConstants.Log_AlignRobotWithLimelight)
        {
            SmartDashboard.putNumber("ARWL: Tgt Change", targetChange);
            SmartDashboard.putNumber("ARWL: Ang. Change", angleChange);
        }

        if (targetChange < Threshold_StillTarget && angleChange > Threshold_StillAngle)
        {
            // If the robot isn't rotating, yet the target is rapidly moving
            // we'll assume the Limelight targetting system is flickering and the
            // actual target remains at a constant position.
            TargetXPose = Last_TargetXPose;
        }
        else
        {
            // Otherwise, in normal condition, our TargetXPose just follows the Limelight's
            // calculated xPose
            TargetXPose = xPose;
        }
        return true;
    }

    public void HandleAccumulation()
    {
        if (Double.isNaN(Last_TargetXPose) || Double.isNaN(Last_Angle) || Double.isNaN(TargetXPose))
        {
            return;
        }

        // If the sign of the last calculated xPose and the current calculated xPose are
        // different, the robot has probably overshot it's target. As such, we will reset the
        // accumulator as to prevent 'integrator windup'.
        boolean shouldReset = MathUtils.Sign(Last_TargetXPose) != MathUtils.Sign(TargetXPose);
        if (shouldReset)
        {
            AngleCorrectionController.reset();
        }

        if (LaunchConstants.Log_AlignRobotWithLimelight)
        {
            SmartDashboard.putBoolean("ARWL: Reset", shouldReset);
        }
    }

    public void HandleHistory()
    {
        Last_TargetXPose = TargetXPose;
        Last_Angle = GetGyroscopeAngle();
    }

    public void HandleMovement()
    {
        // Calculate the needed adjustment using a PID controller
        Double angularVelocity = MathUtil.clamp(-AngleCorrectionController.calculate(TargetXPose), -MaxSpeed, MaxSpeed);
        
        // If we're at our setpoint, we'll want the robot to try and stop as to prevent unintended behavior
        boolean atSetpoint = AngleCorrectionController.atSetpoint();
        if (atSetpoint)
        {
            DriveSystem.ArcadeDrive(0, 0);
        }
        else
        {
            // Otherwise, we'll rotate the robot based on the aforecalculated angular velocity
            DriveSystem.ArcadeDrive(angularVelocity, 0);
        }

        if (LaunchConstants.Log_AlignRobotWithLimelight)
        {
            SmartDashboard.putBoolean("ARWL: At Setpoint", atSetpoint);
            SmartDashboard.putNumber("ARWL: XPose", TargetXPose);
            SmartDashboard.putNumber("ARWL: Angular Velocity", angularVelocity);
        }
    }

    @Override
    public void execute() 
    {
        if (!Initialised)
        {
            Init();
            Initialised = true;
            return;
        }

        if (!HasValidTarget())
        {
            return;
        }

        HandleAccumulation();
        HandleHistory();
        HandleMovement();
    }

    @Override
    public void end(boolean interrupted) 
    {
        DriveSystem.StopDrive();
        AngleCorrectionController.reset();
    }

    @Override
    public boolean isFinished()
    {
        if (Double.isNaN(Last_Angle))
        {
            return AngleCorrectionController.atSetpoint();
        }

        return AngleCorrectionController.atSetpoint() && Math.abs(Last_Angle - GetGyroscopeAngle()) < Threshold_StillAngle;
    }
}
