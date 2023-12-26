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

    // Declare OpMode members
    private final ElapsedTime runtime = new ElapsedTime();
    boolean clawToggle = false;
    boolean rightClawToggle = false;
    boolean leftClawToggle = false;
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
            drive();
            elevator();
            armPivot();
            airplane();
        }
    }

    // toggling system
    public void toggle(Gamepad currentGamepad2, Gamepad previousGamepad2) {
        try {
            previousGamepad2.copy(currentGamepad2);
            currentGamepad2.copy(gamepad2);
        }

        catch (Exception e) {
            // :)
        }

        if (currentGamepad2.a && !previousGamepad2.a) {
            clawToggle = !clawToggle;
        }
        if (currentGamepad2.left_bumper && !previousGamepad2.left_bumper) {
            leftClawToggle = !leftClawToggle;
        }
        if (currentGamepad2.right_bumper && !previousGamepad2.right_bumper) {
            rightClawToggle = !rightClawToggle;
        }
    }

    // control everything on the claw: pincers + flipping
    public void claw() {

        // variables for fun
        final double LEFT_CLAW_OPEN = 1;
        final double LEFT_CLAW_CLOSE = 0;
        final double RIGHT_CLAW_OPEN = 0;
        final double RIGHT_CLAW_CLOSE = 1;

        // open/close both claws at the same time for fast pickup/dropoff
        if (gamepad2.a) {
            hardware.rightClawServo.setPosition(RIGHT_CLAW_CLOSE);
            hardware.leftClawServo.setPosition(LEFT_CLAW_CLOSE);
        }

        // open/close the claws individually for more precise placement
        if (rightClawToggle) {
            hardware.rightClawServo.setPosition(RIGHT_CLAW_CLOSE);
        } else {
            hardware.rightClawServo.setPosition(RIGHT_CLAW_OPEN);
        }
        if (leftClawToggle) {
            hardware.leftClawServo.setPosition(LEFT_CLAW_CLOSE);
        } else {
            hardware.leftClawServo.setPosition(LEFT_CLAW_OPEN);
        }

        if (gamepad2.left_trigger > 0.25) {
            hardware.verticalServo.setPosition(hardware.verticalServo.getPosition()-0.001);
        }
        if (gamepad2.right_trigger > 0.25) {
            hardware.verticalServo.setPosition(hardware.verticalServo.getPosition()+0.001);
        }
    }

    // obvious
    public void airplane() {
        if (gamepad1.right_bumper) {
            hardware.planeLaunchServo.setPosition(1);
            sleep(2500);
            hardware.planeLaunchServo.setPosition(0);
        }
    }

    // control arm extension
    public void elevator() {
        // down
        if (gamepad2.right_stick_y > 0.25) {
            hardware.elevatorMotor.setPower(-0.45);
        }

        // up
        else if (gamepad2.right_stick_y < -0.25) {
            hardware.elevatorMotor.setPower(0.45);
        }

        // hold position if there is no input
        else {
            hardware.elevatorMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            hardware.elevatorMotor.setPower(0);
        }
    }

    // control arm's circular motion
    public void armPivot() {
        double armPivotSpeed = 0.85;
        double armPower = gamepad2.left_stick_y;
        hardware.armMotor.setPower(armPower * armPivotSpeed);
    }

//     airplane
    public void drone() {
        if (gamepad1.y) {
            hardware.planeLaunchServo.setPosition(0);
        }
    }

    // drive code
    public void drive() {
        // mecanum
        double drive = gamepad1.left_stick_y;
        double turn = -gamepad1.right_stick_x;
        double strafe = -gamepad1.left_stick_x;

        // strafing
        double FL = Range.clip(drive + strafe + turn, -0.5, 0.5);
        double FR = Range.clip(drive - strafe - turn, -0.5, 0.5);
        double BL = Range.clip(drive - strafe + turn, -0.5, 0.5);
        double BR = Range.clip(drive + strafe - turn, -0.5, 0.5);

        double driveSpeed = 1.75;
        double sniperPercent = 0.25;

        // sniper mode = slower and more precise movement
        if (gamepad1.left_trigger > 0) {
            hardware.frontLeftMotor.setPower(FL * driveSpeed * sniperPercent);
            hardware.frontRightMotor.setPower(FR * driveSpeed * sniperPercent);
            hardware.backLeftMotor.setPower(BL * driveSpeed * sniperPercent);
            hardware.backRightMotor.setPower(BR * driveSpeed * sniperPercent);
        }

        // brakes (doesn't really do anything, safety feature)
        else if (gamepad1.right_trigger > 0) {
            hardware.frontLeftMotor.setPower(FL * 0);
            hardware.frontRightMotor.setPower(FR * 0);
            hardware.backLeftMotor.setPower(BL * 0);
            hardware.backRightMotor.setPower(BR * 0);

        }

        // drive normally
        else {
            hardware.frontLeftMotor.setPower(FL * driveSpeed);
            hardware.frontRightMotor.setPower(FR * driveSpeed);
            hardware.backLeftMotor.setPower(BL * driveSpeed);
            hardware.backRightMotor.setPower(BR * driveSpeed);
        }
    }
}