package org.frc6300.robot.commands;

import org.frc6300.robot.Robot;
import org.frc6300.robot.subsystems.Lifter;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RLeft extends CommandGroup {
	public RLeft(Robot robot) {
		// Hold onto the cube
		addSequential(new CloseClaw(robot.claw));

		// Lift
		addSequential(new AutoLift(robot.lifter, 1.0, 1.5));

		// Drive to switch
		addSequential(new AutoDrive(robot.drivetrain, 1.0, 3.4));
	}
}
