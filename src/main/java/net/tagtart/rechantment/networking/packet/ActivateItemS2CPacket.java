package net.tagtart.rechantment.networking.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;


// EVERYTHING IN HERE IS NOT USED CURRENTLY, JUST BOILERPLATE
public class ActivateItemS2CPacket {

    private final ItemStack updatedItem;
    public ActivateItemS2CPacket(int inventoryIndex, ItemStack updatedItem) {

        this.updatedItem = updatedItem;
    }

    public ActivateItemS2CPacket(FriendlyByteBuf buf) {

        this.updatedItem = buf.readItem();
    }

    public void toBytes(FriendlyByteBuf buf) {

        buf.writeItemStack(updatedItem, false);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Player player = Minecraft.getInstance().player;
            // Client stuff here
            if (player != null) {
                System.out.println("Server Item NBT: " + updatedItem.getTag());

               player.getInventory().setChanged();
            }

        });
        return true;
    }


}
