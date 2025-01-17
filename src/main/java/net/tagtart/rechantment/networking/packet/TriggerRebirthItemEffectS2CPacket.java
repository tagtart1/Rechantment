package net.tagtart.rechantment.networking.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TriggerRebirthItemEffectS2CPacket extends AbstractPacket {

    private ItemStack rebirthItem;

    public TriggerRebirthItemEffectS2CPacket(ItemStack pRebirthItem) {
        super();
        rebirthItem = pRebirthItem;
    }

    public TriggerRebirthItemEffectS2CPacket(FriendlyByteBuf buf) {
        super(buf);
        rebirthItem = buf.readItem();
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeItem(rebirthItem);
    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();

        context.enqueueWork(() -> {

            Minecraft.getInstance().gameRenderer.displayItemActivation(rebirthItem);

        });
        return true;
    }
}
