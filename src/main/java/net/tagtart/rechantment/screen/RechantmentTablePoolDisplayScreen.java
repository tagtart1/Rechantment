package net.tagtart.rechantment.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.Level;
import net.tagtart.rechantment.Rechantment;
import net.tagtart.rechantment.block.entity.RechantmentTableBlockEntity;
import net.tagtart.rechantment.util.BookRarityProperties;
import net.tagtart.rechantment.util.UtilFunctions;

import java.awt.geom.RoundRectangle2D;
import java.awt.print.Book;
import java.util.ArrayList;

public class RechantmentTablePoolDisplayScreen extends AbstractContainerScreen<RechantmentTablePoolDisplayMenu> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Rechantment.MOD_ID, "textures/gui/enchant_table_loot_pool_screen.png");

    private static final ResourceLocation LEFT_ARROW_LOCATION = new ResourceLocation(Rechantment.MOD_ID, "textures/gui/arrow_button_left.png");
    private static final ResourceLocation RIGHT_ARROW_LOCATION = new ResourceLocation(Rechantment.MOD_ID, "textures/gui/arrow_button_right.png");

    private static final ResourceLocation LINE_SHADER_LOCATION = new ResourceLocation(Rechantment.MOD_ID, "shaders/program/ench_table_line_shader");
    private static final ResourceLocation SIMPLE_LINE_LOCATION = new ResourceLocation(Rechantment.MOD_ID, "textures/gui/enchant_table_loot_pool_effect_simple.png");
    private static final ResourceLocation UNIQUE_LINE_LOCATION = new ResourceLocation(Rechantment.MOD_ID, "textures/gui/enchant_table_loot_pool_effect_unique.png");
    private static final ResourceLocation ELITE_LINE_LOCATION = new ResourceLocation(Rechantment.MOD_ID, "textures/gui/enchant_table_loot_pool_effect_elite.png");
    private static final ResourceLocation ULTIMATE_LINE_LOCATION = new ResourceLocation(Rechantment.MOD_ID, "textures/gui/enchant_table_loot_pool_effect_ultimate.png");
    private static final ResourceLocation LEGENDARY_LINE_LOCATION = new ResourceLocation(Rechantment.MOD_ID, "textures/gui/enchant_table_loot_pool_effect_legendary.png");
    private ResourceLocation[] shaderEffectsByBookID;
    private ShaderInstance lineShader;

    private RechantmentTableBlockEntity blockEntity; // Mostly just to provide this back to main enchant screen when switching back to
    private Level level;                             // Also just to provide back to main enchant screen.

    private ArrayList<HoverableGuiRenderable> hoverables;

    private final String LEFT_ARROW_IDENTIFIER = "left_arrow";
    private final String RIGHT_ARROW_IDENTIFIER = "right_arrow";

    private int viewingPropertyIndex = 0;
    private float timeElapsed = 0.0f;

    public RechantmentTablePoolDisplayScreen(RechantmentTablePoolDisplayMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;

        this.imageWidth = 180;
        this.imageHeight = 222;

        this.leftPos = (width - imageWidth) / 2;
        this.topPos = (height - imageHeight) / 2;

        hoverables = new ArrayList<>();
        hoverables.add(new HoverableButtonGuiRenderable(LEFT_ARROW_LOCATION, LEFT_ARROW_IDENTIFIER, leftPos + 17, topPos + 26));
        hoverables.add(new HoverableButtonGuiRenderable(RIGHT_ARROW_LOCATION, RIGHT_ARROW_IDENTIFIER, leftPos + 129, topPos + 26));

        this.shaderEffectsByBookID = new ResourceLocation[5];
        this.shaderEffectsByBookID[0] = SIMPLE_LINE_LOCATION;
        this.shaderEffectsByBookID[1] = UNIQUE_LINE_LOCATION;
        this.shaderEffectsByBookID[2] = ELITE_LINE_LOCATION;
        this.shaderEffectsByBookID[3] = ULTIMATE_LINE_LOCATION;
        this.shaderEffectsByBookID[4] = LEGENDARY_LINE_LOCATION;

        this.lineShader = UtilFunctions.loadShader(LINE_SHADER_LOCATION);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        timeElapsed += pPartialTick;

        guiGraphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, imageWidth, imageHeight, imageWidth, imageHeight);

        lineShader.safeGetUniform("Time").set(timeElapsed);
        lineShader.safeGetUniform("Resolution").set((float)imageWidth, (float)imageHeight);

        RenderSystem.setShader(() -> lineShader);
        /*RenderSystem.activeTexture(GL_TEXTURE0);
        Minecraft.getInstance().getTextureManager().bindForSetup(TEXTURE);
        lineShader.safeGetUniform("MainDiffuse").set(0);*/

        Minecraft.getInstance().getTextureManager().bindForSetup(shaderEffectsByBookID[viewingPropertyIndex]);
        lineShader.safeGetUniform("LineEffectMap").set(0);

        lineShader.apply();

        UtilFunctions.fakeInnerBlit(guiGraphics, this.leftPos, this.leftPos + imageWidth, this.topPos, this.topPos + imageHeight, 0,
                0.0f, 1.0f, 0.0f, 1.0f);

        this.font.drawInBatch("Fart", 20, 20, 0xFFFFFF, true, guiGraphics.pose().last().pose(), Minecraft.getInstance().renderBuffers().bufferSource(), Font.DisplayMode.NORMAL, 0, 0xF000F0);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {

        renderBackground(guiGraphics);

        super.render(guiGraphics, mouseX, mouseY, delta);

        // Render all hoverables. They will handle tooltip rendering as well.
        for (HoverableGuiRenderable hoverable : hoverables) {
            hoverable.render(guiGraphics, mouseX, mouseY, delta);
        }

        renderTooltip(guiGraphics, mouseX, mouseY);

    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {

        for (HoverableGuiRenderable hoverable : hoverables) {
            if (hoverable.tryClickMouse(pMouseX, pMouseY, pButton)) {

                // Left arrow, go back in rarity index
                if (hoverable.identifier.equals(LEFT_ARROW_IDENTIFIER)) {
                    setViewingPropertyIndex(viewingPropertyIndex - 1);
                }

                // Right arrow, go up in rarity index
                if (hoverable.identifier.equals(RIGHT_ARROW_IDENTIFIER)) {
                    setViewingPropertyIndex(viewingPropertyIndex + 1);
                }
            }
        }

        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {

        for (HoverableGuiRenderable hoverable : hoverables) {
            hoverable.tryReleaseMouse(pMouseX, pMouseY, pButton);
        }

        return super.mouseReleased(pMouseX, pMouseY, pButton);
    }

    private void setViewingPropertyIndex(int index) {
        if (index < 0)
            index = BookRarityProperties.getAllProperties().length - 1;
        index = index % BookRarityProperties.getAllProperties().length;
        viewingPropertyIndex = index;
    }

    private BookRarityProperties getCurrentViewingProperties() {
        return BookRarityProperties.getAllProperties()[viewingPropertyIndex];
    }

}
