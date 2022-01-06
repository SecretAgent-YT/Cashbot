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

public class BuyCommand extends Command {

    @Override
    public CommandData getData() {
        return new CommandData("buy", getDescription()).addOption(OptionType.STRING, "item", "Item you want to purchase", true);
    }

    @Override
    public String getDescription() {
        return "Allows you to buy something from the shop.";
    }

    @Override
    public void onCalled(CommandContext context) {
        CashBotUser cashBotUser = CashBot.API.getUser(context.getUser());
        Material material = Material.fromName(context.getOption("item").getAsString());
        if (material == null) {
            Embed embed = new Embed("That item doesn't exist");
            embed.setColor(Color.RED);
            embed.setDescription("What are you doing, smh");
            context.reply(embed.build(), false).queue();
            return;
        } else if (cashBotUser.getWallet() < material.getPrice()) {
            Embed embed = new Embed("You can't afford that item");
            embed.setColor(Color.RED);
            embed.setDescription("Go get the guap before coming here again");
            context.reply(embed.build(), false).queue();
            return;
        }
        cashBotUser.addItem(material);
        cashBotUser.setWallet(cashBotUser.getWallet() - material.getPrice());
        Embed embed = new Embed("You bought " + material.getName());
        embed.setDescription("Hope you enjoy it, just don't sell it for the grape juice");
        embed.setColor(Color.GREEN);
        context.reply(embed.build(), false).queue();
    }

}
