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

package thecudster.sre.features.impl.dragons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.gui.FloatPair;
import thecudster.sre.util.gui.GuiElement;
import thecudster.sre.util.gui.ScreenRenderer;
import thecudster.sre.util.gui.SmartFontRenderer;
import thecudster.sre.util.gui.colours.CommonColors;
import thecudster.sre.util.sbutil.CurrentLoc;
import thecudster.sre.util.sbutil.Utils;

import java.util.ArrayList;

public class DragTracker {
    public static ArrayList<String> recentDrags = new ArrayList<String>();
    public static ArrayList<String> dragGui = new ArrayList<String>();
    public static void updateGui() {
        dragGui.clear();
        dragGui.add(EnumChatFormatting.GOLD + "Recent Dragons: ");
        for (String s : recentDrags) {
            dragGui.add(EnumChatFormatting.WHITE + " " + s);
        }
        dragGui.add("");
        dragGui.add(EnumChatFormatting.GOLD + "Drags Since: ");
        dragGui.add(EnumChatFormatting.GOLD + " Superior: " + EnumChatFormatting.WHITE + SkyblockReinvented.config.dragsSinceSup);
        dragGui.add(EnumChatFormatting.AQUA + " AOTD: " + EnumChatFormatting.WHITE + SkyblockReinvented.config.dragsSinceAotd);
        dragGui.add(EnumChatFormatting.RED + " Pet: " + EnumChatFormatting.WHITE + SkyblockReinvented.config.dragsSincePet);

    }
    static {
        new DragGUI();
    }
    public static class DragGUI extends GuiElement {
        String[] dragDemo = new String[10];
        public DragGUI() {
            super("Dragon GUI", new FloatPair(0, 0));
            SkyblockReinvented.GUIMANAGER.registerElement(this);
            dragDemo[0] = EnumChatFormatting.GOLD + "Recent Dragons: ";
            dragDemo[1] = EnumChatFormatting.WHITE + " Young Dragon";
            dragDemo[2] = EnumChatFormatting.AQUA + " Wise Dragon";
            dragDemo[3] = EnumChatFormatting.GOLD + " Superior Dragon";
            dragDemo[4] = EnumChatFormatting.RED + " Strong Dragon";
            dragDemo[5] = " ";
            dragDemo[6] = EnumChatFormatting.GOLD + "Drags Since: ";
            dragDemo[7] = EnumChatFormatting.GOLD + " Superior: " + EnumChatFormatting.WHITE + 2;
            dragDemo[8] = EnumChatFormatting.AQUA + " AOTD: " + EnumChatFormatting.WHITE + 6;
            dragDemo[9] = EnumChatFormatting.RED + " Pet: " + EnumChatFormatting.WHITE + 48;
        }
        @Override
        public void render() {
            if (!this.getToggled()) { return; }
            ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
            boolean leftAlign = getActualX() < sr.getScaledWidth() / 2f;
            for (int i = 0; i < dragGui.size(); i++) {
                SmartFontRenderer.TextAlignment alignment = leftAlign ? SmartFontRenderer.TextAlignment.LEFT_RIGHT : SmartFontRenderer.TextAlignment.RIGHT_LEFT;
                ScreenRenderer.fontRenderer.drawString(dragGui.get(i), leftAlign ? this.getActualX() : this.getActualX() + this.getWidth(), this.getActualY() + i * ScreenRenderer.fontRenderer.FONT_HEIGHT, CommonColors.WHITE, alignment, SmartFontRenderer.TextShadow.NORMAL);
            }
        }
        @Override
        public void demoRender() {
            if (!this.getToggled()) { return; }
            ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
            boolean leftAlign = getActualX() < sr.getScaledWidth() / 2f;
            for (int i = 0; i < dragDemo.length; i++) {
                SmartFontRenderer.TextAlignment alignment = leftAlign ? SmartFontRenderer.TextAlignment.LEFT_RIGHT : SmartFontRenderer.TextAlignment.RIGHT_LEFT;
                ScreenRenderer.fontRenderer.drawString(dragDemo[i], leftAlign ? 0 : this.getActualWidth(), i * ScreenRenderer.fontRenderer.FONT_HEIGHT, CommonColors.WHITE, alignment, SmartFontRenderer.TextShadow.NORMAL);
            }
        }

        @Override
        public boolean getToggled() {
            return SkyblockReinvented.config.dragTracker && !Utils.inDungeons && Utils.inSkyblock && (CurrentLoc.currentLoc.equals("Dragon's Nest") || CurrentLoc.currentLoc.equals("The End"));
        }

