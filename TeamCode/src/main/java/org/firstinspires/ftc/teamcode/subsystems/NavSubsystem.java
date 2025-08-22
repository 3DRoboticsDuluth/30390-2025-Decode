package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.game.Config.config;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.game.Alliance;
import org.firstinspires.ftc.teamcode.game.Pose;
import org.firstinspires.ftc.teamcode.game.Side;

/** @noinspection UnaryPlus, unused */
@Config
public class NavSubsystem {
    public static double TILE_WIDTH = 23.5;
    public static double ROBOT_LENGTH = 14;
    public static double ROBOT_WIDTH = 14;
    public static double IN_PER_TICK = 0.00196752029;
    public static double VEL_SCALAR = 0.7;
    public static double MAX_ACCEL_SCALAR = 1;
    public static double MIN_ACCEL_SCALAR = 0.7;
    public static double ANG_SCALAR = 1;
    public static double MAX_VEL = 90 * VEL_SCALAR;
    public static double MAX_ACCEL = MAX_VEL * MAX_ACCEL_SCALAR;
    public static double MIN_ACCEL = -MAX_ACCEL * MIN_ACCEL_SCALAR;
    public static double MAX_ANG_VEL = PI * ANG_SCALAR;
    public static double MAX_ANG_ACCEL = MAX_ANG_VEL;

    public Pose getStartPose() {
        return config.side == null || config.side == Side.UNKNOWN ||
            config.alliance == null || config.alliance == Alliance.UNKNOWN ?
            new Pose(0, 0, 0) :
            createPose(
                config.side.sign * 7,
                config.alliance.sign * 3 * TILE_WIDTH,
                config.alliance.sign * toRadians(-90),
                Axial.BACK
            );
    }

    public enum Axial {
        FRONT(+1), CENTER(0), BACK(-1);
        
        public final int signum;
        
        Axial(int signum) {
            this.signum = signum;
        }
    }

    public enum Lateral {
        LEFT(+1), CENTER(0), RIGHT(-1);
        
        public final int signum;
        
        Lateral(int signum) {
            this.signum = signum;
        }
    }

    public Pose createPose(Pose pose, Axial axial) {
        return createPose(pose, axial, Lateral.CENTER);
    }

    public Pose createPose(Pose pose, Lateral lateral) {
        return createPose(pose, Axial.CENTER, lateral);
    }

    public Pose createPose(Pose pose, Axial axial, Lateral lateral) {
        return createPose(pose.x, pose.y, pose.heading, axial, lateral);
    }

    public Pose createPose(double x, double y, double heading) {
        return createPose(x, y, heading, Axial.CENTER, Lateral.CENTER);
    }

    public Pose createPose(double x, double y, double heading, double axialOffset, double lateralOffset) {
        return createPose(x, y, heading, Axial.CENTER, Lateral.CENTER, axialOffset, lateralOffset);
    }

    public Pose createPose(double x, double y, double heading, Axial axial) {
        return createPose(x, y, heading, axial, Lateral.CENTER);
    }

    public Pose createPose(double x, double y, double heading, Axial axial, double axialOffset) {
        return createPose(x, y, heading, axial, Lateral.CENTER, axialOffset, 0);
    }

    public Pose createPose(double x, double y, double heading, Lateral lateral) {
        return createPose(x, y, heading, Axial.CENTER, lateral);
    }

    public Pose createPose(double x, double y, double heading, Lateral lateral, double lateralOffset) {
        return createPose(x, y, heading, Axial.CENTER, lateral, 0, lateralOffset);
    }

    public Pose createPose(double x, double y, double heading, Axial axial, Lateral lateral) {
        return createPose(x, y, heading, axial, lateral, 0, 0);
    }

    public Pose createPose(double x, double y, double heading, Axial axial, Lateral lateral, double axialOffset, double lateralOffset) {
        double axialHeading = normalizeHeading(heading);
        axialOffset -= axial.signum * ROBOT_LENGTH / 2;
        x += cos(axialHeading) * axialOffset;
        y += sin(axialHeading) * axialOffset;

        double lateralHeading = normalizeHeading(heading + PI / 2);
        lateralOffset -= lateral.signum * ROBOT_WIDTH / 2;
        x += cos(lateralHeading) * lateralOffset;
        y += sin(lateralHeading) * lateralOffset;
        
        return new Pose(jitter(x), jitter(y), jitter(heading));
    }

    public double normalizeHeading(double heading) {
        if (heading > +PI) heading -= PI * 2;
        if (heading < -PI) heading += PI * 2;
        return heading;
    }
    
    private double jitter(double value) {
        return value * (1 + Math.random() / 1000);
    }
}
