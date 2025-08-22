package org.firstinspires.ftc.teamcode.commands;

public class Commands {
    public static WaitCommands wait;
    public static ConfigCommands config;
    public static DriveCommands drive;
    public static AutoCommands auto;

    public static void initialize() {
        wait = new WaitCommands();
        config = new ConfigCommands();
        drive = new DriveCommands();
        auto = new AutoCommands();
    }
}
