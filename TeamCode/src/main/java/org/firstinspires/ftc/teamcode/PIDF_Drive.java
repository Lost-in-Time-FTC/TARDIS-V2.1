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

    public static double p = 0, i = 0, d = 0;
    public static double f = 0;

    public static int target = 0;

    private final double tickes_in_degree = 700 / 180.0;

    private DcMotorEx front_right_motor;

    @Override
    public void init() {
        controller = new PIDController(p, i, d);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        front_right_motor = hardwareMap.get(DcMotorEx.class, "frontRightMotor");
        front_right_motor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER); // Reset the motor encoder
        front_right_motor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER); // Turn the motor back on when we are done
        int position = front_right_motor.getCurrentPosition();
    }

    @Override
    public void loop() {
        controller.setPID(p, i, d);
        int frontRightMotorPos = front_right_motor.getCurrentPosition();
        double pid = controller.calculate(frontRightMotorPos, target);
        double ff = Math.cos(Math.toRadians(target / tickes_in_degree)) * f;

        double power = pid + ff;

        front_right_motor.setPower(power);

        telemetry.addData("fr pos", frontRightMotorPos);
        telemetry.addData("target", target);
        telemetry.update();
    }
}
