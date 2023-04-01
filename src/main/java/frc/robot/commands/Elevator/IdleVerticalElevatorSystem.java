package frc.robot.commands.Elevator;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.ElevatorConstants;
import frc.robot.subsystems.VerticalElevatorSystem;

public class IdleVerticalElevatorSystem extends CommandBase 
{
    public VerticalElevatorSystem Elevator;

    public Timer IdleTimer;

    public double IdleTime;

    public boolean HasInit;

    private IdleVerticalElevatorSystem(VerticalElevatorSystem elevatorSystem, double idleTime) 
    {
        Elevator = elevatorSystem;
        IdleTimer = new Timer();
        IdleTime = idleTime;
        HasInit = false;
        addRequirements(Elevator);
    }

    @Override
    public void execute()
    {
        if (!HasInit)
        {
            IdleTimer.start();
            HasInit = true;
            return;
        }

        Elevator.ElevatorMotor.set(ElevatorConstants.VerticalMotorInertia);
    }

    @Override
    public void end(boolean interrupted) 
    {
        Elevator.ElevatorMotor.set(0);
    }

    @Override
    public boolean isFinished() 
    {
        return IdleTimer.hasElapsed(IdleTime);
    }
}