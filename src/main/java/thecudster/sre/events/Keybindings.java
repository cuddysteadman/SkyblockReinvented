/*
 * SkyblockReinvented - Hypixel Skyblock Improvement Modification for Minecraft
 * Copyright (C) 2021 theCudster
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
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
        if (!Utils.inSkyblock) { return; }
        if (SkyblockReinvented.keyBindings[0].isPressed()) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/bz");
        }
        if (SkyblockReinvented.keyBindings[1].isPressed()) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/ah");
        }
        if (SkyblockReinvented.keyBindings[2].isPressed()) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/visit prtl");
        }
        if (SkyblockReinvented.keyBindings[3].isPressed()) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/pets");
        }
        if (SkyblockReinvented.keyBindings[4].isPressed()) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/echest");
        }
        if (SkyblockReinvented.keyBindings[5].isPressed()) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/wd");
        }
        if (SkyblockReinvented.keyBindings[6].isPressed()) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/craftingmenu");
        }
        if (SkyblockReinvented.keyBindings[7].isPressed()) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/sbmenu");
        }
        if (SkyblockReinvented.keyBindings[8].isPressed()) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/skills");
        }
        if (SkyblockReinvented.keyBindings[9].isPressed()) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/hotm");
        }
        if (SkyblockReinvented.keyBindings[10].isPressed()) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/warp hub");
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/warp hub");
        }
        if (SkyblockReinvented.keyBindings[11].isPressed()) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/dh");
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/dh");
        }

    }
}
