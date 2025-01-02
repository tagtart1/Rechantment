package net.tagtart.rechantment.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class HoverableButtonGuiRenderable extends HoverableGuiRenderable{
    public HoverableButtonGuiRenderable(ResourceLocation textureResource, String identifier, int posX, int posY) {
        super(textureResource, identifier, posX, posY);
        imageWidth = 48;
        imageHeight = 16;

        imageViewWidth = 16;
        imageViewHeight = 16;

        renderUVOffsetU = 16;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        super.render(guiGraphics, mouseX, mouseY, delta);

        // Clicked and hovered
        if (leftMouseClicked) {
            renderUVOffsetU = 0;
        }

        // Hovered but not clicked
        else if (hoveredThisFrame) {
            renderUVOffsetU = 32;
        }
    }

    @Override
    protected void onHoverEnd() {
        super.onHoverEnd();
        renderUVOffsetU = 16;
    }
}
