package net.tagtart.rechantment.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.tagtart.rechantment.util.UtilFunctions;

import javax.annotation.Nullable;
import java.util.List;

public class ChanceGem extends Item {
    public ChanceGem(Properties properties) {
        super(properties);
    }

    @Override
    public Component getName(ItemStack pStack) {
        return Component.translatable("item.rechantment.chance_gem").withStyle(ChatFormatting.AQUA);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        Component itemDescription = Component.translatable("item.rechantment.chance_gem.description");

        String itemDescriptionString = itemDescription.getString();
        int maxWidthTooltip = 165;
        pTooltipComponents.add(Component.literal(" "));
        // pTooltipComponents.add(itemDescription);
        // Prevents the description text from making the tooltip go across the entire screen like a chump
         List<String> splitText = UtilFunctions.wrapText(itemDescriptionString, maxWidthTooltip);
        for (String s : splitText) {
            pTooltipComponents.add(Component.literal(s.trim()));
        }

        pTooltipComponents.add(Component.literal(" "));

        pTooltipComponents.add(Component.literal("→ ᴅʀᴀɢ ɴ ᴅʀᴏᴘ ᴏɴᴛᴏ ʏᴏᴜʀ").withStyle(ChatFormatting.GRAY));
        pTooltipComponents.add(Component.literal("ʙᴏᴏᴋ ᴛᴏ ᴀᴘᴘʟʏ ᴛʜɪꜱ ɢᴇᴍ").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return true;
    }
}
