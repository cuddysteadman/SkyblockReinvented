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
package thecudster.sre.events;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.sbutil.Utils;

public class Keybindings {
    @SubscribeEvent
    public void onKey(KeyInputEvent event) {
        if (SkyblockReinvented.keyBindings[0].isPressed()) {
            Utils.checkForDungeons();
            Utils.checkForSkyblock();
            Utils.checkIronman();
        } else if (SkyblockReinvented.keyBindings[1].isPressed()) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/pickupstash");
        }
    }
}
