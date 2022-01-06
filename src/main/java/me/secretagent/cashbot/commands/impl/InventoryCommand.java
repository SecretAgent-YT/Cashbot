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
import java.util.Map;

public class InventoryCommand extends Command {

    @Override
    public CommandData getData() {
        return new CommandData("inventory", getDescription());
    }

    @Override
    public String getDescription() {
        return "Checks your inventory.";
    }

    @Override
    public void onCalled(CommandContext context) {
        User user = context.getUser();
        CashBotUser cashBotUser = CashBot.API.getUser(user);
        Embed embed = new Embed("Inventory of " + user.getName());
        embed.setColor(Color.GREEN);
        for (Map.Entry<Material, Long> entry : cashBotUser.getInventory().entrySet()) {
            if (entry.getValue() != 0) {
                long quantity = entry.getValue();
                Material material = entry.getKey();
                embed.appendDescription(material.getEmote() + " " + material.getName() + " - `" + quantity + "`\n");
            }
        }
        context.reply(embed.build(), false).queue();
    }

}
