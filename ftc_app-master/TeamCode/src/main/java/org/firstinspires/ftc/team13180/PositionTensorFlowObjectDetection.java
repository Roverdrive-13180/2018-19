//Made by Rohan Gulati and a little bit from Shivy
package org.firstinspires.ftc.team13180;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.robot.Robot;
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

    private double NAVIGATER_POWER = 0.9;
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
    public boolean goForTheGold(boolean isDepot) {
        List<Recognition> recognitions = null;

        // reset the timeout time and start motion.
        ElapsedTime runtime = new ElapsedTime();

        runtime.reset();
        int timeoutMs = 30000;

        int seek_angle = 15;
        int seek_angle_increment = 10;
        RoboNavigator.DIRECTION last_direction = RoboNavigator.DIRECTION.TURN_LEFT;

        boolean gold_found = false;
        boolean ready_to_hit = false;
        while ((runtime.milliseconds() < timeoutMs)) {
            opMode.sleep(600);
            recognitions = tensorFlow.getRecognitions();
            Recognition gold = null;

            // Find the gold Recognition object
            for (Recognition recognition : recognitions) {
                if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                    gold = recognition;
                    gold_found = true;
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
                    ready_to_hit = true;
                } else {
                    centerTheGold(gold);
                    // move closer to the gold
                    opMode.telemetry.addData("move closer by 20 cms:", "");
                    robotNavigator.encoderDrive(RoboNavigator.DIRECTION.SHIFT_LEFT, NAVIGATER_POWER, 20, 10000);
                }
            } else {
                // We don't have gold in this iteration. Either we are too close or we haven't found gold yet
                if (gold_found) {
                    // Likely gold has fallen off the bottom of the frame
                    ready_to_hit = true;
                }
                else {
                    // We ha=ven't see the gold yet, seek it
                    // adjustments to find gold by oscillating by seek_angle
                    if (last_direction == RoboNavigator.DIRECTION.TURN_LEFT) {
                        last_direction = RoboNavigator.DIRECTION.TURN_RIGHT;
                        seek_angle = seek_angle + seek_angle_increment;
                    }
                    else {
                        last_direction = RoboNavigator.DIRECTION.TURN_LEFT;
                        seek_angle = 2 * seek_angle;
                    }

                    robotNavigator.encoderDrive(last_direction, NAVIGATER_POWER, seek_angle, 10000);
                }
            }
            if (ready_to_hit) {
                // Too close hit and exit
                opMode.telemetry.addData("Too close hit and exit:", "");
                robotNavigator.encoderDrive(RoboNavigator.DIRECTION.TURN_LEFT, NAVIGATER_POWER, 90, 10000);
                robotNavigator.encoderDrive(RoboNavigator.DIRECTION.FORWARD, NAVIGATER_POWER, 50, 10000);

                // Correction for seek_angle
                // Flip the direction first
                if (seek_angle > 15) {
                    if (last_direction == RoboNavigator.DIRECTION.TURN_LEFT)
                        last_direction = RoboNavigator.DIRECTION.TURN_RIGHT;
                    else
                        last_direction = RoboNavigator.DIRECTION.TURN_LEFT;

                    robotNavigator.encoderDrive(last_direction, NAVIGATER_POWER, seek_angle, 10000);

                    if (isDepot) {
                        // Turn a little more in the same direction to point to depot
                        robotNavigator.encoderDrive(last_direction, NAVIGATER_POWER, 45, 10000);
                    }
                }
                break;
            }
            opMode.telemetry.update();
        }

        opMode.telemetry.addData("Returning from gotForGold:", "");
        return true;
    }

    /**
     *
     * @return
     */
    public int goForTheGoldNew(boolean isDepot) {
        List<Recognition> recognitions = null;

        double distance = 40;

        int i = 0;
        for (i = 0; i < 3; i++) {
            opMode.sleep(2000);
            recognitions = tensorFlow.getUpdatedRecognitions();
            Recognition gold = null;
            if (recognitions != null && recognitions.size() == 1 && recognitions.get(0).getLabel().equals(LABEL_GOLD_MINERAL)) {
                gold = recognitions.get(0);
                opMode.telemetry.addData("Found Gold:", "Label=%s L=%f T=%f R=%f B=%f W=%f H=%f C=%f ImgW=%d ImgH=%d", //FOUND THE GOLD HERE, DETECTED AND BREAK, "" IS WHATEVER IT IS RN
                        gold.getLabel(), gold.getLeft(), gold.getTop(), gold.getRight(), gold.getBottom(),
                        gold.getWidth(), gold.getHeight(), gold.getConfidence(), gold.getImageWidth(),
                        gold.getImageHeight());
                break;
            }
            if (i == 0) {
                robotNavigator.encoderDrive(RoboNavigator.DIRECTION.FORWARD, NAVIGATER_POWER, distance, 10000);
            }
            if (i == 1) {
                robotNavigator.encoderDrive(RoboNavigator.DIRECTION.BACKWARD, NAVIGATER_POWER, distance * 2 -5 , 10000);
                i++;
                break;
            }
        }

        // hit and exit
        opMode.telemetry.addData("Hit and exit:", "");


        if (isDepot) {
            // Move back left 90 degree
            robotNavigator.encoderDrive(RoboNavigator.DIRECTION.TURN_LEFT, NAVIGATER_POWER, 90, 10000);


            // Move forward and hit the gold
            robotNavigator.encoderDrive(RoboNavigator.DIRECTION.FORWARD, NAVIGATER_POWER, 25, 10000);


            robotNavigator.encoderDrive(RoboNavigator.DIRECTION.FORWARD, NAVIGATER_POWER, 45, 10000);

            // Turn to point to depot
            if (i == 1) {
                robotNavigator.encoderDrive(RoboNavigator.DIRECTION.TURN_LEFT, NAVIGATER_POWER, 45, 10000);
            }
            else if (i == 2) {
                robotNavigator.encoderDrive(RoboNavigator.DIRECTION.TURN_RIGHT, NAVIGATER_POWER, 35, 10000);

            }

        }
            else {
                robotNavigator.encoderDrive(RoboNavigator.DIRECTION.TURN_LEFT, NAVIGATER_POWER, 90, 10000);
                if (i == 0 || i == 1) {
                    robotNavigator.encoderDrive(RoboNavigator.DIRECTION.SHIFT_RIGHT, NAVIGATER_POWER, 15, 10000);
                }
                //Move Backward to hit gold

                robotNavigator.encoderDrive(RoboNavigator.DIRECTION.FORWARD, NAVIGATER_POWER, 25, 10000);
            }



        opMode.telemetry.addData("Returning from gotForGold:", "");
        opMode.telemetry.update();
        return i;

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
