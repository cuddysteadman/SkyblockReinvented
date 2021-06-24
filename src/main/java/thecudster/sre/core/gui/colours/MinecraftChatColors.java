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

package thecudster.sre.core.gui.colours;
/**
 * Taken from Skytils under GNU Affero General Public license.
 * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
 * @author My-Name-Is-Jeff
 * @author Sychic
 */
public class MinecraftChatColors extends CustomColor.SetBase {
    private MinecraftChatColors(int rgb) {
        super(rgb);
    }

    public static final MinecraftChatColors BLACK = new MinecraftChatColors(0x000000);
    public static final MinecraftChatColors DARK_BLUE = new MinecraftChatColors(0x0000AA);
    public static final MinecraftChatColors DARK_GREEN = new MinecraftChatColors(0x00AA00);
    public static final MinecraftChatColors DARK_AQUA = new MinecraftChatColors(0x00AAAA);
    public static final MinecraftChatColors DARK_RED = new MinecraftChatColors(0xAA0000);
    public static final MinecraftChatColors DARK_PURPLE = new MinecraftChatColors(0xAA00AA);
    public static final MinecraftChatColors GOLD = new MinecraftChatColors(0xFFAA00);
    public static final MinecraftChatColors GRAY = new MinecraftChatColors(0xAAAAAA);
    public static final MinecraftChatColors DARK_GRAY = new MinecraftChatColors(0x555555);
    public static final MinecraftChatColors BLUE = new MinecraftChatColors(0x5555FF);
    public static final MinecraftChatColors GREEN = new MinecraftChatColors(0x55FF55);
    public static final MinecraftChatColors AQUA = new MinecraftChatColors(0x55FFFF);
    public static final MinecraftChatColors RED = new MinecraftChatColors(0xFF5555);
    public static final MinecraftChatColors LIGHT_PURPLE = new MinecraftChatColors(0xFF55FF);
    public static final MinecraftChatColors YELLOW = new MinecraftChatColors(0xFFFF55);
    public static final MinecraftChatColors WHITE = new MinecraftChatColors(0xFFFFFF);

    private static final MinecraftChatColors[] colors = {
            BLACK,     DARK_BLUE,    DARK_GREEN, DARK_AQUA,
            DARK_RED,  DARK_PURPLE,  GOLD,       GRAY,
            DARK_GRAY, BLUE,         GREEN,      AQUA,
            RED,       LIGHT_PURPLE, YELLOW,     WHITE
    };

    private static final String[] names = {
            "BLACK",     "DARK_BLUE",    "DARK_GREEN", "DARK_AQUA",
            "DARK_RED",  "DARK_PURPLE",  "GOLD",       "GRAY",
            "DARK_GRAY", "BLUE",         "GREEN",      "AQUA",
            "RED",       "LIGHT_PURPLE", "YELLOW",     "WHITE"
    };

    public static final ColorSet<MinecraftChatColors> set = new ColorSet<>(colors, names);
}
