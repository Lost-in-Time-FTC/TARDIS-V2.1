package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

@SuppressWarnings("unused")
@Autonomous(name = "RedPropBlobAutonomousImplBetter")
public class PropBlobAutonomousImplRedBetter extends PropBlobAutonomousABC {
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


        MotorGroup driveMotors = new MotorGroup((Motor) hardware.frontRightMotor, (Motor) hardware.frontLeftMotor, (Motor) hardware.backRightMotor, (Motor) hardware.backLeftMotor);
        MotorGroup leftWheels = new MotorGroup((Motor) hardware.frontRightMotor, (Motor) hardware.backRightMotor);
        MotorGroup rightWheels = new MotorGroup((Motor) hardware.frontLeftMotor, (Motor) hardware.backLeftMotor);
        MotorGroup strafeJackSparrow = new MotorGroup((Motor) hardware.frontRightMotor, (Motor) hardware.backLeftMotor);
        MotorGroup strafeBlackBeard = new MotorGroup((Motor) hardware.frontLeftMotor, (Motor) hardware.backRightMotor);

        // Pick up preloaded pixels and adjust arm position
        if (position == PropBlobDetection.PropBlobPosition.LEFT) {
            telemetry.addData("left red", "4324");
            // close both claws to hold pixels
            hardware.rightClawServo.setPosition(RIGHT_CLAW_CLOSE);
            hardware.leftClawServo.setPosition(LEFT_CLAW_CLOSE);
            // move forward
            driveMotors.setRunMode(Motor.RunMode.PositionControl);
            driveMotors.setPositionCoefficient(0.05);
            double kP = driveMotors.getPositionCoefficient();
            driveMotors.setTargetPosition(820);      // an integer representing
            driveMotors.set(0);
            driveMotors.setPositionTolerance(13.6);   // allowed maximum error
            while (!driveMotors.atTargetPosition()) {
                driveMotors.set(0.75);
            }
            driveMotors.stopMotor(); // stop the motor

            // rotate left to face with spikestrip
            rotateLeft_(leftWheels, rightWheels, 500);
            // rotate claw downwards for more accurate placement
            hardware.verticalServo.setPosition(0.7);
            sleep(1000);
            // move forward to bring arm over the spikestrip
            driveMotors.setRunMode(Motor.RunMode.PositionControl);
            driveMotors.setTargetPosition(450);      // an integer representing
            driveMotors.set(0);
            driveMotors.setPositionTolerance(13.6);   // allowed maximum error
            while (!driveMotors.atTargetPosition()) {
                driveMotors.set(0.75);
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
                hardware.verticalServo.setPosition(0.50);
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
                sleep(750);
                hardware.elevatorMotor.setPower(-0.45);
                sleep(250);
                hardware.leftClawServo.setPosition(LEFT_CLAW_OPEN);
                sleep(1000);
                hardware.elevatorMotor.setPower(0.45);
                sleep(1250);
                hardware.elevatorMotor.setPower(0);
            }

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
            hardware.verticalServo.setPosition(0.50);
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
            sleep(750);
            hardware.elevatorMotor.setPower(-0.45);
            sleep(250);
            hardware.leftClawServo.setPosition(LEFT_CLAW_OPEN);
            sleep(1000);
            hardware.elevatorMotor.setPower(0.45);
            sleep(1250);
            hardware.elevatorMotor.setPower(0);

        } else if (position == PropBlobDetection.PropBlobPosition.RIGHT) {
            telemetry.addData("right red", "4324");
            // close both claws to hold pixels
            hardware.rightClawServo.setPosition(RIGHT_CLAW_CLOSE);
            hardware.leftClawServo.setPosition(LEFT_CLAW_CLOSE);
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
            setAllWheelMotorTargetPosition(-350);
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
            hardware.verticalServo.setPosition(0.50);
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
            strafeLeft(250);
            setAllWheelMotorPower(0.6);
            setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
            trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
            setAllWheelMotorPower(0);
            // extend claw, drop pixel, retract
            hardware.elevatorMotor.setPower(-0.45);
            sleep(750);
            hardware.elevatorMotor.setPower(-0.45);
            sleep(250);
            hardware.leftClawServo.setPosition(LEFT_CLAW_OPEN);
            sleep(1000);
            hardware.elevatorMotor.setPower(0.45);
            sleep(1250);
            hardware.elevatorMotor.setPower(0);

        }
        telemetry.addData("placeholder ", 4324);
        sleep(5000);
        hardware.rightClawServo.setPosition(RIGHT_CLAW_CLOSE);
        hardware.leftClawServo.setPosition(LEFT_CLAW_CLOSE);
    }
}


