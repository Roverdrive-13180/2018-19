package org.firstinspires.ftc.team13180;
/**Made by Rohan Gulati
 * Does Autonomous Crater then drops Team Marker into Depot
 */

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class FullAutonomousCrater extends LinearOpMode {
    private RoboNavigator robotNavigator;
    private RoboLander lander;
    private RoboGrabber grabber;
    private PositionTensorFlowObjectDetection fullpositionTFOD;

    // TODO: Measure distance
    // private ConceptVuforiaNavRoverRuckus vuforia;
    private GoldTensorFlowObjectDetection fulltensorFlow;

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
        fulltensorFlow = new GoldTensorFlowObjectDetection();
        fulltensorFlow.init(this);

        fullpositionTFOD = new PositionTensorFlowObjectDetection(this, robotNavigator, fulltensorFlow);
        fullpositionTFOD.init(this);


        telemetry.addData("Status:", "initialized");
        telemetry.update();

        waitForStart();

        fulltensorFlow.activate();

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


            fullpositionTFOD.goForTheGoldNew(false,true);


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
