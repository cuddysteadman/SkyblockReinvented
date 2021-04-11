package thecudster.sre.features.impl.rendering;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.sbutil.ItemUtil;

public class DeleteOwnSpiritBats {
    @SubscribeEvent(receiveCanceled = true)
    public void onRender(RenderLivingEvent.Pre event) {
            if (event.entity instanceof EntityBat) {
                if (SkyblockReinvented.config.spiritBats) {
                    if (Minecraft.getMinecraft().thePlayer.getHeldItem() != null) {
                        if (ItemUtil.getSkyBlockItemID(Minecraft.getMinecraft().thePlayer.getHeldItem()) != null) {
                            if (ItemUtil.getSkyBlockItemID(Minecraft.getMinecraft().thePlayer.getHeldItem()).equals("BAT_WAND")) {
                                Minecraft.getMinecraft().theWorld.removeEntity(event.entity);
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
