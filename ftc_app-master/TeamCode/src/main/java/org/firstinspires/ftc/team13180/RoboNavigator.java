package org.firstinspires.ftc.team13180;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import static java.lang.Math.abs;

/**
 * Created by Shivam Adeshara on 12/24/2017.
 * Updated by RG on 8/11/19. - added methods that can make robot go any direction-
 */

public class RoboNavigator {

    public LinearOpMode opMode;

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
        topl.setPower(-abs(power));
        topr.setPower(abs(power));
        rearl.setPower(abs(power));
        rearr.setPower(-abs(power));
        if(logging) {
            opMode.telemetry.addData ("RoboNavigator:", "shiftLeft (power=%f)", power);
        }
    }

    public void shiftRight (double power){
        topl.setPower(abs(power));
        topr.setPower(-abs(power));
        rearl.setPower(-abs(power));
        rearr.setPower(abs(power));
        if(logging) {
            opMode.telemetry.addData ("RoboNavigator:", "shiftRight (power=%f)", power);
        }
    }
    public void diaganolFrontRight (double power){
        topl.setPower(abs(power));
        rearr.setPower(abs(power));
        topr.setPower(.25*abs(power));
        rearl.setPower(.25*abs(power));
    }

    public void diagonalFrontLeft (double power){
        topr.setPower(abs(power));
        rearl.setPower(abs(power));
        topl.setPower(.25*abs(power));
        rearr.setPower(.25*abs(power));
    }

    public void diagonalBackRight (double power){
        topl.setPower(-abs(power));
        rearr.setPower(-abs(power));
        topr.setPower(.25*-abs(power));
        rearl.setPower(.25*-abs(power));
    }

    public void diagonalBackLeft (double power){
        topr.setPower(-abs(power));
        rearl.setPower(-abs(power));
        topl.setPower(.25*-abs(power));
        rearr.setPower(.25*-abs(power));
    }
    public double getAngle(double x,double y){
        /*
        atan is arctangent (inverse tangent). "angle" is the reference angle of the direction joystick is facing
        following 3 if else statements get the total principal angle depending on which quadrant the joystick is pointed in
        **EVERYTHING IS IN RADIANS**
        */
        return -1*Math.atan2(y,x);
//        double angle=Math.atan2(y,x);
/*        if(x<0 && y<0) {
            angle += 180;
        }
        else if(x<0 && y>0){
            angle=180-angle;
        }
        else if(x>0 && y<0) {
            angle = 360 - angle;
        }
        */
  //      return Math.toRadians(angle);

    }
    public void AnyMecanum(double x,double y){
        double power = Math.sqrt(x * x + y * y);
        topr.setPower((x+y)/power);
        topl.setPower((y-x)/power);
        rearr.setPower((y-x)/power);
        rearl.setPower((x+y)/power);
    }

    public void AccMecanum(double x,double y,double turn){
                                                                             //           / |
                                                                        //               /  |  y
        double mult=Math.sqrt(x*x+y*y); //hypotenuse of triangle formed by joystick x&y /___|
                                                                       //                 x
        /*also magnitude  is a multiplier for how fast robot will go

        following 2 methods just plot angle on sin function sin(angle- 1/4pi) for back left and top right wheels
        &   sin(angle+1/4pi) for top left and back right wheels power
        functions just have an output of what power should be
        That is for left joystick
        */
        double angle=getAngle(x,y);
        angle=Math.toRadians(angle);
        double PosP=getUnitCirclePos(angle,mult);
        double NegP=getUnitCircleNeg(angle,mult);
        //turn is meant for right joystick: can add turn in the middle of a shift to avoid obstacles
        PosP+=turn;
        NegP+=turn;
        if(Math.abs(PosP)>1 || Math.abs(NegP)>1){
            PosP=PosP/(Math.max(PosP,NegP));
            NegP=NegP/(Math.max(PosP,NegP));
            //maintains proportion between both
        }
        topl.setPower(NegP);
        rearl.setPower(PosP);
        topr.setPower(PosP);
        rearr.setPower(NegP);
        /*
        topl.setPower(PosP);
        rearl.setPower(NegP);
        topr.setPower(NegP);
        rearr.setPower(PosP);
        */
        //ask rg if any questions
    }
    public void AngAccMecanum(double angle,double mult, double turn){

        /*following 2 methods just plot angle on sin function sin(angle- 1/4pi) for back left and top right wheels
        &   sin(angle+1/4pi) for top left and back right wheels power
        functions just have an output of what power should be
        That is for left joystick
        */
        double PosP=getUnitCirclePos(angle,mult);
        double NegP=getUnitCircleNeg(angle,mult);
        //turn is meant for right joystick: can add turn in the middle of a shift to avoid obstacles
        PosP+=turn;
        NegP+=turn;
        if(Math.abs(PosP)>1 || Math.abs(NegP)>1){
            PosP=PosP/(Math.max(PosP,NegP));
            NegP=NegP/(Math.max(PosP,NegP));
            //maintains proportion between both
        }
        topl.setPower(PosP);
        rearl.setPower(NegP);
        topr.setPower(NegP);
        rearr.setPower(PosP);
        //ask rg if any questions
    }
    private double getUnitCircleNeg(double angle,double mult){
        double p=Math.sin(angle-(0.25*Math.PI));
        return p*mult;
    }
    private double getUnitCirclePos(double angle,double mult){
        double p=Math.sin(angle+(0.25*Math.PI));
        return p*mult;
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

    public void omniWheelDrive(double r, double robotAngle, double rightX) {
        double v1 = r * Math.cos(robotAngle) + rightX;
        double v2 = r * Math.sin(robotAngle) - rightX;
        double v3 = r * Math.sin(robotAngle) + rightX;
        double v4 = r * Math.cos(robotAngle) - rightX;

        if(v1 != 0 && v2 != 0 && v3 != 0 && v4 != 0) {
            opMode.telemetry.addData("OmniWheel:", "v1=%f v2=%f v3=%f v4=%f", v1, v2, v3, v4);
        }


        topl.setPower(v1);
        topr.setPower(v2);
        rearl.setPower(v3);
        rearr.setPower(v4);
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
     *
     */
    private static  final double ROBO_DIAMETER_CM = 65;
    private static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    private static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared up
    private static final double     WHEEL_DIAMETER_CM   = 10.0 ;     // For figuring circumference
    private static final double     COUNTS_PER_CM         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_CM * 3.1415);
    private  static final double CMS_PER_DEGREE = 3.1415 * ROBO_DIAMETER_CM / 360;
    private  static final double COUNTS_PER_DEGREE = COUNTS_PER_CM * CMS_PER_DEGREE;
    private static final double SHIFT_SLIPPAGE_CORRECTION = 1.35;


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
            topl.setTargetPosition((int) (topr.getCurrentPosition() + (cms * COUNTS_PER_CM)));
            topr.setTargetPosition((int) (topl.getCurrentPosition() + (cms * COUNTS_PER_CM)));
            rearl.setTargetPosition((int) (rearr.getCurrentPosition() + (cms * COUNTS_PER_CM)));
            rearr.setTargetPosition((int) (rearl.getCurrentPosition() + (cms * COUNTS_PER_CM)));
        }
        else if (direction == DIRECTION.BACKWARD) {
            topl.setTargetPosition((int) (topr.getCurrentPosition() - (cms * COUNTS_PER_CM)));
            topr.setTargetPosition((int) (topl.getCurrentPosition() - (cms * COUNTS_PER_CM)));
            rearl.setTargetPosition((int) (rearr.getCurrentPosition() - (cms * COUNTS_PER_CM)));
            rearr.setTargetPosition((int) (rearl.getCurrentPosition() - (cms * COUNTS_PER_CM)));
        } else if (direction == DIRECTION.SHIFT_RIGHT) {
            topl.setTargetPosition((int) (topr.getCurrentPosition() + (cms * COUNTS_PER_CM * SHIFT_SLIPPAGE_CORRECTION)));
            topr.setTargetPosition((int) (topl.getCurrentPosition() - (cms * COUNTS_PER_CM * SHIFT_SLIPPAGE_CORRECTION)));
            rearl.setTargetPosition((int) (rearr.getCurrentPosition() - (cms * COUNTS_PER_CM * SHIFT_SLIPPAGE_CORRECTION)));
            rearr.setTargetPosition((int) (rearl.getCurrentPosition() + (cms * COUNTS_PER_CM * SHIFT_SLIPPAGE_CORRECTION)));
        } else if (direction == DIRECTION.SHIFT_LEFT) {
            topl.setTargetPosition((int) (topr.getCurrentPosition() - (cms * COUNTS_PER_CM * SHIFT_SLIPPAGE_CORRECTION)));
            topr.setTargetPosition((int) (topl.getCurrentPosition() + (cms * COUNTS_PER_CM * SHIFT_SLIPPAGE_CORRECTION)));
            rearl.setTargetPosition((int) (rearr.getCurrentPosition() + (cms * COUNTS_PER_CM * SHIFT_SLIPPAGE_CORRECTION)));
            rearr.setTargetPosition((int) (rearl.getCurrentPosition() - (cms * COUNTS_PER_CM * SHIFT_SLIPPAGE_CORRECTION)));
        } else if (direction == DIRECTION.TURN_LEFT){
            topl.setTargetPosition((int) (topr.getCurrentPosition() - (cms * COUNTS_PER_DEGREE)));
            topr.setTargetPosition((int) (topl.getCurrentPosition() + (cms * COUNTS_PER_DEGREE)));
            rearl.setTargetPosition((int) (rearr.getCurrentPosition() - (cms * COUNTS_PER_DEGREE)));
            rearr.setTargetPosition((int) (rearl.getCurrentPosition() + (cms * COUNTS_PER_DEGREE)));
        }
        else if (direction == DIRECTION.TURN_RIGHT) {
            topl.setTargetPosition((int) (topr.getCurrentPosition() + (cms * COUNTS_PER_DEGREE)));
            topr.setTargetPosition((int) (topl.getCurrentPosition() - (cms * COUNTS_PER_DEGREE)));
            rearl.setTargetPosition((int) (rearr.getCurrentPosition() + (cms * COUNTS_PER_DEGREE)));
            rearr.setTargetPosition((int) (rearl.getCurrentPosition() - (cms * COUNTS_PER_DEGREE)));
        }
    }



    private boolean isBusy () {
        return topr.isBusy() && topl.isBusy() && rearr.isBusy() && rearl.isBusy();
    }

    public enum DIRECTION {
        FORWARD,
        BACKWARD,
        SHIFT_LEFT,
        SHIFT_RIGHT,
        TURN_LEFT,
        TURN_RIGHT
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
                    (runtime.milliseconds() < timeoutMs) &&
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

