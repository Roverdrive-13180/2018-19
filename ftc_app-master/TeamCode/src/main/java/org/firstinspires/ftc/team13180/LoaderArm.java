package org.firstinspires.ftc.team13180;

//import com.qualcomm.hardware.ArmableUsbDevice;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Shivam Adeshara on 12/24/2017.
 */

public class  LoaderArm {
    private DcMotor elevator;
    //private Servo armServo;
    private Servo leftArmServo;
    private Servo rightArmServo;

    public void init(HardwareMap hardwareMap) {
        leftArmServo = hardwareMap.get(Servo.class, "leftArmServo");
        rightArmServo = hardwareMap.get(Servo.class, "rightArmServo");
        //armServo.scaleRange(0.0, 1.0);
        leftArmServo.getController().pwmEnable();
        rightArmServo.getController().pwmEnable();
 //       leftArmServo.setDirection(Servo.Direction.FORWARD);
 //       rightArmServo.setDirection(Servo.Direction.REVERSE);
        elevator = hardwareMap.get(DcMotor.class, "elevator");
        elevator.setDirection(DcMotorSimple.Direction.REVERSE);

        // TODO : Check this
        /*closeArm();
        try {
            moveUpArmTime(0.25, 2000);
        } catch (Exception e) {

        } */
    }
    public Servo getLeftArmServo() {
        return leftArmServo;
    }

    public Servo getRightArmServo() {
        return rightArmServo;
    }

    public void moveUpArm (double power){
        elevator.setPower(power);
    }

    public void moveDownArm (double power){
        elevator.setPower (-power);
    }

    public void moveUpArmTime(double power, long time) throws InterruptedException {
        moveUpArm(power);
        Thread.sleep(time);
        stopArm();
    }

    public void moveDownArmTime(double power, long time) throws InterruptedException {
        moveDownArm(power);
        Thread.sleep(time);
        stopArm();
    }


    public void openArm ()
    {
        leftArmServo.setPosition(0);
        rightArmServo.setPosition(1);
    }

    public void closeArm (){
        leftArmServo.setPosition(1);
        rightArmServo.setPosition(0);
    }

    public void stopArm(){
        elevator.setPower(0);
    }


}
