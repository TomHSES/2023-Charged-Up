package frc.robot.commands.Elevator;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.VerticalElevatorSystem;

public class ManualElevatorSystem extends CommandBase
{
    public VerticalElevatorSystem ElevatorSystem;

    public Double Speed;

    public ManualElevatorSystem(VerticalElevatorSystem elevatorSystem, double speed)
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
}
