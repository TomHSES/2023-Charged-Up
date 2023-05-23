package frc.robot.constants;

import frc.robot.datatypes.AutonomousRoutineMode;

public final class AutonomousConstants 
{
    public static AutonomousRoutineMode AutonomousRoutine = AutonomousRoutineMode.OnlyDrive;
    
    public static double kOnChargeStationAngle = 11.0;

    public static double kSlowDriveSpeed = GeneralConstants.kRobotDriveInertia + 0.1;

    public static double kFastDriveSpeed = GeneralConstants.kRobotDriveInertia + 0.3;

    // Porportional value for the Auto_Drive PID controller
    // This value is per percentage of max drive speed
    // This value is in terms of DutyCycleEncoder value
    public static double kDrivePIDPercentP = 0.001;

    // Integral value for the Auto_Drive PID controller
    // This value is in terms of DutyCycleEncoder value
    public static double kDrivePIDPercentI = 0.001;

    // Derivative value for the Auto_Drive PID controller
    // This value is in terms of DutyCycleEncoder value
    public static double kDrivePIDPercentD = 0.0;

    // Dynamically assigned at the start of Auto_Initialisation
    // Set manually by the driver in SmartDashboard
    // 1: Parallel to the game-piece furthest away from loading zone
    // 4: Parallel to the game-piece closest to the loading zone
    // 2 - 3: Etc, etc.
    public static int StartingPosition = -1;
}
