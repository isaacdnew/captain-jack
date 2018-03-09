package org.frc6300.robot.commands;

import org.frc6300.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoDrive extends Command {
	private Drivetrain drivetrain;
	private double seconds;
	private double forwardSpeed;
	
	/**
	 * Drives the robot straight forward over a specified distance.
	 * @param drivetrain the drivetrain to use
	 * @param forwardSpeed the speed at which to go forward
	 * @param seconds the time for which to drive.
	 */
    public AutoDrive(Drivetrain drivetrain, double forwardSpeed, double seconds) {
    	this.drivetrain = drivetrain;
    	this.seconds = seconds;
    	this.forwardSpeed = forwardSpeed;
        requires(drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	drivetrain.shiftDown();
    	drivetrain.enable();
    	drivetrain.setSpeeds(forwardSpeed, forwardSpeed);
    	Timer.delay(seconds);
    	drivetrain.setSpeeds(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	drivetrain.setSpeeds(0, 0);
    }
}
