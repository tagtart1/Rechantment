package net.tagtart.rechantment.item.custom;

import net.minecraft.sounds.SoundSource;
import net.tagtart.rechantment.config.RechantmentCommonConfigs;
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
import org.jetbrains.annotations.Nullable;
import oshi.util.tuples.Pair;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

public class EnchantedBookItem extends Item {
    Random random = new Random();

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
    public boolean overrideStackedOnOther(ItemStack pStack, Slot pSlot, ClickAction pAction, Player pPlayer) {

        ItemStack otherStack = pSlot.getItem();
        if (pAction == ClickAction.PRIMARY && (otherStack.isEnchanted() || otherStack.isEnchantable())) {
            CompoundTag enchantmentTag = pStack.getTag().getCompound("Enchantment");
            int enchantmentLevel = enchantmentTag.getInt("lvl");
            String enchantmentRaw = enchantmentTag.getString("id");
            ResourceLocation resourceLocation = new ResourceLocation(enchantmentRaw);
            Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(resourceLocation);
            assert enchantment != null;
            boolean canEnchantGeneral = enchantment.canEnchant(otherStack);

            Map<Enchantment, Integer> otherStackEnchantmentsInfo = EnchantmentHelper.getEnchantments(otherStack);
            Collection<Enchantment> otherStackEnchants = otherStackEnchantmentsInfo.keySet();

            // TODO - take into account the success rate!!
            // TODO - Rainbow color enchantments

            if (canEnchantGeneral && !otherStack.isEnchanted()) {
                // No enchantments on the other item so it can be applied

                otherStackEnchantmentsInfo.put(enchantment, enchantmentLevel);
                applyEnchantsSafely(otherStackEnchantmentsInfo, otherStack, pPlayer, pStack);
                return true;
            } else if (canEnchantGeneral) {
                // Loop through each of the otherStacks enchant and make sure they are compatible with the incoming enchant

                if (otherStackEnchants.contains(enchantment)) {
                    int otherEnchantLevel = otherStackEnchantmentsInfo.get(enchantment);
                    if (otherEnchantLevel == enchantment.getMaxLevel()) {
                        sendClientMessage(pPlayer, Component.literal("This item already has this enchantment maxed!").withStyle(ChatFormatting.RED));
                        return true;
                    } else if (enchantmentLevel < otherEnchantLevel) {
                        sendClientMessage(pPlayer, Component.literal("This item already has this enchantment!").withStyle(ChatFormatting.RED));
                        return true;
                    } else {
                        if (otherEnchantLevel == enchantmentLevel) {
                            otherStackEnchantmentsInfo.put(enchantment, otherEnchantLevel + 1 );
                        } else {
                            otherStackEnchantmentsInfo.put(enchantment, enchantmentLevel );
                        }
                        applyEnchantsSafely(otherStackEnchantmentsInfo, otherStack, pPlayer, pStack);
                        return true;
                    }
                } else {
                    for (Enchantment otherEnchantment : otherStackEnchants) {
                        boolean isCompatible = otherEnchantment.isCompatibleWith(enchantment);

                        if (!isCompatible) {
                            sendClientMessage(pPlayer, Component.translatable(enchantment.getDescriptionId())
                                    .append(" is not compatible with ")
                                    .append(Component.translatable(otherEnchantment.getDescriptionId()))
                                    .withStyle(ChatFormatting.RED) );
                            return true;
                        }
                    }
                    // Enchant good to go, enchant that thing!
                    otherStackEnchantmentsInfo.put(enchantment, enchantmentLevel);
                    applyEnchantsSafely(otherStackEnchantmentsInfo, otherStack, pPlayer, pStack);
                    return true;
                }


            } else {
                sendClientMessage(pPlayer, Component.literal("Enchantment cannot be applied to this item").withStyle(ChatFormatting.RED));
                return true;
            }

        } else {
            return false;
        }
    }

    private class EnchantAttemptResults {

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


    private void sendClientMessage(Player pPlayer, Component textComponent) {
        if (pPlayer.level().isClientSide) {
            pPlayer.sendSystemMessage(textComponent);
        }
    }

    private boolean isSuccessfulEnchant(int successRate) {
        return random.nextInt(100) < successRate;
    }

    // Applies enchants safely by overwriting previous enchants to avoid duplication
    private void applyEnchantsSafely( Map<Enchantment, Integer> enchants, ItemStack item, Player pPlayer, ItemStack enchantedBook) {
        assert enchantedBook.getTag() != null;
        // if (pPlayer.level().isClientSide()) return;
        int successRate = enchantedBook.getTag().getInt("SuccessRate");
        if (isSuccessfulEnchant(successRate)) {
            EnchantmentHelper.setEnchantments(enchants, item);
            pPlayer.playSound(SoundEvents.PLAYER_LEVELUP);
            sendClientMessage(pPlayer, Component.literal("Successfully enchanted."));
        } else {
            // Play bad sound
            pPlayer.playSound(ModSounds.ENCHANTED_BOOK_FAIL.get(), 1.5f, 1f);
            sendClientMessage(pPlayer, Component.literal("Enchantment failed to apply to item, lol."));
        }
        // Break the book regardless of success or not
        enchantedBook.shrink(1);
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
