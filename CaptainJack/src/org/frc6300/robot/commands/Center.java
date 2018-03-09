package org.frc6300.robot.commands;

import org.frc6300.robot.Robot;
import org.frc6300.robot.subsystems.Lifter;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Center extends CommandGroup {
	public enum switchPosition {
		LEFT, RIGHT
	}
	private switchPosition switchPosition;
	
	public Center(Robot robot, switchPosition switchPosition) {
		this.switchPosition = switchPosition;
		// Hold onto the cube
		addSequential(new CloseClaw(robot.claw));

		// Lift arm
		addSequential(new AutoLift(robot.lifter, 1.0, 1.7));

		// Drive to Switch, based on switchPosition
		addSequential(new AutoDrive(robot.drivetrain, 0.7, 0.8));
	}
}
