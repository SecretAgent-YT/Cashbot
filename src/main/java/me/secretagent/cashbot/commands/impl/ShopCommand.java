package me.secretagent.cashbot.commands.impl;

import me.secretagent.cashbot.CashBot;
import me.secretagent.cashbot.api.CashBotUser;
import me.secretagent.cashbot.commands.Command;
import me.secretagent.cashbot.commands.CommandContext;
import me.secretagent.cashbot.item.Material;
import me.secretagent.cashbot.util.Embed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import java.awt.*;
import java.util.HashMap;

public class ShopCommand extends Command {

    @Override
    public CommandData getData() {
        return new CommandData("shop", getDescription());
    }

    @Override
    public String getDescription() {
        return "Lists all the items available for purchase.";
    }

    @Override
    public void onCalled(CommandContext context) {
        Embed embed = new Embed("Shop");
        embed.setColor(Color.GREEN);
        for (Material material : Material.values()) {
            embed.appendDescription(material.getEmote() + " " + material.getName() + ": `" + material.getPrice() + "$`\n");
        }
        context.reply(embed.build(), false).queue();
    }

}
