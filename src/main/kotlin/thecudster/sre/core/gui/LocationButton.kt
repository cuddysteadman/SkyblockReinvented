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

import net.minecraft.client.gui.GuiButton
import thecudster.sre.core.gui.GuiElement
import net.minecraft.client.Minecraft
import thecudster.sre.core.gui.RenderUtils
import net.minecraft.client.renderer.GlStateManager
import thecudster.sre.core.gui.LocationButton
import net.minecraft.client.audio.SoundHandler
import java.awt.Color

/**
 * Taken from Skytils under GNU Affero General Public license.
 * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
 * @author My-Name-Is-Jeff
 * @author Sychic
 */
class LocationButton : GuiButton {
    @JvmField
    var x = 0f
    @JvmField
    var y = 0f
    @JvmField
    var x2 = 0f
    @JvmField
    var y2 = 0f
    private val scale = 0.0
    @JvmField
    var element: GuiElement

    constructor(element: GuiElement) : super(-1, 0, 0, null) {
        this.element = element
        x = this.element.actualX - 4
        y = this.element.actualY - 4
        x2 = x + this.element.actualWidth + 6
        y2 = y + this.element.actualHeight + 6
    }

    constructor(buttonId: Int, element: GuiElement) : super(-1, 0, 0, null) {
        this.element = element
    }

    private fun refreshLocations() {
        x = element.actualX - 4
        y = element.actualY - 4
        x2 = x + element.actualWidth + 6
        y2 = y + element.actualHeight + 6
    }

    override fun drawButton(mc: Minecraft, mouseX: Int, mouseY: Int) {
        refreshLocations()
        hovered = mouseX >= x && mouseY >= y && mouseX < x2 && mouseY < y2
        val c = Color(255, 255, 255, if (hovered) 100 else 40)
        RenderUtils.drawRect(0.0, 0.0, (element.width + 4).toDouble(), (element.height + 4).toDouble(), c.rgb)
        GlStateManager.translate(2f, 2f, 0f)
        element.demoRender()
        GlStateManager.translate(-2f, -2f, 0f)
        if (hovered) {
            lastHoveredElement = element
        }
    }

    override fun mousePressed(mc: Minecraft, mouseX: Int, mouseY: Int): Boolean {
        return enabled && visible && hovered
    }

    /**
     * get rid of clicking noise
     */
    override fun playPressSound(soundHandlerIn: SoundHandler) {}

    companion object {
        @JvmField
        var lastHoveredElement: GuiElement? = null
    }
}