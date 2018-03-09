/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.frc6300.robot.subsystems;

import org.frc6300.robot.RobotMap;
import org.frc6300.robot.commands.TeleDrive;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The drivetrain. It drives the robot.
 */
public class Drivetrain extends PIDSubsystem {
	// Drive motors
	private final SpeedController lfMotor = new VictorSP(RobotMap.lfMotor);
	private final SpeedController lbMotor = new VictorSP(RobotMap.lbMotor);
	private final SpeedController rfMotor = new VictorSP(RobotMap.rfMotor);
	private final SpeedController rbMotor = new VictorSP(RobotMap.rbMotor);
	double leftSpeed = 0;
	double rightSpeed = 0;

	// Gearbox solenoids
	private final DoubleSolenoid lSol = new DoubleSolenoid(RobotMap.lSolPort1, RobotMap.lSolPort2);
	private final DoubleSolenoid rSol = new DoubleSolenoid(RobotMap.rSolPort1, RobotMap.rSolPort2);

	// Encoders
	private final Encoder lEncoder = new Encoder(RobotMap.lEncoderPort1, RobotMap.lEncoderPort2, false, Encoder.EncodingType.k4X);
	private final Encoder rEncoder = new Encoder(RobotMap.rEncoderPort1, RobotMap.rEncoderPort2, false, Encoder.EncodingType.k4X);

	private final double encRevsPerPulse = 1.0 / 400;
	private final double lowGearRatio = 15.0 / 42;
	private final double highGearRatio = 1.0; // TODO count gear teeth to measure ratio, or don't bother
	private final double wheelDiameter = 6.0; // inches
	private final double pulseDistLowGear = encRevsPerPulse * lowGearRatio * Math.PI * wheelDiameter;
	private final double pulseDistHighGear = encRevsPerPulse * highGearRatio * Math.PI * wheelDiameter;

	// Heading control TODO tune pid
	Gyro gyro = new ADXRS450_Gyro();
	static final double p = 0.04;
	static final double i = 0.0002;
	static final double d = 0.22;
	static final double feedForward = 0.06;
	static final double pidPeriod = 0.005;

	public Drivetrain() {
		super("drivetrain", p, i, d, feedForward, pidPeriod);
		getPIDController().setAbsoluteTolerance(2.0);
		getPIDController().setInputRange(0.0, 360.0);
		getPIDController().setContinuous(true);
		getPIDController().setOutputRange(-1.0, 1.0);

		lfMotor.setInverted(RobotMap.lfInverted);
		lbMotor.setInverted(RobotMap.lbInverted);
		rfMotor.setInverted(RobotMap.rfInverted);
		rbMotor.setInverted(RobotMap.rbInverted);
		
		lEncoder.setReverseDirection(RobotMap.lEncoderInverted);
		rEncoder.setReverseDirection(RobotMap.rEncoderInverted);

		shiftDown();
	}

	// HEADING CONTROL
	@Override
	protected double returnPIDInput() {
		return gyro.getAngle() - (360 * Math.floor(gyro.getAngle() / 360));
	}

	@Override
	protected void usePIDOutput(double output) {
		double leftOutput = leftSpeed + output;
		double rightOutput = rightSpeed - output;
		if (leftOutput > 1 || rightOutput > 1) {
			double maxOutput = Math.max(leftOutput, rightOutput);
			leftOutput /= maxOutput;
			rightOutput /= maxOutput;
		}
		lfMotor.set(leftOutput * 0.5);
		lbMotor.set(leftOutput * 0.5);
		rfMotor.set(rightOutput * 0.5);
		rbMotor.set(rightOutput * 0.5);
	}
	
	@Override
	public void setSetpoint(double setpoint) {
		super.setSetpoint(setpoint - (360 * Math.floor(setpoint / 360)));
	}

	public void calibrateGyro() {
		System.out.println("Calibrating the gyro...");
		SmartDashboard.putBoolean("Gyro Calibrated?", false);
		gyro.calibrate();
		System.out.println("Gyro calibrated.");
		SmartDashboard.putBoolean("Gyro Calibrated?", true);
	}

	// DRIVING
	public void setSpeeds(double leftSpeed, double rightSpeed) {
		this.leftSpeed = leftSpeed;
		this.rightSpeed = rightSpeed;

		if (!getPIDController().isEnabled()) {
			lfMotor.set(leftSpeed);
			lbMotor.set(leftSpeed);
			rfMotor.set(rightSpeed);
			rbMotor.set(rightSpeed);
		}
	}

	/**
	 * Stops all motors, disabling heading control.
	 */
	public void stop() {
		disable();
		lfMotor.stopMotor();
		lbMotor.stopMotor();
		rfMotor.stopMotor();
		rbMotor.stopMotor();
	}

	// SHIFTING
	public void shiftUp() {
		lSol.set(DoubleSolenoid.Value.kReverse);
		rSol.set(DoubleSolenoid.Value.kReverse);
		rEncoder.setDistancePerPulse(pulseDistHighGear);
		lEncoder.setDistancePerPulse(pulseDistHighGear);
		rEncoder.reset();
		lEncoder.reset();
	}

	public void shiftDown() {
		lSol.set(DoubleSolenoid.Value.kForward);
		rSol.set(DoubleSolenoid.Value.kForward);
		rEncoder.setDistancePerPulse(pulseDistLowGear);
		lEncoder.setDistancePerPulse(pulseDistLowGear);
		rEncoder.reset();
		lEncoder.reset();
	}

	public double getForwardSpeed() {
		return (lEncoder.getRate() + rEncoder.getRate()) / 2;
	}
	public void putEncoderData() {
		SmartDashboard.putNumber("Left Enc Count", lEncoder.get());
		SmartDashboard.putNumber("Left Enc Dist", lEncoder.getDistance());
		SmartDashboard.putNumber("Left Enc Rate", lEncoder.getRate());
		SmartDashboard.putNumber("Right Enc Count", rEncoder.get());
		SmartDashboard.putNumber("Right Enc Dist", rEncoder.getDistance());
		SmartDashboard.putNumber("Right Enc Rate", rEncoder.getRate());
	}
	
	// MISCELLANEOUS
	public void initDefaultCommand() {
		setDefaultCommand(new TeleDrive(this));
	}
}
