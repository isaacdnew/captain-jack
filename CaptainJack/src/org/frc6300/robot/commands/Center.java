package org.frc6300.robot.commands;

import org.frc6300.robot.Robot;
import org.frc6300.robot.subsystems.Lifter;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Center extends CommandGroup {
	public Center(Robot robot) {
		// Hold onto the cube
		addSequential(new CloseClaw(robot.claw));

		// Flop and lift simultaneously
		addSequential(new AutoLift(robot.lifter, 1.0, 1.7));

		// Drive to auto line, but turn left first to avoid the pile of power cubes
		addSequential(new AutoDrive(robot.drivetrain, 0.7, 3.5)); 
	}
}
