package net.tagtart.rechantment.util;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import net.tagtart.rechantment.config.RechantmentCommonConfigs;
import oshi.util.tuples.Pair;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class UtilFunctions {
    // TO-DO MOVE THIS TO A UTIL
    // Utility method to wrap text into lines
    public static List<String> wrapText(String text, int maxWidth) {
        List<String> lines = new ArrayList<>();
        StringBuilder currentLine = new StringBuilder();
        Font font = Minecraft.getInstance().font; // Get the Minecraft font renderer

        for (String word : text.split(" ")) {
            // Check if adding the next word exceeds the maxWidth
            if (font.width(currentLine + word) > maxWidth) {
                lines.add(currentLine.toString()); // Add the current line to the list
                currentLine = new StringBuilder(); // Start a new line
            }
            currentLine.append(word).append(" ");
        }

        // Add the last line if it exists
        if (!currentLine.isEmpty()) {
            lines.add(currentLine.toString().trim());
        }

        return lines;
    }


    public static ItemStack getItemStackFromString(String itemName) {
        ResourceLocation itemResource = new ResourceLocation(itemName);

        Item item = ForgeRegistries.ITEMS.getValue(itemResource);

        if (item == null) {
            return ItemStack.EMPTY;
        }

        return new ItemStack(item);
    }

    public static Pair<String, ChatFormatting> getRarityInfo(String enchantmentRaw) {
        if (RechantmentCommonConfigs.RARITY_0_ENCHANTMENTS.get().contains(enchantmentRaw)) {
            return new Pair<>("simple", ChatFormatting.GRAY);
        } else if (RechantmentCommonConfigs.ELITE_ENCHANTMENTS.get().contains(enchantmentRaw)) {
            return new Pair<>("elite", ChatFormatting.AQUA);
        } else if (RechantmentCommonConfigs.UNIQUE_ENCHANTMENTS.get().contains(enchantmentRaw)) {
            return new Pair<>("unique", ChatFormatting.GREEN);
        } else if (RechantmentCommonConfigs.ULTIMATE_ENCHANTMENTS.get().contains(enchantmentRaw)) {
            return new Pair<>("ultimate", ChatFormatting.YELLOW);
        } else if (RechantmentCommonConfigs.LEGENDARY_ENCHANTMENTS.get().contains(enchantmentRaw)) {
            return new Pair<>("legendary", ChatFormatting.GOLD);
        }
        return new Pair<>("simple", ChatFormatting.GRAY);
    }

    public static Pair<String, ChatFormatting> getRarityInfo(float rarity) {

        int rarityInt = Math.round(rarity);
        return switch (rarityInt) {
            case 1 -> new Pair<>("simple", ChatFormatting.GRAY);
            case 2 -> new Pair<>("unique", ChatFormatting.GREEN);
            case 3 -> new Pair<>("elite", ChatFormatting.AQUA);
            case 4 -> new Pair<>("ultimate", ChatFormatting.YELLOW);
            case 5 -> new Pair<>("legendary", ChatFormatting.GOLD);
            default -> new Pair<>("simple", ChatFormatting.GRAY);
        };
    }

    // Forms a bounding box around the provided position by offsetting the corners by the provided offset values,
    // then returns an array of all block states in the level inside that bounding box. If a filter is provided,
    // only blocks matching the type provided will be returned (otherwise, all are returned). Keep in mind the offsets are absolutes,
    // as in, lowerLeft will be subtracted from given position, and upperRight will be added!
    public static Pair<BlockState[], BlockPos[]> getAllBlockInfoAroundBlock(Level level, BlockPos centerPos, Vec3i lowerLeftOffset, Vec3i upperRightOffset, @Nullable Block blockFilter) {

        Vec3i lowerLeft = centerPos.subtract(lowerLeftOffset);
        Vec3i upperRight = centerPos.offset(upperRightOffset);

        ArrayList<BlockPos> positions = new ArrayList<>();
        ArrayList<BlockState> states = new ArrayList<>();
        for (int x = lowerLeft.getX(); x <= upperRight.getX(); ++x) {
            for (int y = lowerLeft.getY(); y <= upperRight.getY(); ++y) {
                for (int z = lowerLeft.getZ(); z <= upperRight.getZ(); ++z) {
                    BlockPos pos = new BlockPos(x, y, z);
                    BlockState state = level.getBlockState(pos);

                    if (blockFilter == null || state.is(blockFilter)) {
                        positions.add(pos);
                        states.add(state);
                    }
                }
            }
        }

        return new Pair<>(states.toArray(BlockState[]::new), positions.toArray(BlockPos[]::new));
    }

    public static boolean playerMeetsExpRequirement(BookRarityProperties bookProperties, Player player) {
        return player.totalExperience >= bookProperties.requiredExp;
    }

    // Note that is checks if the blocks match the required type for safety.
    // Recommended to pass in smaller (possibly already filtered) arrays for performance.
    public static boolean playerMeetsBookshelfRequirement(BookRarityProperties bookProperties, BlockState[] states) {
        int shelvesPresent = Arrays.stream(states).filter(blockState -> blockState.is(Blocks.BOOKSHELF)).toArray().length;
        return shelvesPresent >= bookProperties.requiredBookShelves;
    }

    // Note that is checks if the blocks match the required type for safety.
    // Recommended to pass in smaller (possibly already filtered) arrays for performance.
    public static boolean playerMeetsFloorRequirement(BookRarityProperties bookProperties, BlockState[] states) {
        int blocksPresent = Arrays.stream(states).filter(blockState -> blockState.is(bookProperties.floorBlock)).toArray().length;
        return blocksPresent >= 9;
    }

    public static Pair<BlockState[], BlockPos[]> scanAroundBlockForBookshelves(Level level, BlockPos blockPos){

        // Might read these from config instead but probably not for now?
        // Compute AABB corners for scanning level:
        Vec3i bookshelfCheckLowerLeftOffset = new Vec3i(3, 0, 3);
        Vec3i bookshelfCheckUpperRightOffset = new Vec3i(3, 2, 3);

        var bookBlockInfo = UtilFunctions.getAllBlockInfoAroundBlock(
                level,
                blockPos,
                bookshelfCheckLowerLeftOffset,
                bookshelfCheckUpperRightOffset,
                Blocks.BOOKSHELF
        );

        return bookBlockInfo;
    }

    public static Pair<BlockState[], BlockPos[]> scanAroundBlockForValidFloors(Block validBlock, Level level, BlockPos blockPos){

        Vec3i floorCheckLowerLeftOffset = new Vec3i(1, 0, 1);
        Vec3i floorCheckUpperRightOffset = new Vec3i(1, 0, 1);

         var floorBlockInfo = UtilFunctions.getAllBlockInfoAroundBlock(
                level,
                blockPos.subtract(new Vec3i(0, 1, 0)), // Checking one block under enchant table.
                floorCheckLowerLeftOffset,
                floorCheckUpperRightOffset,
                validBlock
        );

        return floorBlockInfo;
    }

    public static boolean playerMeetsAllEnchantRequirements(BookRarityProperties bookProperties, Player player, BlockState[] bookshelves, BlockState[] floorBlocks) {

        boolean expReqMet      = playerMeetsExpRequirement(bookProperties, player);
        boolean shelvesReqMet  = playerMeetsBookshelfRequirement(bookProperties, bookshelves);
        boolean floorReqMet    = playerMeetsFloorRequirement(bookProperties, floorBlocks);

        return expReqMet && shelvesReqMet && floorReqMet;
    }


    public static<T extends Enchantment> Pair<T, Integer> getEnchantmentFromItem (String enchantmentToGet, ItemStack enchantedItem, Class<T> enchantmentClass) {

        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(enchantedItem);

        ResourceLocation enchantResource = new ResourceLocation(enchantmentToGet);
        Enchantment enchantmentBase = ForgeRegistries.ENCHANTMENTS.getValue(enchantResource);

        if (enchantments.containsKey(enchantmentBase)) {
            return new Pair<>(enchantmentClass.cast(enchantmentBase), enchantments.get(enchantmentBase));
        }
        return null;
    }

}
