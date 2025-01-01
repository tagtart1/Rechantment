package net.tagtart.rechantment.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.tagtart.rechantment.Rechantment;

// TODO: REMOVE THIS?
// Just here in case it's needed for dev.
public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Rechantment.MOD_ID);

    public static final RegistryObject<CreativeModeTab> TUTORIAL_TAB = CREATIVE_MODE_TABS.register("rechantment_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(Blocks.ENCHANTING_TABLE))
                    .title(Component.literal("Rechantment"))  // !! Translatable need a name defined in en_us.json!!!
                    .displayItems((pParameters, pOutput) -> {

                        // Adding Modded items to tab display in this order via accept()
                        pOutput.accept(Blocks.ENCHANTING_TABLE);

                        pOutput.accept(ModItems.CHANCE_GEM.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}