package org.firstinspires.ftc.team13180s3;
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
@TeleOp(name="FieldPositioning", group="manualmode")
public class FieldPositioning extends LinearOpMode {
    private RoboNavigator robonav;
    BNO055IMU imu;
    Orientation pos;
    double Turnpower=0.9;
    double MovePower=0.6;
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
            if(Math.abs(x)>0 || Math.abs(y)>0) {          //when direction inputted
                double res = Math.toDegrees(robonav.getAngle(x, y)); //gets principal angle of joystick
                double mult=Math.sqrt(x*x+y*y);
                if (res >= 90) {
                    res -= 90;
                }
                else {
                    res += 270;
                }
                res=360-res;
                //makes principal angle into actual angle in which joystick was turned
                pos = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES); //get robot position
                double cur = Double.parseDouble(formatAngle(pos.angleUnit, pos.firstAngle)); //gets z angle (heading) in double format
                if ((res - JoystickMargin) >= cur || cur >= (res + JoystickMargin)) {    // if robot hasn't turned till the right position yet (+- some margin)
                    RoboNavigator.DIRECTION Direction = RoboNavigator.DIRECTION.TURN_LEFT; //initalizes direction for encoderdrive to left
                    double TurnAng = 0; //initializes angle to 0
                    if (res > cur) {
                        double tRight = res - cur;
                        double tLeft = (360 - res) + (cur);
                        Direction = (tRight <= tLeft) ? RoboNavigator.DIRECTION.TURN_RIGHT : RoboNavigator.DIRECTION.TURN_LEFT;
                        TurnAng = (tRight <= tLeft) ? tRight : tLeft;
                    } else if (cur > res) {
                        double tLeft = cur - res;
                        double tRight = (360 - cur) + res;
                        Direction = (tRight <= tLeft) ? RoboNavigator.DIRECTION.TURN_RIGHT : RoboNavigator.DIRECTION.TURN_LEFT;
                        TurnAng = (tRight <= tLeft) ? tRight : tLeft;
                    }
                    // code above finds out which direction is best to turn in left or right, and if so how much
                    robonav.encoderDrive(Direction, Turnpower, TurnAng, 10000); //inputs acquired angles into encoderdrive, turns exactly to the point where it should
                }
                robonav.moveForward(MovePower*mult); //goes forward till gamepad joystick is let go
            }
        }

    }


    String formatAngle(AngleUnit angUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angUnit, angle));
    }

    String formatDegrees(double degrees){
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }
}
