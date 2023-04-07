package frc.robot.Utils;

import edu.wpi.first.wpilibj.RobotController;

public class Time 
{
    public static double GetMsClock() 
    {
        return RobotController.getFPGATime() / 1000.0;
    }
}
