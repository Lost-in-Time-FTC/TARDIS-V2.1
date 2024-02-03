package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

@SuppressWarnings("unused")
@Autonomous(name = "BlueFarAuto")
public class PropBlobAutonomousImplBlueFar extends PropBlobAutonomousABC {

    private PIDController slidecontroller;
    public static double p2 = 0.006, i2 = 0, d2 = 0;
    public static double f2 = 0;
    public static int target2 = 0;
    private final double tickes_in_degree = 700 / 180.0;



    @Override
    public void runOpMode() throws InterruptedException {
        initHardware(PropBlobDetection.AllianceColor.BLUE);
        trackTelemetryWhileNotIsStarted();
        slidecontroller = new PIDController(p2, i2, d2);
        waitForStart();

        Hardware hardware = new Hardware(hardwareMap);
        // Store detected parking position
        PropBlobDetection.PropBlobPosition position = propBlobDetection.getPosition();

        final double LEFT_CLAW_OPEN = 1;
        final double LEFT_CLAW_CLOSE = 0;
        final double RIGHT_CLAW_OPEN = 0;
        final double RIGHT_CLAW_CLOSE = 1;

        // Pick up preloaded pixels and adjust arm position
        if (position == PropBlobDetection.PropBlobPosition.LEFT) {
            telemetry.addData("left blue", "4324");
            // close both claws to hold pixels
            hardware.rightClawServo.setPosition(RIGHT_CLAW_CLOSE);
            hardware.leftClawServo.setPosition(LEFT_CLAW_CLOSE);
            // rotate claw down for more accuracy
            hardware.verticalServo.setPosition(0.6);
            // move forward
            setAllWheelMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            setAllWheelMotorTargetPosition(1000);
            setAllWheelMotorPower(0.6);
            setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
            trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
            setAllWheelMotorPower(0);
            // strafe left to align with spikestrip
            setAllWheelMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            strafeLeft(452);
            setAllWheelMotorPower(0.25);
            setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
            trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
            setAllWheelMotorPower(0);
            // release the pixel and let it fall for 2 seconds
            hardware.leftClawServo.setPosition(LEFT_CLAW_OPEN);
            sleep(2000);
            hardware.leftClawServo.setPosition(LEFT_CLAW_CLOSE);

        } else if (position == PropBlobDetection.PropBlobPosition.CENTER) {
            telemetry.addData("center blue", "4324");
            hardware.rightClawServo.setPosition(RIGHT_CLAW_CLOSE);
            hardware.leftClawServo.setPosition(LEFT_CLAW_CLOSE);
            // rotate claw downwards for more accurate placement, also pushes the prop out of the way
            hardware.verticalServo.setPosition(0.6);
            sleep(1000);
            // Go forward
            setAllWheelMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            setAllWheelMotorTargetPosition(1430);
            setAllWheelMotorPower(0.4);
            setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
            trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
            setAllWheelMotorPower(0);
            // release the pixel and let it fall for 2 seconds
            hardware.leftClawServo.setPosition(LEFT_CLAW_OPEN);
            sleep(2000);
            hardware.leftClawServo.setPosition(LEFT_CLAW_CLOSE);

        } else if (position == PropBlobDetection.PropBlobPosition.RIGHT) {
            telemetry.addData("left blue", "4324");
            // close both claws to hold pixels
            hardware.rightClawServo.setPosition(RIGHT_CLAW_CLOSE);
            hardware.leftClawServo.setPosition(LEFT_CLAW_CLOSE);
            // rotate claw down for more accuracy
            hardware.verticalServo.setPosition(0.6);
            // move forward
            setAllWheelMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            setAllWheelMotorTargetPosition(1000);
            setAllWheelMotorPower(0.6);
            setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
            trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
            setAllWheelMotorPower(0);
            // strafe left to align with spikestrip
            setAllWheelMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            strafeLeft(452);
            setAllWheelMotorPower(0.25);
            setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
            trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
            setAllWheelMotorPower(0);
            // release the pixel and let it fall for 2 seconds
            hardware.leftClawServo.setPosition(LEFT_CLAW_OPEN);
            sleep(2000);
            hardware.leftClawServo.setPosition(LEFT_CLAW_CLOSE);
        }
        telemetry.addData("placeholder ", 4324);
        sleep(5000);
        hardware.rightClawServo.setPosition(RIGHT_CLAW_CLOSE);
        hardware.leftClawServo.setPosition(LEFT_CLAW_CLOSE);
    }
}


