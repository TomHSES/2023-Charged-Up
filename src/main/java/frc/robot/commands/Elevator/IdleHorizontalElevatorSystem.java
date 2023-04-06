package frc.robot.commands.Elevator;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.ElevatorConstants;
import frc.robot.subsystems.HorizontalElevatorSystem;

public class IdleHorizontalElevatorSystem extends CommandBase
{
    public HorizontalElevatorSystem ElevatorSystem;

    public Timer ElevatorTimer;

    public IdleHorizontalElevatorSystem(HorizontalElevatorSystem elevatorSystem)
    {
        ElevatorSystem = elevatorSystem;
        ElevatorTimer = new Timer();
        addRequirements(ElevatorSystem);
    }
    
    @Override
    public void execute()
    {
        if (ElevatorSystem.Encoder.getPosition() < ElevatorConstants.InHorizontalThreshold)
        {
            ElevatorTimer.start();
        }
        else
        {
            ElevatorTimer.reset();
        }

        double inertiaSpeed = ElevatorConstants.HorizontalMotorInertia;
        inertiaSpeed += ElevatorTimer.hasElapsed(2) ? 0.01 : 0.0;
        ElevatorSystem.ElevatorMotor.set(inertiaSpeed);
    }

    @Override
    public void end(boolean interrupted) 
    {
        ElevatorSystem.ElevatorMotor.set(0);
    }
}
