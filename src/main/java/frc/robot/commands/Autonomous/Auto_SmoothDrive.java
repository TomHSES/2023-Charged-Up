package frc.robot.commands.Autonomous;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Utils.MathUtils;
import frc.robot.constants.AutonomousConstants;
import frc.robot.constants.GeneralConstants;
import frc.robot.subsystems.TankDriveSystem;

public class Auto_SmoothDrive extends CommandBase
{
    public TankDriveSystem TankDriveSystem;

    // In terms of feet value
    public double DesiredDistance_Feet;

    // In terms of encoder value
    public double DesiredDistance;

    public double MaxSpeed;

    // In terms of feet
    public double SetpointTolerance;

    public PIDController DriveController;

    public boolean HasInit = false;

    public double CurrentEncoderValue;

    public double OldEncoderValue;

    public double DriveGain;

    public Auto_SmoothDrive(TankDriveSystem tankDriveSystem, double desiredDistance, double maxSpeed, double setpointTolerance)
    {
        TankDriveSystem = tankDriveSystem;
        DesiredDistance_Feet = desiredDistance;
        DesiredDistance = DesiredDistance_Feet / GeneralConstants.kDriveEncoderToFeetConversionFactor;
        MaxSpeed = maxSpeed;
        SetpointTolerance = setpointTolerance;
        DriveController.setSetpoint(DesiredDistance);
        DriveController.setTolerance(SetpointTolerance);
        DriveController.setPID(AutonomousConstants.kDrivePIDPercentP, AutonomousConstants.kDrivePIDPercentI, AutonomousConstants.kDrivePIDPercentD);
        addRequirements(tankDriveSystem);
    }

    @Override
    public void execute()
    {
        OldEncoderValue = CurrentEncoderValue;
        CurrentEncoderValue = TankDriveSystem.LeftEncoder.getDistance();
        if (MathUtils.Sign(OldEncoderValue - DesiredDistance) != MathUtils.Sign(CurrentEncoderValue - DesiredDistance))
        {
            DriveController.reset();
        }

        DriveGain = MathUtil.clamp(DriveController.calculate(CurrentEncoderValue), -MaxSpeed, MaxSpeed);
        if (DriveController.atSetpoint())
        {
            TankDriveSystem.ArcadeDrive(0, 0);
        }
        else
        {
            TankDriveSystem.ArcadeDrive(0, DriveGain);
        }
    }

    @Override
    public void end(boolean interrupted)
    {
        TankDriveSystem.ArcadeDrive(0, 0);
    }

    @Override
    public boolean isFinished()
    {
        return HasInit && DriveController.atSetpoint() && DriveGain < SetpointTolerance * AutonomousConstants.kDrivePIDPercentP;
    }
}
