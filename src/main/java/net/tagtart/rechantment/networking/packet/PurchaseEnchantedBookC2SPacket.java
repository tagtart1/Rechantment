package net.tagtart.rechantment.networking.packet;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.tagtart.rechantment.util.AllBookProperties;
import net.tagtart.rechantment.util.BookRequirementProperties;
import net.tagtart.rechantment.util.UtilFunctions;

import java.util.function.Supplier;

public class PurchaseEnchantedBookC2SPacket extends AbstractPacket {

    private final NetworkDirection networkDirection = NetworkDirection.PLAY_TO_SERVER;

    private int bookPropertiesIndex;
    private BlockPos enchantTablePos;

    public PurchaseEnchantedBookC2SPacket(int pPropertiesIndex, BlockPos pEnchantTablePos) {
        super();
        bookPropertiesIndex = pPropertiesIndex;
        enchantTablePos = pEnchantTablePos;
    }

    public PurchaseEnchantedBookC2SPacket(FriendlyByteBuf buf) {
        super(buf);
        bookPropertiesIndex = buf.readInt();
        enchantTablePos = buf.readBlockPos();
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(bookPropertiesIndex);
        buf.writeBlockPos(enchantTablePos);
    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();

        context.enqueueWork(() -> {
            // We are now running this ONLY on server.

            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel();
            Inventory playerInventory = player.getInventory();
            BookRequirementProperties bookProperties = AllBookProperties.getAllProperties()[bookPropertiesIndex];

            BlockState[] bookshelves = UtilFunctions.scanAroundBlockForBookshelves(level, enchantTablePos);
            BlockState[] floorBlocks = UtilFunctions.scanAroundBlockForValidFloors(bookProperties.floorBlock, level, enchantTablePos);

            // Remaining work:
            // - Handle all logic left WITHOUT creating a book item to reward; just work off of client messages!
            //     - Once the above works test without config just yet. Hardcode one enchantment per-tier and test weights and proper selection.
            // - Create S2C packet that returns result enchant attempt. This is only for the sake of client messages/closing player screen after
            // failure in this case, but will make good practice for doing a similar thing and creating a screen of the enchantment pools!
            // - Logic in question:
            //      - Remove EXP from player after successful enchantment.
            //      - Destroy bookshelves/floor blocks according to chances (level.destroy(blockPos))
            //      -
            if (playerInventory.getFreeSlot() != -1) {
                //playerInventory.add() // Add randomly created book here.
            }

        });
        return true;
    }

    @Override
    public NetworkDirection getNetworkDirection() {
        return networkDirection;
    }
}
