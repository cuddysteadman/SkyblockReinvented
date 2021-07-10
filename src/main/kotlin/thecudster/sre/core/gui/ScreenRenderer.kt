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

import thecudster.sre.core.gui.colours.CustomColor
import net.minecraft.client.renderer.GlStateManager
import thecudster.sre.core.gui.SmartFontRenderer
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import thecudster.sre.core.gui.ScreenRenderer

/**
 * Taken from Wynntils under GNU Affero General Public License v3.0
 * https://github.com/Wynntils/Wynntils/blob/development/LICENSE
 * @author Wynntils
 */
class ScreenRenderer {
    fun color(color: CustomColor) {
        color.applyColor()
    }

    fun color(r: Float, g: Float, b: Float, alpha: Float) {
        GlStateManager.color(r, g, b, alpha)
    }

    companion object {
        @JvmField
        var fontRenderer = SmartFontRenderer()
        var mc = Minecraft.getMinecraft()
        var screen: ScaledResolution? = null
        val scale: Float
            get() = 1.0f

        /** refresh
         * Triggered by a slower loop(client tick), refresh
         * updates the screen resolution to match the window
         * size and sets the font renderer in until its ok.
         * Do not call this method from anywhere in the mod!
         */
        @JvmStatic
        fun refresh() {
            screen = ScaledResolution(mc)
        }
    }
}