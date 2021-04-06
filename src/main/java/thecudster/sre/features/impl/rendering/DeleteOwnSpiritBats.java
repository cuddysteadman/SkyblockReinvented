package thecudster.sre.features.impl.rendering;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.gui.DrawWaypoint;
import thecudster.sre.util.ItemUtil;
import thecudster.sre.util.Utils;

public class DeleteOwnSpiritBats {
    @SubscribeEvent(receiveCanceled = true)
    public void onRender(RenderLivingEvent.Pre event) {
        Utils.checkForDungeons();
        Utils.checkForSkyblock();
            if (event.entity instanceof EntityBat) {
                if (SkyblockReinvented.config.spiritBats) {
                    if (Minecraft.getMinecraft().thePlayer.getHeldItem() != null) {
                        if (ItemUtil.getSkyBlockItemID(Minecraft.getMinecraft().thePlayer.getHeldItem()) != null) {
                            if (ItemUtil.getSkyBlockItemID(Minecraft.getMinecraft().thePlayer.getHeldItem()).equals("BAT_WAND")) {
                                Minecraft.getMinecraft().theWorld.removeEntity(event.entity);

                            }
                            else if(Utils.inDungeons) {
                                DrawWaypoint.drawWaypoint(0.01f, event.entity.getPosition().down(),"Secret: Bat");
                            }
                        }
                        else if(Utils.inDungeons) {
                            DrawWaypoint.drawWaypoint(0.01f, event.entity.getPosition().down(),"Secret: Bat");
                        }
                    }
                    else if(Utils.inDungeons) {
                        DrawWaypoint.drawWaypoint(0.01f, event.entity.getPosition().down(),"Secret: Bat");
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
