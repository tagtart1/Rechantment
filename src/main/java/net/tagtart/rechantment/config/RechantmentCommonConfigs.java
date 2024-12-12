package net.tagtart.rechantment.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.tagtart.rechantment.util.EnchantmentPoolEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RechantmentCommonConfigs {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> UNIQUE_ENCHANTMENTS;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> ELITE_ENCHANTMENTS;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> ULTIMATE_ENCHANTMENTS;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> LEGENDARY_ENCHANTMENTS;

    // Simple tier configs.
    public static final ForgeConfigSpec.ConfigValue<? extends String>       RARITY_0_KEY;
    public static final ForgeConfigSpec.ConfigValue<? extends Integer>      RARITY_0_COLOR;
    public static final ForgeConfigSpec.ConfigValue<? extends Integer>      RARITY_0_EXP_COST;
    public static final ForgeConfigSpec.ConfigValue<? extends Integer>      RARITY_0_WORLD_SPAWN_WEIGHT;
    public static final ForgeConfigSpec.ConfigValue<? extends Integer>      RARITY_0_MIN_SUCCESS;
    public static final ForgeConfigSpec.ConfigValue<? extends Integer>      RARITY_0_MAX_SUCCESS;
    public static final ForgeConfigSpec.ConfigValue<? extends Integer>      RARITY_0_FORCED_BOOK_BREAKS;
    public static final ForgeConfigSpec.ConfigValue<? extends Integer>      RARITY_0_FORCED_FLOOR_BREAKS;
    public static final ForgeConfigSpec.ConfigValue<? extends Float>        RARITY_0_BOOK_BREAK_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<? extends Float>        RARITY_0_FLOOR_BREAK_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<? extends String>       RARITY_0_FLOOR_BLOCK_TYPE;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> RARITY_0_ENCHANTMENTS;


    static {

        // Simple rarity builder default config
        BUILDER.push("Configs for Rechantment Mod");

        BUILDER.comment("Configs for Simple Rarity Enchantments");

        BUILDER.push("Book Rarities");

        // Simple rarity properties.
        BUILDER.push("Simple");
        RARITY_0_KEY = BUILDER.define("key", "simple");
        RARITY_0_COLOR = BUILDER.define("color", 11184810);
        RARITY_0_EXP_COST = BUILDER.define("exp_cost", 100);
        RARITY_0_WORLD_SPAWN_WEIGHT = BUILDER.define("spawn_weight", 40);
        RARITY_0_MIN_SUCCESS = BUILDER.define("min_success", 25);
        RARITY_0_MAX_SUCCESS = BUILDER.define("max_uccess", 85);
        RARITY_0_FORCED_BOOK_BREAKS = BUILDER.define("guaranteed_bookshelf_breaks", 0);
        RARITY_0_FORCED_FLOOR_BREAKS = BUILDER.define("guaranteed_floor_breaks", 0);
        RARITY_0_BOOK_BREAK_CHANCE = BUILDER.define("book_break_chance", 0.00f);
        RARITY_0_FLOOR_BREAK_CHANCE = BUILDER.define("floor_break_chance", 0.05f);
        RARITY_0_FLOOR_BLOCK_TYPE = BUILDER.define("floor_block_type", "minecraft:iron_block");
//"minecraft:smite",
//                        "minecraft:aqua_affinity",
//                       "minecraft:efficiency",
//                       "minecraft:knockback",
//                        "minecraft:lure",
//                        "minecraft:piercing",
//                        "minecraft:power",
//                        "minecraft:protection",
//                        "minecraft:punch",
//                        "minecraft:sharpness"
        ArrayList<String> rarity_0_default_enchantments = new ArrayList<>();
        rarity_0_default_enchantments.add("minecraft:smite|10|1-4|40,30,20,10");

        BUILDER.comment(
                "List of enchantments with weights, levels, and level weights.",
                "Format: <enchantment>|<weight>|<level-range>|<level-weights>",
                "Example: minecraft:unbreaking|10|1-3|10,20,30");
        RARITY_0_ENCHANTMENTS = BUILDER.defineList("enchantments", rarity_0_default_enchantments, s -> s instanceof String);
        BUILDER.pop();
        // Unique rarity builder default config
        BUILDER.comment("Configs for Unique Rarity Enchantments");
        BUILDER.push("unique");
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
                "minecraft:soul_speed"
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

