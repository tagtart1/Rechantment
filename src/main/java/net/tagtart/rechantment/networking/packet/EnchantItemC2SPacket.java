package net.tagtart.rechantment.networking.packet;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.tagtart.rechantment.networking.ModPackets;
import net.tagtart.rechantment.sound.ModSounds;

import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

public class EnchantItemC2SPacket {
    private final ItemStack bookStack;
    private final ItemStack itemToEnchantStack;
    public EnchantItemC2SPacket(ItemStack bookStack, ItemStack itemToEnchantStack) {
        this.bookStack = bookStack;
        this.itemToEnchantStack = itemToEnchantStack;
    }

    public EnchantItemC2SPacket(FriendlyByteBuf buf) {
        this.bookStack = buf.readItem();
        this.itemToEnchantStack = buf.readItem();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeItemStack(bookStack, false);
        buf.writeItemStack(itemToEnchantStack, false);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();

        context.enqueueWork(() -> {
            // Server stuff here
            ServerPlayer player = context.getSender();
            if (player == null) return;
            ServerLevel level = player.serverLevel();



            CompoundTag enchantmentTag = bookStack.getTag().getCompound("Enchantment");
            int enchantmentLevel = enchantmentTag.getInt("lvl");
            String enchantmentRaw = enchantmentTag.getString("id");
            ResourceLocation resourceLocation = new ResourceLocation(enchantmentRaw);
            Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(resourceLocation);
            assert enchantment != null;
            boolean canEnchantGeneral = enchantment.canEnchant(itemToEnchantStack);

            Map<Enchantment, Integer> otherStackEnchantmentsInfo = EnchantmentHelper.getEnchantments(itemToEnchantStack);
            Collection<Enchantment> otherStackEnchants = otherStackEnchantmentsInfo.keySet();

            // TODO - take into account the success rate!!
            // TODO - Rainbow color enchantments

            if (canEnchantGeneral && !itemToEnchantStack.isEnchanted()) {
                // No enchantments on the other item so it can be applied

                otherStackEnchantmentsInfo.put(enchantment, enchantmentLevel);
                applyEnchantsSafely(otherStackEnchantmentsInfo, itemToEnchantStack, player, level, bookStack);

            } else if (canEnchantGeneral) {
                // Loop through each of the otherStacks enchant and make sure they are compatible with the incoming enchant

                if (otherStackEnchants.contains(enchantment)) {
                    int otherEnchantLevel = otherStackEnchantmentsInfo.get(enchantment);
                    if (otherEnchantLevel == enchantment.getMaxLevel()) {
                        sendClientMessage(player, Component.literal("This item already has this enchantment maxed!").withStyle(ChatFormatting.RED));

                    } else if (enchantmentLevel < otherEnchantLevel) {
                        sendClientMessage(player, Component.literal("This item already has this enchantment!").withStyle(ChatFormatting.RED));

                    } else {
                        if (otherEnchantLevel == enchantmentLevel) {
                            otherStackEnchantmentsInfo.put(enchantment, otherEnchantLevel + 1 );
                        } else {
                            otherStackEnchantmentsInfo.put(enchantment, enchantmentLevel );
                        }
                        applyEnchantsSafely(otherStackEnchantmentsInfo, itemToEnchantStack, player, level, bookStack);

                    }
                } else {
                    for (Enchantment otherEnchantment : otherStackEnchants) {
                        boolean isCompatible = otherEnchantment.isCompatibleWith(enchantment);

                        if (!isCompatible) {
                            sendClientMessage(player, Component.translatable(enchantment.getDescriptionId())
                                    .append(" is not compatible with ")
                                    .append(Component.translatable(otherEnchantment.getDescriptionId()))
                                    .withStyle(ChatFormatting.RED) );

                        }
                    }
                    // Enchant good to go, enchant that thing!
                    otherStackEnchantmentsInfo.put(enchantment, enchantmentLevel);
                    applyEnchantsSafely(otherStackEnchantmentsInfo, itemToEnchantStack, player, level, bookStack);

                }
            } else {
                sendClientMessage(player, Component.literal("Enchantment cannot be applied to this item").withStyle(ChatFormatting.RED));
            }
        });
        return true;
    }

    // Applies enchants safely by overwriting previous enchants to avoid duplication
    private void applyEnchantsSafely(Map<Enchantment, Integer> enchants, ItemStack item, ServerPlayer pPlayer, ServerLevel level, ItemStack enchantedBook) {
        assert enchantedBook.getTag() != null;
        int slotIndex = pPlayer.getInventory().findSlotMatchingItem(item);
        int successRate = enchantedBook.getTag().getInt("SuccessRate");
        if (isSuccessfulEnchant(successRate)) {
            EnchantmentHelper.setEnchantments(enchants, item);
            level.playSound(null, pPlayer.getOnPos(), SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 1f, 1f);
            sendClientMessage(pPlayer, Component.literal("Successfully enchanted.").withStyle(ChatFormatting.GREEN));
        } else {
            // Play bad sound
            level.playSound(null, pPlayer.getOnPos(), ModSounds.ENCHANTED_BOOK_FAIL.get(), SoundSource.PLAYERS, 4f, 1f);
            sendClientMessage(pPlayer, Component.literal("Enchantment failed to apply to item, lolol.").withStyle(ChatFormatting.RED));
        }
        // Break the book regardless of success or not
        enchantedBook.shrink(1);
        pPlayer.getInventory().setPickedItem(enchantedBook);
        pPlayer.getInventory().setItem(slotIndex, item);
        ModPackets.sentToPlayer(new SyncEnchantItemS2CPacket(slotIndex, item), pPlayer);
    }

    private void sendClientMessage(ServerPlayer pPlayer, Component textComponent) {
            pPlayer.sendSystemMessage(textComponent);
    }

    private boolean isSuccessfulEnchant(int successRate) {
        Random random = new Random();
        return random.nextInt(100) < successRate;
    }

}
