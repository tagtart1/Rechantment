package net.tagtart.rechantment.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public abstract class AbstractPacket {

    protected AbstractPacket() {

    }

    public AbstractPacket(FriendlyByteBuf buf) {

    }

    public abstract void toBytes(FriendlyByteBuf buf);

    public abstract boolean handle(Supplier<NetworkEvent.Context> supplier);
}
