package net.tagtart.rechantment.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class CourageEnchantment extends Enchantment {
    public CourageEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pSlots) {
        super(pRarity, pCategory, pSlots);
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    public boolean canEnchant(ItemStack pStack) {
        return pStack.getItem() instanceof ShieldItem;
    }
}