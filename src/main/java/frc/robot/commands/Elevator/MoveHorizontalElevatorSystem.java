package frc.robot.commands.Elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HorizontalElevatorSystem;

public class MoveHorizontalElevatorSystem extends CommandBase
{
    public HorizontalElevatorSystem ElevatorSystem;

    public double Velocity;

    public MoveHorizontalElevatorSystem(HorizontalElevatorSystem elevatorSystem, double velocity)
    {
        ElevatorSystem = elevatorSystem;
        Velocity = velocity;
        addRequirements(ElevatorSystem);
    }
    
    @Override
    public void execute()
    {
        ElevatorSystem.ElevatorMotor.set(Velocity);
    }

    @Override
    public void end(boolean interrupted)
    {
        ElevatorSystem.ElevatorMotor.set(0.0);
    }
}
