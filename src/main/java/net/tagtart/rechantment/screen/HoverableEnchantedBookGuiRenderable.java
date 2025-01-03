package net.tagtart.rechantment.screen;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.EnchantmentTableBlockEntity;
import net.tagtart.rechantment.util.BookRarityProperties;

import java.util.ArrayList;
import java.util.function.Supplier;

public class HoverableEnchantedBookGuiRenderable extends HoverableGuiRenderable {

    public int propertiesIndex;
    public Supplier<ArrayList<Component>> tooltipSupplier; // So that a screen can supply a custom tooltip and change how it does that.

    public HoverableEnchantedBookGuiRenderable(Supplier<ArrayList<Component>> pTooltipSupplier, int pPropertiesIndex, int posX, int posY) {
        super(BookRarityProperties.getAllProperties()[pPropertiesIndex].iconResourceLocation, posX, posY);
        propertiesIndex = pPropertiesIndex;
        tooltipSupplier = pTooltipSupplier;
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
        customTooltipLines = tooltipSupplier.get();
    }
}
