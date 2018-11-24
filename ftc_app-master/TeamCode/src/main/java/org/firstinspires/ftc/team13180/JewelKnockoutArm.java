package org.firstinspires.ftc.team13180;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Shivam Adeshara on 12/24/2017.
 */

public class JewelKnockoutArm {
    private Servo jewelServo;
    private Servo jewelKnock;

    public void init(HardwareMap hardwareMap) {
        jewelServo = hardwareMap.get(Servo.class,"jewelServo");
        jewelKnock = hardwareMap.get(Servo.class, "jewelKnock");
        //jewelServo.scaleRange(0.0, 1.0);
        jewelServo.getController().pwmEnable();
        jewelServo.setDirection(Servo.Direction.FORWARD);
        jewelKnock.getController().pwmEnable();
        jewelKnock.setDirection(Servo.Direction.FORWARD);

        //jewelServo.setDirection(Servo.Direction.REVERSE);
    }
    public Servo getJewelServo() {
        return jewelServo;
    }
    public void downJewelArm () {
        jewelServo.setPosition(0.43);
        // TODO jewelServo.setPosition(0.43);
        //jewelServo.setPosition(0.25);
    }

    public void upJewelArm (){
        jewelServo.setPosition(1.0);
        //jewelServo.setPosition(0.75);
    }

    public void leftJewelArm () { jewelKnock.setPosition(0.35);}

    public void rightJewelArm () { jewelKnock.setPosition(0.75);}

    public void middleJewelArm () {jewelKnock.setPosition(0.5);}


    public void stopJewelArm() {
        jewelServo.setPosition(0.0);
    }


    public void setJewelArmPosition(double position) {
        jewelServo.setPosition(position);
    }

    public double getJewelArmPosition() {
        return jewelServo.getPosition();
    }



}
