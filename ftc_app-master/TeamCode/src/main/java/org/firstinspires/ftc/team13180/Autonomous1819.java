package org.firstinspires.ftc.team13180;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


/**
 *
 */
@Autonomous
@Disabled
public class Autonomous1819 extends LinearOpMode {
    @Override
    public void runOpMode() {


    }
}


/*

@Autonomous(name="AutonomousJewelArm", group="autonomusGroup1")
@Disabled
public class AutonomousJewelArm extends LinearOpMode {
    private RobotNavigator robotNavigator;
    private LoaderArm loaderArm;
    private JewelColorSensor jewelColorSensor;
    private JewelKnockoutArm jewelKnockoutArm;

    @Override
    public void runOpMode () {
        double forwardPower = 0.5;
        int forwardTime = 1000;
        double leftPower = 0.5;
        int leftTime = 1000;

        double armPower = 0.25;
        int armTime = 1000;

        robotNavigator = new RobotNavigator();
        robotNavigator.init(hardwareMap);

        loaderArm = new LoaderArm();
        //loaderArm.init(hardwareMap);

        // Wait for 2 seconds
        try {
            Thread.sleep(2000);
        } catch(Exception e) {

        }

        waitForStart();

        try {
            jewelKnockoutArm.getJewelServo().getController().pwmEnable();
            Thread.sleep(2000);
            Servo.Direction direction = jewelKnockoutArm.getJewelServo().getDirection();
            //jewelKnockoutArm.jewelServo.setDirection(Servo.Direction.REVERSE);
            jewelKnockoutArm.getJewelServo().setDirection(Servo.Direction.FORWARD);
            Thread.sleep(2000);
            telemetry.addData("Position:", jewelKnockoutArm.getJewelArmPosition());
            //    telemetry.update();
            // Move the Arm Down
            jewelKnockoutArm.setJewelArmPosition(1.0);
            Thread.sleep(2000);

            Thread.sleep(2000);
            //    telemetry.update();

            jewelKnockoutArm.setJewelArmPosition(0.5);
            Thread.sleep(2000);


            //jewelKnockoutArm.jewelServo.setDirection(direction);

        } catch (Exception e) {
            telemetry.addData("Exception:", e);
            telemetry.update();
        }

        // Wait for 2 seconds
        try {
            Thread.sleep(3000);
        } catch(Exception e) {

        }
    }
}


*/