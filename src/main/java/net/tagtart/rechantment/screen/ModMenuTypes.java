package net.tagtart.rechantment.screen;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tagtart.rechantment.Rechantment;

public class ModMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, Rechantment.MOD_ID);

    public static final RegistryObject<MenuType<RechantmentTableMenu>> RECHANTMENT_TABLE_MENU =
            registerMenuType("rechantment_table_menu", RechantmentTableMenu::new);

    public static final RegistryObject<MenuType<RechantmentTablePoolDisplayMenu>> RECHANTMENT_TABLE_POOL_DISPLAY_MENU =
            registerMenuType("rechantment_table_pool_display_menu", RechantmentTablePoolDisplayMenu::new);

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
