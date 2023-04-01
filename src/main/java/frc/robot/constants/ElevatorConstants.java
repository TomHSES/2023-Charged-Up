package frc.robot.constants;

public final class ElevatorConstants 
{
    public static double kSparkMaxEncoderError = 0.1;

    public static double kVerticalElevatorPositionConversionFactor = 1.0;

    public static double kVerticalElevatorVelocityConversionFactor = 1.0;

    public static double kVerticalElevatorControllerP = 0.01;

    public static double kVerticalElevatorControllerI = 0.01;

    public static double kVerticalElevatorControllerD = 0.0;

    // The minimum percent speed of the big NEO motor required to counteract the gravity felt by the vertical elevator
    // Calibrated as a part of CalibrateVerticalElevatorSystem
    public static double VerticalMotorInertia = Double.NaN;

    public static double VerticalElevatorBottomPosition = Double.NaN;

    public static double HorizontalElevatorBottomPosition = Double.NaN;
}
