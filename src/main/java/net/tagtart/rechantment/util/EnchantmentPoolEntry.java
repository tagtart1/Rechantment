package net.tagtart.rechantment.util;

import java.util.ArrayList;
import java.util.List;

public class EnchantmentPoolEntry
{
    public final String enchantment;
    public final int weight;
    public final int minLevel;
    public final int maxLevel;
    public final List<Integer> levelWeights;

    public EnchantmentPoolEntry(String enchantment, int weight, int minLevel, int maxLevel, List<Integer> levelWeights) {
        this.enchantment = enchantment;
        this.weight = weight;
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.levelWeights = levelWeights;
    }

    public static EnchantmentPoolEntry fromString(String configString) {
        String[] parts = configString.split("\\|");
        String enchantment = parts[0];
        int weight = Integer.parseInt(parts[1]);

        String[] levelRange = parts[2].split("-");
        int minLevel = Integer.parseInt(levelRange[0]);
        int maxLevel = Integer.parseInt(levelRange[1]);

        List<Integer> levelWeights = new ArrayList<>();
        String[] weightStrings = parts[3].split(",");
        for (String weightString : weightStrings) {
            levelWeights.add(Integer.parseInt(weightString));
        }

        return new EnchantmentPoolEntry(enchantment, weight, minLevel, maxLevel, levelWeights);
    }

    @Override
    public String toString() {
        return enchantment + "|" + weight + "|" + minLevel + "-" + maxLevel + "|" +
                String.join(",", levelWeights.stream().map(String::valueOf).toList());
    }
}
