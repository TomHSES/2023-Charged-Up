package frc.robot.commands.Autonomous;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.commands.Elevator.DropVerticalElevator;
import frc.robot.commands.Elevator.LiftVerticalElevator;
import frc.robot.commands.Elevator.ManualHElevatorSystem;
import frc.robot.commands.Elevator.ToggleHorizontalElevator;
import frc.robot.commands.Wrist.RotateWrist;
import frc.robot.constants.AutonomousConstants;
import frc.robot.constants.ElevatorConstants;
import frc.robot.constants.WristConstants;
import frc.robot.subsystems.ClawSystem;
import frc.robot.subsystems.VerticalElevatorSystem;
import frc.robot.RobotContainer;

public class AutonomousRoutine extends CommandBase
{
    public RobotContainer RobotContainer;

    public AutonomousRoutine(RobotContainer robotContainer)
    {
        RobotContainer = robotContainer;
    }

    public CommandBase AutoRoutine()
    {
        switch (AutonomousConstants.AutonomousRoutine)
        {
            default:
            case None:
                return new Auto_Initialisation(RobotContainer.GyroscopeSystem, RobotContainer.DriveSystem);

            case Default:
            {
                return Commands.sequence(new Auto_Initialisation(RobotContainer.GyroscopeSystem, RobotContainer.DriveSystem),
                new LiftVerticalElevator(RobotContainer.VerticalElevator, ElevatorConstants.VertElevConePos, 0.33),
                new RotateWrist(null, -0.8, 0.5), // Min > Max = Wrist falls outwards
                new ToggleHorizontalElevator(RobotContainer.HorizontalElevator, 1), // + value = Elevator out
                new Auto_Sleep(1),
                RobotContainer.ClawSystem.DisablePistons_Command(),
                new Auto_Sleep(1),
                new DropVerticalElevator(RobotContainer.VerticalElevator, -0.1),
                new Auto_Sleep(1),
                new Auto_Rotate(RobotContainer.GyroscopeSystem, RobotContainer.DriveSystem, 180, 3),
                new Auto_Sleep(1),
                new Auto_Drive(RobotContainer.DriveSystem, 10, 0.5),
                new Auto_Rotate(RobotContainer.GyroscopeSystem, RobotContainer.DriveSystem, 180, 3),
                new Auto_Drive(RobotContainer.DriveSystem, 3, 0.4));
            }

            /*case ScoreAndBalance:
            {
                return Commands.sequence(new Auto_Initialisation(RobotContainer.GyroscopeSystem, RobotContainer.DriveSystem),
                new Auto_Drive(RobotContainer.DriveSystem, 3, AutonomousConstants.kFastDriveSpeed, 0.1),
                new RotateWristAuto(RobotContainer.WristSystem, WristConstants.kWristExtendedPosition, 1000),
                RobotContainer.ClawSystem.DisablePistons_Command(),
                new Auto_Drive(RobotContainer.DriveSystem, 1, AutonomousConstants.kSlowDriveSpeed, 0.1),
                RobotContainer.ClawSystem.EnablePistons_Command(),
                new Auto_Rotate(RobotContainer.GyroscopeSystem, RobotContainer.DriveSystem, 180, 3),
                new RotateWristAuto(RobotContainer.WristSystem, 0, 1000),
                new Auto_Drive(RobotContainer.DriveSystem, 8, AutonomousConstants.kFastDriveSpeed, 0.1),
                new RotateWristAuto(RobotContainer.WristSystem, WristConstants.kWristExtendedPosition, 1000),
                new MoveHorizontalElevatorSystem(null, 0),
                new Auto_BalanceRoutine(RobotContainer.GyroscopeSystem, RobotContainer.DriveSystem));
            }*/
        }
    }
}
