// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.AlignRobotWithLimelight;
import frc.robot.commands.CancelAllCommands;
import frc.robot.commands.FalconSystemRev;
import frc.robot.commands.RotateFalconToPosition;
import frc.robot.commands.Test_AutoTurn;
import frc.robot.commands.Test_DescendIntake;
import frc.robot.subsystems.TankDriveSystem;
import frc.robot.subsystems.TestSparkMaxSystem;
import frc.robot.subsystems.FalconSystem;
import frc.robot.subsystems.DoubleSolenoidSystem;
import frc.robot.subsystems.GyroscopeSystem;
import frc.robot.subsystems.LimelightSystem;
import frc.robot.subsystems.VelocityFalconSystem;

import java.lang.ModuleLayer.Controller;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  public TankDriveSystem DriveSystem;

  public GyroscopeSystem GyroscopeSystem;

  public DoubleSolenoidSystem DoubleSolenoidSystem;

  public VelocityFalconSystem Test_FalconSystem;

  public CommandJoystick Joystick;

  public LimelightSystem LimelightSystem;

  public TestSparkMaxSystem TSMS;

  public FalconSystem TestFalcon;

  // Replace with CommandPS4Controller or CommandJoystick if needed
  /*private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);*/

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    InitialiseSystems();
    configureBindings();
    Finalise();
  }

  public void InitialiseSystems() {
    DriveSystem = new TankDriveSystem(2, 4, 3, 1);
    DoubleSolenoidSystem = new DoubleSolenoidSystem(10, 0, 1);
    GyroscopeSystem = new GyroscopeSystem();
    GyroscopeSystem.CalibrateGyroscope(DriveSystem);
    Test_FalconSystem = new VelocityFalconSystem(8, "Shooter");
    LimelightSystem = new LimelightSystem();
    //Test_SwerveSystem = new SwerveTestSystem(21, 23);
    //TSMS = new TestSparkMaxSystem(21);
    Joystick = new CommandJoystick(0);
   // TestFalcon = new FalconSystem(62);
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    /*new Trigger(m_exampleSubsystem::exampleCondition)
        .onTrue(new ExampleCommand(m_exampleSubsystem));*/

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    /*m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());*/
    //Joystick.button(5).onTrue(Test_FalconSystem.ZeroEncoder(50));
   // Joystick.button(6).whileTrue(new RotateFalconToPosition(Test_FalconSystem, 45000, 100, 1));//.whileFalse(new RotateFalconToPosition(Test_FalconSystem, 3000, 5000, 5));
  //  Joystick.button(6).onTrue(MPUSystem.Recalibrate());
    //Joystick.button(6).whileTrue(TSMS.Test(-0.33)).whileFalse(TSMS.Test(0d));
    //Joystick.button(6).whileTrue(TestFalcon.SetSpeed(0.33)).whileFalse(TestFalcon.SetSpeed(0.0));
   /* Joystick.button(7).onTrue(DoubleSolenoidSystem.SolenoidOff());
    Joystick.button(8).onTrue(DoubleSolenoidSystem.SolenoidForward());
    Joystick.button(9).onTrue(DoubleSolenoidSystem.SolenoidReverse());
    Joystick.button(10).onTrue(GyroscopeSystem.CalibrateGyroscope(DriveSystem));*/
    Joystick.button(11).whileTrue(AlignRobotWithLimelight.ScheduleCommand(LimelightSystem, DriveSystem, GyroscopeSystem, 0.5, 0.025, 0.01, 0));
    Joystick.button(3).onTrue(new CancelAllCommands());
    Joystick.button(4).onTrue(LimelightSystem.TogglePipeline());



    //Joystick.button(3).whileTrue(Test_SwerveSystem.TestDrive(Joystick)).whileFalse(Test_SwerveSystem.TestStop());
  }

  public void Finalise() 
  {
    //Test_SwerveSystem.setDefaultCommand(Test_SwerveSystem.TestDrive(Joystick));
    DriveSystem.setDefaultCommand(DriveSystem.TwistDrive(Joystick));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return null; //Autos.exampleAuto(m_exampleSubsystem);
  }
}
