package frc.robot.constants;

public final class WristConstants 
{
    public static double kWristControllerP = 0.001;

    public static double kWristControllerI = 0.0;

    public static double kWristControllerD = 0.0;

    public static double kWristControllerTolerance = 1000;

    public static double kWristExtendedPosition = 22500;

    // Where the COM changes to an extent where the wrist falls in the opposite direction
    public static double kWristMidPosition = 12000;

    public static double kAltSpeedExponent = 1 + (kWristMidPosition / kWristExtendedPosition);

    public static double kAltWristExtendedPosition = Math.pow(kWristExtendedPosition, kAltSpeedExponent);
}
