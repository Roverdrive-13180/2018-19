package org.firstinspires.ftc.team13180;
//Made by Rohan Gulati -8/25/19
import android.renderscript.ScriptGroup;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

import java.util.Locale;
@TeleOp(name="FieldPositioningShift", group="manualmode")
public class FieldPositioningShift extends LinearOpMode {
    private RoboNavigator robonav;
    private LinearOpMode opMode;
    BNO055IMU imu;
    Orientation pos;
    double Turnpower=0.9;
    double MovePower=0.5;
    final double JoystickMargin=10;
    @Override
    public void runOpMode(){
        robonav=new RoboNavigator(this);
        robonav.init();

        BNO055IMU.Parameters param = new BNO055IMU.Parameters();
        param.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        param.calibrationDataFile = "BNO055IMUCalibration.json";
        param.loggingEnabled      = true;
        param.loggingTag          = "IMU";
        imu = hardwareMap.get(BNO055IMU.class, "imu123");
        imu.initialize(param);
        waitForStart();
        while (opModeIsActive()){
            telemetry.update();
            double x=gamepad1.left_stick_x;
            double y=gamepad1.left_stick_y;
            if(gamepad1.left_bumper){
                robonav.turnLeft(0.4);

            }
            else if(gamepad1.right_bumper){
                robonav.turnRight(0.4);
            }
            else if(Math.abs(x)>0.15 || Math.abs(y)>0.15) {          //when direction inputted
                double res = Math.toDegrees(robonav.getAngle(x, y)); //gets principal angle of joystick
                pos = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES); //get robot position
                double cur = Double.parseDouble(formatAngle(pos.angleUnit, pos.firstAngle)); //gets z angle (heading) in double format
                double mult=Math.sqrt(x*x+y*y);
                double ang=ImuToPrincipal(cur);
                double finalangle=ReceiveDifference(ang,res);
                finalangle=Math.toRadians(finalangle);
                robonav.opMode.telemetry.addData ("current", "shiftRight (power=%f)", ang);
                robonav.opMode.telemetry.addData ("joystick:", "shiftRight (power=%f)", res);
                robonav.opMode.telemetry.addData ("difference:", "shiftRight (power=%f)", finalangle);
                robonav.AnyMecanum(mult*Math.cos(finalangle),mult*Math.sin(finalangle));


            }
            else {
                robonav.stopMotor();
            }
        }

    }
    double ImuToPrincipal(double ang){
        return ang+90;
        /*
        ang=360-ang;
        if(ang>270){
            ang-=270;
        }
        else{
            ang+=90;
        }
        return ang;
 */
    }
    double ReceiveDifference(double CurPos,double FinPos){
        if(CurPos>FinPos){
            CurPos-=FinPos;
            CurPos=ImuToPrincipal(CurPos);
            return CurPos;
        }
        else{
            FinPos-=CurPos;
            FinPos=360-FinPos;
            FinPos=ImuToPrincipal(FinPos);
            return FinPos;
        }
    }
    String formatAngle(AngleUnit angUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angUnit, angle));
    }

    String formatDegrees(double degrees){
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }
}
