/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.frc6300.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

	// Motor ports
	public static final int lfMotor = 0;
	public static final int lbMotor = 3;
	public static final int rfMotor = 2;
	public static final int rbMotor = 1;

	public static final int liftMotor = 8;

	public static final int wristMotor = 4;

	public static final int winchMotor = 5; // TODO wire this up

	// Motor Inversions
	public static final boolean lfInverted = false;
	public static final boolean lbInverted = false;
	public static final boolean rfInverted = true;
	public static final boolean rbInverted = true;

	public static final boolean liftInverted = false;

	public static final boolean wristInverted = false; // TODO check this

	public static final boolean winchInverted = true; // TODO check this

	// Encoder Ports
	public static final int lEncoderPort1 = 0;
	public static final int lEncoderPort2 = 1;
	public static final int rEncoderPort1 = 2;
	public static final int rEncoderPort2 = 3;

	public static final int wristEncPort1 = 6;
	public static final int wristEncPort2 = 7;

	// Encoder Inversions
	public static final boolean lEncoderInverted = true; // TODO check this
	public static final boolean rEncoderInverted = false; // TODO check this

	public static final boolean wristEncInverted = false; // TODO check this

	// Lifter Potentiometer
	public static final int liftPot = 0;

	// Lever Lengths
	public static final int armLengthInches = 38;
	public static final int wristLengthInches = 27;

	// Solenoid Ports
	public static final int lSolPort1 = 0;
	public static final int lSolPort2 = 1;
	public static final int rSolPort1 = 2;
	public static final int rSolPort2 = 3;

	public static final int lClawSolPort1 = 4;
	public static final int lClawSolPort2 = 5;
	public static final int rClawSolPort1 = 6;
	public static final int rClawSolPort2 = 7;
}
