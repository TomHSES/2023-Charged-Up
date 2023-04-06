package frc.robot.commands.Elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.Utils.MathUtils;
import frc.robot.constants.ElevatorConstants;
import frc.robot.subsystems.VerticalElevatorSystem;

public class RotateVerticalElevatorSystem extends CommandBase
{
    public VerticalElevatorSystem Elevator;

    public PIDController PIDController;

    public double MaxSpeed = 0.75;

    public double Threshold;

    public double CalculatedPositionalIncrement;

    public RotateVerticalElevatorSystem(VerticalElevatorSystem elevatorSystem, double referencePos)
    {
        Elevator = elevatorSystem;
        PIDController = new PIDController(ElevatorConstants.kVerticalElevatorControllerP, ElevatorConstants.kVerticalElevatorControllerI, ElevatorConstants.kVerticalElevatorControllerD);
        PIDController.setSetpoint(referencePos);
        PIDController.setTolerance(Threshold);
    }

    @Override
    public void execute()
    {
        CalculatedPositionalIncrement = PIDController.calculate(Elevator.GetEncoderPosition());
        double motorSpeed = ElevatorConstants.kVerticalElevatorVelocityConversionFactor * CalculatedPositionalIncrement;
        boolean atSetpoint = PIDController.atSetpoint();
        if (atSetpoint)
        {
            motorSpeed = 0;

            if (atSetpoint)
            {
                PIDController.reset();
            }
        }
        else
        {
            motorSpeed = MathUtils.Clamp(motorSpeed, -MaxSpeed, MaxSpeed);
        }

        if (motorSpeed < 0)
        {
            motorSpeed += ElevatorConstants.VerticalMotorInertia;
        }
        Elevator.ElevatorMotor.set(TalonFXControlMode.PercentOutput, motorSpeed);
    }

    @Override
    public boolean isFinished() 
    {
        return PIDController.atSetpoint() && Math.abs(CalculatedPositionalIncrement) < Threshold;
    }
}