package frc.robot.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class GyroscopeSystem extends SubsystemBase 
{
    public ADXRS450_Gyro Gyroscope;

    public Double AverageDrift; // Degrees / Seconds

    public GyroscopeSystem() 
    {
        Gyroscope = new ADXRS450_Gyro();

        Shuffleboard.getTab("Robot Data").add(Gyroscope);
    }

    public CommandBase CalibrateGyroscope(TankDriveSystem driveSystem) 
    {
        return run(() -> 
        {
            driveSystem.TankDrive(0, 0);
            Gyroscope.calibrate();
            Gyroscope.reset();
        });
    }
}