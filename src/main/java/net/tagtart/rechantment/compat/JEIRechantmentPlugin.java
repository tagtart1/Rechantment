package net.tagtart.rechantment.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.ingredients.subtypes.ISubtypeManager;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.ForgeRegistries;
import net.tagtart.rechantment.Rechantment;
import net.tagtart.rechantment.item.ModItems;
import net.tagtart.rechantment.util.BookRarityProperties;
import net.tagtart.rechantment.util.EnchantmentPoolEntry;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class JEIRechantmentPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Rechantment.MOD_ID, "jei_plugin");
    }


    // Refer to kaupenjoe video if  you want implement extreme compatibility with our mod.
    // We could have the enchantment gui in JEI with each of our books - giving odds, etc

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {

    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        // Register item subtypes for JEI to distinguish by NBT for our books
        registration.registerSubtypeInterpreter(ModItems.ENCHANTED_BOOK.get(), (itemStack, level) -> {
            if (itemStack.hasTag() && itemStack.getTag().contains("Enchantment")) {
                return itemStack.getTag().getCompound("Enchantment").toString();
            }
            return null;
        });
    }

}
