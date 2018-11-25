package org.firstinspires.ftc.team13180;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Shivam Adeshara on 12/29/2017.
 * This is autonomous program with time
 */

@Autonomous(name="Autonomous1819", group="autonomusGroup1")
public class Autonomous1819 extends LinearOpMode {
    private RobotNavigator robotNavigator;
    private ConceptTensorFlowObjectDetection tensorFlow;
    private ConceptVuforiaNavRoverRuckus vuforia;
    private Lander lander;

    @Override
    public void runOpMode() {
        double forwardPower = 0.5;
        int forwardTime = 1000;
        double leftPower = 0.5;
        int leftTime = 1000;

        double armPower = 0.25;
        int armTime = 1000;

        robotNavigator = new RobotNavigator();
        robotNavigator.init(hardwareMap);
        //
        tensorFlow = new ConceptTensorFlowObjectDetection();
        //
        vuforia = new ConceptVuforiaNavRoverRuckus();

        //
        lander = new Lander();
        lander.init(hardwareMap);

        telemetry.addData("Status:", "initialized");
        telemetry.update();

        // Wait for 1/2 seconds

        waitForStart();

        try {
            lander.moveDown(1);
            Thread.sleep(100);
            lander.stopMotor();

            //robotNavigator.shiftRightTime(0.5,1000);

            //
        } catch (Exception e) {
            telemetry.addData("Exception:", e);
            telemetry.update();
        }
    }
}