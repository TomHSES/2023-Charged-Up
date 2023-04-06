package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.RobotContainer;
import frc.robot.commands.Elevator.MoveHorElevToSetpoint;
import frc.robot.commands.Elevator.RotateVerticalElevatorSystem;
import frc.robot.constants.ElevatorConstants;

public class Scoring
{
    public RobotContainer RobotContainer;

    public CommandBase ActiveCommand;

    public boolean Scoring;
    
    public Scoring(RobotContainer robotContainer)
    {
        Scoring = false;
        RobotContainer = robotContainer;
    }

    public CommandBase ScoringRoutine(boolean cone)
    {
        Scoring = true;
        double verticalElevatorPosition = cone ? ElevatorConstants.VertElevConePos : ElevatorConstants.VertElevCubePos;
        ActiveCommand = Commands.sequence(new RotateVerticalElevatorSystem(RobotContainer.VerticalElevator, verticalElevatorPosition),
        new MoveHorElevToSetpoint(RobotContainer.HorizontalElevator, 0.15, ElevatorConstants.HorizontalElevatorBottomPosition, 0.05));
        return ActiveCommand;
    }

    public CommandBase UndoScoringRoutine()
    {
        Scoring = false;
        ActiveCommand = Commands.sequence(new MoveHorElevToSetpoint(RobotContainer.HorizontalElevator, -0.15, 0, 0.05),
        new RotateVerticalElevatorSystem(RobotContainer.VerticalElevator, 0));
        return ActiveCommand;
    }

    public CommandBase ToggleScoringRoutine(boolean cone)
    {
        if (ActiveCommand != null)
        {
            ActiveCommand.cancel();
        }

        if (Scoring)
        {
            return UndoScoringRoutine();
        }
        else
        {
            return ScoringRoutine(cone);
        }
    }
}
