package net.tagtart.rechantment.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.tagtart.rechantment.Rechantment;
import net.tagtart.rechantment.networking.packet.OpenEnchantTableScreenC2SPacket;
import net.tagtart.rechantment.networking.packet.PurchaseEnchantedBookC2SPacket;
import net.tagtart.rechantment.networking.packet.TriggerRebirthItemEffectS2CPacket;

// EVERYTHING IN HERE IS NOT USED CURRENTLY, JUST BOILERPLATE
public class ModPackets {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;

    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(Rechantment.MOD_ID, "packets"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(PurchaseEnchantedBookC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PurchaseEnchantedBookC2SPacket::new)
                .encoder(PurchaseEnchantedBookC2SPacket::toBytes)
                .consumerMainThread(PurchaseEnchantedBookC2SPacket::handle)
                .add();

        net.messageBuilder(OpenEnchantTableScreenC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(OpenEnchantTableScreenC2SPacket::new)
                .encoder(OpenEnchantTableScreenC2SPacket::toBytes)
                .consumerMainThread(OpenEnchantTableScreenC2SPacket::handle)
                .add();

        net.messageBuilder(TriggerRebirthItemEffectS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(TriggerRebirthItemEffectS2CPacket::new)
                .encoder(TriggerRebirthItemEffectS2CPacket::toBytes)
                .consumerMainThread(TriggerRebirthItemEffectS2CPacket::handle)
                .add();
    }

    public static <PACKET> void sentToServer(PACKET packet) {
        INSTANCE.sendToServer(packet);
    }

    public static <PACKET> void sentToPlayer(PACKET packet, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }
}
