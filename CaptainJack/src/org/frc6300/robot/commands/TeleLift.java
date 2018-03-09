package org.frc6300.robot.commands;

import org.frc6300.robot.OI;
import org.frc6300.robot.subsystems.Lifter;
import org.frc6300.robot.subsystems.Wrist;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TeleLift extends Command {
	private Lifter lifter;
	private Wrist wrist;
	private static final boolean AUTOMATIC_WRIST = false;

	private enum LifterPosition {
		STOWED, BOTTOM_LEGAL, ILLEGAL, TOP_LEGAL
	}

	private LifterPosition lifterPosition;

	public TeleLift(Lifter lifter, Wrist wrist) {
		this.lifter = lifter;
		this.wrist = wrist;
		requires(lifter);
		requires(wrist);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		lifter.disable();
		if (AUTOMATIC_WRIST) {
			wrist.enable();
		} else {
			wrist.disable();
		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (AUTOMATIC_WRIST) {
			wrist.setLifterAngleOffset(getLifterAngle());
			if (lifter.getPosition() <= Lifter.minVertLength) {
				// Set setpoint only if it needs to change
				if (!(lifterPosition == LifterPosition.STOWED)) {
					wrist.setSetpoint(160);
				}
				lifterPosition = LifterPosition.STOWED;
			} else if (Lifter.minVertLength < lifter.getPosition() && lifter.getPosition() <= Lifter.topIllegalLength) {
				// Set setpoint only if it needs to change
				if (!(lifterPosition == LifterPosition.ILLEGAL)) {
					wrist.setSetpoint(-90);
				}
				lifterPosition = LifterPosition.ILLEGAL;
			} else if (Lifter.topIllegalLength < lifter.getPosition()) {
				// Set setpoint only if it needs to change
				if (!(lifterPosition == LifterPosition.TOP_LEGAL)) {
					wrist.setSetpoint(0);
				}
				lifterPosition = LifterPosition.TOP_LEGAL;
			}
		} else {
			wrist.setMotor(OI.deadZone(-OI.cubeJoy.getRawAxis(OI.leftY)) * 0.75);
		}
		lifter.setMotor(OI.deadZone(OI.cubeJoy.getRawAxis(OI.rightY))); // there's no neg sign because we want inverted
																		// control (pull down = lift up).
		//SmartDashboard.putNumber("Lifter Length", lifter.getPosition());
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}

	private double getLifterAngle() {
		double a = Lifter.actuatorMountToElbow;
		double b = Lifter.elbowToRodEnd;
		double c = Lifter.collapsedLength;
		return Math.acos(((a*a) + (b*b) - (c*c)) / (2 * a * b));
	}
}
