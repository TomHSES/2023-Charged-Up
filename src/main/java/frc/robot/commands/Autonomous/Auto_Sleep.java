package frc.robot.commands.Autonomous;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Auto_Sleep extends CommandBase
{
    public Timer Timer;

    public int SleepTime;

    public boolean HasInit;

    public Auto_Sleep(int sleepTime)
    {
        Timer = new Timer();
        SleepTime = sleepTime;
    }

    @Override
    public void execute()
    {
        if (!HasInit)
        {
            Timer.reset();
            Timer.start();
            HasInit = true;
        }
    }

    @Override
    public void end(boolean interrupted)
    {
        Timer.stop();
    }

    @Override
    public boolean isFinished()
    {
        return Timer.hasElapsed(SleepTime);
    }
}
