package frc.robot;

public final class Constants {
    public enum Mode{
        REAL,
        SIM,
        REPLAY
    }
    public static final Mode CURRENT_MODE = Mode.SIM;
    public static final int PDH_PORT = 1;
    
    public static final String CANIVORE_NAME = "CANivore";
    

    public static final class SHOOTER_CONSTANTS {
        public static final int LEFT_TALON_PORT = 0;
        public static final int RIGHT_TALON_PORT = 0;
        public static final double SHOOTER_GEAR_RATIO = 17/25; // 17 motor rotations correspond to 25 flywheel rotations
        public static final double OFF_VOLTAGE = 0.;
        public static final double LEFT_SCORE_RPM = 0.;
        public static final double RIGHT_SCORE_RPM = 0.;
        public static final double LEFT_FEED_RPM = 0.;
        public static final double RIGHT_FEED_RPM = 0.;
        public static final double LEFT_EJECT_RPM = 0.;
        public static final double RIGHT_EJECT_RPM = 0.;
        public static final double ROLLER_RADIUS_METERS = 0.051;
        public static final double ACCEPTABLE_RPM_ERROR = 0;
    
    }

    public static final class CARTRIDGE_CONSTANTS {}

    public static final class ARM_CONSTANTS {
        public static final int MOTOR_ID = 0;
        public static final double kV = 0.;
        public static final double kS = 0.;
        public static final double kA = 0.;
        public static final double kP = 0.;
        public static final double kI = 0.;
        public static final double kD = 0.;
        public static final double MM_ACCEL = 0.;
        public static final double MM_CRUISE = 0.;
        public static final double MM_JERK = 0.;

    }

    public static final class BELTWRAP_CONSTANTS {}

    public static final class INTAKE_CONSTANTS {}

    public static final class drivebaseConstants {

    }
}
