package frc.robot.subsystems.uptake;

import org.littletonrobotics.junction.AutoLog;


public interface UptakeIO {
    
    default void setVoltage(double voltage) {}

    default void updateInputs(UptakeIOInputs inputs) {}

    @AutoLog
    class UptakeIOInputs {
        public double uptakeVoltage;
        public double uptakeCurrent;
        public double uptakeTemperature;
    }
}
