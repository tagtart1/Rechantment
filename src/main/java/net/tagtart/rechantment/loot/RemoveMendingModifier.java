package net.tagtart.rechantment.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import net.tagtart.rechantment.config.RechantmentCommonConfigs;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Supplier;

public class RemoveMendingModifier extends LootModifier {
    public static final Supplier<Codec<RemoveMendingModifier>> CODEC = Suppliers.memoize(()
            -> RecordCodecBuilder.create(inst -> codecStart(inst).apply(inst, RemoveMendingModifier::new)));

    public RemoveMendingModifier(LootItemCondition[] conditions) {
        super(conditions);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext lootContext) {
        if (RechantmentCommonConfigs.REMOVE_MENDING_ENABLED.get()) {
            for (int i = 0; i < generatedLoot.size(); i++) {
                ItemStack stack = generatedLoot.get(i);

                if (stack.isEnchanted()) {
                    Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
                    System.out.println(enchantments);
                    if (enchantments.containsKey(Enchantments.MENDING)) {
                        enchantments.remove(Enchantments.MENDING);
                        EnchantmentHelper.setEnchantments(enchantments, stack);
                        generatedLoot.set(i, stack);
                    }
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
