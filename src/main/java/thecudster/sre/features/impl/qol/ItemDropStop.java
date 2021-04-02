package thecudster.sre.features.impl.qol;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.util.ItemUtil;

public class ItemDropStop {
    /*public boolean hasDoneTwice = false;
    @SubscribeEvent(receiveCanceled = true)
    public void onDrop(ItemTossEvent event) {
        if (event.entityItem.getEntityItem() == null) { return; }
        if (ItemUtil.getSkyBlockItemID(event.entityItem.getEntityItem()).contains("SACK")) {
            if (hasDoneTwice) {
                hasDoneTwice = false;
            }
            else {
                hasDoneTwice = true;
                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("Drop this item again to confirm!"));
                event.setCanceled(true);
            }
        }
    }*/
}
