package org.firstinspires.ftc.team13180;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.team13180.RoboNavigator;

import java.util.List;

import static org.firstinspires.ftc.robotcore.external.tfod.TfodRoverRuckus.LABEL_GOLD_MINERAL;

public class PositionTensorFlowObjectDetection {
    private LinearOpMode opMode;
    private RoboNavigator robotNavigator;
    private GoldTensorFlowObjectDetection tensorFlow;

    private double NAVIGATER_POWER = 0.6;

    PositionTensorFlowObjectDetection(LinearOpMode _opMode,
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

    public boolean goForTheGold() {
        List<Recognition> recognitions = null;

        while (true) {
            recognitions = tensorFlow.getRecognitions();
            Recognition gold = null;

            for (Recognition recognition : recognitions) {
                if (recognition.getLabel().equals(LABEL_GOLD_MINERAL))
                    gold = recognition;
            }

            if (gold != null) {
                if (gold.getWidth() >= 1100 || gold.getHeight() >= 600) {
                    // Too close hit and exit
                    break;
                } else
                    centerTheGold(gold);
            } else {
                // adjustments to find gold
                ;
            }
            return false;
        }
        return true;
    }

        public boolean centerTheGold (Recognition gold) {
            float centerOfGold = gold.getLeft() + gold.getWidth() / 2;

            if (centerOfGold <= 600) {
                //move a little to right
                ;
            }
            else if (centerOfGold >= 680) {
                // move a little to the left
                ;
            }
            else
                // move forward by 2cm
            ;
            return true;
        }

}








