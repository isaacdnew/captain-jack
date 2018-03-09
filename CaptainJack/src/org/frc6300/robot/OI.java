/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.frc6300.robot;

import org.frc6300.robot.commands.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	// xbox controller axis mappings
	public static final int leftX = 0;
	public static final int leftY = 1;
	public static final int lTrigAxis = 2;
	public static final int rTrigAxis = 3;
	public static final int rightX = 4;
	public static final int rightY = 5;

	// xbox controller button mappings
	public static final int a = 1;
	public static final int b = 2;
	public static final int x = 3;
	public static final int y = 4;
	public static final int lTrigBtn = 5;
	public static final int rTrigBtn = 6;

	public static final Joystick driveJoy = new Joystick(0);
	// private final JoystickButton driveA = new JoystickButton(driveJoy, a);
	// private final JoystickButton driveB = new JoystickButton(driveJoy, b);
	// private final JoystickButton driveX = new JoystickButton(driveJoy, x);
	// private final JoystickButton driveY = new JoystickButton(driveJoy, y);
	private final JoystickButton driveRTrig = new JoystickButton(driveJoy, rTrigBtn);
	private final JoystickButton driveLTrig = new JoystickButton(driveJoy, lTrigBtn);

	public static final Joystick cubeJoy = new Joystick(1);
	// private final JoystickButton cubeA = new JoystickButton(cubeJoy, a);
	// private final JoystickButton cubeB = new JoystickButton(cubeJoy, b);
	// private final JoystickButton cubeX = new JoystickButton(cubeJoy, x);
	// private final JoystickButton cubeY = new JoystickButton(cubeJoy, y);
	private final JoystickButton cubeRTrig = new JoystickButton(cubeJoy, rTrigBtn);
	private final JoystickButton cubeLTrig = new JoystickButton(cubeJoy, lTrigBtn);

	public OI(Robot robot) {
//		driveLTrig.whenPressed(new ShiftUp(robot.drivetrain));
//		driveRTrig.whenPressed(new ShiftDown(robot.drivetrain));

		// cubeA.whenPressed(new LiftArmTo("floor"));
		// cubeB.whenPressed(new LiftArmTo("switch"));
		// cubeY.whenPressed(new LiftArmTo("scale"));

		cubeLTrig.whenPressed(new OpenClaw(robot.claw));
		cubeRTrig.whenPressed(new CloseClaw(robot.claw));
	}

	/**
	 * Adds a deadzone to, for example, a joystick input that does not completely
	 * zero itself mechanically.
	 * 
	 * @param input
	 *            the input value (any double between -1 and 1, inclusively).
	 * @param radius
	 *            how far from zero the input can be for the output to still be
	 *            zero. This must be greated than 0 and less than 1.
	 * @return the value after application of the deadzone (between -1 and 1,
	 *         inclusively).
	 */
	public static double deadZone(double input) {
		double output;
		double radius = 0.2;
		double maxOutput = 1;
		if (radius > maxOutput) {
			output = 0;
		} else {
			if (input > radius) {
				output = ((maxOutput * (input - maxOutput)) / (maxOutput - radius)) + maxOutput;
			} else if (input < -radius) {
				output = ((maxOutput * (input + maxOutput)) / (maxOutput - radius)) - maxOutput;
			} else {
				output = 0;
			}
		}
		if (Math.abs(output) > maxOutput) {
			output = maxOutput;
		}
		return output;
	}
}