        @Override
        public int getHeight() {
            return ScreenRenderer.fontRenderer.FONT_HEIGHT * dragDemo.length;
        }

        @Override
        public int getWidth() {

            return ScreenRenderer.fontRenderer.getStringWidth(dragDemo[3]);
        }
    }
    public void onChat(ClientChatReceivedEvent event) {
        if (event.type == 0x2) {
            return;
        }
        if (!Utils.inSkyblock) { return; }
        String unformatted = StringUtils.stripControlCodes(event.message.getUnformattedText());
        System.out.println(unformatted);
        if (SkyblockReinvented.config.dragTracker && CurrentLoc.currentLoc.equals("Dragon's Nest") && !Utils.inDungeons) {
            if (unformatted.contains("The ") && unformatted.contains(" Dragon") && unformatted.contains("has spawned!")) {
                unformatted = unformatted.substring(unformatted.indexOf("The ") + 4, unformatted.indexOf(" Dragon"));
                System.out.println(unformatted);
                switch (unformatted) {
                    case "Protector":
                        if (recentDrags.size() >= 3) {
                            recentDrags.remove(3);
                        }
                        recentDrags.add(0, EnumChatFormatting.DARK_BLUE + unformatted);
                        SkyblockReinvented.config.dragsSinceSup++;
                        break;
                    case "Strong":
                        if (recentDrags.size() >= 3) {
                            recentDrags.remove(3);
                        }
                        recentDrags.add(0, EnumChatFormatting.RED + unformatted);
                        SkyblockReinvented.config.dragsSinceSup++;
                        break;
                    case "Old":
                        if (recentDrags.size() >= 3) {
                            recentDrags.remove(3);
                        }
                        recentDrags.add(0, EnumChatFormatting.YELLOW + unformatted);
                        SkyblockReinvented.config.dragsSinceSup++;
                        break;
                    case "Young":
                        if (recentDrags.size() >= 3) {
                            recentDrags.remove(3);
                        }
                        recentDrags.add(0, EnumChatFormatting.WHITE + unformatted);
                        SkyblockReinvented.config.dragsSinceSup++;
                        break;
                    case "Unstable":
                        if (recentDrags.size() >= 3) {
                            recentDrags.remove(3);
                        }
                        recentDrags.add(0, EnumChatFormatting.DARK_PURPLE + unformatted);
                        SkyblockReinvented.config.dragsSinceSup++;
                        break;
                    case "Wise":
                        if (recentDrags.size() >= 3) {
                            recentDrags.remove(3);
                        }
                        recentDrags.add(0, EnumChatFormatting.AQUA + unformatted);
                        SkyblockReinvented.config.dragsSinceSup++;
                        break;
                    case "Superior":
                        if (recentDrags.size() >= 3) {
                            recentDrags.remove(3);
                        }
                        recentDrags.add(EnumChatFormatting.GOLD + unformatted);
                        SkyblockReinvented.config.dragsSinceSup = 0;
                        break;
                    default:
                        Utils.sendMsg("§cSkyblockReinvented found an issue with the dragon tracker. Please report this!");
                }
                SkyblockReinvented.config.markDirty();
                SkyblockReinvented.config.writeData();
            } else if (unformatted.contains("has obtained Aspect of the Dragons!") && unformatted.contains(Minecraft.getMinecraft().thePlayer.getName())) {
                SkyblockReinvented.config.dragsSinceAotd = 0;
                SkyblockReinvented.config.dragsSincePet++;
                SkyblockReinvented.config.markDirty();
                SkyblockReinvented.config.writeData();
            } else if (unformatted.contains("has obtained") && unformatted.contains("Lvl 1") && unformatted.contains("Ender Dragon!")
                    && unformatted.contains(Minecraft.getMinecraft().thePlayer.getName())) {
                SkyblockReinvented.config.dragsSincePet = 0;
                SkyblockReinvented.config.dragsSinceAotd++;
                SkyblockReinvented.config.markDirty();
                SkyblockReinvented.config.writeData();
            } else if (unformatted.contains("has obtained") && unformatted.contains(Minecraft.getMinecraft().thePlayer.getName())) {
                SkyblockReinvented.config.dragsSincePet++;
                SkyblockReinvented.config.dragsSinceAotd++;
                SkyblockReinvented.config.markDirty();
                SkyblockReinvented.config.writeData();
            }
        }
    }
}
