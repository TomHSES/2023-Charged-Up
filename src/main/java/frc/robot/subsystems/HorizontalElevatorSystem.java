package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.Elevator.IdleHorizontalElevatorSystem;
import frc.robot.constants.ElevatorConstants;
import frc.robot.constants.LaunchConstants;

public class HorizontalElevatorSystem extends SubsystemBase
{
    public final CANSparkMax ElevatorMotor;

    public final RelativeEncoder Encoder;

    public PIDController ElevatorController;

    public double ElevatorRefEncoderPosition;

    public HorizontalElevatorSystem(int canID)
    {
        ElevatorMotor = new CANSparkMax(canID, MotorType.kBrushless);
        ElevatorMotor.restoreFactoryDefaults();

        Encoder = ElevatorMotor.getEncoder();
        ElevatorController = new PIDController(ElevatorConstants.kHorizontalElevatorControllerP, ElevatorConstants.kHorizontalElevatorControllerI, ElevatorConstants.kHorizontalElevatorControllerD);

        Encoder.setPositionConversionFactor(ElevatorConstants.kHorizontalElevatorPositionConversionFactor);
        Encoder.setVelocityConversionFactor(ElevatorConstants.kHorizontalElevatorVelocityConversionFactor);

        ElevatorMotor.setIdleMode(IdleMode.kBrake);
        ElevatorMotor.setSmartCurrentLimit(50);

        ElevatorMotor.burnFlash();

        Encoder.setPosition(0);
        setDefaultCommand(new IdleHorizontalElevatorSystem(this));
    }

    @Override
    public void periodic()
    {
        if (LaunchConstants.Log_ElevatorSystems)
        {
            SmartDashboard.putNumber("HE Encoder Position", Encoder.getPosition());
        }
    }
}