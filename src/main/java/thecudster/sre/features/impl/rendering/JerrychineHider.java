package thecudster.sre.features.impl.rendering;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import scala.collection.parallel.ParIterableLike;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.sbutil.ItemUtil;
import thecudster.sre.util.sbutil.Utils;

public class JerrychineHider {
    @SubscribeEvent
    public void onRender(RenderLivingEvent.Pre<EntityArmorStand> event) {
        if (!SkyblockReinvented.config.hideJerry) { return; }
        try {
            if (event.entity instanceof EntityArmorStand) {
                EntityArmorStand entity = ((EntityArmorStand) event.entity);
                ItemStack headSlot = entity.getCurrentArmor(3);
                if (entity.getCurrentArmor(0) != null || entity.getCurrentArmor(1) != null || entity.getCurrentArmor(2) != null) { return; }
                if (headSlot != null && headSlot.getItem() == Items.skull && headSlot.hasTagCompound()) {
                    if (ItemUtil.getSkyBlockItemID(Minecraft.getMinecraft().thePlayer.getHeldItem()).equals("JERRY_STAFF")) {
                        event.setCanceled(true);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

