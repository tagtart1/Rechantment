package net.tagtart.rechantment.screen;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.EnchantmentTableBlockEntity;
import net.tagtart.rechantment.util.BookRarityProperties;

import java.awt.print.Book;

public class HoverableEnchantedBookItemRenderable extends HoverableItemRenderable {

    public BookRarityProperties bookProperties;
    public int propertiesIndex;

    protected RechantmentTableScreen screen;

    protected EnchantmentTableBlockEntity enchantmentTable;

    public HoverableEnchantedBookItemRenderable(RechantmentTableScreen pScreen, int pPropertiesIndex, ResourceLocation textureResource, int posX, int posY) {
        super(pScreen.playerInventory, textureResource, posX, posY);
        propertiesIndex = pPropertiesIndex;
        bookProperties = BookRarityProperties.getAllProperties()[propertiesIndex];
        screen = pScreen;
    }

    @Override
    protected void onHoverStart() {
        updateTooltipLines();
    }

    @Override
    protected void onHoverEnd() {
        customTooltipLines.clear();
    }

    public void updateTooltipLines() {
        // If we just hovered over this object, create tooltip info (ensure it's updated if info, like exp, changed at any point)
        customTooltipLines = screen.getEnchantTableTooltipLines(bookProperties);
    }
}
