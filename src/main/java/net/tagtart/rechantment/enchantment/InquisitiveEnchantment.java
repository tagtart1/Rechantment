package net.tagtart.rechantment.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.Arrays;
import java.util.List;

public class InquisitiveEnchantment extends Enchantment {
    public InquisitiveEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pSlots) {
        super(pRarity, pCategory, pSlots);
    }

    private final List<Float> InquisitiveMultipliers = Arrays.asList(
            1.25f, // Level 1
            1.50f, // Level 2
            1.75f, // Level 3
            2.00f  // Level 4
    );

    public float getExpMultiplier(int level) {
        return InquisitiveMultipliers.get(level - 1);
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public boolean canEnchant(ItemStack pStack) {
        return pStack.getItem() instanceof AxeItem || super.canEnchant(pStack);
    }
}
