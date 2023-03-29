package frc.robot.commands.Autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.AutonomousConstants;
import frc.robot.subsystems.GyroscopeSystem;
import frc.robot.subsystems.TankDriveSystem;

public class Auto_DriveToGamePiece extends CommandBase
{
    public boolean HasInit;

    public int ObtainPosition;

    public GyroscopeSystem GyroscopeSystem;

    public TankDriveSystem TankDriveSystem;

    // Assumes the robot is positioned at it's assigned AutonomousConstant.StartingPosition
    public Auto_DriveToGamePiece(GyroscopeSystem gyroscopeSystem, TankDriveSystem tankDriveSystem, int obtainPosition)
    {
        ObtainPosition = obtainPosition;
    }

    @Override
    public void execute()
    {
        int movementState = ObtainPosition - AutonomousConstants.StartingPosition;

        if (movementState == 0)
        {
            if (TankDriveSystem.RightEncoder.getDistance() < 20)
            {
                TankDriveSystem.ArcadeDrive(0, AutonomousConstants.kFastDriveSpeed);
            }
            else
            {
                TankDriveSystem.ArcadeDrive(0, 0);
            }
        }
    }

}
