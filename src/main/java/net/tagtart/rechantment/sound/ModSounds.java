package net.tagtart.rechantment.sound;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tagtart.rechantment.Rechantment;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Rechantment.MOD_ID);

    public static final RegistryObject<SoundEvent> ENCHANTED_BOOK_FAIL = registerSoundEvents("enchanted_book_fail");

    public static final RegistryObject<SoundEvent> REBIRTH_ITEM = registerSoundEvents("rebirth_item");
    public static final RegistryObject<SoundEvent> ENCHANT_TABLE_AMBIENT = registerSoundEvents("enchant_table_ambient");
    public static final RegistryObject<SoundEvent> ENCHANT_TABLE_CHARGE = registerSoundEvents("enchant_table_charge");
    public static final RegistryObject<SoundEvent> ENCHANT_TABLE_DISCHARGE = registerSoundEvents("enchant_table_discharge");
    public static final RegistryObject<SoundEvent> ENCHANT_TABLE_OPEN = registerSoundEvents("enchant_table_open");
    public static final RegistryObject<SoundEvent> ENCHANT_TABLE_CLOSE = registerSoundEvents("enchant_table_close");

    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Rechantment.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
