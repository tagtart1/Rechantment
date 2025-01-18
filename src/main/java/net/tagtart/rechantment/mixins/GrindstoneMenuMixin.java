package net.tagtart.rechantment.mixins;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.tagtart.rechantment.config.RechantmentCommonConfigs;
import net.tagtart.rechantment.item.ModItems;
import net.tagtart.rechantment.util.BookRarityProperties;
import net.tagtart.rechantment.util.UtilFunctions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Mixin(GrindstoneMenu.class)
public abstract class GrindstoneMenuMixin {

    @Shadow @Final private Container repairSlots;

    @Shadow @Final private Container resultSlots;

    @Shadow private int xp;

    @Shadow protected abstract ItemStack mergeEnchants(ItemStack pCopyTo, ItemStack pCopyFrom);

    @Shadow protected abstract ItemStack removeNonCurses(ItemStack pStack, int pDamage, int pCount);

    private boolean isEnchantedBook(ItemStack pStack) {
        return pStack.is(Items.ENCHANTED_BOOK) || pStack.is(ModItems.ENCHANTED_BOOK.get());
    }

    @Inject(method = "<init>*", at = @At("TAIL"), cancellable = true)
    public void constructorOverrideMainSlots(int pContainerId, Inventory pPlayerInventory, final ContainerLevelAccess pAccess, CallbackInfo cir) throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        GrindstoneMenu instance = (GrindstoneMenu) (Object) this;

        Field field = ObfuscationReflectionHelper.findField(AbstractContainerMenu.class, "slots");
        field.setAccessible(true);
        NonNullList<Slot> slots = (NonNullList<Slot>)field.get(instance);

        slots.removeIf((slot) -> slot.container == repairSlots || slot.container == resultSlots);

        Slot topInputSlot = new Slot(repairSlots, 0, 49, 19) {
            public boolean mayPlace(ItemStack pStack) {
                return pStack.isDamageableItem() || isEnchantedBook(pStack) || pStack.isEnchanted() || pStack.canGrindstoneRepair();
            }
        };
        Slot bottomInputSlot = new Slot(repairSlots, 1, 49, 40) {
            public boolean mayPlace(ItemStack pStack) {
                return pStack.isDamageableItem() || isEnchantedBook(pStack) || pStack.isEnchanted() || pStack.canGrindstoneRepair();
            }
        };
        Slot resultSlot = new Slot(resultSlots, 2, 129, 34) {
            public boolean mayPlace(ItemStack p_39630_) {
                return false;
            }

            public void onTake(Player pPlayer, ItemStack pStack) {
                if (!ForgeHooks.onGrindstoneTake(repairSlots, pAccess, this::getExperienceAmount)) {
                    pAccess.execute((pLevel, pBlockPos) -> {
                        if (pLevel instanceof ServerLevel) {
                            ExperienceOrb.award((ServerLevel)pLevel, Vec3.atCenterOf(pBlockPos), this.getExperienceAmount(pLevel));
                        }

                        pLevel.levelEvent(1042, pBlockPos, 0);
                    });
                    repairSlots.setItem(0, ItemStack.EMPTY);
                    repairSlots.setItem(1, ItemStack.EMPTY);
                }
            }

            private int getExperienceAmount(Level pLevel) {
                ItemStack topInput = repairSlots.getItem(0);
                ItemStack bottomInput = repairSlots.getItem(1);

                if (xp > -1) {
                    return xp;
                } else {
                    int l = 0;
                    l += this.getExperienceFromItem(topInput);
                    l += this.getExperienceFromItem(bottomInput);
                    if (l > 0) {
                        int i1 = (int)Math.ceil((double)l / (double)2.0F);
                        return i1 + pLevel.random.nextInt(i1);
                    } else {
                        return 0;
                    }
                }
            }

            private int getExperienceFromItem(ItemStack pStack) {
                int l = 0;
                Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(pStack);

                for(Map.Entry<Enchantment, Integer> entry : map.entrySet()) {
                    Enchantment enchantment = (Enchantment)entry.getKey();
                    Integer integer = (Integer)entry.getValue();
                    if (!enchantment.isCurse()) {
                        l += enchantment.getMinCost(integer);
                    }
                }

                return l;
            }
        };

        topInputSlot.index = 0;
        bottomInputSlot.index = 1;
        resultSlot.index = 2;

        slots.add(topInputSlot);
        slots.add(bottomInputSlot);
        slots.add(resultSlot);
        slots.sort(Comparator.comparingInt(a -> a.index));
    }

    @Inject(method = "createResult", at = @At("HEAD"), cancellable = true)
    public void createResult(CallbackInfo cir) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        GrindstoneMenu instance = (GrindstoneMenu) (Object) this;

        Method broadcastMethod = AbstractContainerMenu.class.getDeclaredMethod("broadcastChanges");
        broadcastMethod.setAccessible(true);

        ItemStack topSlot = this.repairSlots.getItem(0);
        ItemStack bottomSlot = this.repairSlots.getItem(1);

        boolean rechantmentBookInTopOnly = (!topSlot.isEmpty() && topSlot.is(ModItems.ENCHANTED_BOOK.get()) && bottomSlot.isEmpty());
        boolean rechantmentBookInBottomOnly = (!bottomSlot.isEmpty() && bottomSlot.is(ModItems.ENCHANTED_BOOK.get()) && topSlot.isEmpty());

        if (rechantmentBookInTopOnly || rechantmentBookInBottomOnly) {
            ItemStack currentStack = rechantmentBookInTopOnly ? topSlot : bottomSlot;
            CompoundTag rootTag = currentStack.getTag();
            CompoundTag enchantmentTag = rootTag.getCompound("Enchantment");
            String enchantmentRaw = enchantmentTag.getString("id");
            String[] enchantmentInfo = enchantmentRaw.split(":");
            BookRarityProperties enchantRarityInfo = UtilFunctions.getPropertiesFromEnchantment(enchantmentRaw);

            if (enchantRarityInfo != null) {
                Random rand = new Random();
                this.xp = rand.nextInt(enchantRarityInfo.minGrindstoneXP, enchantRarityInfo.maxGrindstoneXP + 1);

                ResourceLocation itemLocation = new ResourceLocation(RechantmentCommonConfigs.GRINDSTONE_RESULT_ITEM.get());
                this.resultSlots.setItem(0, new ItemStack(ForgeRegistries.ITEMS.getValue(itemLocation)));
                broadcastMethod.invoke(instance);
                cir.cancel();
            }
        }
    }
}
