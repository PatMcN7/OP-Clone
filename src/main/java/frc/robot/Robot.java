// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import org.littletonrobotics.junction.LogFileUtil;
import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.NT4Publisher;
import org.littletonrobotics.junction.wpilog.WPILOGReader;
import org.littletonrobotics.junction.wpilog.WPILOGWriter;

import com.ctre.phoenix6.SignalLogger;

import edu.wpi.first.hal.HALUtil;
import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends LoggedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;
  private final LinearFilter average = LinearFilter.movingAverage(50); // Moving Average for loop cycles (ripped from 2910)
                                                                            // Admittedly I do not understand why the value 50 was chosen
  @Override
  public void robotInit() {
    Logger.recordMetadata("ProjectName", "MyProject"); // Set a metadata value

    switch (Constants.CURRENT_MODE) {
      case REAL:
        // Running on a real robot, log to a USB stick ("/U/logs")
        Logger.addDataReceiver(new WPILOGWriter());
        Logger.addDataReceiver(new NT4Publisher());
        new PowerDistribution(Constants.PDH_PORT, ModuleType.kRev); // Enables power distribution logging. Need to make
                                                                    // sure this works.
        break;

      case SIM:
        // Running a physics simulator, log to Network Tables
        Logger.addDataReceiver(new NT4Publisher());
        break;

      case REPLAY:
        // Replaying a log, set up replay source
        setUseTiming(false); // Run as fast as possible
        String logPath = LogFileUtil.findReplayLog();
        Logger.setReplaySource(new WPILOGReader(logPath));
        Logger.addDataReceiver(new WPILOGWriter(LogFileUtil.addPathSuffix(logPath, "_sim")));
        break;
    }

    // See http://bit.ly/3YIzFZ6 for more information on timestamps in AdvantageKit.
    // Logger.disableDeterministicTimestamps()

    SignalLogger.enableAutoLogging(true); // Auto CTRE device logging. Kind of redundant because of akit
                                          // Can disable if using too much CPU or memory
                                          // Should only log when connected to FMS. Nice backup ig.

    // Start AdvantageKit logger
    Logger.start();

    m_robotContainer = new RobotContainer();
  }

  @Override
  public void robotPeriodic() {
    double start = HALUtil.getFPGATime(); // All this logic calculates the length of our loop cycles and average cycles
    CommandScheduler.getInstance().run(); 
    double end = HALUtil.getFPGATime(); 
    double commandSchedulerTime = (end - start) / 1e3; // Dividing by 1000 to convert time difference from microseconds to ms 
    Logger.recordOutput("Times/CommandSchedulerMs", commandSchedulerTime);
    Logger.recordOutput("Times/CommandSchedulerMsAverage", average.calculate(commandSchedulerTime));
    // This should be fairly accurate
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void disabledExit() {
  }

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void autonomousExit() {
  }

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void teleopExit() {
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {
  }

  @Override
  public void testExit() {
  }
}
