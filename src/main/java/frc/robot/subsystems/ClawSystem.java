package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
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

    public RelativeEncoder LeftEncoder;

    public CANSparkMax RightMotor;

    public RelativeEncoder RightEncoder;

    public DoubleSolenoid Piston;

    public ClawSystem() 
    {
        LeftMotor = new CANSparkMax(IDs.ClawMotor_Left, MotorType.kBrushless);
        RightMotor = new CANSparkMax(IDs.ClawMotor_Right, MotorType.kBrushless);
        Piston = new DoubleSolenoid(IDs.PneumaticControlModule, PneumaticsModuleType.CTREPCM, IDs.ClawPistons_Forward, IDs.ClawPistons_Reverse);
    }

    public CommandBase SpinMotors(double motorSpeed)
    {
        return runEnd(() ->
        {
            LeftMotor.set(motorSpeed);
            RightMotor.set(motorSpeed);
        }, () ->
        {
            LeftMotor.set(0);
            RightMotor.set(0);
        });
    }

    public CommandBase ToggleClaw(Value clawMode)
    {
        return runOnce(() -> 
        {
            Piston.set(clawMode);
        });
    }
}