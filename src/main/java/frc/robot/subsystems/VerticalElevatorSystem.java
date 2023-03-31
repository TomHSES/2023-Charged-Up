package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.GeneralConstants;

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
        ElevatorController = new PIDController(canID, canID, canID);

        Encoder.setPositionConversionFactor(GeneralConstants.kVerticalElevatorPositionConversionFactor);
        Encoder.setVelocityConversionFactor(velConvFactor);

        PIDController.setP(p);
        PIDController.setI(i);
        PIDController.setD(d);
        PIDController.setFF(ff);
        PIDController.setOutputRange(-outPutRange, outPutRange);
        PIDController.setReference(outPutRange, ControlType.kPosition);

        SparkMax.setIdleMode(IdleMode.kCoast);
        SparkMax.setSmartCurrentLimit(50);

        SparkMax.burnFlash();

        Encoder.setPosition(0);
    }

    @Override
    public void periodic()
    {

    }
}
