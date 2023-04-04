package frc.robot.constants;

public final class ElevatorConstants 
{
    public static double kSparkMaxEncoderError = 0.1;

    public static double kVerticalElevatorPositionConversionFactor = 1.0;

    public static double kVerticalElevatorVelocityConversionFactor = 1.0;

    public static double kVerticalElevatorControllerP = 0.01;

    public static double kVerticalElevatorControllerI = 0.01;

    public static double kVerticalElevatorControllerD = 0.0;

    public static double kHorizontalElevatorPositionConversionFactor = 1.0;

    public static double kHorizontalElevatorVelocityConversionFactor = 1.0;

    public static double kHorizontalElevatorControllerP = 0.01;

    public static double kHorizontalElevatorControllerI = 0.01;

    public static double kHorizontalElevatorControllerD = 0.0;

    // The minimum percent speed of the big NEO motor required to counteract the gravity felt by the vertical elevator
    // Calibrated as a part of CalibrateVerticalElevatorSystem
    public static double VerticalMotorInertia = Double.NaN;

    public static double HorizontalMotorInertia = 0.02;

    public static double VerticalElevatorTopPosition = Double.NaN;

    public static double HorizontalElevatorBottomPosition = Double.NaN;
}
