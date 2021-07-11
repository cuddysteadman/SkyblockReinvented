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

import java.util.*

/**
 * Taken from Wynntils under GNU AGPL v3.0
 * https://github.com/Wynntils/Wynntils/blob/development/LICENSE
 * @author Wynntils
 */
class ColorSet<T : CustomColor?>(private val colors: Array<T>, names: Array<String>) {
    private val names: Array<String?>
    private val nameMap: MutableMap<String, T?>

    /**
     * Returns the colour code for a CustomColor if it is in the set, -1 if it isn't.
     */
    fun getCode(c: CustomColor?): Int {
        if (c == null) return -1
        for (i in colors.indices) {
            if (colors[i] == c) {
                return i
            }
        }
        return -1
    }

    /**
     * Return the name colour in the set, or `null` if `c` isn't in the set.
     */
    fun getName(c: CustomColor?): String? {
        val code = getCode(c)
        return if (code == -1) null else names[code]
    }

    /**
     * Shorthand for `getName(fromCode(code))`
     */
    fun getName(code: Int): String? {
        return if (code < 0 || names.size <= code) null else names[code]
    }

    /**
     * Return the colour in the set corresponding to the name given
     */
    fun fromName(name: String?): T? {
        var name = name ?: return null
        name = name.trim { it <= ' ' }.replace(' ', '_').replace("_", "").toUpperCase(Locale.ROOT)
        return nameMap.getOrDefault(name, null)
    }

    fun has(name: String?): Boolean {
        return fromName(name) != null
    }

    fun has(code: Int): Boolean {
        return 0 <= code && code < colors.size
    }

    fun has(c: CustomColor?): Boolean {
        return getCode(c) != -1
    }

    /**
     * `size() - 1` is the maximum value for `fromCode`.
     *
     * @return The number of colours in the set
     */
    fun size(): Int {
        return colors.size
    }

    /**
     * @return the colours as integers
     */
    fun asInts(): IntArray {
        val colors = IntArray(colors.size)
        for (i in colors.indices) {
            colors[i] = this.colors[i]!!.toInt()
        }
        return colors
    }

    init {
        this.names = arrayOfNulls(names.size)
        assert(colors.size == this.names.size)
        nameMap = HashMap(colors.size)
        for (i in colors.indices) {
            val name = names[i].trim { it <= ' ' }.replace(' ', '_').toUpperCase(Locale.ROOT)
            nameMap[name.replace("_", "")] = colors[i]
            this.names[i] = name
        }
    }
}