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
package thecudster.sre.features.impl.qol

import thecudster.sre.util.sbutil.ItemUtil.getItemLore
import thecudster.sre.util.Utils.sendMsg
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.io.IOException
import net.minecraftforge.client.event.GuiScreenEvent
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.inventory.GuiChest
import net.minecraft.inventory.ContainerChest
import thecudster.sre.features.impl.qol.MiscGUIs
import java.lang.NumberFormatException
import net.minecraft.util.EnumChatFormatting
import thecudster.sre.features.impl.qol.DiscordRPC
import net.minecraft.client.gui.ScaledResolution
import org.lwjgl.opengl.GL11
import net.minecraft.client.gui.Gui
import org.apache.commons.lang3.StringUtils
import thecudster.sre.SkyblockReinvented
import thecudster.sre.util.Utils
import java.awt.Color
import java.lang.Exception

class MiscGUIs {
    @SubscribeEvent
    @Throws(IOException::class)
    fun showScreen(event: GuiScreenEvent.BackgroundDrawnEvent?) {
        /*
         * Modified/Improved/Reinvented from Nate's Skyblock Mod under GNU General Public License.
         * https://github.com/Nat3z/SkyblockMod/blob/main/LICENSE
         * @author Nat3z
         */
        if (!Utils.inSkyblock) return
        if (SkyblockReinvented.config.jacobRender) {
            val mc = Minecraft.getMinecraft()
            if (mc.currentScreen is GuiChest) {
                val chest = mc.currentScreen as GuiChest
                val inventory = chest.inventorySlots as ContainerChest
                if (inventory.lowerChestInventory.displayName.unformattedText == "Your Contests") {
                    val slots = chest.inventorySlots.inventorySlots
                    for (toCheck in slots) {
                        if (toCheck.stack != null) {
                            if (toCheck.stack.hasDisplayName()) {
                                val name = toCheck.stack.displayName
                                if (name.contains("Autumn") || name.contains("Winter") || name.contains("Spring") || name.contains(
                                        "Summer"
                                    )
                                ) {
                                    if (toCheck.stack.isItemEnchanted) {
                                        showOnSlot(
                                            chest.inventorySlots.inventorySlots.size,
                                            toCheck.xDisplayPosition,
                                            toCheck.yDisplayPosition,
                                            Color.green.rgb
                                        )
                                    } else {
                                        showOnSlot(
                                            chest.inventorySlots.inventorySlots.size,
                                            toCheck.xDisplayPosition,
                                            toCheck.yDisplayPosition,
                                            Color.red.rgb
                                        )
                                    }
                                }
                            }
                        }
                    }
                    return
                }
            }
        }
        if (SkyblockReinvented.config.hubOverlay) {
            try {
                val mc = Minecraft.getMinecraft()
                if (mc.currentScreen is GuiChest) {
                    val chest = mc.currentScreen as GuiChest
                    val inventory = chest.inventorySlots as ContainerChest
                    if (inventory.lowerChestInventory.displayName.unformattedText == "SkyBlock Hub Selector") {
                        val slots = chest.inventorySlots.inventorySlots
                        for (toCheck in slots) {
                            if (toCheck.stack != null) {
                                if (toCheck.stack.hasDisplayName()) {
                                    val name = toCheck.stack.displayName
                                    if (name.contains("SkyBlock Hub")) {
                                        val lore = getItemLore(toCheck.stack)
                                        var found = false
                                        var foundYellow = false
                                        for (loreval in lore) {
                                            var s = loreval
                                            if (s.contains("Full!") || s.contains("Already connected!")) {
                                                found = true
                                                showOnSlot(
                                                    chest.inventorySlots.inventorySlots.size,
                                                    toCheck.xDisplayPosition,
                                                    toCheck.yDisplayPosition,
                                                    Color.red.rgb
                                                )
                                            } else if (s.contains("Players: ")) {
                                                s = s.substring(11, 13)
                                                s = s.replace("/", "")
                                                if (s.toInt() > 70) {
                                                    foundYellow = true
                                                }
                                            }
                                        }
                                        if (!found && !foundYellow) {
                                            showOnSlot(
                                                chest.inventorySlots.inventorySlots.size,
                                                toCheck.xDisplayPosition,
                                                toCheck.yDisplayPosition,
                                                Color.green.rgb
                                            )
                                        } else if (foundYellow) {
                                            showOnSlot(
                                                chest.inventorySlots.inventorySlots.size,
                                                toCheck.xDisplayPosition,
                                                toCheck.yDisplayPosition,
                                                Color.yellow.rgb
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        return
                    }
                }
            } catch (ex: NumberFormatException) {
                sendMsg(EnumChatFormatting.RED.toString() + "SRE caught and logged an exception. Please report this.")
                ex.printStackTrace() // temp fix
            }
        }
        if (SkyblockReinvented.config.toSearch != null && !SkyblockReinvented.config.toSearch.isEmpty()) {
            val mc = Minecraft.getMinecraft()
            if (mc.currentScreen is GuiChest) {
                val chest = mc.currentScreen as GuiChest
                val inventory = chest.inventorySlots as ContainerChest
                val displayText = inventory.lowerChestInventory.displayName.unformattedText
                if (displayText.contains("Ender Chest") || displayText.contains("Accessory Bag") || displayText.contains(
                        "Wardrobe"
                    ) || displayText.contains("Backpack")
                ) {
                    val slots = chest.inventorySlots.inventorySlots
                    for (toCheck in slots) {
                        if (toCheck.stack != null) {
                            if (toCheck.stack.hasDisplayName()) {
                                val name = toCheck.stack.displayName
                                for (s in SkyblockReinvented.config.toSearch) {
                                    if (StringUtils.containsIgnoreCase(name, s)) {
                                        showOnSlot(
                                            chest.inventorySlots.inventorySlots.size,
                                            toCheck.xDisplayPosition,
                                            toCheck.yDisplayPosition,
                                            Color.green.rgb
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        val mc = Minecraft.getMinecraft()
        if (SkyblockReinvented.config.chestStop >= 0 && Utils.inDungeons) {
            if (mc.currentScreen is GuiChest) {
                val chest = mc.currentScreen as GuiChest
                val inventory = chest.inventorySlots as ContainerChest
                val displayText = inventory.lowerChestInventory.displayName.unformattedText
                if (displayText == "Wood Chest") {
                    woodenChest = true
                    return
                }
                val dungeonChest =
                    displayText == "Emerald Chest" || displayText == "Obsidian Chest" || displayText == "Gold Chest" || displayText == "Diamond Chest"
                if (dungeonChest) {
                    woodenChest = false
                }
            }
        }
        if (SkyblockReinvented.config.reforgeSoundOff) {
            if (mc.currentScreen is GuiChest) {
                val chest = mc.currentScreen as GuiChest
                val inventory = chest.inventorySlots as ContainerChest
                val displayText = inventory.lowerChestInventory.displayName.unformattedText
                if (displayText == "Reforge Item" || displayText == "Reforge Accessory Bag") {
                    inReforge = true
                }
            }
        }
        if (mc.currentScreen is GuiChest) {
            val chest = mc.currentScreen as GuiChest
            val inventory = chest.inventorySlots as ContainerChest
            val displayText = inventory.lowerChestInventory.displayName.unformattedText
            if (displayText == "Chest" && Utils.inDungeons && Utils.inSkyblock) {
                inDungeonChest = true
            }
        }
        if (mc.currentScreen is GuiChest && SkyblockReinvented.config.discordMode == 0) {
            val chest = mc.currentScreen as GuiChest
            val inventory = chest.inventorySlots as ContainerChest
            val displayText = inventory.lowerChestInventory.displayName.unformattedText
            if (displayText == "SkyBlock Menu") {
                try {
                    if (inventory.inventorySlots[48] == null) return
                    if (inventory.inventorySlots[48].stack == null) return
                    if (getItemLore(inventory.inventorySlots[48].stack) == null) return
                    for (s in getItemLore(inventory.inventorySlots[48].stack)) {
                        if (s.contains("Playing on: ")) {
                            DiscordRPC.state =
                                "Profile: " + net.minecraft.util.StringUtils.stripControlCodes(s.substring(s.indexOf(": ") + 2))
                            break
                        }
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        }
    }

    companion object {
        var foundBadItem = false
        var woodenChest = false
        var inDungeonChest = false

        /*
     * Taken from Danker's Skyblock Mod under GPL 3.0 license.
     * https://github.com/bowser0000/SkyblockMod/blob/master/LICENSE
     * @author bowser0000
     */
        fun showOnSlot(size: Int, xSlotPos: Int, ySlotPos: Int, color: Int) {
            val sr = ScaledResolution(Minecraft.getMinecraft())
            val guiLeft = (sr.scaledWidth - 176) / 2
            val guiTop = (sr.scaledHeight - 222) / 2
            val x = guiLeft + xSlotPos
            var y = guiTop + ySlotPos
            if (size != 90) y += (6 - (size - 36) / 9) * 9
            GL11.glTranslated(0.0, 0.0, 1.0)
            Gui.drawRect(x, y, x + 16, y + 16, color)
            GL11.glTranslated(0.0, 0.0, -1.0)
        }

        @JvmField
        var inReforge = false
    }
}