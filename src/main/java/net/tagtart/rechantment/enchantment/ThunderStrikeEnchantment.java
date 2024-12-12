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
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
import java.util.Random;

// Chance to cast a lightning bolt on target
public class ThunderStrikeEnchantment extends Enchantment {
    public ThunderStrikeEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
    }

    private float LIGHTNING_DAMAGE = 5.0f;
  // Maps enchantment level to a success rate of spawning lightning
   private final Map<Integer, Integer> successMapping = Map.of(
           1, 15,
           2, 20
   );



    @Override
    public void doPostAttack(LivingEntity pAttacker, Entity pTarget, int pLevel) {

        if (!pAttacker.level().isClientSide()) {
            ServerLevel world = ((ServerLevel) pAttacker.level());
            BlockPos targetPosition = pTarget.blockPosition();

            if (isSuccess(pLevel)) {
                LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, world);
                lightningBolt.moveTo(Vec3.atBottomCenterOf(targetPosition));
                lightningBolt.setVisualOnly(true);
                world.addFreshEntity(lightningBolt);

                pTarget.hurt(pTarget.damageSources().lightningBolt(), LIGHTNING_DAMAGE);
            }
        }
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    public boolean canEnchant(ItemStack pStack) {
        return pStack.getItem() instanceof AxeItem || super.canEnchant(pStack);
    }

    private boolean isSuccess(int level) {
          int successRate =   successMapping.get(level);
            Random random = new Random();
            return random.nextInt(100) < successRate;
    }
}
