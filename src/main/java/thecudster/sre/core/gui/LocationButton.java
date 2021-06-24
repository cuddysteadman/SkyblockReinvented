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
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import thecudster.sre.core.gui.GuiElement;
import thecudster.sre.core.gui.RenderUtils;

import java.awt.*;

public class LocationButton extends GuiButton {
    public static GuiElement lastHoveredElement = null;
    public float x;
    public float y;
    public float x2;
    public float y2;
    private double scale;
    public GuiElement element;

    public LocationButton(GuiElement element) {
        super(-1, 0, 0, null);
        this.element = element;
        this.x = this.element.getActualX() - 4;
        this.y = this.element.getActualY() - 4;
        this.x2 = this.x + this.element.getActualWidth() + 6;
        this.y2 = this.y + this.element.getActualHeight() + 6;
    }

    public LocationButton(int buttonId, GuiElement element) {
        super(-1, 0, 0, null);
        this.element = element;
    }

    private void refreshLocations() {
        this.x = this.element.getActualX() - 4;
        this.y = this.element.getActualY() - 4;
        this.x2 = this.x + this.element.getActualWidth() + 6;
        this.y2 = this.y + this.element.getActualHeight() + 6;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        refreshLocations();
        hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x2 && mouseY < this.y2;
        Color c = new Color(255,255,255, hovered ? 100 : 40);
        RenderUtils.drawRect(0, 0, this.element.getWidth() + 4, this.element.getHeight() + 4, c.getRGB());
        GlStateManager.translate(2, 2, 0);
        this.element.demoRender();
        GlStateManager.translate(-2, -2, 0);
        if (hovered) {
            lastHoveredElement = element;
        }
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        return this.enabled && this.visible && hovered;
    }

    /**
     * get rid of clicking noise
     */
    @Override
    public void playPressSound(SoundHandler soundHandlerIn) {}

    public GuiElement getElement() {
        return this.element;
    }
}
