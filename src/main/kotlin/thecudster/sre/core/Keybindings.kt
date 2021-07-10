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
package thecudster.sre.core

import thecudster.sre.util.Utils.checkForDungeons
import thecudster.sre.util.Utils.checkForSkyblock
import thecudster.sre.util.Utils.checkIronman
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent
import thecudster.sre.features.impl.qol.MiscFeatures
import net.minecraft.client.Minecraft
import thecudster.sre.SkyblockReinvented

class Keybindings {
    @SubscribeEvent
    fun onKey(event: KeyInputEvent?) {
        if (SkyblockReinvented.keyBindings[0]!!.isPressed) {
            checkForDungeons()
            checkForSkyblock()
            checkIronman()
        } else if (SkyblockReinvented.keyBindings[1]!!.isPressed && MiscFeatures.needsToPickup) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/pickupstash")
        }
    }
}