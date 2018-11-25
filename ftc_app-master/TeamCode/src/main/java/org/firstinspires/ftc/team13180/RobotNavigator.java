package org.firstinspires.ftc.team13180;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Shivam Adeshara on 12/24/2017.
 */

public class RobotNavigator {
    private DcMotor topl;
    private DcMotor topr;
    private DcMotor rearr;
    private DcMotor rearl;

    public void init(HardwareMap hardwareMap) {
        topl = hardwareMap.get(DcMotor.class, "Topl");
        topr= hardwareMap.get(DcMotor.class, "Topr");
        rearl = hardwareMap.get(DcMotor.class, "Rearl");
        rearr = hardwareMap.get(DcMotor.class, "Rearr");
        //leftMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    public void setRunWithEncoderMode() {
        topr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        topl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rearl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rearr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void moveForward(double power) {
        topr.setPower(power);
        topl.setPower(power);
        rearl.setPower(power);
        rearr.setPower(power);
    }

    public void moveBackward(double power) {
        topr.setPower(-power);
        topl.setPower(-power);
        rearl.setPower(-power);
        rearr.setPower(-power);
    }

    public void shiftLeft (double power){
        topl.setPower(power);
        topr.setPower(-power);
        rearl.setPower(-power);
        rearr.setPower(power);
    }

    public void shiftRight (double power){
        topl.setPower(-power);
        topr.setPower(power);
        rearl.setPower(power);
        rearr.setPower(-power);
    }

    public void stopMotor() {
        topl.setPower(0);
        topr.setPower(0);
        rearl.setPower(0);
        rearr.setPower(0);
    }
    public void turnRight (double power){
        topl.setPower(power);
        topr.setPower(-power);
        rearl.setPower(-power);
        rearr.setPower(power);
    }

    public void turnLeft (double power){
        topl.setPower(-power);
        topr.setPower(power);
        rearl.setPower(power);
        rearr.setPower(-power);
    }

    public void moveForwardTime(double power, long time) throws InterruptedException {
        moveForward(power);
        Thread.sleep(time);
        stopMotor();
    }

    public void moveBackwardTime(double power, long time) throws InterruptedException {
        moveBackward(power);
        Thread.sleep(time);
        stopMotor();
    }

    public void moveRightTime(double power, long time) throws InterruptedException {
        moveRight(power);
        Thread.sleep(time);
        stopMotor();
    }

    public void moveLeftTime(double power, long time) throws InterruptedException {
        moveLeft(power);
        Thread.sleep(time);
        stopMotor();
    }
    public void shiftRightTime(double power, long time) throws InterruptedException {
        shiftRight(power);
        Thread.sleep(time);
        stopMotor();
    }
    public void shiftLeftTime(double power, long time) throws InterruptedException {
        shiftLeft(power);
        Thread.sleep(time);
        stopMotor();
    }

    /* Set poer to both top left and rear left motor */
    public void  setLeftMotorPower(double power) {
        topl.setPower(power);
        rearl.setPower(power);
    }

    public void  setRightMotorPower(double power) {
        topr.setPower(power);
        rearr.setPower(power);
    }

    public boolean isRearLeftMotorBusy() {
        return rearl.isBusy();
    }

    public boolean isRearRightMotorBusy() {
        return rearr.isBusy();
    }

    public boolean isTopLeftMotorBusy() {
        return topl.isBusy();
    }

    public boolean isTopRightMotorBusy() {
        return topr.isBusy();
    }

    /* TODO: Test */
    public void moveLeft (double power){
        topl.setPower(power);
        topr.setPower(-power);
        //rearl.setPower(power);
        //rearr.setPower(-power);
    }

    public void moveRight (double power){
        topl.setPower(-power);
        topr.setPower(power);
        //rearl.setPower (-power);
        //rearr.setPower(power);
    }

    // TODO : Check if we need this code
    public void setLeftMotorTargetPosition(int position) {
        //leftMotor.setTargetPosition(position);
        topl.setTargetPosition(position);
        rearl.setTargetPosition(position);
    }

    public void setRightMotorTargetPosition(int position) {
        //rightMotor.setTargetPosition(position);
        topr.setTargetPosition(position);
        rearr.setTargetPosition(position);
    }

    public int getTopLeftMotorCurrentPosition() {
        return topl.getCurrentPosition();
    }

    public int getTopRightMotorCurrentPosition() {
        return topr.getCurrentPosition();
    }

    public int getRearLeftMotorCurrentPosition() {
        return rearl.getCurrentPosition();
    }

    public int getRearRightMotorCurrentPosition() {
        return rearr.getCurrentPosition();
    }

    public void setRunToPosition() {
        topl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        topr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rearl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rearl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void setMotorDirection(DcMotor motor, DcMotor.Direction direction) {
        motor.setDirection(direction); // Set to REVERSE if using AndyMark motor
    }

    public void setMotorDirection() {
        topl.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        rearl.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors
        topr.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        rearr.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors
    }

    /* TODO: See how motor are connected : Else make change direction */
    public void setLeftMotorForwardDirection() {
        topl.setDirection(DcMotor.Direction.FORWARD);
        rearl.setDirection(DcMotor.Direction.FORWARD);
        topr.setDirection(DcMotor.Direction.FORWARD);
        rearr.setDirection(DcMotor.Direction.FORWARD);
    }

}

