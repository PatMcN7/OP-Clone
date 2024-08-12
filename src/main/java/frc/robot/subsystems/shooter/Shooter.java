// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.shooter;

import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.inputs.LoggableInputs;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.shooter.ShooterIOInputsAutoLogged;

public class Shooter extends SubsystemBase {

  private final RobotContainer container;
  private final ShooterIO io;
  private final ShooterIOInputsAutoLogged inputs = new ShooterIOInputsAutoLogged();

  public enum WantedState {
    OFF,
    SCORING,
    FEEDING,
    EJECTING
  }

  public enum SystemState {
    IS_OFF,
    IS_SCORING,
    IS_FEEDING,
    IS_EJECTING
  }

  private WantedState wantedState = WantedState.SCORING;
  private SystemState systemState = SystemState.IS_SCORING;

  public Shooter(ShooterIO io, RobotContainer container) {
    this.io = io;
    this.container = container;
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("Shooter", inputs);
    SystemState newState = handleStateTransitions();
    if (newState != systemState) {
      Logger.recordOutput("Shooter/SystemState", newState.toString());
      systemState = newState;
    }

    if (DriverStation.isDisabled()) {
      systemState = SystemState.IS_OFF;
    }
    
    switch (systemState) {
    }

    Logger.recordOutput("Shooter/WantedState", wantedState);
  }

  
  private SystemState handleStateTransitions() {
    return switch (wantedState) {
        case SCORING -> SystemState.IS_SCORING;
        case FEEDING -> SystemState.IS_FEEDING;
        case EJECTING -> SystemState.IS_EJECTING;
        default -> SystemState.IS_OFF;
    };
}

}
