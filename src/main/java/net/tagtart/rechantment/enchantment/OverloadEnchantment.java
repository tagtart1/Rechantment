package net.tagtart.rechantment.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.Arrays;
import java.util.List;

public class OverloadEnchantment extends Enchantment {
    public OverloadEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pSlots) {
        super(pRarity, pCategory, pSlots);
    }

    private final List<Float> maxHealthIncreaseTiers =Arrays.asList(
            2f,
            4f,
            6f
    );

    public float getMaxHealthTier(int level) {
        return maxHealthIncreaseTiers.get(level - 1);
    }

    public static float getAdditionalHearts(int level) {
        return level * 2;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}
