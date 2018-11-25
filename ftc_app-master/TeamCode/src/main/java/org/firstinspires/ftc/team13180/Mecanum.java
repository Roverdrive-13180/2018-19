package org.firstinspires.ftc.team13180;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;



@TeleOp
public class Mecanum extends LinearOpMode {

    private DcMotor Topl;
    private DcMotor Topr;
    private DcMotor Rearl;
    private DcMotor Rearr;
    private DcMotor Lander;
    private DcMotor Grabber;
    private DcMotor Winch;
    private Servo Tilt1;
    private Servo Tilt2;



    @Override
    public void runOpMode() {
        //imu = hardwareMap.get(Gyroscope.class, "imu");
        Topl = hardwareMap.get(DcMotor.class, "Topl");
        Topr = hardwareMap.get(DcMotor.class, "Topr");
        Rearl = hardwareMap.get(DcMotor.class, "Rearl");
        Rearr = hardwareMap.get(DcMotor.class, "Rearr");
        Lander= hardwareMap.get(DcMotor.class, "Lander");
        Grabber = hardwareMap.get(DcMotor.class, "Grabber");
        Winch = hardwareMap.get(DcMotor.class, "Winch");
        Tilt1 = hardwareMap.get(Servo.class, "Tilt1");
        Tilt2 = hardwareMap.get(Servo.class, "Tilt1");




        waitForStart();
        double tgtPower1 = 0;
        double tgtPower2 = 0;
        double tgtPower3 = 0;
        double tgtPower4 = 0;
        double tgtPower5 = 0;
        double multiPlier1 = 1;
        double multiPlier2 = 1;
        double RbrPower=0;
        double WnchPower=0;
        double Rbrmultiplier=0.5;
        double Wnchmultiplier=0.5;
        while (opModeIsActive()) {
            // Gamepad 1            // MotorTest1
            tgtPower1 = multiPlier1 * this.gamepad1.left_stick_y;
            Topl.setPower(tgtPower1);
            // MotorTest2
            tgtPower2 = -multiPlier1 * this.gamepad1.right_stick_y;
            //tgtPower1 = - multiPlier1 * this.gamepad1.right_stick_y;
            Topr.setPower(tgtPower2);
            tgtPower3 = multiPlier1 * this.gamepad1.left_stick_y;
            Rearl.setPower(tgtPower3);
            // MotorTest2
            tgtPower4 = -multiPlier1 * this.gamepad1.right_stick_y;
            //tgtPower1 = - multiPlier1 * this.gamepad1.right_stick_y;
            Rearr.setPower(tgtPower4);
            if(gamepad1.left_stick_y!=0){
                tgtPower1=multiPlier1*this.gamepad1.left_stick_y;
                Topl.setPower(tgtPower1);
                //__________
                tgtPower2=-multiPlier1*this.gamepad1.left_stick_y;
                Topr.setPower(tgtPower2);
                //__________
                tgtPower3=multiPlier1*this.gamepad1.left_stick_y;
                Rearl.setPower(tgtPower3);
                //__________
                tgtPower4=-multiPlier1*this.gamepad1.left_stick_y;
                Rearr.setPower(tgtPower4);
                //__________
            }
            if(gamepad1.left_stick_x!=0){
                tgtPower1=-multiPlier1*this.gamepad1.left_stick_x;
                Topl.setPower(tgtPower1);
                //__________
                tgtPower2=-multiPlier1*this.gamepad1.left_stick_x;
                Topr.setPower(tgtPower2);
                //__________
                tgtPower3=multiPlier1*this.gamepad1.left_stick_x;
                Rearl.setPower(tgtPower3);
                //__________
                tgtPower4=multiPlier1*this.gamepad1.left_stick_x;
                Rearr.setPower(tgtPower4);
                //__________
            }
            if(gamepad1.right_stick_x!=0){
                tgtPower1=multiPlier1*this.gamepad1.left_stick_x;
                Topl.setPower(tgtPower1);
                //__________
                tgtPower2=multiPlier1*this.gamepad1.left_stick_x;
                Topr.setPower(tgtPower2);
                //__________
                tgtPower3=multiPlier1*this.gamepad1.left_stick_x;
                Rearl.setPower(tgtPower3);
                //__________
                tgtPower4=multiPlier1*this.gamepad1.left_stick_x;
                Rearr.setPower(tgtPower4);
                //__________
            }
            if(gamepad2.right_trigger!=0) {
                Lander.setPower(tgtPower5);
                tgtPower5=-multiPlier2*this.gamepad2.right_trigger;

            }

            RbrPower=Rbrmultiplier*this.gamepad2.right_stick_y;
            Grabber.setPower(RbrPower);

            if (gamepad2.left_stick_y !=0){
                Winch.setPower(WnchPower);
                WnchPower = Wnchmultiplier*this.gamepad2.left_stick_y;
            }

            Tilt1.setPosition(0);
            Tilt2.setPosition(0);
            if(gamepad2.dpad_up){
                Tilt1.setPosition(0.5);
                Tilt2.setPosition(0.5);

            }
            if(gamepad2.dpad_down){
                Tilt2.setPosition(0);
                Tilt2.setPosition(0);
            }




            // Gamepad 2
            // Gamepad 2 is used to control Motor 3 and 4

            //tgtPower2 = - multiPlier2 * this.gamepad2.right_stick_y;
            //motorTest4.setPower(tgtPower2);
            // check to see if we need to move the servo.
            // Gamepad 2 is used to control Servo Motor 1 and 2


        }
    }
}







