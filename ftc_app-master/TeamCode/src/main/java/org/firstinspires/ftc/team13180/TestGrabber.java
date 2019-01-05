package org.firstinspires.ftc.team13180;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="TestGrabber", group ="manualmode")
public class TestGrabber extends LinearOpMode{

    private RoboGrabber grabber;

    static final double SPEED = 0.5;

    @Override
    public void runOpMode() {

        grabber = new RoboGrabber(this);
        grabber.init();

        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.a) {
                grabber.moveGrabberUp(SPEED);
            }
            else if (gamepad1.b) {
                grabber.moveGrabberDown(SPEED);
            }
            else if (gamepad1.x) {
                grabber.spinIn();
            }
            else if (gamepad1.y) {
                grabber.spinOut();
            }

            }
        }

    }


