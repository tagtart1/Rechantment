package net.tagtart.rechantment.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.tagtart.rechantment.Rechantment;
import net.tagtart.rechantment.loot.ReplaceItemModifier;

public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifierProvider(PackOutput output) {
        super(output, Rechantment.MOD_ID);
    }

    @Override
    protected void start() {
        add("replace_vanilla_books", new ReplaceItemModifier(new LootItemCondition[]{

        }));
    }
}
