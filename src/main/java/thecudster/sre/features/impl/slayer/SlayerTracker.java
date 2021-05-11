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

package thecudster.sre.features.impl.slayer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.gui.FloatPair;
import thecudster.sre.util.gui.GuiElement;
import thecudster.sre.util.gui.ScreenRenderer;
import thecudster.sre.util.gui.SmartFontRenderer;
import thecudster.sre.util.gui.colours.CommonColors;
import thecudster.sre.util.sbutil.Utils;

public class SlayerTracker {
    public static String rngesusMeter = EnumChatFormatting.LIGHT_PURPLE + "Not detected yet!";
    public static String xpLeft = EnumChatFormatting.LIGHT_PURPLE + "Not detected yet!";
    public static String currentSlayer = EnumChatFormatting.LIGHT_PURPLE + "Not detected yet!";
    public static String current = EnumChatFormatting.LIGHT_PURPLE + "Not detected yet!";
    public static String[] displayText = {EnumChatFormatting.GREEN + "XP Until Next Level: " + EnumChatFormatting.LIGHT_PURPLE + "Not detected yet!",
            EnumChatFormatting.GREEN + "RNGesus Meter: " + EnumChatFormatting.LIGHT_PURPLE + "Not detected yet!",
            EnumChatFormatting.GREEN + "Current Slayer: " + EnumChatFormatting.LIGHT_PURPLE + "Not detected yet!"};
    private final int nxtLvl = " - Next LVL in ".length();
    private static final Minecraft mc = Minecraft.getMinecraft();
    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent event) {
        displayText[0] = EnumChatFormatting.GREEN + "XP Until Next Level: " + xpLeft;
        displayText[1] = EnumChatFormatting.GREEN + "RNGesus Meter: " + rngesusMeter;
        displayText[2] = EnumChatFormatting.GREEN + "Current Slayer: " + currentSlayer;
    }
    @SubscribeEvent(receiveCanceled = true, priority = EventPriority.HIGHEST)
    public void onChat(ClientChatReceivedEvent event) {
        if (SkyblockReinvented.config.slayerInfo) {
            String unformatted = event.message.getUnformattedText();
            String meterText = "RNGesus Meter: -------------------- ";
            if (unformatted.contains(meterText)) {
                unformatted = EnumChatFormatting.LIGHT_PURPLE + unformatted.substring(meterText.length() + 3);
                SlayerTracker.rngesusMeter = unformatted;
                event.setCanceled(true);
                return;
            }
            String xpLeftRev = "   Zombie Slayer LVL ";
            if (unformatted.contains(xpLeftRev)) {
                SlayerTracker.currentSlayer = EnumChatFormatting.LIGHT_PURPLE + "Revenant";
                unformatted = unformatted.substring(xpLeftRev.length() + nxtLvl + 1, unformatted.length() - 1);
                SlayerTracker.xpLeft = EnumChatFormatting.LIGHT_PURPLE + unformatted;
                event.setCanceled(true);
                return;
            }
            String xpLeftTara = "   Spider Slayer LVL ";
            if (unformatted.contains(xpLeftTara)) {
                SlayerTracker.currentSlayer = EnumChatFormatting.LIGHT_PURPLE + "Tarantula";
                unformatted = unformatted.substring(xpLeftTara.length() + nxtLvl + 1, unformatted.length() - 1);
                SlayerTracker.xpLeft = EnumChatFormatting.LIGHT_PURPLE + unformatted;
                event.setCanceled(true);
                return;
            }
            String xpLeftSven = "   Wolf Slayer LVL ";
            if (unformatted.contains(xpLeftSven)) {
                SlayerTracker.currentSlayer = EnumChatFormatting.LIGHT_PURPLE + "Sven";
                unformatted = unformatted.substring(xpLeftSven.length() + nxtLvl + 1, unformatted.length() - 1);
                SlayerTracker.xpLeft = EnumChatFormatting.LIGHT_PURPLE + unformatted;
                event.setCanceled(true);
                return;
            }
        }
    }
    static {
        new SlayerGuiElement();
    }
    public static class SlayerGuiElement extends GuiElement {
        public SlayerGuiElement() {
            super("Slayer XP", new FloatPair(0.00625f, 0.14626351f));
            SkyblockReinvented.GUIMANAGER.registerElement(this);
        }
        @Override
        public void render() {
            EntityPlayerSP player = mc.thePlayer;
            ScaledResolution sr = new ScaledResolution(mc);
            if (this.getToggled() && player != null && mc.theWorld != null) {
                boolean leftAlign = getActualX() < sr.getScaledWidth() / 2f;
                for (int i = 0; i < 3; i++) {
                    SmartFontRenderer.TextAlignment alignment = leftAlign ? SmartFontRenderer.TextAlignment.LEFT_RIGHT : SmartFontRenderer.TextAlignment.RIGHT_LEFT;
                    ScreenRenderer.fontRenderer.drawString(displayText[i], leftAlign ? this.getActualX() : this.getActualX() + this.getWidth(), this.getActualY() + i * ScreenRenderer.fontRenderer.FONT_HEIGHT, CommonColors.WHITE, alignment, SmartFontRenderer.TextShadow.NORMAL);
                }
            }
        }

        @Override
        public void demoRender() {
            ScaledResolution sr = new ScaledResolution(mc);
            if (Utils.inSkyblock && !Utils.inDungeons && SkyblockReinvented.config.slayerInfo) {
                boolean leftAlign = getActualX() < sr.getScaledWidth() / 2f;
                for (int i = 0; i < 3; i++) {
                    SmartFontRenderer.TextAlignment alignment = leftAlign ? SmartFontRenderer.TextAlignment.LEFT_RIGHT : SmartFontRenderer.TextAlignment.RIGHT_LEFT;
                    ScreenRenderer.fontRenderer.drawString(displayText[i], leftAlign ? 0 : this.getActualWidth(), i * ScreenRenderer.fontRenderer.FONT_HEIGHT, CommonColors.WHITE, alignment, SmartFontRenderer.TextShadow.NORMAL);
                }
            }
        }

        @Override
        public boolean getToggled() {
            return SkyblockReinvented.config.slayerInfo && Utils.inSkyblock && !Utils.inDungeons && Utils.inLoc(SlayerFeatures.slayerLocs);
        }

        @Override
        public int getHeight() {
            return ScreenRenderer.fontRenderer.FONT_HEIGHT * 3;
        }

        @Override
        public int getWidth() {
            return ScreenRenderer.fontRenderer.getStringWidth(displayText[0]);
        }
    }
}
