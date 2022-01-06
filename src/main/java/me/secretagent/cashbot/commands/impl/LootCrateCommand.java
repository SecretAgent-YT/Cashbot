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
import java.util.concurrent.ThreadLocalRandom;

public class LootCrateCommand extends Command {

    @Override
    public CommandData getData() {
        return new CommandData("lootcrate", getDescription());
    }

    @Override
    public String getDescription() {
        return "Opens a Loot Crate.";
    }

    @Override
    public void onCalled(CommandContext context) {
        CashBotUser cashBotUser = CashBot.API.getUser(context.getUser());
        if (!cashBotUser.hasItem(Material.LOOT_CRATE)) {
            Embed embed = new Embed("You don't have any loot crates!");
            embed.setColor(Color.RED);
            embed.setDescription("You have to buy a loot crate from the shop before you run this command!");
            context.reply(embed.build(), false).queue();
            return;
        }
        long amount = ThreadLocalRandom.current().nextLong(100);
        Material item = Material.values()[ThreadLocalRandom.current().nextInt(Material.values().length - 1)];
        Embed embed = new Embed("You opened a loot crate!");
        cashBotUser.setWallet(cashBotUser.getWallet() + amount);
        cashBotUser.addItem(item);
        cashBotUser.removeItem(Material.LOOT_CRATE);
        embed.setColor(Color.GREEN);
        embed.appendFormatField("Money", amount + "$");
        embed.appendField("Item", "`" + item.getName() + "` " + item.getEmote());
        context.reply(embed.build(), false).queue();
    }

}
