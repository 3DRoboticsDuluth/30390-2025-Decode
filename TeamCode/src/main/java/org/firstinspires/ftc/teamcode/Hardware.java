package org.firstinspires.ftc.teamcode;

import static com.qualcomm.hardware.lynx.LynxModule.BulkCachingMode.MANUAL;
import static com.seattlesolvers.solverslib.hardware.motors.Motor.GoBILDA.RPM_312;
import static com.seattlesolvers.solverslib.hardware.motors.Motor.GoBILDA.RPM_43;
import static com.seattlesolvers.solverslib.hardware.motors.Motor.GoBILDA.RPM_435;
import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.RADIANS;

import android.util.Log;

import com.acmerobotics.roadrunner.ftc.Encoder;
import com.acmerobotics.roadrunner.ftc.LynxFirmware;
import com.acmerobotics.roadrunner.ftc.OverflowEncoder;
import com.acmerobotics.roadrunner.ftc.RawEncoder;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.adaptations.ftclib.MotorEx;
import org.firstinspires.ftc.teamcode.adaptations.ftclib.MotorGroup;

import java.util.List;

public class Hardware {
    public HardwareMap hardwareMap;

    public List<LynxModule> modules;

    public VoltageSensor batteryVoltageSensor;

    public IMU imu;
    public YawPitchRollAngles imuAngles;
    public AngularVelocity imuVelocities;

    public MotorGroup drive;
    public MotorEx driveFrontLeft;
    public MotorEx driveFrontRight;
    public MotorEx driveBackLeft;
    public MotorEx driveBackRight;
    public Encoder odometryLeft;
    public Encoder odometryCenter;
    public Encoder odometryRight;

    public Hardware(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

        LynxFirmware.throwIfModulesAreOutdated(hardwareMap);

        modules = hardwareMap.getAll(LynxModule.class);

        for (LynxModule module : modules) {
            module.setBulkCachingMode(MANUAL);
        }

        batteryVoltageSensor = hardwareMap.voltageSensor.iterator().next();

        imu = hardwareMap.get(IMU.class, "imu");

        drive = new MotorGroup(
            driveFrontLeft = new MotorEx(hardwareMap, "driveFrontLeft", RPM_435),
            driveFrontRight = new MotorEx(hardwareMap, "driveFrontRight", RPM_435),
            driveBackLeft = new MotorEx(hardwareMap, "driveBackLeft", RPM_435),
            driveBackRight = new MotorEx(hardwareMap, "driveBackRight", RPM_435)
        );

        odometryLeft = new OverflowEncoder(new RawEncoder(hardwareMap.get(DcMotorEx.class, "driveBackLeft")));
        odometryCenter = new OverflowEncoder(new RawEncoder(hardwareMap.get(DcMotorEx.class, "driveFrontRight")));
        odometryRight = new OverflowEncoder(new RawEncoder(hardwareMap.get(DcMotorEx.class, "driveBackRight")));

        pivotLeft = new MotorEx(hardwareMap, "pivotLeft", RPM_43);
        pivotRight = new MotorEx(hardwareMap, "pivotRight", RPM_43);
        pivotEncoder = hardwareMap.get(DcMotorEx.class, "driveFrontLeft");
        
        slideLower = new MotorEx(hardwareMap, "slideLower", RPM_312);
        slideUpper = new MotorEx(hardwareMap, "slideUpper", RPM_312);

        elementWrist = hardwareMap.get(Servo.class, "elementWrist");
        elementWheel = hardwareMap.get(CRServo.class,"elementWheel");
        elementClaw = hardwareMap.get(Servo.class, "elementClaw");

        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        
        lightsLeft = hardwareMap.get(RevBlinkinLedDriver.class, "lightsLeft");
        lightsRight = hardwareMap.get(RevBlinkinLedDriver.class, "lightsRight");
        lightsIndicator = hardwareMap.get(Servo.class, "lightsIndicator");
        
        clearBulkCache();
        updateImuAnglesAndVelocities();
    }

    public void clearBulkCache() {
        for (LynxModule module : modules) {
            module.clearBulkCache();
        }
    }

    public void updateImuAnglesAndVelocities() {
        try {
            imuAngles = imu.getRobotYawPitchRollAngles();
            imuVelocities = imu.getRobotAngularVelocity(RADIANS);
        } catch (Exception e) {
            Log.e("Hardware", "Error attempting to get IMU angles and velocities.", e);
        }
    }
}
