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

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraft.util.Vec3
import thecudster.sre.features.impl.qol.TreasureLocs
import thecudster.sre.core.gui.RenderUtils
import net.minecraft.util.BlockPos
import net.minecraft.util.StringUtils
import thecudster.sre.SkyblockReinvented
import java.util.HashMap

class TreasureLocs {
    @SubscribeEvent
    fun onChat(event: ClientChatReceivedEvent) {
        if (!SkyblockReinvented.config.treasureWaypoints) {
            return
        }
        val unformatted = StringUtils.stripControlCodes(event.message.unformattedText)
        locations.filter { unformatted.contains(it.key!!) }.forEach {RenderUtils.drawWaypoint(20f, BlockPos(it.value.xCoord, it.value.yCoord, it.value.zCoord), "Treasure")}
    }

    companion object {
        var locations: HashMap<String?, Vec3> = hashMapOf(
            "There is a treasure chest somewhere in a small cave in the Gorge." to Vec3(256.0, 70.0, 491.0),
            "In the Mushroom Gorge where blue meets the ceiling and the floor, you will find what you are looking for." to Vec3(209.0, 42.0, -522.0),
            "There is likely a treasure under the wooden bridge in the oasis." to Vec3(170.0, 76.0, 375.0)
            )
        @JvmStatic
        fun init() {
            /*
            locations["I saw some treasure next to a house in the gorge."] = Vec3(295.0, 85.0, 561.0)
            locations["I seem to recall something near the well in the village."] = Vec3(170.0, 76.0, 375.0)
            locations["I saw some treasure by a cow skull near the village."] = Vec3(143, 77, 401)
            locations["There's this guy who says he has the best sheep in the world. I think I saw something around his hut."] =
                Vec3(392, 84, 366)
            locations["I remember there was a stone pillar made only of cobblestone in the oasis, could be something there."] =
                Vec3(118, 65, 408)
            locations["There was a hay stack with a crop greener than usual around it, I think there is something there."] =
                Vec3(339, 82, 388)
            locations["Some dirt was kicked up by the water pool in the overgrown Mushroom Cave. Have a look over there."] =
                Vec3(238, 56, 405)
            locations["There are some small ruins out in the desert, might want to check it out."] =
                Vec3(317, 101, 472)
            locations["I was at the upper oasis today, I recall seeing something on the cobblestone stepping stones."] =
                Vec3(189, 77, 465)
            locations["Near a melon patch inside a tunnel in the mountain I spotted something."] = Vec3(254, 100, 570)
            locations["I spotted something by an odd looking mushroom on one of the ledges in the Mushroom Gorge, you should check it out."] =
                Vec3(309, 73, 553)
            locations["I was in the desert earlier, and I saw something near a red sand rock"] = Vec3(357, 82, 324)
            locations["There's a single piece of tall grass growing in the desert, I saw something there."] =
                Vec3(283, 76, 363)
            locations["Something caught my eye by the red sand near the bridge over the gorge."] =
                Vec3(306, 105, 490)
            locations["I found something by a mossy stone pillar in the oasis, you should take a look."] =
                Vec3(181, 93, 542)
            locations["I saw something near a farmer's cart, you should check it out."] = Vec3(155, 90, 593)
            locations["I thought I saw something near the smallest stone pillar in the oasis."] = Vec3(94, 65, 450)
            locations["I was down near the lower oasis yesterday, I think I saw something under the bridge."] =
                Vec3(142, 70, 448)
            locations["There's a treasure chest somewhere in a small cave in the gorge."] =
                Vec3(258, 70, 491)
            locations["There's a small house in the gorge, I saw some treasure near there"] =
                Vec3(297, 87, 562)
            locations["Down in the Glowing Mushroom Cave, there was a weird looking mushroom, check it out."] =
                Vec3(181, 46, 452)
            locations["There's some old stone structures in the Mushroom Gorge, give it a look."] = Vec3(225, 54, 501)
            locations["Theres this guy who collects animals to experiment on, I think I saw something near his house."] =
                Vec3(261, 179, 561)
             */
        }
    }
}