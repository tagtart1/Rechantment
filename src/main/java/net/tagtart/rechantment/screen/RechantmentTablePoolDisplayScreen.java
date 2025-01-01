package net.tagtart.rechantment.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractScrollWidget;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.Level;
import net.tagtart.rechantment.block.entity.RechantmentTableBlockEntity;

public class RechantmentTablePoolDisplayScreen extends AbstractContainerScreen<RechantmentTablePoolDisplayMenu> {

    private RechantmentTableBlockEntity blockEntity; // Mostly just to provide this back to main enchant screen when switching back to
    private Level level;                             // Also just to provide back to main enchant screen.

    public RechantmentTablePoolDisplayScreen(RechantmentTablePoolDisplayMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);

        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;

        this.leftPos = (width - imageWidth) / 2;
        this.topPos = (height - imageHeight) / 2;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {

        this.font.drawInBatch("Fart", 20, 20, 0xFFFFFF, true, guiGraphics.pose().last().pose(), Minecraft.getInstance().renderBuffers().bufferSource(), Font.DisplayMode.NORMAL, 0, 0xF000F0);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {

        renderBackground(guiGraphics);

        super.render(guiGraphics, mouseX, mouseY, delta);

        // Render all hoverables. They will handle tooltip rendering as well.
//        for (HoverableItemRenderable hoverable : hoverables) {
//            hoverable.render(guiGraphics, mouseX, mouseY, delta);
//        }
    }
}
