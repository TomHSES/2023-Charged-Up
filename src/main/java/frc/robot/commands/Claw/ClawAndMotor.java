package frc.robot.commands.Claw;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClawSystem;

public class ClawAndMotor extends CommandBase 
{
    public ClawSystem Claw;

    public double MotorVelocity;

    public boolean HasInit;

    private ClawAndMotor(ClawSystem clawSystem, double motorVelocity) 
    {
        Claw = clawSystem;
        MotorVelocity = motorVelocity;
        HasInit = false;
        addRequirements(Claw);
    }

    @Override
    public void execute()
    {
        if (!HasInit)
        {
            Claw.ToggleClaw(Value.kForward);
            HasInit = true;
            return;
        }

        Claw.SpinMotors(MotorVelocity);
    }

    @Override
    public void end(boolean interrupted) 
    {
        Claw.ToggleClaw(Value.kReverse);
    }
}