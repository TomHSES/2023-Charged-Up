package frc.robot.commands.Wrist;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.WristSystemTwo;

public class ManualWristSystem extends CommandBase
{
    public WristSystemTwo WristSystem;

    public Double Speed;

    public ManualWristSystem(WristSystemTwo elevatorSystem, double speed)
    {
        WristSystem = elevatorSystem;
        Speed = speed;
        addRequirements(WristSystem);
    }
    
    @Override
    public void execute()
    {
        WristSystem.ElevatorMotor.set(TalonFXControlMode.PercentOutput, Speed);
    }

    @Override
    public void end(boolean interrupted)
    {
        WristSystem.ElevatorMotor.set(TalonFXControlMode.PercentOutput, 0.0);
    }
}