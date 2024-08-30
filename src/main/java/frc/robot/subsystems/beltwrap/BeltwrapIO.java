package frc.robot.subsystems.beltwrap;

import org.littletonrobotics.junction.AutoLog;


public interface BeltwrapIO {
    
    default void setVoltage(double voltage) {}

    default void updateInputs(BeltwrapIOInputs inputs) {}

    @AutoLog
    class BeltwrapIOInputs {
        public double beltwrapVoltage;
        public double beltwrapCurrent;
        public double beltwrapTemperature;
    }
}
