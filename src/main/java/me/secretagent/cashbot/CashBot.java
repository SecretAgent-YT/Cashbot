package me.secretagent.cashbot;

import com.mongodb.MongoClient;
import me.secretagent.cashbot.api.CashBotAPI;
import me.secretagent.cashbot.commands.CommandManager;
import me.secretagent.cashbot.commands.impl.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class CashBot {

    private final JDA jda;

    public static CashBotAPI API = new CashBotAPI(new MongoClient());

    public CashBot(String token) throws Exception {
        jda = JDABuilder.createDefault(token)
                .setChunkingFilter(ChunkingFilter.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .setActivity(Activity.watching("Elon Musk's Portfolio"))
                .build().awaitReady();
        CommandManager commmandManager = new CommandManager(jda);
        commmandManager.registerCommands(new BalanceCommand(),
                new WorkCommand(),
                new StealCommand(),
                new DepositCommand(),
                new WithdrawCommand(),
                new ShopCommand(),
                new InventoryCommand(),
                new BuyCommand(),
                new LootCrateCommand());
    }



}
