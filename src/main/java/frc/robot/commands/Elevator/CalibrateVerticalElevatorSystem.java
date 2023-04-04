package frc.robot.commands.Elevator;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.ElevatorConstants;
import frc.robot.subsystems.VerticalElevatorSystem;

public class CalibrateVerticalElevatorSystem extends CommandBase 
{
    public VerticalElevatorSystem Elevator;

    public double OldEncoderValue;

    public double CurrentSpeed;

    public double MaxSpeed = 0.5;

    public CalibrateVerticalElevatorSystem(VerticalElevatorSystem elevatorSystem) 
    {
        Elevator = elevatorSystem;
        addRequirements(Elevator);
    }

    @Override
    public void execute()
    {
        Elevator.ElevatorMotor.set(TalonFXControlMode.PercentOutput, CurrentSpeed);

        double encoderPosition = Elevator.GetEncoderPosition();
        if (Math.abs(encoderPosition - OldEncoderValue) <= ElevatorConstants.kSparkMaxEncoderError
        || !Double.isNaN(ElevatorConstants.VerticalMotorInertia) && CurrentSpeed < ElevatorConstants.VerticalMotorInertia + 0.05)
        {
            CurrentSpeed = MathUtil.clamp(CurrentSpeed + 0.01, -MaxSpeed, MaxSpeed);
        }

        if (Double.isNaN(ElevatorConstants.VerticalMotorInertia) && OldEncoderValue != 0)
        {
            ElevatorConstants.VerticalMotorInertia = CurrentSpeed - 0.01;
        }

        OldEncoderValue = encoderPosition;
    }

    @Override
    public void end(boolean interrupted) 
    {
        ElevatorConstants.VerticalElevatorTopPosition = Elevator.GetEncoderPosition();
        Elevator.ElevatorMotor.set(TalonFXControlMode.PercentOutput, ElevatorConstants.VerticalMotorInertia);
    }

    @Override
    public boolean isFinished() 
    {
        return Elevator.ElevatorLimitSwitch.get();
    }
}
