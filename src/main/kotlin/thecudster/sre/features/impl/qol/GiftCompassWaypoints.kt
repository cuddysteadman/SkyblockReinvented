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
package thecudster.sre.features.impl.qol

import thecudster.sre.util.sbutil.ItemUtil.getSkyBlockItemID
import net.minecraft.util.BlockPos
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import thecudster.sre.events.PacketEvent.ReceiveEvent
import net.minecraft.network.play.server.S05PacketSpawnPosition
import net.minecraftforge.client.event.RenderWorldLastEvent
import thecudster.sre.util.sbutil.CurrentLoc
import net.minecraft.client.Minecraft
import thecudster.sre.SkyblockReinvented
import thecudster.sre.util.sbutil.ItemUtil
import thecudster.sre.features.impl.qol.GiftCompassWaypoints
import thecudster.sre.core.gui.RenderUtils
import thecudster.sre.util.Utils
import java.awt.Color

class GiftCompassWaypoints {
    var pos: BlockPos? = null
    @SubscribeEvent(receiveCanceled = true)
    fun onReceivePacket(event: ReceiveEvent) {
        if (!Utils.inSkyblock) return
        if (event.packet is S05PacketSpawnPosition) {
            val spawnPosition = event.packet as S05PacketSpawnPosition?
            pos = spawnPosition!!.spawnPos
        }
    }

    @SubscribeEvent
    fun onWorldRender(event: RenderWorldLastEvent) {
        if (!Utils.inSkyblock) return
        if (!(CurrentLoc.currentLoc == "Jerry's Workshop" || CurrentLoc.currentLoc == "Jerry Pond")) return
        if (Minecraft.getMinecraft().thePlayer.heldItem != null && getSkyBlockItemID(Minecraft.getMinecraft().thePlayer.heldItem) != null) {
            if (getSkyBlockItemID(Minecraft.getMinecraft().thePlayer.heldItem) == "GIFT_COMPASS") {
                if (pos != null) {
                    if (SkyblockReinvented.config.giftCompassWaypoints && !found) {
                        RenderUtils.drawWaypoint(event.partialTicks, pos as BlockPos, "Gift", Color(2, 250, 39), true, 1f, 2.0)
                    }
                }
            }
        }
    }

    companion object {
        var found = false
    }
}