// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.shooter.ShooterSubsystem;

public class Superstructure extends SubsystemBase {
  private ShooterSubsystem shooter;

  public enum WantedSuperState {}
  public enum CurrentSuperState {}

  /** Creates a new Superstructure. */
  public Superstructure() {}  // This is going to be a very large class that I plan to write after finishing every subsystem. 
                              // I suppose you could write the superstates first but I'm not sure what I want them to look like yet

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
} 
