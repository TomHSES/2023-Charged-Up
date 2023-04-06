// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.Elevator.CalibrateVerticalElevatorSystem;
import frc.robot.commands.Elevator.ManualElevatorSystem;
import frc.robot.commands.Elevator.MoveHorizontalElevatorSystem;
import frc.robot.commands.Wrist.RotateWrist;
import frc.robot.commands.Wrist.RotateWrist_Uncontrolled;
import frc.robot.commands.Scoring;
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

    public CommandJoystick AltJoystick;

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
            // Joystick 0: Driver-preferred controls
            // Joystick 1: Manual controls in case the controls assigned to Joystick 0 breaks

            Joystick.button(9).whileTrue(new MoveHorizontalElevatorSystem(HorizontalElevator, -0.15));
            Joystick.button(10).whileTrue(new MoveHorizontalElevatorSystem(HorizontalElevator, 0.10));

            Joystick.button(7).whileTrue(new ManualElevatorSystem(VerticalElevator, 0.66));
            Joystick.button(8).whileTrue(new ManualElevatorSystem(VerticalElevator, -0.1));

            Joystick.button(12).onTrue(Scoring.ToggleScoringRoutine(true));

            Joystick.button(5).whileTrue(ClawSystem.SpinMotors(-0.5));
            Joystick.button(11).onTrue(ClawSystem.TogglePistons_Command());

            Joystick.button(6).whileTrue(new RotateWrist(WristSystem, -0.8, 0.5));
            Joystick.button(4).whileTrue(new RotateWrist(WristSystem, -0.5, 0.8));

            //-------------------------------------------------------------------------

            AltJoystick.button(7).onTrue(ClawSystem.DisablePistons_Command());
            AltJoystick.button(8).onTrue(ClawSystem.EnablePistons_Command());

            boolean testing = true;

            if (testing)
            {
                AltJoystick.button(9).onTrue(new RotateWrist_Uncontrolled(WristSystem, -0.8));
                AltJoystick.button(10).onTrue(new RotateWrist_Uncontrolled(WristSystem, 0.8));

                AltJoystick.button(3).whileTrue(new ManualElevatorSystem(VerticalElevator, 0.45));
                AltJoystick.button(4).whileTrue(new ManualElevatorSystem(VerticalElevator, 0.4));

                AltJoystick.button(5).whileTrue(new ManualElevatorSystem(VerticalElevator, 0.35));
                AltJoystick.button(6).whileTrue(new ManualElevatorSystem(VerticalElevator, 0.3));
            }
            else
            {
                AltJoystick.button(9).whileTrue(new ManualElevatorSystem(VerticalElevator, 0.66));
                AltJoystick.button(10).whileTrue(new ManualElevatorSystem(VerticalElevator, -0.1));

                AltJoystick.button(4).onTrue(new RotateWrist_Uncontrolled(WristSystem, -0.8));
                AltJoystick.button(6).onTrue(new RotateWrist_Uncontrolled(WristSystem, 0.8));
            }
        } 
        else 
        {
            Joystick.button(9).whileTrue(new MoveHorizontalElevatorSystem(HorizontalElevator, -0.15));
            Joystick.button(10).whileTrue(new MoveHorizontalElevatorSystem(HorizontalElevator, 0.10));

            Joystick.button(7).whileTrue(new ManualElevatorSystem(VerticalElevator, 0.66));
            Joystick.button(8).whileTrue(new ManualElevatorSystem(VerticalElevator, -0.1));

            Joystick.button(12).onTrue(Scoring.ToggleScoringRoutine(true));

            Joystick.button(5).whileTrue(ClawSystem.SpinMotors(-0.5));
            Joystick.button(11).onTrue(ClawSystem.TogglePistons_Command());

            Joystick.button(6).whileTrue(new RotateWrist(WristSystem, -0.8, 0.5));
            Joystick.button(4).whileTrue(new RotateWrist(WristSystem, -0.5, 0.8));
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
