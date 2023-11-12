package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@SuppressWarnings("unused")
@Autonomous(name = "Prop Detector Auto")
public class PropAutonomous extends LinearOpMode {
    // Name of the Webcam to be set in the config
    private final String webcamName = "Webcam 1";
    // Hardware
    private Hardware hardware;
    private PropDetection propDetection;
    private OpenCvCamera camera;

    @Override
    public void runOpMode() throws InterruptedException {

        // Init hardware
        hardware = new Hardware(hardwareMap);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, webcamName), cameraMonitorViewId);
        propDetection = new PropDetection();
        camera.setPipeline(propDetection);

        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
            }
        });

        while (!isStarted()) {
            telemetry.addData("ROTATION: ", propDetection.getPosition());
            telemetry.update();
        }

        waitForStart();
        PropDetection.ParkingPosition position = propDetection.getPosition();
        if (position == PropDetection.ParkingPosition.LEFT) {
            hardware.driveLeftTime(0.5, 1300);
        } else if (position == PropDetection.ParkingPosition.RIGHT) {
            hardware.driveRightTime(0.5, 1300);
        } else if (position == PropDetection.ParkingPosition.CENTER) {
            hardware.driveForwardTime(0.5, 900);
        } else {
            telemetry.addData("Unable to Process Color", "");
            telemetry.update();
        }
    }
}