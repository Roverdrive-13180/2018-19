package org.firstinspires.ftc.team13180;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static java.lang.Math.abs;

/**
 * This is a lander class having following functions
 * Move lander motor up and down and stop the motor
 */
public class RoboLander {

    private LinearOpMode opMode;

    private DcMotor lander;

    private static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    private static final double     DRIVE_GEAR_REDUCTION    = 0.333 ;     // This is < 1.0 if geared UP
    private static final double     WHINCH_DIAMETER_CM   = 0.7 ;     // For figuring circumference
    static final double     COUNTS_PER_CM         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHINCH_DIAMETER_CM * 3.1415);

    RoboLander(LinearOpMode op)
    {
        opMode = op;
    }

    public void init() {
        lander = opMode.hardwareMap.get(DcMotor.class, "Lander");
        //lander.setDirection(DcMotor.Direction.REVERSE);

        opMode.telemetry.addData ("RoboLander:", "Initialized");
        opMode.telemetry.update();
    }

    public void moveUp(double power) {

        lander.setPower(abs(power));
        opMode.telemetry.addData("RoboLander:", "moveUp(): power set to %f", power);
        opMode.telemetry.update();
    }

    public void moveDown(double power)
    {
        lander.setPower(-abs(power));
        opMode.telemetry.addData("RoboLander:", "moveDown(): power set to %f", -power);
        opMode.telemetry.update();
    }

    public void stopMotor()
    {
        lander.setPower(0);
        opMode.telemetry.addData("RoboLander:", "stopMotor()");
        opMode.telemetry.update();
    }

    public void moveUpTime(double power, long time) {
        moveUp(power);
        opMode.sleep(time);
        stopMotor();
    }

    public void moveDownTime(double power, long time) {
        moveDown(power);
        opMode.sleep(time);
        stopMotor();
    }


    /*
     *  Method to perform a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the opmode running.
     */
    public void encoderDrive(double speed,
                             double cms,
                             double timeoutMs) {
        int newLanderTarget;

        opMode.telemetry.addData("RoboLander:", "Resetting Encoders");    //
        opMode.telemetry.update();

        lander.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lander.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Send telemetry message to indicate successful Encoder reset
        opMode.telemetry.addData("Path0",  "Starting at %7d",
                lander.getCurrentPosition());
        opMode.telemetry.update();


        // Ensure that the opmode is still active
        if (opMode.opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLanderTarget = lander.getCurrentPosition() + (int)(cms * COUNTS_PER_CM);
            lander.setTargetPosition(newLanderTarget);

            // Turn On RUN_TO_POSITION
            lander.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            ElapsedTime     runtime = new ElapsedTime();

            runtime.reset();

            lander.setPower(abs(speed));

            // keep looping while we are still active, and there is time left, and motor is running.
            while (opMode.opModeIsActive() &&
                    (runtime.seconds() < timeoutMs) &&
                    (lander.isBusy())) {

                // Display it for the driver.
                opMode.telemetry.addData("Path1",  "Running to %7d", newLanderTarget);
                opMode.telemetry.addData("Path2",  "Running at %7d",
                        lander.getCurrentPosition());
                opMode.telemetry.update();
            }

            // Stop all motion;
            lander.setPower(0);

            // Turn off RUN_TO_POSITION
            lander.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

}
