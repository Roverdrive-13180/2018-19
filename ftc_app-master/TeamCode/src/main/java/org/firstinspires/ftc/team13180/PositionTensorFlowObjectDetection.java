package org.firstinspires.ftc.team13180;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.team13180.RoboNavigator;

public class PositionTensorFlowObjectDetection {




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
               }
               if(isTooLeft(goldTensorFlowObjectDetection)== true){
                   //move robot right

               }
               if(isTooRight(goldTensorFlowObjectDetection)== true){
                   //move robot left
               }
               }
    }





