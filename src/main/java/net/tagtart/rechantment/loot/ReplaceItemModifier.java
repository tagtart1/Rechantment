package net.tagtart.rechantment.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.tagtart.rechantment.item.ModItems;
import net.tagtart.rechantment.util.BookRarityProperties;
import net.tagtart.rechantment.util.EnchantmentPoolEntry;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.function.Supplier;

public class ReplaceItemModifier extends LootModifier {
    public static final Supplier<Codec<ReplaceItemModifier>> CODEC = Suppliers.memoize(()
    -> RecordCodecBuilder.create(inst -> codecStart(inst).apply(inst, ReplaceItemModifier::new)));

    public ReplaceItemModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext lootContext) {
        for (int i = 0; i < generatedLoot.size(); i++) {
            ItemStack stack = generatedLoot.get(i);
            if (stack.getItem() instanceof EnchantedBookItem) {
                ItemStack replacementBook = new ItemStack(ModItems.ENCHANTED_BOOK.get());
                BookRarityProperties bookRarityProperties = BookRarityProperties.getRandomRarityWeighted();
                EnchantmentPoolEntry randomEnchantment = bookRarityProperties.getRandomEnchantmentWeighted();
                int enchantmentLevel = randomEnchantment.getRandomEnchantLevelWeighted();
                CompoundTag rootTag = replacementBook.getOrCreateTag();
                CompoundTag enchantmentTag = new CompoundTag();

                enchantmentTag.putString("id", randomEnchantment.enchantment);
                enchantmentTag.putInt("lvl", enchantmentLevel);

                Random random = new Random();
                int successRate = random.nextInt(bookRarityProperties.minSuccess, bookRarityProperties.maxSuccess);

                rootTag.put("Enchantment", enchantmentTag);
                rootTag.putInt("SuccessRate", successRate);

                generatedLoot.set(i, replacementBook);
            }
        }
        return generatedLoot;
    }


    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
