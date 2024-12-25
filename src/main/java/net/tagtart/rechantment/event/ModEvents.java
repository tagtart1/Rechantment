package net.tagtart.rechantment.event;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.UntouchingEnchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.tagtart.rechantment.Rechantment;
import net.tagtart.rechantment.enchantment.*;
import net.tagtart.rechantment.sound.ModSounds;
import net.tagtart.rechantment.util.UtilFunctions;
import oshi.util.tuples.Pair;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ModEvents {

    @Mod.EventBusSubscriber(modid = Rechantment.MOD_ID)
    public static class ForgeEvents {

        public static float SHIELD_BASH_KNOCKBACK = 1.15f;
        public static float SHIELD_BASH_KNOCKBACK_Y = 0.4f;

        public static int SHIELD_COURAGE_SPEED_DURATION = 40; // Speed in ticks


        @SubscribeEvent
        public static void onShieldBlock(ShieldBlockEvent event) {
            LivingEntity player = event.getEntity();
            DamageSource source = event.getDamageSource();
            Entity attacker = source.getEntity();
            ItemStack shield = player.getUseItem();

            if(!(shield.getItem() instanceof ShieldItem)) return;


            // TODO: add Courage enchantment
            ResourceLocation bashResource = new ResourceLocation("rechantment:bash");
            ResourceLocation courageResource = new ResourceLocation("rechantment:courage");
            Map<Enchantment, Integer> shieldEnchants = EnchantmentHelper.getEnchantments(shield);
            // Handle bash enchantment
            if (shieldEnchants.containsKey(ForgeRegistries.ENCHANTMENTS.getValue(bashResource))) {
                if (source.getDirectEntity() instanceof Projectile) {
                    return;
                }

                double d0 = attacker.getX() - player.getX();
                double d1 = attacker.getZ() - player.getZ();
                Vec2 toAttacker = new Vec2((float)d0, (float)d1);
                toAttacker = toAttacker.normalized();
                toAttacker = toAttacker.scale(SHIELD_BASH_KNOCKBACK);


                if (attacker.isPushable()) {
                    attacker.push(toAttacker.x, SHIELD_BASH_KNOCKBACK_Y, toAttacker.y);
                }

            }

            // Check for Courage enchantment
            if (shieldEnchants.containsKey(ForgeRegistries.ENCHANTMENTS.getValue(courageResource))) {
                  int enchantmentLevel = shieldEnchants.get(ForgeRegistries.ENCHANTMENTS.getValue(courageResource));
                  MobEffectInstance speedEffect = new MobEffectInstance(
                          MobEffects.MOVEMENT_SPEED,
                          SHIELD_COURAGE_SPEED_DURATION,
                          enchantmentLevel - 1
                  );
                  player.addEffect(speedEffect);
            }
        }

        @SubscribeEvent
        public static void onItemToolTip(ItemTooltipEvent event) {
            ItemStack stack = event.getItemStack();
            List<Component> tooltip = event.getToolTip();

            if (stack.isEnchanted()) {
                for (Map.Entry<Enchantment, Integer> entry : EnchantmentHelper.getEnchantments(stack).entrySet()) {
                    Enchantment enchantment = entry.getKey();
                    int level = entry.getValue();
                    String enchantmentRaw = ForgeRegistries.ENCHANTMENTS.getKey(enchantment).toString();
                    // Create the modified enchantment tooltip with color change
                    String enchantmentText = enchantment.getFullname(level).getString();
                    Component modifiedText = Component.literal(enchantmentText).withStyle(UtilFunctions.getRarityInfo(enchantmentRaw).getB());  // Change to any color you prefer

                    for(int i = 0; i < tooltip.size(); i++) {
                        Component tooltipLine = tooltip.get(i);
                        if (tooltipLine.getString().contains(enchantmentText)) {
                            tooltip.set(i, modifiedText);
                        }
                    }
                }
            }
        }

        @SubscribeEvent
        public static void onLivingHurt(LivingHurtEvent event) {
            // Check if attacker is player
            if (event.getSource().getEntity() instanceof LivingEntity player) {
                ItemStack weapon = player.getMainHandItem();
                float bonusDamage = 0;
                Pair<VoidsBaneEnchantment, Integer> voidsBaneEnchantment = UtilFunctions
                        .getEnchantmentFromItem("rechantment:voids_bane",
                                weapon,
                                VoidsBaneEnchantment.class);
                Pair<HellsFuryEnchantment, Integer> hellsFuryEnchantment = UtilFunctions
                        .getEnchantmentFromItem("rechantment:hells_fury",
                                weapon,
                                HellsFuryEnchantment.class);
                Pair<BerserkEnchantment, Integer> berserkEnchantment = UtilFunctions
                        .getEnchantmentFromItem("rechantment:berserk",
                                weapon,
                                BerserkEnchantment.class);

                ResourceLocation targetId = ForgeRegistries.ENTITY_TYPES.getKey(event.getEntity().getType());
                if (targetId == null) return;
                String targetIdString = targetId.toString();
                if (voidsBaneEnchantment != null && voidsBaneEnchantment.getA().validTargets.contains(targetIdString) )
                {
                    int enchantmentOnWeaponLevel = voidsBaneEnchantment.getB();
                    bonusDamage += voidsBaneEnchantment.getA().getDamageBonus(enchantmentOnWeaponLevel);
                } else if (hellsFuryEnchantment != null && hellsFuryEnchantment.getA().validTargets.contains(targetIdString)) {
                    int enchantmentOnWeaponLevel = hellsFuryEnchantment.getB();
                    bonusDamage += hellsFuryEnchantment.getA().getDamageBonus(enchantmentOnWeaponLevel);
                }

                if (berserkEnchantment != null) {
                    int enchantmentOnWeaponLevel = berserkEnchantment.getB();
                    bonusDamage += berserkEnchantment.getA().getDamageBonus(player, enchantmentOnWeaponLevel);
                }

                // Apply the damage effects
                event.setAmount(event.getAmount() + bonusDamage);
            }
        }



        // Telepathy, Vein Miner, Timber, Wisdom Enchantments - Blocks
        // TODO: TELEPATHY ONLY DOESNT TAKE DURABILITY
        @SubscribeEvent
        public static void onBlockBreak(BlockEvent.BreakEvent event) {

            if (event.getPlayer().level().isClientSide()) return;

            ItemStack handItem = event.getPlayer().getMainHandItem();
            Pair<TelepathyEnchantment, Integer> telepathyEnchantment = UtilFunctions.getEnchantmentFromItem("rechantment:telepathy", handItem, TelepathyEnchantment.class);
            Pair<VeinMinerEnchantment, Integer> veinMinerEnchantment = UtilFunctions.getEnchantmentFromItem("rechantment:vein_miner", handItem, VeinMinerEnchantment.class);
            Pair<TimberEnchantment, Integer> timberEnchantment = UtilFunctions.getEnchantmentFromItem("rechantment:timber", handItem, TimberEnchantment.class);
            Pair<WisdomEnchantment, Integer> wisdomEnchantment = UtilFunctions.getEnchantmentFromItem("rechantment:wisdom", handItem, WisdomEnchantment.class);
            int fortuneEnchantmentLevel = handItem.getEnchantmentLevel(Enchantments.BLOCK_FORTUNE);
            ServerLevel level = (ServerLevel) event.getPlayer().level();


            if (veinMinerEnchantment != null && event.getState().is(Tags.Blocks.ORES)) {
                BlockPos[] oresToDestroy = UtilFunctions.BFSLevelForBlocks(level, Tags.Blocks.ORES, event.getPos(), 10, true);
                destroyBulkBlocks(event, oresToDestroy, level, handItem, (telepathyEnchantment != null) ? telepathyEnchantment.getA() : null, fortuneEnchantmentLevel);
                event.setCanceled(true);
            }

            else if (timberEnchantment != null && event.getState().is(BlockTags.LOGS)) {
                BlockPos[] woodToDestroy = UtilFunctions.BFSLevelForBlocks(level, BlockTags.LOGS, event.getPos(), 10, true);
                destroyBulkBlocks(event, woodToDestroy, level, handItem, (telepathyEnchantment != null) ? telepathyEnchantment.getA() : null, fortuneEnchantmentLevel);
                event.setCanceled(true);
            }

            // Telepathy check. Only happens when destroying a single block normally here
            else if (telepathyEnchantment != null) {
                telepathicallyDestroyBlock(event, event.getPos(), level, handItem, fortuneEnchantmentLevel);
                handItem.hurt(1, level.random, (ServerPlayer)event.getPlayer());
            }

            // Fortune by itself with vanilla enchants without other breakevent enchant
            // TODO: coniditionly ensure this is allowed in config
            else if (fortuneEnchantmentLevel != 0){
                // Block info
                BlockState blockState = event.getState();
                Block block = blockState.getBlock();
                BlockPos blockPos = event.getPos();

                if (!blockState.is(Tags.Blocks.ORES) || !handItem.isCorrectToolForDrops(blockState)) return;

                // Fetch the block drops without tool
               List<ItemStack> drops = Block.getDrops(event.getState(), level, event.getPos(), null);
               // Pop exp
               int expToPop = blockState.getExpDrop(level, RandomSource.create(), blockPos, 0, 0);
               if (wisdomEnchantment != null) {
                   float expMultiplier = wisdomEnchantment.getA().getExpMultiplier(wisdomEnchantment.getB());
                   expToPop = (int)((float)expToPop * expMultiplier);
                }
                block.popExperience(level, blockPos, expToPop);


               applyNerfedFortune(drops, fortuneEnchantmentLevel);

               // Pop the resource with nerfed fortune
                for(ItemStack drop : drops) {
                    Block.popResource(level, blockPos, drop);
                }

               level.removeBlock(blockPos, false);
               event.setCanceled(true);

            }

            // Wisdom by itself without the other break event enchant
            else if (wisdomEnchantment != null) {
                float expMultiplier = wisdomEnchantment.getA().getExpMultiplier(wisdomEnchantment.getB());
                int newExpToDrop = (int)((float)event.getExpToDrop() * expMultiplier);
                event.setExpToDrop(newExpToDrop);
            }
        }

        private static void applyNerfedFortune(List<ItemStack> items, int eLevel) {
            for(ItemStack item : items) {
                int chanceToDouble = 0;
                switch(eLevel) {
                    case 1: {
                        chanceToDouble = 30; // Config later
                        break;
                    }
                    case 2: {
                        chanceToDouble = 50; // Config later
                        break;
                    }
                    case 3: {
                        chanceToDouble = 80; // Config later
                        break;
                    }
                    default:
                        break;
                }
                Random random = new Random();
                if (random.nextInt(100 ) < chanceToDouble) {
                    item.setCount(item.getCount() * 2);
                }
            }
        }

        private static boolean telepathicallyDestroyBlock(BlockEvent.BreakEvent event, BlockPos blockPos, ServerLevel level, ItemStack handItem, int fortuneEnchantmentLevel) {
            List<ItemStack> drops = Collections.emptyList();
            BlockState blockState = level.getBlockState(blockPos);

            // Cancelling event prevents the block from doing its drops
            event.setCanceled(true);

            // Checks if the tool can actually mine and cause drops


            // TODO: config this
            // checking if the state is an ore makes fortune with axe on melon and mushrooms not work but whatever, who really does that?
            if (fortuneEnchantmentLevel != 0 && blockState.is(Tags.Blocks.ORES)) {
                drops = Block.getDrops(blockState, level, blockPos, null);
                applyNerfedFortune(drops, fortuneEnchantmentLevel);
            } else {
                drops =  Block.getDrops(blockState, level, blockPos, null, event.getPlayer(), handItem);
            }



            // Get wisdom if applied
            Pair<WisdomEnchantment, Integer> wisdomEnchantment = UtilFunctions.getEnchantmentFromItem("rechantment:wisdom", handItem, WisdomEnchantment.class);

            // This check prevents a block from "breaking" twice while also working with vein miner breaks
            if (event.getPos() != blockPos) {
                 level.destroyBlock(blockPos, false);
            } else {
                 level.removeBlock(blockPos, false);
            }

            if (!handItem.isCorrectToolForDrops(blockState)) return true;

            // Get silk if applied
            boolean hasSilkTouch = EnchantmentHelper.hasSilkTouch(handItem);
            int expToDrop = blockState.getExpDrop(level, RandomSource.create(), blockPos, 0, hasSilkTouch ? 1 : 0);
            for (ItemStack drop : drops) {
                if (!event.getPlayer().addItem(drop)) {
                    event.getPlayer().drop(drop, false);
                }
            }

            if (expToDrop > 0) {
                Player player = event.getPlayer();
                if (wisdomEnchantment != null) {
                    float expMultiplier = wisdomEnchantment.getA().getExpMultiplier(wisdomEnchantment.getB());
                    expToDrop = (int) ((float) expToDrop * expMultiplier);
                }
                ExperienceOrb expOrb = new ExperienceOrb(level, player.getX(), player.getY(), player.getZ(), expToDrop);
                level.addFreshEntity(expOrb);
            }
            return true;
        }

        // Vein miner / timber specific
        private static void destroyBulkBlocks(BlockEvent.BreakEvent event, BlockPos[] blocksToDestroy, ServerLevel level, ItemStack handItem, @Nullable TelepathyEnchantment telepathyEnchantment, int fortuneEnchantmentLevel) {
            int destroyedSuccessfully = 0;
            Pair<WisdomEnchantment, Integer> wisdomEnchantment = UtilFunctions.getEnchantmentFromItem("rechantment:wisdom", handItem, WisdomEnchantment.class);

            for (BlockPos blockPos : blocksToDestroy) {
                BlockState blockState = level.getBlockState(blockPos);

                // Account for telepathy with each block to destroy
                if (telepathyEnchantment != null && telepathicallyDestroyBlock(event, blockPos, level, handItem, fortuneEnchantmentLevel)) {
                    ++destroyedSuccessfully;
                }

                // If no telepathy, just do basic block destroy manually.
                else {
                    ++destroyedSuccessfully;
                    List<ItemStack> itemsToDrop = null;

                    // TODO: config this !
                    if (fortuneEnchantmentLevel != 0 && blockState.is(Tags.Blocks.ORES)) {
                        itemsToDrop = Block.getDrops(blockState, level, blockPos, null);
                        applyNerfedFortune(itemsToDrop, fortuneEnchantmentLevel);
                    } else {
                        itemsToDrop = Block.getDrops(blockState, level, blockPos, null, event.getPlayer(), handItem);
                    }

                    if (event.getPos() != blockPos ) {
                        level.destroyBlock(blockPos, false);
                    } else {
                        level.removeBlock(blockPos, false);
                    }


                    // Manually pop the resource
                    if (handItem.isCorrectToolForDrops(blockState)) {
                        for (ItemStack item : itemsToDrop) {
                            Block.popResource(level, blockPos, item);
                        }


                        Block block = blockState.getBlock();
                        boolean hasSilkTouch = EnchantmentHelper.hasSilkTouch(handItem);
                        int expToDrop = blockState.getExpDrop(level, RandomSource.create(), blockPos, 0, hasSilkTouch ? 1 : 0);

                        if (wisdomEnchantment != null) {
                            float expMultiplier = wisdomEnchantment.getA().getExpMultiplier(wisdomEnchantment.getB());
                            expToDrop = (int) ((float) expToDrop * expMultiplier);
                        }

                        block.popExperience(level, blockPos, expToDrop);
                    }
                }
            }

            handItem.hurt(destroyedSuccessfully, level.random, (ServerPlayer)event.getPlayer());
        }

        @SubscribeEvent
        public static void onLivingDrops(LivingDropsEvent event) {

            if (event.getEntity() instanceof Player) return;

           if ((event.getSource().getEntity() instanceof Player player)) {
               ItemStack weapon = player.getMainHandItem();
               Pair<TelepathyEnchantment, Integer> telepathyEnchantment = UtilFunctions.getEnchantmentFromItem("rechantment:telepathy", weapon, TelepathyEnchantment.class );
               if (telepathyEnchantment == null) return;

               Collection<ItemEntity> items = event.getDrops();


               for (ItemEntity item : items) {
                   if (!player.addItem(item.getItem())) {
                       ItemStack itemToDrop = item.getItem();
                       player.drop(itemToDrop, false);
                   }
               }

               event.setCanceled(true);
            }
        }


        // TODO: make telpathy exp goto player
        @SubscribeEvent
        public static void onExpDropFromHostile(LivingExperienceDropEvent event) {
            MobCategory mobCategory = event.getEntity().getType().getCategory();
            if (mobCategory != MobCategory.MONSTER ||
                event.getAttackingPlayer() == null) return;

            ItemStack weapon = event.getAttackingPlayer().getMainHandItem();
            Pair<InquisitiveEnchantment, Integer> inquisitiveEnchantment = UtilFunctions.getEnchantmentFromItem("rechantment:inquisitive", weapon, InquisitiveEnchantment.class);
            if (inquisitiveEnchantment != null) {
                InquisitiveEnchantment inquisitiveEnchantInstance = inquisitiveEnchantment.getA();
                int enchantLevel = inquisitiveEnchantment.getB();
                int newExpToDrop = (int)((float) event.getDroppedExperience() * inquisitiveEnchantInstance.getExpMultiplier(enchantLevel));
                event.setDroppedExperience(newExpToDrop);
            }
        }

        @SubscribeEvent
        public static void onArmorEquip(LivingEquipmentChangeEvent event) {
            if (event.getSlot().getType() != EquipmentSlot.Type.ARMOR) return;
            if (!(event.getEntity() instanceof  Player player)) return;

            ItemStack newArmor = event.getTo();
            Pair<OverloadEnchantment, Integer> overloadEnchantment = UtilFunctions.getEnchantmentFromItem(
                    "rechantment:overload",
                    newArmor,
                    OverloadEnchantment.class
            );

            if (overloadEnchantment != null) {

                float newMaxHealth =  overloadEnchantment.getA().getMaxHealthTier(overloadEnchantment.getB());
                if (player.getMaxHealth() != newMaxHealth) {

                    player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(newMaxHealth);
                    if (player.getHealth() > player.getMaxHealth()) {
                        player.setHealth(player.getMaxHealth());
                    }
                    // Make sure this plays
                    player.level().playSound(null, player.getOnPos(), SoundEvents.TRIDENT_RETURN, SoundSource.PLAYERS, 1f, 1f);
                }

            } else {
                player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20f);

                if (player.getHealth() > player.getMaxHealth()) {
                    player.setHealth(player.getMaxHealth());
                    player.level().playSound(null, player.getOnPos(), SoundEvents.PLAYER_HURT, SoundSource.PLAYERS, 1f, 1f);
                }
            }
        }
    }
}
