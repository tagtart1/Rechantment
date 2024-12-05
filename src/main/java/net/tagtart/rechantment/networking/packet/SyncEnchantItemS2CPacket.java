package net.tagtart.rechantment.networking.packet;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.tagtart.rechantment.sound.ModSounds;

import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

public class SyncEnchantItemS2CPacket {
    private final int inventoryIndex;
    private final ItemStack updatedItem;
    public SyncEnchantItemS2CPacket(int inventoryIndex, ItemStack updatedItem) {
        this.inventoryIndex = inventoryIndex;
        this.updatedItem = updatedItem;
    }

    public SyncEnchantItemS2CPacket(FriendlyByteBuf buf) {
        this.inventoryIndex = buf.readInt();
        this.updatedItem = buf.readItem();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(inventoryIndex);
        buf.writeItemStack(updatedItem, false);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Player player = Minecraft.getInstance().player;
            // Client stuff here
            if (player != null) {
                System.out.println("Server Item NBT: " + updatedItem.getTag());

               player.getInventory().setItem(inventoryIndex, updatedItem);
               player.getInventory().setChanged();
            }

        });
        return true;
    }


}
