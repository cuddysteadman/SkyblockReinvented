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

import net.minecraft.util.BlockPos
import net.minecraftforge.client.event.RenderLivingEvent
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import thecudster.sre.SkyblockReinvented
import thecudster.sre.util.Utils
import thecudster.sre.util.Utils.inLoc
import thecudster.sre.util.sbutil.ArrStorage

class RemoveVillagers {
    var hasInit = false
    @SubscribeEvent(receiveCanceled = true, priority = EventPriority.HIGHEST)
    fun onCheckRender(event: RenderLivingEvent.Pre<*>) {
        if (!SkyblockReinvented.config.renderVillagers || !Utils.inSkyblock && inLoc(ArrStorage.hubLocs)) {
            return
        }
        if (!hasInit) {
            init()
            hasInit = true
        }
        for (pos in villagerLocs) {
            if (event.entity.getDistanceSq(pos) < 9) {
                event.isCanceled = true
                return
            }
        }
    }

    fun init() {
        // https://hypixel-skyblock.fandom.com/wiki/Village_Villagers
        add(38, 68, -46)
        add(0, 70, -54)
        add(-35, 70, -36)
        add(28, 69, -57)
        add(-7, 70, -75)
        add(-25, 70, -103)
        add(27, 70, -116)
        add(-6, 70, -89)
        add(-21, 68, -124)
        add(17, 70, -99)
        add(-16, 70, -81)
        add(-75, 70, -107)
    }

    private fun add(x: Int, y: Int, z: Int) {
        villagerLocs.add(BlockPos(x, y, z))
    }

    companion object {
        var villagerLocs = ArrayList<BlockPos>()
    }
}