package net.tagtart.rechantment.networking.packet;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.tagtart.rechantment.item.ModItems;
import net.tagtart.rechantment.networking.PurchaseBookResultCase;
import net.tagtart.rechantment.util.AllBookProperties;
import net.tagtart.rechantment.util.BookRequirementProperties;
import net.tagtart.rechantment.util.UtilFunctions;

import java.util.Random;
import java.util.function.Supplier;

// This should only be created if the CLIENT side has also properly detected that
// a purchase can be made!
public class PurchaseEnchantedBookC2SPacket extends AbstractPacket {

    private int bookPropertiesIndex;
    private BlockPos enchantTablePos;

    public PurchaseEnchantedBookC2SPacket(int pPropertiesIndex, BlockPos pEnchantTablePos) {
        super();
        bookPropertiesIndex = pPropertiesIndex;
        enchantTablePos = pEnchantTablePos;
    }

    public PurchaseEnchantedBookC2SPacket(FriendlyByteBuf buf) {
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
            // We are now running this ONLY on server.

            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel();

            Inventory playerInventory = player.getInventory();
            BookRequirementProperties bookProperties = AllBookProperties.getAllProperties()[bookPropertiesIndex];

            var bookshelves = UtilFunctions.scanAroundBlockForBookshelves(level, enchantTablePos);
            var floorBlocks = UtilFunctions.scanAroundBlockForValidFloors(bookProperties.floorBlock, level, enchantTablePos);

            boolean meetsEXPRequirement = UtilFunctions.playerMeetsExpRequirement(bookProperties, player);
            boolean meetsBookshelfRequirement = UtilFunctions.playerMeetsBookshelfRequirement(bookProperties, bookshelves.getA());
            boolean meetsFloorBlocksRequirement = UtilFunctions.playerMeetsFloorRequirement(bookProperties, floorBlocks.getA());

            PurchaseBookResultCase failCase = PurchaseBookResultCase.SUCCESS;

            // Check if player inventory is full. If so, return failure.
            if (playerInventory.getFreeSlot() == -1) failCase = PurchaseBookResultCase.INVENTORY_FULL;

            // Check basic requirements with helper methods.
            else if (!meetsEXPRequirement) failCase = PurchaseBookResultCase.INSUFFICIENT_EXP;
            else if (!meetsBookshelfRequirement) failCase = PurchaseBookResultCase.INSUFFICIENT_BOOKS;
            else if (!meetsFloorBlocksRequirement) failCase = PurchaseBookResultCase.INSUFFICIENT_FLOOR;

            // At this point, should be good to go. Can destroy blocks and reward the book.
            else
            {
                Random random = new Random();

                // Destroy block at that position if rolls to do it. Also don't check more than
                // the number of shelves that are actually required just in case they all roll to break somehow
                for (int i = 0; i < bookshelves.getB().length || i < bookProperties.requiredBookShelves; ++i) {
                    BlockPos position = bookshelves.getB()[i];
                    if (random.nextFloat() < bookProperties.bookBreakChance)
                        level.destroyBlock(position, false);
                }

                // Do same for floor blocks.
                for (int i = 0; i < floorBlocks.getB().length; ++i) {
                    BlockPos position = floorBlocks.getB()[i];
                    if (random.nextFloat() < bookProperties.floorBreakChance)
                        level.destroyBlock(position, false);
                }

                ItemStack toGive = new ItemStack(ModItems.ENCHANTED_BOOK.get());

                // Our custom Enchantment tag is different from the "Enchantments" list that items
                // will normally get when calling itemStack.enchant(), so we must add the tag manually,
                // as well as the success rate.
                CompoundTag rootTag = toGive.getOrCreateTag();

                CompoundTag enchantmentTag = new CompoundTag();
                enchantmentTag.putInt("lvl", 1);
                enchantmentTag.putString("id", "minecraft:unbreaking");

                IntTag successTag = IntTag.valueOf(99);
                rootTag.put("Enchantment", enchantmentTag);
                rootTag.put("SuccessRate", successTag);

                //toGive.enchant(Enchantments.BLOCK_FORTUNE, 2); // Just to test.
                player.addItem(toGive);
            }

            sendEnchantResultPlayerMessage(player, failCase);
        });
        return true;
    }

    // This is specifically for server side checks. If there is a de-sync of some kind, player will always
    // be forced out of their container and a message sent (unlike on client side, where behavior/message is result-dependent).
    private void sendEnchantResultPlayerMessage(Player player, PurchaseBookResultCase failCase) {
        switch(failCase) {
            case INVENTORY_FULL:
                break;
            // Not enough EXP
            case INSUFFICIENT_EXP:
                break;
            // Not enough bookshelves
            case INSUFFICIENT_BOOKS:
                break;
            // Not enough floor blocks.
            case INSUFFICIENT_FLOOR:
                break;
        }

        player.closeContainer();
    }
}
