package frc.robot.commands.Wrist;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Utils.Time;
import frc.robot.subsystems.WristSystemTwo;

public class ManualWristSystem_Timed extends CommandBase
{
    public WristSystemTwo WristSystem;

    public double BeginVelocity;

    public double EndVelocity;

    public double VelocityDifference;

    public double BeginTime;

    public double RunTime;

    public double ElapsedTime;

    public boolean HasInit;

    public ManualWristSystem_Timed(WristSystemTwo elevatorSystem, double beginVelocity, double endVelocity, double runTime)
    {
        HasInit = false;
        WristSystem = elevatorSystem;
        BeginVelocity = beginVelocity;
        EndVelocity = endVelocity;
        VelocityDifference = beginVelocity - endVelocity;
        RunTime = runTime;
        addRequirements(WristSystem);
    }
    
    @Override
    public void execute()
    {
        if (!HasInit)
        {
            BeginTime = Time.GetMsClock();
            HasInit = true;
            return;
        }

        ElapsedTime = (Time.GetMsClock() - BeginTime) / 1000;

        double currentVelocity = BeginVelocity - (VelocityDifference * ElapsedTime / RunTime);
        WristSystem.ElevatorMotor.set(TalonFXControlMode.PercentOutput, currentVelocity);
    }

    @Override
    public void end(boolean interrupted)
    {
        WristSystem.ElevatorMotor.set(TalonFXControlMode.PercentOutput, 0.0);
    }

    @Override
    public boolean isFinished()
    {
        return ElapsedTime >= RunTime;
    }
}