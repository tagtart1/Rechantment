package net.tagtart.rechantment.networking.packet;

import com.mojang.serialization.Decoder;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;
import net.tagtart.rechantment.block.entity.RechantmentTableBlockEntity;
import net.tagtart.rechantment.screen.RechantmentTableMenu;
import net.tagtart.rechantment.screen.RechantmentTablePoolDisplayMenu;

import java.util.function.Supplier;

public class OpenEnchantTableScreenC2SPacket extends AbstractPacket {

    private int screenIndex;
    private int bookPropertiesIndex;
    private BlockPos enchantTablePos;

    public OpenEnchantTableScreenC2SPacket(int pScreenIndex, int pPropertiesIndex, BlockPos pEnchantTablePos) {
        super();
        screenIndex = pScreenIndex;
        bookPropertiesIndex = pPropertiesIndex;
        enchantTablePos = pEnchantTablePos;
    }

    public OpenEnchantTableScreenC2SPacket(FriendlyByteBuf buf) {
        super(buf);
        screenIndex = buf.readInt();
        bookPropertiesIndex = buf.readInt();
        enchantTablePos = buf.readBlockPos();
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(screenIndex);
        buf.writeInt(bookPropertiesIndex);
        buf.writeBlockPos(enchantTablePos);
    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();

        context.enqueueWork(() -> {

            ServerPlayer serverPlayer = context.getSender();
            ServerLevel level = serverPlayer.serverLevel();

            BlockEntity baseBlockEntity = level.getBlockEntity(enchantTablePos);
            if (!(baseBlockEntity instanceof RechantmentTableBlockEntity)) return;

            RechantmentTableBlockEntity blockEntity = (RechantmentTableBlockEntity)baseBlockEntity;

            if (screenIndex == 0) {
                SimpleMenuProvider openMenu = new SimpleMenuProvider(
                        (id, inventory, player) -> new RechantmentTableMenu(id, inventory, blockEntity), (blockEntity).getDisplayName());
                NetworkHooks.openScreen(serverPlayer, openMenu, blockEntity.getBlockPos());
            }
            if (screenIndex == 1) {
                SimpleMenuProvider openMenu = new SimpleMenuProvider(
                        (id, inventory, player) -> {
                            return new RechantmentTablePoolDisplayMenu(id, inventory, level.getBlockEntity(enchantTablePos), bookPropertiesIndex);
                        },
                        blockEntity.getDisplayName());
                NetworkHooks.openScreen(serverPlayer, openMenu, friendlyByteBuf -> {
                    friendlyByteBuf.writeBlockPos(enchantTablePos);
                    friendlyByteBuf.writeInt(bookPropertiesIndex);
                });
            }
        });

        return true;
    }
}
