package org.frc6300.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.frc6300.robot.ClawCamPipeline;

import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.vision.VisionThread;

/**
 * The camera that looks at the cube from the claw (for human use only)
 */
public class ClawCam extends Subsystem {
	UsbCamera clawCam;
	final int imgWidth = 160;
	final int imgHeight = 120;
	final int fps = 20;
	final int brightness = 20;
	final int exposure = 50;
	final int whiteBalance = 1000;

	VisionThread visionThread;
	final Object turnAngleSync = new Object();
	double centerX = 0.0;
	double lastTurnAngle = 0.0;
	double turnAngle = 0.0;

	public ClawCam(int port) {
		clawCam = new UsbCamera("ClawCam", port);
		clawCam.setResolution(imgWidth, imgHeight);
		clawCam.setFPS(fps);
		clawCam.setBrightness(brightness);
		clawCam.setExposureAuto();
		clawCam.setWhiteBalanceManual(whiteBalance);
	}

	public void startRecording() {
		CameraServer.getInstance().startAutomaticCapture(clawCam);
	}

	public void startProcessing() {
		startRecording();
		CvSource outputStream = CameraServer.getInstance().putVideo("ClawCam", imgWidth, imgHeight);
		visionThread = new VisionThread(clawCam, new ClawCamPipeline(), pipeline -> {
			outputStream.putFrame(pipeline.blurOutput());
		});
		visionThread.start();
	}

	public void initDefaultCommand() {
	}
}