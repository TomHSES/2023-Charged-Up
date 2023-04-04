package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClawSystem extends SubsystemBase 
{
    public CANSparkMax LeftMotor;

    public RelativeEncoder LeftEncoder;

    public CANSparkMax RightMotor;

    public RelativeEncoder RightEncoder;

    public DoubleSolenoid Piston;

    public double MotorSpeed = 0.33;

    public ClawSystem(int leftNEOCanID, int rightNEOCanID, int pcmCanID, int pistonForward, int pistonReverse) 
    {
        LeftMotor = new CANSparkMax(leftNEOCanID, MotorType.kBrushless);
        RightMotor = new CANSparkMax(rightNEOCanID, MotorType.kBrushless);
        Piston = new DoubleSolenoid(pcmCanID, PneumaticsModuleType.CTREPCM, pistonForward, pistonReverse);
    }

    public CommandBase SpinMotors()
    {
        return runEnd(() ->
        {
            LeftMotor.set(MotorSpeed);
            RightMotor.set(MotorSpeed);
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