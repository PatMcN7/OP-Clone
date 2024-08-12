package frc.robot.subsystems.shooter;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import frc.robot.Constants;

public class ShooterIOTalonFX implements ShooterIO {
    private final static int SLOT = 0; // Which PID slot to use onboard TalonFX. Almost never use auxillary PID slot 1

    private final TalonFX leftTalon;
    private final TalonFX rightTalon;
    private final TalonFXConfiguration config;

    private final VoltageOut leftTalonVoltageOut = new VoltageOut(0, true, false, false, false);
    private final VoltageOut rightTalonVoltageOut = new VoltageOut(0, true, false, false, false);
    // FOC is enabled here, but I don't know if it will actually be better with our
    // reduction. Needs to be tested.
    private final VelocityVoltage leftTalonVelocityVoltage = new VelocityVoltage(0, 0, true, 0, SLOT, false, false,
            false);
    private final VelocityVoltage rightTalonVelocityVoltage = new VelocityVoltage(0, 0, true, 0, SLOT, false, false,
            false);
    
    public ShooterIOTalonFX(){
        leftTalon = new TalonFX(Constants.SHOOTER_CONSTANTS.LEFT_TALON_PORT, Constants.CANIVORE_NAME);
        rightTalon = new TalonFX(Constants.SHOOTER_CONSTANTS.RIGHT_TALON_PORT, Constants.CANIVORE_NAME);
        config = new TalonFXConfiguration();
        config.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        config.CurrentLimits.SupplyCurrentLimitEnable = true;
        config.CurrentLimits.SupplyCurrentLimit = 40;
        config.CurrentLimits.StatorCurrentLimitEnable = true;
        config.CurrentLimits.StatorCurrentLimit = 80;
        config.Voltage.PeakForwardVoltage = 12.0;
        config.Voltage.PeakReverseVoltage = -12.0;
        config.ClosedLoopRamps.VoltageClosedLoopRampPeriod = 0.02;
        
        config.Slot0.kV = 0.18; // recalc gain estimation, sysid needed obv
        config.Slot0.kP = 0.;

        leftTalon.getConfigurator().apply(config);
        rightTalon.getConfigurator().apply(config);

        leftTalon.setInverted(true); // Need to test to figure out this value
    }

    @Override
    public void updateInputs(ShooterIOInputs inputs) {
        inputs.leftCurrent = leftTalon.getStatorCurrent().getValueAsDouble();
        inputs.rightCurrent = rightTalon.getStatorCurrent().getValueAsDouble();
        inputs.leftVoltage = leftTalon.getMotorVoltage().getValueAsDouble();
        inputs.rightVoltage = rightTalon.getMotorVoltage().getValueAsDouble();
        inputs.leftTemp = leftTalon.getDeviceTemp().getValueAsDouble();
        inputs.rightTemp = rightTalon.getDeviceTemp().getValueAsDouble();
        inputs.leftRPM = (leftTalon.getVelocity().getValueAsDouble() * 60) * Constants.SHOOTER_CONSTANTS.SHOOTER_GEAR_RATO; // Converting from motor rps to rpm, then converting from motor rpm to flywheel rpm
        inputs.rightRPM = (rightTalon.getVelocity().getValueAsDouble() * 60) * Constants.SHOOTER_CONSTANTS.SHOOTER_GEAR_RATO;
        
    }

    @Override
    public void setVoltage(double leftAppliedVoltage, double rightAppliedVoltage) {
        leftTalon.setControl(leftTalonVoltageOut.withOutput(leftAppliedVoltage));
    }
}
