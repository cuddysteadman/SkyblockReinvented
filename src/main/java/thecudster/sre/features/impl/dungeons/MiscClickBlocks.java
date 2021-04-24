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
package thecudster.sre.features.impl.dungeons;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.events.GuiContainerEvent;
import thecudster.sre.features.impl.qol.MiscGUIs;
import thecudster.sre.util.sbutil.ItemUtil;
import thecudster.sre.util.sbutil.Utils;

/*
 * Modified from Skytils under GNU Affero Public license.
 * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
 * @author My-Name-Is-Jeff
 * @author Sychic
 */
public class MiscClickBlocks {
    int num = 0;
    public String supposedName;
	@SubscribeEvent(receiveCanceled = true)
    public void onSlotClick(GuiContainerEvent.SlotClickEvent event) {
        if (!Utils.inSkyblock) return;

        if (event.container instanceof ContainerChest) {
            ContainerChest chest = (ContainerChest) event.container;

            IInventory inventory = chest.getLowerChestInventory();
            Slot slot = event.slot;
            if (slot == null) return;
            ItemStack item = slot.getStack();
            String inventoryName = inventory.getDisplayName().getUnformattedText();
            if (item != null) {
                if (item.hasDisplayName()) {
                    String itemName = item.getDisplayName();

                    NBTTagCompound extraAttributes = ItemUtil.getExtraAttributes(item);

                    if (inventoryName.equals("Catacombs Gate")) {
                        if (SkyblockReinvented.config.floorLock > 0) {
                            if (slot.inventory == Minecraft.getMinecraft().thePlayer.inventory || slot.slotNumber == 50 || slot.slotNumber == 49 || slot.slotNumber == 48 || slot.slotNumber == 47)
                                return;
                            switch (SkyblockReinvented.config.floorLock) {
                                case 1:
                                    supposedName = "§aThe Catacombs §8- §eFloor I";
                                    break;
                                case 2:
                                    supposedName = "§aThe Catacombs §8- §eFloor II";
                                    break;
                                case 3:
                                    supposedName = "§aThe Catacombs §8- §eFloor III";
                                    break;
                                case 4:
                                    supposedName = "§aThe Catacombs §8- §eFloor IV";
                                    break;
                                case 5:
                                    supposedName = "§aThe Catacombs §8- §eFloor V";
                                    break;
                                case 6:
                                    supposedName = "§aThe Catacombs §8- §eFloor VI";
                                    break;
                                case 7:
                                    supposedName = "§aThe Catacombs §8- §eFloor VII";
                                    break;
                                default:
                                    supposedName = "Floor Null";
                                    break;
                            }
                            if (item.getItem() != Items.skull || !(itemName.equals(supposedName))) {
                                event.setCanceled(true);
                                return;
                            }
                        }
                    }
                }
            }
        }
        if (!Utils.inSkyblock) return;
        if (!Utils.inDungeons) {return;}
        /**
         * Modified from Skytils under GNU Affero General Public license.
         * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
         * @author Sychic
         * @author My-Name-Is-Jeff
         */

        if (event.container instanceof ContainerChest) {
            if (MiscGUIs.foundBadItem) {
                num++;
                int neededClick = SkyblockReinvented.config.chestStop - num;
                if (neededClick > 0) {
                    event.setCanceled(true);
                    return;
                }
            }
        }
    }
    @SubscribeEvent
    public void onGuiClose(GuiOpenEvent event) {
	    MiscGUIs.foundBadItem = false;
        num = 0;
        MiscGUIs.inReforge = false;
    }
    /**
     * Modified from Skytils under GNU Affero General Public license.
     * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
     * @author My-Name-Is-Jeff
     * @author Sychic
     */
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onTooltip(ItemTooltipEvent event) {
        if (!Utils.inSkyblock || !Utils.inDungeons) { return; }
        if (!ItemUtil.getDisplayName(event.itemStack).contains("Open Reward Chest") || event.itemStack == null) { return; }
        if (MiscGUIs.woodenChest || !MiscGUIs.foundBadItem) {
            for (int i = 0; i < event.toolTip.size(); i++) {
                if (event.toolTip.get(i).contains("your bank") || event.toolTip.get(i).contains("Purchase this chest to receive") ||
                        event.toolTip.get(i).contains("the rewards above") || event.toolTip.get(i).contains("open one chest") ||
                        event.toolTip.get(i).contains("choose wisely") || event.toolTip.contains("per Dungeons run")) {
                    event.toolTip.remove(i);
                }
            }
            event.toolTip.remove(0);
            event.toolTip.remove(0);
            event.toolTip.remove(0);
            event.toolTip.remove(0);
            event.toolTip.remove(3);
            event.toolTip.remove(2);
            return;
        }
        if (event.itemStack != null) {
            if (SkyblockReinvented.config.chestStop > 0 && MiscGUIs.foundBadItem && ItemUtil.getDisplayName(event.itemStack).contains("Open Reward Chest")) {
                for (int i = 0; i < event.toolTip.size(); i++) {
                    if (event.toolTip.get(i).contains("NOTE: Coins are withdrawn from")) {
                        int neededClicks = SkyblockReinvented.config.chestStop - num;
                        event.toolTip.set(i, "§eClick §a" + neededClicks + "§e times to open this chest!");
                    }
                    if (event.toolTip.get(i).contains("your bank") || event.toolTip.get(i).contains("Purchase this chest to receive") ||
                            event.toolTip.get(i).contains("the rewards above") || event.toolTip.get(i).contains("open one chest") ||
                            event.toolTip.get(i).contains("choose wisely") || event.toolTip.contains("per Dungeons run")) {
                        event.toolTip.remove(i);
                    }
                }
                event.toolTip.remove(0);
                event.toolTip.remove(0);
                event.toolTip.remove(0);
                event.toolTip.remove(0);
            }
        }
    }

}
