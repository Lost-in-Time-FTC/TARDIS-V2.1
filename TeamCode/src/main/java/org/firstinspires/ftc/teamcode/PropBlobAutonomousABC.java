package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@SuppressWarnings("unused")
public abstract class PropBlobAutonomousABC extends LinearOpMode {
    // PID
    public static double speed = 1200; // Arbitrary number; static to allow for analyzing how PID performs through multiple speeds in dashboard
    public static PIDCoefficients pidCoefficients = new PIDCoefficients(0, 0, 0); // PID coefficients that need to be tuned probably through FTC dashboard
    public PIDCoefficients pidGains = new PIDCoefficients(0, 0, 0); // PID gains which we will define later in the process
    ElapsedTime PIDTimer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS); // Timer
    // Constants
    final double LEFT_CLAW_OPEN = 0;
    final double LEFT_CLAW_CLOSE = 1;
    final double RIGHT_CLAW_OPEN = 1;
    final double RIGHT_CLAW_CLOSE = 0;
    final double CLAW_ROTATE_UP = 1;
    final double CLAW_ROTATE_DOWN = 0;
    final double ARM_PIVOT_SPEED = 1;
    final double ARM_PIVOT_SPEED_DOWN = 1;
    final int ARM_POSITION_LOW_TOLERANCE = 10;
    final int ARM_POSITION_HIGH_TOLERANCE = 50;
    // Name of the Webcam to be set in the config
    public String webcamName = "Webcam 1";
    // Hardware
    public Hardware hardware;
    public PropBlobDetection propBlobDetection;
    public OpenCvCamera camera;

    public final void initHardware(PropBlobDetection.AllianceColor color) {
        // Generic hardware
        hardware = new Hardware(hardwareMap);

        // OpenCV camera
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, webcamName), cameraMonitorViewId);
        propBlobDetection = new PropBlobDetection(color);
        camera.setPipeline(propBlobDetection);

        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
            }
        });
    }

    public final void trackTelemetryWhileNotIsStarted() {
        while (!isStarted()) {
            telemetry.addData("Prop Position: ", propBlobDetection.getPosition());
            telemetry.addData("Path", "Moving");
            telemetry.addData("fr ticks", hardware.frontRightMotor.getCurrentPosition());
            telemetry.addData("fl ticks", hardware.frontLeftMotor.getCurrentPosition());
            telemetry.addData("br ticks", hardware.backRightMotor.getCurrentPosition());
            telemetry.addData("bl ticks", hardware.backLeftMotor.getCurrentPosition());
            telemetry.addData("arm ticks", hardware.armMotor.getCurrentPosition());
            telemetry.addData("elevator ticks", hardware.elevatorMotor.getCurrentPosition());
            telemetry.update();
        }
    }

    public final void strafeLeft(int targetPosition) {
        hardware.frontRightMotor.setTargetPosition(targetPosition);
        hardware.backRightMotor.setTargetPosition(-targetPosition);
        hardware.frontLeftMotor.setTargetPosition(-targetPosition);
        hardware.backLeftMotor.setTargetPosition(targetPosition);
    }

    public final void strafeRight(int targetPosition) {
        hardware.frontRightMotor.setTargetPosition(-targetPosition);
        hardware.backRightMotor.setTargetPosition(targetPosition);
        hardware.frontLeftMotor.setTargetPosition(targetPosition);
        hardware.backLeftMotor.setTargetPosition(-targetPosition);
    }

    public final void rotateRight(int targetPosition) {
        hardware.frontRightMotor.setTargetPosition(-targetPosition);
        hardware.backRightMotor.setTargetPosition(-targetPosition);
        hardware.frontLeftMotor.setTargetPosition(targetPosition);
        hardware.backLeftMotor.setTargetPosition(targetPosition);
    }

    public final void rotateLeft(int targetPosition) {
        hardware.frontRightMotor.setTargetPosition(targetPosition);
        hardware.backRightMotor.setTargetPosition(targetPosition);
        hardware.frontLeftMotor.setTargetPosition(-targetPosition);
        hardware.backLeftMotor.setTargetPosition(-targetPosition);
    }

    public final void rotateLeft_(MotorGroup leftWheels, MotorGroup rightWheels, int targetPosition) {
        leftWheels.setRunMode(Motor.RunMode.PositionControl);
        leftWheels.setPositionCoefficient(0.05);
        double kPLeftWheels = leftWheels.getPositionCoefficient();
        leftWheels.setTargetPosition(-targetPosition);      // an integer representing
        leftWheels.set(0);
        leftWheels.setPositionTolerance(13.6);   // allowed maximum error
        while (!leftWheels.atTargetPosition()) {
            leftWheels.set(0.75);
        }

        rightWheels.setRunMode(Motor.RunMode.PositionControl);
        rightWheels.setPositionCoefficient(0.05);
        double kPRightWheels = rightWheels.getPositionCoefficient();
        rightWheels.setTargetPosition(targetPosition);      // an integer representing
        rightWheels.set(0);
        rightWheels.setPositionTolerance(13.6);   // allowed maximum error
        while (!rightWheels.atTargetPosition()) {
            rightWheels.set(0.75);
        }

        leftWheels.stopMotor(); // stop the motor
        rightWheels.stopMotor(); // stop the motor
    }

    public final void setAllWheelMotorPower(double power) {
        hardware.frontRightMotor.setPower(power);
        hardware.frontLeftMotor.setPower(power);
        hardware.backRightMotor.setPower(power);
        hardware.backLeftMotor.setPower(power);
    }

    public final void setAllWheelMotorMode(DcMotor.RunMode mode) {
        hardware.frontRightMotor.setMode(mode);
        hardware.frontLeftMotor.setMode(mode);
        hardware.backRightMotor.setMode(mode);
        hardware.backLeftMotor.setMode(mode);
    }

    public final void setAllWheelMotorTargetPosition(int position) {
        hardware.frontRightMotor.setTargetPosition(position);
        hardware.backRightMotor.setTargetPosition(position);
        hardware.frontLeftMotor.setTargetPosition(position);
        hardware.backLeftMotor.setTargetPosition(position);
//        setAllWheelMotorTargetPositionTolerance(30);
    }

    public final void setAllWheelMotorTargetPositionTolerance(int tolerance) {
        // Set motor tolerance on wheels (allows less precision but less pausing/thinking)
        hardware.frontRightMotor.setTargetPositionTolerance(tolerance);
        hardware.backRightMotor.setTargetPositionTolerance(tolerance);
        hardware.frontLeftMotor.setTargetPositionTolerance(tolerance);
        hardware.backLeftMotor.setTargetPositionTolerance(tolerance);
    }

    public final void trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy() {
        while (hardware.frontRightMotor.isBusy() || hardware.frontLeftMotor.isBusy() || hardware.backRightMotor.isBusy() || hardware.backLeftMotor.isBusy()) {
            telemetry.addData("Path", "Moving");
            telemetry.addData("fr ticks", hardware.frontRightMotor.getCurrentPosition());
            telemetry.addData("fl ticks", hardware.frontLeftMotor.getCurrentPosition());
            telemetry.addData("br ticks", hardware.backRightMotor.getCurrentPosition());
            telemetry.addData("bl ticks", hardware.backLeftMotor.getCurrentPosition());
            telemetry.update();
        }
    }
}
