package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class SparkMaxSystem 
{
    public final CANSparkMax SparkMax;

    public final RelativeEncoder Encoder;

    public final SparkMaxPIDController PIDController;

    public SparkMaxSystem(int canID, double posConvFactor, double velConvFactor, double p, double i, double d, double ff, double outPutRange) 
    {
        SparkMax = new CANSparkMax(canID, MotorType.kBrushless);

        SparkMax.restoreFactoryDefaults();

        Encoder = SparkMax.getEncoder();
        PIDController = SparkMax.getPIDController();
        PIDController.setFeedbackDevice(Encoder);

        Encoder.setPositionConversionFactor(posConvFactor);
        Encoder.setVelocityConversionFactor(velConvFactor);

        PIDController.setP(p);
        PIDController.setI(i);
        PIDController.setD(d);
        PIDController.setFF(ff);
        PIDController.setOutputRange(-outPutRange, outPutRange);
        PIDController.setReference(outPutRange, ControlType.kPosition);

        SparkMax.setIdleMode(IdleMode.kBrake);
        SparkMax.setSmartCurrentLimit(50);

        SparkMax.burnFlash();

        Encoder.setPosition(0);
    }
}
