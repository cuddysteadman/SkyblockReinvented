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
package thecudster.sre.features.impl.rendering

import net.minecraft.client.Minecraft
import net.minecraft.client.entity.EntityOtherPlayerMP
import net.minecraft.entity.monster.*
import net.minecraftforge.client.event.RenderLivingEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import thecudster.sre.SkyblockReinvented
import thecudster.sre.util.RenderUtils
import thecudster.sre.util.Utils
import thecudster.sre.util.sbutil.ItemUtil.getSkyBlockItemID
import java.awt.Color

class HyperionOverlay {
    var name: String? = ""
    @SubscribeEvent
    fun hyperionOverlay(event: RenderLivingEvent.Pre<*>) {
        if (event.entity is EntityOtherPlayerMP) {
            return
        }
        if (!Utils.inSkyblock) return
        if (SkyblockReinvented.config.hyperionOverlay) {
            val player = Minecraft.getMinecraft().thePlayer
            val rot = player.lookVec
            if (Minecraft.getMinecraft().thePlayer.heldItem != null) {
                if (getSkyBlockItemID(Minecraft.getMinecraft().thePlayer.heldItem) != null) name =
                    getSkyBlockItemID(Minecraft.getMinecraft().thePlayer.heldItem)
                if (name == "HYPERION" || name == "VALKYRIE" || name == "SCYLLA" || name == "ASTREA") { // guessing on scylla because no metadata on wiki but who uses scylla lol
                    val distance = event.entity.getDistance(
                        rot.xCoord * 6 + player.posX,
                        player.posY + rot.yCoord * 6,
                        player.posZ + rot.zCoord * 6
                    ) <= 6
                    if (distance) {
                        if (event.entity is EntityZombie || event.entity is EntitySilverfish ||
                            event.entity is EntitySkeleton || event.entity is EntityEnderman ||
                            event.entity is EntitySpider
                        ) {
                            if (player.canEntityBeSeen(event.entity)) {
                                RenderUtils.drawFilledBoundingBox(
                                    event.entity.entityBoundingBox,
                                    Color(27, 255, 11, 255),
                                    3f
                                )
                                return
                            }
                        }
                    }
                }
            }
        }
    }
}