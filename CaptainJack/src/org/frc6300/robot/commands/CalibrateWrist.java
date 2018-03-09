package org.frc6300.robot.commands;

import org.frc6300.robot.subsystems.Wrist;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CalibrateWrist extends Command {

	private Wrist wrist;

	public CalibrateWrist(Wrist wrist) {
		this.wrist = wrist;
		requires(wrist);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		wrist.disable();
		wrist.setMotor(0.2);
		Timer.delay(0.2);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return wrist.getEncStopped();
	}

	// Called once after isFinished returns true
	protected void end() {
		wrist.reset();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
