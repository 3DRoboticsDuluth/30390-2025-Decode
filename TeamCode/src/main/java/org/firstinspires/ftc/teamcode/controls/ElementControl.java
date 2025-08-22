package org.firstinspires.ftc.teamcode.controls;

import static com.seattlesolvers.solverslib.gamepad.GamepadKeys.Button.LEFT_BUMPER;
import static com.seattlesolvers.solverslib.gamepad.GamepadKeys.Button.RIGHT_BUMPER;
import static com.seattlesolvers.solverslib.gamepad.GamepadKeys.Button.Y;
import static com.seattlesolvers.solverslib.gamepad.GamepadKeys.Trigger.LEFT_TRIGGER;
import static com.seattlesolvers.solverslib.gamepad.GamepadKeys.Trigger.RIGHT_TRIGGER;
import static org.firstinspires.ftc.teamcode.commands.Commands.element;
import static org.firstinspires.ftc.teamcode.opmodes.OpMode.gamepad2;

import com.seattlesolvers.solverslib.command.button.Trigger;

public class ElementControl {
    private static final double TRIGGER_THRESHOLD = 0.5;

    public ElementControl() {
        gamepad2.getGamepadButton(Y).and(
            gamepad2.getGamepadButton(LEFT_BUMPER))
            .toggleWhenActive(element.invert(), element.revert());

        gamepad2.getGamepadButton(Y).and(
            gamepad2.getGamepadButton(RIGHT_BUMPER))
            .toggleWhenActive(element.close(), element.open());

        new Trigger(() -> gamepad2.getTrigger(RIGHT_TRIGGER) > TRIGGER_THRESHOLD)
            .whileActiveOnce(element.in())
            .whenInactive(element.stop());

        new Trigger(() -> gamepad2.getTrigger(LEFT_TRIGGER) > TRIGGER_THRESHOLD)
            .whileActiveOnce(element.out())
            .whenInactive(element.stop());
    }
}
