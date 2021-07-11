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
package thecudster.sre.core.gui.structure.colours

import net.minecraft.client.renderer.GlStateManager
import java.util.*

/** CustomColor
 * will represent color or complex colors
 * in a more efficient way than awt's Color or minecraft's color ints.
 *
 * Taken from Wynntils under GNU Affero General Public License v3.0
 * https://github.com/Wynntils/Wynntils/blob/development/LICENSE
 * @author Wynntils
 */
open class CustomColor(
    var r: Float, // The RED   value of the color(0.0f -> 1.0f)
    var g: Float, // The GREEN value of the color(0.0f -> 1.0f)
    var b: Float, // The BLUE  value of the color(0.0f -> 1.0f)
    var a // The ALPHA value of the color(0.0f -> 1.0f)
    : Float
) {
    /** applyColor
     * Will set the color to OpenGL's active color
     */
    fun applyColor() {
        GlStateManager.color(r, g, b, a)
    }

    open fun setA(a: Float): CustomColor {
        this.a = a
        return this
    }

    /**
     * `c.toInt() & 0xFFFFFF` to get `0xRRGGBB` (without alpha)
     *
     * @return 0xAARRGGBB
     */
    fun toInt(): Int {
        val r = (Math.min(r, 1f) * 255).toInt()
        val g = (Math.min(g, 1f) * 255).toInt()
        val b = (Math.min(b, 1f) * 255).toInt()
        val a = (Math.min(a, 1f) * 255).toInt()
        return a shl 24 or (r shl 16) or (g shl 8) or b
    }

    fun toInt(alpha: Int): Int {
        val r = (Math.min(r, 1f) * 255).toInt()
        val g = (Math.min(g, 1f) * 255).toInt()
        val b = (Math.min(b, 1f) * 255).toInt()
        val a = Math.min(alpha, 255)
        return a shl 24 or (r shl 16) or (g shl 8) or b
    }

    /** this is = rgba(255,255,255,255)  */
    override fun toString(): String {
        return "rgba(" + (r * 255).toInt() + "," + (g * 255).toInt() + "," + (b * 255).toInt() + "," + (a * 255).toInt() + ")"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other is CustomColor) {
            val c = other
            return r == c.r && g == c.g && b == c.b && a == c.a
        }
        return false
    }

    override fun hashCode(): Int {
        return Arrays.hashCode(floatArrayOf(r, g, b, a))
    }

    open class SetBase : CustomColor {
        internal constructor(rgb: Int) : super(
            (rgb shr 16) / 255f,
            (rgb shr 8 and 0xFF) / 255f,
            (rgb and 0xFF) / 255f,
            1f
        ) {
        }

        internal constructor(r: Float, g: Float, b: Float, a: Float) : super(r, g, b, a) {}

        // Prevent setA on global references. Create a copy with `new CustomColor(c)` first.
        override fun setA(a: Float): CustomColor {
            UnsupportedOperationException("Cannot set alpha of common color").printStackTrace()
            return this
        }
    }

    companion object {
        fun fromBytes(r: Byte, g: Byte, b: Byte, a: Float): CustomColor {
            return CustomColor(
                java.lang.Byte.toUnsignedInt(r) / 255f,
                java.lang.Byte.toUnsignedInt(g) / 255f,
                java.lang.Byte.toUnsignedInt(b) / 255f,
                a
            )
        }
    }
}