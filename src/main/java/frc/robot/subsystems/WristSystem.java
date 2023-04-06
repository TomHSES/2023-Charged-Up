package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.IDs;
import frc.robot.constants.LaunchConstants;

public class WristSystem extends SubsystemBase 
{
    public TalonFX WristMotor;

    public DoubleSolenoid PneumaticBrake;

    public boolean BrakeActive;

    public WristSystem() 
    {
        WristMotor = new TalonFX(IDs.WristMotor);
        PneumaticBrake = new DoubleSolenoid(IDs.PneumaticControlModule, PneumaticsModuleType.CTREPCM, IDs.WristBrakes_Forward, IDs.WristBrakes_Reverse);
        BrakeActive = false;
        TogglePneumaticBrake(BrakeActive);
    }

    public void TogglePneumaticBrake(boolean active)
    {
        PneumaticBrake.set(active ? Value.kForward : Value.kReverse);
    }

    public CommandBase TogglePneumaticBrake_Command(boolean active)
    {
        return runOnce(() -> TogglePneumaticBrake(active));
    }

    @Override
    public void periodic()
    {
        if (LaunchConstants.Log_WristSystem)
        {
            SmartDashboard.putBoolean("Brakes Active", BrakeActive);
            SmartDashboard.putNumber("Wrist Pos.", WristMotor.getSensorCollection().getIntegratedSensorPosition());
            SmartDashboard.putNumber("Wrist Vel.", WristMotor.getSensorCollection().getIntegratedSensorVelocity());
        }
    }
}