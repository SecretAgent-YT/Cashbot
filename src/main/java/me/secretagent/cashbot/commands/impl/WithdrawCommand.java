package me.secretagent.cashbot.commands.impl;

import me.secretagent.cashbot.CashBot;
import me.secretagent.cashbot.api.CashBotUser;
import me.secretagent.cashbot.commands.Command;
import me.secretagent.cashbot.commands.CommandContext;
import me.secretagent.cashbot.util.Embed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import java.awt.*;

public class WithdrawCommand extends Command {

    @Override
    public CommandData getData() {
        return new CommandData("withdraw", getDescription()).addOption(OptionType.INTEGER, "amount", "Amount you want to deposit", true);
    }

    @Override
    public String getDescription() {
        return "Withdraws money from your bank into your wallet.";
    }

    @Override
    public void onCalled(CommandContext context) {
        User user = context.getUser();
        CashBotUser cashBotUser = CashBot.API.getUser(user);
        long amount = context.getOption("amount").getAsLong();
        if (cashBotUser.getBank() < amount) {
            Embed embed = new Embed("Not enough money");
            embed.setColor(Color.RED);
            embed.setDescription("You don't have enough money in ur bank, smh.");
            context.reply(embed.build(), false).queue();
            return;
        }
        cashBotUser.setWallet(cashBotUser.getWallet() + amount);
        cashBotUser.setBank(cashBotUser.getBank() - amount);
        Embed embed = new Embed("You withdrew `" + amount + "$`");
        embed.setColor(Color.GREEN);
        embed.setDescription("Getting ready to run away, huh?");
        context.reply(embed.build(), false).queue();
    }

}
