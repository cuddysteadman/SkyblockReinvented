package thecudster.sre.features.impl.rendering;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.ItemUtil;
import thecudster.sre.util.gui.GuiUtils;

public class DeleteOwnSpiritBats {
    @SubscribeEvent(receiveCanceled = true)
    public void onRender(RenderLivingEvent.Pre event) {
        if (SkyblockReinvented.config.spiritBats) {
            if (event.entity instanceof EntityBat) {
                if (Minecraft.getMinecraft().thePlayer.getHeldItem() != null) {
                    if (ItemUtil.getSkyBlockItemID(Minecraft.getMinecraft().thePlayer.getHeldItem()) != null) {
                        if (ItemUtil.getSkyBlockItemID(Minecraft.getMinecraft().thePlayer.getHeldItem()).equals("BAT_WAND")) {
                            Minecraft.getMinecraft().theWorld.removeEntity(event.entity);
                            GuiUtils.DrawMeter(1800, 1800, 500, 500, 550, 450, 0.05);
                        }
                    }
                }
            }
        }
        if (SkyblockReinvented.config.svenPups) {
            if (event.entity instanceof EntityWolf) {
                EntityWolf wolf = ((EntityWolf) event.entity);
                if (wolf.isChild()) {
                    event.setCanceled(true);
                }

            }
        }
    }
}
