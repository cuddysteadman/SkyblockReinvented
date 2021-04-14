package thecudster.sre.features.impl.rendering;
import net.minecraft.block.BlockEndPortal;
import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.gui.RenderUtil;
import thecudster.sre.util.sbutil.CurrentLoc;

import java.awt.*;

public class DragonArrowHitbox {
    @SubscribeEvent
    public void onRender(RenderLivingEvent event) {
        if (CurrentLoc.currentLoc != "The End") {
            return;
        }
        if (SkyblockReinvented.config.endDragHitbox) {
            if (event.entity instanceof EntityDragon || event.entity instanceof EntityIronGolem) {
                if (Minecraft.getMinecraft().thePlayer.canEntityBeSeen(event.entity)) {
                    RenderUtil.drawFilledBoundingBox(event.entity.getEntityBoundingBox(), new Color(255, 255, 255, 255), 3);
                    return;
                }
            }
        }
        if (SkyblockReinvented.config.specialHitbox) {
            if (event.entity instanceof EntityEnderman) {
                EntityEnderman enderman = (EntityEnderman) event.entity;
                if (enderman.getHeldItem().getItem().equals(BlockEndPortalFrame.blockRegistry)) {
                    if (Minecraft.getMinecraft().thePlayer.canEntityBeSeen(enderman)) {
                        RenderUtil.drawFilledBoundingBox(enderman.getEntityBoundingBox(), new Color(255, 255, 255, 255), 3);
                        return;
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public void onJoin(EntityJoinWorldEvent event) {
        if (SkyblockReinvented.config.hideOtherArrows) {
            if (event.entity instanceof EntityArrow) {
                EntityArrow arrow = (EntityArrow) event.entity;
                if (arrow.shootingEntity instanceof EntityOtherPlayerMP) {
                    event.setCanceled(true);
                    return;
                }
            }
        }
    }
}
