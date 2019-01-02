package org.firstinspires.ftc.team13180;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import static java.lang.Math.abs;

/**
 * Created by Shivam Adeshara on 12/24/2017.
 */

public class RoboNavigator {

    private LinearOpMode opMode;

    private DcMotor topl;
    private DcMotor topr;
    private DcMotor rearr;
    private DcMotor rearl;

    boolean logging = true;

    RoboNavigator (LinearOpMode op)
    {
        opMode = op;
    }

    public void init() {
        topl = opMode.hardwareMap.get(DcMotor.class, "Topl");
        topr= opMode.hardwareMap.get(DcMotor.class, "Topr");
        rearl = opMode.hardwareMap.get(DcMotor.class, "Rearl");
        rearr = opMode.hardwareMap.get(DcMotor.class, "Rearr");
        topr.setDirection(DcMotor.Direction.REVERSE);
        rearr.setDirection(DcMotor.Direction.REVERSE);
        if(logging) {
            opMode.telemetry.addData("RoboNavigator:", "Initialized");
        }
    }

    public void moveForward(double power) {
        topr.setPower(abs(power));
        topl.setPower(abs(power));
        rearl.setPower(abs(power));
        rearr.setPower(abs(power));
        if(logging) {
            opMode.telemetry.addData ("RoboNavigator:", "moveForward (power=%f)", power);
        }
    }

    public void moveBackward(double power) {
        topr.setPower(-abs(power));
        topl.setPower(-abs(power));
        rearl.setPower(-abs(power));
        rearr.setPower(-abs(power));
        if(logging) {
            opMode.telemetry.addData ("RoboNavigator:", "moveBackward (power=%f)", power);
        }
    }

    public void shiftLeft (double power){
        topl.setPower(abs(power));
        topr.setPower(-abs(power));
        rearl.setPower(-abs(power));
        rearr.setPower(abs(power));
        if(logging) {
            opMode.telemetry.addData ("RoboNavigator:", "shiftLeft (power=%f)", power);
        }
    }

    public void shiftRight (double power){
        topl.setPower(-abs(power));
        topr.setPower(abs(power));
        rearl.setPower(abs(power));
        rearr.setPower(-abs(power));
        if(logging) {
            opMode.telemetry.addData ("RoboNavigator:", "shiftRight (power=%f)", power);
        }
    }

    public void stopMotor() {
        topl.setPower(0);
        topr.setPower(0);
        rearl.setPower(0);
        rearr.setPower(0);
        if(logging) {
            opMode.telemetry.addData ("RoboNavigator:", "stopMotor ()");
        }
    }
    public void turnRight (double power){
        topl.setPower(abs(power));
        topr.setPower(-abs(power));
        rearl.setPower(abs(power));
        rearr.setPower(-abs(power));
        if(logging) {
            opMode.telemetry.addData ("RoboNavigator:", "turnRight (power=%f)", power);
        }
    }

    public void turnLeft (double power){
        topl.setPower(-abs(power));
        topr.setPower(abs(power));
        rearl.setPower(-abs(power));
        rearr.setPower(abs(power));
        if(logging) {
            opMode.telemetry.addData ("RoboNavigator:", "turnLeft (power=%f)", power);
        }
    }

    public void moveForwardTime(double power, long time) {
        moveForward(power);
        opMode.sleep(time);
        stopMotor();
    }

    public void moveBackwardTime(double power, long time) {
        moveBackward(power);
        opMode.sleep(time);
        stopMotor();
    }
    public void shiftRightTime(double power, long time) {
        shiftRight(power);
        opMode.sleep(time);
        stopMotor();}

    public void shiftLeftTime(double power, long time) {
        shiftLeft(power);
        opMode.sleep(time);
        stopMotor();
    }

    public void turnRightTime(double power, long time) {
        turnRight(power);
        opMode.sleep(time);
        stopMotor();
    }

    public void turnLefttTime(double power, long time) {
        turnLeft(power);
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

    private static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    private static final double     DRIVE_GEAR_REDUCTION    = 0.5 ;     // This is < 1.0 if geared up
    private static final double     WHEEL_DIAMETER_CM   = 10.0 ;     // For figuring circumference
    private static final double     COUNTS_PER_CM         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_CM * 3.1415);

    private void setRunMode (DcMotor.RunMode runMode) {
        topr.setMode(runMode);
        topl.setMode(runMode);
        rearr.setMode(runMode);
        rearl.setMode(runMode);
    }

