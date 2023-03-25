package frc.robot.threads;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.TankDriveSystem;

public class Encoders extends Thread
{
    public TankDriveSystem TankDriveSystem;

    public final DutyCycleEncoder Tank_LeftEncoder;

    public double Tank_LeftEncoder_Drift;
    
    public final DutyCycleEncoder Tank_RightEncoder;

    public double Tank_RightEncoder_Drift;

    public Timer CalibrationTimer;

    public boolean CalibrateTankEncoders = false;

    public Encoders(TankDriveSystem tankDriveSystem)
    {
        TankDriveSystem = tankDriveSystem;
        Tank_LeftEncoder = tankDriveSystem.LeftEncoder;
        Tank_RightEncoder = tankDriveSystem.RightEncoder;
        CalibrationTimer = new Timer();
    }

    public void CalibrateTankDrive()
    {
        CalibrationTimer.reset();
        TankDriveSystem.StopDrive();
        Tank_LeftEncoder.reset();
        Tank_RightEncoder.reset();
        CalibrateTankEncoders = true;
    }

    @Override
    public void run()
    {
        try
        {
            if (CalibrateTankEncoders)
            {
                CalibrationTimer.start();
                TankDriveSystem.StopDrive();
                if (CalibrationTimer.hasElapsed(1))
                {
                    Tank_LeftEncoder_Drift = Tank_LeftEncoder.getAbsolutePosition();
                    Tank_RightEncoder_Drift = Tank_RightEncoder.getAbsolutePosition();
                    CalibrateTankEncoders = false;
                }
            }
        }
        catch (Exception exception)
        {
            
        }
    }
}
