package net.tagtart.rechantment.enchantment;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;


public class BashEnchantment extends Enchantment {
    public BashEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
    }




    @Override
    public int getMaxLevel() {
        return 1;
    }


    public boolean canEnchant(ItemStack pStack) {
        return pStack.getItem() instanceof ShieldItem;
    }
}
