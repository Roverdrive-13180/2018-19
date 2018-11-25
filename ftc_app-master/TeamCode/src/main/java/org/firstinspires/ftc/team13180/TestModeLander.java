package org.firstinspires.ftc.team13180;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp (name="TestModeLander", group="testGroup1")
public class TestModeLander extends LinearOpMode {
    private DcMotor landerMotor;
    @Override
    public void runOpMode() {
        //imu = hardwareMap.get(Gyroscope.class, "imu");
        //landerMotor = hardwareMap.get(DcMotor.class, "Lander");

        // Create a Lander class and initialize
        Lander lander = new Lander();
        lander.init(hardwareMap);

        double tgtPower1 = 0;
        double multiPlier1 = 0.5;

        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.left_stick_y > 0) {
                tgtPower1 = multiPlier1 * this.gamepad1.left_stick_y;
                lander.moveUp(tgtPower1);
            } else if (gamepad1.left_stick_y < 0) {
                tgtPower1 = multiPlier1 * this.gamepad1.left_stick_y;
                lander.moveDown(tgtPower1);
            } else if (gamepad1.left_stick_y == 0){
                lander.stopMotor();
            }

                if(gamepad1.y) {
                    // move to 0 degrees.

                    //servoTest2.setPosition(1);
                } else if (gamepad1.a) {
                    // move to 180 degrees.

                    //servoTest1.setPosition(1);
                    //servoTest2.setPosition(0);
                }
            }

        }
    }

