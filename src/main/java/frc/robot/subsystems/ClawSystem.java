package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.IDs;

public class ClawSystem extends SubsystemBase 
{
    public CANSparkMax LeftMotor;

    public CANSparkMax RightMotor;

    public DoubleSolenoid Piston;

    public boolean PistonActive;

    public ClawSystem() 
    {
        LeftMotor = new CANSparkMax(IDs.ClawMotor_Left, MotorType.kBrushless);
        RightMotor = new CANSparkMax(IDs.ClawMotor_Right, MotorType.kBrushless);
        Piston = new DoubleSolenoid(IDs.PneumaticControlModule, PneumaticsModuleType.CTREPCM, IDs.ClawPistons_Forward, IDs.ClawPistons_Reverse);
        EnablePistons();
    }

    public CommandBase SpinMotors(double motorSpeed)
    {
        return runEnd(() ->
        {
            LeftMotor.set(-motorSpeed);
            RightMotor.set(motorSpeed);
        }, () ->
        {
            LeftMotor.set(0);
            RightMotor.set(0);
        });
    }

    public void EnablePistons()
    {
        PistonActive = true;
        Piston.set(Value.kForward);
    }

    public CommandBase EnablePistons_Command()
    {
        return runOnce(() -> EnablePistons());
    }

    public void DisablePistons()
    {
        PistonActive = false;
        Piston.set(Value.kReverse);
    }

    public CommandBase DisablePistons_Command()
    {
        return runOnce(() -> DisablePistons());
    }

    public void TogglePistons()
    {
        if (PistonActive)
        {
            DisablePistons();
        }
        else
        {
            EnablePistons();
        }
    }

    public CommandBase TogglePistons_Command()
    {
        return runOnce(() -> TogglePistons());
    }
}