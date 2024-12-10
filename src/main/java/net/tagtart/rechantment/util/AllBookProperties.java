package net.tagtart.rechantment.util;

import net.minecraft.world.level.block.Blocks;

// TODO: REMOVE THIS ONCE CONFIGS ARE SET UP. MOVE TO DIFFERENT CLASS.
public class AllBookProperties {

    private static BookRequirementProperties[] ALL_BOOK_PROPERTIES;

    public static BookRequirementProperties[] getAllProperties() {
        if (ALL_BOOK_PROPERTIES != null) {
            return ALL_BOOK_PROPERTIES;
        }

        // HARD CODING THESE FOR NOW. NEED TO READ FROM CONFIG LATER!
        ALL_BOOK_PROPERTIES = new BookRequirementProperties[5];
        ALL_BOOK_PROPERTIES[0] = new BookRequirementProperties(
                1.0f,
                100,
                0.0f,
                4,
                0.05f,
                Blocks.IRON_BLOCK
        );

        ALL_BOOK_PROPERTIES[1] = new BookRequirementProperties(
                2.0f,
                200,
                0.0f,
                8,
                0.045f,
                Blocks.GOLD_BLOCK
        );

        ALL_BOOK_PROPERTIES[2] = new BookRequirementProperties(
                3.0f,
                400,
                0.015f,
                16,
                0.03f,
                Blocks.DIAMOND_BLOCK
        );

        ALL_BOOK_PROPERTIES[3] = new BookRequirementProperties(
                4.0f,
                500,
                0.015f,
                32,
                0.075f,
                Blocks.EMERALD_BLOCK
        );

        ALL_BOOK_PROPERTIES[4] = new BookRequirementProperties(
                5.0f,
                2000,
                0.015f,
                45,
                0.08f,
                Blocks.ANCIENT_DEBRIS
        );
    }
}
