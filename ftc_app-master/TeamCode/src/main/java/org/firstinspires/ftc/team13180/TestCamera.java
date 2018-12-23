package org.firstinspires.ftc.team13180;

import android.support.annotation.Nullable;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Raj Raghuwanshi on 12/23/2018.
 * This is Test TeleOp program which does following.
 * 1. When button A is pressed, the camera will look for the gold mineral
 * 2. If found, it will print the value
 */

@TeleOp(name="Test_Camera", group="manualmode")
public class TestCamera extends LinearOpMode {

    private GoldTensorFlowObjectDetection tensorFlow;

    @Override
    public void runOpMode() {
        // Take picture to determine the robot location after landing.
        // Initialize the gold mineral position detector
        tensorFlow = new GoldTensorFlowObjectDetection();
        tensorFlow.init(this);

        telemetry.addData("Status:", "initialized");
        telemetry.update();

        waitForStart();

        tensorFlow.activate();

        sleep(2000);

        try {
            int count = 0;
            String goldLocation = "";

            while (opModeIsActive()) {

                if (gamepad1.a) {


                    // Get the gold mineral position (Left, Center or Right).
                    goldLocation = findGoldMineral();

                }
                telemetry.update();
            }


            // Shutdown Tensorflow as we are not going to use in manual mode.
            tensorFlow.shutdown();

            // Move to Depot

        } catch (Exception e) {
            telemetry.addData("Exception:", e);
            telemetry.update();
        }
    }

    @Nullable
    private String findGoldMineral() {
        String goldLocation;
        goldLocation = tensorFlow.getGoldLocation() ;

        if(goldLocation.equals("Center")){
            telemetry.addData("GoldLocation:", "Center");

        } else if(goldLocation.equals("Left")) {

            telemetry.addData("GoldLocation:", "Left");

        } else if (goldLocation.equals(("Right"))){

            telemetry.addData("GoldLocation:", "Right");

        }
        else {

            telemetry.addData("GoldLocation:", "Unknown");
        }
        return goldLocation;
    }
}