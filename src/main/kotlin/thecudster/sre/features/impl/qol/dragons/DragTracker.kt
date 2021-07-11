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
package thecudster.sre.features.impl.qol.dragons

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.util.EnumChatFormatting
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import thecudster.sre.SkyblockReinvented
import thecudster.sre.core.gui.structure.FloatPair
import thecudster.sre.core.gui.structure.GuiElement
import thecudster.sre.core.gui.structure.ScreenRenderer
import thecudster.sre.core.gui.structure.SmartFontRenderer
import thecudster.sre.core.gui.structure.SmartFontRenderer.TextAlignment
import thecudster.sre.core.gui.structure.colours.CommonColors
import thecudster.sre.util.Utils
import thecudster.sre.util.Utils.sendMsg
import thecudster.sre.util.sbutil.CurrentLoc
import thecudster.sre.util.sbutil.stripControlCodes

class DragTracker {
    companion object {
        @JvmField
        var recentDrags = ArrayList<String>()
        var dragGui = ArrayList<String>()
        @JvmStatic
        fun updateGui() {
            dragGui.clear()
            dragGui.add(EnumChatFormatting.GOLD.toString() + "Recent Dragons: ")
            for (s in recentDrags) {
                dragGui.add(EnumChatFormatting.WHITE.toString() + " " + s)
            }
            dragGui.add("")
            dragGui.add(EnumChatFormatting.GOLD.toString() + "Drags Since: ")
            dragGui.add(EnumChatFormatting.GOLD.toString() + " Superior: " + EnumChatFormatting.WHITE + SkyblockReinvented.config.dragsSinceSup)
            dragGui.add(EnumChatFormatting.AQUA.toString() + " AOTD: " + EnumChatFormatting.WHITE + SkyblockReinvented.config.dragsSinceAotd)
            dragGui.add(EnumChatFormatting.RED.toString() + " Pet: " + EnumChatFormatting.WHITE + SkyblockReinvented.config.dragsSincePet)
        }

        init {
            DragGUI()
        }
    }

    class DragGUI : GuiElement("Dragon GUI", FloatPair(0, 0)) {
        var dragDemo = arrayOfNulls<String>(10)
        override fun render() {
            if (!this.toggled || !(!Utils.inDungeons && Utils.inSkyblock && (CurrentLoc.currentLoc == "Dragon's Nest" || CurrentLoc.currentLoc == "The End"))) return
            val sr = ScaledResolution(Minecraft.getMinecraft())
            val leftAlign = actualX < sr.scaledWidth / 2f
            for (i in dragGui.indices) {
                val alignment = if (leftAlign) TextAlignment.LEFT_RIGHT else TextAlignment.RIGHT_LEFT
                ScreenRenderer.fontRenderer.drawString(
                    dragGui[i],
                    if (leftAlign) this.actualX else this.actualX + this.width,
                    this.actualY + i * ScreenRenderer.fontRenderer.FONT_HEIGHT,
                    CommonColors.WHITE,
                    alignment,
                    SmartFontRenderer.TextShadow.NORMAL
                )
            }
        }

        override fun demoRender() {
            val sr = ScaledResolution(Minecraft.getMinecraft())
            val leftAlign = actualX < sr.scaledWidth / 2f
            for (i in dragDemo.indices) {
                val alignment = if (leftAlign) TextAlignment.LEFT_RIGHT else TextAlignment.RIGHT_LEFT
                ScreenRenderer.fontRenderer.drawString(
                    dragDemo[i],
                    if (leftAlign) 0.0f else this.actualWidth,
                    (i * ScreenRenderer.fontRenderer.FONT_HEIGHT).toFloat(),
                    CommonColors.WHITE,
                    alignment,
                    SmartFontRenderer.TextShadow.NORMAL
                )
            }
        }

        override val toggled: Boolean
            get() = SkyblockReinvented.config.dragTracker

        override val height: Int
            get() = ScreenRenderer.fontRenderer.FONT_HEIGHT * dragDemo.size

        override val width: Int
            get() = ScreenRenderer.fontRenderer.getStringWidth(dragDemo[3])

        init {
            SkyblockReinvented.GUIMANAGER!!.registerElement(this)
            dragDemo[0] = EnumChatFormatting.GOLD.toString() + "Recent Dragons: "
            dragDemo[1] = EnumChatFormatting.WHITE.toString() + " Young Dragon"
            dragDemo[2] = EnumChatFormatting.AQUA.toString() + " Wise Dragon"
            dragDemo[3] = EnumChatFormatting.GOLD.toString() + " Superior Dragon"
            dragDemo[4] = EnumChatFormatting.RED.toString() + " Strong Dragon"
            dragDemo[5] = " "
            dragDemo[6] = EnumChatFormatting.GOLD.toString() + "Drags Since: "
            dragDemo[7] = EnumChatFormatting.GOLD.toString() + " Superior: " + EnumChatFormatting.WHITE + 2
            dragDemo[8] = EnumChatFormatting.AQUA.toString() + " AOTD: " + EnumChatFormatting.WHITE + 6
            dragDemo[9] = EnumChatFormatting.RED.toString() + " Pet: " + EnumChatFormatting.WHITE + 48
        }
    }

