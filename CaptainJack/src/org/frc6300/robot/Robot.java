/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.frc6300.robot;

import org.frc6300.robot.commands.*;
import org.frc6300.robot.subsystems.*;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the Timed Robot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	private Compressor compressor = new Compressor();

	// Subsystems
	public final Drivetrain drivetrain = new Drivetrain();
	public final Lifter lifter = new Lifter();
	public final Claw claw = new Claw();
	public final Wrist wrist = new Wrist();
	public final Winch winch = new Winch();
	private final ClawCam gearCam = new ClawCam(1);
	private final TowerCam towerCam = new TowerCam(0);
	
	public OI oi;
	
	private enum StartingSide {
		LEFT, RIGHT, CENTER
	}

	Command autonomousCommand;
	SendableChooser<StartingSide> sideChooser = new SendableChooser<>();

	/**
	 * This method is run when the robot is first started up and should be used for
	 * any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI(this);

		sideChooser.addDefault("Starting from Left", StartingSide.LEFT);
		sideChooser.addObject("Starting from Center", StartingSide.CENTER);
		sideChooser.addObject("Starting from Right", StartingSide.RIGHT);
		SmartDashboard.putData("Starting Side", sideChooser);

		compressor.start();
		gearCam.startProcessing();
		towerCam.startProcessing();
		drivetrain.calibrateGyro();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode. You
	 * can use it to reset any subsystem information you want to clear when the
	 * robot is disabled.
	 */
	@Override
	public void disabledInit() {
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * Chooses an autonomous command based on data from the FMS and the user, and
	 * then starts the command.
	 */
	@Override
	public void autonomousInit() {
		compressor.start();
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		StartingSide startingSide = sideChooser.getSelected();
		if (gameData.charAt(0) == 'L') {
			switch (startingSide) {
			case LEFT:
				autonomousCommand = new LLeft(this);
				System.out.println("LLeft");
				break;
			case RIGHT:
				autonomousCommand = new LRight(this);
				System.out.println("LRight");
				break;
			case CENTER:
				autonomousCommand = new Center(this);
				System.out.println("Center");
				break;
			default:
				System.out.println("Invalid starting side!");
				break;
			}
		} else if (gameData.charAt(0) == 'R') {
			switch (startingSide) {
			case LEFT:
				autonomousCommand = new RLeft(this);
				System.out.println("RLeft");
				break;
			case RIGHT:
				autonomousCommand = new RRight(this);
				System.out.println("RRight");
				break;
			case CENTER:
				autonomousCommand = new Center(this);
				System.out.println("Center");
				break;
			default:
				System.out.println("Invalid starting side!");
				break;
			}
		} else {
			System.out.println("Invalid game data!");
		}
		// schedule the autonomous command
		if (autonomousCommand != null) {
			autonomousCommand.start();
		} else {
			System.out.println("autonomousCommand is null! Running auto line code.");
			autonomousCommand = new AutoDrive(drivetrain, 0.5, 2.0);
			autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
//		compressor.start();
		if (autonomousCommand != null) {
			autonomousCommand.cancel();
		}
//		drivetrain.disable();
		final TeleLift teleLift = new TeleLift(this.lifter, this.wrist);
		teleLift.start();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}
	
	@Override
	public void testInit() {
		final TeleLift teleLift = new TeleLift(this.lifter, this.wrist);
		teleLift.start();
//		compressor.start();
	}
	
	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
