package frc.robot.commands.Wrist;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.WristSystem;

public class RotateWrist_Uncontrolled extends CommandBase
{
    public WristSystem WristSystem;

    public double Velocity;

    public RotateWrist_Uncontrolled(WristSystem wristSystem, double velocity)
    {
        WristSystem = wristSystem;
        Velocity = velocity;
        addRequirements(WristSystem);
    }

    @Override
    public void execute()
    {
        WristSystem.WristMotor.set(TalonFXControlMode.PercentOutput, Velocity);
    }

    @Override
    public void end(boolean interrupted)
    {
        WristSystem.WristMotor.set(TalonFXControlMode.PercentOutput, 0);
    }
}
