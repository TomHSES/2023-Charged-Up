package frc.robot.commands.IntakeArm;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Utils.MathUtils;
import frc.robot.constants.ElevatorConstants;
import frc.robot.constants.IntakeConstants;
import frc.robot.subsystems.IntakeArmSystem;

public class RotateArm extends CommandBase 
{
    public IntakeArmSystem IntakeArm;

    public boolean HasInit;

    private RotateArm(IntakeArmSystem intakeArmSystem) 
    {
        IntakeArm = intakeArmSystem;
        HasInit = false;
        addRequirements(IntakeArm);
    }

    @Override
    public void execute()
    {
        if (!HasInit)
        {
            IntakeArm.ArmController.reset();
            IntakeArm.ArmController.setPID(IntakeConstants.kIntakeControllerP, IntakeConstants.kIntakeControllerI, IntakeConstants.kIntakeControllerD);
            IntakeArm.ArmController.setSetpoint(0);
            IntakeArm.ArmController.setTolerance(IntakeConstants.kIntakeControllerTolerance);
            HasInit = true;
            return;
        }

        double rawGain = IntakeArm.ArmController.calculate(IntakeArm.FalconMotor.getSensorCollection().getIntegratedSensorPosition());
        double clampedGain = MathUtils.Clamp(rawGain * IntakeConstants.kIntakeEncoderToDegreeConversionFactor, -IntakeConstants.kIntakeMaxSpeed, IntakeConstants.kIntakeMaxSpeed);
        IntakeArm.FalconMotor.set(TalonFXControlMode.PercentOutput, clampedGain);
    }

    @Override
    public void end(boolean interrupted) 
    {
        IntakeArm.FalconMotor.set(TalonFXControlMode.PercentOutput, 0);
    }

    @Override
    public boolean isFinished() 
    {
        return IntakeArm.ArmController.atSetpoint();
    }
}