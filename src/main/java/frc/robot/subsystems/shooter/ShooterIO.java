package frc.robot.subsystems.shooter;

import org.littletonrobotics.junction.AutoLog;

public interface ShooterIO {

    default void setVoltage(double leftAppliedVoltage, double rightAppliedVoltage) {}
    
    default void setRPM(double leftTargetRPM, double rightTargetRPM) {}

    default void updateInputs(ShooterIOInputs inputs) {}

    @AutoLog
    class ShooterIOInputs{
        public double leftCurrent = 0.;
        public double rightCurrent = 0.;
        public double leftVoltage = 0.;
        public double rightVoltage = 0.;
        public double leftRPM = 0.;
        public double rightRPM = 0.;
        public double leftTemp = 0.;
        public double rightTemp = 0.;
    }
}