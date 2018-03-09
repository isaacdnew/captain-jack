package org.frc6300.robot.subsystems;

import org.frc6300.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Lifter extends PIDSubsystem {
	// TODO calibrate these lengths
	public static final double minLength = 0.45;
	public static final double minVertLength = 1.68;
	public static final double switchLength = 5.1;
	public static final double topIllegalLength = 7.8;
	public static final double scaleMaxLength = 8.7;
	public static final double maxLength = 9.0;
	public static final double actuatorMountToElbow = 10;
	public static final double elbowToRodEnd = 10;
	public static final double collapsedLength = 10;

	private SpeedController motor = new VictorSP(RobotMap.liftMotor);

	private static final double actuatorRange = 10.0; // inches
	private static final double potOffset = 0.0; // inches
	private Potentiometer pot = new AnalogPotentiometer(RobotMap.liftPot, actuatorRange, potOffset);

	private static final double p = 0.05;
	private static final double i = 0.0;
	private static final double d = 0.0;
	private static final double feedForward = 0;
	private static final double pidPeriod = 0.005;

	public Lifter() {
		super("lifter", p, i, d, feedForward, pidPeriod);
		motor.setInverted(RobotMap.liftInverted);

		setInputRange(potOffset, actuatorRange + potOffset);
		getPIDController().setContinuous(false);
		setAbsoluteTolerance(1.0);
		setOutputRange(-1.0, 1.0);
	}

	public void initDefaultCommand() {
	}

	protected double returnPIDInput() {
		return pot.pidGet() + potOffset;
	}

	protected void usePIDOutput(double output) {
		if (minLength < getPosition() && getPosition() < maxLength) {
			motor.set(output);
			SmartDashboard.putBoolean("Lifter at end?", false);
		} else if (getPosition() <= minLength) {
			motor.set(0.2);
			SmartDashboard.putBoolean("Lifter at end?", true);
		} else if (maxLength <= getPosition()) {
			motor.set(-0.2);
			SmartDashboard.putBoolean("Lifter at end?", true);
		}
	}

	@Override
	public void setSetpoint(double angle) {
		if (minLength < angle && angle < maxLength) {
			super.setSetpoint(angle);
			System.out.println("Setting Lifter Setpoint to " + angle);
		} else if (angle <= minLength) {
			super.setSetpoint(minLength);
		} else if (maxLength <= angle) {
			super.setSetpoint(maxLength);
		}
	}

	public void setMotor(double power) {
		if (getPIDController().isEnabled()) {
			System.out.println("Lifter PID enabled; not setting motor.");
		} else {
//			if (minLength < getPosition() && getPosition() < maxLength) {
//				motor.set(power);
//				SmartDashboard.putBoolean("Lifter at end?", false);
//			} else if (getPosition() <= minLength) {
//				if (power > 0) {
//					motor.set(power);
//				}
//				else {
//					motor.set(0);
//				}
//				SmartDashboard.putBoolean("Lifter at end?", true);
//			} else if (maxLength <= getPosition() && power < 0) {
//				if (power < 0) {
//					motor.set(power);
//				}
//				else {
//					motor.set(0);
//				}
//				SmartDashboard.putBoolean("Lifter at end?", true);
//			}
			motor.set(power);
		}
	}
}
