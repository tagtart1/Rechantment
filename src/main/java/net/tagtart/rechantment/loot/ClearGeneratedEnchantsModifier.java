package net.tagtart.rechantment.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.*;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.tagtart.rechantment.config.RechantmentCommonConfigs;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ClearGeneratedEnchantsModifier extends LootModifier {
    public static final Supplier<Codec<ClearGeneratedEnchantsModifier>> CODEC = Suppliers.memoize(()
            -> RecordCodecBuilder.create(inst -> codecStart(inst).apply(inst, ClearGeneratedEnchantsModifier::new)));

    public ClearGeneratedEnchantsModifier(LootItemCondition[] conditions) {
        super(conditions);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext lootContext) {
        if (RechantmentCommonConfigs.CLEAR_ENCHANTED_LOOT.get()) {
            boolean excludeLowerTiers = RechantmentCommonConfigs.EXCLUDE_LOWER_TIER_LOOT.get();
            boolean excludeFishing = RechantmentCommonConfigs.EXCLUDE_FISHING_LOOT.get();
            String lootTableId = lootContext.getQueriedLootTableId().toString();

            if ((lootTableId.contains("minecraft:gameplay/fishing") && excludeFishing)) return generatedLoot;
            for (ItemStack itemStack : generatedLoot) {
                Item item = itemStack.getItem();
                if (itemStack.isEnchanted()) {

                    if (excludeLowerTiers) {
                        // Excludes gold, leather, and chain armor
                        if (item instanceof ArmorItem armorItem) {
                            ArmorMaterial material = armorItem.getMaterial();
                            if (material == ArmorMaterials.LEATHER ||
                                    material == ArmorMaterials.GOLD) {
                                continue;
                            }
                        }

                        // Excludes gold tool
                        else if (item instanceof TieredItem tieredItem) {
                            Tier tier = tieredItem.getTier();
                            if (tier == Tiers.GOLD ||
                                    tier == Tiers.WOOD ||
                                    tier == Tiers.STONE) {
                                continue;
                            }
                        }
                    }
                    itemStack.removeTagKey("Enchantments");
                }
            }
        }
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
