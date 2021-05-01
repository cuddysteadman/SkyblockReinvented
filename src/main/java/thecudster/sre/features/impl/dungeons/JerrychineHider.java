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
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.sbutil.ItemUtil;
import thecudster.sre.util.sbutil.Utils;

public class JerrychineHider {
    @SubscribeEvent
    public void onRender(RenderLivingEvent.Pre<EntityArmorStand> event) {
        if (!SkyblockReinvented.config.hideJerry || !Utils.inSkyblock) { return; }
        try {
            if (event.entity instanceof EntityArmorStand) {
                EntityArmorStand entity = ((EntityArmorStand) event.entity);
                ItemStack headSlot = entity.getCurrentArmor(3);
                if (entity.getCurrentArmor(0) != null || entity.getCurrentArmor(1) != null || entity.getCurrentArmor(2) != null) { return; }
                if (headSlot != null && headSlot.getItem() == Items.skull && headSlot.hasTagCompound()) {
                    if (ItemUtil.getSkyBlockItemID(Minecraft.getMinecraft().thePlayer.getHeldItem()).equals("JERRY_STAFF")) {
                        event.entity.setInvisible(true);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

