package frc.robot.constants;

public final class IntakeConstants 
{
    public static double kIntakeControllerP = 0.001;

    public static double kIntakeControllerI = 0.0;

    public static double kIntakeControllerD = 0.0;

    public static double kIntakeControllerTolerance = 1000;

    // A value that's multiplied by the Falcon 500's built in position encoder value
    // Ideally returns an accurate enough approximation for the angular change
    public static double kIntakeEncoderToDegreeConversionFactor = 0.01;

    public static double kIntakeMaxSpeed = 0.33;
}
