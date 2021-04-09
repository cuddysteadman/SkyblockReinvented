/*
 * SkyblockReinvented - Hypixel Skyblock Improvement Modification for Minecraft
 * Copyright (C) 2021 theCudster
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package thecudster.sre.features.impl.qol;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.util.ItemUtil;

public class ItemDropStop {
    /*public boolean hasDoneTwice = false;
    @SubscribeEvent(receiveCanceled = true)
    public void onDrop(ItemTossEvent event) {
        if (event.entityItem.getEntityItem() == null) { return; }
        if (ItemUtil.getSkyBlockItemID(event.entityItem.getEntityItem()).contains("SACK")) {
            if (hasDoneTwice) {
                hasDoneTwice = false;
            }
            else {
                hasDoneTwice = true;
                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("Drop this item again to confirm!"));
                event.setCanceled(true);
            }
        }
    }*/
}
