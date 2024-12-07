package net.tagtart.rechantment.enchantment;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.Map;
import java.util.Random;

// Chance to cast a lightning bolt on target
public class ThunderStrikeEnchantment extends Enchantment {
    public ThunderStrikeEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
    }

  // Maps enchantment level to a success rate of spawning lightning
   private final Map<Integer, Integer> successMapping = Map.of(
           1, 10,
           2, 20
   );

    @Override
    public void doPostAttack(LivingEntity pAttacker, Entity pTarget, int pLevel) {

        if (!pAttacker.level().isClientSide()) {
            ServerLevel world = ((ServerLevel) pAttacker.level());
            BlockPos targetPosition = pTarget.blockPosition();

            if (isSuccess(pLevel)) {
                EntityType.LIGHTNING_BOLT.spawn(world, (ItemStack) null, (ServerPlayer) pAttacker, targetPosition, MobSpawnType.TRIGGERED, true, true);
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
