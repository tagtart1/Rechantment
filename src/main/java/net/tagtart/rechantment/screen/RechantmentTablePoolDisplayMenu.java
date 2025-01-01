package net.tagtart.rechantment.screen;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.tagtart.rechantment.block.entity.RechantmentTableBlockEntity;
import net.tagtart.rechantment.util.BookRarityProperties;

import java.awt.print.Book;

public class RechantmentTablePoolDisplayMenu extends AbstractContainerMenu {

    private final RechantmentTableBlockEntity blockEntity;
    private final Level level;

    private BookRarityProperties bookProperties;

    public RechantmentTablePoolDisplayMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), 0); // Default to first properites index
    }

    public RechantmentTablePoolDisplayMenu(int pContainerId, Inventory inv, BlockEntity entity, int bookPropertiesIndex) {
        super(ModMenuTypes.RECHANTMENT_TABLE_POOL_DISPLAY_MENU.get(), pContainerId);

        blockEntity = ((RechantmentTableBlockEntity) entity);
        level = inv.player.level();
        bookProperties = BookRarityProperties.getAllProperties()[bookPropertiesIndex];
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
