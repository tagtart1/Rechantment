package net.tagtart.rechantment.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.tagtart.rechantment.item.ModItems;
import net.tagtart.rechantment.util.BookRequirementProperties;

public class HoverableItemRenderable implements Renderable {

    protected final ResourceLocation texture;
    protected final BookRequirementProperties bookProperties;

    // Item icons are 16x16, so we draw entire texture given
    // (Have to assume it's 16x16 as well)
    protected int imageWidth = 16;
    protected int imageHeight = 16;

    // Offset positions relative to parent gui origin.
    protected int renderOffsetPosX;
    protected int renderOffsetPosY;

    public HoverableItemRenderable(ResourceLocation textureResource, BookRequirementProperties properties, int posX, int posY)
    {
        renderOffsetPosX = posX;
        renderOffsetPosY = posY;
        texture = textureResource;
        bookProperties = properties;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta)
    {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, texture);

        guiGraphics.blit(texture, renderOffsetPosX, renderOffsetPosY, 0, 0, imageWidth, imageHeight, imageWidth, imageHeight);
        //guiGraphics.renderFakeItem(itemStack, renderOffsetPosX, renderOffsetPosY);
        //guiGraphics.fill(parentOriginX, parentOriginY, parentOriginX + renderOffsetPosX, parentOriginY + renderOffsetPosY, 1);
    }
}

