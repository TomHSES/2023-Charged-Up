package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClawSystem extends SubsystemBase 
{
    public DoubleSolenoid RightSolenoid;

    public DoubleSolenoid LeftSolenoid;

    public ClawSystem(int moduleCANID, int forwardChannelPort, int reverseChannelPort) 
    {
        //LeftSolenoid = new DoubleSolenoid(moduleCANID, PneumaticsModuleType.CTREPCM, 0, 1);
        RightSolenoid = new DoubleSolenoid(moduleCANID, PneumaticsModuleType.CTREPCM, 1, 2);
    }

    public CommandBase SolenoidOff() 
    {
        return runOnce(() -> 
        {
            //LeftSolenoid.set(Value.kOff);
            RightSolenoid.set(Value.kOff);
        });
    }

    public CommandBase SolenoidForward()
    {
        return runOnce(() -> 
        {
            //LeftSolenoid.set(Value.kForward);
            RightSolenoid.set(Value.kForward);
        });
    }

    public CommandBase SolenoidReverse()
    {
        return runOnce(() -> 
        {
            //LeftSolenoid.set(Value.kReverse);
            RightSolenoid.set(Value.kReverse);
        });
    }
}