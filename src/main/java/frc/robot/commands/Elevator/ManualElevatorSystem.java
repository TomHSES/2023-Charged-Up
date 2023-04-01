package frc.robot.commands.Elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ElevatorSystem;

public class ManualElevatorSystem extends CommandBase
{
    public ElevatorSystem ElevatorSystem;

    public double Speed;

    public ManualElevatorSystem(ElevatorSystem elevatorSystem, double speed)
    {
        ElevatorSystem = elevatorSystem;
        Speed = speed;
        addRequirements(ElevatorSystem);
    }
    
    @Override
    public void execute()
    {
        ElevatorSystem.ElevatorMotor.SparkMax.set(Speed);
    }

    @Override
    public void end(boolean interrupted)
    {
        ElevatorSystem.ElevatorMotor.SparkMax.set(0.0);
    }
}
