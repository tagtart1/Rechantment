package net.adamtwitty.adammod.datagen;

import net.adamtwitty.adammod.AdamMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = AdamMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new net.adamtwitty.adammod.datagen.ModRecipeProvider(packOutput));
        generator.addProvider(event.includeServer(), net.adamtwitty.adammod.datagen.ModLootTableProvider.create(packOutput));

        generator.addProvider(event.includeClient(), new net.adamtwitty.adammod.datagen.ModBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new ModItemModelProvider(packOutput, existingFileHelper));

        net.adamtwitty.adammod.datagen.ModBlockTagGenerator blockTagGenerator = generator.addProvider(event.includeServer(),
                new net.adamtwitty.adammod.datagen.ModBlockTagGenerator(packOutput, lookupProvider, existingFileHelper));

        generator.addProvider(event.includeServer(), new net.adamtwitty.adammod.datagen.ModItemTagGenerator(packOutput, lookupProvider, blockTagGenerator.contentsGetter(), existingFileHelper));

    }
}
