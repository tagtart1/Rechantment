package net.tagtart.rechantment.screen;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.EnchantmentTableBlockEntity;

public class RechantmentTableMenu extends AbstractContainerMenu {

    public final EnchantmentTableBlockEntity blockEntity;
    private final Level level;

    private static final int slotCount = 5;

    public RechantmentTableMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public RechantmentTableMenu(int pContainerId, Inventory inv, BlockEntity entity) {
        super(ModMenuTypes.RECHANTMENT_TABLE_MENU.get(), pContainerId);

        blockEntity = ((EnchantmentTableBlockEntity) entity);
        this.level = inv.player.level();
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, Blocks.ENCHANTING_TABLE);
    }
}
