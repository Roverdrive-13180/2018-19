package org.firstinspires.ftc.team13180;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name="TestNavigator", group="manualmode")

public class TestNavigator extends LinearOpMode {

    private RoboNavigator robotnav;

    static final double SPEED = 0.2;

    @Override
    public void runOpMode() {

        robotnav = new RoboNavigator(this);
        robotnav.init();

        waitForStart();

        while (opModeIsActive()) {

            double rx=gamepad1.left_stick_x;
            double ry=gamepad1.left_stick_y;
            double r2x=gamepad2.left_stick_x;
            double r2y=gamepad2.left_stick_y;
            double turn=gamepad2.right_stick_x;
            if (ry>0 && ry>Math.abs(rx)) {
                robotnav.moveForward(SPEED*ry);
            }
            else if (ry<0 && Math.abs(ry)>Math.abs(rx)) {
                robotnav.moveBackward(SPEED*ry*-1);
            }
            else if (rx>0 && rx>Math.abs(ry)) {
                robotnav.shiftLeft(SPEED* rx);
            }
            else if (rx<0 && Math.abs(rx)>Math.abs(ry)) {
                robotnav.shiftRight(SPEED*rx*-1);
            }
            else if (gamepad1.right_bumper) {
                robotnav.turnLeft(SPEED);
            }
            else if (gamepad1.left_bumper) {
                robotnav.turnRight(SPEED);
            }

            else if(Math.abs(r2x)>0 || Math.abs(r2y)>0){   //can go any direction 360 degrees based on controller input
                robotnav.AnyMecanum(r2x,r2y);
            }
            /*
            else if (gamepad2.y){
                robotnav.diaganolFrontRight(SPEED);
            }
            else if (gamepad2.x){
                robotnav.diagonalFrontLeft(SPEED);
            }
            else if (gamepad2.b){
                robotnav.diagonalBackRight(SPEED);
            }
            else if (gamepad2.a){
                robotnav.diagonalBackLeft(SPEED);
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
                robotnav.encoderDrive(RoboNavigator.DIRECTION.TURN_LEFT, SPEED, 90, 10000);
            }
            else if (gamepad2.right_bumper){
                robotnav.encoderDrive(RoboNavigator.DIRECTION.TURN_RIGHT,SPEED, 90,10000);
            }
            */
           else {
                robotnav.stopMotor();
            }

        }
    }
}