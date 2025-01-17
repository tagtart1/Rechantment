package net.tagtart.rechantment.item;

import net.minecraft.Util;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tagtart.rechantment.Rechantment;
import net.tagtart.rechantment.util.BookRarityProperties;
import net.tagtart.rechantment.util.EnchantmentPoolEntry;
import net.tagtart.rechantment.util.UtilFunctions;

// TODO: REMOVE THIS?
// Just here in case it's needed for dev.
public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Rechantment.MOD_ID);

    public static final RegistryObject<CreativeModeTab> RECHANTMENT_TAB = CREATIVE_MODE_TABS.register("rechantment_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> {
                        ItemStack icon = new ItemStack(ModItems.ENCHANTED_BOOK.get());
                        CompoundTag rootTag = icon.getOrCreateTag();


                        rootTag.putBoolean("IconOnly", true);
                        return icon;
                    })
                    .title(Component.translatable("creative.rechantment.title"))  // !! Translatable need a name defined in en_us.json!!!
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.CHANCE_GEM.get());
                        BookRarityProperties[] bookRarityProperties = BookRarityProperties.getAllProperties();

                        for(BookRarityProperties bookRarityProperty : bookRarityProperties) {
                            for(EnchantmentPoolEntry enchantPoolEntry : bookRarityProperty.enchantmentPool) {
                               String enchantmentRaw = enchantPoolEntry.enchantment;
                                Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(enchantmentRaw));
                                if (enchantment == null) continue;
                               int maxLevel = enchantment.getMaxLevel();
                               for(int i = 1 ; i <= maxLevel ; i++) {
                                   ItemStack book = new ItemStack(ModItems.ENCHANTED_BOOK.get());

                                   CompoundTag rootTag = book.getOrCreateTag();
                                   CompoundTag enchantmentTag = new CompoundTag();

                                   enchantmentTag.putInt("lvl", i);
                                   enchantmentTag.putString("id", enchantmentRaw);

                                   rootTag.put("Enchantment", enchantmentTag);
                                   rootTag.putInt("SuccessRate", 100);
                                   book.setTag(rootTag);
                                   pOutput.accept(book);
                               }
                            }
                        }
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}