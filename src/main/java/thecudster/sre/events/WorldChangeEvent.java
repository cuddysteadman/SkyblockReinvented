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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.versioning.DefaultArtifactVersion;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.features.impl.skills.bestiary.BestiaryProgress;
import thecudster.sre.features.impl.dungeons.CreeperSolver;
import thecudster.sre.features.impl.dungeons.HideIncorrectLivids;
import thecudster.sre.util.api.APIHandler;
import thecudster.sre.util.api.APIUtil;
import thecudster.sre.util.sbutil.Utils;

public class WorldChangeEvent {
    public boolean updateChecked = false;
    public static boolean notInSB = true;
    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) throws InterruptedException {
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        CreeperSolver.finishedPuzzle = false;
                        CreeperSolver.solved = 0;
                        HideIncorrectLivids.foundLivid = false;
                        HideIncorrectLivids.livid = null;
                        // updateCheck();
                        Utils.checkForDungeons();
                        Utils.checkForSkyblock();
                        if (SkyblockReinvented.config.bestiaryInfo && Utils.inSkyblock) {
                            new Thread(() -> {
                                check();
                                BestiaryProgress.getThings();
                            });
                        }
                        if (SkyblockReinvented.config.joinSB) {
                            if (!Utils.inSkyblock) {
                                notInSB = true;
                            } else {
                                notInSB = false;
                            }
                        }
                    }
                },3000
        );
    }
    /*
    * Converted to Java from JavaScript & modified from SBCount.
    * Unlicensed
    * @author IcarusPhantom_
     */
    public void check() {
        if (!SkyblockReinvented.config.skyblockUpdates) { return; }
        JsonObject data = APIUtil.getJSONResponse("https://api.hypixel.net/resources/skyblock/skills");
        String version = data.get("version").getAsString();
        int intVersion = Integer.parseInt(version.replace(".", ""));
        int oldVersion = Integer.parseInt(SkyblockReinvented.config.version.replace(".", ""));
        if (intVersion > oldVersion) {
            Utils.sendMsg(EnumChatFormatting.YELLOW + "Skyblock Version " + EnumChatFormatting.GREEN + SkyblockReinvented.config.version +
                    EnumChatFormatting.AQUA + " ➜ " + EnumChatFormatting.GREEN + version);
            Utils.sendMsg(EnumChatFormatting.GOLD + "The Skyblock Version has changed!");
            Utils.sendMsg(EnumChatFormatting.GOLD + "Something was patched, fixed, updated or added.");
            Utils.sendMsg(EnumChatFormatting.RED + "Warning! Server reboots might occur.");
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED +
                    "Warning! Bugs might be in this version. Please make sure to check #skyblock-updates by clicking on this message.")
                    .setChatStyle(new ChatStyle()
                    .setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.com/channels/825217968438902825/835657883106279474"))));
            SkyblockReinvented.config.version = "" + version;
            SkyblockReinvented.config.markDirty();
            SkyblockReinvented.config.writeData();
        }
    }
    public void updateCheck() {
        if (!updateChecked) {
            updateChecked = true;
            new Thread(() -> {
                EntityPlayer player = Minecraft.getMinecraft().thePlayer;

                System.out.println("Checking for updates...");
                JsonObject latestRelease = APIHandler.getResponse("https://api.github.com/repos/theCudster/SkyblockReinvented/releases/latest");
                String latestTag = latestRelease.get("tag_name").getAsString();
                DefaultArtifactVersion currentVersion = new DefaultArtifactVersion(SkyblockReinvented.VERSION);
                DefaultArtifactVersion latestVersion = new DefaultArtifactVersion(latestTag.substring(1));

                if (currentVersion.compareTo(latestVersion) < 0) {
                    String releaseURL = latestRelease.get("html_url").getAsString();

                    ChatComponentText update = new ChatComponentText(EnumChatFormatting.GREEN + "" + EnumChatFormatting.BOLD + "  [UPDATE]  ");
                    update.setChatStyle(update.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, releaseURL)));

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    player.addChatMessage(new ChatComponentText(SkyblockReinvented.VERSION + " is outdated. Please update to " + latestTag + ".\n").appendSibling(update));
                }
            }).start();
        }
    }
}
