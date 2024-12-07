package net.tagtart.rechantment.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.tagtart.rechantment.item.ModItems;
import org.jetbrains.annotations.NotNull;
import oshi.util.tuples.Pair;

import java.util.*;

// TODO: REMOVE THIS CLASS, AND REPLACE ALL LOGIC USING THEM WITH CONFIG INFO?
public class BookRequirementProperties {

    public float rarity;
    public int requiredExp;
    public float bookBreakChance;
    public int requiredBookShelves;
    public float floorBreakChance;
    public Block floorBlock;

    public BookRequirementProperties(float pRarity, int pRequiredExp, float pBookshelfBreakChance, int pRequiredBookshelves, float pFloorBreakChance, Block pFloorBlock)
    {
        rarity = pRarity;
        requiredExp = pRequiredExp;
        bookBreakChance = pBookshelfBreakChance;
        requiredBookShelves = pRequiredBookshelves;
        floorBreakChance = pFloorBreakChance;
        floorBlock = pFloorBlock;
    }



}
