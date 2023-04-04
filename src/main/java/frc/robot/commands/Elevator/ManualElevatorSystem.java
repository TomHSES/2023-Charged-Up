package frc.robot.commands.Elevator;

import edu.wpi.first.wpilibj2.command.button.CommandJoystick;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.VerticalElevatorSystem;

public class ManualElevatorSystem extends CommandBase
{
    public VerticalElevatorSystem ElevatorSystem;

    public CommandJoystick Joystick;

    public ManualElevatorSystem(VerticalElevatorSystem elevatorSystem, CommandJoystick joystick)
    {
        ElevatorSystem = elevatorSystem;
        Joystick = joystick;
        addRequirements(ElevatorSystem);
    }
    
    @Override
    public void execute()
    {
        double rawThrottle = Joystick.getThrottle();
        ElevatorSystem.ElevatorMotor.set(TalonFXControlMode.PercentOutput, rawThrottle);
    }

    @Override
    public void end(boolean interrupted)
    {
        ElevatorSystem.ElevatorMotor.set(TalonFXControlMode.PercentOutput, 0.0);
    }
}
