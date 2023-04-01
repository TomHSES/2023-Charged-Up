package frc.robot.commands.Autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.AutonomousConstants;
import frc.robot.subsystems.GyroscopeSystem;
import frc.robot.subsystems.TankDriveSystem;

public class Auto_BalanceRoutine extends CommandBase
{
    public GyroscopeSystem GyroscopeSystem;

    public TankDriveSystem TankDriveSystem;

    // Assumes the Robot is already on the Charge Station
    public Auto_BalanceRoutine(GyroscopeSystem gyroscopeSystem, TankDriveSystem tankDriveSystem)
    {
        GyroscopeSystem = gyroscopeSystem;
        TankDriveSystem = tankDriveSystem;
    }

    @Override
    public void execute()
    {
        double angularOffset = GyroscopeSystem.MPUY;
        if (angularOffset > AutonomousConstants.kOnChargeStationAngle)
        {
            TankDriveSystem.ArcadeDrive(0, AutonomousConstants.kSlowDriveSpeed);
        }
        else if (angularOffset < -AutonomousConstants.kOnChargeStationAngle)
        {
            TankDriveSystem.ArcadeDrive(0, -AutonomousConstants.kSlowDriveSpeed);
        }
    }

    @Override
    public void end(boolean interrupted)
    {
        TankDriveSystem.StopDrive();
    }
}
