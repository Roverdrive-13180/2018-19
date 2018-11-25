package org.firstinspires.ftc.team13180;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * This is a lander clasds having following functions
 * Move lander motor up and down and stop the motor
 */
public class Lander {
    private DcMotor lander;

    public void init(HardwareMap hardwareMap) {
        lander = hardwareMap.get(DcMotor.class, "Lander");
        //leftMotor.setDirection(DcMotor.Direction.REVERSE);
    }
    public void moveDown(double power) {
        lander.setPower(power);
    }

    public void moveUp(double power) {
        lander.setPower(power);
    }

    public void stopMotor() {
        lander.setPower(0);
    }
}
