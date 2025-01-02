package net.tagtart.rechantment.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Optional;

public class HoverableGuiRenderable implements Renderable {

    protected ResourceLocation renderTexture;

    // Item icons are 16x16 by default, but these can be set to any
    // texture size. Entire image will render by default.
    protected int imageWidth = 16;
    protected int imageHeight = 16;

    protected int imageViewWidth = imageWidth;
    protected int imageViewHeight = imageHeight;

    // Offset positions relative to parent gui origin.
    protected int renderOffsetPosX = 0;
    protected int renderOffsetPosY = 0;

    // UV offset for rendering
    protected int renderUVOffsetU = 0;
    protected int renderUVOffsetV = 0;

    protected ArrayList<Component> customTooltipLines;
    protected boolean hoveredLastFrame  = false;
    protected boolean hoveredThisFrame  = false;
    protected boolean leftMouseClicked  = false;    // True if left mouse is currently clicked and is on top of renderable.
    protected boolean rightMouseClicked = false;    // True if right mouse is currently clicked and is on top of renderable.

    public final String identifier;

    public HoverableGuiRenderable(ResourceLocation textureResource, String pIdentifier, int posX, int posY) {
        renderOffsetPosX = posX;
        renderOffsetPosY = posY;
        renderTexture = textureResource;
        identifier = pIdentifier;

        customTooltipLines = new ArrayList<>();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta)
    {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, renderTexture);

        guiGraphics.blit(renderTexture, renderOffsetPosX, renderOffsetPosY, renderUVOffsetU, renderUVOffsetV, imageViewWidth, imageViewHeight, imageWidth, imageHeight);

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

    protected void onClickMouse() {

    }

    protected void onReleaseMouse() {

    }

    private void onClickMouse(int pButton) {
        if (pButton == 0 && !leftMouseClicked) {
            leftMouseClicked = true;
        }
        if (pButton == 1 && !rightMouseClicked) {
            rightMouseClicked = true;
        }

        onClickMouse();
    }

    private void onReleaseMouse(int pButton) {
        if (pButton == 0 && leftMouseClicked) {
            leftMouseClicked = false;
        }
        if (pButton == 1 && rightMouseClicked) {
            rightMouseClicked = false;
        }

        onReleaseMouse();
    }


    public boolean tryClickMouse(double pMouseX, double pMouseY, int pButton) {
        if (isMouseOverlapped((int)Math.round(pMouseX), (int)Math.round(pMouseY))) {
            onClickMouse(pButton);
            return true;
        }

        return false;
    }

    public boolean tryReleaseMouse(double pMouseX, double pMouseY, int pButton) {
        if (isMouseOverlapped((int)Math.round(pMouseX), (int)Math.round(pMouseY))) {
            onReleaseMouse(pButton);
            return true;
        }

        return false;
    }

    protected void renderCustomTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        if (!customTooltipLines.isEmpty()) {
            guiGraphics.renderTooltip(Minecraft.getInstance().font, customTooltipLines, Optional.empty(), mouseX, mouseY);
        }
    }

    // Uses point intersection with 2D AABB to determine if mouse is over the renderable.
    public boolean isMouseOverlapped(int mouseX, int mouseY) {
        int minX = renderOffsetPosX;
        int minY = renderOffsetPosY;
        int maxX = renderOffsetPosX + imageViewHeight;
        int maxY = renderOffsetPosY + imageViewWidth;

        return minX <= mouseX && maxX >= mouseX && minY <= mouseY && maxY >= mouseY;
    }

}

