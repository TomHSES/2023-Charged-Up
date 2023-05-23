package frc.robot.commands.Autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.TankDriveSystem;

public class Auto_TimeDrive extends CommandBase
{
    public TankDriveSystem TankDriveSystem;

    public double DesiredTime;

    public Timer Timer;

    public double MaxSpeed;

    public boolean HasInit;

    public Auto_TimeDrive(TankDriveSystem tankDriveSystem, double speed, double desiredTime)
    {
        TankDriveSystem = tankDriveSystem;
        Timer = new Timer();
        DesiredTime = desiredTime;
        MaxSpeed = speed;
        addRequirements(tankDriveSystem);
    }

    @Override
    public void execute()
    {
        if (!HasInit)
        {
            Timer.start();
            HasInit = true;
            return;
        }

        if (SmartDashboard.getBoolean("Autonomous", true))
        {
            TankDriveSystem.ArcadeDrive(0, MaxSpeed);
        }
   }

    @Override
    public void end(boolean interrupted)
    {
        TankDriveSystem.StopDrive();
    }

    @Override
    public boolean isFinished()
    {
        return Timer.hasElapsed(DesiredTime);
    }
}