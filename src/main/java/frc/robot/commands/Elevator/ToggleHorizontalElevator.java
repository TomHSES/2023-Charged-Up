package frc.robot.commands.Elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Utils.Time;
import frc.robot.constants.ElevatorConstants;
import frc.robot.subsystems.HorizontalElevatorSystem;

public class ToggleHorizontalElevator extends CommandBase
{
    public HorizontalElevatorSystem ElevatorSystem;

    public double Direction;

    public boolean HasInit;

    public double BeginTime;

    public ToggleHorizontalElevator(HorizontalElevatorSystem elevatorSystem, double direction)
    {
        ElevatorSystem = elevatorSystem;
        Direction = direction;
        addRequirements(ElevatorSystem);
    }
    
    @Override
    public void execute()
    {
        if (!HasInit)
        {
            BeginTime = Time.GetMsClock();
            HasInit = true;
            return;
        }

        double elapsedSeconds = (Time.GetMsClock() - BeginTime) / 1500;
        double speed = Math.abs(ElevatorConstants.HorizontalMotorInertia);
        if (elapsedSeconds < 1)
        {
            speed *= Math.cos(elapsedSeconds * Math.PI / 2) * 3 + 1.5;
        }
        
        ElevatorSystem.IdleDirection = Direction;
        ElevatorSystem.ElevatorMotor.set(speed * Direction);
    }

    @Override
    public void end(boolean interrupted)
    {
        ElevatorSystem.ElevatorMotor.set(0.0);
    }

    /*@Override
    public boolean isFinished()
    {
        double elapsedSeconds = (Time.GetMsClock() - BeginTime) / 1000;
        return elapsedSeconds > 2;
    }*/
}
