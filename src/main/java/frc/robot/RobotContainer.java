// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.AlignRobotWithLimelight;
import frc.robot.commands.CalibrateElevatorSystem;
import frc.robot.commands.CancelAllCommands;
import frc.robot.commands.ResetEncoder;
import frc.robot.commands.RotateFalconToPosition;
import frc.robot.commands.Elevator.CalibrateVerticalElevatorSystem;
import frc.robot.commands.Elevator.ManualElevatorSystem;
import frc.robot.commands.IntakeArm.ManualIntakeArm;
import frc.robot.constants.LaunchConstants;
import frc.robot.subsystems.TankDriveSystem;
import frc.robot.subsystems.SparkMaxSystem;
import frc.robot.subsystems.IntakeArmSystem;
import frc.robot.subsystems.ClawSystem;
import frc.robot.subsystems.ElevatorSystem;
import frc.robot.subsystems.GyroscopeSystem;
import frc.robot.subsystems.LimelightSystem;
import frc.robot.subsystems.VelocityFalconSystem;
import frc.robot.subsystems.VerticalElevatorSystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

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

    public ClawSystem DoubleSolenoidSystem;

    public VelocityFalconSystem Test_FalconSystem;

    public CommandJoystick Joystick;

    public CommandXboxController Controller;

    public LimelightSystem LimelightSystem;

    public ElevatorSystem VerticalElevator;

    public ElevatorSystem HorizontalElevator;

    public VerticalElevatorSystem VerticalElevatorSystem;

    public IntakeArmSystem IntakeArmSystem;

    public Compressor Compressor;

    public RobotContainer() 
    {
        InitialiseSystems();
        configureBindings();
        Finalise();
    }

    public void InitialiseSystems() 
    {
        DriveSystem = new TankDriveSystem(2, 4, 3, 1);
        DoubleSolenoidSystem = new ClawSystem(10, 0, 1);
        GyroscopeSystem = new GyroscopeSystem();
        GyroscopeSystem.CalibrateGyro(DriveSystem);
       // Test_FalconSystem = new VelocityFalconSystem(8, "Shooter");
       // LimelightSystem = new LimelightSystem();
        VerticalElevator = new ElevatorSystem(22);
        HorizontalElevator = new ElevatorSystem(23);
        IntakeArmSystem = new IntakeArmSystem(62);
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
            Joystick.button(7).whileTrue(new ManualElevatorSystem(HorizontalElevator, 0.05));
            Joystick.button(8).whileTrue(new ManualElevatorSystem(VerticalElevator, 1));

            Joystick.button(9).whileTrue(new ManualElevatorSystem(HorizontalElevator, -0.15));
            Joystick.button(10).whileTrue(new ManualElevatorSystem(VerticalElevator, -0.1));

            Joystick.button(11).whileTrue(new ManualIntakeArm(IntakeArmSystem, 0.15));
            Joystick.button(12).whileTrue(new ManualIntakeArm(IntakeArmSystem, -0.15));


            Joystick.button(5).onTrue(DoubleSolenoidSystem.SolenoidForward());
            Joystick.button(6).onTrue(DoubleSolenoidSystem.SolenoidReverse());
            Joystick.button(4).onTrue(DoubleSolenoidSystem.SolenoidOff());

          //  Joystick.button(11).whileTrue(AlignRobotWithLimelight.ScheduleCommand(LimelightSystem, DriveSystem,
                 //   GyroscopeSystem, 0.5, 0.025, 0.01, 0));
           // Joystick.button(10).whileTrue(CalibrateElevatorSystem.ScheduleCalibration(VerticalElevator));
           // Joystick.button(9).whileTrue(CalibrateElevatorSystem.ScheduleCalibration(HorizontalElevator));
          //  Joystick.button(12).whileTrue(new CalibrateVerticalElevatorSystem(VerticalElevatorSystem));
            Joystick.button(3).onTrue(new CancelAllCommands());
           // Joystick.button(4).onTrue(LimelightSystem.TogglePipeline());
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
