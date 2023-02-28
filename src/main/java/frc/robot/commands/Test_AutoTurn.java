// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.TankDriveSystem;
import frc.robot.subsystems.GyroscopeSystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Test_AutoTurn extends CommandBase {
    public final TankDriveSystem DriveSystem;

    public final GyroscopeSystem GyroscopeSystem;

    public double InitialAngle;

    public Test_AutoTurn(TankDriveSystem driveSystem, GyroscopeSystem gyroSystem) {
        DriveSystem = driveSystem;
        GyroscopeSystem = gyroSystem;
        addRequirements(driveSystem);
        addRequirements(gyroSystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        InitialAngle = GyroscopeSystem.Gyroscope.getAngle();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        DriveSystem.ArcadeDrive(0.25, 2);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        DriveSystem.StopDrive();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return GyroscopeSystem.Gyroscope.getAngle() >= InitialAngle + 180;
    }
}
