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
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class WorkCommand extends Command {

    private final HashMap<User, Long> cooldownMap = new HashMap<>();

    @Override
    public CommandData getData() {
        return new CommandData("work", getDescription());
    }

    @Override
    public String getDescription() {
        return "Gives you money, but is it worth it?";
    }

    @Override
    public void onCalled(CommandContext context) {
        if (cooldownMap.containsKey(context.getUser()) && cooldownMap.get(context.getUser()) >= System.currentTimeMillis()) {
            Embed embed = new Embed("Chill my guy");
            long timeLeft = cooldownMap.get(context.getUser()) - System.currentTimeMillis();
            embed.setColor(Color.RED);
            embed.setDescription("You're on a cooldown, you have `" + (Math.round((float) (timeLeft / 1000))) + "` seconds left!");
            context.reply(embed.build(), false).queue();
            return;
        }
        CashBotUser cashBotUser = CashBot.API.getUser(context.getUser());
        int amount = ThreadLocalRandom.current().nextInt(100);
        cashBotUser.setWallet(cashBotUser.getWallet() + amount);
        Embed embed = new Embed("You worked and got `" + amount + "$`");
        embed.setColor(Color.GREEN);
        embed.setDescription("Good job, now hand over that money\nfor social security");
        context.reply(embed.build(), false).queue();
        cooldownMap.put(context.getUser(), System.currentTimeMillis() + 300 * 1000L);
    }

}
