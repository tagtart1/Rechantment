package net.tagtart.rechantment.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.tagtart.rechantment.Rechantment;
import net.tagtart.rechantment.util.BookRequirementProperties;
import net.tagtart.rechantment.util.UtilFunctions;
import oshi.util.tuples.Pair;

import java.util.ArrayList;
import java.util.List;

public class RechantmentTableScreen extends AbstractContainerScreen<RechantmentTableMenu> {

    public static final Style PINK_COLOR_STYLE = Style.EMPTY.withColor(0xFCB4B4);
    public static final Style MID_GRAY_COLOR_STYLE = Style.EMPTY.withColor(0xA8A8A8);

    public static final ResourceLocation SIMPLE_LOCATION = new ResourceLocation(Rechantment.MOD_ID, "textures/item/simple.png");
    public static final ResourceLocation UNIQUE_LOCATION = new ResourceLocation(Rechantment.MOD_ID, "textures/item/unique.png");
    public static final ResourceLocation ELITE_LOCATION = new ResourceLocation(Rechantment.MOD_ID, "textures/item/elite.png");
    public static final ResourceLocation ULTIMATE_LOCATION = new ResourceLocation(Rechantment.MOD_ID, "textures/item/ultimate.png");
    public static final ResourceLocation LEGENDARY_LOCATION = new ResourceLocation(Rechantment.MOD_ID, "textures/item/legendary.png");

