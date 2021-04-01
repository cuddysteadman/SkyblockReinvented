package thecudster.sre.features.impl.rendering;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;

public class RemoveDumbNametags {
    @SubscribeEvent
    public void onRender(RenderLivingEvent.Pre event) {
        if (event.entity.getCustomNameTag() != null) {
            if (SkyblockReinvented.config.renderVillagers) {
                if (event.entity.getCustomNameTag().contains("NEW UPDATE")) {
                    Minecraft.getMinecraft().theWorld.removeEntity(event.entity);
                }
                if (event.entity.getCustomNameTag().contains("Jerry")) {
                    Minecraft.getMinecraft().theWorld.removeEntity(event.entity);
                }
                if (event.entity.getCustomNameTag().contains("CLICK")) {
                    Minecraft.getMinecraft().theWorld.removeEntity(event.entity);
                }
            }
        }
    }
}
