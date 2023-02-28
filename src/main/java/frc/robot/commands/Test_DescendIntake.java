package frc.robot.commands;

import frc.robot.subsystems.FalconSystem;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Test_DescendIntake extends CommandBase 
{
    public FalconSystem FalconSystem;

    public Test_DescendIntake(FalconSystem intakeArmSystem) 
    {
        FalconSystem = intakeArmSystem;
        addRequirements(intakeArmSystem);
    }

    @Override
    public void execute() 
    {
        if (FalconSystem.FalconMotor.getSensorCollection().getIntegratedSensorPosition() > 2000)
        {
            FalconSystem.FalconMotor.set(TalonFXControlMode.PercentOutput, 0.041f);
        }

        SmartDashboard.putNumber("Sensor P", FalconSystem.FalconMotor.getSensorCollection().getIntegratedSensorPosition());
    }

    @Override
    public void end(boolean interrupted) 
    {
        FalconSystem.FalconMotor.set(TalonFXControlMode.PercentOutput, 0);
    }

    @Override
    public boolean isFinished() 
    {
        return FalconSystem.FalconMotor.getSensorCollection().getIntegratedSensorPosition() <= 2000;
    }
}