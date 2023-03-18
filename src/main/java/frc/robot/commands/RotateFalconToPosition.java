package frc.robot.commands;

import frc.robot.subsystems.VelocityFalconSystem;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class RotateFalconToPosition extends CommandBase 
{
    public VelocityFalconSystem FalconSystem;

    private boolean HasInit = false;

    public double InitialPosition;

    public double IntendedPosition;

    public double PositionalDifferenceSign;

    public double InitialVelocity;

    public double CurrentVelocity;

    public double MaxVelocity;

    public double VelocityIncrement;

    public RotateFalconToPosition(VelocityFalconSystem intakeArmSystem, double intendedPosition, double maxVelocity, double velocityIncrement) 
    {
        FalconSystem = intakeArmSystem;
        IntendedPosition = intendedPosition;
        MaxVelocity = Math.abs(maxVelocity);
        VelocityIncrement = Math.abs(velocityIncrement);
        addRequirements(intakeArmSystem);
    }

    public void Initialize()
    {
        InitialPosition = FalconSystem.FalconMotor.getSensorCollection().getIntegratedSensorPosition();
        InitialVelocity = FalconSystem.FalconMotor.getSensorCollection().getIntegratedSensorVelocity();
        CurrentVelocity = InitialVelocity;
        if (IntendedPosition - InitialPosition != 0)
        {
            PositionalDifferenceSign = (IntendedPosition - InitialPosition) > 0 ? 1 : -1;
        }
        VelocityIncrement *= PositionalDifferenceSign;
        SmartDashboard.putNumber("Dir", PositionalDifferenceSign);
        SmartDashboard.putNumber("VIncre", VelocityIncrement);
    }

    @Override
    public void execute() 
    {
        if (!HasInit)
        {
            Initialize();
            HasInit = true;
            return;
        }

        if (PositionalDifferenceSign > 0 && CurrentVelocity < MaxVelocity ||
        PositionalDifferenceSign < 0 && CurrentVelocity > -MaxVelocity)
        {
            CurrentVelocity += VelocityIncrement;
        }
        else
        {
            CurrentVelocity = MaxVelocity * PositionalDifferenceSign;
        }

        SmartDashboard.putNumber("SVelocity", CurrentVelocity);
        FalconSystem.FalconMotor.set(TalonFXControlMode.Position, 1000);
        double sensorPosition = FalconSystem.FalconMotor.getSensorCollection().getIntegratedSensorPosition();
        SmartDashboard.putNumber("SPos", sensorPosition);
        SmartDashboard.putNumber("IPos", IntendedPosition);
        if (sensorPosition < IntendedPosition)
        {
            //FalconSystem.FalconMotor.set(TalonFXControlMode.Velocity, CurrentVelocity);
        }
        else
        {
            //FalconSystem.FalconMotor.set(TalonFXControlMode.PercentOutput, 0.06);
        }
    }

    @Override
    public void end(boolean interrupted) 
    {
        FalconSystem.FalconMotor.set(TalonFXControlMode.PercentOutput, 0);
    }

    @Override
    public boolean isFinished() 
    {
        return false;
    }
}