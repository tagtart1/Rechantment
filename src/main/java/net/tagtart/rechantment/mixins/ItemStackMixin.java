package net.tagtart.rechantment.mixins;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.tagtart.rechantment.enchantment.ModEnchantments;
import net.tagtart.rechantment.enchantment.RebirthEnchantment;
import net.tagtart.rechantment.util.UtilFunctions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import oshi.util.tuples.Pair;

import javax.swing.text.Utilities;
import java.util.Map;
import java.util.function.Consumer;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(method = "hurtAndBreak", at = @At("Head"), cancellable = true)
    public <T extends LivingEntity> void hurtAndBreak(int pAmount, T pEntity, Consumer<T> pOnBroken, CallbackInfo ci) {
        ItemStack itemstack = (ItemStack) (Object) this;
        Pair<RebirthEnchantment, Integer> rebirthEnchantmentPair = UtilFunctions.getEnchantmentFromItem("rechantment:rebirth", itemstack, RebirthEnchantment.class);
        if (itemstack.getItem() instanceof ArmorItem armorItem && rebirthEnchantmentPair != null) {
            if (!pEntity.level().isClientSide && (!(pEntity instanceof Player) || !((Player) pEntity).getAbilities().instabuild) && itemstack.isDamageableItem()) {
                pAmount = itemstack.getItem().damageItem(itemstack, pAmount, pEntity, pOnBroken);
                if (itemstack.hurt(pAmount, pEntity.getRandom(), pEntity instanceof ServerPlayer ? (ServerPlayer) pEntity : null)) {
                    EquipmentSlot armorSlot = armorItem.getEquipmentSlot();
                    pOnBroken.accept(pEntity);
                    Item item = itemstack.getItem();
                    itemstack.shrink(1);
                    if (pEntity instanceof  Player player) {
                        player.awardStat(Stats.ITEM_BROKEN.get(item));

                        RebirthEnchantment rebirthEnchantment = rebirthEnchantmentPair.getA();
                        if (rebirthEnchantment.shouldBeReborn(rebirthEnchantmentPair.getB())){
                            UtilFunctions.triggerRebirthClientEffects(player, (ServerLevel) player.level(), armorItem.getDefaultInstance());

                           ItemStack armorItemStack = armorItem.getDefaultInstance();
                           armorItemStack.setTag(itemstack.getTag());
                            Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(armorItemStack);
                            enchantments.remove(rebirthEnchantment);
                            enchantments.put(ModEnchantments.REBORN.get(), 1);
                            EnchantmentHelper.setEnchantments(enchantments, armorItemStack);

                            armorItemStack.removeTagKey("RepairCost");
                            if (armorSlot != null) {
                                player.setItemSlot(armorSlot, armorItemStack);
                            }
                        } else {
                            player.sendSystemMessage(Component.literal("Your item failed to be reborn!").withStyle(ChatFormatting.RED));
                        }
                    }

                    itemstack.setDamageValue(0);


                }
            }
        }
    }
}
