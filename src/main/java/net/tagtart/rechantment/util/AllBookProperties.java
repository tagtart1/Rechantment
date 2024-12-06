package net.tagtart.rechantment.util;

import net.minecraft.world.level.block.Blocks;

// TODO: REMOVE THIS ONCE CONFIGS ARE SET UP.
public class AllBookProperties {

    public static BookRequirementProperties SIMPLE_PROPERTIES = new BookRequirementProperties(100, 0.0f, 4, 0.05f, Blocks.IRON_BLOCK);
    public static BookRequirementProperties UNIQUE_PROPERTIES = new BookRequirementProperties(200, 0.0f, 8, 0.045f, Blocks.GOLD_BLOCK);
    public static BookRequirementProperties ELITE_PROPERTIES = new BookRequirementProperties(400, 0.015f, 16, 0.03f, Blocks.DIAMOND_BLOCK);
    public static BookRequirementProperties ULTIMATE_PROPERTIES = new BookRequirementProperties(500, 0.015f, 32, 0.075f, Blocks.EMERALD_BLOCK);
    public static BookRequirementProperties LEGENDARY_PROPERTIES = new BookRequirementProperties(2000, 0.015f, 45, 0.08f, Blocks.ANCIENT_DEBRIS);

}