    @SubscribeEvent
    fun onChat(event: ClientChatReceivedEvent) {
        if (event.type.toInt() == 0x2) {
            return
        }
        if (!Utils.inSkyblock) return
        var unformatted = event.message.unformattedText.stripControlCodes()
        if (SkyblockReinvented.config.dragTracker && CurrentLoc.currentLoc == "Dragon's Nest" && !Utils.inDungeons) {
            if (unformatted.contains("The ") && unformatted.contains(" Dragon") && unformatted.contains("has spawned!")) {
                unformatted = unformatted.substring(unformatted.indexOf("The ") + 4, unformatted.indexOf(" Dragon"))
                println(unformatted)
                when (unformatted) {
                    "Protector" -> {
                        if (recentDrags.size >= 3) {
                            recentDrags.removeAt(3)
                        }
                        recentDrags.add(0, EnumChatFormatting.DARK_BLUE.toString() + unformatted)
                        SkyblockReinvented.config.dragsSinceSup++
                    }
                    "Strong" -> {
                        if (recentDrags.size >= 3) {
                            recentDrags.removeAt(3)
                        }
                        recentDrags.add(0, EnumChatFormatting.RED.toString() + unformatted)
                        SkyblockReinvented.config.dragsSinceSup++
                    }
                    "Old" -> {
                        if (recentDrags.size >= 3) {
                            recentDrags.removeAt(3)
                        }
                        recentDrags.add(0, EnumChatFormatting.YELLOW.toString() + unformatted)
                        SkyblockReinvented.config.dragsSinceSup++
                    }
                    "Young" -> {
                        if (recentDrags.size >= 3) {
                            recentDrags.removeAt(3)
                        }
                        recentDrags.add(0, EnumChatFormatting.WHITE.toString() + unformatted)
                        SkyblockReinvented.config.dragsSinceSup++
                    }
                    "Unstable" -> {
                        if (recentDrags.size >= 3) {
                            recentDrags.removeAt(3)
                        }
                        recentDrags.add(0, EnumChatFormatting.DARK_PURPLE.toString() + unformatted)
                        SkyblockReinvented.config.dragsSinceSup++
                    }
                    "Wise" -> {
                        if (recentDrags.size >= 3) {
                            recentDrags.removeAt(3)
                        }
                        recentDrags.add(0, EnumChatFormatting.AQUA.toString() + unformatted)
                        SkyblockReinvented.config.dragsSinceSup++
                    }
                    "Superior" -> {
                        if (recentDrags.size >= 3) {
                            recentDrags.removeAt(3)
                        }
                        recentDrags.add(EnumChatFormatting.GOLD.toString() + unformatted)
                        SkyblockReinvented.config.dragsSinceSup = 0
                    }
                    else -> sendMsg("§cSkyblockReinvented found an issue with the dragon tracker. Please report this!")
                }
                SkyblockReinvented.config.markDirty()
                SkyblockReinvented.config.writeData()
            } else if (unformatted.contains("has obtained Aspect of the Dragons!") && unformatted.contains(Minecraft.getMinecraft().thePlayer.name)) {
                SkyblockReinvented.config.dragsSinceAotd = 0
                SkyblockReinvented.config.dragsSincePet++
                SkyblockReinvented.config.markDirty()
                SkyblockReinvented.config.writeData()
            } else if (unformatted.contains("has obtained") && unformatted.contains("Lvl 1") && unformatted.contains("Ender Dragon!")
                && unformatted.contains(Minecraft.getMinecraft().thePlayer.name)
            ) {
                SkyblockReinvented.config.dragsSincePet = 0
                SkyblockReinvented.config.dragsSinceAotd++
                SkyblockReinvented.config.markDirty()
                SkyblockReinvented.config.writeData()
            } else if (unformatted.contains("has obtained") && unformatted.contains(Minecraft.getMinecraft().thePlayer.name)) {
                SkyblockReinvented.config.dragsSincePet++
                SkyblockReinvented.config.dragsSinceAotd++
                SkyblockReinvented.config.markDirty()
                SkyblockReinvented.config.writeData()
            }
        }
    }
}