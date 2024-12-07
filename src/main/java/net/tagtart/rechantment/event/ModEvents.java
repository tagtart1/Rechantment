package net.tagtart.rechantment.event;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.tagtart.rechantment.Rechantment;
import net.tagtart.rechantment.util.UtilFunctions;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class ModEvents {

    @Mod.EventBusSubscriber(modid = Rechantment.MOD_ID)
    public static class ForgeEvents {

        public static float SHIELD_BASH_KNOCKBACK = 1.15f;
        public static float SHIELD_BASH_KNOCKBACK_Y = 0.4f;

        @SubscribeEvent
        public static void onShieldBlock(ShieldBlockEvent event) {
            LivingEntity player = event.getEntity();
            DamageSource source = event.getDamageSource();
            Entity attacker = source.getEntity();
            ItemStack shield = player.getUseItem();

            if(shield.getItem() instanceof ShieldItem) {
                // TODO: tweak knockback value

                // TODO: add Courage enchantment
                ResourceLocation bashResource = new ResourceLocation("rechantment:bash");
                Map<Enchantment, Integer> shieldEnchants = EnchantmentHelper.getEnchantments(shield);
                // Handle bash enchantment
                if (shieldEnchants.containsKey(ForgeRegistries.ENCHANTMENTS.getValue(bashResource))) {
                    if (source.getDirectEntity() instanceof Projectile) {
                        return;
                    }

                    double d0 = attacker.getX() - player.getX();
                    double d1 = attacker.getZ() - player.getZ();
                    Vec2 toAttacker = new Vec2((float)d0, (float)d1);
                    toAttacker = toAttacker.normalized();
                    toAttacker = toAttacker.scale(SHIELD_BASH_KNOCKBACK);


                        if (attacker.isPushable()) {
                            attacker.push(toAttacker.x, SHIELD_BASH_KNOCKBACK_Y, toAttacker.y);
                        }

                    System.out.println(attacker.isPushable());
                }
            }
        }

        @SubscribeEvent
        public static void onItemToolTip(ItemTooltipEvent event) {
            ItemStack stack = event.getItemStack();
            List<Component> tooltip = event.getToolTip();

            if (stack.isEnchanted()) {
                for (Map.Entry<Enchantment, Integer> entry : EnchantmentHelper.getEnchantments(stack).entrySet()) {
                    Enchantment enchantment = entry.getKey();
                    int level = entry.getValue();
                    String enchantmentRaw = ForgeRegistries.ENCHANTMENTS.getKey(enchantment).toString();
                    // Create the modified enchantment tooltip with color change
                    String enchantmentText = enchantment.getFullname(level).getString();
                    Component modifiedText = Component.literal(enchantmentText).withStyle(Style.EMPTY
                            .withColor(UtilFunctions.getRarityInfo(enchantmentRaw).getB()));  // Change to any color you prefer

                    for(int i = 0; i < tooltip.size(); i++) {
                        Component tooltipLine = tooltip.get(i);
                        if (tooltipLine.getString().contains(enchantmentText)) {
                            tooltip.set(i, modifiedText);
                        }
                    }
                }
            }
        }
    }
}
