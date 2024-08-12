package frc.robot.subsystems.shooter;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;
import frc.robot.Constants;
import frc.robot.Robot;

// 1.475z   
public class ShooterIOSim implements ShooterIO {
    private final FlywheelSim leftFlywheel = new FlywheelSim(DCMotor.getKrakenX60(1), 0.68, 0.0002); // connor needs to check
                                                                                                    
    private final FlywheelSim rightFlywheel = new FlywheelSim(DCMotor.getKrakenX60(1), 0.68, 0.0002);

    private final PIDController leftFeedback = new PIDController(1.5, 0, 0);
    private final PIDController rightFeedback = new PIDController(1.5, 0, 0);
    private double leftAppliedVolts = 0.0;
    private double rightAppliedVolts = 0.0;
    
    @Override
    public void updateInputs(ShooterIOInputs inputs){
        leftFlywheel.update(Robot.defaultPeriodSecs);
        rightFlywheel.update(Robot.defaultPeriodSecs);

        inputs.leftVoltage = leftAppliedVolts;
        inputs.rightVoltage = rightAppliedVolts;
        inputs.leftRPM = leftFlywheel.getAngularVelocityRPM();
        inputs.rightRPM = rightFlywheel.getAngularVelocityRPM();
    }

    @Override
    public void setVoltage(double leftAppliedVolts, double rightAppliedVolts){
        this.leftAppliedVolts = leftAppliedVolts;
        this.rightAppliedVolts = rightAppliedVolts;

        leftFlywheel.setInputVoltage(leftAppliedVolts);
        rightFlywheel.setInputVoltage(rightAppliedVolts);
    }

    @Override
    public void setRPM(double leftTargetRPM, double rightTargetRPM){
        double leftVelocityFromRPM = leftTargetRPM * Constants.SHOOTER_CONSTANTS.ROLLER_RADIUS_METERS * 2 * Math.PI / 60; // RPM * Circumference = Meters per minute. / 60 = meters per second
        leftAppliedVolts = leftFeedback.calculate(leftFlywheel.getAngularVelocityRPM(), leftVelocityFromRPM); // pid control to get voltage to command
        leftAppliedVolts = MathUtil.clamp(leftTargetRPM, -12., 12.); // ensuring that output to motor is never more/less than +-12 volts, despite what pid controller might output
        leftFlywheel.setInputVoltage(leftAppliedVolts);

        double rightVelocityFromRPM = rightTargetRPM * Constants.SHOOTER_CONSTANTS.ROLLER_RADIUS_METERS * 2 * Math.PI / 60;
        rightAppliedVolts = rightFeedback.calculate(rightFlywheel.getAngularVelocityRPM(), rightVelocityFromRPM); // pid control to
        rightAppliedVolts = MathUtil.clamp(rightTargetRPM, -12., 12.); // ensuring that output to motor is never more/
        rightFlywheel.setInputVoltage(rightAppliedVolts);
    }

}
