package frc.robot.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/*
 * Handles all thing related to Gyroscopes
 */
public class GyroscopeSystem extends SubsystemBase {

    public ADXRS450_Gyro Gyroscope;

    public GyroscopeSystem() {
        //Gyroscope = new ADXRS450_Gyro();
        //Shuffleboard.getTab("Robot Data").add(Gyroscope);
    }

    /**
     * @return a command that forcefully stops the robot as to calibrate its gyroscope
     */
    public CommandBase CalibrateGyroscope(TankDriveSystem driveSystem) {
        return runOnce(
                () -> {
                    driveSystem.TankDrive(0, 0);
                    Gyroscope.calibrate();
                });
    }

    /**
     * An example method querying a boolean state of the subsystem (for example, a
     * digital sensor).
     *
     * @return value of some boolean subsystem state, such as a digital sensor.
     */
    public boolean exampleCondition() {
        // Query some boolean state, such as a digital sensor.
        return false;
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
}