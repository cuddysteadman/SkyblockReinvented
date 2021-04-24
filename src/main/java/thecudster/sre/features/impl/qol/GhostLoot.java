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

package thecudster.sre.features.impl.qol;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.gui.*;
import thecudster.sre.util.gui.colours.CommonColors;
import thecudster.sre.util.sbutil.CurrentLoc;
import thecudster.sre.util.sbutil.LootTracker;

public class GhostLoot {
    static {
        new GhostGUI();
    }
    public static class GhostGUI extends GuiElement {
        public GhostGUI() {
            super("Ghost GUI", new FloatPair(0, 0));
            SkyblockReinvented.GUIMANAGER.registerElement(this);
        }
        @Override
        public void render() {
            if (!this.getToggled()) { return; }
            ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
            boolean leftAlign = getActualX() < sr.getScaledWidth() / 2f;
            for (int i = 0; i < LootTracker.display.length; i++) {
                SmartFontRenderer.TextAlignment alignment = leftAlign ? SmartFontRenderer.TextAlignment.LEFT_RIGHT : SmartFontRenderer.TextAlignment.RIGHT_LEFT;
                ScreenRenderer.fontRenderer.drawString(LootTracker.display[i], leftAlign ? this.getActualX() : this.getActualX() + this.getWidth(), this.getActualY() + i * ScreenRenderer.fontRenderer.FONT_HEIGHT, CommonColors.WHITE, alignment, SmartFontRenderer.TextShadow.NORMAL);
            }
        }

        @Override
        public void demoRender() {
            ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
            boolean leftAlign = getActualX() < sr.getScaledWidth() / 2f;
            if (!this.getToggled()) { return; }
            for (int i = 0; i < LootTracker.display.length; i++) {
                SmartFontRenderer.TextAlignment alignment = leftAlign ? SmartFontRenderer.TextAlignment.LEFT_RIGHT : SmartFontRenderer.TextAlignment.RIGHT_LEFT;
                ScreenRenderer.fontRenderer.drawString(LootTracker.display[i], leftAlign ? 0 : this.getActualWidth(), i * ScreenRenderer.fontRenderer.FONT_HEIGHT, CommonColors.WHITE, alignment, SmartFontRenderer.TextShadow.NORMAL);
            }
        }

        @Override
        public boolean getToggled() {
            return SkyblockReinvented.config.ghostTracker;
        }

        @Override
        public int getHeight() {
            return ScreenRenderer.fontRenderer.FONT_HEIGHT * 6;
        }

        @Override
        public int getWidth() {
            return ScreenRenderer.fontRenderer.getStringWidth(LootTracker.display[5]);
        }
    }
    @SubscribeEvent
    public void onDeath(LivingDeathEvent event) {
        if (event.entity instanceof EntityCreeper) {
            if (CurrentLoc.currentLoc.equals("The Mist")) {
                EntityCreeper creeper = (EntityCreeper) event.entity;
                if (creeper.getPowered()) {
                    LootTracker.ghostsSinceSorrow++;
                }
            }
        }
    }
}
