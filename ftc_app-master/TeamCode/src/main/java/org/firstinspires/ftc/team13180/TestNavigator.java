package org.firstinspires.ftc.team13180;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name="TestNavigator", group="manualmode")

public class TestNavigator extends LinearOpMode {

    private RoboNavigator robotnav;

    static final double SPEED = 0.5;

    @Override
    public void runOpMode() {

        robotnav = new RoboNavigator(this);
        robotnav.init();

        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.y) {
                robotnav.moveForward(SPEED);
            }
            else if (gamepad1.a) {
                robotnav.moveBackward(SPEED);
            }
            else if (gamepad1.b) {
                robotnav.shiftRight(SPEED);
            }
            else if (gamepad1.x) {
                robotnav.shiftLeft(SPEED);
            }
            else if (gamepad1.right_bumper) {
                robotnav.turnRight(SPEED);
            }
            else if (gamepad1.left_bumper) {
                robotnav.turnLeft(SPEED);
            }


            // EncoderMode
            else if (gamepad2.y) {
                robotnav.encoderDrive(RoboNavigator.DIRECTION.FORWARD, SPEED, 50, 10000 );
            }
            else if (gamepad2.a) {
                robotnav.encoderDrive(RoboNavigator.DIRECTION.BACKWARD, SPEED, 50, 10000 );
            }
            else if (gamepad2.b) {
                robotnav.encoderDrive(RoboNavigator.DIRECTION.SHIFT_RIGHT, SPEED, 50, 10000 );
            }
            else if (gamepad2.x) {
                robotnav.encoderDrive(RoboNavigator.DIRECTION.SHIFT_LEFT, SPEED, 50, 10000 );
            }
            else if (gamepad2.left_bumper) {
                robotnav.encoderDrive(RoboNavigator.DIRECTION.TURN_LEFT, SPEED, 10, 10000);
            }
            else if (gamepad2.right_bumper){
                robotnav.encoderDrive(RoboNavigator.DIRECTION.TURN_RIGHT,SPEED, 10,10000);
            }
           else {
                robotnav.stopMotor();
            }
        }
    }
}