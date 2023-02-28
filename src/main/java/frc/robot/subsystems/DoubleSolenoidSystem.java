package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DoubleSolenoidSystem extends SubsystemBase {

    public DoubleSolenoid DoubleSolenoid;

    public DoubleSolenoidSystem(int moduleCANID, int forwardChannelPort, int reverseChannelPort) {
        DoubleSolenoid = new DoubleSolenoid(moduleCANID, 
        PneumaticsModuleType.CTREPCM, 
        forwardChannelPort,
        reverseChannelPort);
    }

    public CommandBase SolenoidOff() {
        return runOnce(
                () -> {
                    DoubleSolenoid.set(Value.kOff);
                });
    }

    public CommandBase SolenoidForward() {
        return runOnce(
                () -> {
                    DoubleSolenoid.set(Value.kForward);
                });
    }

    public CommandBase SolenoidReverse() {
        return runOnce(
                () -> {
                    DoubleSolenoid.set(Value.kReverse);
                });
    }
}