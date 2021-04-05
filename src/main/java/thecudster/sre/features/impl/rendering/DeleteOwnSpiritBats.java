package thecudster.sre.features.impl.rendering;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.DrawWaypoint;
import thecudster.sre.util.ItemUtil;
import thecudster.sre.util.RenderUtil;
import thecudster.sre.util.Utils;
import thecudster.sre.util.gui.GuiUtils;

public class DeleteOwnSpiritBats {
    @SubscribeEvent(receiveCanceled = true)
    public void onRender(RenderLivingEvent.Pre event) {

            if (event.entity instanceof EntityBat) {
                Utils.checkForDungeons();
                Utils.checkForSkyblock();
                if (SkyblockReinvented.config.spiritBats) {
                    if (Minecraft.getMinecraft().thePlayer.getHeldItem() != null) {
                        if (ItemUtil.getSkyBlockItemID(Minecraft.getMinecraft().thePlayer.getHeldItem()) != null) {
                            if (ItemUtil.getSkyBlockItemID(Minecraft.getMinecraft().thePlayer.getHeldItem()).equals("BAT_WAND")) {
                                Minecraft.getMinecraft().theWorld.removeEntity(event.entity);

                            }
                        }
                    }
                }
                else if(Utils.inDungeons) {
                    DrawWaypoint.drawWaypoint(0.01f, event.entity.getPosition().down(),"Secret: Bat");
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
        if (SkyblockReinvented.config.raffleWaypoint) {
            if (event.entity instanceof EntityArmorStand) {
                if (event.entity.getCustomNameTag() != null) {
                    if (event.entity.getCustomNameTag().contains("BRING YOUR TICKETS")) {
                        DrawWaypoint.drawWaypoint(0.01f, event.entity.getPosition().down(), "Raffle Box");
                    }
                }
            }
        }
    }
}
