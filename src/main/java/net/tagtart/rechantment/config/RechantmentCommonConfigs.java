package net.tagtart.rechantment.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RechantmentCommonConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> SIMPLE_ENCHANTMENTS;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> UNIQUE_ENCHANTMENTS;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> ELITE_ENCHANTMENTS;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> ULTIMATE_ENCHANTMENTS;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> LEGENDARY_ENCHANTMENTS;

    static {
        // Simple rarity builder default config
        BUILDER.push("Configs for Rechantment Mod");
        BUILDER.comment("Configs for Simple Rarity Enchantments").push("simple");
        SIMPLE_ENCHANTMENTS = BUILDER.defineList("enchantments", Arrays.asList(
                        "minecraft:smite",
                        "minecraft:aqua_affinity",
                        "minecraft:efficiency",
                        "minecraft:knockback",
                        "minecraft:lure",
                        "minecraft:piercing",
                        "minecraft:power",
                        "minecraft:protection",
                        "minecraft:punch",
                        "minecraft:sharpness"),
                s -> s instanceof String);
        BUILDER.pop();
        // Unique rarity builder default config
        BUILDER.comment("Configs for Unique Rarity Enchantments").push("unique");
        UNIQUE_ENCHANTMENTS = BUILDER.defineList("enchantments", Arrays.asList(
                "minecraft:bane_of_arthropods",
                "minecraft:blast_protection",
                "minecraft:feather_falling",
                "minecraft:fire_protection",
                "minecraft:impaling",
                "minecraft:loyalty",
                "minecraft:projectile_protection",
                "minecraft:quick_charge",
                "minecraft:respiration",
                "minecraft:riptide",
                "minecraft:thorns",
                "minecraft:unbreaking"), s -> s instanceof String);
        BUILDER.pop();

        // Elite rarity builder default config
        BUILDER.comment("Configs for Elite Rarity Enchantments").push("elite");
        ELITE_ENCHANTMENTS = BUILDER.defineList("enchantments", Arrays.asList(
                "minecraft:fortune",
                "minecraft:looting",
                "minecraft:depth_strider",
                "minecraft:fire_aspect",
                "minecraft:flame",
                "minecraft:frost_walker",
                "minecraft:soul_speed",
                "rechantment:voids_bane"
        ), s -> s instanceof String);
        BUILDER.pop();

        // Ultimate rarity builder default config
        BUILDER.comment("Configs for Ultimate Rarity Enchantments").push("ultimate");
        ULTIMATE_ENCHANTMENTS = BUILDER.defineList("enchantments", Arrays.asList(
                "minecraft:channeling",
                "minecraft:luck_of_the_sea",
                "minecraft:multishot",
                "minecraft:silk_touch",
                "minecraft:sweeping",
                "minecraft:swift_sneak"
        ), s -> s instanceof String);
        BUILDER.pop();


        // Legendary rarity builder default config
        BUILDER.comment("Configs for Legendary Rarity Enchantments").push("legendary");
        LEGENDARY_ENCHANTMENTS = BUILDER.defineList("enchantments", Arrays.asList(
                "minecraft:infinity"
        ), s -> s instanceof String);

        BUILDER.pop();


        SPEC = BUILDER.build();
    }
}

