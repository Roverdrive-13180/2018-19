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

     //Yeeeeeeeee
    //Hello future me you are epick
     //Hello Roverdrive
    private RoboNavigator robotNavigator;
    private RoboLander lander;
    private RoboGrabber grabber;

    // TODO: Measure distance
    // private ConceptVuforiaNavRoverRuckus vuforia;
    private GoldTensorFlowObjectDetection tensorFlow;

    private double LANDER_POWER = 0.5;
    private double NAVIGATER_POWER = 0.6;

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

        telemetry.addData("Status:", "initialized");
        telemetry.update();

        waitForStart();

        tensorFlow.activate();

        sleep(2000);

        try {
            // lower lander
            lander.encoderDrive(LANDER_POWER, 23, 10000);
            robotNavigator.encoderDrive(RoboNavigator.DIRECTION.SHIFT_LEFT, NAVIGATER_POWER, 24, 10000);
            robotNavigator.encoderDrive(RoboNavigator.DIRECTION.FORWARD, NAVIGATER_POWER, 17, 10000);
            robotNavigator.encoderDrive(RoboNavigator.DIRECTION.SHIFT_RIGHT, NAVIGATER_POWER, 24, 10000);


            int count = 0;
            String goldLocation = "";

            while (opModeIsActive() && count < 5) {
                // Get the gold mineral position (Left, Center or Right).
                 goldLocation = tensorFlow.getGoldLocation() ;

                if(goldLocation.equals("Center")){
                    robotNavigator.encoderDrive(RoboNavigator.DIRECTION.FORWARD, NAVIGATER_POWER, 60,10000);
                    telemetry.addData("GoldLoction:", "Center");
                    break;
                } else if(goldLocation.equals("Left")) {
                    robotNavigator.encoderDrive(RoboNavigator.DIRECTION.FORWARD, NAVIGATER_POWER, 30, 10000);
                    robotNavigator.encoderDrive(RoboNavigator.DIRECTION.SHIFT_LEFT, NAVIGATER_POWER, 40, 10000);
                    robotNavigator.encoderDrive(RoboNavigator.DIRECTION.FORWARD, NAVIGATER_POWER, 34,10000);
                    telemetry.addData("GoldLoction:", "Left");
                    break;
                } else if (goldLocation.equals(("Right"))){
                    robotNavigator.encoderDrive(RoboNavigator.DIRECTION.FORWARD, NAVIGATER_POWER, 30, 10000);
                    robotNavigator.encoderDrive(RoboNavigator.DIRECTION.SHIFT_RIGHT, NAVIGATER_POWER, 40, 10000);
                    robotNavigator.encoderDrive(RoboNavigator.DIRECTION.FORWARD,NAVIGATER_POWER, 34,10000);
                    telemetry.addData("GoldLoction:", "Right");
                    break;
                }
                else {
                    robotNavigator.encoderDrive(RoboNavigator.DIRECTION.SHIFT_LEFT, NAVIGATER_POWER, 5,10000);
                    telemetry.addData("GoldLoction:", "Unknown");
                    count++;
                }

                telemetry.update();
            }


            // Shutdown Tensorflow as we are not going to use in manual mode.
            tensorFlow.shutdown();

            // Move to Depot
            if (goldLocation.equals("")) {
                robotNavigator.encoderDrive(RoboNavigator.DIRECTION.SHIFT_RIGHT, NAVIGATER_POWER, 5,10000);
                robotNavigator.encoderDrive(RoboNavigator.DIRECTION.FORWARD, NAVIGATER_POWER, 55+70,10000);
            }
            else if (goldLocation.equals("Center")) {
                robotNavigator.encoderDrive(RoboNavigator.DIRECTION.FORWARD, NAVIGATER_POWER, 70,10000);
            }
            else if (goldLocation.equals("Left")) {
                robotNavigator.encoderDrive(RoboNavigator.DIRECTION.FORWARD, NAVIGATER_POWER, 40,10000);
                robotNavigator.encoderDrive(RoboNavigator.DIRECTION.SHIFT_RIGHT, NAVIGATER_POWER, (count*5)+40,10000);
                robotNavigator.encoderDrive(RoboNavigator.DIRECTION.FORWARD, NAVIGATER_POWER, 30,10000);
            }
            else if (goldLocation.equals("Right")) {
                robotNavigator.encoderDrive(RoboNavigator.DIRECTION.FORWARD, NAVIGATER_POWER, 40,10000);
                robotNavigator.encoderDrive(RoboNavigator.DIRECTION.SHIFT_LEFT, NAVIGATER_POWER, (count*5)+40,10000);
                robotNavigator.encoderDrive(RoboNavigator.DIRECTION.FORWARD, NAVIGATER_POWER, 30,10000);
            }

            grabber.tiltDown();
            grabber.spinOutTime(1.0, 3000);
            robotNavigator.encoderDrive(RoboNavigator.DIRECTION.BACKWARD, NAVIGATER_POWER, 10, 10000);
            grabber.spinOutTime(1.0, 3000);
            robotNavigator.encoderDrive(RoboNavigator.DIRECTION.BACKWARD, NAVIGATER_POWER, 20, 10000);


        } catch (Exception e) {
            telemetry.addData("Exception:", e);
            telemetry.update();
        }
    }
}