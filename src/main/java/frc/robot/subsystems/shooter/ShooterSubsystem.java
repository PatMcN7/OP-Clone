// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.shooter;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class ShooterSubsystem extends SubsystemBase {

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

  private WantedState wantedState = WantedState.OFF;
  private SystemState systemState = SystemState.IS_OFF;

  public ShooterSubsystem(ShooterIO io, RobotContainer container) {
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
      case IS_OFF:
        handleIsOff();
        break;
      case IS_SCORING:
        handleIsScoring();
        break;
      case IS_FEEDING:
        handleIsFeeding();
        break;
      case IS_EJECTING:
        handleEjecting();
        break;

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

  private void handleIsOff() {
    io.setVoltage(Constants.SHOOTER_CONSTANTS.OFF_VOLTAGE, Constants.SHOOTER_CONSTANTS.OFF_VOLTAGE);
  }

  private void handleIsScoring() {
    io.setRPM(Constants.SHOOTER_CONSTANTS.LEFT_SCORE_RPM, Constants.SHOOTER_CONSTANTS.RIGHT_SCORE_RPM);
  }

  private void handleIsFeeding() {
    io.setRPM(Constants.SHOOTER_CONSTANTS.LEFT_FEED_RPM, Constants.SHOOTER_CONSTANTS.RIGHT_FEED_RPM);
  }

  private void handleEjecting() {
    io.setRPM(Constants.SHOOTER_CONSTANTS.LEFT_EJECT_RPM, Constants.SHOOTER_CONSTANTS.RIGHT_EJECT_RPM);
  }

  public boolean atScoreSetpoint() {
    return MathUtil.isNear(Constants.SHOOTER_CONSTANTS.LEFT_SCORE_RPM, inputs.leftRPM, Constants.SHOOTER_CONSTANTS.ACCEPTABLE_RPM_ERROR) 
    && MathUtil.isNear(Constants.SHOOTER_CONSTANTS.RIGHT_SCORE_RPM, inputs.rightRPM, Constants.SHOOTER_CONSTANTS.ACCEPTABLE_RPM_ERROR);
  }

  public boolean atFeedSetpoint() {
    return MathUtil.isNear(Constants.SHOOTER_CONSTANTS.LEFT_FEED_RPM, inputs.leftRPM, Constants.SHOOTER_CONSTANTS.ACCEPTABLE_RPM_ERROR)
    && MathUtil.isNear(Constants.SHOOTER_CONSTANTS.RIGHT_FEED_RPM, inputs.rightRPM, Constants.SHOOTER_CONSTANTS.ACCEPTABLE_RPM_ERROR);
  }

  public void setWantedState(WantedState wantedState) {
    this.wantedState = wantedState;
  }
}
