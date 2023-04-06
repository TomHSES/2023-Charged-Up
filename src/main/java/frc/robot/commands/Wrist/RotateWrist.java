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
        if (!HasInit)
        {
            WristSystem.TogglePneumaticBrake(false);
            HasInit = true;
            return;
        }

        WristSystem.WristMotor.set(TalonFXControlMode.PercentOutput, Velocity);
    }

    @Override
    public void end(boolean interrupted)
    {
        WristSystem.TogglePneumaticBrake(true);
    }
}
