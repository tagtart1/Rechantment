package net.tagtart.rechantment.enchantment;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VoidsBaneEnchantment extends Enchantment {
    public VoidsBaneEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pSlots) {
        super(pRarity, pCategory, pSlots);
    }

    public final List<String> validTargets = Arrays.asList(
            "endermanoverhaul:end_enderman",
            "endermanoverhaul:end_islands_enderman",
            "betterend:end_slime",
            "betterend:shadow_walker",
            "alexsmobs:cosmaw",
            "alexsmobs:void_worm",
            "alexsmobs:mimicube",
            "minecraft:enderman",
            "minecraft:endermite",
            "minecraft:shulker",
            "minecraft:ender_dragon");

    private final List<Float> damageBonusLevels = Arrays.asList(
            2f, // Level 1
            3f, // Level 2
            4f, // Level 3
            5f  // Level 4
    );




    public float getDamageBonus(int pLevel) {
        return damageBonusLevels.get(pLevel - 1);
    }


    // ALSO CHECK for Hells Fury later
    @Override
    public boolean checkCompatibility(Enchantment pEnchantment) {
        return !(pEnchantment instanceof DamageEnchantment);
    }

    @Override
    public boolean canEnchant(ItemStack pStack) {
        return pStack.getItem() instanceof AxeItem || super.canEnchant(pStack);
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }
}
