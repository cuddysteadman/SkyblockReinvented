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
package thecudster.sre.features.impl.skills.slayer

import thecudster.sre.util.Utils.playLoudSound
import thecudster.sre.util.Utils.getUnformattedChat
import thecudster.sre.util.Utils.sendMsg
import thecudster.sre.util.sbutil.ScoreboardUtil.sidebarLines
import thecudster.sre.util.sbutil.ScoreboardUtil.cleanSB
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraft.client.gui.GuiChat
import net.minecraft.client.Minecraft
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraft.util.IChatComponent
import net.minecraft.util.ChatComponentText
import net.minecraft.util.EnumChatFormatting
import net.minecraft.util.ChatStyle
import net.minecraft.event.ClickEvent
import net.minecraftforge.client.event.GuiScreenEvent
import org.lwjgl.input.Mouse
import thecudster.sre.SkyblockReinvented
import thecudster.sre.util.sbutil.ScoreboardUtil
import thecudster.sre.features.impl.skills.slayer.SlayerReminder
import java.lang.InterruptedException
import thecudster.sre.core.gui.GuiManager
import thecudster.sre.util.Utils
import java.util.*

class SlayerReminder {
    private var lastMaddoxCommand = "/cb placeholder"
    private var lastMaddoxTime = 0.0
    @SubscribeEvent
    fun onMouseInputPost(event: GuiScreenEvent.MouseInputEvent.Post) {
        if (!Utils.inSkyblock) return
        if (Mouse.getEventButton() == 0 && event.gui is GuiChat) {
            if (SkyblockReinvented.config.maddoxClickable && System.currentTimeMillis() / 1000.0 - lastMaddoxTime < 10) {
                Minecraft.getMinecraft().thePlayer.sendChatMessage(lastMaddoxCommand)
            }
        }
    }

    @SubscribeEvent
    fun onChat(event: ClientChatReceivedEvent) {
        val unformatted = getUnformattedChat(event)
        if (unformatted.contains("Talk to Maddox to claim your")) {
            if (SkyblockReinvented.config.remindSlayer) {
                if (unformatted.contains("Spider Slayer XP!")) {
                    remindSlayer("Tarantula")
                }
                if (unformatted.contains("Zombie Slayer XP!")) {
                    remindSlayer("Revenant")
                }
                if (unformatted.contains("Wolf Slayer XP!")) {
                    remindSlayer("Sven")
                }
                event.isCanceled = true
            }
            return
        }
        if (SkyblockReinvented.config.maddoxClickable) {
            /*
             * Taken from Danker's Skyblock Mod under GPL 3.0 license.
             * https://github.com/bowser0000/SkyblockMod/blob/master/LICENSE
             * @author bowser0000
             */
            if (unformatted.contains("[OPEN MENU]")) {
                event.isCanceled = true
                val listOfSiblings = event.message.siblings
                for (sibling in listOfSiblings) {
                    if (sibling.unformattedText.contains("[OPEN MENU]")) {
                        lastMaddoxCommand = sibling.chatStyle.chatClickEvent.value
                        lastMaddoxTime = System.currentTimeMillis() / 1000.0
                    }
                }
                if (SkyblockReinvented.config.maddoxMsg) Minecraft.getMinecraft().thePlayer.addChatMessage(
                    ChatComponentText(EnumChatFormatting.GREEN.toString() + "Open chat then click anywhere on-screen to open Maddox (this message is by SRE, taken from DSM.)")
                )
                sendMsg(EnumChatFormatting.GREEN.toString() + "Please download DSM to support them so that you don't have to use this feature anymore!")
                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(
                    ChatComponentText(EnumChatFormatting.GRAY.toString() + "https://github.com/bowser0000/SkyblockMod/releases/").setChatStyle(
                        ChatStyle()
                            .setChatClickEvent(
                                ClickEvent(
                                    ClickEvent.Action.OPEN_URL,
                                    "https://github.com/bowser0000/SkyblockMod/releases/"
                                )
                            )
                    )
                )
            }
        }
    }

    fun remindSlayer(slayer: String?) {
        Timer().schedule(
            object : TimerTask() {
                override fun run() {
                    try {
                        var found = false
                        var notClaimed = false
                        val scoreboard = sidebarLines
                        for (s in scoreboard) {
                            val sCleaned = cleanSB(s)
                            if (sCleaned.contains(slayer!!)) {
                                found = true
                            }
                            if (sCleaned.contains("Boss slain!")) {
                                notClaimed = true
                            }
                        }
                        if (notClaimed || !found) {
                            when (slayer) {
                                "Tarantula" -> {
                                    remindTara()
                                    remindRevenant()
                                    remindSven()
                                }
                                "Revenant" -> {
                                    remindRevenant()
                                    remindSven()
                                }
                                "Sven" -> remindSven()
                            }
                        }
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            },
            8000
        )
    }

    companion object {
        @Throws(InterruptedException::class)
        fun remindRevenant() {
            Thread.sleep(5000)
            playLoudSound("random.orb", 0.5)
            GuiManager.createTitle("§cStart a new Revenant slayer!", 20)
        }

        @Throws(InterruptedException::class)
        fun remindTara() {
            Thread.sleep(5000)
            playLoudSound("random.orb", 0.5)
            GuiManager.createTitle("§cStart a new Tarantula slayer!", 20)
        }

        @Throws(InterruptedException::class)
        fun remindSven() {
            Thread.sleep(5000)
            playLoudSound("random.orb", 0.5)
            GuiManager.createTitle("§cStart a new Sven slayer!", 20)
        }
    }
}