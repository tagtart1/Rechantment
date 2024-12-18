package net.tagtart.rechantment.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.Arrays;
import java.util.List;

public class BerserkEnchantment extends Enchantment {
    public BerserkEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pSlots) {
        super(pRarity, pCategory, pSlots);
    }

    private final List<Float> damageBonus = Arrays.asList(
            .15f,
            .20f,
            .25f
    );

    public float getDamageBonus(LivingEntity player, int level) {
       float playerHealth = player.getHealth();
       float playerMaxHealth = player.getMaxHealth();
       float missingHealth = playerMaxHealth - playerHealth;
       float additionalDamage = damageBonus.get(level - 1);
       // Multiply the additional damage by the missing health
        // Getting weird float precision math results here idk
       return missingHealth * additionalDamage;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean canEnchant(ItemStack pStack) {
        return pStack.getItem() instanceof AxeItem || super.canEnchant(pStack);
    }

    /**
    @Override
    protected boolean checkCompatibility(Enchantment pOther) {
        return !(pOther instanceof  ThunderStrikeEnchantment);
    }
    **/
}
