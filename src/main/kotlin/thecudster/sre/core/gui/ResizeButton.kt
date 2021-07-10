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
package thecudster.sre.core.gui

import thecudster.sre.core.gui.GuiElement
import thecudster.sre.core.gui.ResizeButton.Corner
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.Minecraft
import thecudster.sre.core.gui.ResizeButton
import thecudster.sre.core.gui.colours.CommonColors
import thecudster.sre.core.gui.RenderUtils
import net.minecraft.client.gui.ScaledResolution
import org.lwjgl.input.Mouse

/**
 * Taken from Skytils under GNU Affero General Public license.
 * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
 * @author My-Name-Is-Jeff
 * @author Sychic
 */
class ResizeButton(var x: Float, var y: Float, var element: GuiElement, val corner: Corner) :
    GuiButton(-1, 0, 0, null) {
    override fun drawButton(mc: Minecraft, mouseX: Int, mouseY: Int) {
        val scale = element.scale
        hovered = mouseX >= x && mouseY >= y && mouseX < x + SIZE * 2 * scale && mouseY < y + SIZE * 2 * scale
        val color = if (hovered) CommonColors.WHITE.toInt() else CommonColors.WHITE.toInt(70)
        RenderUtils.drawRect(0.0, 0.0, (SIZE * 2).toDouble(), (SIZE * 2).toDouble(), color)
    }

    override fun mousePressed(mc: Minecraft, mouseX: Int, mouseY: Int): Boolean {
        val sr = ScaledResolution(mc)
        val minecraftScale = sr.scaleFactor.toFloat()
        val floatMouseX = Mouse.getX() / minecraftScale
        val floatMouseY = (mc.displayHeight - Mouse.getY()) / minecraftScale
        return hovered
    }

    enum class Corner {
        TOP_LEFT, TOP_RIGHT, BOTTOM_RIGHT, BOTTOM_LEFT
    }

    companion object {
        const val SIZE = 2
    }
}