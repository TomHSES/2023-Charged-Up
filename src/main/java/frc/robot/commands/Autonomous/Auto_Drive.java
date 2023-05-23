package frc.robot.commands.Autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.GeneralConstants;
import frc.robot.subsystems.TankDriveSystem;

public class Auto_Drive extends CommandBase
{
    public TankDriveSystem TankDriveSystem;

    // In terms of feet value
    public double DesiredDistance_Feet;

    // In terms of encoder value
    public double DesiredDistance;

    public double MaxSpeed;

    public Auto_Drive(TankDriveSystem tankDriveSystem, double desiredDistance, double maxSpeed)
    {
        TankDriveSystem = tankDriveSystem;
        DesiredDistance_Feet = desiredDistance;
        DesiredDistance = DesiredDistance_Feet / GeneralConstants.kDriveEncoderToFeetConversionFactor;
        MaxSpeed = maxSpeed;
        addRequirements(tankDriveSystem);
    }

    @Override
    public void execute()
    {
        TankDriveSystem.ArcadeDrive(0, MaxSpeed);
    }

    @Override
    public void end(boolean interrupted)
    {
        TankDriveSystem.StopDrive();
    }

    @Override
    public boolean isFinished()
    {
        return TankDriveSystem.LeftEncoder.get() >= DesiredDistance_Feet;
    }
}
