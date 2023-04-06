package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.constants.GeneralConstants;
import frc.robot.constants.IDs;
import frc.robot.constants.LaunchConstants;

public class TankDriveSystem extends SubsystemBase 
{
    public final WPI_VictorSPX LeftFront_Motor;
    public final WPI_VictorSPX LeftBack_Motor;
    public final WPI_VictorSPX RightFront_Motor;
    public final WPI_VictorSPX RightBack_Motor;

    public final MotorControllerGroup LeftMotorController;
    public final MotorControllerGroup RightMotorController;

    public final DutyCycleEncoder LeftEncoder;
    public final DutyCycleEncoder RightEncoder;

    public final DifferentialDrive Drive;

    public TankDriveSystem()
    {
        LeftFront_Motor = new WPI_VictorSPX(IDs.DriveMotor_LeftFront);
        LeftBack_Motor = new WPI_VictorSPX(IDs.DriveMotor_LeftBack);
        RightFront_Motor = new WPI_VictorSPX(IDs.DriveMotor_RightFront);
        RightBack_Motor = new WPI_VictorSPX(IDs.DriveMotor_RightBack);

        LeftMotorController = new MotorControllerGroup(LeftFront_Motor, LeftBack_Motor);
        RightMotorController = new MotorControllerGroup(RightFront_Motor, RightBack_Motor);

        Drive = new DifferentialDrive(LeftMotorController, RightMotorController);

        LeftEncoder = new DutyCycleEncoder(IDs.DriveEncoder_Left);
        LeftEncoder.setDistancePerRotation(GeneralConstants.kDriveEncoderToFeetConversionFactor);

        RightEncoder = new DutyCycleEncoder(IDs.DriveEncoder_Right);
        RightEncoder.setDistancePerRotation(GeneralConstants.kDriveEncoderToFeetConversionFactor);
    }

    @Override
    public void periodic()
    {
        if (LaunchConstants.Log_DriveTrain)
        {
            SmartDashboard.putNumber("Enc. Freq. L", LeftEncoder.getDistance());
            SmartDashboard.putNumber("Enc. Freq. R", RightEncoder.getDistance());
        }
    }

    /**
     * @return A mechanism that simply implement driving for both Logitech Joystick and Controller
     * Alternate between controller type through modifying the LaunchConstants
     */
    public CommandBase DefaultDrive(CommandJoystick joystick, CommandXboxController controller) 
    {
        return run(() -> 
        {
            if (LaunchConstants.Controller)
            {
                ArcadeDrive(controller.getRawAxis(3), controller.getRawAxis(1));
            }
            else
            {
                double rawThrottle = joystick.getThrottle();
                double throttle = Math.abs((rawThrottle - 1) / 2);
                ArcadeDrive(joystick.getRawAxis(2) * throttle, joystick.getRawAxis(1) * throttle);

                if (LaunchConstants.Log_DriveTrain)
                {
                    SmartDashboard.putNumber("Throttle", throttle);
                    SmartDashboard.putNumber("Raw Throttle", rawThrottle);
                }
            }
        });
    }

    public void StopDrive() 
    {
        Drive.tankDrive(0, 0);
    }

    public void TankDrive(double leftSpeed, double rightSpeed) 
    {
        if (!LaunchConstants.Safety_RestrictDriveTrain)
        {
            Drive.tankDrive(leftSpeed, rightSpeed);
        }
    }

    public void ArcadeDrive(double rotation, double speed) 
    {
        if (!LaunchConstants.Safety_RestrictDriveTrain)
        {
            Drive.arcadeDrive(speed, rotation);
        }
    }
}