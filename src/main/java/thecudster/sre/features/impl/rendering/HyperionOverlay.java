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
import thecudster.sre.util.RenderUtil;

import java.awt.*;

public class HyperionOverlay {
    @SubscribeEvent
    public void hyperionOverlay(RenderLivingEvent.Pre event) {
        if (event.entity instanceof EntityOtherPlayerMP) {
            return;
        }
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        Vec3 rot = player.getLookVec();
        if (Minecraft.getMinecraft().thePlayer.getHeldItem() != null) {
            if (Minecraft.getMinecraft().thePlayer.getHeldItem().getDisplayName().contains("Hyperion")) { // using display name because I don't have a hyperion to test with lmao
                boolean distance = event.entity.getDistance(rot.xCoord * 6 + player.posX, player.posY + rot.yCoord * 6, player.posZ + rot.zCoord * 6) <= 6;
                if (distance) {
                    if (event.entity instanceof EntityZombie || event.entity instanceof EntitySilverfish ||
                            event.entity instanceof EntitySkeleton || event.entity instanceof EntityEnderman ||
                            event.entity instanceof EntitySpider) {
                        RenderUtil.drawOutlinedBoundingBox(event.entity.getEntityBoundingBox(), new Color(27, 255, 11, 255), 3, 1f);
                        return;
                    }
                }
            }
        }
    }
}
