package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

@SuppressWarnings("unused")
@Autonomous(name = "PropBlobAutonomousImpl")
public class PropBlobAutonomousImpl extends PropBlobAutonomousABC {
    @Override
    public void runOpMode() throws InterruptedException {
        initHardware();
        trackTelemetryWhileNotIsStarted();
        waitForStart();

        // hardware.armMotor.setPositionPIDFCoefficients(20);
        // hardware.armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Store detected parking position
        PropBlobDetection.PropBlobPosition position = propBlobDetection.getPosition();

        // Pick up preloaded pixels and adjust arm position
        hardware.closeClaw();
        sleep(300);

        hardware.armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.armMotor.setTargetPositionTolerance(ARM_POSITION_LOW_TOLERANCE);
        hardware.armMotor.setTargetPosition(675);
        hardware.armMotor.setPower(ARM_PIVOT_SPEED);
        hardware.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (hardware.armMotor.isBusy()) {
            hardware.verticalServo.setPosition(CLAW_ROTATE_UP);
            telemetry.addData("Arm", "Moving");
            telemetry.addData("arm ticks", hardware.armMotor.getCurrentPosition());
            telemetry.update();
        }

        hardware.armMotor.setPower(0);




        if (position == PropBlobDetection.PropBlobPosition.LEFT) {
            telemetry.addData("left", "4324");
            // Strafe left
            setAllWheelMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            strafeLeft(2000);
            setAllWheelMotorPower(1);
            setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
            trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
            setAllWheelMotorPower(0);

            // Go forward
            setAllWheelMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            setAllWheelMotorTargetPosition(2300);
            setAllWheelMotorPower(0.6);
            setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);

            while (hardware.frontRightMotor.isBusy() || hardware.frontLeftMotor.isBusy() || hardware.backRightMotor.isBusy() || hardware.backLeftMotor.isBusy()) {
                hardware.elevatorMotor.setPower(0.7);
                sleep(1500);
                hardware.elevatorMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                hardware.armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }

            hardware.elevatorMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            hardware.armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            // Place first pixel
            hardware.armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            hardware.armMotor.setTargetPositionTolerance(ARM_POSITION_LOW_TOLERANCE);
            hardware.armMotor.setTargetPosition(-400);
            hardware.armMotor.setPower(ARM_PIVOT_SPEED);
            hardware.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            while (hardware.armMotor.isBusy()) {
                telemetry.addData("moving", "moving");
                telemetry.update();
            }

            hardware.armMotor.setPower(0);

            hardware.openClaw();
            sleep(250);

            // Move arm back up to place on the board
            hardware.armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            hardware.armMotor.setTargetPositionTolerance(ARM_POSITION_LOW_TOLERANCE);
            hardware.armMotor.setTargetPosition(1000);
            hardware.armMotor.setPower(ARM_PIVOT_SPEED);
            hardware.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            while (hardware.armMotor.isBusy()) {
                telemetry.addData("moving", "moving");
                telemetry.update();

                // Rotate left
                setAllWheelMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                rotateLeft(850);
                setAllWheelMotorPower(0.25);
                setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
                trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
                setAllWheelMotorPower(0);

                // Go forward to the board
                setAllWheelMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                setAllWheelMotorTargetPosition(2300);
                setAllWheelMotorPower(0.6);
                setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            hardware.armMotor.setPower(0);

            // Move arm down to place pixel on board
            hardware.armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            hardware.armMotor.setTargetPositionTolerance(ARM_POSITION_LOW_TOLERANCE);
            hardware.armMotor.setTargetPosition(-400);
            hardware.armMotor.setPower(ARM_PIVOT_SPEED);
            hardware.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            while (hardware.armMotor.isBusy()) {
                telemetry.addData("moving", "moving");
                telemetry.update();
            }

            hardware.armMotor.setPower(0);

            hardware.openClaw();
            sleep(250);

            // Strafe left to park
            setAllWheelMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            strafeLeft(2000);
            setAllWheelMotorPower(1);
            setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
            trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
            setAllWheelMotorPower(0);
        } else if (position == PropBlobDetection.PropBlobPosition.CENTER) {
            // Go forward
            setAllWheelMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            setAllWheelMotorTargetPosition(2300);
            setAllWheelMotorPower(0.6);
            setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);

            while (hardware.frontRightMotor.isBusy() || hardware.frontLeftMotor.isBusy() || hardware.backRightMotor.isBusy() || hardware.backLeftMotor.isBusy()) {
                hardware.elevatorMotor.setPower(0.7);
                sleep(1500);
                hardware.elevatorMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                hardware.armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }

            hardware.elevatorMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            hardware.armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            // Place first pixel
            hardware.armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            hardware.armMotor.setTargetPositionTolerance(ARM_POSITION_LOW_TOLERANCE);
            hardware.armMotor.setTargetPosition(-400);
            hardware.armMotor.setPower(ARM_PIVOT_SPEED);
            hardware.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            while (hardware.armMotor.isBusy()) {
                telemetry.addData("moving", "moving");
                telemetry.update();
            }

            hardware.armMotor.setPower(0);

            hardware.openClaw();
            sleep(250);

            // Move arm back up to place on the board
            hardware.armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            hardware.armMotor.setTargetPositionTolerance(ARM_POSITION_LOW_TOLERANCE);
            hardware.armMotor.setTargetPosition(1000);
            hardware.armMotor.setPower(ARM_PIVOT_SPEED);
            hardware.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            while (hardware.armMotor.isBusy()) {
                telemetry.addData("moving", "moving");
                telemetry.update();

                // Rotate left
                setAllWheelMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                rotateLeft(850);
                setAllWheelMotorPower(0.25);
                setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
                trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
                setAllWheelMotorPower(0);

                // Go forward to the board
                setAllWheelMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                setAllWheelMotorTargetPosition(2300);
                setAllWheelMotorPower(0.6);
                setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            hardware.armMotor.setPower(0);

            // Move arm down to place pixel on board
            hardware.armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            hardware.armMotor.setTargetPositionTolerance(ARM_POSITION_LOW_TOLERANCE);
            hardware.armMotor.setTargetPosition(-400);
            hardware.armMotor.setPower(ARM_PIVOT_SPEED);
            hardware.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            while (hardware.armMotor.isBusy()) {
                telemetry.addData("moving", "moving");
                telemetry.update();
            }

            hardware.armMotor.setPower(0);

            hardware.openClaw();
            sleep(250);

            // Strafe left more to park
            setAllWheelMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            strafeLeft(2000);
            setAllWheelMotorPower(1);
            setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
            trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
            setAllWheelMotorPower(0);
        } else if (position == PropBlobDetection.PropBlobPosition.RIGHT) {
            telemetry.addData("right", "4324");
            // Rotate right
            setAllWheelMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rotateRight(750);
            setAllWheelMotorPower(1);
            setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
            trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
            setAllWheelMotorPower(0);

            // Go forward
            setAllWheelMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            setAllWheelMotorTargetPosition(2300);
            setAllWheelMotorPower(0.6);
            setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);

            while (hardware.frontRightMotor.isBusy() || hardware.frontLeftMotor.isBusy() || hardware.backRightMotor.isBusy() || hardware.backLeftMotor.isBusy()) {
                hardware.elevatorMotor.setPower(0.7);
                sleep(1500);
                hardware.elevatorMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                hardware.armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }

            hardware.elevatorMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            hardware.armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            // Place first pixel
            hardware.armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            hardware.armMotor.setTargetPositionTolerance(ARM_POSITION_LOW_TOLERANCE);
            hardware.armMotor.setTargetPosition(-400);
            hardware.armMotor.setPower(ARM_PIVOT_SPEED);
            hardware.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            while (hardware.armMotor.isBusy()) {
                telemetry.addData("moving", "moving");
                telemetry.update();
            }

            hardware.armMotor.setPower(0);

            hardware.openClaw();
            sleep(250);

            // Move arm back up to place on the board
            hardware.armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            hardware.armMotor.setTargetPositionTolerance(ARM_POSITION_LOW_TOLERANCE);
            hardware.armMotor.setTargetPosition(1000);
            hardware.armMotor.setPower(ARM_PIVOT_SPEED);
            hardware.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            while (hardware.armMotor.isBusy()) {
                telemetry.addData("moving", "moving");
                telemetry.update();

                // Rotate left
                setAllWheelMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                rotateLeft(1200);
                setAllWheelMotorPower(0.25);
                setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
                trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
                setAllWheelMotorPower(0);

                // Go forward to the board
                setAllWheelMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                setAllWheelMotorTargetPosition(2300);
                setAllWheelMotorPower(0.6);
                setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            hardware.armMotor.setPower(0);

            // Move arm down to place pixel on board
            hardware.armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            hardware.armMotor.setTargetPositionTolerance(ARM_POSITION_LOW_TOLERANCE);
            hardware.armMotor.setTargetPosition(-400);
            hardware.armMotor.setPower(ARM_PIVOT_SPEED);
            hardware.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            while (hardware.armMotor.isBusy()) {
                telemetry.addData("moving", "moving");
                telemetry.update();
            }

            hardware.armMotor.setPower(0);

            hardware.openClaw();
            sleep(250);

            // Strafe right to park
            setAllWheelMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            strafeRight(2000);
            setAllWheelMotorPower(1);
            setAllWheelMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
            trackAllWheelCurrentPositionTelemetryWhileMotorIsBusy();
            setAllWheelMotorPower(0);
        }

        // KUSHAL LOOK AT THIS!!!!!!!!!!!!!!! NEED TO CHANGE THE VALUES AND FIX THE MOVEMENTS
    }
}
