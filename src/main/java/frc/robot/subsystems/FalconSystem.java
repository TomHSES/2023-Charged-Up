package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Utils.MathUtils;

public class FalconSystem extends SubsystemBase {

    public TalonFX FalconMotor;

    public double CurrentSpeed;

    public FalconSystem(int falconCANID) 
    {
        FalconMotor = new TalonFX(falconCANID); 
        CurrentSpeed = 0;
        setDefaultCommand(Log());
    }

    // Don't handle motor rotation in a default command because of 'delays'
    public CommandBase Log() 
    {
        return run(() -> 
        {
            SmartDashboard.putNumber("F500 Position", FalconMotor.getSensorCollection().getIntegratedSensorPosition());
            SmartDashboard.putNumber("F500 Velocity", FalconMotor.getSensorCollection().getIntegratedSensorVelocity());
            SmartDashboard.putNumber("F500 Speed", CurrentSpeed);
        });
    }

    public void IncremenentSpeed(double increment)
    {
        CurrentSpeed += increment;
        CurrentSpeed = MathUtils.Clamp(CurrentSpeed, -1d, 1d);
    }

    public CommandBase SetSpeed(Double speed)
    {
        return run(() ->
        {
            FalconMotor.set(ControlMode.PercentOutput, speed);
        });
    }

    public CommandBase ZeroEncoder(int timeoutMs)
    {
        return runOnce(() -> 
        {
            FalconMotor.getSensorCollection().setIntegratedSensorPosition(0, timeoutMs);
        });
    }
}