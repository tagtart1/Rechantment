package net.tagtart.rechantment.util;

import net.minecraft.world.level.block.Blocks;

// TODO: REMOVE THIS ONCE CONFIGS ARE SET UP. MOVE TO DIFFERENT CLASS.
public class AllBookProperties {

    private static BookRarityProperties[] ALL_BOOK_PROPERTIES;

    public static BookRarityProperties[] getAllProperties() {
        if (ALL_BOOK_PROPERTIES != null) {
            return ALL_BOOK_PROPERTIES;
        }

        // HARD CODING THESE FOR NOW. NEED TO READ FROM CONFIG LATER!
        ALL_BOOK_PROPERTIES = new BookRarityProperties[5];
        ALL_BOOK_PROPERTIES[0] = new BookRarityProperties(
                1.0f,
                100,
                0.0f,
                4,
                0.90f,
                Blocks.IRON_BLOCK
        );

        ALL_BOOK_PROPERTIES[1] = new BookRarityProperties(
                2.0f,
                200,
                0.0f,
                8,
                0.045f,
                Blocks.GOLD_BLOCK
        );

        ALL_BOOK_PROPERTIES[2] = new BookRarityProperties(
                3.0f,
                400,
                0.015f,
                16,
                0.03f,
                Blocks.DIAMOND_BLOCK
        );

        ALL_BOOK_PROPERTIES[3] = new BookRarityProperties(
                4.0f,
                500,
                0.015f,
                32,
                0.075f,
                Blocks.EMERALD_BLOCK
        );

        ALL_BOOK_PROPERTIES[4] = new BookRarityProperties(
                5.0f,
                2000,
                0.015f,
                45,
                0.08f,
                Blocks.ANCIENT_DEBRIS
        );

        return ALL_BOOK_PROPERTIES;
    }
}