    private static final Component grayHyphen = Component.literal("- ").withStyle(MID_GRAY_COLOR_STYLE);
    private static final Component whiteArrow = Component.literal("→ ").withStyle(ChatFormatting.WHITE);
    private static final Component redX = Component.literal("✘ ").withStyle(ChatFormatting.DARK_RED);

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Rechantment.MOD_ID, "textures/gui/enchantment_table.png");

    private ArrayList<HoverableEnchantedBookItemRenderable> hoverables;

    public Inventory playerInventory;

    private BlockState[] cachedBookshelvesInRange;
    private BlockState[] cachedFloorBlocksInRange;

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
        hoverables.add(new HoverableEnchantedBookItemRenderable(this, 0, SIMPLE_LOCATION, leftPos + 10, topPos + 44));
        hoverables.add(new HoverableEnchantedBookItemRenderable(this, 1, UNIQUE_LOCATION,  leftPos + 150, topPos + 44));
        hoverables.add(new HoverableEnchantedBookItemRenderable(this,  2, ELITE_LOCATION,leftPos + 41,  topPos + 41));
        hoverables.add(new HoverableEnchantedBookItemRenderable(this, 3, ULTIMATE_LOCATION, leftPos + 116, topPos + 41));
        hoverables.add(new HoverableEnchantedBookItemRenderable(this, 4, LEGENDARY_LOCATION, leftPos + 77, topPos + 37));
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
                    BookRequirementProperties properties = hoverable.bookProperties;

                    if (!expRequirementMet(properties)) {
                        // Send warning message to player, and close screen.
                        playerInventory.player.closeContainer();
                        break;
                    }

                    if (!floorRequirementsMet(properties) && !bookshelfRequirementsMet(properties)) {
                        // Just play sound, don't close
                        break;
                    }

                    // At this point, they meet the requirements.
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

        // VERY IMPORTANT to note. On client side, we only recompute if requirements are valid when tooltip is refreshed,
        // which only happens when a new item is initially hovered in this case. Server-side checks are in PurchaseEnchantedBookC2SPacket.
        refreshCachedBlockStates(properties);

        ArrayList<Component> tooltipLines = new ArrayList<>();

        Pair<String, ChatFormatting> enchantRarityInfo = UtilFunctions.getRarityInfo(properties.rarity);

        // Tooltip "title", with rarity icon and generic book name.
        Component rarityIcon = Component.translatable("enchantment.rarity." + enchantRarityInfo.getA());
        Component bookTitle = Component.translatable("item.rechantment.enchanted_book").withStyle(enchantRarityInfo.getB());
        tooltipLines.add(Component.literal(rarityIcon.getString()).append(" ").append(bookTitle));
        tooltipLines.add(Component.literal(" "));

        String description = Component.translatable("tooltip.rechantment.enchantment_table.enchanted_book_desc").getString();
        List<String> splitText = UtilFunctions.wrapText(description, 165);
        for (String line : splitText) {
            tooltipLines.add(Component.literal(line.trim()));
        }
        tooltipLines.add(Component.literal(" "));

        // Experience requirement
        Component experienceTitle = Component.translatable("tooltip.rechantment.enchantment_table.experience").withStyle(MID_GRAY_COLOR_STYLE);
        Component experienceFrac = Component.literal(playerInventory.player.totalExperience + "/" + properties.requiredExp + " EXP").withStyle(ChatFormatting.WHITE);
        tooltipLines.add(experienceTitle.copy().append(": ").append(experienceFrac));
        tooltipLines.add(Component.literal(" "));

        // Other requirements:
        Component requirementsTitle = Component.translatable("tooltip.rechantment.enchantment_table.requirements").append(": ").withStyle(MID_GRAY_COLOR_STYLE);
        tooltipLines.add(requirementsTitle);

        // Bookshelf count requirement. Green color if requirement met.
        String bookshelfCount = String.valueOf(properties.requiredBookShelves);
        String bookshelvesName = Component.translatable("tooltip.rechantment.enchantment_table.bookshelves").getString();
        ChatFormatting bookReqMetColor = bookshelfRequirementsMet(properties) ? ChatFormatting.GREEN : ChatFormatting.RED;
        Component fullBookRequirementColored = Component.literal(bookshelfCount + " " + bookshelvesName).withStyle(bookReqMetColor);
        tooltipLines.add(grayHyphen.copy().append(fullBookRequirementColored));

        // Floor block requirement. Green color if requirement met.
        String floorBlockName = properties.floorBlock.getName().getString();
        String floorTranslated = Component.translatable("tooltip.rechantment.enchantment_table.floor").getString();
        ChatFormatting floorReqMetColor = floorRequirementsMet(properties) ? ChatFormatting.GREEN : ChatFormatting.RED;
        Component fullFloorRequirementColored = Component.literal(floorBlockName + " " + floorTranslated).withStyle(floorReqMetColor);
        tooltipLines.add(grayHyphen.copy().append(fullFloorRequirementColored));
        tooltipLines.add(Component.literal(" "));

        // Break chances:
        String chancePerBlock = Component.translatable("tooltip.rechantment.enchantment_table.chance_per").getString();
        Component breakChanceTitle = Component.translatable("tooltip.rechantment.enchantment_table.break_chance").append(":").withStyle(MID_GRAY_COLOR_STYLE);
        tooltipLines.add(breakChanceTitle);

        // Bookshelves break chance
        String bookBreakChance = String.format("%.1f%%", properties.bookBreakChance * 100f);
        tooltipLines.add(Component.literal("- " + bookshelvesName + ":"));
        tooltipLines.add(Component.literal("    - " + bookBreakChance + " " + chancePerBlock).withStyle(PINK_COLOR_STYLE));

        // Floor break chance
        String floorBreakChance = String.format("%.1f%%", properties.floorBreakChance * 100f);
        tooltipLines.add(Component.literal("- " + floorTranslated + ":"));
        tooltipLines.add(Component.literal("    - " + floorBreakChance + " " + chancePerBlock).withStyle(PINK_COLOR_STYLE));
        tooltipLines.add(Component.literal(" "));

        // Shows left-click prompt if met, otherwise shows warning that requirements not met.
        if (playerMeetsAllEnchantRequirements(properties)) {
            Component leftClickPrompt = Component.translatable("tooltip.rechantment.enchantment_table.left_click").withStyle(enchantRarityInfo.getB());
            tooltipLines.add(whiteArrow.copy().append(leftClickPrompt));
        }
        else {
            Component reqNotMet = Component.translatable("tooltip.rechantment.enchantment_table.requirements_not_met").withStyle(ChatFormatting.RED);
            tooltipLines.add(redX.copy().append(reqNotMet));
        }

        // Final line, show right click prompt.
        Component rightClickPrompt = Component.translatable("tooltip.rechantment.enchantment_table.right_click").withStyle(enchantRarityInfo.getB());
        tooltipLines.add(whiteArrow.copy().append(rightClickPrompt));

        return tooltipLines;
    }

    public void refreshCachedBlockStates(BookRequirementProperties bookProperties) {

        Level level = playerInventory.player.level();
        BlockPos enchantTablePos = menu.blockEntity.getBlockPos();

        cachedBookshelvesInRange = UtilFunctions.scanAroundBlockForBookshelves(level, enchantTablePos);
        cachedFloorBlocksInRange = UtilFunctions.scanAroundBlockForValidFloors(bookProperties.floorBlock, level, enchantTablePos);
    }

    public boolean expRequirementMet(BookRequirementProperties bookProperties) {
        return UtilFunctions.playerMeetsExpRequirement(bookProperties, playerInventory.player);
    }

    public boolean bookshelfRequirementsMet (BookRequirementProperties bookProperties) {
        return UtilFunctions.playerMeetsBookshelfRequirement(bookProperties, cachedBookshelvesInRange);
    }

    public boolean floorRequirementsMet(BookRequirementProperties bookProperties) {
        return UtilFunctions.playerMeetsFloorRequirement(bookProperties, cachedFloorBlocksInRange);
    }

    public boolean playerMeetsAllEnchantRequirements(BookRequirementProperties bookProperties) {
        return  bookshelfRequirementsMet(bookProperties) &&
                expRequirementMet(bookProperties) &&
                floorRequirementsMet(bookProperties);
    }
}