package net.tagtart.rechantment.item.custom;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.tagtart.rechantment.config.RechantmentCommonConfigs;
import net.tagtart.rechantment.networking.ModPackets;
import net.tagtart.rechantment.networking.packet.EnchantItemC2SPacket;
import net.tagtart.rechantment.sound.ModSounds;
import net.tagtart.rechantment.util.UtilFunctions;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.event.RenderItemInFrameEvent;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.internal.TextComponentMessageFormatHandler;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import oshi.util.tuples.Pair;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

public class EnchantedBookItem extends Item {


    // Holds the item names for each icon on the tooltip
    private String[] baseIconItems = {
            "minecraft:iron_helmet",
            "minecraft:iron_chestplate",
            "minecraft:iron_leggings",
            "minecraft:iron_boots",
            "minecraft:iron_pickaxe",
            "minecraft:iron_axe",
            "minecraft:iron_shovel",
            "minecraft:iron_hoe",
            "minecraft:iron_sword",
            "minecraft:fishing_rod",
            "minecraft:trident",
            "minecraft:shield",
            "minecraft:crossbow",
            "minecraft:elytra",
    };

    public EnchantedBookItem(Properties pProperties) {

        super(pProperties);
    }

    @Override
    public Component getName(ItemStack pStack) {
        CompoundTag rootTag = pStack.getTag();
        CompoundTag enchantmentTag = rootTag.getCompound("Enchantment");
        String enchantmentRaw = enchantmentTag.getString("id");
        String[] enchantmentInfo = enchantmentRaw.split(":");
        String enchantmentSource = enchantmentInfo[0];
        String enchantmentName = enchantmentInfo[1];
        Pair<String, ChatFormatting> enchantRarityInfo = getRarityInfo(enchantmentRaw);

        int enchantmentLvl = enchantmentTag.getInt("lvl");
        String romanLevel = Component.translatable("enchantment.level." + enchantmentLvl).getString();

        String enchantFormattedName = Component.translatable("enchantment." + enchantmentSource + "." + enchantmentName).getString();
        String rarityIcon = Component.translatable("enchantment.rarity." + enchantRarityInfo.getA()).getString();

        return Component.literal(rarityIcon + " ")
                .append(Component.literal(enchantFormattedName + " " + romanLevel)
                        .withStyle(enchantRarityInfo.getB()));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        assert pStack.getTag() != null;
        // maybe delete this line later \/
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        int maxWidthTooltip = 165;
        int successRate = pStack.getTag().getInt("SuccessRate");
        CompoundTag enchantmentTag = pStack.getTag().getCompound("Enchantment");
        String enchantmentRaw = enchantmentTag.getString("id");
        String[] enchantmentInfo = enchantmentRaw.split(":");

        pTooltipComponents.add(Component.literal(" "));

        Component translatable = Component.translatable("enchantment." + enchantmentInfo[0] + "." + enchantmentInfo[1] + ".description");
        String resolvedText = translatable.getString();
        List<String> splitText = UtilFunctions.wrapText(resolvedText, maxWidthTooltip);
        for (String line : splitText) {
            pTooltipComponents.add(Component.literal(line.trim()));
        }

        pTooltipComponents.add(Component.literal(" "));

        pTooltipComponents.add(Component.literal("Success Rate: ")
                .setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_GREEN))  // Green for "Success Rate: "
                .append(Component.literal(successRate + "%")
                        .setStyle(Style.EMPTY.withColor(ChatFormatting.WHITE))));
        pTooltipComponents.add(Component.literal(" "));
        pTooltipComponents.add(Component.literal("→ ᴅʀᴀɢ ɴ ᴅʀᴏᴘ ᴏɴᴛᴏ ʏᴏᴜʀ").withStyle(ChatFormatting.GRAY));
        pTooltipComponents.add(Component.literal("ɪᴛᴇᴍ ᴛᴏ ᴀᴘᴘʟʏ ᴛʜɪꜱ ʙᴏᴏᴋ").withStyle(ChatFormatting.GRAY));
        pTooltipComponents.add(Component.literal(" "));


        ResourceLocation resourceLocation = new ResourceLocation(enchantmentRaw);
        Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(resourceLocation);

        assert enchantment != null;

        pTooltipComponents.add(getApplicableIcons(enchantment));

    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return true;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
    }

    @Override
    public boolean overrideStackedOnOther(@NotNull ItemStack pStack, Slot pSlot, @NotNull ClickAction pAction, @NotNull Player pPlayer) {

        ItemStack otherStack = pSlot.getItem();
        if (pAction == ClickAction.PRIMARY && (otherStack.isEnchanted() || otherStack.isEnchantable())) {
            if (!pPlayer.level().isClientSide()) {
                ModPackets.sentToServer(new EnchantItemC2SPacket(pStack, otherStack));

            }

            return true;
        } else {
            return false;
        }
    }



    private Pair<String, ChatFormatting> getRarityInfo(String enchantmentRaw) {
        if (RechantmentCommonConfigs.SIMPLE_ENCHANTMENTS.get().contains(enchantmentRaw)) {
            return new Pair<>("simple", ChatFormatting.GRAY);
        } else if (RechantmentCommonConfigs.ELITE_ENCHANTMENTS.get().contains(enchantmentRaw)) {
            return new Pair<>("elite", ChatFormatting.AQUA);
        } else if (RechantmentCommonConfigs.UNIQUE_ENCHANTMENTS.get().contains(enchantmentRaw)) {
            return new Pair<>("unique", ChatFormatting.GREEN);
        } else if (RechantmentCommonConfigs.ULTIMATE_ENCHANTMENTS.get().contains(enchantmentRaw)) {
            return new Pair<>("ultimate", ChatFormatting.YELLOW);
        } else if (RechantmentCommonConfigs.LEGENDARY_ENCHANTMENTS.get().contains(enchantmentRaw)) {
            return new Pair<>("legendary", ChatFormatting.GOLD);
        }
        return new Pair<>("simple", ChatFormatting.GRAY);
    }







    private Component getApplicableIcons(Enchantment enchantment) {
        MutableComponent text = Component.translatable("");
        for (String itemName : baseIconItems) {
            ItemStack item = UtilFunctions.getItemStackFromString(itemName);
            if (enchantment.canEnchant(item)) {
                // Breaks up the itemname to only get the identify string for the icon
                String[] itemNameParts = itemName.split("[:_]");
                String coreName = itemNameParts[itemNameParts.length - 1];

                // Get the icon png from the translatable
                text.append(Component.translatable("enchantment.icon." +coreName));
            }

        }
        return text;
    }


}
