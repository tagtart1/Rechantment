package net.tagtart.rechantment.enchantment;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
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

    List<String> affectedMobs = Arrays.asList(
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



    @Override
    public void doPostAttack(LivingEntity pAttacker, Entity pTarget, int pLevel) {
        ResourceLocation entityId = ForgeRegistries.ENTITY_TYPES.getKey(pTarget.getType());
        if (entityId == null) { return;}
        if (affectedMobs.contains(entityId.toString())) {
            System.out.println(entityId);
        }
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
