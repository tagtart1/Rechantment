package net.tagtart.rechantment.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
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

    public static RegistryObject<Enchantment> COURAGE =
            ENCHANTMENTS.register("courage", () -> new CourageEnchantment(
                    Enchantment.Rarity.COMMON, EnchantmentCategory.BREAKABLE, EquipmentSlot.OFFHAND));

    public static RegistryObject<Enchantment> VOIDS_BANE =
            ENCHANTMENTS.register("voids_bane", () -> new VoidsBaneEnchantment(
                    Enchantment.Rarity.COMMON, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND
            ));

    public static RegistryObject<Enchantment> HELLS_FURY =
            ENCHANTMENTS.register("hells_fury", () -> new HellsFuryEnchantment(
               Enchantment.Rarity.COMMON, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND
            ));

    public static RegistryObject<Enchantment> ICE_ASPECT =
            ENCHANTMENTS.register("ice_aspect", () -> new IceAspectEnchantment(
                    Enchantment.Rarity.COMMON, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND
            ));

    public static RegistryObject<Enchantment> WISDOM =
            ENCHANTMENTS.register("wisdom", () -> new WisdomEnchantment(
                    Enchantment.Rarity.COMMON, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND
            ));

    public static RegistryObject<Enchantment> INQUISITIVE =
            ENCHANTMENTS.register("inquisitive", () -> new InquisitiveEnchantment(
                    Enchantment.Rarity.COMMON, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND
            ));

    public static RegistryObject<Enchantment> BERSERK =
            ENCHANTMENTS.register("berserk", () -> new BerserkEnchantment(
                    Enchantment.Rarity.COMMON, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND
            ));

    public static RegistryObject<Enchantment> OVERLOAD =
            ENCHANTMENTS.register("overload", () -> new OverloadEnchantment(
                    Enchantment.Rarity.COMMON, EnchantmentCategory.ARMOR_CHEST, EquipmentSlot.CHEST
            ));

    public static RegistryObject<Enchantment> TELEPATHY =
            ENCHANTMENTS.register("telepathy", () -> new TelepathyEnchantment(
                    Enchantment.Rarity.COMMON, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND
            ));

    public static RegistryObject<Enchantment> VEIN_MINER =
            ENCHANTMENTS.register("vein_miner", () -> new VeinMinerEnchantment(
                    Enchantment.Rarity.COMMON, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND
            ));

    public static RegistryObject<Enchantment> TIMBER =
            ENCHANTMENTS.register("timber", () -> new TimberEnchantment(
                    Enchantment.Rarity.COMMON, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND
            ));

    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }
}
