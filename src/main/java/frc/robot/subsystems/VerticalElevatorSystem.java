package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.Elevator.IdleVerticalElevatorSystem;
import frc.robot.constants.ElevatorConstants;
import frc.robot.constants.IDs;
import frc.robot.constants.LaunchConstants;

public class VerticalElevatorSystem extends SubsystemBase
{
    public TalonFX ElevatorMotor;

    public DigitalInput ElevatorLimitSwitch;

    public PIDController ElevatorController;

    public double ElevatorRefEncoderPosition;

    public VerticalElevatorSystem()
    {
        ElevatorMotor = new TalonFX(IDs.ElevatorMotor_Vertical);
        ElevatorMotor.configFactoryDefault(50);
        ElevatorController = new PIDController(ElevatorConstants.kVerticalElevatorControllerP, ElevatorConstants.kVerticalElevatorControllerI, ElevatorConstants.kVerticalElevatorControllerD);
        ElevatorLimitSwitch = new DigitalInput(IDs.LimitSwitch_Elevator);
        setDefaultCommand(new IdleVerticalElevatorSystem(this));
    }

    public CommandBase RunMotor(double velocity)
    {
        return runEnd(() ->
        {
            ElevatorMotor.set(TalonFXControlMode.PercentOutput, velocity);
        }, () ->
        {
            ElevatorMotor.set(TalonFXControlMode.PercentOutput, 0);
        });
    }

    public double GetEncoderPosition()
    {
        return ElevatorMotor.getSensorCollection().getIntegratedSensorPosition();
    }

    @Override
    public void periodic()
    {
        if (LaunchConstants.Log_ElevatorSystems)
        {
            SmartDashboard.putNumber("V.E. Inertia", ElevatorConstants.VerticalMotorInertia);
            SmartDashboard.putNumber("V.E. Enc. Pos.", GetEncoderPosition());
            SmartDashboard.putBoolean("V.E. Switch", ElevatorLimitSwitch.get());
        }
    }
}
