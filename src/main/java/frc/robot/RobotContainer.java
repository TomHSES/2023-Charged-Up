// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.Elevator.CalibrateVerticalElevatorSystem;
import frc.robot.commands.Elevator.ManualElevatorSystem;
import frc.robot.commands.Elevator.MoveHorizontalElevatorSystem;
import frc.robot.commands.Wrist.RotateWrist;
import frc.robot.commands.Scoring;
import frc.robot.constants.ElevatorConstants;
import frc.robot.constants.IDs;
import frc.robot.constants.LaunchConstants;
import frc.robot.subsystems.TankDriveSystem;
import frc.robot.subsystems.WristSystem;
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

    public CommandXboxController Controller;

    public LimelightSystem LimelightSystem;

    public VerticalElevatorSystem VerticalElevator;

    public HorizontalElevatorSystem HorizontalElevator;

    public WristSystem WristSystem;

    public ClawSystem ClawSystem;

    public Scoring Scoring;

    public RobotContainer() 
    {
        InitialiseSystems();
        configureBindings();
        Finalise();
    }

    public void InitialiseSystems() 
    {
        DriveSystem = new TankDriveSystem();
        DriveSystem.LeftEncoder.reset();
        DriveSystem.RightEncoder.reset();
        GyroscopeSystem = new GyroscopeSystem();
        GyroscopeSystem.CalibrateGyro(DriveSystem);
        ClawSystem = new ClawSystem();
        VerticalElevator = new VerticalElevatorSystem();
        VerticalElevator.ElevatorMotor.getSensorCollection().setIntegratedSensorPosition(0, 30);
        HorizontalElevator = new HorizontalElevatorSystem();
        HorizontalElevator.Encoder.setPosition(0);
        WristSystem = new WristSystem();
        WristSystem.WristMotor.getSensorCollection().setIntegratedSensorPosition(0, 30);
        Scoring = new Scoring(this);

        if (LaunchConstants.Controller) 
        {
            Controller = new CommandXboxController(IDs.Port_Controller);
        }
        else
        {
            Joystick = new CommandJoystick(IDs.Port_Joystick);
        }

        if (LaunchConstants.Limelight)
        {
            LimelightSystem = new LimelightSystem();
        }
    }

    private void configureBindings()
    {
        // Button 8: Handle aligning cones on node
        // All driver should do is to intake game piece and drive to node
        // Claw motors will only spin for shooting to upper nodes/platforms

        // Button 7: Handling scoring cubes on platform

        // Button 12: Intaking from double substation

        // Button 11: Open/close claw

        // Button 6: Wrist down

        // Button 4: Wrist up

        // Button 5: Rotate claw wheels


        // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
        /*
         * new Trigger(m_exampleSubsystem::exampleCondition)
         * .onTrue(new ExampleCommand(m_exampleSubsystem));
        */

        if (LaunchConstants.Controller) 
        {
           // Controller.button(1).whileTrue(CalibrateElevatorSystem.ScheduleCalibration(VerticalElevator));
        }
        else if (LaunchConstants.CompetitionControls)
        {
            Joystick.button(9).whileTrue(new MoveHorizontalElevatorSystem(HorizontalElevator, -0.15));
            Joystick.button(10).whileTrue(new MoveHorizontalElevatorSystem(HorizontalElevator, 0.10));

            Joystick.button(7).whileTrue(new ManualElevatorSystem(VerticalElevator, 0.66));
            Joystick.button(8).whileTrue(new ManualElevatorSystem(VerticalElevator, -0.1));

            Joystick.button(12).onTrue(Scoring.ToggleScoringRoutine(true));

            Joystick.button(5).whileTrue(ClawSystem.SpinMotors(-0.5));
            Joystick.button(11).onTrue(ClawSystem.TogglePistons_Command());

            Joystick.button(6).whileTrue(new RotateWrist(WristSystem, -1));
            Joystick.button(4).whileTrue(new RotateWrist(WristSystem, 1));
        } 
        else 
        {
            Joystick.button(7).whileTrue(new MoveHorizontalElevatorSystem(HorizontalElevator, -0.15));
            Joystick.button(8).whileTrue(new MoveHorizontalElevatorSystem(HorizontalElevator, 0.15));

            Joystick.button(3).whileTrue(new ManualElevatorSystem(VerticalElevator, 0.66));
            Joystick.button(4).whileTrue(new CalibrateVerticalElevatorSystem(VerticalElevator));

            Joystick.button(5).whileTrue(ClawSystem.SpinMotors(-0.33));
            Joystick.button(6).onTrue(ClawSystem.TogglePistons_Command());

            // Joystick.button(11).whileTrue(ClawSystem.ToggleClaw(Value.kForward));
            // Joystick.button(12).whileTrue(ClawSystem.ToggleClaw(Value.kReverse));

            Joystick.button(11).whileTrue(new RotateWrist(WristSystem, -0.5));
            Joystick.button(12).whileTrue(new RotateWrist(WristSystem, 0.5));
            Joystick.button(9).onTrue(WristSystem.DisableBrakes_Command());
            Joystick.button(10).onTrue(WristSystem.EnableBrakes_Command());

            // Joystick.button(9).whileTrue(new
            // RotateVerticalElevatorSystem(VerticalElevator, 0));
            // Joystick.button(10).whileTrue(new
            // RotateVerticalElevatorSystem(VerticalElevator,
            // ElevatorConstants.VerticalElevatorTopPosition));
        }
    }

    public void Finalise() 
    {
        DriveSystem.setDefaultCommand(DriveSystem.DefaultDrive(Joystick, Controller));
    }

    public Command getAutonomousCommand() 
    {
        return null; // Autos.exampleAuto(m_exampleSubsystem);
    }
}
