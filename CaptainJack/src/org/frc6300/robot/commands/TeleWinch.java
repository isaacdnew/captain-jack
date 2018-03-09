package org.frc6300.robot.commands;

import org.frc6300.robot.OI;
import org.frc6300.robot.subsystems.Winch;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TeleWinch extends Command {

	private Winch winch;

	public TeleWinch(Winch winch) {
		this.winch = winch;
		requires(winch);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		winch.setMotor(
				OI.deadZone(OI.cubeJoy.getRawAxis(OI.rTrigAxis)) - OI.deadZone(OI.cubeJoy.getRawAxis(OI.lTrigAxis)));

		// if (Timer.getMatchTime() < 45) {
		// winch.setMotor(OI.deadZone(OI.cubeJoy.getRawAxis(OI.rTrigAxis))
		// - OI.deadZone(OI.cubeJoy.getRawAxis(OI.lTrigAxis)));
		// } else {
		// winch.setMotor(0);
		// }
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		winch.setMotor(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
