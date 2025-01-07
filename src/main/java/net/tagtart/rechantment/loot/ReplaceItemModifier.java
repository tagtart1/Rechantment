package net.tagtart.rechantment.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.ForgeConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import net.tagtart.rechantment.config.RechantmentCommonConfigs;
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
            // Replaces iron, diamond, and netherite enchanted gear with books

            boolean replaceEnchantedLoot = RechantmentCommonConfigs.REPLACE_ENCHANTED_LOOT.get();
            boolean excludeLowerTiers = RechantmentCommonConfigs.EXCLUDE_LOWER_TIER_LOOT.get();
            boolean excludeFishing = RechantmentCommonConfigs.EXCLUDE_FISHING_LOOT.get();
            String lootTableId = lootContext.getQueriedLootTableId().toString();

            // Removes vanilla enchanted books from loot pools
            // Happens by default - not configurable
            if (stack.getItem() instanceof EnchantedBookItem) {
                ItemStack replacementBook = rollModdedBook();

                generatedLoot.set(i, replacementBook);
            }



            // Gold, leather, chainmail tiers remain with their enchants
            else if (replaceEnchantedLoot && stack.isEnchanted()) {
                Item item = stack.getItem();
                if ((lootTableId.contains("minecraft:gameplay/fishing") && excludeFishing)) {
                    stack.removeTagKey("Enchantments");
                    continue;
                }
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


                // The item is an enchanted piece of gear so replace it with a rolled book
                ItemStack replacementBook = rollModdedBook();
                generatedLoot.set(i, replacementBook);
            }

        }
        return generatedLoot;
    }


    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }

    private ItemStack rollModdedBook() {
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

        return replacementBook;
    }
}
