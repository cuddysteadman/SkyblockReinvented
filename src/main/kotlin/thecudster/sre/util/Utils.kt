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
package thecudster.sre.util

import net.minecraft.client.Minecraft
import net.minecraft.scoreboard.ScoreObjective
import net.minecraftforge.common.MinecraftForge
import thecudster.sre.events.JoinSkyblockEvent
import thecudster.sre.events.LeaveSkyblockEvent
import thecudster.sre.util.sbutil.ScoreboardUtil
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraftforge.fml.common.ObfuscationReflectionHelper
import net.minecraft.client.network.NetworkPlayerInfo
import net.minecraft.scoreboard.ScorePlayerTeam
import com.google.common.collect.ComparisonChain
import com.google.common.collect.Ordering
import net.minecraft.block.Block
import net.minecraft.world.WorldSettings
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import thecudster.sre.events.PacketEvent.ReceiveEvent
import net.minecraft.network.play.server.S02PacketChat
import net.minecraftforge.client.event.ClientChatReceivedEvent
import thecudster.sre.util.sbutil.CurrentLoc
import thecudster.sre.util.sbutil.CancelParticleHelper
import net.minecraft.event.ClickEvent
import net.minecraft.inventory.Slot
import net.minecraft.util.*
import java.lang.Exception
import java.util.*

object Utils {
    private val mc = Minecraft.getMinecraft()
    @JvmField
    var inSkyblock = false
    @JvmField
    var inDungeons = false
    var shouldBypassVolume = false
    var random = Random()

    /**
     * Taken from Skytils under GNU Affero General Public license.
     * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
     * @author Sychic
     * @author My-Name-Is-Jeff
     */
    val isOnHypixel: Boolean
        get() = try {
            if (mc != null && mc.theWorld != null && !mc.isSingleplayer) {
                if (mc.thePlayer != null && mc.thePlayer.clientBrand != null) {
                    if (mc.thePlayer.clientBrand.toLowerCase().contains("hypixel")) true
                }
                if (mc.currentServerData != null) mc.currentServerData.serverIP.toLowerCase().contains("hypixel")
            }
            false
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }

    /**
     * Modified from Danker's Skyblock Mod under GPL 3.0 license
     * https://github.com/bowser0000/SkyblockMod/blob/master/LICENSE
     * @author bowser0000
     */
    @JvmStatic
    fun checkForSkyblock() {
        if (isOnHypixel) {
            val scoreboardObj = mc!!.theWorld.scoreboard.getObjectiveInDisplaySlot(1)
            if (scoreboardObj != null) {
                val scObjName = scoreboardObj.displayName
                if (scObjName.contains("SKYBLOCK")) {
                    MinecraftForge.EVENT_BUS.post(JoinSkyblockEvent())
                    inSkyblock = true
                    return
                }
            }
        }
        MinecraftForge.EVENT_BUS.post(LeaveSkyblockEvent())
        inSkyblock = false
    }

    /**
     * Taken from Danker's Skyblock Mod under GPL 3.0 license
     * https://github.com/bowser0000/SkyblockMod/blob/master/LICENSE
     * @author bowser0000
     */
    var romanNumerals: Map<Char, Int> = hashMapOf('I' to 1, 'V' to 5, 'X' to 10, 'L' to 50, 'C' to 100, 'D' to 500, 'M' to 1000)

    @JvmStatic
    fun getIntFromString(text: String, romanNumeral: Boolean): Int {
        if (text.matches(Regex(".*\\d.*"))) {
            return StringUtils.stripControlCodes(text).replace("[^\\d]".toRegex(), "").toInt()
        } else if (romanNumeral) {
            var number = 0
            var i = 0
            while (i < text.length) {
                val roman = romanNumerals[text[i]]!!
                if (i != text.length - 1 && roman < romanNumerals[text[i + 1]]!!) {
                    number += romanNumerals[text[i + 1]]!! - roman
                    i++
                } else {
                    number += roman
                }
                i++
            }
            return number
        }
        return -1
    }

    /**
     * Modified from Skytils under GNU Affero General Public license.
     * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
     * @author Sychic
     * @author My-Name-Is-Jeff
     */
    @JvmStatic
    fun checkForDungeons() {
        if (inSkyblock) {
            val scoreboard = ScoreboardUtil.sidebarLines
            for (s in scoreboard) {
                val sCleaned = ScoreboardUtil.cleanSB(s)
                if (sCleaned.contains("The Catacombs") || sCleaned.contains("Dungeon Cleared:")) {
                    inDungeons = true
                    return
                }
            }
        }
        inDungeons = false
    }

    fun getSlotUnderMouse(gui: GuiContainer): Slot {
        return ObfuscationReflectionHelper.getPrivateValue(GuiContainer::class.java, gui, "theSlot", "field_147006_u")
    }

    fun getBlocksWithinRangeAtSameY(center: BlockPos, radius: Int, y: Int): Iterable<BlockPos> {
        val corner1 = BlockPos(center.x - radius, y, center.z - radius)
        val corner2 = BlockPos(center.x + radius, y, center.z + radius)
        return BlockPos.getAllInBox(corner1, corner2)
    }

