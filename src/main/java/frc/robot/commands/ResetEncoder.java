package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.GyroscopeSystem;
import frc.robot.subsystems.WristSystem;
import frc.robot.subsystems.TankDriveSystem;

public class ResetEncoder extends CommandBase
{
    public Subsystem Subsystem;

    public ResetEncoder(Subsystem subsystem)
    {
        Subsystem = subsystem;
        addRequirements(Subsystem);
    }

    @Override
    public void end(boolean interrupted)
    {
        if (Subsystem instanceof TankDriveSystem)
        {
            TankDriveSystem tankDriveSystem = (TankDriveSystem)Subsystem;
            tankDriveSystem.LeftEncoder.reset();
            tankDriveSystem.RightEncoder.reset();
        }
        else if (Subsystem instanceof GyroscopeSystem)
        {
            GyroscopeSystem gyroscopeSystem = (GyroscopeSystem)Subsystem;
            gyroscopeSystem.ResetGyro();
        }
        else if (Subsystem instanceof WristSystem)
        {
            WristSystem intakeArmSystem = (WristSystem)Subsystem;
            intakeArmSystem.WristMotor.getSensorCollection().setIntegratedSensorPosition(0, 30);
        }
    }

    @Override 
    public boolean isFinished()
    {
        return true;
    }
}
