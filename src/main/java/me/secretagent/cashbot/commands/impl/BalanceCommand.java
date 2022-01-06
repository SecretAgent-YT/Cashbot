package me.secretagent.cashbot.commands.impl;

import me.secretagent.cashbot.CashBot;
import me.secretagent.cashbot.api.CashBotUser;
import me.secretagent.cashbot.commands.Command;
import me.secretagent.cashbot.commands.CommandContext;
import me.secretagent.cashbot.item.Material;
import me.secretagent.cashbot.util.Embed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import java.awt.*;

public class BalanceCommand extends Command {

    @Override
    public CommandData getData() {
        return new CommandData("balance", getDescription()).addOption(OptionType.USER, "user", "User you want to look up");
    }

    @Override
    public String getDescription() {
        return "Checks your balance or another guild member's balance.";
    }

    @Override
    public void onCalled(CommandContext context) {
        User user;
        if (context.getOption("user") != null) {
            user = context.getOption("user").getAsUser();
        } else {
            user = context.getUser();
        }
        CashBotUser cashBotUser = CashBot.API.getUser(user);
        Embed embed = new Embed("Balance of " + user.getName());
        embed.setColor(Color.GREEN);
        embed.appendFormatField("Wallet", cashBotUser.getWallet() + "$");
        embed.appendFormatField("Bank", cashBotUser.getBank() + "$");
        context.reply(embed.build(), false).queue();
    }

}
