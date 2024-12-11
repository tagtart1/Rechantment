package net.tagtart.rechantment.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.tagtart.rechantment.Rechantment;
import net.tagtart.rechantment.networking.packet.EnchantItemC2SPacket;
import net.tagtart.rechantment.networking.packet.EnchantPurchaseResultS2CPacket;
import net.tagtart.rechantment.networking.packet.PurchaseEnchantedBookC2SPacket;
import net.tagtart.rechantment.networking.packet.SyncEnchantItemS2CPacket;

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

        net.messageBuilder(EnchantItemC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(EnchantItemC2SPacket::new)
                .encoder(EnchantItemC2SPacket::toBytes)
                .consumerMainThread(EnchantItemC2SPacket::handle)
                .add();

        net.messageBuilder(SyncEnchantItemS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SyncEnchantItemS2CPacket::new)
                .encoder(SyncEnchantItemS2CPacket::toBytes)
                .consumerMainThread(SyncEnchantItemS2CPacket::handle)
                .add();

        net.messageBuilder(PurchaseEnchantedBookC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PurchaseEnchantedBookC2SPacket::new)
                .encoder(PurchaseEnchantedBookC2SPacket::toBytes)
                .consumerMainThread(PurchaseEnchantedBookC2SPacket::handle)
                .add();

//        net.messageBuilder(EnchantPurchaseResultS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
//                .decoder(EnchantPurchaseResultS2CPacket::new)
//                .encoder(EnchantPurchaseResultS2CPacket::toBytes)
//                .consumerMainThread(EnchantPurchaseResultS2CPacket::handle)
//                .add();
    }

    public static <PACKET> void sentToServer(PACKET packet) {
        INSTANCE.sendToServer(packet);
    }

    public static <PACKET> void sentToPlayer(PACKET packet, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }
}
