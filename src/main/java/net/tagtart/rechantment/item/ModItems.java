package net.tagtart.rechantment.item;


import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tagtart.rechantment.Rechantment;
import net.tagtart.rechantment.item.custom.ChanceGem;
import net.tagtart.rechantment.item.custom.EnchantedBookItem;

import java.awt.print.Book;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Rechantment.MOD_ID);

    public static final RegistryObject<Item> ENCHANTED_BOOK = ITEMS.register("enchanted_book", () -> new EnchantedBookItem(new Item.Properties()));

    public static final RegistryObject<Item> CHANCE_GEM = ITEMS.register("chance_gem", () -> new ChanceGem(new Item.Properties()));

    public static void register (IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
