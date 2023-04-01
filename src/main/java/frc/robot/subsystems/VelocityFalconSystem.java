package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class VelocityFalconSystem extends SubsystemBase {

    public TalonFX FalconMotor;

    public String FalconName;

    public double CurrentVelocity;
    
    public VelocityFalconSystem(int falconCANID, String falconName) 
    {
        FalconMotor = new TalonFX(falconCANID); 
        FalconMotor.configFactoryDefault();
        FalconName = falconName;
        int timeOutMs = 30;
        int loopIDX = 0;
        double f = 1023;
        double p = .25;
        double i = .001;
        double d = 20;
        FalconMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, loopIDX, timeOutMs);
        FalconMotor.configNominalOutputForward(0, timeOutMs);
        FalconMotor.configNominalOutputReverse(0, timeOutMs);
        FalconMotor.configPeakOutputForward(0, timeOutMs);
        FalconMotor.configPeakOutputReverse(-0, timeOutMs);
        FalconMotor.configMotionAcceleration(i);
        FalconMotor.configMotionCruiseVelocity(i);
        FalconMotor.setInverted(false);
        FalconMotor.setSensorPhase(false);
        FalconMotor.config_kF(loopIDX, f, timeOutMs);
        FalconMotor.config_kP(loopIDX, p, timeOutMs);
        FalconMotor.config_kI(loopIDX, i, timeOutMs);
        FalconMotor.config_kD(loopIDX, d, timeOutMs);
        setDefaultCommand(Log());
    }

    public CommandBase Log() 
    {
        return run(() -> 
        {
            SmartDashboard.putNumber(FalconName + " Pos", FalconMotor.getSensorCollection().getIntegratedSensorPosition());
            SmartDashboard.putNumber(FalconName + " Vel", FalconMotor.getSensorCollection().getIntegratedSensorVelocity());
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