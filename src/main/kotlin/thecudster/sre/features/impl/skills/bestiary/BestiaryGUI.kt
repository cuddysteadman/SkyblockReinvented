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
package thecudster.sre.features.impl.skills.bestiary

import net.minecraft.client.Minecraft
import thecudster.sre.core.gui.GuiElement
import thecudster.sre.core.gui.FloatPair
import net.minecraft.client.entity.EntityPlayerSP
import thecudster.sre.features.impl.skills.bestiary.BestiaryGUI
import net.minecraft.client.gui.ScaledResolution
import thecudster.sre.features.impl.skills.bestiary.BestiaryProgress
import thecudster.sre.core.gui.SmartFontRenderer.TextAlignment
import thecudster.sre.core.gui.SmartFontRenderer
import thecudster.sre.core.gui.ScreenRenderer
import thecudster.sre.core.gui.colours.CommonColors
import net.minecraft.util.EnumChatFormatting
import thecudster.sre.SkyblockReinvented
import thecudster.sre.features.impl.skills.bestiary.BestiaryGUI.BestiaryInfo
import thecudster.sre.util.Utils

class BestiaryGUI {
    class BestiaryInfo : GuiElement("Bestiary Info", FloatPair(0.004687f, 0.25639135f)) {
        private val mc = Minecraft.getMinecraft()
        override fun render() {
            val player = mc.thePlayer
            val sr = ScaledResolution(mc)
            if (this.toggled && player != null && mc.theWorld != null && !Utils.inDungeons && Utils.inSkyblock && BestiaryProgress.secondsSinceKill < 10) {
                val leftAlign = actualX < sr.scaledWidth / 2f
                for (i in 0..1) {
                    val alignment = if (leftAlign) TextAlignment.LEFT_RIGHT else TextAlignment.RIGHT_LEFT
                    ScreenRenderer.fontRenderer.drawString(
                        BestiaryProgress.current[i],
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
            for (i in 0..1) {
                val alignment = if (leftAlign) TextAlignment.LEFT_RIGHT else TextAlignment.RIGHT_LEFT
                ScreenRenderer.fontRenderer.drawString(
                    BestiaryProgress.current[i],
                    if (leftAlign) 0.0f else this.actualWidth,
                    (i * ScreenRenderer.fontRenderer.FONT_HEIGHT).toFloat(),
                    CommonColors.WHITE,
                    alignment,
                    SmartFontRenderer.TextShadow.NORMAL
                )
            }
        }

        override val toggled: Boolean
            get() = SkyblockReinvented.config.bestiaryInfo

        override val height: Int
            get() = ScreenRenderer.fontRenderer.FONT_HEIGHT * 2

        override val width: Int
            get() =  ScreenRenderer.fontRenderer.getStringWidth(
                EnumChatFormatting.RED.toString() +
                        "Kills to next level: " + EnumChatFormatting.GOLD + "Not detected yet!"
            )

        init {
            SkyblockReinvented.GUIMANAGER!!.registerElement(this)
        }
    }

    init {
        BestiaryInfo()
    }
}