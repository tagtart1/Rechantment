package net.tagtart.rechantment.event;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.tagtart.rechantment.Rechantment;
import net.tagtart.rechantment.enchantment.*;
import net.tagtart.rechantment.sound.ModSounds;
import net.tagtart.rechantment.util.UtilFunctions;
import oshi.util.tuples.Pair;

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
                float bonusDamage = 0;
                Pair<VoidsBaneEnchantment, Integer> voidsBaneEnchantment = UtilFunctions
                        .getEnchantmentFromItem("rechantment:voids_bane",
                                weapon,
                                VoidsBaneEnchantment.class);
                Pair<HellsFuryEnchantment, Integer> hellsFuryEnchantment = UtilFunctions
                        .getEnchantmentFromItem("rechantment:hells_fury",
                                weapon,
                                HellsFuryEnchantment.class);
                Pair<BerserkEnchantment, Integer> berserkEnchantment = UtilFunctions
                        .getEnchantmentFromItem("rechantment:berserk",
                                weapon,
                                BerserkEnchantment.class);

                ResourceLocation targetId = ForgeRegistries.ENTITY_TYPES.getKey(event.getEntity().getType());
                if (targetId == null) return;
                String targetIdString = targetId.toString();
                if (voidsBaneEnchantment != null && voidsBaneEnchantment.getA().validTargets.contains(targetIdString) )
                {
                    int enchantmentOnWeaponLevel = voidsBaneEnchantment.getB();
                    bonusDamage += voidsBaneEnchantment.getA().getDamageBonus(enchantmentOnWeaponLevel);
                } else if (hellsFuryEnchantment != null && hellsFuryEnchantment.getA().validTargets.contains(targetIdString)) {
                    int enchantmentOnWeaponLevel = hellsFuryEnchantment.getB();
                    bonusDamage += hellsFuryEnchantment.getA().getDamageBonus(enchantmentOnWeaponLevel);
                }

                if (berserkEnchantment != null) {
                    int enchantmentOnWeaponLevel = berserkEnchantment.getB();
                    bonusDamage += berserkEnchantment.getA().getDamageBonus(player, enchantmentOnWeaponLevel);
                }

                // Apply the damage effects
                event.setAmount(event.getAmount() + bonusDamage);
            }
        }
        @SubscribeEvent
        public static void onBlockBreak(BlockEvent.BreakEvent event) {
            // Do the Wisdom stuff here
            ItemStack pickaxe = event.getPlayer().getMainHandItem();
            Pair<WisdomEnchantment, Integer> wisdomEnchant = UtilFunctions.getEnchantmentFromItem("rechantment:wisdom", pickaxe, WisdomEnchantment.class);
                if (wisdomEnchant != null) {
                    // Multiply the exp orbs droppped
                    float expMultiplier = wisdomEnchant.getA().getExpMultiplier(wisdomEnchant.getB());
                    int newExpToDrop = (int)((float)event.getExpToDrop() * expMultiplier);
                    event.setExpToDrop(newExpToDrop);
                }

        }



        @SubscribeEvent
        public static void onExpDropFromHostile(LivingExperienceDropEvent event) {
            MobCategory mobCategory = event.getEntity().getType().getCategory();
            if (mobCategory != MobCategory.MONSTER ||
                event.getAttackingPlayer() == null) return;

            ItemStack weapon = event.getAttackingPlayer().getMainHandItem();
            Pair<InquisitiveEnchantment, Integer> inquisitiveEnchantment = UtilFunctions.getEnchantmentFromItem("rechantment:inquisitive", weapon, InquisitiveEnchantment.class);
            if (inquisitiveEnchantment != null) {
                InquisitiveEnchantment inquisitiveEnchantInstance = inquisitiveEnchantment.getA();
                int enchantLevel = inquisitiveEnchantment.getB();
                int newExpToDrop = (int)((float) event.getDroppedExperience() * inquisitiveEnchantInstance.getExpMultiplier(enchantLevel));
                event.setDroppedExperience(newExpToDrop);
            }
        }

        @SubscribeEvent
        public static void onArmorEquip(LivingEquipmentChangeEvent event) {
            if (event.getSlot().getType() == EquipmentSlot.Type.ARMOR) {
                LivingEntity player = event.getEntity();

                // Example: Check if new item is a specific armor piece
                ItemStack newArmor = event.getTo();
                if (!newArmor.isEmpty()) {
                    player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(24f);
                    if (!player.level().isClientSide) {
                        player.level().playSound(null, player.getOnPos(), SoundEvents.TRIDENT_RETURN, SoundSource.PLAYERS, 1f, 1f);
                    }
                    System.out.println(newArmor);
                } else {
                    player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20f);

                    if (player.getHealth() > player.getMaxHealth()) {
                        player.setHealth(player.getMaxHealth());
                    }


                }

            }
        }
    }
}
