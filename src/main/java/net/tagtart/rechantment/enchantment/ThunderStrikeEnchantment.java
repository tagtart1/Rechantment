package net.tagtart.rechantment.enchantment;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.BiomeSources;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

// Chance to cast a lightning bolt on target
public class ThunderStrikeEnchantment extends Enchantment {
    public ThunderStrikeEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
    }

    private final float LIGHTNING_DAMAGE = 5.0f;
    private final float LIGHTNING_RADIUS = 2.0f;
    private final float LIGHTNING_KNOCKBACK = 1.10f;
    private final float LIGHTNING_Y_KNOCKBACK = 0.3f;
  // Maps enchantment level to a success rate of spawning lightning
   private final List<Float> successRates = Arrays.asList(
          0.10f,    // Level 1
          0.15f     // Level 2
   );

    public float rollLightningStrike(LivingEntity pAttacker, Entity mainTarget, int level) {
        if (isSuccess(level)) {
            ServerLevel world = ((ServerLevel) pAttacker.level());
            LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, world);
            lightningBolt.moveTo(Vec3.atBottomCenterOf(mainTarget.blockPosition()));
            lightningBolt.setVisualOnly(true);
            world.addFreshEntity(lightningBolt);

            AABB area = new AABB(
                    mainTarget.getX() - LIGHTNING_RADIUS, mainTarget.getY() - LIGHTNING_RADIUS, mainTarget.getZ() - LIGHTNING_RADIUS,
                    mainTarget.getX() + LIGHTNING_RADIUS, mainTarget.getY() + LIGHTNING_RADIUS, mainTarget.getZ() + LIGHTNING_RADIUS
            );

            world.getEntities(mainTarget, area, e -> e instanceof LivingEntity).forEach(entity -> {
                LivingEntity target = (LivingEntity) entity;
                if (target == pAttacker) return;
                // Now do the mff damage
                target.hurt(target.damageSources().lightningBolt(), LIGHTNING_DAMAGE);

                double d0 = target.getX() - mainTarget.getX();
                double d1 = target.getZ() - mainTarget.getZ();
                Vec2 toAttacker = new Vec2((float)d0, (float)d1);
                toAttacker = toAttacker.normalized();
                toAttacker = toAttacker.scale(LIGHTNING_KNOCKBACK);


                if (target.isPushable()) {
                    target.push(toAttacker.x, LIGHTNING_Y_KNOCKBACK, toAttacker.y);
                }
            });

            return LIGHTNING_DAMAGE;
        }
        return 0f;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    public boolean canEnchant(ItemStack pStack) {
        return pStack.getItem() instanceof AxeItem || super.canEnchant(pStack);
    }

    private boolean isSuccess(int level) {
          float successRate =  successRates.get(level - 1);
            Random random = new Random();
            return random.nextFloat() < successRate;
    }
}
