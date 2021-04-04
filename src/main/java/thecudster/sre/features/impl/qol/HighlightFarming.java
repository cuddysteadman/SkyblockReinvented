package thecudster.sre.features.impl.qol;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.awt.Color;

import thecudster.sre.SkyblockReinvented;
import thecudster.sre.events.GuiContainerEvent;
import thecudster.sre.util.ItemUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class HighlightFarming {
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
    /*
     * Modified/Improved/Reinvented (heh) from Nate's Skyblock Mod under GNU General Public License.
     * https://github.com/Nat3z/SkyblockMod/blob/main/LICENSE
     * @author Nat3z
     */
    @SubscribeEvent
    public void showScreen(GuiScreenEvent.BackgroundDrawnEvent event) {
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
                                    List<String> lore = ItemUtil.getItemLore(toCheck.getStack());
                                    boolean found = false;
                                    for (String s : lore) {
                                        if (s.contains("Full!") || s.contains("Already connected!")) {
                                            found = true;
                                            showOnSlot(chest.inventorySlots.inventorySlots.size(), toCheck.xDisplayPosition, toCheck.yDisplayPosition, Color.red.getRGB());
                                        }
                                    }
                                    if (!found) {
                                        showOnSlot(chest.inventorySlots.inventorySlots.size(), toCheck.xDisplayPosition, toCheck.yDisplayPosition, Color.green.getRGB());
                                    }
                                }
                            }
                        }
                    }
                    return;
                }
            }
        }
        if (SkyblockReinvented.config.toSearch != null && !SkyblockReinvented.config.toSearch.isEmpty()) {
            Minecraft mc = Minecraft.getMinecraft();
            if (mc.currentScreen instanceof GuiChest) {
                GuiChest chest = (GuiChest) mc.currentScreen;
                ContainerChest inventory = (ContainerChest) chest.inventorySlots;
                String displayText = inventory.getLowerChestInventory().getDisplayName().getUnformattedText();
                if (displayText.contains("Ender Chest") || displayText.contains("Accessory Bag") || displayText.contains("Wardrobe")) {
                    List<Slot> slots = chest.inventorySlots.inventorySlots;

                    for (Slot toCheck : slots) {
                        if (toCheck.getStack() != null) {
                            if (toCheck.getStack().hasDisplayName()) {
                                boolean found = false;
                                String name = toCheck.getStack().getDisplayName();
                                for (String s : SkyblockReinvented.config.toSearch) {
                                    if (StringUtils.containsIgnoreCase(name, s)) {
                                        found = true;
                                        showOnSlot(chest.inventorySlots.inventorySlots.size(), toCheck.xDisplayPosition, toCheck.yDisplayPosition, Color.green.getRGB());
                                    }
                                }
                                if (!found) {
                                    showOnSlot(chest.inventorySlots.inventorySlots.size(), toCheck.xDisplayPosition, toCheck.yDisplayPosition, Color.red.getRGB());
                                }
                            }
                        }
                    }
                }
            }
        }
    }


}