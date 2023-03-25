package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class CancelAllCommands extends CommandBase
{
    @Override
    public boolean isFinished() 
    {
        return true;
    }
}
