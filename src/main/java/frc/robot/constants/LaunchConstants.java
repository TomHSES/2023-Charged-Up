package frc.robot.constants;

public final class LaunchConstants 
{
    // Prevents the Robot's from driving due to any circumstance
    public static boolean Safety_RestrictDriveTrain = false;

    // Whether or not the Robot is driven using a controller or a joystick
    public static boolean Controller = false;

    // Whether the Robot is currently using the MPU-6050 attached to a Raspberry Pi
    public static boolean MPU = false;

    // Whether the Robot is currently using the Limelight as a visual system
    public static boolean Limelight = false;

    public static boolean CompetitionControls = true;

    public static boolean Log_DriveTrain = true;

    public static boolean Log_AlignRobotWithLimelight = false;

    public static boolean Log_GyroscopeSystem = true;

    public static boolean Log_ElevatorSystems = true;

    public static boolean Log_WristSystem = true;
}
