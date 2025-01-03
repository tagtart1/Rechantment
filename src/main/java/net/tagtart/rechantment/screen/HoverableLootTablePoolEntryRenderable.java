package net.tagtart.rechantment.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantments;
import net.tagtart.rechantment.Rechantment;
import net.tagtart.rechantment.item.custom.EnchantedBookItem;

public class HoverableLootTablePoolEntryRenderable extends HoverableGuiRenderable{

    private static final ResourceLocation TABLE_ENTRY_BOX_LOCATION = new ResourceLocation(Rechantment.MOD_ID, "textures/gui/enchant_table_loot_pool_entry_box.png");

    private final int enchantmentInfoOffsetY = 13;

    private int propertiesIndex = 0;
    private Font renderFont;

    public HoverableLootTablePoolEntryRenderable(Font pRenderFont, int pPropertiesIndex, int posX, int posY) {
        super(TABLE_ENTRY_BOX_LOCATION, posX, posY);
        propertiesIndex = pPropertiesIndex;
        renderFont = pRenderFont;

        imageWidth = 144;
        imageHeight = 51;
        imageViewWidth = imageWidth;
        imageViewHeight = imageHeight;
        renderDefaultTexture = true;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        super.render(guiGraphics, mouseX, mouseY, delta);

        guiGraphics.pose().pushPose();
        float scaleFac = 0.75f;
        float invScaleFac = 1.0f / scaleFac;
        guiGraphics.pose().scale(scaleFac, scaleFac, scaleFac);
        guiGraphics.drawString(renderFont, "Thunder Strike", (renderOffsetPosX + 3) * invScaleFac, (getEntryLabelOffsetY()) * invScaleFac, 0xFFFFFF, true);
        renderFont.drawInBatch("Thunder Strike", (renderOffsetPosX + 3) * invScaleFac, (getEntryLabelOffsetY()) * invScaleFac, 0xFFFFFF, true, guiGraphics.pose().last().pose(), Minecraft.getInstance().renderBuffers().bufferSource(), Font.DisplayMode.NORMAL, 0, 0xF000F0);
        renderFont.drawInBatch("10.0%", (renderOffsetPosX + 119) * invScaleFac, (getEntryLabelOffsetY()) * invScaleFac, 0xFFFFFF, true, guiGraphics.pose().last().pose(), Minecraft.getInstance().renderBuffers().bufferSource(), Font.DisplayMode.NORMAL, 0, 0xF000F0);
        renderFont.drawInBatch("______________________________", (renderOffsetPosX + 3) * invScaleFac, (renderOffsetPosY + 15) * invScaleFac, 0xFFFFFF, true, guiGraphics.pose().last().pose(), Minecraft.getInstance().renderBuffers().bufferSource(), Font.DisplayMode.NORMAL, 0, 0xF000F0);
        renderFont.drawInBatch("______________________________", (renderOffsetPosX + 4) * invScaleFac, (renderOffsetPosY + 15) * invScaleFac, 0xFFFFFF, true, guiGraphics.pose().last().pose(), Minecraft.getInstance().renderBuffers().bufferSource(), Font.DisplayMode.NORMAL, 0, 0xF000F0);
        renderFont.drawInBatch(EnchantedBookItem.getApplicableIcons(Enchantments.UNBREAKING), (renderOffsetPosX + 3) * invScaleFac, (renderOffsetPosY + 5) * invScaleFac, 0x222222, false, guiGraphics.pose().last().pose(), Minecraft.getInstance().renderBuffers().bufferSource(), Font.DisplayMode.NORMAL, 0, 0xF000F0);
        renderFont.drawInBatch(EnchantedBookItem.getApplicableIcons(Enchantments.UNBREAKING), (renderOffsetPosX + 2) * invScaleFac, (renderOffsetPosY + 4) * invScaleFac, 0xFFFFFF, false, guiGraphics.pose().last().pose(), Minecraft.getInstance().renderBuffers().bufferSource(), Font.DisplayMode.NORMAL, 0, 0xF000F0);
        guiGraphics.pose().popPose();

        guiGraphics.pose().pushPose();
        scaleFac = 0.5f;
        invScaleFac = 1.0f / scaleFac;
        guiGraphics.pose().scale(scaleFac, scaleFac, scaleFac);
        renderFont.drawInBatch("I:", (renderOffsetPosX + 5) * invScaleFac, (getEntryLabelOffsetY() + 10) * invScaleFac, 0xFFFFFF, true, guiGraphics.pose().last().pose(), Minecraft.getInstance().renderBuffers().bufferSource(), Font.DisplayMode.NORMAL, 0, 0xF000F0);
        renderFont.drawInBatch("25.0%", (renderOffsetPosX + 124) * invScaleFac, (getEntryLabelOffsetY() + 10) * invScaleFac, 0xFFFFFF, true, guiGraphics.pose().last().pose(), Minecraft.getInstance().renderBuffers().bufferSource(), Font.DisplayMode.NORMAL, 0, 0xF000F0);
        renderFont.drawInBatch("II:", (renderOffsetPosX + 5) * invScaleFac, (getEntryLabelOffsetY() + 15) * invScaleFac, 0xFFFFFF, true, guiGraphics.pose().last().pose(), Minecraft.getInstance().renderBuffers().bufferSource(), Font.DisplayMode.NORMAL, 0, 0xF000F0);
        renderFont.drawInBatch("25.0%", (renderOffsetPosX + 124) * invScaleFac, (getEntryLabelOffsetY() + 15) * invScaleFac, 0xFFFFFF, true, guiGraphics.pose().last().pose(), Minecraft.getInstance().renderBuffers().bufferSource(), Font.DisplayMode.NORMAL, 0, 0xF000F0);
        renderFont.drawInBatch("III:", (renderOffsetPosX + 5) * invScaleFac, (getEntryLabelOffsetY() + 20) * invScaleFac, 0xFFFFFF, true, guiGraphics.pose().last().pose(), Minecraft.getInstance().renderBuffers().bufferSource(), Font.DisplayMode.NORMAL, 0, 0xF000F0);
        renderFont.drawInBatch("25.0%", (renderOffsetPosX + 124) * invScaleFac, (getEntryLabelOffsetY() + 20) * invScaleFac, 0xFFFFFF, true, guiGraphics.pose().last().pose(), Minecraft.getInstance().renderBuffers().bufferSource(), Font.DisplayMode.NORMAL, 0, 0xF000F0);
        renderFont.drawInBatch("IV:", (renderOffsetPosX + 5) * invScaleFac, (getEntryLabelOffsetY() + 25) * invScaleFac, 0xFFFFFF, true, guiGraphics.pose().last().pose(), Minecraft.getInstance().renderBuffers().bufferSource(), Font.DisplayMode.NORMAL, 0, 0xF000F0);
        renderFont.drawInBatch("25.0%", (renderOffsetPosX + 124) * invScaleFac, (getEntryLabelOffsetY() + 25) * invScaleFac, 0xFFFFFF, true, guiGraphics.pose().last().pose(), Minecraft.getInstance().renderBuffers().bufferSource(), Font.DisplayMode.NORMAL, 0, 0xF000F0);
        renderFont.drawInBatch("V:", (renderOffsetPosX + 5) * invScaleFac, (getEntryLabelOffsetY() + 30) * invScaleFac, 0xFFFFFF, true, guiGraphics.pose().last().pose(), Minecraft.getInstance().renderBuffers().bufferSource(), Font.DisplayMode.NORMAL, 0, 0xF000F0);
        renderFont.drawInBatch("25.0%", (renderOffsetPosX + 124) * invScaleFac, (getEntryLabelOffsetY() + 30) * invScaleFac, 0xFFFFFF, true, guiGraphics.pose().last().pose(), Minecraft.getInstance().renderBuffers().bufferSource(), Font.DisplayMode.NORMAL, 0, 0xF000F0);

        guiGraphics.pose().popPose();
    }

    private int getEntryLabelOffsetY() {
        return enchantmentInfoOffsetY + renderOffsetPosY;
    }
}
