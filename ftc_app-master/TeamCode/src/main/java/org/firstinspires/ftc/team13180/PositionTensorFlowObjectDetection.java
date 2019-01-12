package org.firstinspires.ftc.team13180;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.team13180.RoboNavigator;

import java.util.List;

import static java.lang.Math.abs;
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
                if (recognition.getLeft() <= 640) {
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
                if (recognition.getLeft() >= 640) {
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

  /*public void setPosition(GoldTensorFlowObjectDetection goldTensorFlowObjectDetection){
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
        int timeoutMs = 10000;

        while ((runtime.milliseconds() < timeoutMs)) {
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
                if (gold.getBottom() >= 680) {
                    // Too close hit and exit
                    opMode.telemetry.addData("Too close hit and exit:", "");
                    robotNavigator.encoderDrive(RoboNavigator.DIRECTION.SHIFT_RIGHT, NAVIGATER_POWER, 50, 10000);
                    break;
                } else {
                    centerTheGold(gold);
                    // move forward 5 cms
                    opMode.telemetry.addData("move forward 10 cms:", "");
                    robotNavigator.encoderDrive(RoboNavigator.DIRECTION.SHIFT_RIGHT, NAVIGATER_POWER, 10, 10000);
                }
            } else {
                // adjustments to find gold
                opMode.telemetry.addData("Turning right:", "");
                robotNavigator.encoderDrive(RoboNavigator.DIRECTION.TURN_RIGHT,NAVIGATER_POWER,40,10000);
                opMode.sleep(500);
                robotNavigator.encoderDrive(RoboNavigator.DIRECTION.TURN_LEFT,NAVIGATER_POWER,80,10000);
                opMode.sleep(500);

            }
            opMode.telemetry.update();
            opMode.sleep(2000);
        }

        opMode.telemetry.addData("Returning from gotForGold:", "");
        return true;
    }

    public void centerTheGold(Recognition gold) {
        float centerOfGold = gold.getLeft() + gold.getWidth() / 2;

        // Calculate the cms per pixel for current gold
        float cms_per_pixel = 5 / gold.getHeight();
        float pixels_to_move = abs(640 - centerOfGold);

        float cms_to_move = cms_per_pixel * pixels_to_move;

        if (centerOfGold <= 600) {
            // move to left
            robotNavigator.encoderDrive(RoboNavigator.DIRECTION.BACKWARD, NAVIGATER_POWER, cms_to_move, 10000);
            opMode.telemetry.addData("move to left:", "%s", cms_to_move);
        } else if (centerOfGold >= 680) {
            // move to the right
            robotNavigator.encoderDrive(RoboNavigator.DIRECTION.FORWARD, NAVIGATER_POWER, cms_to_move, 10000);
            opMode.telemetry.addData("move to right:", "%s", cms_to_move);
        }
        return;
    }

}
