package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@SuppressWarnings("unused")
public class Hardware {
    public Servo verticalServo;
//    public Servo planeAngleServo;
//    public Servo planeLaunchServo;
    public DcMotorEx frontLeftMotor;
    public DcMotorEx frontRightMotor;
    public DcMotorEx backLeftMotor;
    public DcMotorEx backRightMotor;
    public Servo rightClawServo;
    public Servo leftClawServo;
    public DcMotorEx elevatorMotor;
    public DcMotorEx armMotor;
    public DcMotorEx climbMotor;
    final double LEFT_CLAW_OPEN = 0;
    final double LEFT_CLAW_CLOSE = 1;
    final double RIGHT_CLAW_OPEN = 1;
    final double RIGHT_CLAW_CLOSE = 0;
    final double CLAW_ROTATE_UP = 1;
    final double CLAW_ROTATE_DOWN = 0;

    public Hardware(HardwareMap hardwareMap) {
        frontRightMotor = hardwareMap.get(DcMotorEx.class, "frontRightMotor");
        frontLeftMotor = hardwareMap.get(DcMotorEx.class, "frontLeftMotor");
        backRightMotor = hardwareMap.get(DcMotorEx.class, "backRightMotor");
        backLeftMotor = hardwareMap.get(DcMotorEx.class, "backLeftMotor");
        rightClawServo = hardwareMap.get(Servo.class, "rightClawServo");
        leftClawServo = hardwareMap.get(Servo.class, "leftClawServo");
        elevatorMotor = hardwareMap.get(DcMotorEx.class, "elevatorMotor");
        armMotor = hardwareMap.get(DcMotorEx.class, "armMotor");
        verticalServo = hardwareMap.get(Servo.class, "verticalServo");
        climbMotor = hardwareMap.get(DcMotorEx.class, "climbingMotor");
//        planeAngleServo = hardwareMap.get(Servo.class, "planeAngleServo");
//        planeLaunchServo = hardwareMap.get(Servo.class, "planeLaunchServo");

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backRightMotor.setDirection(DcMotor.Direction.FORWARD);
        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotor.Direction.FORWARD);
        armMotor.setDirection(DcMotor.Direction.REVERSE);
        elevatorMotor.setDirection(DcMotor.Direction.FORWARD);
    }

    public void closeClaw() {
        rightClawServo.setPosition(RIGHT_CLAW_CLOSE);
        leftClawServo.setPosition(LEFT_CLAW_CLOSE);
    }

    public void openClaw() {
        rightClawServo.setPosition(RIGHT_CLAW_OPEN);
        leftClawServo.setPosition(LEFT_CLAW_OPEN);
    }

    public void driveForwardTime(double power, int time) throws InterruptedException {
        backLeftMotor.setPower(power);
        backRightMotor.setPower(power);
        frontLeftMotor.setPower(power);
        frontRightMotor.setPower(power);
        Thread.sleep(time);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
    }

    public void driveBackwardTime(double power, int time) throws InterruptedException {
        backLeftMotor.setPower(-power);
        backRightMotor.setPower(-power);
        frontLeftMotor.setPower(-power);
        frontRightMotor.setPower(-power);
        Thread.sleep(time);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
    }

    public void driveRightTime(double power, int time) throws InterruptedException {
        backLeftMotor.setPower(-power);
        backRightMotor.setPower(power);
        frontLeftMotor.setPower(power);
        frontRightMotor.setPower(-power);
        Thread.sleep(time);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
    }

    public void driveLeftTime(double power, int time) throws InterruptedException {
        backLeftMotor.setPower(power);
        backRightMotor.setPower(-power);
        frontLeftMotor.setPower(-power);
        frontRightMotor.setPower(power);
        Thread.sleep(time);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
    }
}