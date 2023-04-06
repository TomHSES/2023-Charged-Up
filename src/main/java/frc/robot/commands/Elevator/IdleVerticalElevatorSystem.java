package frc.robot.commands.Elevator;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.ElevatorConstants;
import frc.robot.subsystems.VerticalElevatorSystem;

public class IdleVerticalElevatorSystem extends CommandBase 
{
    public VerticalElevatorSystem Elevator;

    public IdleVerticalElevatorSystem(VerticalElevatorSystem elevatorSystem) 
    {
        Elevator = elevatorSystem;
        addRequirements(Elevator);
    }

    @Override
    public void execute()
    {
        Elevator.ElevatorMotor.set(TalonFXControlMode.PercentOutput, ElevatorConstants.VerticalMotorInertia);
    }

    @Override
    public void end(boolean interrupted) 
    {
        Elevator.ElevatorMotor.set(TalonFXControlMode.PercentOutput, 0);
    }
}