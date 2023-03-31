package frc.robot.commands;

import frc.robot.subsystems.IntakeArmSystem;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class FalconSystemRev extends CommandBase 
{
    public IntakeArmSystem FalconSystem;

    private boolean HasInit = false;

    private double InitialSpeed;

    public double TargetSpeed;

    public double IncrementSpeed;

    public FalconSystemRev(IntakeArmSystem falconSystem, double targetSpeed, double incrementSpeed) 
    {
        FalconSystem = falconSystem;
        TargetSpeed = targetSpeed;
        IncrementSpeed = Math.abs(incrementSpeed);
        addRequirements(falconSystem);
    }

    /**
     * We avoid using initialize() and instead opt for HasInit because of potential issues arising
     * from call order. It seems that execute can be called before initialize().
     */
    @Override
    public void initialize() { }

    @Override
    public void execute() 
    {
        if (!HasInit)
        {
            InitialSpeed = FalconSystem.CurrentSpeed;
            if (TargetSpeed < InitialSpeed)
            {
               IncrementSpeed *= -1;
            }
            HasInit = true;
            return;
        }

        FalconSystem.IncremenentSpeed(IncrementSpeed);
        FalconSystem.FalconMotor.set(TalonFXControlMode.PercentOutput, FalconSystem.CurrentSpeed);
    }

    @Override
    public void end(boolean interrupted) 
    {
        if (IncrementSpeed < 0 && FalconSystem.CurrentSpeed < TargetSpeed
        || IncrementSpeed > 0 && FalconSystem.CurrentSpeed > TargetSpeed)
        {
            FalconSystem.CurrentSpeed = TargetSpeed;
            FalconSystem.FalconMotor.set(TalonFXControlMode.PercentOutput, FalconSystem.CurrentSpeed);
        }
    }

    @Override
    public boolean isFinished() 
    {
        return IncrementSpeed < 0 ? FalconSystem.CurrentSpeed <= TargetSpeed : FalconSystem.CurrentSpeed >= TargetSpeed;
    }
}