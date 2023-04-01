package frc.robot.commands.IntakeArm;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ElevatorSystem;
import frc.robot.subsystems.IntakeArmSystem;

public class ManualIntakeArm extends CommandBase
{
    public IntakeArmSystem ElevatorSystem;

    public double Speed;

    public ManualIntakeArm(IntakeArmSystem elevatorSystem, double speed)
    {
        ElevatorSystem = elevatorSystem;
        Speed = speed;
        addRequirements(ElevatorSystem);
    }
    
    @Override
    public void execute()
    {
        ElevatorSystem.FalconMotor.set(TalonFXControlMode.PercentOutput, Speed);
    }

    @Override
    public void end(boolean interrupted)
    {
        ElevatorSystem.FalconMotor.set(TalonFXControlMode.PercentOutput, 0);
    }
}
