package net.tagtart.rechantment.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.tagtart.rechantment.Rechantment;
import net.tagtart.rechantment.util.AllBookProperties;
import net.tagtart.rechantment.util.BookRequirementProperties;
import net.tagtart.rechantment.util.UtilFunctions;
import org.jetbrains.annotations.NotNull;
import oshi.util.tuples.Pair;

import java.util.ArrayList;

public class RechantmentTableScreen extends AbstractContainerScreen<RechantmentTableMenu> {

    public static final ResourceLocation simpleLocation = new ResourceLocation(Rechantment.MOD_ID, "textures/item/simple.png");
    public static final ResourceLocation uniqueLocation = new ResourceLocation(Rechantment.MOD_ID, "textures/item/unique.png");
    public static final ResourceLocation eliteLocation = new ResourceLocation(Rechantment.MOD_ID, "textures/item/elite.png");
    public static final ResourceLocation ultimateLocation = new ResourceLocation(Rechantment.MOD_ID, "textures/item/ultimate.png");
    public static final ResourceLocation legendaryLocation = new ResourceLocation(Rechantment.MOD_ID, "textures/item/legendary.png");

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Rechantment.MOD_ID, "textures/gui/enchantment_table.png");

    private ArrayList<HoverableEnchantedBookItemRenderable> hoverables;

    public Inventory playerInventory;


    public RechantmentTableScreen(RechantmentTableMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        playerInventory = pPlayerInventory;
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

        hoverables = new ArrayList<>();
        hoverables.add(new HoverableEnchantedBookItemRenderable(this, AllBookProperties.SIMPLE_PROPERTIES, simpleLocation, leftPos + 10, topPos + 44));
        hoverables.add(new HoverableEnchantedBookItemRenderable(this, AllBookProperties.UNIQUE_PROPERTIES, uniqueLocation,  leftPos + 150, topPos + 44));
        hoverables.add(new HoverableEnchantedBookItemRenderable(this,  AllBookProperties.ELITE_PROPERTIES, eliteLocation,leftPos + 41,  topPos + 41));
        hoverables.add(new HoverableEnchantedBookItemRenderable(this, AllBookProperties.ULTIMATE_PROPERTIES, ultimateLocation, leftPos + 116, topPos + 41));
        hoverables.add(new HoverableEnchantedBookItemRenderable(this, AllBookProperties.LEGENDARY_PROPERTIES,legendaryLocation, leftPos + 77, topPos + 37));
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

        // If mouse overlaps a custom Hoverable and was left-clicked:
        if (pButton == 0) {
            for (HoverableEnchantedBookItemRenderable hoverable : hoverables) {
                if (hoverable.isMouseOverlapped((int) Math.round(pMouseX), (int) Math.round(pMouseY))) {
                    // Reward book based on properties.
                    // hoverable.bookProperties

                }
            }
        }

        // else if right-clicked:
        // display pools? might have to have pool display code contained in this class too.
        return false;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public ArrayList<Component> getEnchantTableTooltipLines(BookRequirementProperties properties) {

        ArrayList<Component> tooltipLines = new ArrayList<Component>();

        Pair<String, ChatFormatting> enchantRarityInfo = UtilFunctions.getRarityInfo(properties.rarity);

        // Tooltip "title", with rarity icon and generic book name.
        String rarityIcon = Component.translatable("enchantment.rarity." + enchantRarityInfo.getA()).getString();
        String bookTitle = Component.translatable("item.rechantment.enchantment_table.enchanted_book").withStyle(enchantRarityInfo.getB()).getString();
        tooltipLines.add(Component.literal(rarityIcon + " " + bookTitle).withStyle(enchantRarityInfo.getB()));
        tooltipLines.add(Component.literal(" "));

        tooltipLines.add(Component.translatable("item.rechantment.enchantment_table.enchanted_book_desc"));
        tooltipLines.add(Component.literal(" "));

        String experienceTitle = Component.translatable("tooltip.rechantment.enchantment_table.experience").getString();




        return tooltipLines;
    }

    public boolean playerMeetsExpRequirement(BookRequirementProperties bookProperties) {
        return playerInventory.player.totalExperience >= bookProperties.requiredExp;
    }

    public boolean playerMeetsBookshelfRequirement(BookRequirementProperties bookProperties) {
        return false;
    }

    public boolean playerMeetsFloorRequirement(BookRequirementProperties bookProperties) {
        return false;
    }
}
