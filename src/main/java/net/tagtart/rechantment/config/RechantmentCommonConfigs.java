package net.tagtart.rechantment.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.tagtart.rechantment.util.EnchantmentPoolEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RechantmentCommonConfigs {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

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
    public static final ForgeConfigSpec.ConfigValue<? extends Integer>      RARITY_0_REQUIRED_BOOKSHELVES;
    public static final ForgeConfigSpec.ConfigValue<? extends String>       RARITY_0_FLOOR_BLOCK_TYPE;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> RARITY_0_ENCHANTMENTS;

    // Unique tier configs.
    public static final ForgeConfigSpec.ConfigValue<? extends String>       RARITY_1_KEY;
    public static final ForgeConfigSpec.ConfigValue<? extends Integer>      RARITY_1_COLOR;
    public static final ForgeConfigSpec.ConfigValue<? extends Integer>      RARITY_1_EXP_COST;
    public static final ForgeConfigSpec.ConfigValue<? extends Integer>      RARITY_1_WORLD_SPAWN_WEIGHT;
    public static final ForgeConfigSpec.ConfigValue<? extends Integer>      RARITY_1_MIN_SUCCESS;
    public static final ForgeConfigSpec.ConfigValue<? extends Integer>      RARITY_1_MAX_SUCCESS;
    public static final ForgeConfigSpec.ConfigValue<? extends Integer>      RARITY_1_FORCED_BOOK_BREAKS;
    public static final ForgeConfigSpec.ConfigValue<? extends Integer>      RARITY_1_FORCED_FLOOR_BREAKS;
    public static final ForgeConfigSpec.ConfigValue<? extends Float>        RARITY_1_BOOK_BREAK_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<? extends Float>        RARITY_1_FLOOR_BREAK_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<? extends String>       RARITY_1_FLOOR_BLOCK_TYPE;
    public static final ForgeConfigSpec.ConfigValue<? extends Integer>      RARITY_1_REQUIRED_BOOKSHELVES;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> RARITY_1_ENCHANTMENTS;

    // Elite tier configs.
    public static final ForgeConfigSpec.ConfigValue<? extends String>       RARITY_2_KEY;
    public static final ForgeConfigSpec.ConfigValue<? extends Integer>      RARITY_2_COLOR;
    public static final ForgeConfigSpec.ConfigValue<? extends Integer>      RARITY_2_EXP_COST;
    public static final ForgeConfigSpec.ConfigValue<? extends Integer>      RARITY_2_WORLD_SPAWN_WEIGHT;
    public static final ForgeConfigSpec.ConfigValue<? extends Integer>      RARITY_2_MIN_SUCCESS;
    public static final ForgeConfigSpec.ConfigValue<? extends Integer>      RARITY_2_MAX_SUCCESS;
    public static final ForgeConfigSpec.ConfigValue<? extends Integer>      RARITY_2_FORCED_BOOK_BREAKS;
    public static final ForgeConfigSpec.ConfigValue<? extends Integer>      RARITY_2_FORCED_FLOOR_BREAKS;
    public static final ForgeConfigSpec.ConfigValue<? extends Float>        RARITY_2_BOOK_BREAK_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<? extends Float>        RARITY_2_FLOOR_BREAK_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<? extends Integer>      RARITY_2_REQUIRED_BOOKSHELVES;
    public static final ForgeConfigSpec.ConfigValue<? extends String>       RARITY_2_FLOOR_BLOCK_TYPE;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> RARITY_2_ENCHANTMENTS;

    // Ultimate tier configs.
    public static final ForgeConfigSpec.ConfigValue<? extends String>       RARITY_3_KEY;
    public static final ForgeConfigSpec.ConfigValue<? extends Integer>      RARITY_3_COLOR;
    public static final ForgeConfigSpec.ConfigValue<? extends Integer>      RARITY_3_EXP_COST;
    public static final ForgeConfigSpec.ConfigValue<? extends Integer>      RARITY_3_WORLD_SPAWN_WEIGHT;
    public static final ForgeConfigSpec.ConfigValue<? extends Integer>      RARITY_3_MIN_SUCCESS;
    public static final ForgeConfigSpec.ConfigValue<? extends Integer>      RARITY_3_MAX_SUCCESS;
    public static final ForgeConfigSpec.ConfigValue<? extends Integer>      RARITY_3_FORCED_BOOK_BREAKS;
    public static final ForgeConfigSpec.ConfigValue<? extends Integer>      RARITY_3_FORCED_FLOOR_BREAKS;
    public static final ForgeConfigSpec.ConfigValue<? extends Float>        RARITY_3_BOOK_BREAK_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<? extends Float>        RARITY_3_FLOOR_BREAK_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<? extends Integer>      RARITY_3_REQUIRED_BOOKSHELVES;
    public static final ForgeConfigSpec.ConfigValue<? extends String>       RARITY_3_FLOOR_BLOCK_TYPE;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> RARITY_3_ENCHANTMENTS;

    // Legendary tier configs.
    public static final ForgeConfigSpec.ConfigValue<? extends String>       RARITY_4_KEY;
    public static final ForgeConfigSpec.ConfigValue<? extends Integer>      RARITY_4_COLOR;
    public static final ForgeConfigSpec.ConfigValue<? extends Integer>      RARITY_4_EXP_COST;
    public static final ForgeConfigSpec.ConfigValue<? extends Integer>      RARITY_4_WORLD_SPAWN_WEIGHT;
    public static final ForgeConfigSpec.ConfigValue<? extends Integer>      RARITY_4_MIN_SUCCESS;
    public static final ForgeConfigSpec.ConfigValue<? extends Integer>      RARITY_4_MAX_SUCCESS;
    public static final ForgeConfigSpec.ConfigValue<? extends Integer>      RARITY_4_FORCED_BOOK_BREAKS;
    public static final ForgeConfigSpec.ConfigValue<? extends Integer>      RARITY_4_FORCED_FLOOR_BREAKS;
    public static final ForgeConfigSpec.ConfigValue<? extends Float>        RARITY_4_BOOK_BREAK_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<? extends Float>        RARITY_4_FLOOR_BREAK_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<? extends Integer>      RARITY_4_REQUIRED_BOOKSHELVES;
    public static final ForgeConfigSpec.ConfigValue<? extends String>       RARITY_4_FLOOR_BLOCK_TYPE;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> RARITY_4_ENCHANTMENTS;


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
        RARITY_0_MAX_SUCCESS = BUILDER.define("max_success", 85);
        RARITY_0_FORCED_BOOK_BREAKS = BUILDER.define("guaranteed_bookshelf_breaks", 0);
        RARITY_0_FORCED_FLOOR_BREAKS = BUILDER.define("guaranteed_floor_breaks", 0);
        RARITY_0_BOOK_BREAK_CHANCE = BUILDER.define("book_break_chance", 0.00f);
        RARITY_0_FLOOR_BREAK_CHANCE = BUILDER.define("floor_break_chance", 0.05f);
        RARITY_0_REQUIRED_BOOKSHELVES = BUILDER.define("required_bookshelves", 4);
        RARITY_0_FLOOR_BLOCK_TYPE = BUILDER.define("floor_block_type", "minecraft:iron_block");
        BUILDER.comment(
                "List of potential enchantments with weights, levels, and per-level weights.",
                "Format: <enchantment>|<weight>|<level-range>|<level-weights>",
                "Example: minecraft:unbreaking|10|1-3|1,2,3");
        ArrayList<String> rarity_0_default_enchantments = new ArrayList<>();
        rarity_0_default_enchantments.add("minecraft:sharpness|1|1-4|3,3,2,1");
        rarity_0_default_enchantments.add("minecraft:efficiency|1|1-4|3,3,2,1");
        rarity_0_default_enchantments.add("minecraft:knockback|1|1-2|3,2");
        rarity_0_default_enchantments.add("minecraft:power|1|1-4|3,3,2,1");
        rarity_0_default_enchantments.add("minecraft:smite|1|3-4|1,1");
        rarity_0_default_enchantments.add("minecraft:lure|1|2-3|2,1");
        rarity_0_default_enchantments.add("minecraft:protection|1|2-4|3,2,1");
        rarity_0_default_enchantments.add("minecraft:aqua_affinity|1|1|1");
        rarity_0_default_enchantments.add("minecraft:punch|1|1-2|3,1");
        rarity_0_default_enchantments.add("minecraft:piercing|1|3-4|2,1");

        RARITY_0_ENCHANTMENTS = BUILDER.defineList("enchantments", rarity_0_default_enchantments, s -> s instanceof String);
        BUILDER.pop();
        // Unique rarity builder default config
        BUILDER.comment("Configs for Unique Rarity Enchantments");
        BUILDER.push("unique");
        RARITY_1_KEY = BUILDER.define("key", "unique");
        RARITY_1_COLOR = BUILDER.define("color", 5635925);
        RARITY_1_EXP_COST = BUILDER.define("exp_cost", 200);
        RARITY_1_WORLD_SPAWN_WEIGHT = BUILDER.define("spawn_weight", 30);
        RARITY_1_MIN_SUCCESS = BUILDER.define("min_success", 25);
        RARITY_1_MAX_SUCCESS = BUILDER.define("max_success", 85);
        RARITY_1_FORCED_BOOK_BREAKS = BUILDER.define("guaranteed_bookshelf_breaks", 0);
        RARITY_1_FORCED_FLOOR_BREAKS = BUILDER.define("guaranteed_floor_breaks", 0);
        RARITY_1_BOOK_BREAK_CHANCE = BUILDER.define("book_break_chance", 0.00f);
        RARITY_1_FLOOR_BREAK_CHANCE = BUILDER.define("floor_break_chance", 0.045f);
        RARITY_1_REQUIRED_BOOKSHELVES = BUILDER.define("required_bookshelves", 8);
        RARITY_1_FLOOR_BLOCK_TYPE = BUILDER.define("floor_block_type", "minecraft:gold_block");

        ArrayList<String> rarity_1_default_enchantments = new ArrayList<>();
        rarity_1_default_enchantments.add("minecraft:bane_of_arthropods|1|4|1");
        rarity_1_default_enchantments.add("minecraft:loyalty|1|3|1");
        rarity_1_default_enchantments.add("minecraft:projectile_protection|1|4|1");
        rarity_1_default_enchantments.add("minecraft:fire_protection|1|4|1");
        rarity_1_default_enchantments.add("minecraft:thorns|1|2-3|2,1");
        rarity_1_default_enchantments.add("minecraft:unbreaking|1|1-3|3,2,1");
        rarity_1_default_enchantments.add("minecraft:feather_falling|1|3-4|2,1");
        rarity_1_default_enchantments.add("minecraft:quick_charge|1|3|1");
        rarity_1_default_enchantments.add("minecraft:riptide|1|3|1");
        rarity_1_default_enchantments.add("minecraft:respiration|1|1-3|3,2,1");
        rarity_1_default_enchantments.add("minecraft:blast_protection|1|4|1");
        rarity_1_default_enchantments.add("minecraft:impaling|1|3-4|2,1");
        RARITY_1_ENCHANTMENTS = BUILDER.defineList("enchantments", rarity_1_default_enchantments, s -> s instanceof String);

        BUILDER.pop();

        BUILDER.comment("Configs for Elite Rarity Enchantments").push("elite");
        RARITY_2_KEY = BUILDER.define("key", "elite");
        RARITY_2_COLOR = BUILDER.define("color", 5636095);
        RARITY_2_EXP_COST = BUILDER.define("exp_cost", 400);
        RARITY_2_WORLD_SPAWN_WEIGHT = BUILDER.define("spawn_weight", 15);
        RARITY_2_MIN_SUCCESS = BUILDER.define("min_success", 25);
        RARITY_2_MAX_SUCCESS = BUILDER.define("max_success", 85);
        RARITY_2_FORCED_BOOK_BREAKS = BUILDER.define("guaranteed_bookshelf_breaks", 0);
        RARITY_2_FORCED_FLOOR_BREAKS = BUILDER.define("guaranteed_floor_breaks", 0);
        RARITY_2_BOOK_BREAK_CHANCE = BUILDER.define("book_break_chance", 0.015f);
        RARITY_2_FLOOR_BREAK_CHANCE = BUILDER.define("floor_break_chance", 0.03f);
        RARITY_2_REQUIRED_BOOKSHELVES = BUILDER.define("required_bookshelves", 16);
        RARITY_2_FLOOR_BLOCK_TYPE = BUILDER.define("floor_block_type", "minecraft:diamond_block");

        ArrayList<String> rarity_2_default_enchantments = new ArrayList<>();
        rarity_2_default_enchantments.add("minecraft:looting|1|1-3|3,2,1");
        rarity_2_default_enchantments.add("minecraft:flame|1|1|1");
        rarity_2_default_enchantments.add("minecraft:allurement|1|3|1");
        rarity_2_default_enchantments.add("minecraft:frost_walker|1|2|1");
        rarity_2_default_enchantments.add("minecraft:thorns|1|2-3|2,1");
        rarity_2_default_enchantments.add("minecraft:berserk|1|3|1");
        rarity_2_default_enchantments.add("minecraft:depth_strider|1|3|1");
        rarity_2_default_enchantments.add("minecraft:soul_speed|1|3|1");
        rarity_2_default_enchantments.add("minecraft:hells_fury|1|4|1");
        rarity_2_default_enchantments.add("minecraft:voids_bane|1|4|1");
        rarity_2_default_enchantments.add("minecraft:fire_aspect|1|1-2|2,1");
        rarity_2_default_enchantments.add("minecraft:fortune|1|1-3|2,2,1");
        RARITY_2_ENCHANTMENTS = BUILDER.defineList("enchantments", rarity_2_default_enchantments, s -> s instanceof String);

        BUILDER.pop();


        // Ultimate rarity builder default config
        BUILDER.comment("Configs for Ultimate Rarity Enchantments").push("ultimate");
        RARITY_3_KEY = BUILDER.define("key", "ultimate");
        RARITY_3_COLOR = BUILDER.define("color", 16777045);
        RARITY_3_EXP_COST = BUILDER.define("exp_cost", 500);
        RARITY_3_WORLD_SPAWN_WEIGHT = BUILDER.define("spawn_weight", 10);
        RARITY_3_MIN_SUCCESS = BUILDER.define("min_success", 30);
        RARITY_3_MAX_SUCCESS = BUILDER.define("max_success", 85);
        RARITY_3_FORCED_BOOK_BREAKS = BUILDER.define("guaranteed_bookshelf_breaks", 0);
        RARITY_3_FORCED_FLOOR_BREAKS = BUILDER.define("guaranteed_floor_breaks", 0);
        RARITY_3_BOOK_BREAK_CHANCE = BUILDER.define("book_break_chance", 0.015f);
        RARITY_3_FLOOR_BREAK_CHANCE = BUILDER.define("floor_break_chance", 0.075f);
        RARITY_3_REQUIRED_BOOKSHELVES = BUILDER.define("required_bookshelves", 32);
        RARITY_3_FLOOR_BLOCK_TYPE = BUILDER.define("floor_block_type", "minecraft:emerald_block");

        ArrayList<String> rarity_3_default_enchantments = new ArrayList<>();
        rarity_3_default_enchantments.add("minecraft:swift_sneak|1|1-3|3,2,1");
        rarity_3_default_enchantments.add("minecraft:luck_of_the_sea|1|2-3|2,1");
        rarity_3_default_enchantments.add("minecraft:sweeping|1|2-3|2,1");
        rarity_3_default_enchantments.add("minecraft:silk_touch|1|1|1");
        rarity_3_default_enchantments.add("rechantment:ice_aspect|1|2-3|2,1");
        rarity_3_default_enchantments.add("rechantment:vein_miner|1|1|1");
        rarity_3_default_enchantments.add("rechantment:timber|1|1|1");
        rarity_3_default_enchantments.add("minecraft:multishot|1|1|1");
        rarity_3_default_enchantments.add("minecraft:channeling|1|1|1");
        RARITY_3_ENCHANTMENTS = BUILDER.defineList("enchantments", rarity_3_default_enchantments, s -> s instanceof String);

        BUILDER.pop();


        // Legendary rarity builder default config
        BUILDER.comment("Configs for Legendary Rarity Enchantments").push("legendary");
        RARITY_4_KEY = BUILDER.define("key", "legendary");
        RARITY_4_COLOR = BUILDER.define("color", 16755200);
        RARITY_4_EXP_COST = BUILDER.define("exp_cost", 2000);
        RARITY_4_WORLD_SPAWN_WEIGHT = BUILDER.define("spawn_weight", 5);
        RARITY_4_MIN_SUCCESS = BUILDER.define("min_success", 20);
        RARITY_4_MAX_SUCCESS = BUILDER.define("max_success", 85);
        RARITY_4_FORCED_BOOK_BREAKS = BUILDER.define("guaranteed_bookshelf_breaks", 0);
        RARITY_4_FORCED_FLOOR_BREAKS = BUILDER.define("guaranteed_floor_breaks", 0);
        RARITY_4_BOOK_BREAK_CHANCE = BUILDER.define("book_break_chance", 0.015f);
        RARITY_4_FLOOR_BREAK_CHANCE = BUILDER.define("floor_break_chance", 0.08f);
        RARITY_4_REQUIRED_BOOKSHELVES = BUILDER.define("required_bookshelves", 45);
        RARITY_4_FLOOR_BLOCK_TYPE = BUILDER.define("floor_block_type", "minecraft:ancient_debris");

        ArrayList<String> rarity_4_default_enchantments = new ArrayList<>();
        rarity_4_default_enchantments.add("minecraft:infinity|1|1|1");
        rarity_4_default_enchantments.add("rechantment:inquisitive|1|1-4|4,3,2,1");
        rarity_4_default_enchantments.add("rechantment:overload|1|1-3|4,2,1");
        RARITY_4_ENCHANTMENTS = BUILDER.defineList("enchantments", rarity_4_default_enchantments, s -> s instanceof String);

        BUILDER.pop();


        SPEC = BUILDER.build();
    }
}

