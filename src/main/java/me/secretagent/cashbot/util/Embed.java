package me.secretagent.cashbot.util;

import net.dv8tion.jda.api.EmbedBuilder;

public class Embed extends EmbedBuilder {

    public Embed(String title) {
        setTitle(title);
        setFooter("Agent best");
    }

    public void appendFormatField(String name, String value) {
        appendDescription("**" + name + "**: `" + value + "`\n");
    }

    public void appendField(String name, String value) {
        appendDescription("**" + name + "**: " + value + "\n");
    }

}
