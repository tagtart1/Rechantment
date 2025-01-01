package net.tagtart.rechantment.networking.packet;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;
import net.tagtart.rechantment.block.entity.RechantmentTableBlockEntity;
import net.tagtart.rechantment.screen.RechantmentTablePoolDisplayMenu;

import java.util.function.Supplier;

public class OpenEnchantTableLootPoolScreenC2SPacket extends AbstractPacket {

    private int bookPropertiesIndex;
    private BlockPos enchantTablePos;

    public OpenEnchantTableLootPoolScreenC2SPacket(int pPropertiesIndex, BlockPos pEnchantTablePos) {
        super();
        bookPropertiesIndex = pPropertiesIndex;
        enchantTablePos = pEnchantTablePos;
    }

    public OpenEnchantTableLootPoolScreenC2SPacket(FriendlyByteBuf buf) {
        super(buf);
        bookPropertiesIndex = buf.readInt();
        enchantTablePos = buf.readBlockPos();
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
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

            SimpleMenuProvider openMenu = new SimpleMenuProvider(
                    (id, inventory, player) -> new RechantmentTablePoolDisplayMenu(id, inventory, level.getBlockEntity(enchantTablePos), bookPropertiesIndex), blockEntity.getDisplayName());
            NetworkHooks.openScreen(serverPlayer, openMenu, enchantTablePos);
        });

        return true;
    }
}
