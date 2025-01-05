package net.tagtart.rechantment.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
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
            for (ItemStack itemStack : generatedLoot) {
                if (itemStack.isEnchanted()) {
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