    private void setPower (double power) {
        topr.setPower(abs(power));
        topl.setPower(abs(power));
        rearr.setPower(abs(power));
        rearl.setPower(abs(power));
    }

    private void logCurrentPosition () {
        if(logging) {
            opMode.telemetry.addData("CurrentPosition:",
                    "topr=%7d, topl=%7d, rearr=%7d, rearl=%7d",
                    topr.getCurrentPosition(),
                    topl.getCurrentPosition(),
                    rearr.getCurrentPosition(),
                    rearl.getCurrentPosition());
        }
    }

    private void setTargetPosition(DIRECTION direction, double cms) {
        // Determine new target position, and pass to motor controller
        if (direction == DIRECTION.FORWARD) {
            topr.setTargetPosition((int) (topr.getCurrentPosition() + (cms * COUNTS_PER_CM)));
            topl.setTargetPosition((int) (topl.getCurrentPosition() + (cms * COUNTS_PER_CM)));
            rearr.setTargetPosition((int) (rearr.getCurrentPosition() + (cms * COUNTS_PER_CM)));
            rearl.setTargetPosition((int) (rearl.getCurrentPosition() + (cms * COUNTS_PER_CM)));
        }
        if (direction == DIRECTION.BACKWARD) {
            topr.setTargetPosition((int) (topr.getCurrentPosition() - (cms * COUNTS_PER_CM)));
            topl.setTargetPosition((int) (topl.getCurrentPosition() - (cms * COUNTS_PER_CM)));
            rearr.setTargetPosition((int) (rearr.getCurrentPosition() - (cms * COUNTS_PER_CM)));
            rearl.setTargetPosition((int) (rearl.getCurrentPosition() - (cms * COUNTS_PER_CM)));
        }
        if (direction == DIRECTION.SHIFT_LEFT) {
            topr.setTargetPosition((int) (topr.getCurrentPosition() + (cms * COUNTS_PER_CM)));
            topl.setTargetPosition((int) (topl.getCurrentPosition() - (cms * COUNTS_PER_CM)));
            rearr.setTargetPosition((int) (rearr.getCurrentPosition() - (cms * COUNTS_PER_CM)));
            rearl.setTargetPosition((int) (rearl.getCurrentPosition() + (cms * COUNTS_PER_CM)));
        }
        if (direction == DIRECTION.SHIFT_RIGHT) {
            topr.setTargetPosition((int) (topr.getCurrentPosition() - (cms * COUNTS_PER_CM)));
            topl.setTargetPosition((int) (topl.getCurrentPosition() + (cms * COUNTS_PER_CM)));
            rearr.setTargetPosition((int) (rearr.getCurrentPosition() + (cms * COUNTS_PER_CM)));
            rearl.setTargetPosition((int) (rearl.getCurrentPosition() - (cms * COUNTS_PER_CM)));
        }
    }

    private boolean isBusy () {
        return topr.isBusy() && topl.isBusy() && rearr.isBusy() && rearl.isBusy();
    }

    public enum DIRECTION {
        FORWARD,
        BACKWARD,
        SHIFT_LEFT,
        SHIFT_RIGHT
    };

    public void encoderDrive(DIRECTION direction,
                             double speed,
                             double cms,
                             double timeoutMs) {

        if(logging) {
            opMode.telemetry.addData("RoboNavitator:", "Resetting Encoders");    //
        }

        setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);

        if(logging) {
            opMode.telemetry.addData("RoboNavigator:", "Encoders reset");
        }

        // Send telemetry message to indicate currentPosition
        logCurrentPosition();

        // Ensure that the opmode is still active
        if (opMode.opModeIsActive()) {

            // Set Target Position
            setTargetPosition(direction, cms);

            // Turn On RUN_TO_POSITION
            setRunMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            ElapsedTime runtime = new ElapsedTime();

            runtime.reset();

            // Based on direction call corresponding Move function
            setPower (speed);

            // keep looping while we are still active, and there is time left, and motor is running.
            while (opMode.opModeIsActive() &&
                    (runtime.seconds() < timeoutMs) &&
                    (isBusy())) {

                // Display it for the driver.
                logCurrentPosition();
            }

            // Stop all motion;
            stopMotor();

            // Turn off RUN_TO_POSITION
            setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    public void setLogging(boolean value) {
        logging = value;
    }
}

