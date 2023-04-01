package frc.robot.commands.Autonomous;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.AutonomousConstants;
import frc.robot.subsystems.GyroscopeSystem;
import frc.robot.subsystems.TankDriveSystem;

public class Auto_Initialisation extends CommandBase
{
    public boolean HasInit;

    public Timer InitialisationTimer;

    public GyroscopeSystem GyroscopeSystem;

    public TankDriveSystem TankDriveSystem;

    public Auto_Initialisation(GyroscopeSystem gyroscopeSystem, TankDriveSystem tankDriveSystem)
    {
        HasInit = false;
        InitialisationTimer = new Timer();
        GyroscopeSystem = gyroscopeSystem;
        TankDriveSystem = tankDriveSystem;
    }

    @Override
    public void execute()
    {
        if (!HasInit)
        {
            InitialisationTimer.reset();
            InitialisationTimer.start();

            GyroscopeSystem.CalibrateGyro(TankDriveSystem);

            AutonomousConstants.StartingPosition = (int)SmartDashboard.getNumber("Starting Position", -1);

            HasInit = true;
            return;
        }
    }

    @Override
    public void end(boolean interrupted)
    {
        TankDriveSystem.StopDrive();
    }

    @Override
    public boolean isFinished()
    {
        return HasInit && InitialisationTimer.hasElapsed(1);
    }
}