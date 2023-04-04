package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.LaunchConstants;

public class WristSystem extends SubsystemBase 
{
    public TalonFX WristMotor;

    public DoubleSolenoid PneumaticBrake;

    public PIDController ArmController;

    public WristSystem(int falconCanID, int brakeForwardPort, int brakeReversePort) 
    {
        WristMotor = new TalonFX(falconCanID);
        PneumaticBrake = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, brakeForwardPort, brakeReversePort);

        if (LaunchConstants.Log_WristSystem)
        {

        }
    }

    @Override
    public void periodic()
    {
        if (LaunchConstants.Log_WristSystem)
        {
            SmartDashboard.putNumber("Intake Position", WristMotor.getSensorCollection().getIntegratedSensorPosition());
            SmartDashboard.putNumber("Intake Velocity", WristMotor.getSensorCollection().getIntegratedSensorVelocity());
        }
    }
}