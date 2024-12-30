package net.tagtart.rechantment.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import net.tagtart.rechantment.item.ModItems;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class AddItemModifier extends LootModifier {
    public static final Supplier<Codec<AddItemModifier>> CODEC = Suppliers.memoize(()
    -> RecordCodecBuilder.create(inst -> codecStart(inst).apply(inst, AddItemModifier::new)));

    public AddItemModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext lootContext) {
        for (int i = 0; i < generatedLoot.size(); i++) {
            ItemStack stack = generatedLoot.get(i);
            if (stack.getItem() instanceof EnchantedBookItem) {
                ItemStack replacementBook = new ItemStack(ModItems.ENCHANTED_BOOK.get());
                CompoundTag rootTag = replacementBook.getOrCreateTag();
                CompoundTag enchantmentTag = new CompoundTag();

                enchantmentTag.putString("id", "minecraft:fortune");
                enchantmentTag.putInt("lvl", 3);
                int successRate = 99;

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
