package org.firstinspires.ftc.team13180;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import static java.lang.Math.abs;

/**
 * This is a RoboGrabber classs having following functions
 * TODO:
 */
public class RoboGrabber {

    private LinearOpMode opMode;

    private DcMotor grabber;
    private Servo spinner;
    public RoboGrabber (LinearOpMode op) {
        opMode = op;
    }

    public void init() {
        spinner = opMode.hardwareMap.get(Servo.class, "Grabber");
        grabber = opMode.hardwareMap.get(DcMotor.class, "Winch");

        // Set up Tilt in up position
    }

    public void moveGrabberUp(double power) {

        grabber.setPower(abs(power));
    }

    public void moveGrabberDown(double power) {
        grabber.setPower(-abs(power));
    }

    public void moveGrabberUpTime(double power, long time) {
        moveGrabberUp(power);
        opMode.sleep(time);
        stopGrabber();
    }

    public void moveGrabberDownTime(double power, long time) {
        moveGrabberDown(power);
        opMode.sleep(time);
        stopGrabber();
    }


    public void stopGrabber() {
        grabber.setPower(0);
    }

    public void spinIn (){
        spinner.setPosition(0);
    }
    public void spinOut (){
        spinner.setPosition(180);
    }
 /*   public void spinInTime(double power, long time) {
        spinIn(power);
        opMode.sleep(time);
        stopSpinner();
    }

    public void spinOutTime(double power, long time) {
        spinOut(power);
        opMode.sleep(time);
        stopSpinner();
    }

    public void stopSpinner() {
        spinner.setPower(0);
    }

    public void tiltUp() {

    }

    public void tiltDown() {

    } */


}
