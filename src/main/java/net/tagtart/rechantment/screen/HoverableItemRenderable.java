package net.tagtart.rechantment.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;
import java.util.Optional;

public class HoverableItemRenderable implements Renderable {

    protected final ResourceLocation texture;

    protected Inventory playerInventory;

    // Item icons are 16x16 by default, but these can be set to any
    // texture size. Entire image will render by default.
    protected int imageWidth = 16;
    protected int imageHeight = 16;

    // Offset positions relative to parent gui origin.
    protected int renderOffsetPosX;
    protected int renderOffsetPosY;

    protected ArrayList<Component> customTooltipLines;
    protected boolean hoveredLastFrame = false;
    protected boolean hoveredThisFrame = false;

    public HoverableItemRenderable(Inventory pPlayerInventory, ResourceLocation textureResource, int posX, int posY)
    {
        this(textureResource, posX, posY);
        playerInventory = pPlayerInventory;
    }

    public HoverableItemRenderable(ResourceLocation textureResource, int posX, int posY) {
        renderOffsetPosX = posX;
        renderOffsetPosY = posY;
        texture = textureResource;

        customTooltipLines = new ArrayList<>();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta)
    {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, texture);

        guiGraphics.blit(texture, renderOffsetPosX, renderOffsetPosY, 0, 0, imageWidth, imageHeight, imageWidth, imageHeight);

        hoveredThisFrame = isMouseOverlapped(mouseX, mouseY);
        if (hoveredThisFrame) {
            if (!hoveredLastFrame) {
                onHoverStart();
            }
            renderCustomTooltip(guiGraphics, mouseX, mouseY);
        }
        else {
            if (hoveredLastFrame) {
                onHoverEnd();
            }
        }

        hoveredLastFrame = hoveredThisFrame;
    }

    protected void onHoverStart() {

    }

    protected void onHoverEnd() {

    }

    private void renderCustomTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.renderTooltip(Minecraft.getInstance().font, customTooltipLines, Optional.empty(), mouseX, mouseY);
    }

    // Uses point intersection with 2D AABB to determine if mouse is over the renderable.
    public boolean isMouseOverlapped(int mouseX, int mouseY) {
        int minX = renderOffsetPosX;
        int minY = renderOffsetPosY;
        int maxX = renderOffsetPosX + imageWidth;
        int maxY = renderOffsetPosY + imageHeight;

        return minX <= mouseX && maxX >= mouseX && minY <= mouseY && maxY >= mouseY;
    }

}

