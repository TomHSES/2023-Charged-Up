package frc.robot.commands.Elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HorizontalElevatorSystem;

public class MoveHorElevToSetpoint extends CommandBase
{
    public HorizontalElevatorSystem ElevatorSystem;

    public double Velocity;

    public double Setpoint;

    public double SetpointTolerance;

    public MoveHorElevToSetpoint(HorizontalElevatorSystem elevatorSystem, double velocity, double setPoint, double setPointTolerance)
    {
        ElevatorSystem = elevatorSystem;
        Velocity = velocity;
        Setpoint = setPoint;
        SetpointTolerance = setPointTolerance;
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

    @Override
    public boolean isFinished()
    {
        return Math.abs(ElevatorSystem.Encoder.getPosition() - Setpoint) < SetpointTolerance;
    }
}