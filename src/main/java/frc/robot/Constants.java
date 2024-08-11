package frc.robot;

public class Constants {
    public enum Mode{
        REAL,
        SIM,
        REPLAY
    }
    public static final Mode CURRENT_MODE = Mode.SIM;
    public static final int PDH_PORT = 1;
    public static final double SHOOTER_GEAR_RATO = 17/25; // 17 motor rotations correspond to 25 flywheel rotations
    public static final int LEFT_TALON_PORT = 0;
    public static final String CANIVORE_NAME = null;
    public static final int RIGHT_TALON_PORT = 0;
}
