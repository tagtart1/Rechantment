package net.tagtart.rechantment.util;

import net.minecraft.world.level.block.Block;

import java.lang.ref.ReferenceQueue;

// TODO: REMOVE THIS CLASS, AND REPLACE ALL LOGIC USING THEM WITH CONFIG INFO.
public class BookRequirementProperties {

    int requiredExp;
    float bookBreakChance;
    int requiredBookShelves;
    float floorBreakChance;
    Block floorBlock;
    public BookRequirementProperties(int pRequiredExp, float pBookshelfBreakChance, int pRequiredBookshelves, float pFloorBreakChance, Block pFloorBlock)
    {
        requiredExp = pRequiredExp;
        bookBreakChance = pBookshelfBreakChance;
        requiredBookShelves = pRequiredBookshelves;
        floorBreakChance = pFloorBreakChance;
        floorBlock = pFloorBlock;
    }
}
