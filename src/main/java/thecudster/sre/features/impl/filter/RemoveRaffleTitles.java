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

package thecudster.sre.features.impl.filter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.network.play.server.S45PacketTitle;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StringUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.events.PacketEvent;
import thecudster.sre.util.Utils;

/**
 * Modified from Skytils under GNU Affero General Public license.
 * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
 * @author Sychic
 * @author My-Name-Is-Jeff
 */
public class RemoveRaffleTitles {
    @SubscribeEvent
    public void onReceivePacket(PacketEvent.ReceiveEvent event) {
        if (!Utils.inSkyblock) return;
        if (SkyblockReinvented.config.removeRaffleTitles) {
            if (event.packet instanceof S45PacketTitle) {
                S45PacketTitle packet = (S45PacketTitle) event.packet;
                if (packet.getMessage() != null) {
                    String unformatted = StringUtils.stripControlCodes(packet.getMessage().getUnformattedText());
                    for (NetworkPlayerInfo i : Utils.getTabEntries()) {
                        if (i != null) {
                            if (i.getDisplayName() == null) { return; }
                            if (i.getDisplayName().getUnformattedText() == null) { return; }
                            if (unformatted.contains(i.getDisplayName().getUnformattedText())) {
                                event.setCanceled(true);
                            }
                        }
                    }
                    if (unformatted.contains("WINNER #1 (for 3x rewards!)") || unformatted.contains("WINNER #2 (for 3x rewards!") || unformatted.contains("WINNER #3 (for 3x rewards")) {
                        event.setCanceled(true);
                    }
                    if (unformatted.contains("MVP")) {
                        event.setCanceled(true);
                    }
                    if (unformatted.contains("VIP")) {
                        event.setCanceled(true);
                    }
                    if (unformatted.contains("WINNER")) {
                        event.setCanceled(true);
                    }
                }
            }
        }
        if (SkyblockReinvented.config.dangerGhosts) {
            if (event.packet instanceof S45PacketTitle) {
                S45PacketTitle packet = (S45PacketTitle) event.packet;
                if (packet.getMessage() != null) {
                    String unformatted = StringUtils.stripControlCodes(packet.getMessage().getUnformattedText());
                    if (unformatted.contains("Powerful creatures reside in the Mist")) {
                        event.setCanceled(true);
                    }
                    if (unformatted.contains("DANGER")) {
                        event.setCanceled(true);
                    }
                }
            }
        }
    }
}
