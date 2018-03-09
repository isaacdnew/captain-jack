package org.frc6300.robot.subsystems;

import org.frc6300.robot.RobotMap;
import org.frc6300.robot.commands.TeleWinch;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Winch extends Subsystem {
	private SpeedController motor = new VictorSP(RobotMap.winchMotor);

	public Winch() {
		motor.setInverted(RobotMap.winchInverted);
	}

	public void initDefaultCommand() {
		setDefaultCommand(new TeleWinch(this));
	}

	public void setMotor(double power) {
		motor.set(power);
	}
}
