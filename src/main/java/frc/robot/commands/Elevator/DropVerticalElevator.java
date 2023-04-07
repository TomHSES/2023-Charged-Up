package frc.robot.commands.Elevator;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.ElevatorConstants;
import frc.robot.subsystems.VerticalElevatorSystem;

public class DropVerticalElevator extends CommandBase
{
    public VerticalElevatorSystem ElevatorSystem;

    public Double Speed;

    public DropVerticalElevator(VerticalElevatorSystem elevatorSystem, double speed)
    {
        ElevatorSystem = elevatorSystem;
        Speed = speed;
        addRequirements(ElevatorSystem);
    }
    
    @Override
    public void execute()
    {
        ElevatorSystem.ElevatorMotor.set(TalonFXControlMode.PercentOutput, Speed);
    }

    @Override
    public void end(boolean interrupted)
    {
        ElevatorSystem.ElevatorMotor.set(TalonFXControlMode.PercentOutput, 0.0);
    }

    @Override
    public boolean isFinished()
    {
        return ElevatorSystem.GetEncoderPosition() <= ElevatorConstants.kVerticalElevatorThreshold * 3;
    }
}
