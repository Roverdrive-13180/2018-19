package org.firstinspires.ftc.team13180;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * This is autonomous program which does following.
 * 1. When init is pressed, it will initialize lander, graber, navigator and TEnsorflow module
 * 2. Hang the robot on clamp and wait for game to start (i.e. press "Start" only when asked)
 * 3. Robo lander will bring robo down and then move left, forward , right to come our of latch.
 * 4. Robot will find Gold mineral and hit.
 * 5. Do partial parking on Crator side.
 */

@Autonomous(name="Autonomous_Crater", group="autonomusGroup1")
public class Autonomous_Crater extends LinearOpMode {

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
            robotNavigator.encoderDrive(RoboNavigator.DIRECTION.FORWARD, NAVIGATER_POWER, 40, 10000);

            // Move 90 degree to move camera in front
            robotNavigator.encoderDrive(RoboNavigator.DIRECTION.TURN_RIGHT,NAVIGATER_POWER, 90, 10000);


            positionTFOD.goForTheGoldNew(false);


            // TODO: After hitting the gold, do following if we are on Crator side
            // We can goto Depot or We can park (touch the parking)
            // As this time, we are doing the partial parking on crator side
            // TODO: Measure distance and correct it and test.
            robotNavigator.encoderDrive(RoboNavigator.DIRECTION.BACKWARD,NAVIGATER_POWER,47,10000);

            sleep(1000);

        } catch (Exception e) {
            telemetry.addData("Exception:", e);
            telemetry.update();
        }
        }
    }
;