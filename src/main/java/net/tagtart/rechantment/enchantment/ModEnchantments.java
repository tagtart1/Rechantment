package net.tagtart.rechantment.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tagtart.rechantment.Rechantment;

// Registers all custom enchantments
// Rarity can be anything as we overwrite this system within our mod
public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Rechantment.MOD_ID);

    public static RegistryObject<Enchantment> THUNDER_STRIKE =
            ENCHANTMENTS.register("thunder_strike", () -> new ThunderStrikeEnchantment(
                    Enchantment.Rarity.COMMON, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND));

    public static RegistryObject<Enchantment> BASH =
            ENCHANTMENTS.register("bash", () -> new BashEnchantment(
                    Enchantment.Rarity.COMMON, EnchantmentCategory.BREAKABLE, EquipmentSlot.OFFHAND));


    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }
}
