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

import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.versioning.DefaultArtifactVersion;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.api.APIHandler;

public class UpdateChecker {
    public boolean updateChecked = false;
    /*
    * Modified from Danker's Skyblock Mod under GNU General Public License.
    * https://github.com/bowser0000/SkyblockMod/blob/master/LICENSE
    * @author bowser0000
     */
    @SubscribeEvent
    public void check(EntityJoinWorldEvent event) {
        if (!updateChecked) {
            updateChecked = true;
            new Thread(() -> {
                EntityPlayer player = Minecraft.getMinecraft().thePlayer;

                System.out.println("Checking for updates...");
                JsonObject latestRelease = APIHandler.getResponse("https://api.github.com/repos/theCudster/SkyblockReinvented/releases");
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
