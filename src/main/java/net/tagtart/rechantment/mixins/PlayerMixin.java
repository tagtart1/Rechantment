package net.tagtart.rechantment.mixins;


import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.tagtart.rechantment.enchantment.ModEnchantments;
import net.tagtart.rechantment.enchantment.OverloadEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
/*
@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {
    @Unique
    private List<ItemStack> ARMOR_CACHE;


    private static final String OVERLOAD_MODIFER = "overload_max_health_increase";

    public PlayerMixin(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Unique
    private boolean shouldUpdateAttributes = true;

    @Unique
    private boolean newArmorHasOverload = false;
    @Shadow
    public abstract Iterable<ItemStack> getArmorSlots();

    // Leaving this here in case something else breaks and need to try this again.
    //@Inject(method = "tick", at = @At("HEAD"), cancellable = false)
    private void tick(CallbackInfo ci) {
        if (this.ARMOR_CACHE == null) {
            this.ARMOR_CACHE = StreamSupport.stream(getArmorSlots().spliterator(), false)
                    .collect(Collectors.toList());
        }
        Player player = (Player)(Object)this;

        int i = 0;
        // CURRENT BUG: if you leave with overload equipped and unequip to no armor then the overload modifier remains. no idea why
        // as if the cache and the getArmorSlots are not syncing right or something....
        for (ItemStack armorStack : this.getArmorSlots()) {
            ItemStack cachedStack = (ItemStack)this.ARMOR_CACHE.get(i);

            if ((cachedStack.getItem() != armorStack.getItem()) ||
                    (getOverloadLevel(cachedStack) != getOverloadLevel(armorStack)))  {
                int armorOverloadLevel = getOverloadLevel(armorStack);

                if (player.getAttributes().hasModifier(Attributes.MAX_HEALTH, player.getUUID())) {

                    player.getAttribute(Attributes.MAX_HEALTH).removeModifier(player.getUUID());

                    if (player.getHealth() > player.getMaxHealth()) {
                        System.out.println("Lowering health!");
                       player.setHealth(player.getMaxHealth());
                    }

                    if (armorOverloadLevel > 0) {
                        System.out.println("Equipped Overload!");
                    }
                }
                this.shouldUpdateAttributes = true;
                this.ARMOR_CACHE.set(i, armorStack.copy());
            }
            i++;
        }

        if (this.shouldUpdateAttributes) {
            int overloadLevel = EnchantmentHelper.getTagEnchantmentLevel(ModEnchantments.OVERLOAD.get(), player.getItemBySlot(EquipmentSlot.CHEST));

            System.out.println("player health: "+ player.getHealth());
            if (overloadLevel > 0 && !player.getAttributes().hasModifier(Attributes.MAX_HEALTH, player.getUUID())) {
                System.out.println("Updating attributes");
                player.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(new AttributeModifier(player.getUUID(), OVERLOAD_MODIFER, OverloadEnchantment.getAdditionalHearts(overloadLevel), AttributeModifier.Operation.ADDITION));
            }



            this.shouldUpdateAttributes = false;
        }
    }

    // Helper function to get the overload enchantment level
    private int getOverloadLevel(ItemStack itemStack) {
        // Assuming OVERLOAD is the enchantment you're checking for
        return EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.OVERLOAD.get(), itemStack);
    }

}
*/