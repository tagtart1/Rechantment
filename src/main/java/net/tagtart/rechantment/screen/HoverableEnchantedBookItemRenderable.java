package net.tagtart.rechantment.screen;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.EnchantmentTableBlockEntity;
import net.tagtart.rechantment.util.AllBookProperties;
import net.tagtart.rechantment.util.BookRequirementProperties;

public class HoverableEnchantedBookItemRenderable extends HoverableItemRenderable {

    public BookRequirementProperties bookProperties;
    public int propertiesIndex;

    protected RechantmentTableScreen screen;

    protected EnchantmentTableBlockEntity enchantmentTable;

    public HoverableEnchantedBookItemRenderable(RechantmentTableScreen pScreen, int pPropertiesIndex, ResourceLocation textureResource, int posX, int posY) {
        super(pScreen.playerInventory, textureResource, posX, posY);
        propertiesIndex = pPropertiesIndex;
        bookProperties = AllBookProperties.getAllProperties()[propertiesIndex];
        screen = pScreen;
    }

    @Override
    protected void onHoverStart() {
        // If we just hovered over this object, create tooltip info (ensure it's updated if info, like exp, changed at any point)
        customTooltipLines = screen.getEnchantTableTooltipLines(bookProperties);
    }

    @Override
    protected void onHoverEnd() {
        customTooltipLines.clear();
    }
}
