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
package thecudster.sre.events

import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.event.ClickEvent
import net.minecraft.util.ChatComponentText
import net.minecraft.util.ChatStyle
import net.minecraft.util.EnumChatFormatting
import net.minecraftforge.event.world.WorldEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.versioning.DefaultArtifactVersion
import thecudster.sre.SkyblockReinvented
import thecudster.sre.features.impl.dungeons.CreeperSolver
import thecudster.sre.features.impl.skills.bestiary.BestiaryProgress
import thecudster.sre.util.Utils
import thecudster.sre.util.Utils.checkForDungeons
import thecudster.sre.util.Utils.checkForSkyblock
import thecudster.sre.util.Utils.sendHelp
import thecudster.sre.util.Utils.sendMsg
import thecudster.sre.util.api.APIHandler.getResponse
import thecudster.sre.util.api.APIUtil.getJSONResponse
import java.util.*

class WorldChangeEvent {
    var updateChecked = false
    @SubscribeEvent
    @Throws(InterruptedException::class)
    fun onWorldChange(event: WorldEvent.Load?) {
        Timer().schedule(
            object : TimerTask() {
                override fun run() {
                    if (SkyblockReinvented.config.firstLoad) {
                        sendMsg(EnumChatFormatting.GOLD.toString() + "Thanks for downloading SRE! Here's a quick guide to get you started.")
                        sendHelp()
                        SkyblockReinvented.config.firstLoad = false
                        SkyblockReinvented.config.markDirty()
                        SkyblockReinvented.config.writeData()
                    }
                    CreeperSolver.finishedPuzzle = false
                    CreeperSolver.solved = 0
                    // updateCheck();
                    checkForDungeons()
                    checkForSkyblock()
                    if (SkyblockReinvented.config.bestiaryInfo && Utils.inSkyblock) {
                        Thread {
                            check()
                            BestiaryProgress.getThings()
                        }
                    }
                    if (SkyblockReinvented.config.joinSB) {
                        notInSB = !Utils.inSkyblock
                    }
                }
            }, 4000
        )
    }

    /*
    * Converted to Java from JavaScript & modified from SBCount.
    * Unlicensed
    * @author IcarusPhantom_
     */
    fun check() {
        if (!SkyblockReinvented.config.skyblockUpdates) {
            return
        }
        val data = getJSONResponse("https://api.hypixel.net/resources/skyblock/skills")
        val version = data["version"].asString
        val intVersion = version.replace(".", "").toInt()
        val oldVersion = SkyblockReinvented.config.version.replace(".", "").toInt()
        if (intVersion > oldVersion) {
            sendMsg(
                EnumChatFormatting.YELLOW.toString() + "Skyblock Version " + EnumChatFormatting.GREEN + SkyblockReinvented.config.version +
                        EnumChatFormatting.AQUA + " ➜ " + EnumChatFormatting.GREEN + version
            )
            sendMsg(EnumChatFormatting.GOLD.toString() + "The Skyblock Version has changed!")
            sendMsg(EnumChatFormatting.GOLD.toString() + "Something was patched, fixed, updated or added.")
            sendMsg(EnumChatFormatting.RED.toString() + "Warning! Server reboots might occur.")
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(
                ChatComponentText(
                    EnumChatFormatting.RED.toString() +
                            "Warning! Bugs might be in this version. Please make sure to check #skyblock-updates by clicking on this message."
                )
                    .setChatStyle(
                        ChatStyle()
                            .setChatClickEvent(
                                ClickEvent(
                                    ClickEvent.Action.OPEN_URL,
                                    "https://discord.com/channels/825217968438902825/835657883106279474"
                                )
                            )
                    )
            )
            SkyblockReinvented.config.version = "" + version
            SkyblockReinvented.config.markDirty()
            SkyblockReinvented.config.writeData()
        }
    }

    fun updateCheck() {
        if (!updateChecked) {
            updateChecked = true
            Thread {
                val player: EntityPlayer = Minecraft.getMinecraft().thePlayer
                println("Checking for updates...")
                val latestRelease =
                    getResponse("https://api.github.com/repos/theCudster/SkyblockReinvented/releases/latest")
                val latestTag = latestRelease["tag_name"].asString
                val currentVersion = DefaultArtifactVersion(SkyblockReinvented.VERSION)
                val latestVersion = DefaultArtifactVersion(latestTag.substring(1))
                if (currentVersion.compareTo(latestVersion) < 0) {
                    val releaseURL = latestRelease["html_url"].asString
                    val update =
                        ChatComponentText(EnumChatFormatting.GREEN.toString() + "" + EnumChatFormatting.BOLD + "  [UPDATE]  ")
                    update.chatStyle =
                        update.chatStyle.setChatClickEvent(ClickEvent(ClickEvent.Action.OPEN_URL, releaseURL))
                    try {
                        Thread.sleep(2000)
                    } catch (ex: InterruptedException) {
                        ex.printStackTrace()
                    }
                    player.addChatMessage(
                        ChatComponentText(
                            """${SkyblockReinvented.VERSION} is outdated. Please update to $latestTag.
"""
                        ).appendSibling(update)
                    )
                }
            }.start()
        }
    }

    companion object {
        @JvmField
        var notInSB = true
    }
}