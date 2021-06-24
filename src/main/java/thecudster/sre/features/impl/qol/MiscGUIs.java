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
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.opengl.GL11;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.Utils;

import java.awt.*;
import java.io.IOException;
import java.util.List;

import static thecudster.sre.util.sbutil.ItemUtil.getItemLore;

public class MiscGUIs {
    public static boolean foundBadItem = false;
    public static boolean woodenChest = false;
    public static boolean inDungeonChest = false;
    /*
     * Taken from Danker's Skyblock Mod under GPL 3.0 license.
     * https://github.com/bowser0000/SkyblockMod/blob/master/LICENSE
     * @author bowser0000
     */
    public static void showOnSlot(int size, int xSlotPos, int ySlotPos, int color) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        int guiLeft = (sr.getScaledWidth() - 176) / 2;
        int guiTop = (sr.getScaledHeight() - 222) / 2;

        int x = guiLeft + xSlotPos;
        int y = guiTop + ySlotPos;

        if (size != 90) y+= (6 - (size - 36) / 9) * 9;

        GL11.glTranslated(0, 0, 1);
        Gui.drawRect(x, y, x + 16, y + 16, color);
        GL11.glTranslated(0, 0, -1);

    }
    public static boolean inReforge = false;
    @SubscribeEvent
    public void showScreen(GuiScreenEvent.BackgroundDrawnEvent event) throws IOException {
        /*
         * Modified/Improved/Reinvented from Nate's Skyblock Mod under GNU General Public License.
         * https://github.com/Nat3z/SkyblockMod/blob/main/LICENSE
         * @author Nat3z
         */
        if (!Utils.inSkyblock) return;
        if (SkyblockReinvented.config.jacobRender) {
            Minecraft mc = Minecraft.getMinecraft();
            if (mc.currentScreen instanceof GuiChest) {
                GuiChest chest = (GuiChest) mc.currentScreen;
                ContainerChest inventory = (ContainerChest) chest.inventorySlots;

                if (inventory.getLowerChestInventory().getDisplayName().getUnformattedText().equals("Your Contests")) {
                    List<Slot> slots = chest.inventorySlots.inventorySlots;

                    for (Slot toCheck : slots) {
                        if (toCheck.getStack() != null) {
                            if (toCheck.getStack().hasDisplayName()) {
                                String name = toCheck.getStack().getDisplayName();
                                if (name.contains("Autumn") || name.contains("Winter") || name.contains("Spring") || name.contains("Summer")) {
                                    if (toCheck.getStack().isItemEnchanted()) {
                                        showOnSlot(chest.inventorySlots.inventorySlots.size(), toCheck.xDisplayPosition, toCheck.yDisplayPosition, Color.green.getRGB());
                                    } else {
                                        showOnSlot(chest.inventorySlots.inventorySlots.size(), toCheck.xDisplayPosition, toCheck.yDisplayPosition, Color.red.getRGB());
                                    }
                                }
                            }
                        }
                    }
                    return;
                }
            }
        }
        if (SkyblockReinvented.config.hubOverlay) {
            try {
                Minecraft mc = Minecraft.getMinecraft();
                if (mc.currentScreen instanceof GuiChest) {
                    GuiChest chest = (GuiChest) mc.currentScreen;
                    ContainerChest inventory = (ContainerChest) chest.inventorySlots;

                    if (inventory.getLowerChestInventory().getDisplayName().getUnformattedText().equals("SkyBlock Hub Selector")) {
                        List<Slot> slots = chest.inventorySlots.inventorySlots;

                        for (Slot toCheck : slots) {
                            if (toCheck.getStack() != null) {
                                if (toCheck.getStack().hasDisplayName()) {
                                    String name = toCheck.getStack().getDisplayName();
                                    if (name.contains("SkyBlock Hub")) {
                                        List<String> lore = getItemLore(toCheck.getStack());
                                        boolean found = false;
                                        boolean foundYellow = false;
                                        for (String s : lore) {
                                            if (s.contains("Full!") || s.contains("Already connected!")) {
                                                found = true;
                                                showOnSlot(chest.inventorySlots.inventorySlots.size(), toCheck.xDisplayPosition, toCheck.yDisplayPosition, Color.red.getRGB());
                                            } else if (s.contains("Players: ")) {
                                                s = s.substring(11, 13);
                                                s = s.replace("/", "");
                                                if (Integer.parseInt(s) > 70) {
                                                    foundYellow = true;
                                                }
                                            }
                                        }
                                        if (!found && !foundYellow) {
                                            showOnSlot(chest.inventorySlots.inventorySlots.size(), toCheck.xDisplayPosition, toCheck.yDisplayPosition, Color.green.getRGB());
                                        } else if (foundYellow) {
                                            showOnSlot(chest.inventorySlots.inventorySlots.size(), toCheck.xDisplayPosition, toCheck.yDisplayPosition, Color.yellow.getRGB());
                                        }
                                    }
                                }
                            }
                        }
                        return;
                    }
                }
            } catch (NumberFormatException ex) {
                Utils.sendMsg(EnumChatFormatting.RED + "SRE caught and logged an exception. Please report this.");
                ex.printStackTrace(); // temp fix
            }
        }
        if (SkyblockReinvented.config.toSearch != null && !SkyblockReinvented.config.toSearch.isEmpty()) {
            Minecraft mc = Minecraft.getMinecraft();
            if (mc.currentScreen instanceof GuiChest) {
                GuiChest chest = (GuiChest) mc.currentScreen;
                ContainerChest inventory = (ContainerChest) chest.inventorySlots;
                String displayText = inventory.getLowerChestInventory().getDisplayName().getUnformattedText();
                if (displayText.contains("Ender Chest") || displayText.contains("Accessory Bag") || displayText.contains("Wardrobe") || displayText.contains("Backpack")) {
                    List<Slot> slots = chest.inventorySlots.inventorySlots;
                    for (Slot toCheck : slots) {
                        if (toCheck.getStack() != null) {
                            if (toCheck.getStack().hasDisplayName()) {
                                String name = toCheck.getStack().getDisplayName();
                                for (String s : SkyblockReinvented.config.toSearch) {
                                    if (StringUtils.containsIgnoreCase(name, s)) {
                                        showOnSlot(chest.inventorySlots.inventorySlots.size(), toCheck.xDisplayPosition, toCheck.yDisplayPosition, Color.green.getRGB());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Minecraft mc = Minecraft.getMinecraft();
        if (SkyblockReinvented.config.chestStop >= 0 && Utils.inDungeons) {
            if (mc.currentScreen instanceof GuiChest) {
                GuiChest chest = (GuiChest) mc.currentScreen;
                ContainerChest inventory = (ContainerChest) chest.inventorySlots;
                String displayText = inventory.getLowerChestInventory().getDisplayName().getUnformattedText();
                if (displayText.equals("Wood Chest")) {
                    woodenChest = true;
                    return;
                }
                boolean dungeonChest = displayText.equals("Emerald Chest") || displayText.equals("Obsidian Chest") || displayText.equals("Gold Chest")
                        || displayText.equals("Diamond Chest");
                if (dungeonChest) {
                    woodenChest = false;
                }
            }
        }
        if (SkyblockReinvented.config.reforgeSoundOff) {
            if (mc.currentScreen instanceof GuiChest) {
                GuiChest chest = (GuiChest) mc.currentScreen;
                ContainerChest inventory = (ContainerChest) chest.inventorySlots;
                String displayText = inventory.getLowerChestInventory().getDisplayName().getUnformattedText();
                if (displayText.equals("Reforge Item") || displayText.equals("Reforge Accessory Bag")) {
                    inReforge = true;
                }
            }
        }
        if (mc.currentScreen instanceof GuiChest) {
            GuiChest chest = (GuiChest) mc.currentScreen;
            ContainerChest inventory = (ContainerChest) chest.inventorySlots;
            String displayText = inventory.getLowerChestInventory().getDisplayName().getUnformattedText();
            if (displayText.equals("Chest") && Utils.inDungeons && Utils.inSkyblock) {
                inDungeonChest = true;
            }
        }
        if (mc.currentScreen instanceof GuiChest && SkyblockReinvented.config.discordMode == 0) {
            GuiChest chest = (GuiChest) mc.currentScreen;
            ContainerChest inventory = (ContainerChest) chest.inventorySlots;
            String displayText = inventory.getLowerChestInventory().getDisplayName().getUnformattedText();
            if (displayText.equals("SkyBlock Menu")) {
                try {
                    if (inventory.inventorySlots.get(48) == null) return;
                    if (inventory.inventorySlots.get(48).getStack() == null) return;
                    if (getItemLore(inventory.inventorySlots.get(48).getStack()) == null) return;
                    for (String s : getItemLore(inventory.inventorySlots.get(48).getStack())) {
                        if (s.contains("Playing on: ")) {
                            DiscordRPC.state = "Profile: " + net.minecraft.util.StringUtils.stripControlCodes(s.substring(s.indexOf(": ") + 2));
                            break;
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

}