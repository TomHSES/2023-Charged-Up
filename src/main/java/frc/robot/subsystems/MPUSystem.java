package frc.robot.subsystems;

import edu.wpi.first.networktables.BooleanPublisher;
import edu.wpi.first.networktables.DoubleArraySubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class MPUSystem extends SubsystemBase {
    public DoubleArraySubscriber MPUZData;
    public BooleanPublisher RecalibrateMPU;

    public MPUSystem() {
        /*NetworkTableInstance networkTableInstance = NetworkTableInstance.getDefault();
        NetworkTable mpuTable = networkTableInstance.getTable("MPU Data");
        MPUZData = mpuTable.getDoubleArrayTopic("Data").subscribe(new double[]{0, 0, 0});
        RecalibrateMPU = mpuTable.getBooleanTopic("Recalibrate").publish();
       // setDefaultCommand(Listen());
        RecalibrateMPU.set(true);*/
    }

    public CommandBase Listen() {
        return run(
            () -> {
                double[] value = MPUZData.get();
                SmartDashboard.putNumber("MPU-Data X", value[0]);
                SmartDashboard.putNumber("MPU-Data Y", value[1]);
                SmartDashboard.putNumber("MPU-Data Z", value[2]);
            });
    }

    public CommandBase Recalibrate()
    {
        return runOnce(() -> {
            RecalibrateMPU.set(true);
        });
    }
}