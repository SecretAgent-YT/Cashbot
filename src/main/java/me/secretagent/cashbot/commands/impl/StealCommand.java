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

public class StealCommand extends Command {

    private final HashMap<User, Long> cooldownMap = new HashMap<>();

    @Override
    public CommandData getData() {
        return new CommandData("steal", getDescription()).addOption(OptionType.USER, "user", "User you want to steal from", true);
    }

    @Override
    public String getDescription() {
        return "If you're a klepto, smh rn.";
    }

    @Override
    public void onCalled(CommandContext context) {
        if (cooldownMap.containsKey(context.getUser()) && cooldownMap.get(context.getUser()) >= System.currentTimeMillis()) {
            Embed embed = new Embed("Chill my guy");
            embed.setColor(Color.RED);
            long timeLeft = cooldownMap.get(context.getUser()) - System.currentTimeMillis();
            embed.setDescription("You're on a cooldown, you have `" + (Math.round((float) (timeLeft / 1000))) + "` seconds left!");
            context.reply(embed.build(), false).queue();
            return;
        }
        CashBotUser cashBotUser = CashBot.API.getUser(context.getUser());
        CashBotUser victim = CashBot.API.getUser(context.getOption("user").getAsUser());
        if (victim.getWallet() < 50) {
            Embed embed = new Embed("Their poor lol");
            embed.setColor(Color.RED);
            embed.setDescription("This homie is poor, theydon't even have `50$`!!!\nNot worth it...");
            context.reply(embed.build(), false).queue();
            return;
        }
        int amount = (int) (victim.getWallet() * ThreadLocalRandom.current().nextDouble() * 0.07);
        victim.setWallet(victim.getWallet() - amount);
        cashBotUser.setWallet(cashBotUser.getWallet() + amount);
        Embed embed = new Embed("You stole `" + amount + "$`");
        embed.setColor(Color.GREEN);
        embed.setDescription("Was it worth it tho...");
        context.reply(embed.build(), false).queue();
        cooldownMap.put(context.getUser(), System.currentTimeMillis() + 300 * 1000L);
    }

}
