package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Config
@TeleOp
public class PIDF_Slides extends OpMode {
    private PIDController slidecontroller;

    public static double p1 = 0, i1 = 0, d1 = 0;
    public static double f1 = 0;

    public static int target2 = 0;

    private final double tickes_in_degree = 700 / 180.0;

    private DcMotorEx slide_motor;

    @Override
    public void init() {
        slidecontroller = new PIDController(p1, i1, d1);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        slide_motor = hardwareMap.get(DcMotorEx.class, "elevatorMotor");
        slide_motor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER); // Reset the motor encoder
        slide_motor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER); // Turn the motor back on when we are done
        int position = slide_motor.getCurrentPosition();
    }

    @Override
    public void loop() {
        slidecontroller.setPID(p1, i1, d1);
        int slidePos = slide_motor.getCurrentPosition();
        double pid = slidecontroller.calculate(slidePos, target2);
        double ff = Math.cos(Math.toRadians(target2 / tickes_in_degree)) * f1;

        double power = pid + ff;

        slide_motor.setPower(power);

        telemetry.addData("pos", slidePos);
        telemetry.addData("target", target2);
        telemetry.update();
    }
}
