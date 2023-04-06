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
        DisableBrakes();
    }

    public double GetFalconPosition()
    {
        return WristMotor.getSensorCollection().getIntegratedSensorPosition();
    }

    public void DisableBrakes()
    {
        BrakeActive = false;
        PneumaticBrake.set(Value.kForward);
    }

    public CommandBase DisableBrakes_Command()
    {
        return runOnce(() -> DisableBrakes());
    }

    public void EnableBrakes()
    {
        BrakeActive = true;
        PneumaticBrake.set(Value.kReverse);
    }

    public CommandBase EnableBrakes_Command()
    {
        return runOnce(() -> EnableBrakes());
    }

    public void ConfigureBrakes(boolean active)
    {
        if (active)
        {
            EnableBrakes();
        }
        else
        {
            DisableBrakes();
        }
    }

    public CommandBase ConfigureBrakes_Command(boolean active)
    {
        return runOnce(() -> ConfigureBrakes(active));
    }

    public void ToggleBrakes()
    {
        if (BrakeActive)
        {
            DisableBrakes();
        }
        else
        {
            EnableBrakes();
        }
    }

    public CommandBase ToggleBrakes_Command()
    {
        return runOnce(() -> ToggleBrakes());
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