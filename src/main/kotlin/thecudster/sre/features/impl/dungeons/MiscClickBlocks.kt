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
package thecudster.sre.features.impl.dungeons

import net.minecraft.client.Minecraft
import net.minecraft.init.Items
import net.minecraft.inventory.ContainerChest
import net.minecraftforge.client.event.GuiOpenEvent
import net.minecraftforge.event.entity.player.ItemTooltipEvent
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import thecudster.sre.SkyblockReinvented
import thecudster.sre.events.GuiContainerEvent.SlotClickEvent
import thecudster.sre.features.impl.qol.MiscGUIs
import thecudster.sre.util.Utils
import thecudster.sre.util.sbutil.ItemUtil.getDisplayName
import thecudster.sre.util.sbutil.ItemUtil.getExtraAttributes

/*
 * Modified from Skytils under GNU Affero Public license.
 * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
 * @author My-Name-Is-Jeff
 * @author Sychic
 */
class MiscClickBlocks {
    var num = 0
    var supposedName: String? = null
    @SubscribeEvent(receiveCanceled = true, priority = EventPriority.HIGHEST)
    fun onSlotClick(event: SlotClickEvent) {
        if (!Utils.inSkyblock) return
        if (event.container is ContainerChest) {
            val chest = event.container as ContainerChest?
            val inventory = chest!!.lowerChestInventory
            val slot = event.slot ?: return
            val item = slot.stack
            val inventoryName = inventory.displayName.unformattedText
            if (item != null) {
                if (item.hasDisplayName()) {
                    val itemName = item.displayName
                    val extraAttributes = getExtraAttributes(item)
                    if (inventoryName == "Catacombs Gate") {
                        if (SkyblockReinvented.config.floorLock > 0) {
                            if (slot.inventory === Minecraft.getMinecraft().thePlayer.inventory || slot.slotNumber == 50 || slot.slotNumber == 49 || slot.slotNumber == 48 || slot.slotNumber == 47) return
                            supposedName = when (SkyblockReinvented.config.floorLock) {
                                1 -> "§aThe Catacombs §8- §eFloor I"
                                2 -> "§aThe Catacombs §8- §eFloor II"
                                3 -> "§aThe Catacombs §8- §eFloor III"
                                4 -> "§aThe Catacombs §8- §eFloor IV"
                                5 -> "§aThe Catacombs §8- §eFloor V"
                                6 -> "§aThe Catacombs §8- §eFloor VI"
                                7 -> "§aThe Catacombs §8- §eFloor VII"
                                else -> "Floor Null"
                            }
                            if (item.item !== Items.skull || itemName != supposedName) {
                                event.isCanceled = true
                                return
                            }
                        }
                    }
                }
            }
        }
        if (!Utils.inSkyblock) return
        if (!Utils.inDungeons) {
            return
        }
        /**
         * Modified from Skytils under GNU Affero General Public license.
         * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
         * @author Sychic
         * @author My-Name-Is-Jeff
         */
        if (event.container is ContainerChest) {
            if (MiscGUIs.inDungeonChest && !MiscGUIs.woodenChest) {
                num++
                val neededClick = SkyblockReinvented.config.chestStop - num
                if (neededClick > 0) {
                    event.isCanceled = true
                    return
                }
            }
        }
    }

    @SubscribeEvent
    fun onGuiOpen(event: GuiOpenEvent?) {
        MiscGUIs.foundBadItem = false
        num = 0
        MiscGUIs.inDungeonChest = false
        MiscGUIs.inReforge = false
    }

    /**
     * Modified from Skytils under GNU Affero General Public license.
     * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
     * @author My-Name-Is-Jeff
     * @author Sychic
     */
    @SubscribeEvent(priority = EventPriority.LOWEST)
    fun onTooltip(event: ItemTooltipEvent) {
        if (!Utils.inSkyblock || !Utils.inDungeons) {
            return
        }
        if (!getDisplayName(event.itemStack).contains("Open Reward Chest") || event.itemStack == null) {
            return
        }
        if (MiscGUIs.woodenChest || !MiscGUIs.foundBadItem) {
            for (i in event.toolTip.iterator()) {
                if (i.contains("your bank") || i.contains("Purchase this chest to receive") ||
                    i.contains("the rewards above") || i.contains("open one chest") ||
                    i.contains("choose wisely") || i.contains("per Dungeons run")
                ) {
                    event.toolTip.remove(i)
                }
            }
            event.toolTip.removeAt(0)
            event.toolTip.removeAt(0)
            event.toolTip.removeAt(0)
            event.toolTip.removeAt(0)
            event.toolTip.removeAt(3)
            event.toolTip.removeAt(2)
            return
        }
        if (event.itemStack != null) {
            if (SkyblockReinvented.config.chestStop > 0 && MiscGUIs.foundBadItem && getDisplayName(event.itemStack).contains(
                    "Open Reward Chest"
                )
            ) {
                for (i in event.toolTip.indices) {
                    if (event.toolTip[i].contains("NOTE: Coins are withdrawn from")) {
                        val neededClicks = SkyblockReinvented.config.chestStop - num
                        event.toolTip[i] = "§eClick §a$neededClicks§e times to open this chest!"
                    }
                    if (event.toolTip[i].contains("your bank") || event.toolTip[i].contains("Purchase this chest to receive") ||
                        event.toolTip[i].contains("the rewards above") || event.toolTip[i].contains("open one chest") ||
                        event.toolTip[i].contains("choose wisely") || event.toolTip.contains("per Dungeons run")
                    ) {
                        event.toolTip.removeAt(i)
                    }
                }
                event.toolTip.removeAt(0)
                event.toolTip.removeAt(0)
                event.toolTip.removeAt(0)
                event.toolTip.removeAt(0)
            }
        }
    }
}