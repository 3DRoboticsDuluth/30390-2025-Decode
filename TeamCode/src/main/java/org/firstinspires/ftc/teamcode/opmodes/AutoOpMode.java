package org.firstinspires.ftc.teamcode.opmodes;

import static org.firstinspires.ftc.teamcode.commands.Commands.pedro;
import static org.firstinspires.ftc.teamcode.game.Config.config;
import static org.firstinspires.ftc.teamcode.game.Zone.INNER;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.adaptations.pedropathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.adaptations.pedropathing.constants.LConstants;
import org.firstinspires.ftc.teamcode.controls.Controls;
import org.firstinspires.ftc.teamcode.game.Side;

@Autonomous(name = "Auto")
@SuppressWarnings("unused")
public class AutoOpMode extends OpMode {
    private Follower follower;

    @Override
    public void initialize() {
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(new Pose());

        super.initialize();

        config.alliance = Alliance.UNKNOWN;
        config.side = Side.UNKNOWN;
        config.ascentZone = INNER;
        config.delay = 0;
        config.sample = null;

        Controls.initializeAuto();

        waitForStart();
        
        if (isStopRequested()) return;

//        if (config.auto && config.alliance == Alliance.UNKNOWN || config.side == Side.UNKNOWN)
//            throw new RuntimeException("Alliance and/or Side is null");

        schedule(
            pedro.execute()
        );
    }
}
