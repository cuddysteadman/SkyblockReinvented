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

package thecudster.sre.util.sbutil;

import net.minecraft.util.EnumChatFormatting;

import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Taken from Skyblockcatia under MIT License
 * Modified
 * https://github.com/SteveKunG/SkyBlockcatia/blob/1.8.9/LICENSE.md
 * @author SteveKunG
 */
public enum ItemRarity {
    COMMON("COMMON", EnumChatFormatting.WHITE, new Color(255,255,255)),
    UNCOMMON("UNCOMMON", EnumChatFormatting.GREEN, new Color(85,255,85)),
    RARE("RARE", EnumChatFormatting.BLUE, new Color(85,85,255)),
    EPIC("EPIC", EnumChatFormatting.DARK_PURPLE, new Color(190,0,190)),
    LEGENDARY("LEGENDARY", EnumChatFormatting.GOLD, new Color(255,170,0)),
    MYTHIC("MYTHIC", EnumChatFormatting.LIGHT_PURPLE, new Color(255,85,255)),
    SUPREME("SUPREME", EnumChatFormatting.DARK_RED, new Color(170,0,0)),
    SPECIAL("SPECIAL", EnumChatFormatting.RED, new Color(255,85,85)),
    VERY_SPECIAL("VERY SPECIAL", EnumChatFormatting.RED, new Color(170,0,0));

    private static final ItemRarity[] VALUES = Arrays.stream(values()).sorted(Comparator.comparingInt(ItemRarity::ordinal)).toArray(size -> new ItemRarity[size]);
    private final String name;
    private final EnumChatFormatting baseColor;
    public final Color color;

    static {
        for (ItemRarity rarity : values())
        {
            VALUES[rarity.ordinal()] = rarity;
        }
    }

    ItemRarity(String name, EnumChatFormatting baseColor, Color color) {
        this.name = name;
        this.baseColor = baseColor;
        this.color = color;
    }

    public String getName() {
        return this.name;
    }

    public EnumChatFormatting getBaseColor() {
        return this.baseColor;
    }

    public Color getColor() {
        return this.color;
    }

    public static ItemRarity byBaseColor(String color) {
        for (ItemRarity rarity : values())
        {
            if (rarity.baseColor.toString().equals(color))
            {
                return rarity;
            }
        }
        return null;
    }

    public ItemRarity getNextRarity() {
        return VALUES[(this.ordinal() + 1) % VALUES.length];
    }
}