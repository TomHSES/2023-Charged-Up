package frc.robot.commands.Elevator;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.ElevatorConstants;
import frc.robot.subsystems.HorizontalElevatorSystem;

public class IdleHorizontalElevatorSystem extends CommandBase
{
    public HorizontalElevatorSystem HorizontalElevatorSystem;

    public Timer ElevatorTimer;

    public IdleHorizontalElevatorSystem(HorizontalElevatorSystem elevatorSystem)
    {
        HorizontalElevatorSystem = elevatorSystem;
        ElevatorTimer = new Timer();
        addRequirements(HorizontalElevatorSystem);
    }
    
    @Override
    public void execute()
    {
        double encoderPosition = HorizontalElevatorSystem.Encoder.getPosition();
        if (HorizontalElevatorSystem.IdleDirection < 0.0 && encoderPosition < ElevatorConstants.InHorizontalThreshold
        || HorizontalElevatorSystem.IdleDirection > 0.0 && encoderPosition > ElevatorConstants.HorizontalElevatorBottomPosition - ElevatorConstants.InHorizontalThreshold)
        {
            ElevatorTimer.start();
        }
        else
        {
            ElevatorTimer.reset();
        }

        double inertiaSpeed = SmartDashboard.getNumber("Horizontal Inertia", ElevatorConstants.HorizontalMotorInertia);
        inertiaSpeed *= ElevatorTimer.hasElapsed(2) ? 1.33 : 1.0;
        HorizontalElevatorSystem.ElevatorMotor.set(inertiaSpeed * HorizontalElevatorSystem.IdleDirection);
    }

    @Override
    public void end(boolean interrupted) 
    {
        HorizontalElevatorSystem.ElevatorMotor.set(0);
    }
}
