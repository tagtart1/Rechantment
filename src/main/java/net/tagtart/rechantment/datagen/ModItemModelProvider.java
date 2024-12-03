package net.tagtart.rechantment.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.tagtart.rechantment.Rechantment;
import net.tagtart.rechantment.item.ModItems;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Rechantment.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        bookItem(ModItems.ENCHANTED_BOOK);
        textureOnly("simple");
        textureOnly("unique");
        textureOnly("elite");
        textureOnly("ultimate");
        textureOnly("legendary");
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Rechantment.MOD_ID, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder textureOnly(String path) {
        return withExistingParent((path),
                new ResourceLocation("item/generated"))
                .texture("layer0", new ResourceLocation(Rechantment.MOD_ID, "item/" + path));

    }


    private ItemModelBuilder.OverrideBuilder bookItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated"))
                .texture("layer0", new ResourceLocation(Rechantment.MOD_ID, "item/" + "simple"))
                .override()
                .predicate(new ResourceLocation(Rechantment.MOD_ID, "book_rarity"),1)
                .model(new ModelFile.UncheckedModelFile(new ResourceLocation(Rechantment.MOD_ID, "item/simple")))
                .end()
                .override()
                .predicate(new ResourceLocation(Rechantment.MOD_ID, "book_rarity"),2)
                .model(new ModelFile.UncheckedModelFile(new ResourceLocation(Rechantment.MOD_ID, "item/unique")))
                .end()
                .override()
                .predicate(new ResourceLocation(Rechantment.MOD_ID, "book_rarity"),3)
                .model(new ModelFile.UncheckedModelFile(new ResourceLocation(Rechantment.MOD_ID, "item/elite")))
                .end()
                .override()
                .predicate(new ResourceLocation(Rechantment.MOD_ID, "book_rarity"),4)
                .model(new ModelFile.UncheckedModelFile(new ResourceLocation(Rechantment.MOD_ID, "item/ultimate")))
                .end()
                .override()
                .predicate(new ResourceLocation(Rechantment.MOD_ID, "book_rarity"),5)
                .model(new ModelFile.UncheckedModelFile(new ResourceLocation(Rechantment.MOD_ID, "item/legendary")));


    }
}
