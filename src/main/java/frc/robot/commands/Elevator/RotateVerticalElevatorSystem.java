package frc.robot.commands.Elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.math.controller.PIDController;
import frc.robot.Utils.MathUtils;
import frc.robot.constants.ElevatorConstants;
import frc.robot.subsystems.VerticalElevatorSystem;

public class RotateVerticalElevatorSystem extends CommandBase
{
    public VerticalElevatorSystem Elevator;

    public PIDController PIDController;

    public double MaxSpeed = 0.5;

    public double Threshold;

    public double CalculatedPositionalIncrement;

    public RotateVerticalElevatorSystem(VerticalElevatorSystem elevatorSystem, double referencePos, double p, double i, double d)
    {
        Elevator = elevatorSystem;
        PIDController = new PIDController(p, i, d);
        PIDController.setSetpoint(referencePos);
        PIDController.setTolerance(Threshold);
    }

    @Override
    public void execute()
    {
        CalculatedPositionalIncrement = PIDController.calculate(Elevator.Encoder.getPosition());
        double motorSpeed = Elevator.Encoder.getVelocityConversionFactor() * CalculatedPositionalIncrement;
        boolean atSetpoint = PIDController.atSetpoint();
        if (Elevator.ElevatorLimitSwitch.get() || atSetpoint)
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
        Elevator.ElevatorMotor.set(motorSpeed);
    }

    @Override
    public boolean isFinished() 
    {
        return PIDController.atSetpoint() && Math.abs(CalculatedPositionalIncrement) < Threshold;
    }
}