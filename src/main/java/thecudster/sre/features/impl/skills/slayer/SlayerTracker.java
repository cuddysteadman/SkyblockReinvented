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

package thecudster.sre.features.impl.skills.slayer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.gui.*;
import thecudster.sre.util.gui.colours.CommonColors;
import thecudster.sre.util.sbutil.ArrStorage;
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
    String xpLeftSven = "   Wolf Slayer LVL ";
    String xpLeftTara = "   Spider Slayer LVL ";
    String xpLeftRev =  "   Zombie Slayer LVL ";
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
                if (SkyblockReinvented.config.slayerMode == 1) {
                    unformatted = unformatted.substring(2, unformatted.length() - 1);
                    String meter = RenderUtils.progressBar(30, Double.parseDouble(StringUtils.stripControlCodes(unformatted)) / 100, EnumChatFormatting.LIGHT_PURPLE);
                    SlayerTracker.rngesusMeter = meter;
                } else {
                    SlayerTracker.rngesusMeter = unformatted;
                }
                event.setCanceled(true);
                return;
            }
            if (unformatted.contains(xpLeftRev)) {
                SlayerTracker.currentSlayer = EnumChatFormatting.LIGHT_PURPLE + "Revenant";
                event.setCanceled(true);
                if (SkyblockReinvented.config.slayerMode == 1) {
                    progressBarSlayer(unformatted, ArrStorage.revLeveling, false, EnumChatFormatting.GREEN);
                } else {
                    unformatted = unformatted.substring(xpLeftRev.length() + nxtLvl + 1, unformatted.length() - 1);
                    SlayerTracker.xpLeft = EnumChatFormatting.LIGHT_PURPLE + unformatted;
                }
                return;
            }
            if (unformatted.contains(xpLeftTara)) {
                SlayerTracker.currentSlayer = EnumChatFormatting.LIGHT_PURPLE + "Tarantula";
                event.setCanceled(true);
                if (SkyblockReinvented.config.slayerMode == 1) {
                    progressBarSlayer(unformatted, ArrStorage.taraLeveling, false, EnumChatFormatting.GREEN);
                } else {
                    unformatted = unformatted.substring(xpLeftTara.length() + nxtLvl + 1, unformatted.length() - 1);
                    SlayerTracker.xpLeft = EnumChatFormatting.LIGHT_PURPLE + unformatted;
                }

                return;
            }
            if (unformatted.contains(xpLeftSven)) {
                SlayerTracker.currentSlayer = EnumChatFormatting.LIGHT_PURPLE + "Sven";
                event.setCanceled(true);
                if (SkyblockReinvented.config.slayerMode == 1) {
                    progressBarSlayer(unformatted, ArrStorage.svenLeveling, true, EnumChatFormatting.GREEN);
                } else {
                    unformatted = unformatted.substring(xpLeftSven.length() + nxtLvl + 1, unformatted.length() - 1);
                    SlayerTracker.xpLeft = EnumChatFormatting.LIGHT_PURPLE + unformatted;
                }
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
            return SkyblockReinvented.config.slayerInfo && Utils.inSkyblock && !Utils.inDungeons && Utils.inLoc(ArrStorage.slayerLocs);
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
    public void progressBarSlayer(String unformatted, double[] levelingInfo, boolean sven, EnumChatFormatting colour) {
        try {
            int lvl;
            int xp;
            if (sven) {
                lvl = Integer.parseInt(unformatted.substring(xpLeftSven.length(), xpLeftSven.length() + 1));
                xp = Integer.parseInt(unformatted.substring(xpLeftSven.length() + nxtLvl + 1, unformatted.length() - 4).replace(",", ""));
            } else {
                lvl = Integer.parseInt(unformatted.substring(xpLeftRev.length(), xpLeftRev.length() + 1));
                xp = Integer.parseInt(unformatted.substring(xpLeftRev.length() + nxtLvl + 1, unformatted.length() - 4).replace(",", ""));
            }
            if (lvl == 9) {
                SlayerTracker.xpLeft = EnumChatFormatting.GOLD + "MAX LEVEL";
                return;
            }
            SlayerTracker.xpLeft = RenderUtils.progressBar(30, 1.0 - xp / levelingInfo[lvl - 1], colour);
        } catch (Exception ex) {
            Utils.sendMsg(EnumChatFormatting.RED + "SkyblockReinvented caught and logged an exception at SlayerTracker. Please report this!");
            ex.printStackTrace();
        }
    }
}
