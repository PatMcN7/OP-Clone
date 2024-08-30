// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.arm;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicDutyCycle;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmIOTalonFX implements ArmIO {

  private final static int SLOT = 0;
  private final TalonFX armTalon;
  private final TalonFXConfiguration config;
  private final MotionMagicConfigs motionMagicConfigs;

  private final MotionMagicDutyCycle armMotionProfile;

  /** Creates a new ArmIOTalonFX. */
  public ArmIOTalonFX() {
    armTalon = new TalonFX(Constants.ARM_CONSTANTS.MOTOR_ID, Constants.CANIVORE_NAME);
    config = new TalonFXConfiguration();
    config.MotorOutput.NeutralMode = NeutralModeValue.Coast;
    config.CurrentLimits.SupplyCurrentLimitEnable = true;
    config.CurrentLimits.SupplyCurrentLimit = 40;
    config.CurrentLimits.StatorCurrentLimitEnable = true;
    config.CurrentLimits.StatorCurrentLimit = 80;
    config.Voltage.PeakForwardVoltage = 12.0;
    config.Voltage.PeakReverseVoltage = -12.0;
    config.ClosedLoopRamps.VoltageClosedLoopRampPeriod = 0.02; 
    
    
  }

}
