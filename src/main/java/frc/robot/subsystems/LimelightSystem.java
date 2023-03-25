package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LimelightSystem extends SubsystemBase {

    public NetworkTable LimelightTable;

    public Double Tx;

    public Double Ty;

    public Double Tv;

    public PIDController AngleCorrectionController;

    public Double LastAngularOffset;

    public Double StallAngularOffset = 0.0;

    public int StallTimer = 0;

    public Double StallAdjustment = 0.0;

    public LimelightSystem() 
    {
        LimelightTable = NetworkTableInstance.getDefault().getTable("limelight");
        AngleCorrectionController = new PIDController(0.025, 0.01, 0);
        AngleCorrectionController.setSetpoint(0);
        //AngleCorrectionController.setIntegratorRange(-0.33, 0.25);
        //AngleCorrectionController.enableContinuousInput(-30, 30);
        AngleCorrectionController.reset();
       //setDefaultCommand(Listen());
    }

    public CommandBase TogglePipeline()
    {
        return runOnce(() ->
        {
            Integer pipeline = GetLimelightValue("pipeline", -1).intValue();
            if (pipeline == 1)
            {
                SetLimelightValue("pipeline", 2);
            }
            else
            {
                SetLimelightValue("pipeline", 1);
            }
        });
    }

    public void SetLimelightValue(String entryName, Number value)
    {
        LimelightTable.getEntry(entryName).setNumber(value);
    }

    public Number GetLimelightValue(String entryName, Number defaultNumber)
    {
        return LimelightTable.getEntry(entryName).getNumber(defaultNumber);
    }

    public CommandBase ToggleLED()
    {
        return runOnce(() ->
        {
            Integer currentLEDMode = GetLimelightValue("ledMode", -1).intValue();
            Integer newLEDMode = currentLEDMode + 1;
            if (newLEDMode > 3)
            {
                newLEDMode = 0;
            }
            
            String smartDashboardLog = "null";
            switch (newLEDMode)
            {
                case 0:
                    smartDashboardLog = "Default";
                    break;

                case 1:
                    smartDashboardLog = "Off - Forced";
                    break;

                case 2:
                    smartDashboardLog = "Blink - Forced";
                    break;

                case 3:
                    smartDashboardLog = "On - Forced";
                    break;
            }
            SmartDashboard.putString("LED Mode", smartDashboardLog);
            SetLimelightValue("ledMode", newLEDMode);
        });
    }

   /*@Override
    public void periodic()
    {
        Tx = GetLimelightValue("tx", -1).doubleValue();
        Ty = GetLimelightValue("ty", -1).doubleValue();
        Tv = GetLimelightValue("tv", -1).doubleValue();
        SmartDashboard.putNumber("tx", Tx);
        SmartDashboard.putNumber("ty", Ty);
        SmartDashboard.putNumber("tv", Tv);
    }*/
}