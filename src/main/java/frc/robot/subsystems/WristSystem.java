package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.LaunchConstants;

public class WristSystem extends SubsystemBase 
{
    public TalonFX WristMotor;

    public DoubleSolenoid PneumaticBrake;

    public WristSystem(int falconCanID, int brakeForwardPort, int brakeReversePort) 
    {
        WristMotor = new TalonFX(falconCanID);
        PneumaticBrake = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, brakeForwardPort, brakeReversePort);
        PneumaticBrake.set(Value.kReverse);
    }

    boolean active = false;

    public void TogglePneumaticBrake(boolean active)
    {
        PneumaticBrake.set(active ? Value.kForward : Value.kReverse);
    }

    public CommandBase TogglePneumaticBrake_Command()
    {
        active = !active;
        return runOnce(() -> TogglePneumaticBrake(active));
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