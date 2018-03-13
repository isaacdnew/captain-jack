package org.frc6300.robot.commands;

import org.frc6300.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Center extends CommandGroup {
	public enum SwitchPosition {
		LEFT, RIGHT
	}
	private SwitchPosition position;
	
	public Center(Robot robot, SwitchPosition switchPosition) {
		this.position = switchPosition;
		// Hold onto the cube
		addSequential(new CloseClaw(robot.claw));

		// Lift arm
		addSequential(new AutoLift(robot.lifter, 1.0, 1.7));

		// Drive to switch, based on position
		addSequential(new AutoDrive(robot.drivetrain, 0.7, 0.8));
		if (position == SwitchPosition.LEFT) {
			
		}
		else if (position == SwitchPosition.RIGHT) {
			
		}
	}
}
