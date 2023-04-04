package frc.robot.commands.Wrist;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Utils.MathUtils;
import frc.robot.constants.WristConstants;
import frc.robot.subsystems.WristSystem;

public class RotateWristAuto extends CommandBase
{
    public boolean HasInit;

    public WristSystem WristSystem;

    public PIDController WristController;

    public RotateWristAuto(WristSystem wristSystem, int desiredPosition, int tolerance)
    {
        HasInit = false;
        WristSystem = wristSystem;
        WristController = new PIDController(WristConstants.kWristControllerP, WristConstants.kWristControllerI, WristConstants.kWristControllerD);
        WristController.setSetpoint(desiredPosition);
        WristController.setTolerance(tolerance);
        addRequirements(WristSystem);
    }

    @Override
    public void execute()
    {
        if (!HasInit)
        {
            WristSystem.TogglePneumaticBrake(false);
            HasInit = true;
            return;
        }

        double rawGain = WristController.calculate(WristSystem.WristMotor.getSensorCollection().getIntegratedSensorPosition());
        double clampedGain = MathUtils.Clamp(rawGain * WristConstants.kWristEncoderToDegreeConversionFactor, -WristConstants.kWristMaxSpeed, WristConstants.kWristMaxSpeed);
        WristSystem.WristMotor.set(TalonFXControlMode.PercentOutput, clampedGain);
    }

    @Override
    public void end(boolean interrupted)
    {
        WristSystem.TogglePneumaticBrake(true);
    }

    @Override
    public boolean isFinished()
    {
        return HasInit && WristController.atSetpoint();
    }
}
