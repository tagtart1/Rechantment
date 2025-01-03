package net.tagtart.rechantment.networking.packet;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import net.tagtart.rechantment.block.entity.RechantmentTableBlockEntity;
import net.tagtart.rechantment.item.ModItems;
import net.tagtart.rechantment.networking.PurchaseBookResultCase;
import net.tagtart.rechantment.util.BookRarityProperties;
import net.tagtart.rechantment.util.EnchantmentPoolEntry;
import net.tagtart.rechantment.util.UtilFunctions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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
            BookRarityProperties bookProperties = BookRarityProperties.getAllProperties()[bookPropertiesIndex];
            BlockEntity blockEntity = level.getBlockEntity(enchantTablePos);
            RechantmentTableBlockEntity enchTableEntity = (blockEntity instanceof RechantmentTableBlockEntity) ?
                    (RechantmentTableBlockEntity)blockEntity : null;

            var bookshelves = UtilFunctions.scanAroundBlockForBookshelves(level, enchantTablePos);
            var floorBlocks = UtilFunctions.scanAroundBlockForValidFloors(bookProperties.floorBlock, level, enchantTablePos);

            boolean meetsEXPRequirement = UtilFunctions.playerMeetsExpRequirement(bookProperties, player);
            boolean meetsBookshelfRequirement = UtilFunctions.playerMeetsBookshelfRequirement(bookProperties, bookshelves.getA());
            boolean meetsFloorBlocksRequirement = UtilFunctions.playerMeetsFloorRequirement(bookProperties, floorBlocks.getA());
            boolean meetsLapisRequirement = UtilFunctions.playerMeetsLapisRequirement(bookProperties, enchTableEntity.getItemHandlerLapisStack());

            PurchaseBookResultCase failCase = PurchaseBookResultCase.SUCCESS;

            // Check if player inventory is full. If so, return failure.
            if (playerInventory.getFreeSlot() == -1) failCase = PurchaseBookResultCase.INVENTORY_FULL;

            // Check basic requirements with helper methods.
            else if (!meetsEXPRequirement) failCase = PurchaseBookResultCase.INSUFFICIENT_EXP;
            else if (!meetsBookshelfRequirement) failCase = PurchaseBookResultCase.INSUFFICIENT_BOOKS;
            else if (!meetsFloorBlocksRequirement) failCase = PurchaseBookResultCase.INSUFFICIENT_FLOOR;
            else if (!meetsLapisRequirement) failCase = PurchaseBookResultCase.INSUFFICIENT_LAPIS;

            // At this point, should be good to go. Can destroy blocks and reward the book.
            else
            {
                SoundEvent soundToPlay = SoundEvents.EXPERIENCE_ORB_PICKUP;
                Random random = new Random();

                // Destroy block at that position if rolls to do it. Also don't check more than
                // the number of shelves that are actually required just in case they all roll to break somehow.
                // Check via random indices as well.
                ArrayList<Integer> randomIndices = new ArrayList<>();
                for (int i = 0; i < bookshelves.getB().length; ++i) {
                    randomIndices.add(i);
                }
                Collections.shuffle(randomIndices);
                for (int i = 0; i < bookshelves.getB().length && i < bookProperties.requiredBookShelves; ++i) {
                    int bookIndex = randomIndices.get(i);
                    BlockPos position = bookshelves.getB()[bookIndex];
                    if (random.nextFloat() < bookProperties.bookBreakChance)
                        level.destroyBlock(position, false);
                }

                // Do same for floor blocks, with extra logic prevent the block under
                // the table from breaking if possible.
                HashSet<Integer> remainingBlocks = new HashSet<>();
                BlockPos[] floorPositions = floorBlocks.getB();
                for (int i = 0; i < floorPositions.length; ++i)
                    remainingBlocks.add(i);

                int underTableIndex = -1; // If still -1, don't offset the block under table's destruction to another block!
                for (int i = 0; i < floorPositions.length; ++i) {
                    BlockPos position = floorPositions[i];
                    if (random.nextFloat() < bookProperties.floorBreakChance) {

                        if (position.getX() == enchantTablePos.getX() && position.getZ() == enchantTablePos.getZ()) {
                            underTableIndex = i;
                        }
                        else {
                            level.destroyBlock(position, false);
                        }
                        remainingBlocks.remove(i);
                    }
                }

                // This will be true if the center block (under the enchant table) was rolled to be destroyed.
                // This should hopefully pick a random block that hasn't been destroyed. If all others were destroyed,
                // then the center block itself DOES get destroyed since no other blocks remain.
                if (underTableIndex != -1) {
                    ArrayList<Integer> remainingBlocksIterable = new ArrayList<>(remainingBlocks);
                    int randomBlock = underTableIndex;
                    if (!remainingBlocksIterable.isEmpty())
                        randomBlock = random.nextInt(remainingBlocksIterable.size());

                    BlockPos position = floorPositions[randomBlock];
                    level.destroyBlock(position, false);
                }

                // Remove EXP and Lapis from player.
                player.giveExperiencePoints(-Math.min(player.totalExperience, bookProperties.requiredExp));
                enchTableEntity.getItemHandlerLapisStack().shrink(bookProperties.requiredLapis);

                ItemStack toGive = new ItemStack(ModItems.ENCHANTED_BOOK.get());


                CompoundTag rootTag = toGive.getOrCreateTag();

                CompoundTag enchantmentTag = new CompoundTag();
                EnchantmentPoolEntry randomEnchantment = bookProperties.getRandomEnchantmentWeighted();

                enchantmentTag.putInt("lvl", randomEnchantment.getRandomEnchantLevelWeighted());
                enchantmentTag.putString("id", randomEnchantment.enchantment);

                int successRate = random.nextInt(bookProperties.minSuccess, bookProperties.maxSuccess);
                IntTag successTag = IntTag.valueOf(successRate);
                rootTag.put("Enchantment", enchantmentTag);
                rootTag.put("SuccessRate", successTag);

                // Give enchanted book
                player.addItem(toGive);

                // Roll for gem of chance
                double gemOfChanceDropRate = bookProperties.rerollGemChance;
                if (random.nextDouble() < gemOfChanceDropRate) {

                    // Play sound effects, send a message
                    ItemStack chanceGemToGive = new ItemStack(ModItems.CHANCE_GEM.get());
                    soundToPlay = SoundEvents.PLAYER_LEVELUP;
                    player.sendSystemMessage(Component.literal("You found a Gem of Chance!").withStyle(ChatFormatting.GREEN));
                    if(!player.addItem(chanceGemToGive)) {
                        player.drop(chanceGemToGive, false);
                    }
                }

                level.playSound(null, enchantTablePos, soundToPlay, SoundSource.BLOCKS, 1f, 1f);
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
                //player.closeContainer();
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
            // Not enough lapis.
            case INSUFFICIENT_LAPIS:
                break;
        }

    }
}
