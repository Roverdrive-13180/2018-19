package org.firstinspires.ftc.team13180;

import android.support.annotation.Nullable;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;

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

            while (opModeIsActive()) {
                String goldLocation = "";
                if (gamepad1.a) {
                    testMineralLocationPrint();
                    telemetry.update();
                    sleep(5000);
                }
            }

            // Shutdown Tensorflow as we are not going to use in manual mode.
            tensorFlow.shutdown();

            // Move to Depot
        } catch (Exception e) {
            //telemetry.addData("Exception:", e);
            //telemetry.update();
        }
    }

    @Nullable
    private String findGoldMineral() {
        String goldLocation;
        goldLocation = tensorFlow.getGoldLocation();
        if (goldLocation.equals("Center")) {
            telemetry.addData("GoldLocation:", "Center");
        } else if (goldLocation.equals("Left")) {
            telemetry.addData("GoldLocation:", "Left");
        } else if (goldLocation.equals(("Right"))) {
            telemetry.addData("GoldLocation:", "Right");
        } else {
            telemetry.addData("GoldLocation:", "Unknown");
        }
        return goldLocation;
    }

    public void testMineralLocationPrint() {
        List<Recognition> updatedRecognitions = tensorFlow.getUpdatedRecognitions();
        if (updatedRecognitions != null) {
            telemetry.addData("size:","%d", updatedRecognitions.size());

            if (updatedRecognitions.size() == 3) {
                for (Recognition recognition : updatedRecognitions) {
                    telemetry.addData("Result:","Label=%s L=%s T=%s R=%s B=%s W=%f H=%f C=%f", recognition.getLabel(), recognition.getLeft(),recognition.getTop(),recognition.getRight(),recognition.getBottom(),recognition.getWidth(),recognition.getHeight(),recognition.getConfidence());
                }
            } else if (updatedRecognitions.size() == 2) {
                for (Recognition recognition : updatedRecognitions) {
                    telemetry.addData("Result:","Label=%s L=%s T=%s R=%s B=%s W=%f H=%f C=%f", recognition.getLabel(), recognition.getLeft(),recognition.getTop(),recognition.getRight(),recognition.getBottom(),recognition.getWidth(),recognition.getHeight(),recognition.getConfidence());
                }
            } else if (updatedRecognitions.size() == 1) {
                for (Recognition recognition : updatedRecognitions) {
                    telemetry.addData("Result:","Label=%s L=%s T=%s R=%s B=%s W=%f H=%f C=%f", recognition.getLabel(), recognition.getLeft(),recognition.getTop(),recognition.getRight(),recognition.getBottom(),recognition.getWidth(),recognition.getHeight(),recognition.getConfidence());
                }
            }
        } else {
            telemetry.addData("Not Found any object", "");
        }
    }
}