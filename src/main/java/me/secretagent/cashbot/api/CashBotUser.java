package me.secretagent.cashbot.api;

import com.mongodb.client.MongoCursor;
import me.secretagent.cashbot.item.Material;
import net.dv8tion.jda.api.entities.User;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

public class CashBotUser {

    private final User user;
    private final CashBotAPI api;
    private final Document query = new Document();

    public CashBotUser(User user, CashBotAPI api) {
        this.user = user;
        this.api = api;
        this.query.put("_id", user.getId());
        if (getDocument() == null) {
            Document document = new Document();
            document.put("_id", user.getId());
            document.put("wallet", 0L);
            document.put("bank", 0L);
            document.put("bank_max", 1000L);
            Document inventory = new Document();
            for (Material material : Material.values()) {
                inventory.put(material.toString(), 0L);
            }
            document.put("inventory", inventory);
            api.getUsers().insertOne(document);
        }
    }

    public Document getDocument() {
        MongoCursor<Document> cursor = api.getUsers().find().iterator();
        while (cursor.hasNext()) {
            Document doc = cursor.next();
            if (doc.get("_id").toString().equals(user.getId())) {
                return doc;
            }
        }
        return null;
    }

    public Document getInventoryDocument() {
        return getDocument().get("inventory", Document.class);
    }

    public Map<Material, Long> getInventory() {
        Map<Material, Long> map = new HashMap<>();
        Document document = getInventoryDocument();
        for (String string : document.keySet()) {
            Material material = Material.valueOf(string);
            long quantity = document.getLong(string);
            map.put(material, quantity);
        }
        return map;
    }

    public long getItem(Material material) {
        Document document = getInventoryDocument();
        if (!document.containsKey(material.toString())) {
            document.put(material.toString(), 0L);
            Document root = getDocument();
            root.put("inventory", document);
            api.getUsers().replaceOne(query, root);
        }
        return document.getLong(material.toString());
    }

    public boolean hasItem(Material material) {
        return getItem(material) > 0;
    }

    public void addItem(Material material, long amount) {
        Document document = getInventoryDocument();
        document.put(material.toString(), getInventory().get(material) + amount);
        Document root = getDocument();
        root.put("inventory", document);
        api.getUsers().replaceOne(query, root);
    }

    public void addItem(Material material) {
        addItem(material, 1);
    }

    public void removeItem(Material material, long amount) {
        Document document = getInventoryDocument();
        if (document.getLong(material.toString()) <= 0) return;
        document.put(material.toString(), getInventory().get(material) - amount);
        Document root = getDocument();
        root.put("inventory", document);
        api.getUsers().replaceOne(query, root);
    }

    public void removeItem(Material material) {
        removeItem(material, 1);
    }

    public void setWallet(long money) {
        Document document = getDocument();
        document.put("wallet", money);
        api.getUsers().replaceOne(query, document);
    }

    public long getWallet() {
        return getDocument().getLong("wallet");
    }
    
    public void setBank(long money) {
        Document document = getDocument();
        document.put("bank", money);
        api.getUsers().replaceOne(query, document);
    }

    public long getBank() {
        return getDocument().getLong("bank");
    }

    public void setBankMax(long money) {
        Document document = getDocument();
        document.put("bank_max", money);
        api.getUsers().replaceOne(query, document);
    }

    public long getBankMax() {
        return getDocument().getLong("bank_max");
    }

}
