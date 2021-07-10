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

import thecudster.sre.util.Utils.tabEntries
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import thecudster.sre.events.PacketEvent.ReceiveEvent
import net.minecraft.network.play.server.S45PacketTitle
import net.minecraft.client.network.NetworkPlayerInfo
import net.minecraft.util.StringUtils
import thecudster.sre.SkyblockReinvented
import thecudster.sre.util.Utils

/**
 * Modified from Skytils under GNU Affero General Public license.
 * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
 * @author Sychic
 * @author My-Name-Is-Jeff
 */
class RemoveRaffleTitles {
    @SubscribeEvent
    fun onReceivePacket(event: ReceiveEvent) {
        if (!Utils.inSkyblock) return
        if (SkyblockReinvented.config.removeRaffleTitles) {
            if (event.packet is S45PacketTitle) {
                val packet = event.packet as S45PacketTitle?
                if (packet!!.message != null) {
                    val unformatted = StringUtils.stripControlCodes(packet.message.unformattedText)
                    if (tabEntries.filter { it != null && it.displayName != null && it.displayName.unformattedText != null && unformatted.contains(it.displayName.unformattedText) }.isNotEmpty()) {
                        event.isCanceled = true
                    }
                    for (i in tabEntries) {
                        if (i != null) {
                            if (i.displayName == null) {
                                return
                            }
                            if (i.displayName.unformattedText == null) {
                                return
                            }
                            if (unformatted.contains(i.displayName.unformattedText)) {
                                event.isCanceled = true
                            }
                        }
                    }
                    if (unformatted.contains("WINNER #1 (for 3x rewards!)") || unformatted.contains("WINNER #2 (for 3x rewards!") || unformatted.contains(
                            "WINNER #3 (for 3x rewards"
                        )
                    ) {
                        event.isCanceled = true
                    }
                    if (unformatted.contains("MVP")) {
                        event.isCanceled = true
                    }
                    if (unformatted.contains("VIP")) {
                        event.isCanceled = true
                    }
                    if (unformatted.contains("WINNER")) {
                        event.isCanceled = true
                    }
                }
            }
        }
        if (SkyblockReinvented.config.dangerGhosts) {
            if (event.packet is S45PacketTitle) {
                val packet = event.packet as S45PacketTitle?
                if (packet!!.message != null) {
                    val unformatted = StringUtils.stripControlCodes(packet.message.unformattedText)
                    if (unformatted.contains("Powerful creatures reside in the Mist")) {
                        event.isCanceled = true
                    }
                    if (unformatted.contains("DANGER")) {
                        event.isCanceled = true
                    }
                }
            }
        }
    }
}