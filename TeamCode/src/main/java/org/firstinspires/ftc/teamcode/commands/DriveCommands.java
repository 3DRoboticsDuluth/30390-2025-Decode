package org.firstinspires.ftc.teamcode.commands;

import static org.firstinspires.ftc.teamcode.adaptations.roadrunner.Pose2dUtil.hypot;
import static org.firstinspires.ftc.teamcode.commands.Commands.wait;
import static org.firstinspires.ftc.teamcode.game.Config.config;
import static org.firstinspires.ftc.teamcode.game.Element.SAMPLE;
import static org.firstinspires.ftc.teamcode.game.Location.BASKET_DEPOSIT;
import static org.firstinspires.ftc.teamcode.game.Location.FIELD_INTAKE;
import static org.firstinspires.ftc.teamcode.game.Location.OBSERVATION_PARK;
import static org.firstinspires.ftc.teamcode.game.Location.OBSERVATION_SAMPLE;
import static org.firstinspires.ftc.teamcode.game.Location.OBSERVATION_SPECIMEN;
import static org.firstinspires.ftc.teamcode.game.Location.SPIKE_INTAKE;
import static org.firstinspires.ftc.teamcode.game.Location.SUBMERSIBLE_ASCENT;
import static org.firstinspires.ftc.teamcode.game.Location.SUBMERSIBLE_DEPOSIT;
import static org.firstinspires.ftc.teamcode.game.Location.SUBMERSIBLE_INTAKE;
import static org.firstinspires.ftc.teamcode.game.Location.SUBMERSIBLE_PARK;
import static org.firstinspires.ftc.teamcode.game.Position.ENTER;
import static org.firstinspires.ftc.teamcode.game.Position.TARGET;
import static org.firstinspires.ftc.teamcode.opmodes.OpMode.gamepad1;
import static org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem.TO_FAR;
import static org.firstinspires.ftc.teamcode.subsystems.NavSubsystem.TILE_WIDTH;
import static org.firstinspires.ftc.teamcode.subsystems.Subsystems.drive;
import static org.firstinspires.ftc.teamcode.subsystems.Subsystems.nav;
import static org.firstinspires.ftc.teamcode.subsystems.Subsystems.vision;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toDegrees;
import static java.lang.Math.toRadians;

import com.acmerobotics.roadrunner.Pose2d;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.PathBuilder;
import com.pedropathing.pathgen.Point;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.command.SelectCommand;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.adaptations.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.game.Alliance;
import org.firstinspires.ftc.teamcode.game.Location;
import org.firstinspires.ftc.teamcode.game.Position;
import org.firstinspires.ftc.teamcode.game.Side;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.DoubleSupplier;
import java.util.function.Function;

/** @noinspection DataFlowIssue*/
@SuppressWarnings({"unused"})
public class DriveCommands {
    private final HashMap<Location, Function<Pose2d, Pose2d>> locationPoses =
        new HashMap<Location, Function<Pose2d, Pose2d>>() {{
            put(BASKET_DEPOSIT, offset -> nav.getBasketDepositPose(offset));
            put(FIELD_INTAKE, offset -> null);
            put(OBSERVATION_SAMPLE, offset -> nav.getObservationSamplePose(offset));
            put(OBSERVATION_SPECIMEN, offset -> nav.getObservationSpecimenPose(offset));
            put(OBSERVATION_PARK, offset -> nav.getObservationParkPose(offset));
            put(SPIKE_INTAKE, offset -> nav.getSpikeIntakePose(offset));
            put(SUBMERSIBLE_INTAKE, offset -> nav.getSubmersibleIntakePose(offset));
            put(SUBMERSIBLE_DEPOSIT, offset -> nav.getSubmersibleDepositPose(offset));
            put(SUBMERSIBLE_PARK, offset -> nav.getSubmersibleParkPose(offset));
            put(SUBMERSIBLE_ASCENT, offset -> nav.getSubmersibleAscentPose(offset));
        }};
    
    private Pose2d targetPose = null;

    PathBuilder pathBuilder = new PathBuilder();

    public Command setPowerLow() {
        return complete(drive::setPowerLow);
    }

    public Command setPowerMedium() {
        return complete(drive::setPowerMedium);
    }

    public Command setPowerHigh() {
        return complete(drive::setPowerHigh);
    }

    public Command input(DoubleSupplier forward, DoubleSupplier strafe, DoubleSupplier turn) {
        return new RunCommand(
            () -> drive.inputs(
                forward.getAsDouble(),
                strafe.getAsDouble(),
                turn.getAsDouble()
            ), drive
        );
    }

    public Command toIntake(Position position) {
        return to(config.intake, position);
    }
    
    public Command toDeposit(Position position) {
        return to(config.deposit, position);
    }

    public Command toObservationPark(Position position) {
        return to(OBSERVATION_PARK, position);
    }

    public Command toSubmersiblePark(Position position) {
        return to(SUBMERSIBLE_PARK, position);
    }
    
    public Command toAscent(Position position) {
        return to(SUBMERSIBLE_ASCENT, position);
    }

    public Command toElement() {
        return config.intake.element == SAMPLE ? toSample() : toSpecimen();
    }

    public Command toSample() {
        return new SelectCommand(
            () -> vision.elementPose == null || hypot(vision.elementPose) > 30 || config.auto ?
                wait.noop() : to(
                nav.createPose(
                    config.pose.position.x,
                    config.pose.position.y,
                    config.pose.heading.plus(
                        vision.elementPose.heading.toDouble()
                    ).toDouble()
                )
            )
        );
    }

