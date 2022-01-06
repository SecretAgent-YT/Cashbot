package me.secretagent.cashbot.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public enum Material {

    LAPTOP(":computer:", "Laptop", 50),
    PHONE(":mobile_phone:", "Phone", 50),
    GRAPE_JUICE(":wine_glass:", "Grape Juice", 10),
    SMART_WATCH(":watch:", "Smart Watch", 40),
    POSION(":champagne_glass:", "Poison", 30),
    FORTNITECARD(":credit_card:", "19$ Fortnite Card", 19),
    LOOT_CRATE(":gift:", "Loot Crate", 100);

    private final String emote;
    private final String name;
    private final long price;


    Material(String emote, String name, long price) {
        this.emote = emote;
        this.name = name;
        this.price = price;
    }

    public String getEmote() {
        return emote;
    }

    public long getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public static Collection<String> getNames() {
        List<String> stringList = new ArrayList<>();
        for (Material material : values()) {
            stringList.add(material.getName());
        }
        return stringList;
    }

    public static Material fromName(String name, boolean ignoreCase) {
        if (!ignoreCase) {
            return Arrays.stream(values()).filter(material -> material.getName().equals(name)).findFirst().orElse(null);
        } else {
            return Arrays.stream(values()).filter(material -> material.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
        }
    }

    public static Material fromName(String name) {
        return fromName(name, true);
    }

}
