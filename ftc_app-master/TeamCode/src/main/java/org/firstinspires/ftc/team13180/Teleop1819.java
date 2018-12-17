package org.firstinspires.ftc.team13180;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

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
        grabber = new RoboGrabber (this);
        grabber.init();

        waitForStart();

        double multiPlier1 = 1;
        double minPower = 0.1;

        while (opModeIsActive()) {
           double motorPower = 0;
           boolean anySignal = false;

           // Navigator

            if (gamepad1.left_stick_y > minPower){
                motorPower = multiPlier1 * gamepad1.left_stick_y;
                robotnav.moveBackward(abs(motorPower));
                anySignal = true;
                telemetry.addData ("Navigator:", "moveBackward(%f):", motorPower);
                telemetry.update();
            }
            if (gamepad1.left_stick_y < -minPower){
                motorPower = multiPlier1 * gamepad1.left_stick_y;
                robotnav.moveForward(abs(motorPower));
                anySignal = true;
                telemetry.addData ("Navigator:", "moveForward(%f):", motorPower);
                telemetry.update();
            }
            if(gamepad1.right_stick_x > minPower){
                motorPower = multiPlier1 * gamepad1.right_stick_x;
                robotnav.shiftLeft(abs(motorPower));
                anySignal = true;
                telemetry.addData ("Navigator:", "shiftLeft(%f):", motorPower);
                telemetry.update();
            }
            if (gamepad1.right_stick_x < -minPower){
                motorPower = multiPlier1 * gamepad1.right_stick_x;
                robotnav.shiftRight(abs(motorPower));
                anySignal = true;
                telemetry.addData ("Navigator:", "shiftRight(%f):", motorPower);
                telemetry.update();
            }
            if (gamepad1.right_trigger > minPower){
                motorPower = multiPlier1 * gamepad1.right_trigger;
                robotnav.turnLeft(abs(motorPower));
                anySignal = true;
                telemetry.addData ("Navigator:", "turnLeft(%f):", motorPower);
                telemetry.update();
            }
            if (gamepad1.left_trigger > minPower){
                motorPower = multiPlier1 * gamepad1.left_trigger;
                robotnav.turnRight(abs(motorPower));
                anySignal = true;
                telemetry.addData ("Navigator:", "turnRight`x`(%f):", motorPower);
                telemetry.update();
            }

            if (!anySignal)
                robotnav.stopMotor();

            // Lander
            if (gamepad2.y){
                lander.moveUpTime(multiPlier1, 5);
            }

            if (gamepad2.a){
                lander.moveDownTime(multiPlier1, 5);
            }

            //  Grabber
            if (gamepad2.dpad_up) {
                grabber.moveGrabberUpTime(0.5, 5);
            }
            if (gamepad2.dpad_down) {
                grabber.moveGrabberDownTime(0.5, 5);
            }
            if (gamepad2.right_trigger > 0.1) {
                grabber.spinInTime(gamepad2.right_trigger, 5);
            }
            if (gamepad2.left_trigger > 0.1) {
                grabber.spinOutTime(gamepad2.left_trigger, 5);
            }
            if (gamepad2.left_bumper) {
                grabber.tiltDown();
            }
            if (gamepad2.right_bumper) {
                grabber.tiltUp();
            }
        }
    }
}







