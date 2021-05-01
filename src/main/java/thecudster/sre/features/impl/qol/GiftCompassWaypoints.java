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

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.S05PacketSpawnPosition;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.events.ReceivePacketEvent;
import thecudster.sre.util.gui.DrawWaypoint;
import thecudster.sre.util.sbutil.CurrentLoc;
import thecudster.sre.util.sbutil.ItemUtil;
import thecudster.sre.util.sbutil.Utils;

public class GiftCompassWaypoints {
    public BlockPos pos;
    public static boolean found = false;
    @SubscribeEvent(receiveCanceled=true)
    public void onReceivePacket(ReceivePacketEvent event) {
        if (!Utils.inSkyblock) { return; }
        if (event.packet instanceof S05PacketSpawnPosition) {
            S05PacketSpawnPosition spawnPosition = (S05PacketSpawnPosition) event.packet;
            pos = spawnPosition.getSpawnPos();
        }

    }
    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if (!Utils.inSkyblock) { return; }
        if (!(CurrentLoc.currentLoc.equals("Jerry's Workshop") || CurrentLoc.currentLoc.equals("Jerry Pond"))) { return; }
        if (Minecraft.getMinecraft().thePlayer.getHeldItem() != null && ItemUtil.getSkyBlockItemID(Minecraft.getMinecraft().thePlayer.getHeldItem()) != null) {
                if (ItemUtil.getSkyBlockItemID(Minecraft.getMinecraft().thePlayer.getHeldItem()).equals("GIFT_COMPASS")) {
                    if (pos != null) {
                        if (SkyblockReinvented.config.giftCompassWaypoints && !found) {
                            DrawWaypoint.drawWaypoint(event.partialTicks, pos, "Gift");
                        }
                    }
                }

        }

    }
}
