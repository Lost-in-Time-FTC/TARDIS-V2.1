package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

@SuppressWarnings("unused")
@Autonomous(name = "RedCloseAuto")
public class PropBlobAutonomousImplRed extends PropBlobAutonomousABC {
    @Override
    public void runOpMode() throws InterruptedException {
        initHardware(PropBlobDetection.AllianceColor.RED);
        trackTelemetryWhileNotIsStarted();
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
            telemetry.addData("left red", "4324");
            // close both claws to hold pixels
            hardware.rightClawServo.setPosition(RIGHT_CLAW_CLOSE);
            hardware.leftClawServo.setPosition(LEFT_CLAW_CLOSE);
            // move forward
            setAllWheelMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            setAllWheelMotorTargetPosition(820);
            setAllWheelMotorPower(0.4);
            setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
            trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
            setAllWheelMotorPower(0);
            // rotate left to face with spikestrip
            setAllWheelMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rotateLeft(500);
            setAllWheelMotorPower(0.25);
            setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
            trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
            setAllWheelMotorPower(0);
            // rotate claw downwards for more accurate placement
            hardware.verticalServo.setPosition(0.7);
            sleep(1000);
             // move forward to bring arm over the spikestrip
            setAllWheelMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            setAllWheelMotorTargetPosition(450);
            setAllWheelMotorPower(0.6);
            setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
            trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
            setAllWheelMotorPower(0);
            // release the pixel and let it fall for 2 seconds
            hardware.rightClawServo.setPosition(RIGHT_CLAW_OPEN);
            sleep(2000);
            // move backwards
            setAllWheelMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            setAllWheelMotorTargetPosition(-650);
            setAllWheelMotorPower(0.6);
            setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
            trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
            setAllWheelMotorPower(0);
            // rotate right to face board
            setAllWheelMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rotateRight(1500);
            setAllWheelMotorPower(0.25);
            setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
            trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
            setAllWheelMotorPower(0);
            // rotate claw upwards to place onto board
            hardware.verticalServo.setPosition(.3);
            sleep(1000);
            // move forward to be infront of the board
            setAllWheelMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            setAllWheelMotorTargetPosition(1650);
            setAllWheelMotorPower(0.6);
            setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
            trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
            setAllWheelMotorPower(0);
            // strafe left to place on leftside of board
            setAllWheelMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            strafeLeft(625);
            setAllWheelMotorPower(0.25);
            setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
            trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
            setAllWheelMotorPower(0);

            // extend claw, drop pixel, retract
            hardware.elevatorMotor.setPower(-0.45);
            sleep(1000);
            hardware.leftClawServo.setPosition(LEFT_CLAW_OPEN);
            sleep(1000);
            hardware.elevatorMotor.setPower(0.45);
            sleep(1250);
            hardware.elevatorMotor.setPower(0);

            // strafe right to avoid other teams
            setAllWheelMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            strafeRight(1250);
            setAllWheelMotorPower(0.45);
            setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
            trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
            setAllWheelMotorPower(0);
            // move forward to avoid other teams
            setAllWheelMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            setAllWheelMotorTargetPosition(600);
            setAllWheelMotorPower(0.45);
            setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
            trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
            setAllWheelMotorPower(0);

        } else if (position == PropBlobDetection.PropBlobPosition.CENTER) {
            telemetry.addData("center red", "4324");
            hardware.rightClawServo.setPosition(RIGHT_CLAW_CLOSE);
            hardware.leftClawServo.setPosition(LEFT_CLAW_CLOSE);
            // rotate claw downwards for more accurate placement, also pushes the prop out of the way
            hardware.verticalServo.setPosition(0.68);
            sleep(1000);
            // Go forward
            setAllWheelMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            setAllWheelMotorTargetPosition(1400);
            setAllWheelMotorPower(0.4);
            setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
            trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
            setAllWheelMotorPower(0);
            // release the pixel and let it fall for 2 seconds
            hardware.rightClawServo.setPosition(RIGHT_CLAW_OPEN);
            sleep(2000);
            // drive backwards to avoid dropped pixel
            setAllWheelMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            setAllWheelMotorTargetPosition(-250);
            setAllWheelMotorPower(0.6);
            setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
            trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
            setAllWheelMotorPower(0);
            // rotate right to face board
            setAllWheelMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rotateRight(1050);
            setAllWheelMotorPower(0.25);
            setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
            trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
            setAllWheelMotorPower(0);
            // rotate claw upwards to place onto board
            hardware.verticalServo.setPosition(0.30);
            sleep(1000);
            // move forward to be infront of the board
            setAllWheelMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            setAllWheelMotorTargetPosition(1700);
            setAllWheelMotorPower(0.6);
            setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
            trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
            setAllWheelMotorPower(0);

            // extend claw, drop pixel, retract
            hardware.elevatorMotor.setPower(-0.45);
            sleep(1000);
            hardware.leftClawServo.setPosition(LEFT_CLAW_OPEN);
            sleep(1000);
            hardware.elevatorMotor.setPower(0.45);
            sleep(1250);
            hardware.elevatorMotor.setPower(0);

            // strafe right to avoid other teams
            setAllWheelMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            strafeRight(1250);
            setAllWheelMotorPower(0.45);
            setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
            trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
            setAllWheelMotorPower(0);
            // move forward to avoid other teams
            setAllWheelMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            setAllWheelMotorTargetPosition(600);
            setAllWheelMotorPower(0.45);
            setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
            trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
            setAllWheelMotorPower(0);
        } else if (position == PropBlobDetection.PropBlobPosition.RIGHT) {
            telemetry.addData("right red", "4324");
            // close both claws to hold pixels
            hardware.rightClawServo.setPosition(RIGHT_CLAW_CLOSE);
            hardware.leftClawServo.setPosition(LEFT_CLAW_CLOSE);

            // rotate claw downwards to knock thing over
            hardware.verticalServo.setPosition(0.4);

            // rotate claw down for more accuracy
            hardware.verticalServo.setPosition(0.500);
            // move forward
            setAllWheelMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            setAllWheelMotorTargetPosition(850);
            setAllWheelMotorPower(.6);
            setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
            trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
            setAllWheelMotorPower(0);
            // strafe right to align with spikestrip
            setAllWheelMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            strafeRight(500);
            setAllWheelMotorPower(0.25);
            setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
            trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
            setAllWheelMotorPower(0);
            // release the pixel and let it fall for 2 seconds
            hardware.rightClawServo.setPosition(RIGHT_CLAW_OPEN);
            sleep(2000);
            // move backward to avoid pixel
            setAllWheelMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            setAllWheelMotorTargetPosition(-300);
            setAllWheelMotorPower(0.6);
            setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
            trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
            setAllWheelMotorPower(0);
            // rotate right to face board
            setAllWheelMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rotateRight(1050);
            setAllWheelMotorPower(0.25);
            setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
            trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
            setAllWheelMotorPower(0);
            // rotate claw upwards to place onto board
            hardware.verticalServo.setPosition(0.30);
            sleep(1000);
            // move forward
            setAllWheelMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            setAllWheelMotorTargetPosition(1250);
            setAllWheelMotorPower(0.6);
            setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
            trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
            setAllWheelMotorPower(0);
            // strafe left a little bit
            setAllWheelMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            strafeLeft(300);
            setAllWheelMotorPower(0.6);
            setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
            trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
            setAllWheelMotorPower(0);

            // extend claw, drop pixel, retract
            hardware.elevatorMotor.setPower(-0.45);
            sleep(1000);
            hardware.leftClawServo.setPosition(LEFT_CLAW_OPEN);
            sleep(1000);
            hardware.elevatorMotor.setPower(0.45);
            sleep(1250);
            hardware.elevatorMotor.setPower(0);

            // strafe right to avoid other teams
            setAllWheelMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            strafeRight(1250);
            setAllWheelMotorPower(0.45);
            setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
            trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
            setAllWheelMotorPower(0);
            // move forward to avoid other teams
            setAllWheelMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            setAllWheelMotorTargetPosition(600);
            setAllWheelMotorPower(0.45);
            setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
            trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
            setAllWheelMotorPower(0);
        }

        telemetry.addData("placeholder ", 4324);
        sleep(5000);
        hardware.rightClawServo.setPosition(RIGHT_CLAW_CLOSE);
        hardware.leftClawServo.setPosition(LEFT_CLAW_CLOSE);
    }
}


