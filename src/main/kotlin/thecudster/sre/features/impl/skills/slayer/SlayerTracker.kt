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

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.util.EnumChatFormatting
import net.minecraft.util.StringUtils
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import thecudster.sre.SkyblockReinvented
import thecudster.sre.core.gui.structure.SmartFontRenderer.TextAlignment
import thecudster.sre.core.gui.structure.colours.CommonColors
import thecudster.sre.core.gui.structure.FloatPair
import thecudster.sre.core.gui.structure.GuiElement
import thecudster.sre.core.gui.structure.ScreenRenderer
import thecudster.sre.core.gui.structure.SmartFontRenderer
import thecudster.sre.util.RenderUtils
import thecudster.sre.util.Utils
import thecudster.sre.util.Utils.inLoc
import thecudster.sre.util.Utils.sendMsg
import thecudster.sre.util.sbutil.ArrStorage
import thecudster.sre.util.sbutil.stripControlCodes

class SlayerTracker {
    private val nxtLvl = " - Next LVL in ".length
    var xpLeftSven = "   Wolf Slayer LVL "
    var xpLeftTara = "   Spider Slayer LVL "
    var xpLeftRev = "   Zombie Slayer LVL "
    @SubscribeEvent
    fun onRender(event: RenderGameOverlayEvent?) {
        displayText[0] = EnumChatFormatting.GREEN.toString() + "XP Until Next Level: " + xpLeft
        displayText[1] = EnumChatFormatting.GREEN.toString() + "RNGesus Meter: " + rngesusMeter
        displayText[2] = EnumChatFormatting.GREEN.toString() + "Current Slayer: " + currentSlayer
    }

    /*
    @SubscribeEvent
    public void onDeath(LivingDeathEvent event) {
        if (Utils.inLoc(ArrStorage.slayerLocs)) {
            for (String s : ScoreboardUtil.getSidebarLines()) {
                if (s.stripControlCodes().contains("/") && s.stripControlCodes().contains(" Kills")) {
                    String kills = StringUtils.stripControlCodes(s);
                    String toNext = kills.substring(0, kills.indexOf(" Kills"));
                }
            }
        }
    }
     */
    @SubscribeEvent(receiveCanceled = true, priority = EventPriority.HIGHEST)
    fun onChat(event: ClientChatReceivedEvent) {
        if (SkyblockReinvented.config.slayerInfo) {
            var unformatted = event.message.unformattedText
            val meterText = "RNGesus Meter: -------------------- "
            if (unformatted.contains(meterText)) {
                unformatted = EnumChatFormatting.LIGHT_PURPLE.toString() + unformatted.substring(meterText.length + 3)
                if (SkyblockReinvented.config.slayerMode == 1) {
                    unformatted = unformatted.substring(2, unformatted.length - 1)
                    val meter = RenderUtils.progressBar(
                        30,
                        unformatted.stripControlCodes().toDouble() / 100,
                        EnumChatFormatting.LIGHT_PURPLE
                    ) + getPercentageString(
                        StringUtils.stripControlCodes(unformatted).toDouble() / 100
                    )
                    rngesusMeter = meter
                } else {
                    rngesusMeter = unformatted
                }
                event.isCanceled = true
                return
            }
            if (unformatted.contains(xpLeftRev)) {
                currentSlayer = EnumChatFormatting.LIGHT_PURPLE.toString() + "Revenant"
                event.isCanceled = true
                if (SkyblockReinvented.config.slayerMode == 1) {
                    progressBarSlayer(unformatted, ArrStorage.revLeveling, false, EnumChatFormatting.GREEN)
                } else {
                    unformatted = unformatted.substring(xpLeftRev.length + nxtLvl + 1, unformatted.length - 1)
                    xpLeft = EnumChatFormatting.LIGHT_PURPLE.toString() + unformatted
                }
                return
            }
            if (unformatted.contains(xpLeftTara)) {
                currentSlayer = EnumChatFormatting.LIGHT_PURPLE.toString() + "Tarantula"
                event.isCanceled = true
                if (SkyblockReinvented.config.slayerMode == 1) {
                    progressBarSlayer(unformatted, ArrStorage.taraLeveling, false, EnumChatFormatting.GREEN)
                } else {
                    unformatted = unformatted.substring(xpLeftTara.length + nxtLvl + 1, unformatted.length - 1)
                    xpLeft = EnumChatFormatting.LIGHT_PURPLE.toString() + unformatted
                }
                return
            }
            if (unformatted.contains(xpLeftSven)) {
                currentSlayer = EnumChatFormatting.LIGHT_PURPLE.toString() + "Sven"
                event.isCanceled = true
                if (SkyblockReinvented.config.slayerMode == 1) {
                    progressBarSlayer(unformatted, ArrStorage.svenLeveling, true, EnumChatFormatting.GREEN)
                } else {
                    unformatted = unformatted.substring(xpLeftSven.length + nxtLvl + 1, unformatted.length - 1)
                    xpLeft = EnumChatFormatting.LIGHT_PURPLE.toString() + unformatted
                }
                return
            }
        }
    }

