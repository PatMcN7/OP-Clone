package frc.robot.subsystems.arm;

import org.littletonrobotics.junction.AutoLog;


public interface ArmIO {
    default void setPosition(double position) {}

    default void updateInputs(ArmIOInputs inputs) {}

    @AutoLog
    class ArmIOInputs {
        public double armPosition = .0;
        public double armVoltage = .0;
        public double armCurrent = .0;
        public boolean beamBreakTripped = false;
    }
}
