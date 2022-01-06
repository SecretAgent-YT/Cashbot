package me.secretagent.cashbot.api;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import net.dv8tion.jda.api.entities.User;

public class CashBotAPI {

    private final MongoDatabase mongoDatabase;
    private final MongoCollection mongoCollection;

    public CashBotAPI(MongoClient mongoClient) {
        this.mongoDatabase = mongoClient.getDatabase("cashbot");
        this.mongoCollection = mongoDatabase.getCollection("users");
    }

    public CashBotUser getUser(User user) {
        return new CashBotUser(user, this);
    }

    public MongoCollection getUsers() {
        return mongoCollection;
    }

}
