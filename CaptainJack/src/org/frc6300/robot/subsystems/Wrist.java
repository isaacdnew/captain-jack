package org.frc6300.robot.subsystems;

import org.frc6300.robot.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Wrist extends PIDSubsystem {
	private SpeedController motor = new VictorSP(RobotMap.wristMotor);
	private Encoder enc = new Encoder(RobotMap.wristEncPort1, RobotMap.wristEncPort2, false, Encoder.EncodingType.k4X);
	private static final double initEncOffset = 0; // TODO calibrate this
	public static final double foldedAngle = 0; // TODO calibrate this
	private double lifterAngleOffset;

	private static final double p = 0.01;
	private static final double i = 0.0;
	private static final double d = 0.0;
	private static final double feedForward = 0.0;
	private static final double pidPeriod = 0.005;

	public Wrist() {
		super("wrist", p, i, d, feedForward, pidPeriod);
		setInputRange(-initEncOffset, (360.0 - initEncOffset));
		getPIDController().setContinuous(false);
		setOutputRange(-1.0, 1.0);

		enc.setDistancePerPulse(90.0 / 215.0); // 215 steps = 90 degrees on this encoder and gearing setup TODO no
												// longer true with chain drive
		enc.setMinRate(0.5);
	}

	public void initDefaultCommand() {
	}

	protected double returnPIDInput() {
		SmartDashboard.putNumber("Wrist PID Input", getTrueAngle());
		return getTrueAngle();
	}

	protected void usePIDOutput(double output) {
		motor.set(-output);
		SmartDashboard.putNumber("Wrist PID Output", output);
	}

	@Override
	public void setSetpoint(double angle) {
		super.setSetpoint(angle);
		System.out.println("Setting Wrist Setpoint to " + angle);
	}

	private double getTrueAngle() {
			return enc.getDistance() + initEncOffset + lifterAngleOffset;
	}
	
	public void setLifterAngleOffset(double angle) {
		lifterAngleOffset = angle;
	}

	public boolean getEncStopped() {
		return enc.getStopped();
	}

	public void setMotor(double output) {
		if (!getPIDController().isEnabled()) {
			motor.set(output);
		} else {
			System.out.println("Wrist PID enabled; not moving wrist.");
		}
	}

	public void reset() {
		disable();
		enc.reset();
		setSetpoint(0.0);
		enable();
		System.out.println("Wrist was reset.");
	}
}
