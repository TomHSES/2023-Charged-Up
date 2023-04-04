package frc.robot.commands.Elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.ElevatorConstants;
import frc.robot.subsystems.HorizontalElevatorSystem;

public class IdleHorizontalElevatorSystem extends CommandBase
{
    public HorizontalElevatorSystem ElevatorSystem;

    public IdleHorizontalElevatorSystem(HorizontalElevatorSystem elevatorSystem)
    {
        ElevatorSystem = elevatorSystem;
        addRequirements(ElevatorSystem);
    }
    
    @Override
    public void execute()
    {
        ElevatorSystem.ElevatorMotor.set(ElevatorConstants.HorizontalMotorInertia);
    }
}
