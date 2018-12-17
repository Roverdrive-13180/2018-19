package org.firstinspires.ftc.team13180;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name="TestLander", group="manualmode")

public class TestLander extends LinearOpMode {

    private RoboLander lander;
    static final double SPEED = 0.5;

    @Override
    public void runOpMode() {

        lander = new RoboLander(this);
        lander.init();

        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.a) {
                lander.moveDown(SPEED);  // Move down for ms
            }
            else if (gamepad1.b) {
                lander.moveUp(SPEED);  // Move up for ms time
            }
            else if (gamepad1.x) {
                lander.encoderDrive(SPEED, -19, 5000.0);  // Move down (negative distance) 2cms
            }
            else if (gamepad1.y) {
                lander.encoderDrive(SPEED, 19, 5000.0);  // Move up (positive distance) 2cms
            }
            else {
                lander.stopMotor();
            }
        }

    }
}