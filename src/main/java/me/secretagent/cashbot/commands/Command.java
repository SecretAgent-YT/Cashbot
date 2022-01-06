package me.secretagent.cashbot.commands;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public abstract class Command {

    public abstract CommandData getData();

    public void onCalled(CommandContext context) {

    }

    public String getDescription() {
        return "No description.";
    }

}
