package org.firstinspires.ftc.teamcode.controls;

import static com.seattlesolvers.solverslib.gamepad.GamepadKeys.Button.A;
import static com.seattlesolvers.solverslib.gamepad.GamepadKeys.Button.B;
import static com.seattlesolvers.solverslib.gamepad.GamepadKeys.Button.START;
import static com.seattlesolvers.solverslib.gamepad.GamepadKeys.Button.X;
import static com.seattlesolvers.solverslib.gamepad.GamepadKeys.Button.Y;
import static com.seattlesolvers.solverslib.gamepad.GamepadKeys.Trigger.LEFT_TRIGGER;
import static com.seattlesolvers.solverslib.gamepad.GamepadKeys.Trigger.RIGHT_TRIGGER;
import static org.firstinspires.ftc.teamcode.commands.Commands.auto;
import static org.firstinspires.ftc.teamcode.game.Config.config;
import static org.firstinspires.ftc.teamcode.opmodes.OpMode.gamepad1;

import com.seattlesolvers.solverslib.command.button.Trigger;

public class AutoControl {
    public AutoControl() {
        gamepad1.getGamepadButton(START).negate()
            .and(gamepad1.getGamepadButton(X))
            .whenActive(auto.elementIntake(false));

        gamepad1.getGamepadButton(START).negate()
            .and(gamepad1.getGamepadButton(A))
            .and(new Trigger(() -> !config.interrupt))
            .whenActive(auto.elementIntake(true));

        gamepad1.getGamepadButton(START).negate()
            .and(gamepad1.getGamepadButton(Y))
            .whenActive(auto.elementDeposit(false));

        gamepad1.getGamepadButton(START).negate()
            .and(gamepad1.getGamepadButton(B))
            .and(new Trigger(() -> !config.interrupt))
            .whenActive(auto.elementDeposit(true));

        new Trigger(() -> gamepad1.getTrigger(LEFT_TRIGGER) * gamepad1.getTrigger(RIGHT_TRIGGER) > 0.25)
            .whenActive(auto.ascend());
    }
}
