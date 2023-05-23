package frc.robot.commands.Elevator;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.ElevatorConstants;
import frc.robot.subsystems.VerticalElevatorSystem;

public class LiftVerticalElevator extends CommandBase
{
    public VerticalElevatorSystem VerticialElevatorSystem;

    public double DesirePosition;

    public LiftVerticalElevator(VerticalElevatorSystem verticalElevatorSystem, double desiredPosition)
    {
        VerticialElevatorSystem = verticalElevatorSystem;
        DesirePosition = desiredPosition;
        addRequirements(VerticialElevatorSystem);
    }

    @Override
    public void execute()
    {
        VerticialElevatorSystem.ElevatorMotor.set(TalonFXControlMode.PercentOutput, 1);
    }

    @Override
    public void end(boolean interrupted)
    {
        VerticialElevatorSystem.ElevatorMotor.set(TalonFXControlMode.PercentOutput, 0);
    }

    @Override
    public boolean isFinished()
    {
        return VerticialElevatorSystem.GetEncoderPosition() >= DesirePosition - ElevatorConstants.kVerticalElevatorThreshold;
    }
}
