package net.tagtart.rechantment.event;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.tagtart.rechantment.Rechantment;
import net.tagtart.rechantment.enchantment.*;
import net.tagtart.rechantment.util.UtilFunctions;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class ModEvents {

    @Mod.EventBusSubscriber(modid = Rechantment.MOD_ID)
    public static class ForgeEvents {

        public static float SHIELD_BASH_KNOCKBACK = 1.15f;
        public static float SHIELD_BASH_KNOCKBACK_Y = 0.4f;

        public static int SHIELD_COURAGE_SPEED_DURATION = 40; // Speed in ticks


        @SubscribeEvent
        public static void onShieldBlock(ShieldBlockEvent event) {
            LivingEntity player = event.getEntity();
            DamageSource source = event.getDamageSource();
            Entity attacker = source.getEntity();
            ItemStack shield = player.getUseItem();

            if(shield.getItem() instanceof ShieldItem) {


                // TODO: add Courage enchantment
                ResourceLocation bashResource = new ResourceLocation("rechantment:bash");
                ResourceLocation courageResource = new ResourceLocation("rechantment:courage");
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

                }

                // Check for Courage enchantment
                if (shieldEnchants.containsKey(ForgeRegistries.ENCHANTMENTS.getValue(courageResource))) {
                   int enchantmentLevel = shieldEnchants.get(ForgeRegistries.ENCHANTMENTS.getValue(courageResource));
                   MobEffectInstance speedEffect = new MobEffectInstance(
                           MobEffects.MOVEMENT_SPEED,
                           SHIELD_COURAGE_SPEED_DURATION,
                           enchantmentLevel - 1
                   );

                    player.addEffect(speedEffect);
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

        @SubscribeEvent
        public static void onLivingHurt(LivingHurtEvent event) {
            // Check if attacker is player
            if (event.getSource().getEntity() instanceof LivingEntity player) {
                ItemStack weapon = player.getMainHandItem();

                ResourceLocation voidsBaneResource = new ResourceLocation("rechantment:voids_bane");
                ResourceLocation hellsFuryResource = new ResourceLocation("rechantment:hells_fury");

                Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(weapon);
                Enchantment voidsBaneBase = ForgeRegistries.ENCHANTMENTS.getValue(voidsBaneResource);
                Enchantment hellsFuryBase = ForgeRegistries.ENCHANTMENTS.getValue(hellsFuryResource);
                // Make this better later yknow
                // Check if the weapon has voids bane
                if (enchantments.containsKey(voidsBaneBase)) {
                   VoidsBaneEnchantment voidsBane = (VoidsBaneEnchantment) voidsBaneBase;
                   ResourceLocation targetId = ForgeRegistries.ENTITY_TYPES.getKey(event.getEntity().getType());
                   if (targetId != null && voidsBane != null) {
                       String targetIdString = targetId.toString();

                       // Check if the target can be effected by voids bane
                       if (voidsBane.validTargets.contains(targetIdString)) {
                           // Apply bonus damage
                           int enchantmentOnWeaponLevel = enchantments.get(voidsBaneBase);
                           float bonusDamage = voidsBane.getDamageBonus(enchantmentOnWeaponLevel);
                           event.setAmount(event.getAmount() + bonusDamage);

                       }
                   }
                } else if (enchantments.containsKey(hellsFuryBase)) {
                    HellsFuryEnchantment hellsFury = (HellsFuryEnchantment) hellsFuryBase;
                    ResourceLocation targetId = ForgeRegistries.ENTITY_TYPES.getKey(event.getEntity().getType());

                    if (targetId != null && hellsFury != null) {
                        String targetIdString = targetId.toString();

                        if (hellsFury.validTargets.contains(targetIdString)) {
                            int enchantmentOnWeaponLevel = enchantments.get(hellsFuryBase);
                            float bonusDamage = hellsFury.getDamageBonus(enchantmentOnWeaponLevel);
                            event.setAmount(event.getAmount() + bonusDamage);
                        }
                    }
                }
            }
        }
        @SubscribeEvent
        public static void onBlockBreak(BlockEvent.BreakEvent event) {
            // Do the Wisdom stuff here
            // to-do make a helper function that does the checks and returns the enchantment instance
            ItemStack pickaxe = event.getPlayer().getMainHandItem();
            ResourceLocation wisdomResource = new ResourceLocation("rechantment:wisdom");
            Map<Enchantment, Integer> pickaxeEnchantments = EnchantmentHelper.getEnchantments(pickaxe);
            Enchantment wisdomBase = ForgeRegistries.ENCHANTMENTS.getValue(wisdomResource);
            if (pickaxeEnchantments.containsKey(wisdomBase)) {
                WisdomEnchantment wisdom = (WisdomEnchantment) wisdomBase;
                int enchantLevel = pickaxeEnchantments.get(wisdomBase);
                if (wisdom != null) {
                    // Multiply the exp orbs droppped
                    float expMultiplier = wisdom.getExpMultiplier(enchantLevel);
                    int newExpToDrop = (int)((float)event.getExpToDrop() * expMultiplier);
                    System.out.println("old exp before mult: " + event.getExpToDrop());
                    event.setExpToDrop(newExpToDrop);
                    System.out.println(event.getExpToDrop());
                }
            }
        }


        @SubscribeEvent
        public static void onExpDropFromHostile(LivingExperienceDropEvent event) {

            MobCategory mobCategory = event.getEntity().getType().getCategory();
            if (mobCategory == MobCategory.MONSTER) {
                System.out.println("Original exp drop: " + event.getDroppedExperience());
                int newExpToDrop = (int)((float) event.getDroppedExperience() * 10.5);
                event.setDroppedExperience(newExpToDrop);
                System.out.println("New exp dropped: " + event.getDroppedExperience());
            }

        }
    }
}