    companion object {
        var rngesusMeter = EnumChatFormatting.LIGHT_PURPLE.toString() + "Not detected yet!"
        var xpLeft = EnumChatFormatting.LIGHT_PURPLE.toString() + "Not detected yet!"
        var currentSlayer = EnumChatFormatting.LIGHT_PURPLE.toString() + "Not detected yet!"
        var current = EnumChatFormatting.LIGHT_PURPLE.toString() + "Not detected yet!"
        var displayText = arrayOf(
            EnumChatFormatting.GREEN.toString() + "XP Until Next Level: " + EnumChatFormatting.LIGHT_PURPLE + "Not detected yet!",
            EnumChatFormatting.GREEN.toString() + "RNGesus Meter: " + EnumChatFormatting.LIGHT_PURPLE + "Not detected yet!",
            EnumChatFormatting.GREEN.toString() + "Current Slayer: " + EnumChatFormatting.LIGHT_PURPLE + "Not detected yet!"
        ) // EnumChatFormatting.GREEN + "Progress to Boss Spawn: " + EnumChatFormatting.LIGHT_PURPLE + "Not detected yet!",
        private val mc = Minecraft.getMinecraft()

        init {
            SlayerGuiElement()
        }
    }

    class SlayerGuiElement : GuiElement("Slayer XP", FloatPair(0.00625f, 0.14626351f)) {
        override fun render() {
            val player = mc.thePlayer
            val sr = ScaledResolution(mc)
            if (this.toggled && Utils.inSkyblock && inLoc(ArrStorage.slayerLocs) && player != null && mc.theWorld != null) {
                val leftAlign = actualX < sr.scaledWidth / 2f
                for (i in displayText.indices) {
                    val alignment = if (leftAlign) TextAlignment.LEFT_RIGHT else TextAlignment.RIGHT_LEFT
                    ScreenRenderer.fontRenderer.drawString(
                        displayText[i],
                        if (leftAlign) this.actualX else this.actualX + this.width,
                        this.actualY + i * ScreenRenderer.fontRenderer.FONT_HEIGHT,
                        CommonColors.WHITE,
                        alignment,
                        SmartFontRenderer.TextShadow.NORMAL
                    )
                }
            }
        }

        override fun demoRender() {
            val sr = ScaledResolution(mc)
            val leftAlign = actualX < sr.scaledWidth / 2f
            for (i in displayText.indices) {
                val alignment = if (leftAlign) TextAlignment.LEFT_RIGHT else TextAlignment.RIGHT_LEFT
                ScreenRenderer.fontRenderer.drawString(
                    displayText[i],
                    if (leftAlign) 0.0f else this.actualWidth,
                    (i * ScreenRenderer.fontRenderer.FONT_HEIGHT).toFloat(),
                    CommonColors.WHITE,
                    alignment,
                    SmartFontRenderer.TextShadow.NORMAL
                )
            }
        }

        override val toggled: Boolean
            get() = SkyblockReinvented.config.slayerInfo

        override val height: Int
            get() = ScreenRenderer.fontRenderer.FONT_HEIGHT * 3

        override val width: Int
            get() = ScreenRenderer.fontRenderer.getStringWidth(displayText[0])

        init {
            SkyblockReinvented.GUIMANAGER!!.registerElement(this)
        }
    }

    fun progressBarSlayer(unformatted: String, levelingInfo: DoubleArray, sven: Boolean, colour: EnumChatFormatting?) {
        try {
            val lvl: Int
            val xp: Int
            if (sven) {
                lvl = unformatted.substring(xpLeftSven.length, xpLeftSven.length + 1).toInt()
                xp = unformatted.substring(xpLeftSven.length + nxtLvl + 1, unformatted.length - 4).replace(",", "")
                    .toInt()
            } else {
                lvl = unformatted.substring(xpLeftRev.length, xpLeftRev.length + 1).toInt()
                xp = unformatted.substring(xpLeftRev.length + nxtLvl + 1, unformatted.length - 4).replace(",", "")
                    .toInt()
            }
            if (lvl == 9) {
                xpLeft = EnumChatFormatting.GOLD.toString() + "MAX LEVEL"
                return
            }
            val progress = 1.0 - xp / levelingInfo[lvl - 1]
            xpLeft = RenderUtils.progressBar(30, progress, colour!!) + getPercentageString(progress)
        } catch (ex: Exception) {
            sendMsg(EnumChatFormatting.RED.toString() + "SkyblockReinvented caught and logged an exception at SlayerTracker. Please report this!")
            ex.printStackTrace()
        }
    }

    private fun getPercentageString(progress: Double): String {
        val progressPercentage = progress * 100
        val rounded = Math.round(progressPercentage * 10.0) / 10.0
        return if (rounded < 25) {
            EnumChatFormatting.RED.toString() + "" + rounded + "%"
        } else if (rounded < 50) {
            EnumChatFormatting.GOLD.toString() + "" + rounded + "%"
        } else if (rounded < 75) {
            EnumChatFormatting.YELLOW.toString() + "" + rounded + "%"
        } else {
            EnumChatFormatting.GREEN.toString() + "" + rounded + "%"
        }
    }
}