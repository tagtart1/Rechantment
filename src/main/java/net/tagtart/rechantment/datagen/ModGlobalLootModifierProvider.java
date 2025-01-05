package net.tagtart.rechantment.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.tagtart.rechantment.Rechantment;
import net.tagtart.rechantment.loot.ClearGeneratedEnchantsModifier;
import net.tagtart.rechantment.loot.RemoveMendingModifier;
import net.tagtart.rechantment.loot.ReplaceItemModifier;

public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifierProvider(PackOutput output) {
        super(output, Rechantment.MOD_ID);
    }

    @Override
    protected void start() {
        add("replace_vanilla_books", new ReplaceItemModifier(new LootItemCondition[]{
        }));

        add("remove_mending_from_items", new RemoveMendingModifier(new LootItemCondition[]{}));

        add("clear_enchants_from_items", new ClearGeneratedEnchantsModifier(new LootItemCondition[]{}));

        // Example implentation for implementing a new drop

       /* add("chance_gem_from_dirt", new AddItemModifier(new LootItemCondition[] {
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.DIRT).build(),
                LootItemRandomChanceCondition.randomChance(.35f).build()
        }, ModItems.CHANCE_GEM.get())); **/
    }
}
