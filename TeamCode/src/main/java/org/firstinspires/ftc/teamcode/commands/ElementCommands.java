package org.firstinspires.ftc.teamcode.commands;

import static org.firstinspires.ftc.teamcode.commands.Commands.wait;
import static org.firstinspires.ftc.teamcode.subsystems.Subsystems.element;

import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.SelectCommand;

public class ElementCommands {
    public Command invert() {
        return completeWrist(element::invert);
    }

    public Command midvert() {
        return completeWrist(element::midvert);
    }
    
    public Command revert() { //Wrist down
        return completeWrist(element::revert);
    }
    
    public Command in() {
        return complete(element::in, 0);
    }

    public Command out() {
        return complete(element::out, 0.4);
    }
    
    public Command hold() {
        return complete(element::hold, 0);
    }

    public Command stop() {
        return complete(element::stop, 0);
    }

    public Command open() {
        return completeClaw(element::open);
    }

    public Command close() {
        return completeClaw(element::close);
    }

    private Command completeClaw(Runnable runnable) {
        return complete(runnable, 0.4);
    }
    
    private Command completeWrist(Runnable runnable) {
        return complete(runnable, 0);
    }
    
    private Command complete(Runnable runnable, double seconds) {
        return new SelectCommand(
            () -> new InstantCommand(runnable, element)
        ).andThen(
            wait.seconds(seconds)
        );
    }
}
