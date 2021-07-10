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
package thecudster.sre.features.impl.dragons

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.client.event.RenderLivingEvent
import thecudster.sre.util.sbutil.CurrentLoc
import net.minecraft.entity.boss.EntityDragon
import net.minecraft.entity.monster.EntityIronGolem
import net.minecraft.client.Minecraft
import thecudster.sre.core.gui.RenderUtils
import net.minecraft.entity.monster.EntityEnderman
import net.minecraft.block.BlockEndPortalFrame
import net.minecraft.entity.projectile.EntityArrow
import net.minecraftforge.event.entity.EntityJoinWorldEvent
import net.minecraft.client.entity.EntityOtherPlayerMP
import thecudster.sre.SkyblockReinvented
import thecudster.sre.util.Utils
import java.awt.Color

class DragonArrowHitbox {
    @SubscribeEvent
    fun onRender(event: RenderLivingEvent.Pre<*>) {
        if (!Utils.inSkyblock) {
            return
        }
        if (CurrentLoc.currentLoc !== "The End" && CurrentLoc.currentLoc !== "Dragon's Nest") {
            return
        }
        if (SkyblockReinvented.config.endDragHitbox) {
            if (event.entity is EntityDragon || event.entity is EntityIronGolem) {
                if (Minecraft.getMinecraft().thePlayer.canEntityBeSeen(event.entity)) {
                    RenderUtils.drawOutlinedBoundingBox(
                        event.entity.entityBoundingBox,
                        Color(255, 255, 255, 255),
                        3f,
                        1f
                    )
                    return
                }
            }
        }
        if (SkyblockReinvented.config.specialHitbox) {
            if (event.entity is EntityEnderman) {
                val enderman = event.entity as EntityEnderman ?: return
                if (enderman.heldItem == null) {
                    return
                }
                if (enderman.heldItem.item != null) {
                    if (Minecraft.getMinecraft().thePlayer.canEntityBeSeen(enderman) && enderman.heldItem.item == BlockEndPortalFrame.blockRegistry) {
                        RenderUtils.drawOutlinedBoundingBox(
                            enderman.entityBoundingBox,
                            Color(255, 255, 255, 255),
                            3f,
                            1f
                        )
                        return
                    }
                }
            }
        }
        if (SkyblockReinvented.config.arrowHitboxes) {
            for (e in Minecraft.getMinecraft().theWorld.loadedEntityList) {
                if (e is EntityArrow) {
                    if (e.shootingEntity == null || Minecraft.getMinecraft().thePlayer == null) {
                        return
                    }
                    if (e.shootingEntity == Minecraft.getMinecraft().thePlayer) {
                        RenderUtils.drawOutlinedBoundingBox(e.getEntityBoundingBox(), Color(255, 255, 255, 255), 3f, 1f)
                    }
                }
            }
        }
    }

    @SubscribeEvent
    fun onJoin(event: EntityJoinWorldEvent) {
        if (SkyblockReinvented.config.hideOtherArrows) {
            if (event.entity is EntityArrow) {
                val arrow = event.entity as EntityArrow
                if (arrow.shootingEntity is EntityOtherPlayerMP) {
                    event.isCanceled = true
                    return
                }
            }
        }
    }
}