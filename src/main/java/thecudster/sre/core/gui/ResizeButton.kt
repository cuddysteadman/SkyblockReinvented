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

package thecudster.sre.core.gui;
/**
 * Taken from Skytils under GNU Affero General Public license.
 * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
 * @author My-Name-Is-Jeff
 * @author Sychic
 */
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;
import thecudster.sre.core.gui.GuiElement;
import thecudster.sre.core.gui.RenderUtils;
import thecudster.sre.core.gui.colours.CommonColors;

public class ResizeButton extends GuiButton {
    public static final int SIZE = 2;

    public float x;
    public float y;

    private Corner corner;

    public GuiElement element;

    public ResizeButton(float x, float y, GuiElement element, Corner corner) {
        super(-1, 0, 0, null);
        this.element = element;
        this.corner = corner;
        this.x = x;
        this.y = y;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        float scale = element.getScale();
        hovered = mouseX >= x && mouseY >= y && mouseX < x + SIZE * 2 * scale && mouseY < y + SIZE * 2 * scale;
        int color = hovered ? CommonColors.WHITE.toInt() : CommonColors.WHITE.toInt(70);
        RenderUtils.drawRect(0,0, SIZE * 2, SIZE * 2, color);
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        ScaledResolution sr = new ScaledResolution(mc);
        float minecraftScale = sr.getScaleFactor();
        float floatMouseX = Mouse.getX() / minecraftScale;
        float floatMouseY = (mc.displayHeight - Mouse.getY()) / minecraftScale;

        return hovered;
    }

    public Corner getCorner() {
        return this.corner;
    }

    public enum Corner {
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_RIGHT,
        BOTTOM_LEFT
    }
}
