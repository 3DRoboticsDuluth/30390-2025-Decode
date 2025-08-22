package org.firstinspires.ftc.teamcode.commands;

import static org.firstinspires.ftc.teamcode.commands.Commands.auto;
import static org.firstinspires.ftc.teamcode.commands.Commands.drive;
import static org.firstinspires.ftc.teamcode.commands.Commands.element;
import static org.firstinspires.ftc.teamcode.commands.Commands.pivot;
import static org.firstinspires.ftc.teamcode.commands.Commands.slide;
import static org.firstinspires.ftc.teamcode.commands.Commands.vision;
import static org.firstinspires.ftc.teamcode.commands.Commands.wait;
import static org.firstinspires.ftc.teamcode.game.Config.config;
import static org.firstinspires.ftc.teamcode.game.Element.SAMPLE;
import static org.firstinspires.ftc.teamcode.game.Location.BASKET_DEPOSIT;
import static org.firstinspires.ftc.teamcode.game.Location.OBSERVATION_SAMPLE;
import static org.firstinspires.ftc.teamcode.game.Location.OBSERVATION_SPECIMEN;
import static org.firstinspires.ftc.teamcode.game.Location.SPIKE_INTAKE;
import static org.firstinspires.ftc.teamcode.game.Location.SUBMERSIBLE_INTAKE;
import static org.firstinspires.ftc.teamcode.game.Position.ENTER;
import static org.firstinspires.ftc.teamcode.game.Position.EXIT;
import static org.firstinspires.ftc.teamcode.game.Position.TARGET;
import static org.firstinspires.ftc.teamcode.subsystems.SlideSubsystem.INTAKE_IN;
import static org.firstinspires.ftc.teamcode.subsystems.SlideSubsystem.INTAKE_OUT;
import static org.firstinspires.ftc.teamcode.subsystems.TimingSubsystem.playTimer;

import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.SelectCommand;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;

import java.util.function.Supplier;

public class AutoCommands {
    public Command execute() {
        return new InstantCommand();
//        return auto.delayStart().andThen(
//            auto.followGeneratedPath()
//        );
    }
//
//    public Command followGeneratedPath() {
//        return new SelectCommand(
//            () -> {
//                PathBuilder builder = new PathBuilder();
//
//                builder
//                    .addPath(
//                        new BezierLine(
//                            new Point(134.900, 81.000, Point.CARTESIAN),
//                            new Point(108.250, 81.000, Point.CARTESIAN)
//                        )
//                    )
//                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
//                    .addPath(
//                        new BezierCurve(
//                            new Point(108.250, 81.000, Point.CARTESIAN),
//                            new Point(132.600, 104.000, Point.CARTESIAN),
//                            new Point(118.000, 120.500, Point.CARTESIAN)
//                        )
//                    )
//                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(0))
//                    .addPath(
//                        new BezierLine(
//                            new Point(118.000, 120.500, Point.CARTESIAN),
//                            new Point(127.000, 125.500, Point.CARTESIAN)
//                        )
//                    )
//                    .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(45))
//                    .addPath(
//                        new BezierLine(
//                            new Point(127.000, 125.500, Point.CARTESIAN),
//                            new Point(118.000, 132.000, Point.CARTESIAN)
//                        )
//                    )
//                    .setLinearHeadingInterpolation(Math.toRadians(45), Math.toRadians(180))
//                    .addPath(
//                        new BezierLine(
//                            new Point(118.000, 132.000, Point.CARTESIAN),
//                            new Point(126.200, 132.500, Point.CARTESIAN)
//                        )
//                    )
//                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(3))
//                    .addPath(
//                        new BezierCurve(
//                            new Point(126.200, 132.500, Point.CARTESIAN),
//                            new Point(110.000, 106.800, Point.CARTESIAN),
//                            new Point(103.600, 127.800, Point.CARTESIAN)
//                        )
//                    )
//                    .setLinearHeadingInterpolation(Math.toRadians(3), Math.toRadians(107))
//                    .addPath(
//                        new BezierCurve(
//                            new Point(103.600, 127.800, Point.CARTESIAN),
//                            new Point(110.000, 106.800, Point.CARTESIAN),
//                            new Point(126.200, 132.500, Point.CARTESIAN)
//                        )
//                    )
//                    .setLinearHeadingInterpolation(Math.toRadians(107), Math.toRadians(0))
//                    .addPath(
//                        new BezierCurve(
//                            new Point(126.200, 132.500, Point.CARTESIAN),
//                            new Point(82.200, 120.000, Point.CARTESIAN),
//                            new Point(127.800, 120.900, Point.CARTESIAN)
//                        )
//                    )
//                    .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
//                    .addPath(
//                        new BezierLine(
//                            new Point(127.800, 120.900, Point.CARTESIAN),
//                            new Point(108.250, 80.100, Point.CARTESIAN)
//                        )
//                    )
//                    .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(180)).build();
//                return drive.follow(builder);
//            }
//        );
//    }


//public class AutoCommands {
//    public Command execute() {
//        return auto.delayStart().andThen(
//            auto.depositPreLoadedSpecimen(),
//            auto.handleSpikeSamples(),
//            auto.scoreSamplesOrSpecimens(),
//            auto.park()
//        );
//    }
    
    public Command delayStart() {
        return new SelectCommand(
            () -> wait.seconds(config.delay)
        );
    }
}
