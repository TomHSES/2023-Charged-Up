package frc.robot.commands;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.constants.ElevatorConstants;
import frc.robot.subsystems.ElevatorSystem;

public class CalibrateElevatorSystem extends CommandBase
{
    public ElevatorSystem Elevator;

    private CalibrateElevatorSystem(ElevatorSystem elevatorSystem)
    {
        Elevator = elevatorSystem;
        addRequirements(Elevator);
    }

    /*public static CommandBase ScheduleCalibration(ElevatorSystem elevatorSystem)
    {
        return Commands.sequence(new CalibrateElevatorSystem(elevatorSystem), new RotateElevatorToPosition(elevatorSystem, Constants.ElevatorBottomPos, Constants.kElevatorLiftP, Constants.kElevatorLiftI, Constants.kElevatorLiftD));
    }*/

    @Override
    public void execute()
    {
        Elevator.ElevatorMotor.SparkMax.set(0.5);
    }

    @Override
    public void end(boolean interrupted) 
    {
        ElevatorConstants.HorizontalElevatorBottomPosition = -Elevator.ElevatorMotor.Encoder.getPosition();
        SmartDashboard.putNumber("Elevator BP", ElevatorConstants.HorizontalElevatorBottomPosition);
        Elevator.ElevatorMotor.SparkMax.set(0);
        Elevator.ElevatorMotor.Encoder.setPosition(0);
        Elevator.ElevatorMotor.SparkMax.setIdleMode(IdleMode.kBrake);
    }

    @Override
    public boolean isFinished() 
    {
        return Elevator.ElevatorLimitSwitch.get();
    }
}
