package frc.robot.subsystems;

import java.util.Vector;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/*
 * Handles all thing related to Gyroscopes
 */
public class LimelightSystem extends SubsystemBase {

    public NetworkTable LimelightTable;

    public Double Tx;

    public Double Ty;

    public Double Tv;

    public PIDController AngleCorrectionController;

    public Double LastAngularOffset;

    public LimelightSystem() 
    {
        LimelightTable = NetworkTableInstance.getDefault().getTable("limelight");
        AngleCorrectionController = new PIDController(0.1, 0, 0);
        AngleCorrectionController.setSetpoint(0);
        //AngleCorrectionController.setIntegratorRange(-0.25, 0.25);
        //AngleCorrectionController.enableContinuousInput(-30, 30);
        AngleCorrectionController.reset();
       //setDefaultCommand(Listen());
    }

    public CommandBase RotateToTarget(TankDriveSystem tankDriveSystem, Boolean scan, Double maxSpeed)
    {
        return run(() ->
        {
            Double xPose = GetLimelightValue("tx", -1).doubleValue();
            if (LastAngularOffset != null && 
            (xPose == 0.0 && Math.abs(LastAngularOffset) > 5 || Math.abs(LastAngularOffset - xPose) > 5))
            {
                xPose = LastAngularOffset;
                //return;
            }
            else
            {
                LastAngularOffset = xPose;
            }
            Double theta = -AngleCorrectionController.calculate(xPose);
            SmartDashboard.putNumber("Theta", theta);
            SmartDashboard.putNumber("xPose", xPose);
            if (scan && xPose == 0)
            {
                tankDriveSystem.ArcadeDrive(maxSpeed / 2, 0);
            }
            else
            {
                Double minSpeed = 0.25;
                Double offSetSpeed = Math.abs(xPose) * (maxSpeed - minSpeed) / 30;
                Double rotSpeed = minSpeed + offSetSpeed;
                SmartDashboard.putNumber("rotSpeed", rotSpeed);
                theta = MathUtil.clamp(theta, -0.5, 0.5);
                //tankDriveSystem.ArcadeDrive(theta, 0);
            }
        });
    }

    public Double Sign(Double value)
    {
        return value < 0.0 ? -1.0 : (value > 0.0 ? 1.0 : 0.0);
    }

    private void SetLimelightValue(String entryName, Number value)
    {
        LimelightTable.getEntry(entryName).setNumber(value);
    }

    private Number GetLimelightValue(String entryName, Number defaultNumber)
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

    @Override
    public void periodic()
    {
        Tx = GetLimelightValue("tx", -1).doubleValue();
        Ty = GetLimelightValue("ty", -1).doubleValue();
        Tv = GetLimelightValue("tv", -1).doubleValue();
        SmartDashboard.putNumber("tx", Tx);
        SmartDashboard.putNumber("ty", Ty);
        SmartDashboard.putNumber("tv", Tv);
    }

    public Number EstimateConeAreaFromCorners()
    {
        Number[] corners = LimelightTable.getEntry("tcornxy").getNumberArray(new Number[0]); 
        SmartDashboard.putNumber("Corners", corners.length);
        if (corners.length != 6)
        {
            return -1;
        }

        double bottomLeft = corners[0].doubleValue();
        double bottomRight = corners[5].doubleValue();
        double middleTop = corners[4].doubleValue();
        double approxMiddleBottom = (corners[1].doubleValue() + corners[5].doubleValue()) / 2;
        return ((bottomRight - bottomLeft) * (middleTop - approxMiddleBottom)) / 2;
    }
    
    public CommandBase Listen() 
    {
        return run(
            () -> {
               // SmartDashboard.putNumber("Area", EstimateConeAreaFromCorners().doubleValue());
                /*NetworkTableEntry tCornEntry = LimelightTable.getEntry("tcornxy");
                SmartDashboard.putString("tCornEntry Status", tCornEntry == null ? "null" : "good");
                if (tCornEntry.exists()) {
                    Number[] cornerData = tCornEntry.getNumberArray(new Number[0]);
                if (cornerData == null)
                {
                    SmartDashboard.putString("Corner Data", "null");
                    return;
                }

                for (var i = 0; i < cornerData.length; i++)
                {
                    if (cornerData[i] == null)
                    {
                        SmartDashboard.putString("Corner Data: " + i, "null");
                        continue;
                    }
                    SmartDashboard.putNumber("Corner Data: " + i, cornerData[i].doubleValue());
                }*/
            });
    }
}