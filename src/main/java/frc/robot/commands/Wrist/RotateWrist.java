package frc.robot.commands.Wrist;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.WristConstants;
import frc.robot.subsystems.WristSystem;

public class RotateWrist extends CommandBase
{
    public WristSystem WristSystem;

    public double MinVelocity;

    public double MaxVelocity;

    public double AltSpeedExponent;

    public double AltWristExtendedPosition;

    public RotateWrist(WristSystem wristSystem, double minVelocity, double maxVelocity)
    {
        WristSystem = wristSystem;
        MinVelocity = minVelocity;
        MaxVelocity = maxVelocity;
        addRequirements(WristSystem);
        AltSpeedExponent = 1 + (WristConstants.kWristMidPosition / WristConstants.kWristExtendedPosition);
        AltWristExtendedPosition = Math.pow(WristConstants.kWristExtendedPosition, AltSpeedExponent);
    }

    // Min-Speed: Accounts for gravity while leaving a 'soft fall'
    // Max-Speed: Accounts for gravity while the wrist is extended
    public double CalculateWristSpeed(double minSpeed, double maxSpeed)
    {
        double currentWristPosition = WristSystem.GetFalconPosition();
        return ((currentWristPosition * (maxSpeed - minSpeed)) / WristConstants.kWristExtendedPosition) + minSpeed;
    }

    public double CalculateWristSpeed_Alt(double minSpeed, double maxSpeed)
    {
        double currentWristPosition = WristSystem.GetFalconPosition();
        return ((Math.pow(currentWristPosition, AltSpeedExponent) * (maxSpeed - minSpeed)) / AltWristExtendedPosition) + minSpeed;
    }

    @Override
    public void execute()
    {
        WristSystem.WristMotor.set(TalonFXControlMode.PercentOutput, CalculateWristSpeed(MinVelocity, MaxVelocity));
    }

    @Override
    public void end(boolean interrupted)
    {
        WristSystem.WristMotor.set(TalonFXControlMode.PercentOutput, 0);
    }
}
