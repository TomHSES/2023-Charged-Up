package frc.robot.commands.Wrist;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.WristSystem;

public class RotateWrist extends CommandBase
{
    public boolean HasInit;

    public WristSystem WristSystem;

    public double Velocity;

    public RotateWrist(WristSystem wristSystem, double velocity)
    {
        HasInit = false;
        WristSystem = wristSystem;
        Velocity = velocity;
        addRequirements(WristSystem);
    }

    @Override
    public void execute()
    {
        WristSystem.WristMotor.set(TalonFXControlMode.PercentOutput, WristSystem.CalculateWristSpeed(-0.5, 0.8));
    }

    @Override
    public void end(boolean interrupted)
    {
        WristSystem.WristMotor.set(TalonFXControlMode.PercentOutput, 0);
    }
}
