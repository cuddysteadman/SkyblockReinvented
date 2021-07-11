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

import net.minecraft.util.BlockPos
import net.minecraft.util.Vec3
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import thecudster.sre.SkyblockReinvented
import thecudster.sre.util.RenderUtils
import thecudster.sre.util.sbutil.stripControlCodes

class TreasureLocs {
    @SubscribeEvent
    fun onChat(event: ClientChatReceivedEvent) {
        if (SkyblockReinvented.config.treasureWaypoints) {
            val unformatted = event.message.unformattedText.stripControlCodes()
            locations.filter { unformatted.contains(it.key!!) }.forEach{
                RenderUtils.drawWaypoint(20f, BlockPos(it.value.xCoord, it.value.yCoord, it.value.zCoord), "Treasure")
            }
        }
    }
    private var locations: HashMap<String?, Vec3> = hashMapOf(
        "There is a treasure chest somewhere in a small cave in the Gorge." to Vec3(256.0, 70.0, 491.0),
        "In the Mushroom Gorge where blue meets the ceiling and the floor, you will find what you are looking for." to Vec3(209.0, 42.0, -522.0),
        "There is likely a treasure under the wooden bridge in the oasis." to Vec3(170.0, 76.0, 375.0),
        "I saw some treasure next to a house in the gorge." to Vec3(295.0, 85.0, 561.0),
        "I seem to recall something near the well in the village." to Vec3(170.0, 76.0, 375.0),
        "I saw some treasure by a cow skull near the village." to Vec3(143.0, 77.0, 401.0),
        "There's this guy who says he has the best sheep in the world. I think I saw something around his hut." to Vec3(392.0, 84.0, 366.0),
        "I remember there was a stone pillar made only of cobblestone in the oasis, could be something there." to Vec3(118.0, 65.0, 408.0),
        "There was a hay stack with a crop greener than usual around it, I think there is something there." to Vec3(339.0, 82.0, 388.0),
        "Some dirt was kicked up by the water pool in the overgrown Mushroom Cave. Have a look over there." to Vec3(238.0, 56.0, 405.0),
        "There are some small ruins out in the desert, might want to check it out." to Vec3(317.0, 101.0, 472.0),
        "I was at the upper oasis today, I recall seeing something on the cobblestone stepping stones." to Vec3(189.0, 77.0, 465.0),
        "Near a melon patch inside a tunnel in the mountain I spotted something." to Vec3(254.0, 100.0, 570.0),
        "I spotted something by an odd looking mushroom on one of the ledges in the Mushroom Gorge, you should check it out." to Vec3(309.0, 73.0, 553.0),
        "I was in the desert earlier, and I saw something near a red sand rock" to Vec3(357.0, 82.0, 324.0),
        "There's a single piece of tall grass growing in the desert, I saw something there." to Vec3(283.0, 76.0, 363.0),
        "Something caught my eye by the red sand near the bridge over the gorge." to Vec3(306.0, 105.0, 490.0),
        "I found something by a mossy stone pillar in the oasis, you should take a look." to Vec3(181.0, 93.0, 542.0),
        "I saw something near a farmer's cart, you should check it out." to Vec3(155.0, 90.0, 593.0),
        "I thought I saw something near the smallest stone pillar in the oasis." to Vec3(94.0, 65.0, 450.0),
        "I was down near the lower oasis yesterday, I think I saw something under the bridge." to Vec3(142.0, 70.0, 448.0),
        "There's a treasure chest somewhere in a small cave in the gorge." to Vec3(258.0, 70.0, 491.0),
        "There's a small house in the gorge, I saw some treasure near there" to Vec3(297.0, 87.0, 562.0),
        "Down in the Glowing Mushroom Cave, there was a weird looking mushroom, check it out." to Vec3(181.0, 46.0, 452.0),
        "There's some old stone structures in the Mushroom Gorge, give it a look." to Vec3(225.0, 54.0, 501.0),
        "Theres this guy who collects animals to experiment on, I think I saw something near his house." to Vec3(261.0, 179.0, 561.0)
    )
}