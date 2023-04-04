package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
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
    public final TalonFX ElevatorMotor;

    public DigitalInput ElevatorLimitSwitch;

    public PIDController ElevatorController;

    public double ElevatorRefEncoderPosition;

    public VerticalElevatorSystem(int canID)
    {
        ElevatorMotor = new TalonFX(canID);
        ElevatorController = new PIDController(ElevatorConstants.kVerticalElevatorControllerP, ElevatorConstants.kVerticalElevatorControllerI, ElevatorConstants.kVerticalElevatorControllerD);
        ElevatorLimitSwitch = new DigitalInput(0);
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
            SmartDashboard.putNumber("VE Inertia", ElevatorConstants.VerticalMotorInertia);
            SmartDashboard.putNumber("VE Encoder Position", GetEncoderPosition());
        }
    }
}
