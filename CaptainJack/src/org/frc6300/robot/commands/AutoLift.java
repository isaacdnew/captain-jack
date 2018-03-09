package org.frc6300.robot.commands;

import org.frc6300.robot.subsystems.Lifter;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoLift extends Command {
	private Lifter lifter;
	private double power;
	private double seconds;
	private boolean finished = false;

	public AutoLift(Lifter lifter, double power, double seconds) {
		this.lifter = lifter;
		this.power = power;
		this.seconds = seconds;
		requires(lifter);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		lifter.setMotor(power);
		finished = false;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Timer.delay(seconds);
		finished = true;
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return finished;
	}

	// Called once after isFinished returns true
	protected void end() {
		lifter.setMotor(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
