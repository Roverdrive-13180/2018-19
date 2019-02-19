package org.firstinspires.ftc.team13180;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp(name="TestGrabber", group ="manualmode")
public class TestGrabber extends LinearOpMode{

    private RoboGrabber grabber;

    static final double SPEED = 1;

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
            else {
                grabber.stopGrabber();
            }

            if (gamepad1.x) {
                grabber.spinIn();
            }
            else if (gamepad1.y) {
                grabber.spinOut();
            }
            else {
                grabber.stopSpin();
            }
            if(gamepad1.dpad_down){
                grabber.moveWinchDown(SPEED);
            }
            else if(gamepad1.dpad_up){
                grabber.moveWinchUp(SPEED);
            }
            
            else{
                grabber.stopWinch(0);
            }


            }
        }

    }


