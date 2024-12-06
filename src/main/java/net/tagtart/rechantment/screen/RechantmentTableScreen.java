package net.tagtart.rechantment.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.tagtart.rechantment.Rechantment;
import net.tagtart.rechantment.util.AllBookProperties;

import java.util.ArrayList;

public class RechantmentTableScreen extends AbstractContainerScreen<RechantmentTableMenu> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Rechantment.MOD_ID, "textures/gui/enchantment_table.png");

    private ArrayList<HoverableItemRenderable> hoverables;

    public RechantmentTableScreen(RechantmentTableMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();

        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;

        // Why isn't there an easy to just read these from the PNG?
        this.imageWidth = 176;
        this.imageHeight = 90;

        this.leftPos = (width - imageWidth) / 2;
        this.topPos = (height - imageHeight) / 2;

        ResourceLocation simpleLocation = new ResourceLocation(Rechantment.MOD_ID, "textures/item/simple.png");
        ResourceLocation uniqueLocation = new ResourceLocation(Rechantment.MOD_ID, "textures/item/unique.png");
        ResourceLocation eliteLocation = new ResourceLocation(Rechantment.MOD_ID, "textures/item/elite.png");
        ResourceLocation ultimateLocation = new ResourceLocation(Rechantment.MOD_ID, "textures/item/ultimate.png");
        ResourceLocation legendaryLocation = new ResourceLocation(Rechantment.MOD_ID, "textures/item/legendary.png");

        hoverables = new ArrayList<HoverableItemRenderable>();
        hoverables.add(new HoverableItemRenderable(simpleLocation, AllBookProperties.SIMPLE_PROPERTIES, leftPos + 10, topPos + 44));
        hoverables.add(new HoverableItemRenderable(uniqueLocation, AllBookProperties.UNIQUE_PROPERTIES, leftPos + 150, topPos + 44));
        hoverables.add(new HoverableItemRenderable(eliteLocation, AllBookProperties.ELITE_PROPERTIES, leftPos + 41,  topPos + 41));
        hoverables.add(new HoverableItemRenderable(ultimateLocation, AllBookProperties.ULTIMATE_PROPERTIES, leftPos + 116, topPos + 41));
        hoverables.add(new HoverableItemRenderable(legendaryLocation, AllBookProperties.LEGENDARY_PROPERTIES,leftPos + 77, topPos + 37));
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);


        guiGraphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {

        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);

        // Render all hoverables. They will handle tooltip rendering as well.
        for (HoverableItemRenderable hoverable : hoverables) {
            hoverable.render(guiGraphics, mouseX, mouseY, delta);
        }

        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        // Check mouse position:
        // if mouse overlaps a custom Hoverable and was left-clicked:

        // else if right-clicked:
        // display pools? might have to have pool display code contained in this class too.
        return false;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
