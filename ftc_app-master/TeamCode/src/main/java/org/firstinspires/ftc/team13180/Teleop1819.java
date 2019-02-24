package org.firstinspires.ftc.team13180;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;

import static java.lang.Math.abs;


@TeleOp(name="Teleop1819", group="manual")
public class Teleop1819 extends LinearOpMode {

    private RoboNavigator robotnav;
    private RoboLander lander;
    private RoboGrabber grabber;

    @Override
    public void runOpMode() {
        robotnav = new RoboNavigator(this);
        robotnav.init();

        lander = new RoboLander(this);
        lander.init();
        grabber = new RoboGrabber(this);
        grabber.init();

        waitForStart();

        double multiPlier1 = 1;
        double minPower = 0.1;
        double SPEED = 1;

        while (opModeIsActive()) {
            double motorPower = 0;
            boolean anySignal = false;

            // Navigator

            if (gamepad1.left_stick_y > minPower) {
                motorPower = multiPlier1 * gamepad1.left_stick_y;
                robotnav.moveBackward(abs(motorPower));
                anySignal = true;
                telemetry.addData("Navigator:", "moveBackward(%f):", motorPower);
                telemetry.update();
            }
            if (gamepad1.left_stick_y < -minPower) {
                motorPower = multiPlier1 * gamepad1.left_stick_y;
                robotnav.moveForward(abs(motorPower));
                anySignal = true;
                telemetry.addData("Navigator:", "moveForward(%f):", motorPower);
                telemetry.update();
            }
            if (gamepad1.x) {
                motorPower = multiPlier1 * SPEED;
                robotnav.shiftLeft(abs(motorPower));
                anySignal = true;
                telemetry.addData("Navigator:", "shiftLeft(%f):", motorPower);
                telemetry.update();
            }
            if (gamepad1.b) {
                motorPower = multiPlier1 * SPEED;
                robotnav.shiftRight(abs(motorPower));
                anySignal = true;
                telemetry.addData("Navigator:", "shiftRight(%f):", motorPower);
                telemetry.update();
            }
            if (gamepad1.right_trigger > minPower) {
                motorPower = multiPlier1 * gamepad1.right_trigger;
                robotnav.turnLeft(abs(motorPower));
                anySignal = true;
                telemetry.addData("Navigator:", "turnLeft(%f):", motorPower);
                telemetry.update();
            }
            if (gamepad1.left_trigger > minPower) {
                motorPower = multiPlier1 * gamepad1.left_trigger;
                robotnav.turnRight(abs(motorPower));
                anySignal = true;
                telemetry.addData("Navigator:", "turnRight`x`(%f):", motorPower);
                telemetry.update();
            }

            // TODO :
            /*
            double r = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
            double robotAngle = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
            double rightX = gamepad1.right_stick_x;
            robotnav.omniWheelDrive(r, robotAngle, rightX);
            */

            if (!anySignal)
                robotnav.stopMotor();

            // Lander
            if (gamepad2.dpad_up) {
                lander.moveUp(SPEED);
            }
            else if (gamepad2.dpad_down) {
                lander.moveDown(SPEED);

            }
            else {
                lander.stopMotor();
            }

            //  Grabber
            if (gamepad2.y) {
                grabber.moveGrabberUp(SPEED);
            } else if (gamepad2.a) {
                grabber.moveGrabberDown(SPEED);
            } else {
                grabber.stopGrabber();
            }


          /*  if (gamepad2.right_trigger > 0.1) {
                grabber.spinInTime(gamepad2.right_trigger, 5);
            }
            if (gamepad2.left_trigger > 0.1) {
                grabber.spinOutTime(gamepad2.left_trigger, 5);
            }
           */
            if (gamepad2.x) {
                grabber.spinIn();
            } else if (gamepad2.b) {
                grabber.spinOut();

            } else {
                grabber.stopSpin();
            }
            if(gamepad2.right_stick_y>minPower){
                grabber.moveWinchUp(SPEED);

            }
            else if(gamepad2.right_stick_y <-minPower){
                grabber.moveWinchDown(SPEED);
            }
            else{
                grabber.stopWinch(0);
            }
        }
    }
}
