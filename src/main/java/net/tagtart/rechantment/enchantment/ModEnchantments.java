package net.tagtart.rechantment.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tagtart.rechantment.Rechantment;

// Registers all custom enchantments
public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Rechantment.MOD_ID);

    private static final EnchantmentCategory SHIELD_CATEGORY = EnchantmentCategory.create(
            "shield",
            item -> item instanceof ShieldItem
    );

    private static final EnchantmentCategory PICKAXE_CATEGORY = EnchantmentCategory.create(
            "pickaxe",
            item -> item instanceof PickaxeItem
    );

    private static final EnchantmentCategory AXE_CATEGORY = EnchantmentCategory.create(
            "pickaxe",
            item -> item instanceof AxeItem
    );

    public static RegistryObject<Enchantment> THUNDER_STRIKE =
            ENCHANTMENTS.register("thunder_strike", () -> new ThunderStrikeEnchantment(
                    Enchantment.Rarity.VERY_RARE, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND));

    public static RegistryObject<Enchantment> BASH =
            ENCHANTMENTS.register("bash", () -> new BashEnchantment(
                    Enchantment.Rarity.COMMON, SHIELD_CATEGORY, EquipmentSlot.OFFHAND));

    public static RegistryObject<Enchantment> COURAGE =
            ENCHANTMENTS.register("courage", () -> new CourageEnchantment(
                    Enchantment.Rarity.COMMON, SHIELD_CATEGORY, EquipmentSlot.OFFHAND));

    public static RegistryObject<Enchantment> VOIDS_BANE =
            ENCHANTMENTS.register("voids_bane", () -> new VoidsBaneEnchantment(
                    Enchantment.Rarity.RARE, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND
            ));

    public static RegistryObject<Enchantment> HELLS_FURY =
            ENCHANTMENTS.register("hells_fury", () -> new HellsFuryEnchantment(
               Enchantment.Rarity.RARE, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND
            ));

    public static RegistryObject<Enchantment> ICE_ASPECT =
            ENCHANTMENTS.register("ice_aspect", () -> new IceAspectEnchantment(
                    Enchantment.Rarity.RARE, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND
            ));

    public static RegistryObject<Enchantment> WISDOM =
            ENCHANTMENTS.register("wisdom", () -> new WisdomEnchantment(
                    Enchantment.Rarity.RARE, PICKAXE_CATEGORY, EquipmentSlot.MAINHAND
            ));

    public static RegistryObject<Enchantment> INQUISITIVE =
            ENCHANTMENTS.register("inquisitive", () -> new InquisitiveEnchantment(
                    Enchantment.Rarity.RARE, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND
            ));

    public static RegistryObject<Enchantment> BERSERK =
            ENCHANTMENTS.register("berserk", () -> new BerserkEnchantment(
                    Enchantment.Rarity.COMMON, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND
            ));

    public static RegistryObject<Enchantment> OVERLOAD =
            ENCHANTMENTS.register("overload", () -> new OverloadEnchantment(
                    Enchantment.Rarity.VERY_RARE, EnchantmentCategory.ARMOR_CHEST, EquipmentSlot.CHEST
            ));

    public static RegistryObject<Enchantment> TELEPATHY =
            ENCHANTMENTS.register("telepathy", () -> new TelepathyEnchantment(
                    Enchantment.Rarity.COMMON, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND
            ));

    public static RegistryObject<Enchantment> VEIN_MINER =
            ENCHANTMENTS.register("vein_miner", () -> new VeinMinerEnchantment(
                    Enchantment.Rarity.RARE, PICKAXE_CATEGORY, EquipmentSlot.MAINHAND
            ));

    public static RegistryObject<Enchantment> TIMBER =
            ENCHANTMENTS.register("timber", () -> new TimberEnchantment(
                    Enchantment.Rarity.RARE, AXE_CATEGORY, EquipmentSlot.MAINHAND
            ));

    public static RegistryObject<Enchantment> REBIRTH =
            ENCHANTMENTS.register("rebirth", () -> new RebirthEnchantment(
                    Enchantment.Rarity.VERY_RARE, EnchantmentCategory.BREAKABLE, EquipmentSlot.MAINHAND
            ));

    public static RegistryObject<Enchantment> REBORN =
            ENCHANTMENTS.register("reborn", () -> new RebornEnchantment(
                    Enchantment.Rarity.VERY_RARE, EnchantmentCategory.BREAKABLE, EquipmentSlot.MAINHAND
            ));

    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }
}
