// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.arm;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.hardware.TalonFX;
import com.google.flatbuffers.Constants;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmIOTalonFX implements ArmIO {

  private final static int SLOT = 0;
  private final TalonFX armTalon;
  private final MotionMagicConfigs motionMagicConfigs;


  /** Creates a new ArmIOTalonFX. */
  public ArmIOTalonFX() {
    armTalon = new TalonFX(Constants.ARM_CONSTANTS.MOTOR_ID, Constants.CANIVORE_NAME);
  }

}
