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
package thecudster.sre.features.impl.skills.slayer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.core.gui.GuiManager;
import thecudster.sre.util.Utils;
import thecudster.sre.util.sbutil.ScoreboardUtil;

import java.util.List;

import static sun.security.x509.ReasonFlags.UNUSED;

public class SlayerReminder {
    private String lastMaddoxCommand = "/cb placeholder";
    private double lastMaddoxTime = 0;
    public static void remindRevenant() throws InterruptedException {
        Thread.sleep(5000);
        Utils.playLoudSound("random.orb", 0.5);
        GuiManager.createTitle("§cStart a new Revenant slayer!", 20);
    }
    public static void remindTara() throws InterruptedException {
        Thread.sleep(5000);
        Utils.playLoudSound("random.orb", 0.5);
        GuiManager.createTitle("§cStart a new Tarantula slayer!", 20);
    }
    public static void remindSven() throws InterruptedException {
        Thread.sleep(5000);
        Utils.playLoudSound("random.orb", 0.5);
        GuiManager.createTitle("§cStart a new Sven slayer!", 20);
    }
    @SuppressWarnings(UNUSED)
    @SubscribeEvent
    public void onMouseInputPost(GuiScreenEvent.MouseInputEvent.Post event) {
        if (!Utils.inSkyblock) return;
        if (Mouse.getEventButton() == 0 && event.gui instanceof GuiChat) {
            if (SkyblockReinvented.config.maddoxClickable && System.currentTimeMillis() / 1000.0 - lastMaddoxTime < 10) {
                Minecraft.getMinecraft().thePlayer.sendChatMessage(lastMaddoxCommand);
            }
        }
    }
    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String unformatted = Utils.getUnformattedChat(event);
        if (unformatted.contains("Talk to Maddox to claim your")) {
            if (SkyblockReinvented.config.remindSlayer) {
                if (unformatted.contains("Spider Slayer XP!")) {
                    remindSlayer("Tarantula");
                }
                if (unformatted.contains("Zombie Slayer XP!")) {
                    remindSlayer("Revenant");
                }
                if (unformatted.contains("Wolf Slayer XP!")) {
                    remindSlayer("Sven");
                }
                event.setCanceled(true);
            }
            return;
        }
        if (SkyblockReinvented.config.maddoxClickable) {
            /*
             * Taken from Danker's Skyblock Mod under GPL 3.0 license.
             * https://github.com/bowser0000/SkyblockMod/blob/master/LICENSE
             * @author bowser0000
             */

            if (unformatted.contains("[OPEN MENU]")) {
                event.setCanceled(true);
                List<IChatComponent> listOfSiblings = event.message.getSiblings();
                for (IChatComponent sibling : listOfSiblings) {
                    if (sibling.getUnformattedText().contains("[OPEN MENU]")) {
                        lastMaddoxCommand = sibling.getChatStyle().getChatClickEvent().getValue();
                        lastMaddoxTime = System.currentTimeMillis() / 1000.0;
                    }
                }
                if (SkyblockReinvented.config.maddoxMsg)
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Open chat then click anywhere on-screen to open Maddox (this message is by SRE, taken from DSM.)"));
                Utils.sendMsg(EnumChatFormatting.GREEN + "Please download DSM to support them so that you don't have to use this feature anymore!");
                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GRAY + "https://github.com/bowser0000/SkyblockMod/releases/").setChatStyle(new ChatStyle()
                        .setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/bowser0000/SkyblockMod/releases/"))));
            }
        }
    }
    public void remindSlayer(String slayer) {
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        try {
                            boolean found = false;
                            boolean notClaimed = false;
                            List<String> scoreboard = ScoreboardUtil.getSidebarLines();
                            for (String s : scoreboard) {
                                String sCleaned = ScoreboardUtil.cleanSB(s);
                                if (sCleaned.contains(slayer)) {
                                    found = true;
                                }
                                if (sCleaned.contains("Boss slain!")) {
                                    notClaimed = true;
                                }
                            }
                            if (notClaimed || !found) {
                                switch (slayer) {
                                    case "Tarantula": SlayerReminder.remindTara();
                                    case "Revenant": SlayerReminder.remindRevenant();
                                    case "Sven": SlayerReminder.remindSven();
                                }
                            }
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                },
                8000
        );
    }
}
