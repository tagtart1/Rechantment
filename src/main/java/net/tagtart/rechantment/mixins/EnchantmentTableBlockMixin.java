package net.tagtart.rechantment.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EnchantmentTableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.EnchantmentTableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import net.tagtart.rechantment.screen.RechantmentTableMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentTableBlock.class)
public class EnchantmentTableBlockMixin
{
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    public void use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit, CallbackInfoReturnable<InteractionResult> cir) {

        if (pPlayer.level().isClientSide) {
            cir.setReturnValue(InteractionResult.SUCCESS);
        } else {
            BlockEntity entity = pLevel.getBlockEntity(pPos);

            if (entity instanceof EnchantmentTableBlockEntity) {
                SimpleMenuProvider openMenu = new SimpleMenuProvider(
                    (id, inventory, player) -> new RechantmentTableMenu(id, inventory, entity), ((EnchantmentTableBlockEntity)entity).getDisplayName());
                NetworkHooks.openScreen((ServerPlayer)pPlayer, openMenu, pPos);
            }
            cir.setReturnValue(InteractionResult.CONSUME);
        }
    }

}


