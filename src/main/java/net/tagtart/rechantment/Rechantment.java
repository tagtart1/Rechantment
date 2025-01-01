package net.tagtart.rechantment;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.tagtart.rechantment.block.ModBlocks;
import net.tagtart.rechantment.block.entity.ModReplacementBlockEntities;
import net.tagtart.rechantment.config.RechantmentCommonConfigs;
import net.tagtart.rechantment.enchantment.ModEnchantments;
import net.tagtart.rechantment.item.ModCreativeModeTabs;
import net.tagtart.rechantment.item.ModItems;
import net.tagtart.rechantment.loot.ModLootModifiers;
import net.tagtart.rechantment.networking.ModPackets;
import net.tagtart.rechantment.screen.RechantmentTablePoolDisplayScreen;
import net.tagtart.rechantment.sound.ModSounds;
import net.tagtart.rechantment.screen.ModMenuTypes;
import net.tagtart.rechantment.screen.RechantmentTableScreen;
import net.tagtart.rechantment.util.ModItemProperties;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Rechantment.MOD_ID)
public class Rechantment
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "rechantment";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public Rechantment()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModMenuTypes.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModReplacementBlockEntities.register(modEventBus);
        ModSounds.register(modEventBus);
        ModEnchantments.register(modEventBus);
        ModLootModifiers.register(modEventBus);

        ModCreativeModeTabs.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, RechantmentCommonConfigs.SPEC, "rechantment-config.toml");

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            ModPackets.register();
        });
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            ModItemProperties.addCustomItemProperties();
            MenuScreens.register(ModMenuTypes.RECHANTMENT_TABLE_MENU.get(), RechantmentTableScreen::new);
            MenuScreens.register(ModMenuTypes.RECHANTMENT_TABLE_POOL_DISPLAY_MENU.get(), RechantmentTablePoolDisplayScreen::new);
        }
    }
}
