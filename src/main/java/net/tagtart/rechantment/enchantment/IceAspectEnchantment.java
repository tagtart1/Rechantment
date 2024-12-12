package net.tagtart.rechantment.enchantment;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.FireAspectEnchantment;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class IceAspectEnchantment extends Enchantment {
    public IceAspectEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pSlots) {
        super(pRarity, pCategory, pSlots);
    }

    List<Integer> IceAspectDurationTicks = Arrays.asList(
            30, // Level 1
            40  // Level 2
    );

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    protected boolean checkCompatibility(Enchantment pOther) {
        return !(pOther instanceof FireAspectEnchantment);
    }

    @Override
    public void doPostAttack(LivingEntity pAttacker, Entity pTarget, int pLevel) {
        if (!(pTarget instanceof LivingEntity livingTarget)) {return;}
        MobEffectInstance slownessEffect = new MobEffectInstance(
                MobEffects.MOVEMENT_SLOWDOWN,
                IceAspectDurationTicks.get(pLevel - 1),
                2);
        livingTarget.extinguishFire();
        pAttacker.addEffect(slownessEffect);
        livingTarget.addEffect(slownessEffect);
    }
}
