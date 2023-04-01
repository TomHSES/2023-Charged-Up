package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.LaunchConstants;

public class IntakeArmSystem extends SubsystemBase 
{
    public TalonFX FalconMotor;

    public PIDController ArmController;

    public double CurrentSpeed;

    public IntakeArmSystem(int falconCANID) 
    {
        FalconMotor = new TalonFX(falconCANID); 
        CurrentSpeed = 0;
    }

    @Override
    public void periodic()
    {
        if (LaunchConstants.Log_IntakeArmSystem)
        {
            SmartDashboard.putNumber("Intake Position", FalconMotor.getSensorCollection().getIntegratedSensorPosition());
            SmartDashboard.putNumber("Intake Velocity", FalconMotor.getSensorCollection().getIntegratedSensorVelocity());
        }
    }
}