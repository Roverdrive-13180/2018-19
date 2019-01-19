package org.firstinspires.ftc.team13180;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Shivam Adeshara on 01/16/2019.
 * This is autonomous program which does following.
 * 1. When init is pressed, it will initialize lander, graber, navigator and TEnsorflow module
 * 2. Hang the robot on clamp and wait for game to start (i.e. press "Start" only when asked)
 * 3. Robo lander will bring robo down and then move left, forward , right to come our of latch.
 * 4. Robot will find Gold mineral and hit.
 * 5. Do partial parking on Crator side.
 */

@Autonomous(name="Autonomous_Crator", group="autonomusGroup1")
public class Autonomous_Crater extends LinearOpMode {

    private RoboNavigator robotNavigator;
    private RoboLander lander;
    private RoboGrabber grabber;
    private PositionTensorFlowObjectDetection positionTFOD;

    // TODO: Measure distance
    // private ConceptVuforiaNavRoverRuckus vuforia;
    private GoldTensorFlowObjectDetection tensorFlow;

    private double LANDER_POWER = 0.5;
    private double NAVIGATER_POWER = 0.8;

    @Override
    public void runOpMode() {

        robotNavigator = new RoboNavigator(this);
        robotNavigator.init();

        lander = new RoboLander(this);
        lander.init();

        grabber = new RoboGrabber(this);
        grabber.init();


        // Take picture to determine the robot location after landing.
        // vuforia = new ConceptVuforiaNavRoverRuckus();

        // Initialize the gold mineral position detector
        tensorFlow = new GoldTensorFlowObjectDetection();
        tensorFlow.init(this);

        positionTFOD = new PositionTensorFlowObjectDetection(this, robotNavigator, tensorFlow);
        positionTFOD.init(this);


        telemetry.addData("Status:", "initialized");
        telemetry.update();

        waitForStart();

        tensorFlow.activate();

        sleep(200);

        try {
            // lower lander
            robotNavigator.moveForward(0.1);
            lander.encoderDrive(LANDER_POWER, 23, 10000);
            robotNavigator.stopMotor();
            robotNavigator.encoderDrive(RoboNavigator.DIRECTION.BACKWARD, NAVIGATER_POWER, 3, 10000);
            robotNavigator.encoderDrive(RoboNavigator.DIRECTION.SHIFT_LEFT, NAVIGATER_POWER, 10, 10000);
            robotNavigator.encoderDrive(RoboNavigator.DIRECTION.FORWARD, NAVIGATER_POWER, 20, 10000);
            robotNavigator.encoderDrive(RoboNavigator.DIRECTION.SHIFT_RIGHT, NAVIGATER_POWER, 10, 10000);
            robotNavigator.encoderDrive(RoboNavigator.DIRECTION.TURN_RIGHT,NAVIGATER_POWER, 90, 10000);

            // Find Gold and hit it.
            positionTFOD.goForTheGold();

            // TODO: After hitting the gold, do following if we are on Crator side
            // We can goto Depot or We can park (touch the parking)
            // As this time, we are doing the partial parking on crator side
            // TODO: Measure distance and correct it and test.
            robotNavigator.encoderDrive(RoboNavigator.DIRECTION.FORWARD,NAVIGATER_POWER,30,10000);
            sleep(1000);

        } catch (Exception e) {
            telemetry.addData("Exception:", e);
            telemetry.update();
        }
    }
}