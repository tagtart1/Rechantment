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

        });
        return true;
    }

    // Applies enchants safely by overwriting previous enchants to avoid duplication




}
