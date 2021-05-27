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

package thecudster.sre.util.gui.colours;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Taken from Wynntils under GNU AGPL v3.0
 * https://github.com/Wynntils/Wynntils/blob/development/LICENSE
 * @author Wynntils
 */
public class ColorSet<T extends CustomColor> {
    private final T[] colors;
    private final String[] names;
    private final Map<String, T> nameMap;

    public ColorSet(T[] colors, String[] names) {
        this.colors = colors;
        this.names = new String[names.length];

        assert this.colors.length == this.names.length;

        nameMap = new HashMap<>(colors.length);
        for (int i = 0; i < colors.length; ++i) {
            String name = names[i].trim().replace(' ', '_').toUpperCase(Locale.ROOT);
            nameMap.put(name.replace("_", ""), this.colors[i]);
            this.names[i] = name;
        }
    }

    /**
     * Returns the colour code for a CustomColor if it is in the set, -1 if it isn't.
     */
    public int getCode(CustomColor c) {
        if (c == null) return -1;

        for (int i = 0; i < colors.length; ++i) {
            if (colors[i].equals(c)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Return the name colour in the set, or `null` if `c` isn't in the set.
     */
    public String getName(CustomColor c) {
        int code = getCode(c);
        if (code == -1) return null;
        return names[code];
    }

    /**
     * Shorthand for `getName(fromCode(code))`
     */
    public String getName(int code) {
        if (code < 0 || names.length <= code) return null;
        return names[code];
    }

    /**
     * Return the colour in the set corresponding to the name given
     */
    public T fromName(String name) {
        if (name == null) return null;

        name = name.trim().replace(' ', '_').replace("_", "").toUpperCase(Locale.ROOT);
        return nameMap.getOrDefault(name, null);
    }

    public boolean has(String name) {
        return fromName(name) != null;
    }

    public boolean has(int code) {
        return 0 <= code && code < colors.length;
    }

    public boolean has(CustomColor c) {
        return getCode(c) != -1;
    }

    /**
     * `size() - 1` is the maximum value for `fromCode`.
     *
     * @return The number of colours in the set
     */
    public int size() {
        return colors.length;
    }

    /**
     * @return the colours as integers
     */
    public int[] asInts() {
        int[] colors = new int[this.colors.length];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = this.colors[i].toInt();
        }
        return colors;
    }

}