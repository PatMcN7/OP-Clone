// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.arm;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicDutyCycle;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmIOTalonFX implements ArmIO {
 
  private final static int SLOT = 0;
  private final TalonFX armTalon;
  private final TalonFXConfiguration config;
  private final MotionMagicVoltage mRequest;

 

  /** Creates a new ArmIOTalonFX. */
  public ArmIOTalonFX() {
    armTalon = new TalonFX(Constants.ARM_CONSTANTS.MOTOR_ID, Constants.CANIVORE_NAME);
    config = new TalonFXConfiguration();
    mRequest = new MotionMagicVoltage(0);
    config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
    config.CurrentLimits.SupplyCurrentLimitEnable = true;
    config.CurrentLimits.SupplyCurrentLimit = 40;
    config.CurrentLimits.StatorCurrentLimitEnable = true;
    config.CurrentLimits.StatorCurrentLimit = 80;
    config.Voltage.PeakForwardVoltage = 12.0;
    config.Voltage.PeakReverseVoltage = -12.0;
    config.ClosedLoopRamps.VoltageClosedLoopRampPeriod = 0.02; 

    config.Slot0.kS = Constants.ARM_CONSTANTS.kS;
    config.Slot0.kV = Constants.ARM_CONSTANTS.kV;
    config.Slot0.kA = Constants.ARM_CONSTANTS.kA;
    config.Slot0.kP = Constants.ARM_CONSTANTS.kP;
    config.Slot0.kI = Constants.ARM_CONSTANTS.kI;
    config.Slot0.kD = Constants.ARM_CONSTANTS.kI;
    
    config.MotionMagic.MotionMagicAcceleration = Constants.ARM_CONSTANTS.MM_ACCEL;
    config.MotionMagic.MotionMagicCruiseVelocity = Constants.ARM_CONSTANTS.MM_CRUISE;
    config.MotionMagic.MotionMagicJerk = Constants.ARM_CONSTANTS.MM_JERK;
   
    armTalon.getConfigurator().apply(config);
    armTalon.setPosition(0.);
   
    
  }

  @Override 
  public void updateInputs(ArmIOInputs inputs){
    inputs.armCurrent = armTalon.getSupplyCurrent().getValueAsDouble();
    inputs.armPosition = armTalon.getPosition().getValueAsDouble();
    inputs.armPosition = armTalon.getMotorVoltage().getValueAsDouble();
  }

  @Override
  public void setPosition(double position){
    armTalon.setControl(mRequest.withPosition(position));
  }
}
