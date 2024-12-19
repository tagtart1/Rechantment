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

    private final List<Float> maxHealthTiers =Arrays.asList(
            22f,
            24f,
            26f
    );

    public float getMaxHealthTier(int level) {
        return maxHealthTiers.get(level - 1);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}
