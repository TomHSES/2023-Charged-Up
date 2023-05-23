package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.IDs;
import frc.robot.constants.LaunchConstants;

public class WristSystemTwo extends SubsystemBase 
{
    public TalonFX ElevatorMotor;

    public PIDController ElevatorController;

    public double ElevatorRefEncoderPosition;

    public WristSystemTwo()
    {
        ElevatorMotor = new TalonFX(IDs.WristMotor);
        ElevatorMotor.configFactoryDefault(50);
    }

    public double GetPosition()
    {
        return ElevatorMotor.getSensorCollection().getIntegratedSensorPosition();
    }

    public CommandBase RunMotor(double velocity)
    {
        return runEnd(() ->
        {
            ElevatorMotor.set(TalonFXControlMode.PercentOutput, velocity);
        }, () ->
        {
            ElevatorMotor.set(TalonFXControlMode.PercentOutput, 0);
        });
    }

    @Override
    public void periodic()
    {
        if (LaunchConstants.Log_ElevatorSystems)
        {
            SmartDashboard.putNumber("Wrist. Enc. Pos.", GetPosition());
        }
    }
}