package frc.robot.datatypes;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import frc.robot.subsystems.LimelightSystem;
import frc.robot.subsystems.GyroscopeSystem;

/**
 * An alternative system to PID
**/
public class LimelightPID
{
    public LimelightSystem LimelightSystem;

    public Double OutputValue;

    public Double Porportion;

    public Double MaxPorportionalSpeed;

    public Double Integral;

    public Double IntegralResistance = 0.1;

    public Double MaxIntegralSpeed;

    public Double Derivative;

    public Double SetPoint;

    public Double SetPointTolerance;

    public ADXRS450_Gyro Gyroscope;

    public Double InitialAngularOffset;

    public Double HorizontalAngularDifference;

    public Double LastHorizontalAngularDifference;

    public PIDController PIDController_Internal;

    public GyroscopeSystem GyroscopeSystem;

    public LimelightPID(Double porportionRatio, 
    Double integralRatio, 
    Double derivativeRatio,
    Double desiredSetPoint,
    Double tolerance,
    GyroscopeSystem gyroscopeSystem)
    {
        Porportion = porportionRatio;
        Derivative = derivativeRatio;
        Integral = integralRatio;
        PIDController_Internal = new PIDController(porportionRatio, integralRatio, derivativeRatio);
        SetPoint = desiredSetPoint;
        PIDController_Internal.setSetpoint(desiredSetPoint);
        PIDController_Internal.setTolerance(tolerance);
    }

    public void Reset()
    {
        InitialAngularOffset = LimelightSystem.Tx;
        GyroscopeSystem.Gyroscope.reset();
    }

    public void TrackHorizontalAngularDifference()
    {
        LastHorizontalAngularDifference = HorizontalAngularDifference;

        if (LimelightSystem.Tx == 0.0)
        {
            HorizontalAngularDifference = GyroscopeSystem.Gyroscope.getAngle() + InitialAngularOffset;
        }
        else
        {
            HorizontalAngularDifference = LimelightSystem.Tx;
        }
    }

    public Boolean AtSetPoint(Double juxtapositionedValue)
    {
        return Math.abs(juxtapositionedValue - SetPoint) < SetPointTolerance ? true : false;
    }

    public void Calculate(Double maxSpeed)
    {
        Double porportionalIncrement = MathUtil.clamp((HorizontalAngularDifference - SetPoint) * Porportion, -MaxPorportionalSpeed, MaxPorportionalSpeed);
        Double integralIncrement = 0.0;
        if (!AtSetPoint(HorizontalAngularDifference) && 
        Math.abs(HorizontalAngularDifference - LastHorizontalAngularDifference) < IntegralResistance)
        {
            integralIncrement += Integral;
        }
        OutputValue = MathUtil.clamp(OutputValue + porportionalIncrement, 0, 0);
    }
}
