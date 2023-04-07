package frc.robot.commands.Elevator;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.ElevatorConstants;
import frc.robot.subsystems.VerticalElevatorSystem;

public class LiftVerticalElevator extends CommandBase
{
    public VerticalElevatorSystem VerticialElevatorSystem;

    public double DesirePosition;

    public double DesiredPosition_Squared;

    public double DesiredRelativeSpeed;

    private double CurrentEncoderPosition;

    public LiftVerticalElevator(VerticalElevatorSystem verticalElevatorSystem, double desiredPosition, double desiredRelSpeed)
    {
        VerticialElevatorSystem = verticalElevatorSystem;
        DesirePosition = desiredPosition;
        DesiredPosition_Squared = Math.pow(DesirePosition, 2);
        DesiredRelativeSpeed = desiredRelSpeed;
        addRequirements(VerticialElevatorSystem);
    }

    public double CalculateSpeed()
    {
        // https://www.desmos.com/calculator/u7vrjyabrt
        CurrentEncoderPosition = VerticialElevatorSystem.GetEncoderPosition();
        double currentPosition_Squared = Math.pow(CurrentEncoderPosition, 2);
        double distance = DesiredPosition_Squared - currentPosition_Squared;
        double speedMultiplier = 0;
        if (distance > 0)
        {
            speedMultiplier += Math.sqrt(distance) / DesiredPosition_Squared;
        }
        return ElevatorConstants.VerticalMotorInertia + DesiredRelativeSpeed * speedMultiplier;
    }

    @Override
    public void execute()
    {
        VerticialElevatorSystem.ElevatorMotor.set(TalonFXControlMode.PercentOutput, MathUtil.clamp(CalculateSpeed(), -1, 1));
    }

    @Override
    public void end(boolean interrupted)
    {
        VerticialElevatorSystem.ElevatorMotor.set(TalonFXControlMode.PercentOutput, 0);
    }

    @Override
    public boolean isFinished()
    {
        return CurrentEncoderPosition >= DesirePosition - ElevatorConstants.kVerticalElevatorThreshold;
    }
}
