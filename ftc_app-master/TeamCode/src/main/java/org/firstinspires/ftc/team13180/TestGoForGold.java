package org.firstinspires.ftc.team13180;

import android.support.annotation.Nullable;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;

/**
 * Created by Shivam Adeshara and Arnav Pradhan on 1/1/19
 * This is Test GoForGold  program which does following.
 * TODO:
 */

@TeleOp(name="Test_GoForGold", group="manualmode")
public class TestGoForGold extends LinearOpMode {

    private GoldTensorFlowObjectDetection tensorFlow;
    private PositionTensorFlowObjectDetection positionTensorFlowObjectDetection;
    private RoboNavigator robotNavigator;

    @Override
    public void runOpMode() {
        // Take picture to determine the robot location after landing.
        // Initialize the gold mineral position detector
        tensorFlow = new GoldTensorFlowObjectDetection();
        tensorFlow.init(this);

        robotNavigator = new RoboNavigator(this);
        robotNavigator.init();

        // Do not clear log
        telemetry.setAutoClear(false);
        telemetry.addData("Status:", "initialized");
        telemetry.update();

        waitForStart();

        tensorFlow.activate();

        positionTensorFlowObjectDetection = new PositionTensorFlowObjectDetection(this, robotNavigator, tensorFlow);

        sleep(2000);

        try {
            int count = 0;

            while (opModeIsActive()) {
                String goldLocation = "";
                if (gamepad1.a) {
                    positionTensorFlowObjectDetection.goForTheGold();
                    telemetry.update();
                    sleep(2000);
                }
            }

            // Shutdown Tensorflow as we are not going to use in manual mode.
            tensorFlow.shutdown();

          } catch (Exception e) {
            //telemetry.addData("Exception:", e);
            //telemetry.update();
        }
    }



    public void testMineralLocationPrint() {
        List<Recognition> updatedRecognitions = tensorFlow.getUpdatedRecognitions();
        if (updatedRecognitions != null) {
            telemetry.addData("size:","%d", updatedRecognitions.size());

            if (updatedRecognitions.size() == 3) {
                for (Recognition recognition : updatedRecognitions) {
                    telemetry.addData("Result:","Label=%s L=%f T=%f R=%f B=%f W=%f H=%f C=%f ImgW=%d ImgH=%d", recognition.getLabel(), recognition.getLeft(),recognition.getTop(),recognition.getRight(),recognition.getBottom(),recognition.getWidth(),recognition.getHeight(),recognition.getConfidence(), recognition.getImageWidth(), recognition.getImageHeight());
                }
            } else if (updatedRecognitions.size() == 2) {
                for (Recognition recognition : updatedRecognitions) {
                    telemetry.addData("Result:","Label=%s L=%f T=%f R=%f B=%f W=%f H=%f C=%f ImgW=%d ImgH=%d", recognition.getLabel(), recognition.getLeft(),recognition.getTop(),recognition.getRight(),recognition.getBottom(),recognition.getWidth(),recognition.getHeight(),recognition.getConfidence(), recognition.getImageWidth(), recognition.getImageHeight());
                }
            } else if (updatedRecognitions.size() == 1) {
                for (Recognition recognition : updatedRecognitions) {
                    telemetry.addData("Result:","Label=%s L=%f T=%f R=%f B=%f W=%f H=%f C=%f ImgW=%d ImgH=%d", recognition.getLabel(), recognition.getLeft(),recognition.getTop(),recognition.getRight(),recognition.getBottom(),recognition.getWidth(),recognition.getHeight(),recognition.getConfidence(), recognition.getImageWidth(), recognition.getImageHeight());
                }
            }
        } else {
            telemetry.addData("Not Found any object", "");
        }
    }
}