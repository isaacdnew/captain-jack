package org.frc6300.robot.commands;

import org.frc6300.robot.subsystems.Wrist;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoWrist extends Command {
	private Wrist wrist;
	private double power;
	private double seconds;
	private boolean finished = false;

	public AutoWrist(Wrist wrist, double power, double seconds) {
		this.wrist = wrist;
		this.power = power;
		this.seconds = seconds;
		requires(wrist);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		wrist.setMotor(power);
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
		wrist.setMotor(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
