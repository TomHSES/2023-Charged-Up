package frc.robot.constants;

public final class AutonomousConstants 
{
    public static double kOnChargeStationAngle = 11.0;

    public static double kSlowDriveSpeed = GeneralConstants.kRobotDriveInertia + 0.1;

    public static double kFastDriveSpeed = GeneralConstants.kRobotDriveInertia + 0.3;

    // Dynamically assigned at the start of Auto_Initialisation
    // Set manually by the driver in SmartDashboard
    // 1: Parallel to the game-piece furthest away from loading zone
    // 4: Parallel to the game-piece closest to the loading zone
    // 2 - 3: Etc, etc.
    public static int StartingPosition = -1;
}
