// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.Elevator.CalibrateVerticalElevatorSystem;
import frc.robot.commands.Elevator.ManualElevatorSystem;
import frc.robot.commands.Elevator.MoveHorizontalElevatorSystem;
import frc.robot.commands.Elevator.RotateVerticalElevatorSystem;
import frc.robot.commands.Wrist.RotateWrist;
import frc.robot.constants.ElevatorConstants;
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
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

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

    public Compressor Compressor;

    public ClawSystem ClawSystem;

    public RobotContainer() 
    {
        InitialiseSystems();
        configureBindings();
        Finalise();
    }

    public void InitialiseSystems() 
    {
        DriveSystem = new TankDriveSystem(2, 4, 3, 1, 9, 8);
        ClawSystem = new ClawSystem(24, 25, 10, 1, 2);
        GyroscopeSystem = new GyroscopeSystem();
        GyroscopeSystem.CalibrateGyro(DriveSystem);
       // Test_FalconSystem = new VelocityFalconSystem(8, "Shooter");
       // LimelightSystem = new LimelightSystem();
        VerticalElevator = new VerticalElevatorSystem(22);
        HorizontalElevator = new HorizontalElevatorSystem(23);
        WristSystem = new WristSystem(62, 3, 4);
        //Compressor = new Compressor(PneumaticsModuleType.CTREPCM);
        //Compressor.enableDigital();

        if (LaunchConstants.Controller) 
        {
            Controller = new CommandXboxController(0);
        }
        else
        {
            Joystick = new CommandJoystick(0);
        }
    }

    public boolean Brake = false;

    /**
     * Use this method to define your trigger->command mappings. Triggers can be
     * created via the
     * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with
     * an arbitrary
     * predicate, or via the named factories in {@link
     * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
     * {@link
     * CommandXboxController
     * Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
     * PS4} controllers or
     * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
     * joysticks}.
     */
    private void configureBindings()
    {
        // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
        /*
         * new Trigger(m_exampleSubsystem::exampleCondition)
         * .onTrue(new ExampleCommand(m_exampleSubsystem));
        */

        if (LaunchConstants.Controller) 
        {
           // Controller.button(1).whileTrue(CalibrateElevatorSystem.ScheduleCalibration(VerticalElevator));
        }
        else 
        {
            Joystick.button(7).whileTrue(new MoveHorizontalElevatorSystem(HorizontalElevator, -0.15));
            Joystick.button(8).whileTrue(new MoveHorizontalElevatorSystem(HorizontalElevator, 0.15));

            Joystick.button(3).whileTrue(new ManualElevatorSystem(VerticalElevator, Joystick));
            Joystick.button(4).whileTrue(new CalibrateVerticalElevatorSystem(VerticalElevator));

            Joystick.button(5).whileTrue(ClawSystem.SpinMotors());
            Joystick.button(11).whileTrue(ClawSystem.ToggleClaw(Value.kForward));
            Joystick.button(12).whileTrue(ClawSystem.ToggleClaw(Value.kReverse));

            Joystick.button(9).whileTrue(new RotateWrist(WristSystem, 0.15));
            Joystick.button(10).onTrue(WristSystem.TogglePneumaticBrake_Command());//new RotateWrist(WristSystem, -0.15));

            //Joystick.button(9).whileTrue(new RotateVerticalElevatorSystem(VerticalElevator, 0));
            //Joystick.button(10).whileTrue(new RotateVerticalElevatorSystem(VerticalElevator, ElevatorConstants.VerticalElevatorTopPosition));
        }
    }

    public void Finalise() 
    {
       // DriveSystem.setDefaultCommand(DriveSystem.DefaultDrive(Joystick, Controller));
    }

    public Command getAutonomousCommand() 
    {
        return null; // Autos.exampleAuto(m_exampleSubsystem);
    }
}
