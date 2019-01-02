package org.firstinspires.ftc.team13180;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.team13180.RoboNavigator;

import java.util.List;

import static java.lang.Thread.sleep;
import static org.firstinspires.ftc.robotcore.external.tfod.TfodRoverRuckus.LABEL_GOLD_MINERAL;

public class PositionTensorFlowObjectDetection {
    private LinearOpMode opMode;
    private RoboNavigator robotNavigator;
    private GoldTensorFlowObjectDetection tensorFlow;

    private double NAVIGATER_POWER = 0.6;
    private int MIDDLE_VALUE = 640; // Frame is 1280 X 720
    private int TOO_CLOSE_HEIGHT_VALUE = 640; // Frame is 1280 X 720, so height is more that 640 , then it is too close.

    public PositionTensorFlowObjectDetection(LinearOpMode _opMode,
                                      RoboNavigator _roboNavigator,
                                      GoldTensorFlowObjectDetection _tensorFlow) {
        this.opMode = _opMode;
        this.robotNavigator = _roboNavigator;
        this.tensorFlow = _tensorFlow;
    }

    public void init(LinearOpMode opMode) {
    }


    /**
     * @param goldTensorFlowObjectDetection
     * @return Moves robot back if it is too close to the mineral
     */
    public boolean isTooClose(GoldTensorFlowObjectDetection goldTensorFlowObjectDetection) {
        List<Recognition> Recognitions = tensorFlow.getRecognitions();
        for (Recognition recognition : Recognitions) {
            if (recognition.getHeight() >= 640) {
                return true;
            } else {
                break;
            }
        }

        return false;
    }

    // TODO: Check the logic
    public boolean isTooLeft(GoldTensorFlowObjectDetection goldTensorFlowObjectDetection) {
        List<Recognition> recognitions = tensorFlow.getRecognitions();

        if (recognitions.size() == 3) {
            //TODO
            for (Recognition recognition : recognitions) {
                if (recognition.getWidth() <= 640) {
                    return true;
                } else {
                    break;
                }
            }
        } else if (recognitions.size() == 2) {
            for (Recognition recognition : recognitions) {
                if (recognition.getLeft() <= 640) {
                    return true;
                } else {
                    break;
                }
            }
        } else if (recognitions.size() == 1) {
            for (Recognition recognition : recognitions) {

                if (recognition.getLeft() <= 640) {
                    return true;
                } else {
                    break;
                }
            }

        }
        return false;
    }

    // TODO: Check the logic
    public boolean isTooRight(GoldTensorFlowObjectDetection goldTensorFlowObjectDetection) {
        List<Recognition> recognitions = tensorFlow.getRecognitions();

        if (recognitions.size() == 3) {
            for (Recognition recognition : recognitions) {
                if (recognition.getWidth() >= 640) {
                    return true;
                } else {
                    break;
                }
            }
        } else if (recognitions.size() == 2) {
            for (Recognition recognition : recognitions) {
                if (recognition.getLeft() >= 640) {
                    return true;
                } else {
                    break;
                }
            }
        } else if (recognitions.size() == 1) {
            for (Recognition recognition : recognitions) {

                if (recognition.getLeft() >= 640) {
                    return true;
                } else {
                    break;
                }
            }

        }

        return false;
    }

  /*  public void setPosition(GoldTensorFlowObjectDetection goldTensorFlowObjectDetection){
               if(isTooClose(goldTensorFlowObjectDetection)== true) {
                   //move robot back
                   robotNavigator.encoderDrive(RoboNavigator.DIRECTION.BACKWARD, NAVIGATER_POWER, 2, 10000);
               }
               if(isTooLeft(goldTensorFlowObjectDetection)== true){
                   //move robot right
                   robotNavigator.encoderDrive(RoboNavigator.DIRECTION.SHIFT_RIGHT, NAVIGATER_POWER, 2, 10000);

               }
               if(isTooRight(goldTensorFlowObjectDetection)== true){
                   //move robot left
                   robotNavigator.encoderDrive(RoboNavigator.DIRECTION.SHIFT_LEFT, NAVIGATER_POWER, 2, 10000);

               }

    }
    */

    /**
     *
     * @return
     */
    public boolean goForTheGold() {
        List<Recognition> recognitions = null;

        // reset the timeout time and start motion.
        ElapsedTime runtime = new ElapsedTime();

        runtime.reset();
        int timeoutMs = 20000;

        while ((runtime.seconds() < timeoutMs)) {
            recognitions = tensorFlow.getRecognitions();
            Recognition gold = null;

            // Find the gold Recognition object
            for (Recognition recognition : recognitions) {
                if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                    gold = recognition;
                    opMode.telemetry.addData("Found Gold:", "Label=%s L=%f T=%f R=%f B=%f W=%f H=%f C=%f ImgW=%d ImgH=%d",
                            recognition.getLabel(), recognition.getLeft(), recognition.getTop(), recognition.getRight(), recognition.getBottom(),
                            recognition.getWidth(), recognition.getHeight(), recognition.getConfidence(), recognition.getImageWidth(),
                            recognition.getImageHeight());
                    break;
                }
            }

            // Frame is 1280 X 720
            if (gold != null) {
                if (gold.getWidth() >= 1100 || gold.getHeight() >= 600) {
                    // Too close hit and exit
                    opMode.telemetry.addData("Too close hit and exit:", "");
                    robotNavigator.encoderDrive(RoboNavigator.DIRECTION.SHIFT_LEFT, NAVIGATER_POWER, 20, 10000);
                    break;
                } else {
                    centerTheGold(gold);
                    // move forward 5 cms
                    opMode.telemetry.addData("move forward 5 cms:", "");
                    robotNavigator.encoderDrive(RoboNavigator.DIRECTION.SHIFT_LEFT, NAVIGATER_POWER, 5, 10000);

                }
            } else {
                // adjustments to find gold
                opMode.telemetry.addData("Turning right:", "");
                robotNavigator.turnRightTime(NAVIGATER_POWER, 1000);
            }
            opMode.telemetry.update();
            opMode.sleep(1000);
        }

        opMode.telemetry.addData("Returning from gotForGold:", "");
        return true;
    }

    public void centerTheGold(Recognition gold) {
        float centerOfGold = gold.getLeft() + gold.getWidth() / 2;

        if (centerOfGold <= 600) {
            //move a little to right
            // TODO: the move right should be a function of the distance of gold center from the center line
            robotNavigator.encoderDrive(RoboNavigator.DIRECTION.BACKWARD, NAVIGATER_POWER, 1, 10000);
            opMode.telemetry.addData("move a little to right:", "");
        } else if (centerOfGold >= 680) {
            // move a little to the left
            // TODO: the move right should be a function of the distance of gold center from the center line
            robotNavigator.encoderDrive(RoboNavigator.DIRECTION.FORWARD, NAVIGATER_POWER, 1, 10000);
            opMode.telemetry.addData("move a little to left:", "");
        } else {
            // move forward by 2cm
            // TODO: the move right should be a function of the distance of gold center from the center line
            robotNavigator.encoderDrive(RoboNavigator.DIRECTION.SHIFT_LEFT, NAVIGATER_POWER, 5, 10000);
            opMode.telemetry.addData("move forward by 2cm", "");
        }
        return;
    }

}
