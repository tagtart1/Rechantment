package net.tagtart.rechantment.util;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.tagtart.rechantment.Rechantment;
import net.tagtart.rechantment.config.RechantmentCommonConfigs;
import net.tagtart.rechantment.item.ModItems;

public class ModItemProperties {
    public static void addCustomItemProperties() {
        ItemProperties.register(ModItems.ENCHANTED_BOOK.get(),
                new ResourceLocation(Rechantment.MOD_ID, "book_rarity" ),
                (itemStack, clientLevel, livingEntity, i) -> {
                    CompoundTag nbt = itemStack.getTag();
                    float rarity = 0f;
                    assert nbt != null;
                    CompoundTag enchantmentTag = nbt.getCompound("Enchantment");
                    String enchantment = enchantmentTag.getString("id");

                    // TODO: MAKE THE OTHER CONFIG FOR OTHER RARITES AND CODE THAT SHIT IN

                    if (RechantmentCommonConfigs.RARITY_0_ENCHANTMENTS.get().contains(enchantment)) {
                        rarity = 1f;
                    } else if (RechantmentCommonConfigs.UNIQUE_ENCHANTMENTS.get().contains(enchantment)) {
                        rarity = 2f;
                    } else if (RechantmentCommonConfigs.ELITE_ENCHANTMENTS.get().contains(enchantment)) {
                        rarity = 3f;
                    } else if (RechantmentCommonConfigs.ULTIMATE_ENCHANTMENTS.get().contains(enchantment)) {
                        rarity = 4f;
                    } else if (RechantmentCommonConfigs.LEGENDARY_ENCHANTMENTS.get().contains(enchantment)) {
                        rarity = 5f;
                    }
                    return rarity;
                });
    }
}
