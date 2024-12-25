package net.tagtart.rechantment.block.entity;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModReplacementBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, "minecraft"); // This instead of mod-id makes it replace a vanilla registry.

    public static final RegistryObject<BlockEntityType<RechantmentTableBlockEntity>> RECHANTMENT_TABLE_BE =
            BLOCK_ENTITIES.register("enchanting_table", () ->
                    BlockEntityType.Builder.of(RechantmentTableBlockEntity::new,
                            Blocks.ENCHANTING_TABLE).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register((eventBus));
    }
}
