// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.Elevator.LiftVerticalElevator;
import frc.robot.commands.Elevator.ManualVElevatorSystem;
import frc.robot.commands.Elevator.ToggleHorizontalElevator;
import frc.robot.commands.Wrist.ManualWristSystem;
import frc.robot.commands.Wrist.ManualWristSystem_Timed;
import frc.robot.commands.Elevator.ManualHElevatorSystem;
import frc.robot.commands.Scoring;
import frc.robot.commands.Autonomous.AutonomousRoutine;
import frc.robot.commands.Autonomous.Auto_TimeDrive;
import frc.robot.constants.ElevatorConstants;
import frc.robot.constants.IDs;
import frc.robot.constants.LaunchConstants;
import frc.robot.subsystems.TankDriveSystem;
import frc.robot.subsystems.WristSystemTwo;
import frc.robot.subsystems.ClawSystem;
import frc.robot.subsystems.HorizontalElevatorSystem;
import frc.robot.subsystems.GyroscopeSystem;
import frc.robot.subsystems.LimelightSystem;
import frc.robot.subsystems.VerticalElevatorSystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer 
{
    public TankDriveSystem DriveSystem;

    public GyroscopeSystem GyroscopeSystem;

    public CommandJoystick Joystick;

    public CommandJoystick AltJoystick;

    public CommandXboxController Controller;

    public LimelightSystem LimelightSystem;

    public VerticalElevatorSystem VerticalElevator;

    public HorizontalElevatorSystem HorizontalElevator;

    public WristSystemTwo WristSystem;

    public ClawSystem ClawSystem;

    public Scoring Scoring;

    public AutonomousRoutine Auto;

    public RobotContainer() 
    {
        InitialiseSystems();
        configureBindings();
        Finalise();
    }

    public void InitialiseSystems() 
    {
        DriveSystem = new TankDriveSystem();
        GyroscopeSystem = new GyroscopeSystem();
        GyroscopeSystem.CalibrateGyro(DriveSystem);
        ClawSystem = new ClawSystem();
        VerticalElevator = new VerticalElevatorSystem();
        HorizontalElevator = new HorizontalElevatorSystem();
        WristSystem = new WristSystemTwo();
        Scoring = new Scoring(this);
        Auto = new AutonomousRoutine(this);
        if (LaunchConstants.Controller) 
        {
            Controller = new CommandXboxController(IDs.Port_Controller);
        }
        else
        {
            Joystick = new CommandJoystick(IDs.Port_Joystick);

            if (LaunchConstants.CompetitionControls)
            {
                AltJoystick = new CommandJoystick(IDs.Port_AltJoystick);
            }
        }

        if (LaunchConstants.Limelight)
        {
            LimelightSystem = new LimelightSystem();
        }
    }

    private void configureBindings()
    {
        //Joystick.button(2).onTrue(new ManualWristSystem_Timed(WristSystem, 0.8, -0.2, 0.75));

        Joystick.button(6).whileTrue(new ManualWristSystem(WristSystem, -0.33));
        Joystick.button(4).whileTrue(new ManualWristSystem(WristSystem, 0.33));

        Joystick.button(7).whileTrue(new ManualVElevatorSystem(VerticalElevator, 1));
        Joystick.button(8).whileTrue(new ManualVElevatorSystem(VerticalElevator, -1));

        Joystick.button(9).whileTrue(new ToggleHorizontalElevator(HorizontalElevator, 2));
        Joystick.button(10).whileTrue(new ToggleHorizontalElevator(HorizontalElevator, -3));

        Joystick.button(5).whileTrue(ClawSystem.SpinMotors(0.2));
        Joystick.button(3).whileTrue(ClawSystem.SpinMotors(-0.15));

        Joystick.button(11).onTrue(ClawSystem.TogglePistons_Command());
    }

    public void Finalise() 
    {
        DriveSystem.setDefaultCommand(DriveSystem.DefaultDrive(Joystick, Controller));
    }

    public Command getAutonomousCommand() 
    {
        return new Auto_TimeDrive(DriveSystem, -0.66, 2);
    }
}
