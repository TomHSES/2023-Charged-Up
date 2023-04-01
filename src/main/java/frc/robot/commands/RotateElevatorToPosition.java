package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.math.controller.PIDController;
import frc.robot.Utils.MathUtils;
import frc.robot.subsystems.ElevatorSystem;

public class RotateElevatorToPosition extends CommandBase
{
    public ElevatorSystem Elevator;

    public PIDController PIDController;

    public double MaxSpeed = 0.5;

    public double Threshold;

    public double CalculatedPositionalIncrement;

    public RotateElevatorToPosition(ElevatorSystem elevatorSystem, double referencePos, double p, double i, double d)
    {
        Elevator = elevatorSystem;
        PIDController = new PIDController(p, i, d);
        PIDController.setSetpoint(referencePos);
        PIDController.setTolerance(Threshold);
    }

    @Override
    public void execute()
    {
        CalculatedPositionalIncrement = PIDController.calculate(Elevator.ElevatorMotor.Encoder.getPosition());
        double motorSpeed = Elevator.ElevatorMotor.Encoder.getVelocityConversionFactor() * CalculatedPositionalIncrement;
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

        Elevator.ElevatorMotor.SparkMax.set(motorSpeed);
    }

    @Override
    public boolean isFinished() 
    {
        return PIDController.atSetpoint() && Math.abs(CalculatedPositionalIncrement) < Threshold;
    }
}