    private val playerInfoOrdering = object : Ordering<NetworkPlayerInfo>() {
        override fun compare(p_compare_1_: NetworkPlayerInfo?, p_compare_2_: NetworkPlayerInfo?): Int {
            val scorePlayerTeam = p_compare_1_?.playerTeam
            val scorePlayerTeam1 = p_compare_2_?.playerTeam
            if (p_compare_1_ != null) {
                if (p_compare_2_ != null) {
                    return ComparisonChain.start().compareTrueFirst(
                        p_compare_1_.gameType != WorldSettings.GameType.SPECTATOR,
                        p_compare_2_.gameType != WorldSettings.GameType.SPECTATOR
                    ).compare(
                        if (scorePlayerTeam != null) scorePlayerTeam.registeredName else "",
                        if (scorePlayerTeam1 != null) scorePlayerTeam1.registeredName else ""
                    ).compare(p_compare_1_.gameProfile.name, p_compare_2_.gameProfile.name).result()
                }
                return 0
            }
            return -1
        }
    }
    @JvmStatic
    val tabEntries: List<NetworkPlayerInfo>
        get() = if (Minecraft.getMinecraft().thePlayer == null) emptyList() else playerInfoOrdering.sortedCopy(Minecraft.getMinecraft().thePlayer.sendQueue.playerInfoMap)

    fun isInTablist(player: EntityPlayer): Boolean {
        if (mc!!.isSingleplayer) {
            return true
        }
        for (pi in mc.netHandler.playerInfoMap) {
            if (pi.gameProfile.name.equals(player.name, ignoreCase = true)) {
                return true
            }
        }
        return false
    }

    @JvmStatic
    fun isInTablist(playerName: String?): Boolean {
        if (mc!!.isSingleplayer) {
            return true
        }
        for (pi in mc.netHandler.playerInfoMap) {
            if (pi.gameProfile.name.equals(playerName, ignoreCase = true)) {
                return true
            }
        }
        return false
    }

    /**
     * Taken from Danker's Skyblock Mod under GPL 3.0 license
     * https://github.com/bowser0000/SkyblockMod/blob/master/LICENSE
     * @author bowser0000
     */
    fun getNearbyBlock(mc: Minecraft, pos: BlockPos?, vararg blockTypes: Block): BlockPos? {
        if (pos == null) return null
        val pos1 = BlockPos(pos.x - 2, pos.y - 3, pos.z - 2)
        val pos2 = BlockPos(pos.x + 2, pos.y + 3, pos.z + 2)
        var closestBlock: BlockPos? = null
        var closestBlockDistance = 99.0
        val blocks = BlockPos.getAllInBox(pos1, pos2)
        for (block in blocks) {
            for (blockType in blockTypes) {
                if (mc.theWorld.getBlockState(block).block === blockType && block.distanceSq(pos) < closestBlockDistance) {
                    closestBlock = block
                    closestBlockDistance = block.distanceSq(pos)
                }
            }
        }
        return closestBlock
    }

    /**
     * Taken from Danker's Skyblock Mod under GPL 3.0 license
     * https://github.com/bowser0000/SkyblockMod/blob/master/LICENSE
     * @author bowser0000
     */
    fun getFirstBlockPosAfterVectors(mc: Minecraft, pos1: Vec3, pos2: Vec3, strength: Int, distance: Int): BlockPos? {
        val x = pos2.xCoord - pos1.xCoord
        val y = pos2.yCoord - pos1.yCoord
        val z = pos2.zCoord - pos1.zCoord
        for (i in strength until distance * strength) { // Start at least 1 strength away
            val newX = pos1.xCoord + x / strength * i
            val newY = pos1.yCoord + y / strength * i
            val newZ = pos1.zCoord + z / strength * i
            val newBlock = BlockPos(newX, newY, newZ)
            if (mc.theWorld.getBlockState(newBlock).block !== Blocks.air) {
                return newBlock
            }
        }
        return null
    }

    /**
     * Taken from Skytils under GNU Affero General Public license.
     * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
     * @author Sychic
     * @author My-Name-Is-Jeff
     */
    fun cancelChatPacket(ReceivePacketEvent: ReceiveEvent) {
        if (ReceivePacketEvent.packet !is S02PacketChat) return
        ReceivePacketEvent.isCanceled = true
        val packet = ReceivePacketEvent.packet as S02PacketChat
        MinecraftForge.EVENT_BUS.post(ClientChatReceivedEvent(packet.type, packet.chatComponent))
    }

    /**
     * Taken from SkyblockAddons under MIT License
     * https://github.com/BiscuitDevelopment/SkyblockAddons/blob/master/LICENSE
     * @author BiscuitDevelopment
     */
    @JvmStatic
    fun playLoudSound(sound: String?, pitch: Double) {
        shouldBypassVolume = true
        mc!!.thePlayer.playSound(sound, 2f, pitch.toFloat())
        shouldBypassVolume = false
    }

