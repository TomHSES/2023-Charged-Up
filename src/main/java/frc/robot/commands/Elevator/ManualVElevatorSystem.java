package frc.robot.commands.Elevator;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.ElevatorConstants;
import frc.robot.subsystems.VerticalElevatorSystem;

public class ManualVElevatorSystem extends CommandBase
{
    public VerticalElevatorSystem ElevatorSystem;

    public Double Speed;

    public ManualVElevatorSystem(VerticalElevatorSystem elevatorSystem, double speed)
    {
        ElevatorSystem = elevatorSystem;
        Speed = speed;
        addRequirements(ElevatorSystem);
    }
    
    @Override
    public void execute()
    {
        double encoderPosition = ElevatorSystem.GetEncoderPosition();
        if (encoderPosition < 5000 && Speed < 0)
        {
            ElevatorSystem.ElevatorMotor.set(TalonFXControlMode.PercentOutput, 0);
            return;
        }
        else if (encoderPosition > ElevatorConstants.VerticalElevatorTopPosition - 20000 && Speed > 1)
        {
            ElevatorSystem.ElevatorMotor.set(TalonFXControlMode.PercentOutput, 0);
            return;
        }

        ElevatorSystem.ElevatorMotor.set(TalonFXControlMode.PercentOutput, Speed);
    }

    @Override
    public void end(boolean interrupted)
    {
        ElevatorSystem.ElevatorMotor.set(TalonFXControlMode.PercentOutput, 0.0);
    }
}
