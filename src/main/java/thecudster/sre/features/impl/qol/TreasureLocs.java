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

package thecudster.sre.features.impl.qol;

import net.minecraft.util.BlockPos;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.core.gui.RenderUtils;

import java.util.HashMap;
import java.util.Map;

public class TreasureLocs {
    public static Map<String, Vec3> locations = new HashMap<>();
    public static void init() {
        locations.put("There is a treasure chest somewhere in a small cave in the Gorge.", new Vec3(256,70,491));
        locations.put("In the Mushroom Gorge where blue meets the ceiling and the floor, you will find what you are looking for.", new Vec3(209, 42, -522));
        locations.put("There is likely a treasure under the wooden bridge in the oasis.", new Vec3(307,72,554));
        locations.put("I saw some treasure next to a house in the gorge.", new Vec3(295,85,561));
        locations.put("I seem to recall something near the well in the village.", new Vec3(170,76,375));
        locations.put("I saw some treasure by a cow skull near the village.", new Vec3(143,77,401));
        locations.put("There's this guy who says he has the best sheep in the world. I think I saw something around his hut.", new Vec3(392,84,366));
        locations.put("I remember there was a stone pillar made only of cobblestone in the oasis, could be something there.", new Vec3(118,65,408));
        locations.put("There was a hay stack with a crop greener than usual around it, I think there is something there.", new Vec3(339,82,388));
        locations.put("Some dirt was kicked up by the water pool in the overgrown Mushroom Cave. Have a look over there.", new Vec3(238,56,405));
        locations.put("There are some small ruins out in the desert, might want to check it out.", new Vec3(317,101,472));
        locations.put("I was at the upper oasis today, I recall seeing something on the cobblestone stepping stones.", new Vec3(189,77,465));
        locations.put("Near a melon patch inside a tunnel in the mountain I spotted something.", new Vec3(254,100,570));
        locations.put("I spotted something by an odd looking mushroom on one of the ledges in the Mushroom Gorge, you should check it out.", new Vec3(309,73,553));
        locations.put("I was in the desert earlier, and I saw something near a red sand rock", new Vec3(357,82,324));
        locations.put("There's a single piece of tall grass growing in the desert, I saw something there.", new Vec3(283,76,363));
        locations.put("Something caught my eye by the red sand near the bridge over the gorge.", new Vec3(306,105,490));
        locations.put("I found something by a mossy stone pillar in the oasis, you should take a look.", new Vec3(181,93,542));
        locations.put("I saw something near a farmer's cart, you should check it out.", new Vec3(155,90,593));
        locations.put("I thought I saw something near the smallest stone pillar in the oasis.", new Vec3(94,65,450));
        locations.put("I was down near the lower oasis yesterday, I think I saw something under the bridge.", new Vec3(142,70,448));
        locations.put("There's a treasure chest somewhere in a small cave in the gorge.", new Vec3(258,70,491));
        locations.put("There's a small house in the gorge, I saw some treasure near there", new Vec3(297,87,562));
        locations.put("Down in the Glowing Mushroom Cave, there was a weird looking mushroom, check it out.", new Vec3(181,46,452));
        locations.put("There's some old stone structures in the Mushroom Gorge, give it a look.", new Vec3(225,54,501));
        locations.put("Theres this guy who collects animals to experiment on, I think I saw something near his house.", new Vec3(261,179, 561));


    }
    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if (!SkyblockReinvented.config.treasureWaypoints) { return; }
        String unformatted = StringUtils.stripControlCodes(event.message.getUnformattedText());
        for (Map.Entry<String, Vec3> entry : locations.entrySet()) {
            if (unformatted.contains(entry.getKey())) {
                RenderUtils.drawWaypoint(20, new BlockPos(entry.getValue().xCoord, entry.getValue().yCoord, entry.getValue().zCoord), "Treasure");
            }
        }
    }
}
