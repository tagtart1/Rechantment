package net.tagtart.rechantment.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EnchantmentTableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import net.tagtart.rechantment.block.entity.RechantmentTableBlockEntity;
import net.tagtart.rechantment.screen.RechantmentTableMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(EnchantmentTableBlock.class)
public class EnchantmentTableBlockMixin
{
    @Unique
    private static final List<BlockPos> BOOKSHELF_OFFSETS = BlockPos.betweenClosedStream(-3, 0, -3, 3, 2, 3).filter((pos) -> (Math.abs(pos.getX()) <= 3 && Math.abs(pos.getX()) != 2) ||
                                                                                                                                                                                Math.abs(pos.getZ()) <= 3 && Math.abs(pos.getZ()) != 2).map(BlockPos::immutable).toList();

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    public void use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit, CallbackInfoReturnable<InteractionResult> cir) {

        if (pPlayer.level().isClientSide) {
            cir.setReturnValue(InteractionResult.SUCCESS);
        } else {
            BlockEntity entity = pLevel.getBlockEntity(pPos);

            if (entity instanceof RechantmentTableBlockEntity) {
                SimpleMenuProvider openMenu = new SimpleMenuProvider(
                    (id, inventory, player) -> new RechantmentTableMenu(id, inventory, entity), ((RechantmentTableBlockEntity)entity).getDisplayName());
                NetworkHooks.openScreen((ServerPlayer)pPlayer, openMenu, pPos);
            }
            cir.setReturnValue(InteractionResult.CONSUME);
        }
    }

    @Inject(method = "newBlockEntity", at = @At("HEAD"), cancellable = true)
    public void newBlockEntity(BlockPos pPos, BlockState pState, CallbackInfoReturnable<BlockEntity> cir) {
        cir.setReturnValue(new RechantmentTableBlockEntity(pPos, pState));
    }

    @Inject(method = "animateTick", at = @At("TAIL"), cancellable = false)
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom, CallbackInfo ci) {
        for(BlockPos blockpos : BOOKSHELF_OFFSETS) {
            if (pRandom.nextInt(16) == 0 && rechantment$isValidBookShelf(pLevel, pPos, blockpos)) {
                pLevel.addParticle(ParticleTypes.ENCHANT, (double)pPos.getX() + (double)0.5F, (double)pPos.getY() + (double)2.0F, (double)pPos.getZ() + (double)0.5F, (double)((float)blockpos.getX() + pRandom.nextFloat()) - (double)0.5F, (double)((float)blockpos.getY() - pRandom.nextFloat() - 1.0F), (double)((float)blockpos.getZ() + pRandom.nextFloat()) - (double)0.5F);
            }
        }
    }

    @Unique
    private static boolean rechantment$isValidBookShelf(Level pLevel, BlockPos p_207911_, BlockPos p_207912_) {
        return pLevel.getBlockState(p_207911_.offset(p_207912_)).getEnchantPowerBonus(pLevel, p_207911_.offset(p_207912_)) != 0.0F && pLevel.getBlockState(p_207911_.offset(p_207912_.getX() / 2, p_207912_.getY(), p_207912_.getZ() / 2)).is(BlockTags.ENCHANTMENT_POWER_TRANSMITTER);
    }
}


