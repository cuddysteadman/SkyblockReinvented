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

import net.minecraft.client.renderer.GlStateManager;

import java.util.Arrays;

/** CustomColor
 * will represent color or complex colors
 * in a more efficient way than awt's Color or minecraft's color ints.
 *
 * Taken from Wynntils under GNU Affero General Public License v3.0
 * https://github.com/Wynntils/Wynntils/blob/development/LICENSE
 * @author Wynntils
 */
public class CustomColor {
    public float
            r,  // The RED   value of the color(0.0f -> 1.0f)
            g,  // The GREEN value of the color(0.0f -> 1.0f)
            b,  // The BLUE  value of the color(0.0f -> 1.0f)
            a;  // The ALPHA value of the color(0.0f -> 1.0f)

    public CustomColor(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    /** applyColor
     * Will set the color to OpenGL's active color
     */
    public void applyColor() {
        GlStateManager.color(r, g, b, a);
    }

    public CustomColor setA(float a) {
        this.a = a;
        return this;
    }

    /**
     * `c.toInt() & 0xFFFFFF` to get `0xRRGGBB` (without alpha)
     *
     * @return 0xAARRGGBB
     */
    public int toInt() {
        int r = (int) (Math.min(this.r, 1f) * 255);
        int g = (int) (Math.min(this.g, 1f) * 255);
        int b = (int) (Math.min(this.b, 1f) * 255);
        int a = (int) (Math.min(this.a, 1f) * 255);
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    public int toInt(int alpha) {
        int r = (int) (Math.min(this.r, 1f) * 255);
        int g = (int) (Math.min(this.g, 1f) * 255);
        int b = (int) (Math.min(this.b, 1f) * 255);
        int a = Math.min(alpha, 255);
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    public static CustomColor fromBytes(byte r, byte g, byte b, float a) {
        return new CustomColor(Byte.toUnsignedInt(r) / 255f, Byte.toUnsignedInt(g) / 255f, Byte.toUnsignedInt(b) / 255f, a);
    }

    /** this is = rgba(255,255,255,255) **/
    @Override
    public String toString() {
        return "rgba(" + (int)(r*255) + "," + (int)(g*255) + "," + (int)(b*255) + "," + (int)(a*255) +")";
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;

        if (other instanceof CustomColor) {
            CustomColor c = (CustomColor) other;
            return r == c.r && g == c.g && b == c.b && a == c.a;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new float[]{ r, g, b, a });
    }

    public static class SetBase extends CustomColor {
        SetBase(int rgb) {
            super((rgb >> 16) / 255.f, ((rgb >> 8) & 0xFF) / 255.f, (rgb & 0xFF) / 255.f, 1);
        }

        SetBase(float r, float g, float b, float a) {
            super(r, g, b, a);
        }

        // Prevent setA on global references. Create a copy with `new CustomColor(c)` first.
        @Override public CustomColor setA(float a) {
            new UnsupportedOperationException("Cannot set alpha of common color").printStackTrace();
            return this;
        }
    }

}