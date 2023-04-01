package frc.robot.commands.Autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.AutonomousConstants;
import frc.robot.subsystems.GyroscopeSystem;
import frc.robot.subsystems.TankDriveSystem;

public class Auto_DriveToGamePiece extends CommandBase
{
    public boolean HasInit;

    public int MovementState;

    public GyroscopeSystem GyroscopeSystem;

    public TankDriveSystem TankDriveSystem;

    // Assumes the robot is positioned at it's assigned AutonomousConstant.StartingPosition
    public Auto_DriveToGamePiece(GyroscopeSystem gyroscopeSystem, TankDriveSystem tankDriveSystem, int obtainPosition)
    {
        MovementState = obtainPosition - AutonomousConstants.StartingPosition;
    }

    @Override
    public void execute()
    {
        if (MovementState == 0)
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

    @Override
    public void end(boolean interrupted)
    {
        
    }

    @Override
    public boolean isFinished()
    {
        switch (MovementState)
        {
            case 0:
            {
                if (TankDriveSystem.LeftEncoder.getDistance() > 20 || TankDriveSystem.RightEncoder.getDistance() > 20)
                {
                    return true;
                }
            }
            break;

            default:
            {
                return true;
            }
        }

        return false;
    }
}
