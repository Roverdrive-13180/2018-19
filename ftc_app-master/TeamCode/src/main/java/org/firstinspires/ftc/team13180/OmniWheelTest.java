package org.firstinspires.ftc.team13180;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;


/**

 */
@TeleOp(name="OmniWheelTest", group="manualmode")
public class OmniWheelTest extends LinearOpMode {

    /*
     * The mecanum drivetrain involves four separate motors that spin in
     * different directions and different speeds to produce the desired
     * movement at the desired speed.
     */

    // declare and initialize four DcMotors.
    private DcMotor topl;
    private DcMotor topr;
    private DcMotor rearl;
    private DcMotor rearr;
    private LinearOpMode opMode;
    private OpMode v;

    @Override
    public void runOpMode() {

        // Name strings must match up with the config on the Robot Controller
        // app.
        topl = opMode.hardwareMap.get(DcMotor.class, "Topl");
        topr = opMode.hardwareMap.get(DcMotor.class, "Topr");
        rearl = opMode.hardwareMap.get(DcMotor.class, "Rearl");
        rearr = opMode.hardwareMap.get(DcMotor.class, "Rearr");
        topr.setDirection(DcMotor.Direction.REVERSE);
        rearr.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();
        while (opModeIsActive()) {


            double r = Math.hypot(v.gamepad1.left_stick_x, v.gamepad1.left_stick_y);
            double robotAngle = Math.atan2(v.gamepad1.left_stick_y, v.gamepad1.left_stick_x) - Math.PI / 4;
            double rightX = v.gamepad1.right_stick_x;
            final double v1 = r * Math.cos(robotAngle) + rightX;
            final double v2 = r * Math.sin(robotAngle) - rightX;
            final double v3 = r * Math.sin(robotAngle) + rightX;
            final double v4 = r * Math.cos(robotAngle) - rightX;

            topl.setPower(v1);
            topr.setPower(v2);
            rearl.setPower(v3);
            rearr.setPower(v4);
        }
    }
}


