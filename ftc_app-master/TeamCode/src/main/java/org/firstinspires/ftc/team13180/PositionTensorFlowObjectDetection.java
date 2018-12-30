package org.firstinspires.ftc.team13180;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.team13180.RoboNavigator;

public class PositionTensorFlowObjectDetection {
    private RoboNavigator robotNavigator;
    private double NAVIGATER_POWER = 0.6;

    public void init(LinearOpMode opMode){
        robotNavigator = new RoboNavigator(opMode);
        robotNavigator.init();

    }
    




    public boolean isTooClose(GoldTensorFlowObjectDetection goldTensorFlowObjectDetection) {
        return false;

    }
    public boolean isTooLeft(GoldTensorFlowObjectDetection goldTensorFlowObjectDetection){
        return  false;
    }

    public boolean isTooRight(GoldTensorFlowObjectDetection goldTensorFlowObjectDetection){
        return false;

    }

    public void setPosition(GoldTensorFlowObjectDetection goldTensorFlowObjectDetection){
               if(isTooClose(goldTensorFlowObjectDetection)== true) {
                   //move robot back
                   robotNavigator.encoderDrive(RoboNavigator.DIRECTION.BACKWARD, NAVIGATER_POWER, 24, 10000);
               }
               if(isTooLeft(goldTensorFlowObjectDetection)== true){
                   //move robot right
                   robotNavigator.encoderDrive(RoboNavigator.DIRECTION.SHIFT_RIGHT, NAVIGATER_POWER, 24, 10000);


               }
               if(isTooRight(goldTensorFlowObjectDetection)== true){
                   //move robot left
                   robotNavigator.encoderDrive(RoboNavigator.DIRECTION.SHIFT_LEFT, NAVIGATER_POWER, 24, 10000);

               }

    }

}





