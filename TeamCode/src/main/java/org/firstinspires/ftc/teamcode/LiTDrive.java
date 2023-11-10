package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.Servo;

@SuppressWarnings("unused")
@TeleOp(name = "LiT Drive Program 2022-2023", group = "Linear OpMode")

public class LiTDrive extends LinearOpMode {
    final double LEFT_CLAW_OPEN = 1;
    final double LEFT_CLAW_CLOSE = 0.5;
    final double RIGHT_CLAW_OPEN = 0.5;
    final double RIGHT_CLAW_CLOSE = 1;
    final double CLAW_ROTATE_UP = 0;
    final double CLAW_ROTATE_DOWN = 1;

    // Declare OpMode members
    private final ElapsedTime runtime = new ElapsedTime();
    //    TouchSensor touchSensor;
    boolean clawToggle = false;
    boolean rotateToggle = false;
    double armPivotSpeed = 0.85;
    private Hardware hardware = null;
    public void runOpMode() {
        Gamepad currentGamepad2 = new Gamepad();
        Gamepad previousGamepad2 = new Gamepad();

        hardware = new Hardware(hardwareMap);

        hardware.backLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        hardware.backRightMotor.setDirection(DcMotor.Direction.REVERSE);
        hardware.frontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        hardware.frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        hardware.armMotor.setDirection(DcMotor.Direction.FORWARD);
        hardware.elevatorMotor.setDirection(DcMotor.Direction.FORWARD);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // Run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            toggle(currentGamepad2, previousGamepad2);
            claw();
            rotateClaw();
            drive();
            elevator();
            armPivot();
        }

    }

    public void toggle(Gamepad currentGamepad2, Gamepad previousGamepad2) {
        try {
            previousGamepad2.copy(currentGamepad2);
            currentGamepad2.copy(gamepad2);
        } catch (Exception e) {
            // e.printStackTrace();
        }

        if (currentGamepad2.a && !previousGamepad2.a) {
            clawToggle = !clawToggle;
        }
        if (currentGamepad2.b && !previousGamepad2.b) {
            rotateToggle = !rotateToggle;
        }
    }

    public void claw() {
        if (clawToggle) {
             hardware.rightClawServo.setPosition(RIGHT_CLAW_CLOSE);
             hardware.leftClawServo.setPosition(LEFT_CLAW_CLOSE);
        } else {
             hardware.rightClawServo.setPosition(RIGHT_CLAW_OPEN);
             hardware.leftClawServo.setPosition(LEFT_CLAW_OPEN);
        }

//        if (touchSensor.isPressed()) {
//            clawServo.setPosition(CLAW_CLOSE);
//        }
    }

    public void rotateClaw() {
        if (rotateToggle) {
             hardware.verticalServo.setPosition(CLAW_ROTATE_UP);
        } else {
             hardware.verticalServo.setPosition(CLAW_ROTATE_DOWN);
        }
    }

    public void drive() {
        // Mecanum
        double drive = gamepad1.left_stick_y;
        double turn = -gamepad1.right_stick_x;
        double strafe = -gamepad1.left_stick_x;

        // Strafing
        double FL = Range.clip(drive + strafe + turn, -0.5, 0.5);
        double FR = Range.clip(drive - strafe - turn, -0.5, 0.5);
        double BL = Range.clip(drive - strafe + turn, -0.5, 0.5);
        double BR = Range.clip(drive + strafe - turn, -0.5, 0.5);

        double QJSpeed = 1.75;
        double sniperPercent = 0.25;

        // Sniper mode
        if (gamepad1.left_trigger > 0) {
            hardware.frontLeftMotor.setPower(FL * QJSpeed * sniperPercent);
            hardware.frontRightMotor.setPower(FR * QJSpeed * sniperPercent);
            hardware.backLeftMotor.setPower(BL * QJSpeed * sniperPercent);
            hardware.backRightMotor.setPower(BR * QJSpeed * sniperPercent);
        }

        // Brakes
        else if (gamepad1.right_trigger > 0) {
            hardware.frontLeftMotor.setPower(FL * 0);
            hardware.frontRightMotor.setPower(FR * 0);
            hardware.backLeftMotor.setPower(BL * 0);
            hardware.backRightMotor.setPower(BR * 0);

        }
        // Normal drive
        else {
            hardware.frontLeftMotor.setPower(FL * QJSpeed);
            hardware.frontRightMotor.setPower(FR * QJSpeed);
            hardware.backLeftMotor.setPower(BL * QJSpeed);
            hardware.backRightMotor.setPower(BR * QJSpeed);
        }
    }

    public void elevator() {
        // Down
        if (gamepad2.right_stick_y > 0.25) {
            hardware.elevatorMotor.setPower(-0.45);
        }
        // Up
        else if (gamepad2.right_stick_y < -0.25) {
            hardware.elevatorMotor.setPower(0.45);
        } else {
            hardware.elevatorMotor.setPower(0);
        }
    }

    public void armPivot() {
        double armPower = -gamepad2.left_stick_y;
        hardware.armMotor.setPower(armPower * armPivotSpeed);
    }

}