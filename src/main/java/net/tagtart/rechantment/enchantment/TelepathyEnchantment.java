package net.tagtart.rechantment.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class TelepathyEnchantment extends Enchantment {
    public TelepathyEnchantment(Rarity rarity, EnchantmentCategory pCategory, EquipmentSlot... slots) {
        super(rarity, pCategory, slots);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean canEnchant(ItemStack pStack) {
       Item item =  pStack.getItem();
        return item instanceof SwordItem || super.canEnchant(pStack);
    }

}
