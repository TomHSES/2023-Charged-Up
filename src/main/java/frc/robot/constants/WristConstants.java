package frc.robot.constants;

public final class WristConstants 
{
    public static double kWristControllerP = 0.001;

    public static double kWristControllerI = 0.0;

    public static double kWristControllerD = 0.0;

    public static double kWristControllerTolerance = 1000;

    // A value that's multiplied by the Falcon 500's built in position encoder value
    // Ideally returns an accurate enough approximation for the angular change
    public static double kWristEncoderToDegreeConversionFactor = 0.01;

    public static double kWristMaxSpeed = 0.33;
}