    // original
    @JvmField
    var ironmanProfile = false
    @JvmStatic
    fun sendMsg(msg: String?) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText(msg))
    }

    @JvmStatic
    fun sendCommand(command: String) {
        Minecraft.getMinecraft().thePlayer.sendChatMessage("/$command")
    }

    @JvmStatic
    fun checkIronman(): Boolean {
        if (inSkyblock) {
            val scoreboard = ScoreboardUtil.sidebarLines
            for (s in scoreboard) {
                val sCleaned = ScoreboardUtil.cleanSB(s)
                if (sCleaned.contains("Ironman")) {
                    ironmanProfile = true
                    return ironmanProfile
                }
            }
        }
        return false
    }

    @JvmStatic
    fun inLoc(locs: Array<String>): Boolean {
        for (s in locs) {
            if (CurrentLoc.currentLoc == s) {
                return true
            }
        }
        return false
    }

    @JvmStatic
    fun cancelParticles() {
        Minecraft.getMinecraft().theWorld.removeWorldAccess(Minecraft.getMinecraft().renderGlobal)
        Minecraft.getMinecraft().theWorld.addWorldAccess(CancelParticleHelper())
    }

    @JvmStatic
    fun getUnformattedChat(event: ClientChatReceivedEvent): String {
        return StringUtils.stripControlCodes(event.message.unformattedText)
    }

    @JvmStatic
    fun sendHelp() {
        sendMsg(
            """${EnumChatFormatting.GOLD}✰ SkyblockReinvented Commands & Help
${EnumChatFormatting.GOLD}✰ Settings
${EnumChatFormatting.AQUA}   • /sre: open the main GUI
${EnumChatFormatting.AQUA}   • /sre gui: open GUI editing screen (alias: editlocations)
${EnumChatFormatting.AQUA}   • /sre config: open config
${EnumChatFormatting.AQUA}   • /sre vigilance: open Vigilance (config settings) editing screen
${EnumChatFormatting.AQUA}   • /sre discord: join our discord!${EnumChatFormatting.AQUA}   • /sre github: view the source code for SRE on GitHub!${EnumChatFormatting.AQUA}   • /discord {status}: sets your custom status in discord (if you are using that setting)
${EnumChatFormatting.GRAY}     (Aliases: /da, /disc, /discset, /dset, /rp, /rpset)
${EnumChatFormatting.GOLD}✰ General
${EnumChatFormatting.AQUA}   • /re: toggle whether to render players ${EnumChatFormatting.GRAY}(Aliases: /render)
${EnumChatFormatting.AQUA}   • /re add: add a player to the whitelist of rendered players${EnumChatFormatting.AQUA}   • /re search: removes all players from the whitelist then adds the player you specify
${EnumChatFormatting.AQUA}   • /re remove: removes a certain player from the whitelist
${EnumChatFormatting.AQUA}   • /re clear: clears the whitelist
${EnumChatFormatting.AQUA}   • /s add: adds an item name to the whitelist of what to search for
${EnumChatFormatting.AQUA}   • /s search: removes all item names from the whitelist then adds the item name you specify
${EnumChatFormatting.AQUA}   • /s clear: clears the whitelist
${EnumChatFormatting.GOLD}✰ Dungeons
${EnumChatFormatting.AQUA}   • /floor 1, /floor 2, /floor 3, etc: join a dungeon floor${EnumChatFormatting.GRAY} (Aliases: /fl, /catafloor)
${EnumChatFormatting.AQUA}   • /master 1, /master 2, /master 3, etc: joins a master dungeon floor ${EnumChatFormatting.GRAY}(Aliases: /m, /mcata)
${EnumChatFormatting.AQUA}   • /fragrun: parties an online fragrunning bot ${EnumChatFormatting.GRAY}(Aliases: /fr, /frags)
${EnumChatFormatting.GOLD}✰ Features:
${EnumChatFormatting.AQUA}   • /drag: toggles the dragon tracker GUI element ${EnumChatFormatting.GRAY}(Aliases: /dr, /drags)
${EnumChatFormatting.AQUA}   • /drag clear: clears current dragon tracker info ${EnumChatFormatting.GRAY}(Aliases: /dr clear, /drags clear)"""
        )
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(
            ChatComponentText(
                EnumChatFormatting.GOLD.toString() +
                        "Join the" + EnumChatFormatting.AQUA + " discord!"
            ).setChatStyle(
                ChatStyle()
                    .setChatClickEvent(
                        ClickEvent(
                            ClickEvent.Action.OPEN_URL,
                            "https://discord.gg/xkeYgZrRbN"
                        )
                    )
            )
        )
    }

    @JvmStatic
    fun containsAnyOf(check: String, needsToContain: Array<String>): Boolean {
        for (s in needsToContain) {
            if (check.contains(s!!)) return true
        }
        return false
    }

    @JvmStatic
    fun containsAllOf(check: String, needsToContain: Array<String?>): Boolean {
        for (s in needsToContain) {
            if (!check.contains(s!!)) return false
        }
        return true
    }
}