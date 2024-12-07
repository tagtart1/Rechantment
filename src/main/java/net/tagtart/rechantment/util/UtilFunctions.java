package net.tagtart.rechantment.util;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.tagtart.rechantment.config.RechantmentCommonConfigs;
import oshi.util.tuples.Pair;

import java.util.ArrayList;
import java.util.List;

public class UtilFunctions {
    // TO-DO MOVE THIS TO A UTIL
    // Utility method to wrap text into lines
    public static List<String> wrapText(String text, int maxWidth) {
        List<String> lines = new ArrayList<>();
        StringBuilder currentLine = new StringBuilder();
        Font font = Minecraft.getInstance().font; // Get the Minecraft font renderer

        for (String word : text.split(" ")) {
            // Check if adding the next word exceeds the maxWidth
            if (font.width(currentLine + word) > maxWidth) {
                lines.add(currentLine.toString()); // Add the current line to the list
                currentLine = new StringBuilder(); // Start a new line
            }
            currentLine.append(word).append(" ");
        }

        // Add the last line if it exists
        if (!currentLine.isEmpty()) {
            lines.add(currentLine.toString().trim());
        }

        return lines;
    }


    public static ItemStack getItemStackFromString(String itemName) {
        ResourceLocation itemResource = new ResourceLocation(itemName);

        Item item = ForgeRegistries.ITEMS.getValue(itemResource);

        if (item == null) {
            return ItemStack.EMPTY;
        }

        return new ItemStack(item);
    }

    public static Pair<String, ChatFormatting> getRarityInfo(String enchantmentRaw) {
        if (RechantmentCommonConfigs.SIMPLE_ENCHANTMENTS.get().contains(enchantmentRaw)) {
            return new Pair<>("simple", ChatFormatting.GRAY);
        } else if (RechantmentCommonConfigs.ELITE_ENCHANTMENTS.get().contains(enchantmentRaw)) {
            return new Pair<>("elite", ChatFormatting.AQUA);
        } else if (RechantmentCommonConfigs.UNIQUE_ENCHANTMENTS.get().contains(enchantmentRaw)) {
            return new Pair<>("unique", ChatFormatting.GREEN);
        } else if (RechantmentCommonConfigs.ULTIMATE_ENCHANTMENTS.get().contains(enchantmentRaw)) {
            return new Pair<>("ultimate", ChatFormatting.YELLOW);
        } else if (RechantmentCommonConfigs.LEGENDARY_ENCHANTMENTS.get().contains(enchantmentRaw)) {
            return new Pair<>("legendary", ChatFormatting.GOLD);
        }
        return new Pair<>("simple", ChatFormatting.GRAY);
    }

    public static Pair<String, ChatFormatting> getRarityInfo(float rarity) {

        int rarityInt = Math.round(rarity);
        return switch (rarityInt) {
            case 1 -> new Pair<>("simple", ChatFormatting.GRAY);
            case 2 -> new Pair<>("unique", ChatFormatting.GREEN);
            case 3 -> new Pair<>("elite", ChatFormatting.AQUA);
            case 4 -> new Pair<>("ultimate", ChatFormatting.YELLOW);
            case 5 -> new Pair<>("legendary", ChatFormatting.GOLD);
            default -> new Pair<>("simple", ChatFormatting.GRAY);
        };
    }
}
