package frc.robot.commands.IntakeArm;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ElevatorSystem;
import frc.robot.subsystems.WristSystem;

public class ManualIntakeArm extends CommandBase
{
    public WristSystem ElevatorSystem;

    public double Speed;

    public ManualIntakeArm(WristSystem elevatorSystem, double speed)
    {
        ElevatorSystem = elevatorSystem;
        Speed = speed;
        addRequirements(ElevatorSystem);
    }
    
    @Override
    public void execute()
    {
        ElevatorSystem.WristMotor.set(TalonFXControlMode.PercentOutput, Speed);
    }

    @Override
    public void end(boolean interrupted)
    {
        ElevatorSystem.WristMotor.set(TalonFXControlMode.PercentOutput, 0);
    }
}
