package net.tagtart.rechantment.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class TimberEnchantment  extends Enchantment {
    protected TimberEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
    }

    @Override
    public int getMaxLevel() { return 1; }

    @Override
    public boolean canEnchant(ItemStack pStack) {
        return pStack.getItem() instanceof AxeItem;
    }
}
