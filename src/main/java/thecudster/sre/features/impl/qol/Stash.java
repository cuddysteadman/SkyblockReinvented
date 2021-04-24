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

import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.gui.GuiManager;
import thecudster.sre.util.sbutil.Utils;

public class Stash {
    public static boolean needsToPickup = false;

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if (!Utils.inSkyblock) { return; }
        if (!SkyblockReinvented.config.stash) { return; }
        String unformatted = event.message.getUnformattedText();
        unformatted = StringUtils.stripControlCodes(unformatted);
        if (unformatted.contains("You have ") && unformatted.contains("item stashed away!!")) {
            event.setCanceled(true);
            GuiManager.createTitle("Pick up your stash using " + Keyboard.getKeyName(SkyblockReinvented.keyBindings[12].getKeyCode()), 20);
            needsToPickup = true;
        }
        if (unformatted.contains("You picked up all items from your item stash!")) {
            event.setCanceled(true);
            needsToPickup = false;
        }
        else if (unformatted.contains("You picked up") && unformatted.contains("items from your item stash")) {
            event.setCanceled(true);
            needsToPickup = true;
        }
        if (unformatted.contains("From stash: ")) {
            event.setCanceled(true);
        }
        if (unformatted.contains("An item didn't fit in your inventory and was added to your item")) {
            event.setCanceled(true);
        }
    }
}
