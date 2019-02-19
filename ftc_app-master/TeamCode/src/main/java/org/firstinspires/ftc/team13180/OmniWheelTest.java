package org.firstinspires.ftc.team13180;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;


/**

 */
@TeleOp(name="OmniWheelTest", group="manualmode")
public class OmniWheelTest extends LinearOpMode {
    private DcMotor topl;
    private DcMotor topr;
    private DcMotor rearl;
    private DcMotor rearr;

    /*
     * The mecanum drivetrain involves four separate motors that spin in
     * different directions and different speeds to produce the desired
     * movement at the desired speed.
     */

    // declare and initialize four DcMotors.

    boolean logging = true;

    @Override
    public void runOpMode() {

        // Name strings must match up with the config on the Robot Controller
        // app.
        topl = hardwareMap.get(DcMotor.class, "Topl");
        topr = hardwareMap.get(DcMotor.class, "Topr");
        rearl = hardwareMap.get(DcMotor.class, "Rearl");
        rearr = hardwareMap.get(DcMotor.class, "Rearr");
        topr.setDirection(DcMotor.Direction.REVERSE);
        rearr.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("OmniWheel:", "Initialized");


        waitForStart();
        while (opModeIsActive()) {
            double r = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
            double robotAngle = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
            double rightX = gamepad1.right_stick_x;
            double v1 = r * Math.cos(robotAngle) + rightX;
            double v2 = r * Math.sin(robotAngle) - rightX;
            double v3 = r * Math.sin(robotAngle) + rightX;
            double v4 = r * Math.cos(robotAngle) - rightX;

            if(v1 != 0 && v2 != 0 && v3 != 0 && v4 != 0) {
                telemetry.addData("OmniWheel:", "v1=%f v2=%f v3=%f v4=%f", v1, v2, v3, v4);
                telemetry.update();
            }


            topl.setPower(v1);
            topr.setPower(v2);
            rearl.setPower(v3);
            rearr.setPower(v4);

        }
    }
}


