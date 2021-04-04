package thecudster.sre.features.impl.rendering;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.resources.model.ModelRotation;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.entity.monster.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.ItemUtil;
import thecudster.sre.util.RenderUtil;

import java.awt.*;

public class HyperionOverlay {
    public String name = "";
    @SubscribeEvent
    public void hyperionOverlay(RenderLivingEvent.Pre event) {
        if (event.entity instanceof EntityOtherPlayerMP) {
            return;
        }
        if (SkyblockReinvented.config.hyperionOverlay) {
            EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
            Vec3 rot = player.getLookVec();
            if (Minecraft.getMinecraft().thePlayer.getHeldItem() != null) {
                if (ItemUtil.getSkyBlockItemID(Minecraft.getMinecraft().thePlayer.getHeldItem()) != null)
                    name = ItemUtil.getSkyBlockItemID(Minecraft.getMinecraft().thePlayer.getHeldItem());
                if (name.equals("HYPERION") || name.equals("VALKYRIE") || name.equals("SCYLLA") || name.equals("ASTREA")) { // guessing on scylla because no metadata on wiki but who uses scylla lol
                    boolean distance = event.entity.getDistance(rot.xCoord * 6 + player.posX, player.posY + rot.yCoord * 6, player.posZ + rot.zCoord * 6) <= 6;
                    if (distance) {
                        if (event.entity instanceof EntityZombie || event.entity instanceof EntitySilverfish ||
                                event.entity instanceof EntitySkeleton || event.entity instanceof EntityEnderman ||
                                event.entity instanceof EntitySpider) {
                            RenderUtil.drawFilledBoundingBox(event.entity.getEntityBoundingBox(), new Color(27, 255, 11, 255), 3);
                            return;
                        }
                    }
                }
            }
        }
    }
}
