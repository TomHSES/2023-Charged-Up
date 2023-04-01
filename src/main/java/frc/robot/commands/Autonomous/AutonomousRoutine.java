package frc.robot.commands.Autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.GyroscopeSystem;
import frc.robot.subsystems.TankDriveSystem;

public class AutonomousRoutine extends CommandBase
{
    public GyroscopeSystem GyroscopeSystem;

    public TankDriveSystem TankDriveSystem;

    public AutonomousRoutine(GyroscopeSystem gyroscopeSystem, TankDriveSystem tankDriveSystem)
    {
        GyroscopeSystem = gyroscopeSystem;
        TankDriveSystem = tankDriveSystem;
    }

    public CommandBase AutoRoutine()
    {
        return Commands.sequence(new Auto_Initialisation(GyroscopeSystem, TankDriveSystem),
        new Auto_DriveToGamePiece(GyroscopeSystem, TankDriveSystem, 0));
    }
}
