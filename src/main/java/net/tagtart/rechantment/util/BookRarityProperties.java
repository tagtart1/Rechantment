package net.tagtart.rechantment.util;

import net.minecraft.world.level.block.Block;

// TODO: REMOVE THIS CLASS, AND REPLACE ALL LOGIC USING THEM WITH CONFIG INFO?
public class BookRarityProperties {

    public float rarity;
    public int requiredExp;
    public float bookBreakChance;
    public int requiredBookShelves;
    public float floorBreakChance;
    public Block floorBlock;

    public BookRarityProperties(float pRarity, int pRequiredExp, float pBookshelfBreakChance, int pRequiredBookshelves, float pFloorBreakChance, Block pFloorBlock)
    {
        rarity = pRarity;
        requiredExp = pRequiredExp;
        bookBreakChance = pBookshelfBreakChance;
        requiredBookShelves = pRequiredBookshelves;
        floorBreakChance = pFloorBreakChance;
        floorBlock = pFloorBlock;
    }



}
