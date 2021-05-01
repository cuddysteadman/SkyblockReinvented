/*
 * SkyblockReinvented - Hypixel Skyblock Improvement Modification for Minecraft
 *  Copyright (C) 2021 theCudster
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published
 *  by the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package thecudster.sre.features.impl.dragons;

import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.gui.RenderUtil;
import thecudster.sre.util.sbutil.CurrentLoc;
import thecudster.sre.util.sbutil.Utils;

import java.awt.*;

public class DragonArrowHitbox {
    @SubscribeEvent
    public void onRender(RenderLivingEvent.Pre event) {
        if (!Utils.inSkyblock) { return; }
        if (CurrentLoc.currentLoc != "The End" && CurrentLoc.currentLoc != "Dragon's Nest") {
            return;
        }
        if (SkyblockReinvented.config.endDragHitbox) {
            if (event.entity instanceof EntityDragon || event.entity instanceof EntityIronGolem) {
                if (Minecraft.getMinecraft().thePlayer.canEntityBeSeen(event.entity)) {
                    RenderUtil.drawOutlinedBoundingBox(event.entity.getEntityBoundingBox(), new Color(255, 255, 255, 255), 3, 1f);
                    return;
                }
            }
        }
        if (SkyblockReinvented.config.specialHitbox) {
            if (event.entity instanceof EntityEnderman) {
                EntityEnderman enderman = (EntityEnderman) event.entity;
                if (enderman == null) { return; }
                if (enderman.getHeldItem() == null) { return; }
                if (enderman.getHeldItem().getItem() != null) {
                    if (Minecraft.getMinecraft().thePlayer.canEntityBeSeen(enderman) && enderman.getHeldItem().getItem().equals(BlockEndPortalFrame.blockRegistry)) {
                        RenderUtil.drawOutlinedBoundingBox(enderman.getEntityBoundingBox(), new Color(255, 255, 255, 255), 3, 1f);
                        return;
                    }
                }
            }
        }
        if (SkyblockReinvented.config.arrowHitboxes) {
            for (Entity e : Minecraft.getMinecraft().theWorld.loadedEntityList) {
                if (e instanceof EntityArrow) {
                    EntityArrow arrow = (EntityArrow) e;
                    if (arrow.shootingEntity == null || Minecraft.getMinecraft().thePlayer == null) { return; }
                    if (((EntityArrow) e).shootingEntity.equals(Minecraft.getMinecraft().thePlayer)) {
                        RenderUtil.drawOutlinedBoundingBox(e.getEntityBoundingBox(), new Color(255, 255, 255, 255), 3, 1f);
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
                    event.entity.setInvisible(true);
                    return;
                }
            }
        }
    }
}
