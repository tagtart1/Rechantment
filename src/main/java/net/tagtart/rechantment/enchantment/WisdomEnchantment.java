package net.tagtart.rechantment.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class WisdomEnchantment extends Enchantment {
    public WisdomEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pSlots) {
        super(pRarity, pCategory, pSlots);
    }

    private final List<Float> WisdomMultipliers = Arrays.asList(
            2f,     // Level 1
            2.5f    // Level 2
    );

    public float getExpMultiplier(int level) {
        return WisdomMultipliers.get(level - 1);
    }


    @Override
    public boolean canEnchant(ItemStack pStack) {
        return (pStack.getItem() instanceof PickaxeItem);
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }
}
