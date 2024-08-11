package frc.robot.subsystems.shooter;

import org.littletonrobotics.junction.AutoLog;

public interface ShooterIO {

    default void setVoltage(double leftAppliedVoltage, double rightAppliedVoltage) {}
    
    default void setRPM(double leftTargetRPM, double rightTargetRPM) {}

    default void updateInputs(ShooterIOInputs inputs) {}

    @AutoLog
    class ShooterIOInputs{
        public double leftCurrent;
        public double rightCurrent;
        public double leftVoltage;
        public double rightVoltage;
        public double leftRPM;
        public double rightRPM;
        public double leftTemp;
        public double rightTemp;
    }
}