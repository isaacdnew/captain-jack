package org.frc6300.robot.subsystems;

import org.frc6300.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Claw extends Subsystem {
	private DoubleSolenoid lClawSol = new DoubleSolenoid(RobotMap.lClawSolPort1, RobotMap.lClawSolPort2);
	private DoubleSolenoid rClawSol = new DoubleSolenoid(RobotMap.rClawSolPort1, RobotMap.rClawSolPort2);
	
    public Claw() {
    }
    
    public void close() {
    	lClawSol.set(DoubleSolenoid.Value.kReverse);
    	rClawSol.set(DoubleSolenoid.Value.kReverse);
    }
    public void open() {
    	lClawSol.set(DoubleSolenoid.Value.kForward);
    	rClawSol.set(DoubleSolenoid.Value.kForward);
    }
    public void turnOff() {
    	lClawSol.set(DoubleSolenoid.Value.kOff);
    	rClawSol.set(DoubleSolenoid.Value.kOff);
    }
    
    public boolean isClosed() {
    	return lClawSol.get() == DoubleSolenoid.Value.kReverse;
    }
    public boolean isOpen() {
    	return lClawSol.get() == DoubleSolenoid.Value.kForward;
    }
    public boolean isOff() {
    	return lClawSol.get() == DoubleSolenoid.Value.kOff;
    }
    
    public void initDefaultCommand() {
    }
}

