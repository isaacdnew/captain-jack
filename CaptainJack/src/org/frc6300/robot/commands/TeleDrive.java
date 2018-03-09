/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.frc6300.robot.commands;

import org.frc6300.robot.OI;
import org.frc6300.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * An example command. You can replace me with your own command.
 */
public class TeleDrive extends Command {
	private Drivetrain drivetrain;

	public TeleDrive(Drivetrain drivetrain) {
		this.drivetrain = drivetrain;
		requires(drivetrain);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		drivetrain.disable();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double forwardSpeed = OI.deadZone(-OI.driveJoy.getRawAxis(OI.leftY));
		double rotateSpeed = OI.deadZone(OI.driveJoy.getRawAxis(OI.rightX) * 0.8);

		SmartDashboard.putNumber("forwardSpeed", forwardSpeed);
		SmartDashboard.putNumber("rotateSpeed", rotateSpeed);

		double leftSpeed = forwardSpeed + rotateSpeed;
		double rightSpeed = forwardSpeed - rotateSpeed;

		drivetrain.setSpeeds(leftSpeed, rightSpeed);

		if (Math.abs(drivetrain.getForwardSpeed()) > 80) {
			drivetrain.shiftUp();
		} else {
			drivetrain.shiftDown();
		}

		drivetrain.putEncoderData();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		drivetrain.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
