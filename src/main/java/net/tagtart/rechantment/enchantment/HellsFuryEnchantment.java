package net.tagtart.rechantment.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.Arrays;
import java.util.List;

public class HellsFuryEnchantment extends Enchantment {
    public HellsFuryEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pSlots) {
        super(pRarity, pCategory, pSlots);
    }

    public final List<String> validTargets = Arrays.asList(
            "betternether:naga",
            "betternether:jungle_skeleton",
            "frycmobvariants:executioner",
            "frycmobvariants:nightmare",
            "frycmobvariants:infected_piglin",
            "frycmobvariants:infected_piglin_brute",
            "frycmobvariants:soul_stealer",
            "alexsmobs:crimson_mosquito",
            "alexsmobs:soul_vulture",
            "alexsmobs:warped_mosco",
            "alexsmobs:laviathan",
            "alexsmobs:dropbear",
            "minecraft:blaze",
            "minecraft:ghast",
            "minecraft:hoglin",
            "minecraft:zombified_piglin",
            "minecraft:strider",
            "minecraft:piglin_brute",
            "minecraft:piglin",
            "minecraft:magma_cube",
            "minecraft:wither_skeleton"
    );

    private final List<Float> damageBonusLevels = Arrays.asList(
            2f, // Level 1
            3f, // Level 2
            4f, // Level 3
            5f, // Level 4
            6f  // Level 5
    );

    public float getDamageBonus(int pLevel) {
        return damageBonusLevels.get(pLevel - 1);
    }


    // ALSO CHECK for Hells Fury later
    @Override
    public boolean checkCompatibility(Enchantment pEnchantment) {
        return !(pEnchantment instanceof DamageEnchantment || pEnchantment instanceof VoidsBaneEnchantment);
    }

    @Override
    public boolean canEnchant(ItemStack pStack) {
        return pStack.getItem() instanceof AxeItem || super.canEnchant(pStack);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }
}
