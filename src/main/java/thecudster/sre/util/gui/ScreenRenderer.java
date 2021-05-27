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

package thecudster.sre.util.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import thecudster.sre.util.gui.colours.CustomColor;

import java.awt.*;
import java.util.Arrays;

/**
 * Taken from Wynntils under GNU Affero General Public License v3.0
 * https://github.com/Wynntils/Wynntils/blob/development/LICENSE
 * @author Wynntils
 */
public class ScreenRenderer {

    public static SmartFontRenderer fontRenderer = new SmartFontRenderer();
    public static Minecraft mc = Minecraft.getMinecraft();
    public static ScaledResolution screen = null;

    public static float getScale() {
        float scale = 1.0f;
        return scale; }
    /** refresh
     * Triggered by a slower loop(client tick), refresh
     * updates the screen resolution to match the window
     * size and sets the font renderer in until its ok.
     * Do not call this method from anywhere in the mod!
     */
    public static void refresh() {
        screen = new ScaledResolution(mc);
    }

    public void color(CustomColor color) {
        color.applyColor();
    }

    public void color(float r, float g, float b, float alpha) {
        GlStateManager.color(r, g, b, alpha);
    }
}