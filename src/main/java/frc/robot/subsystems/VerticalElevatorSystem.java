package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ElevatorConstants;
import frc.robot.constants.LaunchConstants;

public class VerticalElevatorSystem extends SubsystemBase
{
    public final CANSparkMax ElevatorMotor;

    public final RelativeEncoder Encoder;

    public DigitalInput ElevatorLimitSwitch;

    public PIDController ElevatorController;

    public double ElevatorRefEncoderPosition;

    public VerticalElevatorSystem(int canID)
    {
        ElevatorMotor = new CANSparkMax(canID, MotorType.kBrushless);
        ElevatorMotor.restoreFactoryDefaults();

        Encoder = ElevatorMotor.getEncoder();
        ElevatorController = new PIDController(ElevatorConstants.kVerticalElevatorControllerP, ElevatorConstants.kVerticalElevatorControllerI, ElevatorConstants.kVerticalElevatorControllerD);

        Encoder.setPositionConversionFactor(ElevatorConstants.kVerticalElevatorPositionConversionFactor);
        Encoder.setVelocityConversionFactor(ElevatorConstants.kVerticalElevatorVelocityConversionFactor);

        ElevatorMotor.setIdleMode(IdleMode.kBrake);
        ElevatorMotor.setSmartCurrentLimit(50);

        ElevatorMotor.burnFlash();

        Encoder.setPosition(0);
    }

    @Override
    public void periodic()
    {
        if (LaunchConstants.Log_ElevatorSystems)
        {
            SmartDashboard.putNumber("VE Inertia", ElevatorConstants.VerticalMotorInertia);
            SmartDashboard.putNumber("VE Encoder Position", Encoder.getPosition());
        }
    }
}
