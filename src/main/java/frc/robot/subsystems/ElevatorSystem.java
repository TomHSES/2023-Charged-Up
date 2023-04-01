package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ElevatorSystem extends SubsystemBase
{
    public int CanID;

    public SparkMaxSystem ElevatorMotor;

    public DigitalInput ElevatorLimitSwitch;

    public double ElevatorRefEncoderPosition;

    public ElevatorSystem(int canID)
    {
        CanID = canID;
        ElevatorMotor = new SparkMaxSystem(canID, Math.PI * 2, Math.PI / 30, 0, 0, 0, 0, 0);
    }
    
    @Override 
    public void periodic()
    {
        SmartDashboard.putNumber(CanID + " Pos", ElevatorMotor.SparkMax.getEncoder().getPosition());
    }
}
