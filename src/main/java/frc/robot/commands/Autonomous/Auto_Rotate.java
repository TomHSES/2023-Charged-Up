package frc.robot.commands.Autonomous;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Utils.MathUtils;
import frc.robot.constants.LaunchConstants;
import frc.robot.subsystems.GyroscopeSystem;
import frc.robot.subsystems.TankDriveSystem;

public class Auto_Rotate extends CommandBase
{
    public boolean HasInit;

    public GyroscopeSystem GyroscopeSystem;

    public TankDriveSystem TankDriveSystem;

    public double DesiredRotation;

    public PIDController RotationalController;

    public double RotationalTolerance;

    public double CurrentRotation;

    public double RotationGain;

    public double OldRotation;

    public double MaxRotationSpeed = 0.5;

    public Auto_Rotate(GyroscopeSystem gyroscopeSystem, TankDriveSystem tankDriveSystem, double desiredRotation, double rotationalTolerance)
    {
        GyroscopeSystem = gyroscopeSystem;
        TankDriveSystem = tankDriveSystem;
        DesiredRotation = desiredRotation;
        RotationalController = new PIDController(0.005, 0.01, 0);
        RotationalController.setSetpoint(DesiredRotation);
        RotationalTolerance = rotationalTolerance;
        RotationalController.setTolerance(rotationalTolerance);
        addRequirements(GyroscopeSystem, TankDriveSystem);
    }

    @Override
    public void execute()
    {
        if (!HasInit)
        {
            GyroscopeSystem.ResetGyro();
            HasInit = true;
            return;
        }

        OldRotation = CurrentRotation;
        CurrentRotation = LaunchConstants.MPU ? GyroscopeSystem.MPUZ : GyroscopeSystem.Gyroscope.getAngle();
        CurrentRotation %= 360;

        if (MathUtils.Sign(OldRotation - DesiredRotation) != MathUtils.Sign(CurrentRotation - DesiredRotation))
        {
            RotationalController.reset();
        }
        
        RotationGain = RotationalController.calculate(CurrentRotation);
        double nextGain = MathUtil.clamp(RotationGain, -MaxRotationSpeed, MaxRotationSpeed);
        if (RotationalController.atSetpoint())
        {
            TankDriveSystem.ArcadeDrive(0, 0);
        }
        else
        {
            TankDriveSystem.ArcadeDrive(nextGain, 0);
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
        return HasInit && RotationalController.atSetpoint() && RotationGain < RotationalTolerance * RotationalController.getP();
    }
}
