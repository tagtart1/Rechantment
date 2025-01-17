package net.tagtart.rechantment.event;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tagtart.rechantment.Rechantment;
import net.tagtart.rechantment.util.UtilFunctions;

import java.util.HashSet;

// There are a lot of weird edge cases when items break in different scenarios.
@Mod.EventBusSubscriber(modid = Rechantment.MOD_ID)
public class ScheduledRebirthTasks {

    private static class EnqueuedRebirthEvent{

        public boolean isOffhand;
        public int ticksRemaining;
        public int inventorySlot;
        public ServerPlayer player;
        public ItemStack rebornItem;

        public EnqueuedRebirthEvent(ServerPlayer player, ItemStack rebornItem, int inventorySlot, boolean isOffhand) {
            this.ticksRemaining = 1;
            this.inventorySlot = inventorySlot;
            this.player = player;
            this.rebornItem = rebornItem;
            this.isOffhand = isOffhand;
        }

        public void handleRebirthItem() {
            UtilFunctions.triggerRebirthClientEffects(player, (ServerLevel) player.level(), this.rebornItem.getItem().getDefaultInstance());

            if (this.isOffhand) {
                player.getInventory().offhand.set(0, this.rebornItem);
            }
            else if (this.rebornItem.getItem() instanceof ArmorItem armorItem) {
                EquipmentSlot armorSlot = armorItem.getEquipmentSlot();
                player.setItemSlot(armorSlot, this.rebornItem);
            }
            else if (this.inventorySlot != -1) {
                player.getInventory().setItem(this.inventorySlot, this.rebornItem);
            }
            else {
                player.drop(this.rebornItem, false); // Drop the item if the slot is occupied
            }
        }
    }

    private static final HashSet<EnqueuedRebirthEvent> enqueuedRebirthEvents = new HashSet<>();

    public static void EnqueueItemForRebirth(ServerPlayer player, ItemStack rebornItem, int inventorySlot, boolean isOffhand) {
        enqueuedRebirthEvents.add(new EnqueuedRebirthEvent(player, rebornItem, inventorySlot, isOffhand));
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        for (EnqueuedRebirthEvent rebirthEvent : enqueuedRebirthEvents) {
            rebirthEvent.ticksRemaining--;
            if (rebirthEvent.ticksRemaining <= 0) {
                rebirthEvent.handleRebirthItem();
                enqueuedRebirthEvents.remove(rebirthEvent);
            }
        }
    }
}