    public Command toSpecimen() {
        return new SelectCommand(
            () -> vision.elementPose == null ?
                wait.noop() :
                strafe(vision.elementPose.position.y)
        );
    }

    public Command toStart() {
        return to(nav.getStartPose());
    }

    public Command toNearAprilTag() {
        return new SelectCommand(
            () -> to(
                nav.createPose(
                    signum(config.pose.position.x) * TILE_WIDTH * 2,
                    signum(config.pose.position.y) * TILE_WIDTH * 2,
                    toRadians(90 - signum(config.pose.position.x) * 90)
                )
            )
        );
    }

    public Command to(Location location, Position position) {
        return updatePrecision(location, position).andThen(
            to(locationPoses.get(location).apply(
                location.offsets.get(position)
            ))
        );
    }

    public Command to(double x, double y, double heading) {
        return to(
            nav.createPose(x, y, heading)
        );
    }
    
    public boolean toFar(Pose2d pose) {
        return config.teleop &&
            config.pose != null &&
            pose != null &&
            abs(hypot(pose, config.pose)) > TO_FAR;
    }
    
    public boolean toFarToDeposit() {
        return toFar(
            locationPoses.get(config.deposit).apply(
                config.deposit.offsets.get(ENTER)
            )
        );
    }
    
    public boolean toFarToIntake() {
        return toFar(
            locationPoses.get(config.intake).apply(
                config.intake.offsets.get(ENTER)
            )
        );
    }

    public Command shake() {
        return rumble().andThen(
            turn(-10),
            turn(10)
        );
    }

    public Command rumble() {
        return rumble1().alongWith(rumble2());
    }

    public Command rumble1() {
        return rumble(gamepad1, 1, 1);
    }

    public Command rumble2() {
        return rumble(gamepad1, 1, 1);
    }

    public Command rumble1(double intensity, double seconds) {
        return rumble(gamepad1, intensity, seconds);
    }

    public Command rumble2(double intensity, double seconds) {
        return rumble(gamepad1, intensity, seconds);
    }

    public Command rumble(GamepadEx gamepad, double intensity, double seconds) {
        return new InstantCommand(
            () -> gamepad1.gamepad.rumble(intensity, intensity, (int)(seconds * 1000))
        );
    }

    public Command to(Pose2d pose) {
        return new SelectCommand(() ->
            pose == null || compare(config.pose, pose, true, MecanumDrive.PARAMS.errorPosition) ?
                toFar(pose) || config.alliance == Alliance.UNKNOWN || config.side == Side.UNKNOWN ?
                    shake() : wait.noop() : (
                compare(config.pose, pose, false, MecanumDrive.PARAMS.errorPosition) ?
                    turn(toDegrees(pose.heading.toDouble() - config.pose.heading.toDouble())) :
                    follow(pb -> pb.addPath(new BezierCurve(
                        new Point(config.pose.position.x, config.pose.position.y),
                        new Point(pose.position.x, pose.position.y)
                    )).setLinearHeadingInterpolation(config.pose.heading.toDouble(), pose.heading.toDouble()))
            )
        );
    }

    public Command forward(double distance) {
        double heading = config.pose.heading.toDouble();
        return follow(
            pb -> pb.addPath(new BezierCurve(
                new Point(config.pose.position.x, config.pose.position.y),
                new Point(
                    config.pose.position.x + cos(heading) * distance,
                    config.pose.position.y + sin(heading) * distance
                )
            )).setLinearHeadingInterpolation(config.pose.heading.toDouble(), config.pose.heading.toDouble())
        );
    }

    public Command strafe(double distance) {
        double heading = config.pose.heading.toDouble();
        double bearing = heading + Math.toRadians(90);
        return follow(
            pb -> pb.addPath(new BezierCurve(
                new Point(config.pose.position.x, config.pose.position.y),
                new Point(
                    config.pose.position.x + cos(bearing) * distance,
                    config.pose.position.y + sin(bearing) * distance
                )
            )).setLinearHeadingInterpolation(config.pose.heading.toDouble(), config.pose.heading.toDouble())
        );
    }

    public Command turn(double heading) {
        return follow(
            pb -> pb.setConstantHeadingInterpolation(config.pose.heading.toDouble() + Math.toRadians(heading))
        );
    }

    public Command follow(Consumer<PathBuilder> pathBuilder) {
        return complete(
            () -> drive.followTrajectoryAsync(pathBuilder)
        );
    }

    private Command updatePrecision(Location location, Position position) {
        return new SelectCommand(
            () -> new InstantCommand(
                () -> {
                    MecanumDrive.PARAMS.errorTimeout = location.precise && position == TARGET ? 0.1 : -0.2;
                    MecanumDrive.PARAMS.errorPosition = location.precise && position == TARGET ? 0.5 : 2;
                    MecanumDrive.PARAMS.errorVelocity = location.precise && position == TARGET ? 0.5 : 2;
                    MecanumDrive.PARAMS.errorHeading = location.precise && position == TARGET ? 0.5 : 2;
                }
            )
        );
    }

    private static boolean compare(Pose2d expected, Pose2d actual, boolean includeHeading, double threshold) {
        return abs(expected.position.x - actual.position.x) < threshold &&
            abs(expected.position.y - actual.position.y) < threshold &&
            (!includeHeading || abs(expected.heading.toDouble() - actual.heading.toDouble()) < toRadians(threshold));
    }

    private Command complete(Runnable runnable) {
        return new SelectCommand(
            () -> new InstantCommand(runnable, drive)
        ).andThen(
            wait.until(() -> !drive.isBusy() /*|| compare(drive.drive.pose, targetPose, true, MecanumDrive.PARAMS.errorPosition)*/)
        );
    }
}
