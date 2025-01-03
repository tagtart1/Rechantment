package net.tagtart.rechantment.enchantment;

import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RebirthEnchantment extends Enchantment {
    public RebirthEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pSlots) {
        super(pRarity, pCategory, pSlots);
    }

    private final List<Float> successRates = Arrays.asList(
            0.50f,
            0.75f,
            1.00f
    );


    @Override
    public int getMaxLevel() {
        return 3;
    }

    public boolean shouldBeReborn(int level) {
        return isSuccess(level);
    }

    private boolean isSuccess(int level) {
        float successRate =  successRates.get(level - 1);
        Random random = new Random();
        return random.nextFloat() < successRate;
    }
}
