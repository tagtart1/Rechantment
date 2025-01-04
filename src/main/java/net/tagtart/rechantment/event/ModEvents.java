package net.tagtart.rechantment.event;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.TotemParticle;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
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
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.tagtart.rechantment.Rechantment;
import net.tagtart.rechantment.block.entity.RechantmentTableBlockEntity;
import net.tagtart.rechantment.config.RechantmentCommonConfigs;
import net.tagtart.rechantment.enchantment.*;
import net.tagtart.rechantment.networking.ModPackets;
import net.tagtart.rechantment.sound.ModSounds;
import net.tagtart.rechantment.util.BookRarityProperties;
import net.tagtart.rechantment.util.UtilFunctions;
import oshi.util.Util;
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

        // class to totem particle incase  need to copy
        //new TotemParticle()

        @SubscribeEvent
        public static void onShieldBlock(ShieldBlockEvent event) {
            LivingEntity player = event.getEntity();
            DamageSource source = event.getDamageSource();
            Entity attacker = source.getEntity();
            ItemStack shield = player.getUseItem();
            //TODO: remove this
            //Minecraft.getInstance().gameRenderer.displayItemActivation(shield);
            if (player.level() instanceof ServerLevel serverLevel) {


                ParticleEmitter.emitParticlesOverTime((Player)player, serverLevel, 90, 50);
            }
           // player.level().playSound(null, player.blockPosition(), ModSounds.REBIRTH_ITEM.get(), SoundSource.PLAYERS, 0.7F, 1.0F);
            if(!(shield.getItem() instanceof ShieldItem)) return;

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
                List<String> sortedEnchantStrings = new ArrayList<>();
                // TODO: make this use the List<EntrySet<Enchantment, Integer>> and then sort it, then set the tooltip that way
                // This current solution is poop
                for (Map.Entry<Enchantment, Integer> entry : EnchantmentHelper.getEnchantments(stack).entrySet()) {
                    Enchantment enchantment = entry.getKey();
                    int level = entry.getValue();

                  // Change to any color you prefer


                    String humanReadable = enchantment.getFullname(level).getString();

                    sortedEnchantStrings.add(ForgeRegistries.ENCHANTMENTS.getKey(enchantment).toString() + "~" + humanReadable);
                }

                sortedEnchantStrings.sort((component1, component2) -> {
                    String enchantmentRaw1 = component1.split("~")[0];
                    String enchantmentRaw2 = component2.split("~")[0];

                    BookRarityProperties rarity1 = UtilFunctions.getPropertiesFromEnchantment(enchantmentRaw1);
                    BookRarityProperties rarity2 = UtilFunctions.getPropertiesFromEnchantment(enchantmentRaw2);

                    float rarityValue1 = (rarity1 == null) ? 99 : rarity1.rarity;
                    float rarityValue2 = (rarity2 == null) ? 99 : rarity2.rarity;

                    return Float.compare(rarityValue2, rarityValue1);
                });

                for(int i = 1; i <= sortedEnchantStrings.size(); i++) {
                    String[] splitEnchantment = sortedEnchantStrings.get(i - 1).split("~");
                    String enchantmentRaw = splitEnchantment[0];

                    BookRarityProperties rarityProperties = UtilFunctions.getPropertiesFromEnchantment(enchantmentRaw);

                    Style style = Style.EMPTY;
                    if (Objects.equals(enchantmentRaw, "minecraft:vanishing_curse") || Objects.equals(enchantmentRaw, "minecraft:binding_curse")) {
                        style = Style.EMPTY.withColor(ChatFormatting.RED);
                    }
                    else if (rarityProperties != null) {
                        style = Style.EMPTY.withColor(rarityProperties.color);
                    }
                    Component modifiedText = Component.literal(splitEnchantment[1]).withStyle(style);

                    tooltip.set(i, modifiedText);
                }
            }

            if (stack.getItem() instanceof EnchantedBookItem) {
                tooltip.add(Component.literal("Vanilla books have been disabled.").withStyle(ChatFormatting.RED));
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

                Pair<ThunderStrikeEnchantment, Integer> thunderStrikeEnchantment = UtilFunctions
                        .getEnchantmentFromItem("rechantment:thunder_strike",
                                weapon,
                                ThunderStrikeEnchantment.class);

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

                if (thunderStrikeEnchantment != null) {
                    int enchantmentOnWeaponLevel = thunderStrikeEnchantment.getB();
                    bonusDamage += thunderStrikeEnchantment.getA().rollLightningStrike(
                            player,
                            event.getEntity(),
                            enchantmentOnWeaponLevel);
                }

                // Apply the damage effects
                event.setAmount(event.getAmount() + bonusDamage);
            }
        }



        // Telepathy, Vein Miner, Timber, Wisdom Enchantments - Blocks
        @SubscribeEvent
        public static void onBlockBreak(BlockEvent.BreakEvent event) {

            if (event.getPlayer().level().isClientSide()) return;

            // For enchantment table replacement inventory, this is necessary since it isn't tied to a custom block, just an entity.
            BlockEntity blockEntity = event.getPlayer().level().getBlockEntity(event.getPos());
            if (blockEntity instanceof RechantmentTableBlockEntity) {
                ((RechantmentTableBlockEntity) blockEntity).dropInventory();
            }

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
            }

            else if (timberEnchantment != null && event.getState().is(BlockTags.LOGS)) {
                BlockPos[] woodToDestroy = UtilFunctions.BFSLevelForBlocks(level, BlockTags.LOGS, event.getPos(), 10, true);
                destroyBulkBlocks(event, woodToDestroy, level, handItem, (telepathyEnchantment != null) ? telepathyEnchantment.getA() : null, fortuneEnchantmentLevel);
            }

            // Telepathy check. Only happens when destroying a single block normally here
            else if (telepathyEnchantment != null) {

                telepathicallyDestroyBlock(event, event.getPos(), level, handItem, fortuneEnchantmentLevel);

                if (event.getState().getDestroySpeed(level, event.getPos()) != 0)
                    handItem.hurt(1, level.random, (ServerPlayer)event.getPlayer());
            }

            // Fortune by itself with vanilla enchants without other breakevent enchant
            else if (fortuneEnchantmentLevel != 0 && RechantmentCommonConfigs.FORTUNE_NERF_ENABLED.get()){
                // Block info
                BlockState blockState = event.getState();
                Block block = blockState.getBlock();
                BlockPos blockPos = event.getPos();

                if (!blockState.is(Tags.Blocks.ORES) || !handItem.isCorrectToolForDrops(blockState)) return;

                level.removeBlock(blockPos, false);
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
               Block.popResource(level, event.getPos(), ItemStack.EMPTY);


               // Pop the resource with nerfed fortune
                for(ItemStack drop : drops) {
                    Block.popResource(level, blockPos, drop);
                }

                handItem.hurt(1, level.random, (ServerPlayer)event.getPlayer());

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
                double chanceToDouble = 0.0;
                switch(eLevel) {
                    case 1: {
                        chanceToDouble = RechantmentCommonConfigs.FORTUNE_1_CHANCE.get(); // Config later
                        break;
                    }
                    case 2: {
                        chanceToDouble = RechantmentCommonConfigs.FORTUNE_2_CHANCE.get(); // Config later
                        break;
                    }
                    case 3: {
                        chanceToDouble = RechantmentCommonConfigs.FORTUNE_3_CHANCE.get(); // Config later
                        break;
                    }
                    default:
                        break;
                }
                Random random = new Random();
                if (random.nextDouble() < chanceToDouble) {
                    item.setCount(item.getCount() * 2);
                }
            }
        }

        private static void telepathicallyDestroyBlock(BlockEvent.BreakEvent event, BlockPos blockPos, ServerLevel level, ItemStack handItem, int fortuneEnchantmentLevel) {
            List<ItemStack> drops = Collections.emptyList();
            BlockState blockState = level.getBlockState(blockPos);

            // Prevents block from dropping a resource at this pos
            Block.popResource(level, event.getPos(), ItemStack.EMPTY);

            // checking if the state is an ore makes fortune with axe on melon and mushrooms not work but whatever, who really does that?
            if (RechantmentCommonConfigs.FORTUNE_NERF_ENABLED.get()
                    && fortuneEnchantmentLevel != 0
                    && blockState.is(Tags.Blocks.ORES)) {
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

            // Prevents an axe from mining diamonds for example
            if (!blockState.canHarvestBlock(level, blockPos, event.getPlayer())) return;


            // Get silk if applied
            boolean hasSilkTouch = EnchantmentHelper.hasSilkTouch(handItem);
            int expToDrop = blockState.getExpDrop(level, RandomSource.create(), blockPos, 0, hasSilkTouch ? 1 : 0);
            for (ItemStack drop : drops) {
                if (!event.getPlayer().addItem(drop)) {
                    event.getPlayer().drop(drop, false);
                }
                // Make pickup noise for telepathy
                else {
                    Random random = new Random();
                    float randomPitch = .9f + random.nextFloat() * (1.6f - .9f);
                    level.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, .25f, randomPitch);
                }
            }

            // Teleports the exp orb to player
            if (expToDrop > 0) {
                Player player = event.getPlayer();

                // Multiply if we have wisdom on the tool
                if (wisdomEnchantment != null) {
                    float expMultiplier = wisdomEnchantment.getA().getExpMultiplier(wisdomEnchantment.getB());
                    expToDrop = (int) ((float) expToDrop * expMultiplier);
                }
                ExperienceOrb expOrb = new ExperienceOrb(level, player.getX(), player.getY(), player.getZ(), expToDrop);
                level.addFreshEntity(expOrb);
            }
        }

        // Vein miner / timber specific
        private static void destroyBulkBlocks(BlockEvent.BreakEvent event, BlockPos[] blocksToDestroy, ServerLevel level, ItemStack handItem, @Nullable TelepathyEnchantment telepathyEnchantment, int fortuneEnchantmentLevel) {
            int destroyedSuccessfully = 0;
            Pair<WisdomEnchantment, Integer> wisdomEnchantment = UtilFunctions.getEnchantmentFromItem("rechantment:wisdom", handItem, WisdomEnchantment.class);

            for (BlockPos blockPos : blocksToDestroy) {
                BlockState blockState = level.getBlockState(blockPos);

                // Account for telepathy with each block to destroy
                if (telepathyEnchantment != null ) {
                    telepathicallyDestroyBlock(event, blockPos, level, handItem, fortuneEnchantmentLevel);
                    ++destroyedSuccessfully;
                }

                // If no telepathy, just do basic block destroy manually.
                else {
                    ++destroyedSuccessfully;
                    List<ItemStack> itemsToDrop = null;


                    // Checks for fortune nerf requirements
                    if (RechantmentCommonConfigs.FORTUNE_NERF_ENABLED.get()
                            && fortuneEnchantmentLevel != 0
                            && blockState.is(Tags.Blocks.ORES)) {
                        itemsToDrop = Block.getDrops(blockState, level, blockPos, null);
                        applyNerfedFortune(itemsToDrop, fortuneEnchantmentLevel);

                        Block.popResource(level, event.getPos(), ItemStack.EMPTY);
                    }

                    // Normal drops based off the tool in hand
                    else {
                        itemsToDrop = Block.getDrops(blockState, level, blockPos, null, event.getPlayer(), handItem);
                    }


                    // Create correct particle and noise block breaking effects
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

            // Prevents teleporting player drops
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


        @SubscribeEvent
        public static void onExpDropFromHostile(LivingExperienceDropEvent event) {
            MobCategory mobCategory = event.getEntity().getType().getCategory();
            if (event.getAttackingPlayer() == null) return;
            ItemStack weapon = event.getAttackingPlayer().getMainHandItem();
            Pair<InquisitiveEnchantment, Integer> inquisitiveEnchantment = UtilFunctions.getEnchantmentFromItem("rechantment:inquisitive", weapon, InquisitiveEnchantment.class);
            Pair<TelepathyEnchantment, Integer> telepathyEnchantment = UtilFunctions.getEnchantmentFromItem("rechantment:telepathy", weapon, TelepathyEnchantment.class);


            int expToDrop = event.getDroppedExperience();

            if (inquisitiveEnchantment != null && mobCategory == MobCategory.MONSTER) {
                InquisitiveEnchantment inquisitiveEnchantInstance = inquisitiveEnchantment.getA();
                int enchantLevel = inquisitiveEnchantment.getB();
                expToDrop = (int)((float) expToDrop * inquisitiveEnchantInstance.getExpMultiplier(enchantLevel));
            }

            if (telepathyEnchantment != null) {
                Player player = event.getAttackingPlayer();
                ExperienceOrb expOrb = new ExperienceOrb(event.getAttackingPlayer().level(), player.getX(), player.getY(), player.getZ(), expToDrop);
                event.getAttackingPlayer().level().addFreshEntity(expOrb);
                event.setDroppedExperience(0);
            } else {
                event.setDroppedExperience(expToDrop);
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
                    player.level().playSound(null, player.getOnPos(), SoundEvents.TRIDENT_RETURN, SoundSource.PLAYERS, 1.15f, 1f);
                }

            } else {
                player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20f);

                if (player.getHealth() > player.getMaxHealth()) {
                    player.setHealth(player.getMaxHealth());
                    player.level().playSound(null, player.getEyePosition().x, player.getEyePosition().y, player.getEyePosition().z, SoundEvents.PLAYER_HURT, SoundSource.PLAYERS, 1f, 1f);
                }
            }
        }

        @SubscribeEvent
        public static void onAnvilUpdate(AnvilUpdateEvent event) {
            ItemStack left = event.getLeft();
            ItemStack right = event.getRight();

            // Turn off vanilla enchanted book from applying
            if (left.getItem() instanceof EnchantedBookItem || right.getItem() instanceof EnchantedBookItem) {
                event.setCanceled(true);
            }
        }
    }
}
