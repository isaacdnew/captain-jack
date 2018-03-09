package org.frc6300.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.frc6300.robot.TowerCamPipeline;

import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.vision.VisionThread;

/**
 * The camera that looks at the cube from the claw (for human use only)
 */
public class TowerCam extends Subsystem {
	UsbCamera clawCam;
	final int imgWidth = 160;
	final int imgHeight = 120;
	final int fps = 20;
	final int brightness = 20;
	final int exposure = 50;
	final int whiteBalance = 1000;

	VisionThread visionThread;

	public TowerCam(int port) {
		clawCam = new UsbCamera("TowerCam", port);
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
		CvSource outputStream = CameraServer.getInstance().putVideo("TowerCam", imgWidth, imgHeight);
		visionThread = new VisionThread(clawCam, new TowerCamPipeline(), pipeline -> {
			outputStream.putFrame(pipeline.blurOutput());
		});
		visionThread.start();
	}

	public void initDefaultCommand() {
	}
}