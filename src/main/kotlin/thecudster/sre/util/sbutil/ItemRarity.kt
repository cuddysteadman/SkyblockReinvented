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
package thecudster.sre.util.sbutil

import net.minecraft.util.EnumChatFormatting
import java.awt.Color
import java.util.*

/**
 * Taken from Skyblockcatia under MIT License
 * Kotlin taken from Skytils under GNU Affero General Public license.
 * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
 * https://github.com/SteveKunG/SkyBlockcatia/blob/1.8.9/LICENSE.md
 * @author SteveKunG
 * @author My-Name-Is-Jeff
 * @author Sychic
 */
enum class ItemRarity(val rarityName: String, val baseColor: EnumChatFormatting, val color: Color) {
    COMMON("COMMON", EnumChatFormatting.WHITE, Color(255, 255, 255)), UNCOMMON(
        "UNCOMMON",
        EnumChatFormatting.GREEN,
        Color(85, 255, 85)
    ),
    RARE("RARE", EnumChatFormatting.BLUE, Color(85, 85, 255)), EPIC(
        "EPIC",
        EnumChatFormatting.DARK_PURPLE,
        Color(190, 0, 190)
    ),
    LEGENDARY("LEGENDARY", EnumChatFormatting.GOLD, Color(255, 170, 0)), MYTHIC(
        "MYTHIC",
        EnumChatFormatting.LIGHT_PURPLE,
        Color(255, 85, 255)
    ),
    SUPREME("SUPREME", EnumChatFormatting.DARK_RED, Color(170, 0, 0)), SPECIAL(
        "SPECIAL",
        EnumChatFormatting.RED,
        Color(255, 85, 85)
    ),
    VERY_SPECIAL("VERY SPECIAL", EnumChatFormatting.RED, Color(170, 0, 0));

    companion object {
        private val VALUES: Array<ItemRarity> =
            Arrays.stream(values()).sorted(Comparator.comparingInt { obj: ItemRarity -> obj.ordinal })
                .toArray { size: Int -> arrayOfNulls(size) }

        fun byBaseColor(color: String): ItemRarity? {
            for (rarity in values()) {
                if (rarity.baseColor.toString() == color) {
                    return rarity
                }
            }
            return null
        }

        init {
            for (rarity in values()) {
                values().forEach { rarity -> VALUES[rarity.ordinal] = rarity }
            }
        }
    }

    val nextRarity: ItemRarity
        get() = VALUES[(ordinal + 1) % VALUES.size]
}