// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.CANDriveSubsystem;
import frc.robot.subsystems.CANFuelSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class LeftBespoke extends SequentialCommandGroup {
  /** Creates a new ExampleAuto. */
  public LeftBespoke(CANDriveSubsystem driveSubsystem, CANFuelSubsystem ballSubsystem) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new AutoDrive(driveSubsystem,0.5,  0.0).withTimeout(.9), //back it up
      new SpinUp(ballSubsystem).withTimeout(1),
      new Launch(ballSubsystem).withTimeout(3), //shoot the load
      new AutoDrive(driveSubsystem,-0.5,  0.0).withTimeout(.1), //shimmy shimmy
      new AutoDrive(driveSubsystem,0.5,  0.0).withTimeout(.1),
      new AutoDrive(driveSubsystem,-0.5,  0.0).withTimeout(.1),
      new Launch(ballSubsystem).withTimeout(3), //continue shooting
      new AutoDrive(driveSubsystem, 0, .5).withTimeout(.5), //turn it around
      new ParallelDeadlineGroup(
        new Intake(ballSubsystem).withTimeout(3), //lawn mower
        new AutoDrive(driveSubsystem, .5, 0)), //move to the middle
      new AutoDrive(driveSubsystem, 0, -.5).withTimeout(.9),
      new ParallelDeadlineGroup(
        new Intake(ballSubsystem).withTimeout(3), //lawn mower
        new AutoDrive(driveSubsystem, .5, 0)) //drive to the middle
    );
  }
}
