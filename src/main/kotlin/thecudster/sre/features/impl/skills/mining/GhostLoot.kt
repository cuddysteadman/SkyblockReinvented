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
package thecudster.sre.features.impl.skills.mining

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.entity.monster.EntityCreeper
import net.minecraft.util.EnumChatFormatting
import net.minecraftforge.client.event.RenderLivingEvent
import net.minecraftforge.event.entity.living.LivingDeathEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import thecudster.sre.SkyblockReinvented
import thecudster.sre.core.gui.structure.FloatPair
import thecudster.sre.core.gui.structure.GuiElement
import thecudster.sre.core.gui.structure.ScreenRenderer
import thecudster.sre.core.gui.structure.SmartFontRenderer
import thecudster.sre.core.gui.structure.SmartFontRenderer.TextAlignment
import thecudster.sre.core.gui.structure.colours.CommonColors
import thecudster.sre.util.Utils
import thecudster.sre.util.sbutil.CurrentLoc

class GhostLoot {
    companion object {
        var sorrow = 0
        var bagCash = 0
        var plasma = 0
        var volta = 0
        var ghostlyBoots = 0
        var ghostsSinceSorrow = 0
        var display = arrayOf(
            EnumChatFormatting.GREEN.toString() + "Sorrow: " + sorrow,
            EnumChatFormatting.GREEN.toString() + "Bag of Cash: " + bagCash,
            EnumChatFormatting.GREEN.toString() +
                    "Plasma: " + plasma,
            EnumChatFormatting.GREEN.toString() + "Volta: "
                    + volta,
            EnumChatFormatting.GREEN.toString() + "Ghostly Boots: " + ghostlyBoots,
            EnumChatFormatting.GREEN.toString() + "Ghosts Since Sorrow: " + ghostsSinceSorrow
        )

        init {
            GhostGUI()
        }
    }

    class GhostGUI : GuiElement("Ghost GUI", FloatPair(0, 0)) {
        override fun render() {
            if (!this.toggled || !Utils.inSkyblock) return
            val sr = ScaledResolution(Minecraft.getMinecraft())
            val leftAlign = actualX < sr.scaledWidth / 2f
            for (i in display.indices) {
                val alignment = if (leftAlign) TextAlignment.LEFT_RIGHT else TextAlignment.RIGHT_LEFT
                ScreenRenderer.fontRenderer.drawString(
                    display[i],
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
            for (i in display.indices) {
                val alignment = if (leftAlign) TextAlignment.LEFT_RIGHT else TextAlignment.RIGHT_LEFT
                ScreenRenderer.fontRenderer.drawString(
                    display[i],
                    if (leftAlign) 0.0f else this.actualWidth,
                    (i * ScreenRenderer.fontRenderer.FONT_HEIGHT).toFloat(),
                    CommonColors.WHITE,
                    alignment,
                    SmartFontRenderer.TextShadow.NORMAL
                )
            }
        }

        override val toggled: Boolean
            get() = SkyblockReinvented.config.ghostTracker

        override val height: Int
            get() = ScreenRenderer.fontRenderer.FONT_HEIGHT * 6
        override val width: Int
            get() = ScreenRenderer.fontRenderer.getStringWidth(display[5])

        init {
            SkyblockReinvented.GUIMANAGER!!.registerElement(this)
        }
    }

    @SubscribeEvent
    fun onDeath(event: LivingDeathEvent) {
        if (event.entity is EntityCreeper) {
            if (CurrentLoc.currentLoc == "The Mist") {
                val creeper = event.entity as EntityCreeper
                if (creeper.powered) {
                    ghostsSinceSorrow++
                }
            }
        }
    }

    @SubscribeEvent
    fun onRender(event: RenderLivingEvent.Pre<*>?) {
        display[0] = EnumChatFormatting.GREEN.toString() + "Sorrow: " + EnumChatFormatting.LIGHT_PURPLE + sorrow
        display[1] = EnumChatFormatting.GREEN.toString() + "Bag of Cash: " + EnumChatFormatting.LIGHT_PURPLE + bagCash
        display[2] = EnumChatFormatting.GREEN.toString() + "Plasma: " + EnumChatFormatting.LIGHT_PURPLE + plasma
        display[3] = EnumChatFormatting.GREEN.toString() + "Volta: " + EnumChatFormatting.LIGHT_PURPLE + volta
        display[4] =
            EnumChatFormatting.GREEN.toString() + "Ghostly Boots: " + EnumChatFormatting.LIGHT_PURPLE + ghostlyBoots
        display[5] =
            EnumChatFormatting.GREEN.toString() + "Ghosts Since Sorrow: " + EnumChatFormatting.LIGHT_PURPLE + ghostsSinceSorrow
    }
}