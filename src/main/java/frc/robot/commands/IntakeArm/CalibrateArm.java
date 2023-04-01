/*package frc.robot.commands.IntakeArm;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.ElevatorConstants;
import frc.robot.subsystems.IntakeArmSystem;

public class CalibrateArm extends CommandBase 
{
    public IntakeArmSystem IntakeArm;

    public double CurrentSpeed;

    public double MaxSpeed = 0.5;

    private CalibrateArm(IntakeArmSystem intakeArmSystem) 
    {
        IntakeArm = intakeArmSystem;
        addRequirements(IntakeArm);
    }

    @Override
    public void execute()
    {
        IntakeArm.FalconMotor.set(ControlMode.PercentOutput, CurrentSpeed);

        if (Math.abs(Elevator.Encoder.getPosition() - OldEncoderValue) <= ElevatorConstants.kSparkMaxEncoderError
        || !Double.isNaN(ElevatorConstants.VerticalMotorInertia) && CurrentSpeed < ElevatorConstants.VerticalMotorInertia + 0.05)
        {
            CurrentSpeed = MathUtil.clamp(CurrentSpeed + 0.01, -MaxSpeed, MaxSpeed);
        }

        if (Double.isNaN(ElevatorConstants.VerticalMotorInertia) && OldEncoderValue != 0)
        {
            ElevatorConstants.VerticalMotorInertia = CurrentSpeed - 0.01;
        }

        OldEncoderValue = Elevator.Encoder.getPosition();
    }

    @Override
    public void end(boolean interrupted) 
    {
        ElevatorConstants.VerticalElevatorBottomPosition = -Elevator.Encoder.getPosition();
        Elevator.ElevatorMotor.set(0);
        Elevator.Encoder.setPosition(0);
        Elevator.ElevatorMotor.setIdleMode(IdleMode.kBrake);
    }

    @Override
    public boolean isFinished() 
    {
        return Elevator.ElevatorLimitSwitch.get();
    }
}
*/