package org.firstinspires.ftc.team13180;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
//import org.firstinspires.ftc.teamcode.ManualOpMode13180;

/**
 * Created by Shivam Adeshara on 7/13/2018.
 */
@TeleOp
//public class Pinball extends ManualOpMode13180 {
public class Pinball extends LinearOpMode {
    private Servo leftServo;
    private Servo rightServo;


    @Override
    public void runOpMode() {
        //imu = hardwareMap.get(Gyroscope.class, "imu");
        leftServo = hardwareMap.get(Servo.class, "leftServo");
        rightServo = hardwareMap.get(Servo.class, "rightServo");

        // Waiting for Run button press
        waitForStart();

        // This will be in loop till we stop program
        while (opModeIsActive()) {


            if (gamepad1.left_bumper) {
                // move to 0 degrees.
                leftServo.setPosition(0.5);
                sleep(200);
                leftServo.setPosition(0);

            } else if (gamepad1.right_bumper) {

                rightServo.setPosition(0.25);
                sleep(200);
                rightServo.setPosition(0.85);
            }
            telemetry.addData("Status ",  "Running");
            telemetry.update();
        }

        telemetry.addData("Status ",  "Done");
        telemetry.update();
    }
}