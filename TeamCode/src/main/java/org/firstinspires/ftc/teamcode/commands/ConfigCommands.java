package org.firstinspires.ftc.teamcode.commands;

import static org.firstinspires.ftc.teamcode.commands.Commands.drive;
import static org.firstinspires.ftc.teamcode.game.Config.config;
import static org.firstinspires.ftc.teamcode.game.Location.BASKET_DEPOSIT;
import static org.firstinspires.ftc.teamcode.game.Location.OBSERVATION_SAMPLE;
import static org.firstinspires.ftc.teamcode.game.Location.SUBMERSIBLE_DEPOSIT;
import static org.firstinspires.ftc.teamcode.opmodes.OpMode.gamepad2;
import static java.lang.Math.atan2;
import static java.lang.Math.toDegrees;

import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.SelectCommand;

import org.firstinspires.ftc.teamcode.subsystems.ConfigSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.Subsystems;

public class ConfigCommands {
    public Command setEditable(boolean editable) {
        return complete(
            () -> Subsystems.config.setEditable(editable)
        );
    }

    public Command changeItem(ConfigSubsystem.Change change) {
        return complete(
            () -> Subsystems.config.changeItem(change)
        );
    }

    public Command changeValue(ConfigSubsystem.Change change) {
        return complete(
            () -> Subsystems.config.changeValue(change)
        );
    }
}
