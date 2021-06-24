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

package thecudster.sre.features.impl.skills.bestiary;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.core.gui.FloatPair;
import thecudster.sre.core.gui.GuiElement;
import thecudster.sre.core.gui.ScreenRenderer;
import thecudster.sre.core.gui.SmartFontRenderer;
import thecudster.sre.core.gui.colours.CommonColors;
import thecudster.sre.util.Utils;

import static thecudster.sre.features.impl.skills.bestiary.BestiaryProgress.current;

public class BestiaryGUI {
    private static final Minecraft mc = Minecraft.getMinecraft();
    static {
        new BestiaryInfo();
    }
    public static class BestiaryInfo extends GuiElement {
        public BestiaryInfo() {
            super("Bestiary Info", new FloatPair(0.004687f, 0.25639135f));
            SkyblockReinvented.GUIMANAGER.registerElement(this);
        }
        @Override
        public void render() {
            EntityPlayerSP player = mc.thePlayer;
            ScaledResolution sr = new ScaledResolution(mc);
            if (this.getToggled() && player != null && mc.theWorld != null && !Utils.inDungeons && Utils.inSkyblock && BestiaryProgress.secondsSinceKill < 10) {
                boolean leftAlign = getActualX() < sr.getScaledWidth() / 2f;
                for (int i = 0; i < 2; i++) {
                    SmartFontRenderer.TextAlignment alignment = leftAlign ? SmartFontRenderer.TextAlignment.LEFT_RIGHT : SmartFontRenderer.TextAlignment.RIGHT_LEFT;
                    ScreenRenderer.fontRenderer.drawString(current[i], leftAlign ? this.getActualX() : this.getActualX() + this.getWidth(), this.getActualY() + i * ScreenRenderer.fontRenderer.FONT_HEIGHT, CommonColors.WHITE, alignment, SmartFontRenderer.TextShadow.NORMAL);
                }
            }
        }

        @Override
        public void demoRender() {
            ScaledResolution sr = new ScaledResolution(mc);
                boolean leftAlign = getActualX() < sr.getScaledWidth() / 2f;
                for (int i = 0; i < 2; i++) {
                    SmartFontRenderer.TextAlignment alignment = leftAlign ? SmartFontRenderer.TextAlignment.LEFT_RIGHT : SmartFontRenderer.TextAlignment.RIGHT_LEFT;
                    ScreenRenderer.fontRenderer.drawString(current[i], leftAlign ? 0 : this.getActualWidth(), i * ScreenRenderer.fontRenderer.FONT_HEIGHT, CommonColors.WHITE, alignment, SmartFontRenderer.TextShadow.NORMAL);
                }
        }

        @Override
        public boolean getToggled() {
            return SkyblockReinvented.config.bestiaryInfo;
        }

        @Override
        public int getHeight() {
            return ScreenRenderer.fontRenderer.FONT_HEIGHT * 2;
        }

        @Override
        public int getWidth() {
            return ScreenRenderer.fontRenderer.getStringWidth(EnumChatFormatting.RED +
                    "Kills to next level: " + EnumChatFormatting.GOLD + "Not detected yet!");
        }
    }
}
