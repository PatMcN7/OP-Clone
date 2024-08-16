package frc.robot.subsystems.arm;

import org.littletonrobotics.junction.AutoLog;


public interface ArmIO {
    

    default void setVoltage(double voltage) {}

    default void setPosition(double position) {}

    default void updateInputs(ArmIOInputs inputs) {}

    @AutoLog
    class ArmIOInputs {
        public double armPosition;
        public double armVoltage;
        public double armCurrent;
    }
}
