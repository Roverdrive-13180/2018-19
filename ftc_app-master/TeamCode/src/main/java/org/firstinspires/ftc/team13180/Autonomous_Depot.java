package org.firstinspires.ftc.team13180;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Shivam Adeshara on 11/24/2018.
 * This is autonomous program which does following.
 * 1. When init is pressed, it will start motor and pull robot up
 * 2. Hang the robot on clamp and wait for game to start (i.e. press "Start" only when asked)
 * 3. Robot RoboLander motor will stop and spring tension will bring robot down and clamp should be in middle.
 * 4. Robot will move lelf (check and see if we need to move right) and then move forward
 * 5. Find Gold Mineral position and have logic to  move the robot to Gold mineral (based on Left, Center or Right values)
 */

@Autonomous(name="Autonomous_Depot", group="autonomusGroup1")
public class Autonomous_Depot extends LinearOpMode {

    private RoboNavigator robotNavigator;
    private RoboLander lander;
    private RoboGrabber grabber;
    private PositionTensorFlowObjectDetection positionTFOD;

    // TODO: Measure distance
    // private ConceptVuforiaNavRoverRuckus vuforia;
    private GoldTensorFlowObjectDetection tensorFlow;

    private double LANDER_POWER = 1.0;
    private double NAVIGATER_POWER = 0.9;

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

        try {
            // lower lander
            robotNavigator.moveForward(0.1);
            lander.encoderDrive(LANDER_POWER, 23, 10000);
            robotNavigator.stopMotor();
            robotNavigator.encoderDrive(RoboNavigator.DIRECTION.BACKWARD, 0.5, 2, 10000);
            robotNavigator.encoderDrive(RoboNavigator.DIRECTION.SHIFT_LEFT, NAVIGATER_POWER, 15, 10000);
            robotNavigator.encoderDrive(RoboNavigator.DIRECTION.FORWARD, NAVIGATER_POWER, 25, 10000);
            robotNavigator.encoderDrive(RoboNavigator.DIRECTION.SHIFT_RIGHT, NAVIGATER_POWER, 15, 10000);
            // Move forward 15 cms (TODO: measure)
            robotNavigator.encoderDrive(RoboNavigator.DIRECTION.FORWARD, NAVIGATER_POWER, 15, 10000);
            // Move 90 degree to move camera in front
            robotNavigator.encoderDrive(RoboNavigator.DIRECTION.TURN_RIGHT,NAVIGATER_POWER, 90, 10000);


            //positionTFOD.goForTheGold(true);
            positionTFOD.goForTheGoldNew(true);

           robotNavigator.encoderDrive(RoboNavigator.DIRECTION.FORWARD,NAVIGATER_POWER,65,10000);
           // Drop Marquee
           grabber.spinIn();
           // Move back
           robotNavigator.encoderDrive(RoboNavigator.DIRECTION.BACKWARD,NAVIGATER_POWER,20,10000);
           sleep(1000);
           grabber.stopGrabber();

        } catch (Exception e) {
            telemetry.addData("Exception:", e);
            telemetry.update();
        }
    }
}