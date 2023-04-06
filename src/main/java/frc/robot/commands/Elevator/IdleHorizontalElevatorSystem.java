package frc.robot.commands.Elevator;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
        double horizontalInertia = SmartDashboard.getNumber("HMI", ElevatorConstants.HorizontalMotorInertia);
        ElevatorSystem.ElevatorMotor.set(horizontalInertia);
    }

    @Override
    public void end(boolean interrupted) 
    {
        ElevatorSystem.ElevatorMotor.set(0);
    }
}
