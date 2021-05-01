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
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.events.SecondPassedEvent;
import thecudster.sre.util.gui.*;
import thecudster.sre.util.gui.colours.CommonColors;
import thecudster.sre.util.sbutil.Utils;

public class JerryTimer {
    public static int seconds = 360;
    public static  String display = EnumChatFormatting.LIGHT_PURPLE + "Jerry: " + "6:00";
    private static final Minecraft mc = Minecraft.getMinecraft();
    @SubscribeEvent
    public void onSeconds(SecondPassedEvent event) {
        if (!SkyblockReinvented.config.jerry) { return; }
        if (seconds < 360 && seconds > 0) {
            seconds--;
        }
        if (seconds == 0) {
            display = EnumChatFormatting.LIGHT_PURPLE + "Jerry: " + "Ready!";
            return;
        }
        int secondsDisplay = seconds % 60;
        if (("" + seconds % 60).length() == 1) {
            display = EnumChatFormatting.LIGHT_PURPLE + "Jerry: " + seconds / 60 + ":0" + secondsDisplay;
        } else {
            display = EnumChatFormatting.LIGHT_PURPLE + "Jerry: " + seconds / 60 + ":" + secondsDisplay;
        }

    }
    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if (!SkyblockReinvented.config.jerry) { return; }
        String jerry = StringUtils.stripControlCodes(event.message.getUnformattedText());
        if (jerry.contains("You found a Green Jerry!") || jerry.contains("You found a Blue Jerry!") ||
                jerry.contains("You found a Purple Jerry!") || jerry.contains("You found a Golden Jerry!")) {
            seconds = 359;
        }
    }
    static {
        new JerryTimerGUI();
    }
    public static class JerryTimerGUI extends GuiElement {
        public JerryTimerGUI() {
            super("Jerry Timer", new FloatPair(0, 5));
            SkyblockReinvented.GUIMANAGER.registerElement(this);
        }

        @Override
        public void render() {
            ScaledResolution sr = new ScaledResolution(mc);
            if (this.getToggled() && Minecraft.getMinecraft().thePlayer != null && mc.theWorld != null) {
                RenderUtil.renderTexture(new ResourceLocation("sre:gui/jerry.png"), (int) getActualX(), (int) getActualY());
                ScreenRenderer.fontRenderer.drawString(display, getActualX() + 20,getActualY() + 5, CommonColors.WHITE, SmartFontRenderer.TextAlignment.LEFT_RIGHT, SmartFontRenderer.TextShadow.NORMAL);
            }
        }
        @Override
        public void demoRender() {
            float x = 0;
            float y = 0;
            RenderUtil.renderTexture(new ResourceLocation("sre:gui/jerry.png"), (int) x, (int) y);
            ScreenRenderer.fontRenderer.drawString(display, x + 20, y + 5, CommonColors.WHITE, SmartFontRenderer.TextAlignment.LEFT_RIGHT, SmartFontRenderer.TextShadow.NORMAL);
        }

        @Override
        public boolean getToggled() {
            return Utils.inSkyblock && !Utils.inDungeons && SkyblockReinvented.config.jerry;
        }

        @Override
        public int getHeight() {
            return 16;
        }

        @Override
        public int getWidth() {
            return 12 + ScreenRenderer.fontRenderer.getStringWidth(display);
        }
    }
}
