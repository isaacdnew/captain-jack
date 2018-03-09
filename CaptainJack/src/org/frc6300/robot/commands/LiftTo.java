package org.frc6300.robot.commands;

import org.frc6300.robot.Robot;
import org.frc6300.robot.subsystems.Lifter;
import org.frc6300.robot.subsystems.Wrist;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Lifts the arm to a specified height, given by the desired length of the
 * linear actuator
 */
public class LiftTo extends Command {
	private Lifter lifter;
	private Wrist wrist;
	private double length;
	
	private enum LifterPosition {
		STOWED, BOTTOM_LEGAL, ILLEGAL, TOP_LEGAL
	}

	private LifterPosition lifterPosition;

	public LiftTo(Robot robot, double actuatorLength) {
		this.lifter = robot.lifter;
		this.wrist = robot.wrist;
		this.length = actuatorLength;
		requires(lifter);
		requires(wrist);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		lifter.enable();
		lifter.setSetpoint(length);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (wrist.getPIDController().isEnabled()) {
			wrist.setLifterAngleOffset(lifter.getPosition());
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
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (wrist.getPIDController().isEnabled()) {
			return lifter.onTarget() && wrist.onTarget();
		}
		else {
			return lifter.onTarget();
		}
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
