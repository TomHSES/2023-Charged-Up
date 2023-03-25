package frc.robot.subsystems;

import edu.wpi.first.networktables.BooleanPublisher;
import edu.wpi.first.networktables.DoubleArraySubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LaunchConstants;

public class GyroscopeSystem extends SubsystemBase 
{
    // The single-axis 'default' gyro attached to the RoboRio
    public ADXRS450_Gyro Gyroscope;

    // The NetworkTable instance that MPU related values are published to
    public NetworkTable MPUTable;

    // A subscriber to the topic where MPU axial data is published
    // This instance listens for changes and sets MPUX, MPUY, and MPUZ 
    // to their respective values in MPUListen
    public DoubleArraySubscriber MPUData;

    public double MPUX;

    public double MPUY;

    public double MPUZ;

    // A publisher that allows us to recalibrate the MPU on the Raspberry Pi
    // Publish a value of true for a calibration
    // After a calibration has occured, the Pi automatically resets the shared value to false
    public BooleanPublisher RecalibrateMPU;

    public GyroscopeSystem() 
    {
        Gyroscope = new ADXRS450_Gyro();
        Shuffleboard.getTab("Robot Data").add(Gyroscope);

        // Initialises MPU related data only if the program is launched with that intent
        if (LaunchConstants.MPU)
        {
            MPUTable = NetworkTableInstance.getDefault().getTable("MPU Data");
            MPUData = MPUTable.getDoubleArrayTopic("Data").subscribe(new double[] { 0, 0, 0 });
            RecalibrateMPU = MPUTable.getBooleanTopic("Recalibrate").publish();
            setDefaultCommand(MPUListen());
            RecalibrateMPU.set(true);
        }
    }

    public CommandBase CalibrateGyroscope(TankDriveSystem driveSystem) 
    {
        return runOnce(() -> 
        {
            driveSystem.TankDrive(0, 0);
            Gyroscope.calibrate();
            Gyroscope.reset();
        });
    }

    public CommandBase MPUListen() 
    {
        return run(() -> 
        {
            double[] value = MPUData.get();
            MPUX = value[0];
            MPUY = value[1];
            MPUZ = value[2];

            if (LaunchConstants.Log_GyroscopeSystem)
            {
                SmartDashboard.putNumber("Gyro: MPUX", MPUX);
                SmartDashboard.putNumber("Gyro: MPUY", MPUY);
                SmartDashboard.putNumber("Gyro: MPUZ", MPUZ);
            }
        });
    }

    public CommandBase MPURecalibrate()
    {
        return runOnce(() -> 
        {
            RecalibrateMPU.set(true);
        });
    }
}