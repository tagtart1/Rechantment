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
    public static final ForgeConfigSpec.IntValue                            RARITY_0_COLOR;
    public static final ForgeConfigSpec.IntValue                            RARITY_0_EXP_COST;
    public static final ForgeConfigSpec.IntValue                            RARITY_0_WORLD_SPAWN_WEIGHT;
    public static final ForgeConfigSpec.IntValue                            RARITY_0_MIN_SUCCESS;
    public static final ForgeConfigSpec.IntValue                            RARITY_0_MAX_SUCCESS;
    public static final ForgeConfigSpec.IntValue                            RARITY_0_FORCED_BOOK_BREAKS;
    public static final ForgeConfigSpec.IntValue                            RARITY_0_FORCED_FLOOR_BREAKS;
    public static final ForgeConfigSpec.DoubleValue                         RARITY_0_BOOK_BREAK_CHANCE;
    public static final ForgeConfigSpec.DoubleValue                         RARITY_0_FLOOR_BREAK_CHANCE;
    public static final ForgeConfigSpec.IntValue                            RARITY_0_REQUIRED_BOOKSHELVES;
    public static final ForgeConfigSpec.IntValue                            RARITY_0_REQUIRED_LAPIS;
    public static final ForgeConfigSpec.ConfigValue<? extends String>       RARITY_0_FLOOR_BLOCK_TYPE;
    public static final ForgeConfigSpec.DoubleValue                         RARITY_0_REROLL_GEM_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> RARITY_0_ENCHANTMENTS;

    // Unique tier configs.
    public static final ForgeConfigSpec.ConfigValue<? extends String>       RARITY_1_KEY;
    public static final ForgeConfigSpec.IntValue                            RARITY_1_COLOR;
    public static final ForgeConfigSpec.IntValue                            RARITY_1_EXP_COST;
    public static final ForgeConfigSpec.IntValue                            RARITY_1_WORLD_SPAWN_WEIGHT;
    public static final ForgeConfigSpec.IntValue                            RARITY_1_MIN_SUCCESS;
    public static final ForgeConfigSpec.IntValue                            RARITY_1_MAX_SUCCESS;
    public static final ForgeConfigSpec.IntValue                            RARITY_1_FORCED_BOOK_BREAKS;
    public static final ForgeConfigSpec.IntValue                            RARITY_1_FORCED_FLOOR_BREAKS;
    public static final ForgeConfigSpec.DoubleValue                         RARITY_1_BOOK_BREAK_CHANCE;
    public static final ForgeConfigSpec.DoubleValue                         RARITY_1_FLOOR_BREAK_CHANCE;
    public static final ForgeConfigSpec.IntValue                            RARITY_1_REQUIRED_BOOKSHELVES;
    public static final ForgeConfigSpec.IntValue                            RARITY_1_REQUIRED_LAPIS;
    public static final ForgeConfigSpec.ConfigValue<? extends String>       RARITY_1_FLOOR_BLOCK_TYPE;
    public static final ForgeConfigSpec.DoubleValue                         RARITY_1_REROLL_GEM_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> RARITY_1_ENCHANTMENTS;

    // Elite tier configs.
    public static final ForgeConfigSpec.ConfigValue<? extends String>       RARITY_2_KEY;
    public static final ForgeConfigSpec.IntValue                            RARITY_2_COLOR;
    public static final ForgeConfigSpec.IntValue                            RARITY_2_EXP_COST;
    public static final ForgeConfigSpec.IntValue                            RARITY_2_WORLD_SPAWN_WEIGHT;
    public static final ForgeConfigSpec.IntValue                            RARITY_2_MIN_SUCCESS;
    public static final ForgeConfigSpec.IntValue                            RARITY_2_MAX_SUCCESS;
    public static final ForgeConfigSpec.IntValue                            RARITY_2_FORCED_BOOK_BREAKS;
    public static final ForgeConfigSpec.IntValue                            RARITY_2_FORCED_FLOOR_BREAKS;
    public static final ForgeConfigSpec.DoubleValue                         RARITY_2_BOOK_BREAK_CHANCE;
    public static final ForgeConfigSpec.DoubleValue                         RARITY_2_FLOOR_BREAK_CHANCE;
    public static final ForgeConfigSpec.IntValue                            RARITY_2_REQUIRED_BOOKSHELVES;
    public static final ForgeConfigSpec.IntValue                            RARITY_2_REQUIRED_LAPIS;
    public static final ForgeConfigSpec.ConfigValue<? extends String>       RARITY_2_FLOOR_BLOCK_TYPE;
    public static final ForgeConfigSpec.DoubleValue                         RARITY_2_REROLL_GEM_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> RARITY_2_ENCHANTMENTS;

    // Ultimate tier configs.
    public static final ForgeConfigSpec.ConfigValue<? extends String>       RARITY_3_KEY;
    public static final ForgeConfigSpec.IntValue                            RARITY_3_COLOR;
    public static final ForgeConfigSpec.IntValue                            RARITY_3_EXP_COST;
    public static final ForgeConfigSpec.IntValue                            RARITY_3_WORLD_SPAWN_WEIGHT;
    public static final ForgeConfigSpec.IntValue                            RARITY_3_MIN_SUCCESS;
    public static final ForgeConfigSpec.IntValue                            RARITY_3_MAX_SUCCESS;
    public static final ForgeConfigSpec.IntValue                            RARITY_3_FORCED_BOOK_BREAKS;
    public static final ForgeConfigSpec.IntValue                            RARITY_3_FORCED_FLOOR_BREAKS;
    public static final ForgeConfigSpec.DoubleValue                         RARITY_3_BOOK_BREAK_CHANCE;
    public static final ForgeConfigSpec.DoubleValue                         RARITY_3_FLOOR_BREAK_CHANCE;
    public static final ForgeConfigSpec.IntValue                            RARITY_3_REQUIRED_BOOKSHELVES;
    public static final ForgeConfigSpec.IntValue                            RARITY_3_REQUIRED_LAPIS;
    public static final ForgeConfigSpec.ConfigValue<? extends String>       RARITY_3_FLOOR_BLOCK_TYPE;
    public static final ForgeConfigSpec.DoubleValue                         RARITY_3_REROLL_GEM_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> RARITY_3_ENCHANTMENTS;

    // Legendary tier configs.
    public static final ForgeConfigSpec.ConfigValue<? extends String>       RARITY_4_KEY;
    public static final ForgeConfigSpec.IntValue                            RARITY_4_COLOR;
    public static final ForgeConfigSpec.IntValue                            RARITY_4_EXP_COST;
    public static final ForgeConfigSpec.IntValue                            RARITY_4_WORLD_SPAWN_WEIGHT;
    public static final ForgeConfigSpec.IntValue                            RARITY_4_MIN_SUCCESS;
    public static final ForgeConfigSpec.IntValue                            RARITY_4_MAX_SUCCESS;
    public static final ForgeConfigSpec.IntValue                            RARITY_4_FORCED_BOOK_BREAKS;
    public static final ForgeConfigSpec.IntValue                            RARITY_4_FORCED_FLOOR_BREAKS;
    public static final ForgeConfigSpec.DoubleValue                         RARITY_4_BOOK_BREAK_CHANCE;
    public static final ForgeConfigSpec.DoubleValue                         RARITY_4_FLOOR_BREAK_CHANCE;
    public static final ForgeConfigSpec.IntValue                            RARITY_4_REQUIRED_BOOKSHELVES;
    public static final ForgeConfigSpec.IntValue                            RARITY_4_REQUIRED_LAPIS;
    public static final ForgeConfigSpec.ConfigValue<? extends String>       RARITY_4_FLOOR_BLOCK_TYPE;
    public static final ForgeConfigSpec.DoubleValue                         RARITY_4_REROLL_GEM_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> RARITY_4_ENCHANTMENTS;

    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> ANNOUNCEMENT_ENCHANTMENTS;

    public static final ForgeConfigSpec.ConfigValue<? extends Boolean>      REMOVE_MENDING_ENABLED;

    public static final ForgeConfigSpec.ConfigValue<? extends Boolean>      CLEAR_ENCHANTED_LOOT;
    public static final ForgeConfigSpec.ConfigValue<? extends Boolean>      REPLACE_ENCHANTED_LOOT;
    public static final ForgeConfigSpec.ConfigValue<? extends Boolean>      EXCLUDE_LOWER_TIER_LOOT;
    public static final ForgeConfigSpec.ConfigValue<? extends Boolean>      EXCLUDE_FISHING_LOOT;
    // Fortune nerf configs
    public static final ForgeConfigSpec.ConfigValue<? extends Boolean>      FORTUNE_NERF_ENABLED;
    public static final ForgeConfigSpec.DoubleValue                         FORTUNE_1_CHANCE;
    public static final ForgeConfigSpec.DoubleValue                         FORTUNE_2_CHANCE;
    public static final ForgeConfigSpec.DoubleValue                         FORTUNE_3_CHANCE;

    static {

        // Simple rarity builder default config
        BUILDER.push("Configs for Rechantment Mod");

        BUILDER.comment("Configs for Simple Rarity Enchantments");

        BUILDER.push("Book Rarities");

        // Simple rarity properties.
        BUILDER.push("Simple");
        RARITY_0_KEY = BUILDER.define("key", "simple");
        RARITY_0_COLOR = BUILDER.defineInRange("color", 11184810, 0, Integer.MAX_VALUE);
        RARITY_0_EXP_COST = BUILDER.defineInRange("exp_cost", 100, 0, Integer.MAX_VALUE);
        RARITY_0_WORLD_SPAWN_WEIGHT = BUILDER.defineInRange("spawn_weight", 40, 0, Integer.MAX_VALUE);
        RARITY_0_MIN_SUCCESS = BUILDER.defineInRange("min_success", 25, 0, 100);
        RARITY_0_MAX_SUCCESS = BUILDER.defineInRange("max_success", 90, 0, 100);
        RARITY_0_FORCED_BOOK_BREAKS = BUILDER.defineInRange("guaranteed_bookshelf_breaks", 0, 0, Integer.MAX_VALUE);
        RARITY_0_FORCED_FLOOR_BREAKS = BUILDER.defineInRange("guaranteed_floor_breaks", 0, 0, Integer.MAX_VALUE);
        RARITY_0_BOOK_BREAK_CHANCE = BUILDER.defineInRange("book_break_chance", 0.0, 0.0, 1.0);
        RARITY_0_FLOOR_BREAK_CHANCE = BUILDER.defineInRange("floor_break_chance", 0.05, 0.0, 1.0);
        RARITY_0_REQUIRED_BOOKSHELVES = BUILDER.defineInRange("required_bookshelves", 4, 0, Integer.MAX_VALUE);
        RARITY_0_REQUIRED_LAPIS = BUILDER.defineInRange("required_lapis", 2, 0, 64);
        RARITY_0_FLOOR_BLOCK_TYPE = BUILDER.define("floor_block_type", "minecraft:iron_block");
        RARITY_0_REROLL_GEM_CHANCE = BUILDER.defineInRange("reroll_gem_chance", 0.01, 0.0, 1.0);
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
        RARITY_1_COLOR = BUILDER.defineInRange("color", 5635925, 0, Integer.MAX_VALUE);
        RARITY_1_EXP_COST = BUILDER.defineInRange("exp_cost", 200, 0, Integer.MAX_VALUE);
        RARITY_1_WORLD_SPAWN_WEIGHT = BUILDER.defineInRange("spawn_weight", 30, 0, Integer.MAX_VALUE);
        RARITY_1_MIN_SUCCESS = BUILDER.defineInRange("min_success", 25, 0, 100);
        RARITY_1_MAX_SUCCESS = BUILDER.defineInRange("max_success", 90, 0, 100);
        RARITY_1_FORCED_BOOK_BREAKS = BUILDER.defineInRange("guaranteed_bookshelf_breaks", 0, 0, Integer.MAX_VALUE);
        RARITY_1_FORCED_FLOOR_BREAKS = BUILDER.defineInRange("guaranteed_floor_breaks", 0, 0, Integer.MAX_VALUE);
        RARITY_1_BOOK_BREAK_CHANCE = BUILDER.defineInRange("book_break_chance", 0.0, 0.0, 1.0);
        RARITY_1_FLOOR_BREAK_CHANCE = BUILDER.defineInRange("floor_break_chance", 0.045, 0.0, 1.0);
        RARITY_1_REQUIRED_BOOKSHELVES = BUILDER.defineInRange("required_bookshelves", 8, 0, Integer.MAX_VALUE);
        RARITY_1_REQUIRED_LAPIS = BUILDER.defineInRange("required_lapis", 3, 0, 64);
        RARITY_1_FLOOR_BLOCK_TYPE = BUILDER.define("floor_block_type", "minecraft:gold_block");
        RARITY_1_REROLL_GEM_CHANCE = BUILDER.defineInRange("reroll_gem_chance", 0.01, 0.0, 1.0);
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
        RARITY_2_COLOR = BUILDER.defineInRange("color", 5636095, 0, Integer.MAX_VALUE);
        RARITY_2_EXP_COST = BUILDER.defineInRange("exp_cost", 400, 0, Integer.MAX_VALUE);
        RARITY_2_WORLD_SPAWN_WEIGHT = BUILDER.defineInRange("spawn_weight", 15, 0, Integer.MAX_VALUE);
        RARITY_2_MIN_SUCCESS = BUILDER.defineInRange("min_success", 25, 0, 100);
        RARITY_2_MAX_SUCCESS = BUILDER.defineInRange("max_success", 90, 0, 100);
        RARITY_2_FORCED_BOOK_BREAKS = BUILDER.defineInRange("guaranteed_bookshelf_breaks", 0, 0, Integer.MAX_VALUE);
        RARITY_2_FORCED_FLOOR_BREAKS = BUILDER.defineInRange("guaranteed_floor_breaks", 0, 0, Integer.MAX_VALUE);
        RARITY_2_BOOK_BREAK_CHANCE = BUILDER.defineInRange("book_break_chance", 0.015, 0.0, 1.0);
        RARITY_2_FLOOR_BREAK_CHANCE = BUILDER.defineInRange("floor_break_chance", 0.03, 0.0, 1.0);
        RARITY_2_REQUIRED_BOOKSHELVES = BUILDER.defineInRange("required_bookshelves", 16, 0, Integer.MAX_VALUE);
        RARITY_2_REQUIRED_LAPIS = BUILDER.defineInRange("required_lapis", 3, 0, 64);
        RARITY_2_FLOOR_BLOCK_TYPE = BUILDER.define("floor_block_type", "minecraft:diamond_block");
        RARITY_2_REROLL_GEM_CHANCE = BUILDER.defineInRange("reroll_gem_chance", 0.02, 0.0, 1.0);

        ArrayList<String> rarity_2_default_enchantments = new ArrayList<>();
        rarity_2_default_enchantments.add("minecraft:looting|1|1-3|3,2,1");
        rarity_2_default_enchantments.add("minecraft:flame|1|1|1");
        rarity_2_default_enchantments.add("minecraft:frost_walker|1|2|1");
        rarity_2_default_enchantments.add("rechantment:berserk|1|3|1");
        rarity_2_default_enchantments.add("minecraft:depth_strider|1|3|1");
        rarity_2_default_enchantments.add("minecraft:soul_speed|1|3|1");
        rarity_2_default_enchantments.add("rechantment:hells_fury|1|4|1");
        rarity_2_default_enchantments.add("rechantment:voids_bane|1|4|1");
        rarity_2_default_enchantments.add("minecraft:fire_aspect|1|1-2|2,1");
        rarity_2_default_enchantments.add("minecraft:fortune|1|1-3|2,2,1");
        rarity_2_default_enchantments.add("rechantment:bash|1|1|1");
        RARITY_2_ENCHANTMENTS = BUILDER.defineList("enchantments", rarity_2_default_enchantments, s -> s instanceof String);

        BUILDER.pop();


        // Ultimate rarity builder default config
        BUILDER.comment("Configs for Ultimate Rarity Enchantments").push("ultimate");
        RARITY_3_KEY = BUILDER.define("key", "ultimate");
        RARITY_3_COLOR = BUILDER.defineInRange("color", 16777045, 0, Integer.MAX_VALUE);
        RARITY_3_EXP_COST = BUILDER.defineInRange("exp_cost", 500, 0, Integer.MAX_VALUE);
        RARITY_3_WORLD_SPAWN_WEIGHT = BUILDER.defineInRange("spawn_weight", 10, 0, Integer.MAX_VALUE);
        RARITY_3_MIN_SUCCESS = BUILDER.defineInRange("min_success", 30, 0, 100);
        RARITY_3_MAX_SUCCESS = BUILDER.defineInRange("max_success", 90, 0, 100);
        RARITY_3_FORCED_BOOK_BREAKS = BUILDER.defineInRange("guaranteed_bookshelf_breaks", 0, 0, Integer.MAX_VALUE);
        RARITY_3_FORCED_FLOOR_BREAKS = BUILDER.defineInRange("guaranteed_floor_breaks", 0, 0, Integer.MAX_VALUE);
        RARITY_3_BOOK_BREAK_CHANCE = BUILDER.defineInRange("book_break_chance", 0.015, 0.0, 1.0);
        RARITY_3_FLOOR_BREAK_CHANCE = BUILDER.defineInRange("floor_break_chance", 0.075, 0.0, 1.0);
        RARITY_3_REQUIRED_BOOKSHELVES = BUILDER.defineInRange("required_bookshelves", 32, 0, Integer.MAX_VALUE);
        RARITY_3_REQUIRED_LAPIS = BUILDER.defineInRange("required_lapis", 4, 0, 64);
        RARITY_3_FLOOR_BLOCK_TYPE = BUILDER.define("floor_block_type", "minecraft:emerald_block");
        RARITY_3_REROLL_GEM_CHANCE = BUILDER.defineInRange("reroll_gem_chance", 0.03, 0.0, 1.0);

        ArrayList<String> rarity_3_default_enchantments = new ArrayList<>();
        rarity_3_default_enchantments.add("minecraft:swift_sneak|1|1-3|3,2,1");
        rarity_3_default_enchantments.add("minecraft:luck_of_the_sea|1|2-3|2,1");
        rarity_3_default_enchantments.add("minecraft:sweeping|1|2-3|2,1");
        rarity_3_default_enchantments.add("minecraft:silk_touch|1|1|1");
        rarity_3_default_enchantments.add("rechantment:ice_aspect|1|1-2|2,1");
        rarity_3_default_enchantments.add("rechantment:vein_miner|1|1|1");
        rarity_3_default_enchantments.add("rechantment:timber|1|1|1");
        rarity_3_default_enchantments.add("minecraft:multishot|1|1|1");
        rarity_3_default_enchantments.add("minecraft:channeling|1|1|1");
        rarity_3_default_enchantments.add("rechantment:courage|1|1|1");
        rarity_3_default_enchantments.add("rechantment:telepathy|1|1|1");
        RARITY_3_ENCHANTMENTS = BUILDER.defineList("enchantments", rarity_3_default_enchantments, s -> s instanceof String);

        BUILDER.pop();


        // Legendary rarity builder default config
        BUILDER.comment("Configs for Legendary Rarity Enchantments").push("legendary");
        RARITY_4_KEY = BUILDER.define("key", "legendary");
        RARITY_4_COLOR = BUILDER.defineInRange("color", 16755200, 0, Integer.MAX_VALUE);
        RARITY_4_EXP_COST = BUILDER.defineInRange("exp_cost", 2000, 0, Integer.MAX_VALUE);
        RARITY_4_WORLD_SPAWN_WEIGHT = BUILDER.defineInRange("spawn_weight", 5, 0, Integer.MAX_VALUE);
        RARITY_4_MIN_SUCCESS = BUILDER.defineInRange("min_success", 35, 0, 100);
        RARITY_4_MAX_SUCCESS = BUILDER.defineInRange("max_success", 90, 0, 100);
        RARITY_4_FORCED_BOOK_BREAKS = BUILDER.defineInRange("guaranteed_bookshelf_breaks", 0, 0, Integer.MAX_VALUE);
        RARITY_4_FORCED_FLOOR_BREAKS = BUILDER.defineInRange("guaranteed_floor_breaks", 0, 0, Integer.MAX_VALUE);
        RARITY_4_BOOK_BREAK_CHANCE = BUILDER.defineInRange("book_break_chance", 0.015, 0.0, 1.0);
        RARITY_4_FLOOR_BREAK_CHANCE = BUILDER.defineInRange("floor_break_chance", 0.08, 0.0, 1.0);
        RARITY_4_REQUIRED_BOOKSHELVES = BUILDER.defineInRange("required_bookshelves", 45, 0, Integer.MAX_VALUE);
        RARITY_4_REQUIRED_LAPIS = BUILDER.defineInRange("required_lapis", 5, 0, 64);
        RARITY_4_FLOOR_BLOCK_TYPE = BUILDER.define("floor_block_type", "minecraft:ancient_debris");
        RARITY_4_REROLL_GEM_CHANCE = BUILDER.defineInRange("reroll_gem_chance", 0.05, 0.0, 1.0);
        ArrayList<String> rarity_4_default_enchantments = new ArrayList<>();
        rarity_4_default_enchantments.add("minecraft:infinity|1|1|1");
        rarity_4_default_enchantments.add("rechantment:inquisitive|1|1-4|4,3,2,1");
        rarity_4_default_enchantments.add("rechantment:wisdom|1|1-2|2,1");
        rarity_4_default_enchantments.add("rechantment:overload|1|1-3|4,2,1");
        rarity_4_default_enchantments.add("minecraft:mending|1|1|1");
        rarity_4_default_enchantments.add("rechantment:thunder_strike|1|1-2|2,1");
        rarity_4_default_enchantments.add("rechantment:rebirth|1|1-3|1,1,1");
        RARITY_4_ENCHANTMENTS = BUILDER.defineList("enchantments", rarity_4_default_enchantments, s -> s instanceof String);


        BUILDER.pop();
        BUILDER.pop();

        BUILDER.push("Announce Rare Drop List");
        BUILDER.comment("The game will broadcast a message to all players if a player gets any listed enchantments within the level range to drop from the enchantment table",
                "Format: <enchantment>|<level-range>",
                "Example: minecraft:unbreaking|1-3");
        ArrayList<String> announce_enchantments = new ArrayList<>();
        announce_enchantments.add("rechantment:overload|3");
        announce_enchantments.add("rechantment:thunder_strike|2");
        announce_enchantments.add("minecraft:fortune|3");
        announce_enchantments.add("rechantment:rebirth|1-3");
        ANNOUNCEMENT_ENCHANTMENTS = BUILDER.defineList("announce_enchantments", announce_enchantments, s -> s instanceof String);

        BUILDER.pop();

        BUILDER.push("Remove Mending");
        BUILDER.comment("Removes mending from found enchanted loot from generated world loot. Ex: end_city_treasure");
        BUILDER.comment("Mending books can be found only if you have them set in a rarity pool. Vanilla books are off be default therefore you cannot village trade obtain them.");
        REMOVE_MENDING_ENABLED = BUILDER.define("remove_mending", true);

        BUILDER.pop();

        BUILDER.comment("Configurations for all things related to generated loot drops.");
        BUILDER.push("Loot Table Enhancements");
        BUILDER.comment("Clears enchantments from found generated loot resulting in blank pieces of gear");
        BUILDER.comment("Example: A chest plate found with enchants in the end city loot will be blank with no enchants");
        CLEAR_ENCHANTED_LOOT = BUILDER.define("clear_enchanted_loot", false);

        BUILDER.comment("Replace all enchanted loot into Rechantment books");
        BUILDER.comment("Example: A chest plate found with enchants will be replaced entirely with a rolled enchanted book based on the rarity configs");
        REPLACE_ENCHANTED_LOOT = BUILDER.define("replace_enchanted_loot", false);

        BUILDER.comment("Excludes gold, leather, stone, wood enchanted drops from being affected by the REPLACE_ENCHANTED_LOOT configuration");
        BUILDER.comment("Example: Gold tools and armor from nether port ruins will remain and not be replaced by Rechantment books");
        EXCLUDE_LOWER_TIER_LOOT = BUILDER.define("exclude_lower_tier_loot", false);

        BUILDER.comment("Excludes fishing drops from being affected by the REPLACE_ENCHANTED_LOOT configuration");
        BUILDER.comment("Example: Enchanted bows and fishing rods will not be replaced with Rechantment books");
        EXCLUDE_FISHING_LOOT = BUILDER.define("exclude_fishing_loot", false);

        BUILDER.pop();

        BUILDER.push("Fortune Nerf");
        BUILDER.comment(
                "If enabled, fortune will only double drops based on the chances defined at each level");
        FORTUNE_NERF_ENABLED = BUILDER.define("nerf_enabled", false);
        FORTUNE_1_CHANCE = BUILDER.defineInRange("fortune_1_chance", 0.33, 0.0, 1.0);
        FORTUNE_2_CHANCE = BUILDER.defineInRange("fortune_2_chance", 0.5, 0.0, 1.0);
        FORTUNE_3_CHANCE = BUILDER.defineInRange("fortune_3_chance", 0.65, 0.0, 1.0);

        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}

