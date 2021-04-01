package thecudster.sre.features.impl.rendering;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;

public class RemoveVillagers {
    @SubscribeEvent(receiveCanceled = true, priority = EventPriority.HIGHEST)
    public void onCheckRender(RenderLivingEvent.Pre<EntityVillager> event) {

        if (!(event.entity instanceof EntityVillager)) { return; }
        EntityVillager villagerEntity = (EntityVillager) event.entity;

        if (villagerEntity instanceof EntityVillager && SkyblockReinvented.config.renderVillagers) {
            event.setCanceled(true);
        }


    }
}
