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
public class PIDF_Drive extends OpMode {
    private PIDController controller;

    public static double p3 = 0, i3 = 0, d3 = 0;
    public static double f3 = 0;

    public static int target3 = 0;

    private final double tickes_in_degree = 700 / 180.0;

    private DcMotorEx front_right_motor;

    @Override
    public void init() {
        controller = new PIDController(p3, i3, d3);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        front_right_motor = hardwareMap.get(DcMotorEx.class, "frontRightMotor");
        front_right_motor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER); // Reset the motor encoder
        front_right_motor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER); // Turn the motor back on when we are done
        int position = front_right_motor.getCurrentPosition();
    }

    @Override
    public void loop() {
        controller.setPID(p3, i3, d3);
        int frontRightMotorPos = front_right_motor.getCurrentPosition();
        double pid = controller.calculate(frontRightMotorPos, target3);
        double ff = Math.cos(Math.toRadians(target3 / tickes_in_degree)) * f3;

        double power = pid + ff;

        front_right_motor.setPower(power);

        telemetry.addData("fr pos", frontRightMotorPos);
        telemetry.addData("target", target3);
        telemetry.update();
    }
}
