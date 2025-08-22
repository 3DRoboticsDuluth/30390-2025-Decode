package org.firstinspires.ftc.teamcode.controls;

import static com.seattlesolvers.solverslib.gamepad.GamepadKeys.Button.A;
import static com.seattlesolvers.solverslib.gamepad.GamepadKeys.Button.B;
import static com.seattlesolvers.solverslib.gamepad.GamepadKeys.Button.BACK;
import static com.seattlesolvers.solverslib.gamepad.GamepadKeys.Button.DPAD_DOWN;
import static com.seattlesolvers.solverslib.gamepad.GamepadKeys.Button.DPAD_LEFT;
import static com.seattlesolvers.solverslib.gamepad.GamepadKeys.Button.DPAD_RIGHT;
import static com.seattlesolvers.solverslib.gamepad.GamepadKeys.Button.DPAD_UP;
import static com.seattlesolvers.solverslib.gamepad.GamepadKeys.Button.X;
import static com.seattlesolvers.solverslib.gamepad.GamepadKeys.Button.Y;
import static org.firstinspires.ftc.teamcode.commands.Commands.config;
import static org.firstinspires.ftc.teamcode.game.Sample.ALLIANCE;
import static org.firstinspires.ftc.teamcode.game.Sample.NEUTRAL;
import static org.firstinspires.ftc.teamcode.opmodes.OpMode.gamepad1;
import static org.firstinspires.ftc.teamcode.opmodes.OpMode.gamepad2;
import static org.firstinspires.ftc.teamcode.subsystems.ConfigSubsystem.Change.NEXT;
import static org.firstinspires.ftc.teamcode.subsystems.ConfigSubsystem.Change.PREV;
import static java.lang.Math.abs;
import static java.lang.Math.hypot;

import com.seattlesolvers.solverslib.command.button.Trigger;

import org.firstinspires.ftc.teamcode.game.Basket;
import org.firstinspires.ftc.teamcode.game.Chamber;
import org.firstinspires.ftc.teamcode.game.Config;

public class ConfigControl {

    private static final double JOYSTICK_THRESHOLD = 0.5;

    public ConfigControl() {
        gamepad1.getGamepadButton(BACK)
            .or(gamepad2.getGamepadButton(BACK))
            .whenActive(config.setEditable(true))
            .whenInactive(config.setEditable(false));

        gamepad1.getGamepadButton(DPAD_UP)
            .or(gamepad2.getGamepadButton(DPAD_UP))
            .whenActive(config.changeItem(PREV));

        gamepad1.getGamepadButton(DPAD_DOWN)
            .or(gamepad2.getGamepadButton(DPAD_DOWN))
            .whenActive(config.changeItem(NEXT));

        gamepad1.getGamepadButton(DPAD_LEFT)
            .or(gamepad2.getGamepadButton(DPAD_LEFT))
            .whenActive(config.changeValue(PREV));

        gamepad1.getGamepadButton(DPAD_RIGHT)
            .or(gamepad2.getGamepadButton(DPAD_RIGHT))
            .whenActive(config.changeValue(NEXT));

        // Sample: Neutral vs Alliance

        gamepad2.getGamepadButton(BACK)
            .and(new Trigger(() -> Config.config.started))
            .toggleWhenActive(config.setSample(NEUTRAL), config.setSample(ALLIANCE));

    }
}
