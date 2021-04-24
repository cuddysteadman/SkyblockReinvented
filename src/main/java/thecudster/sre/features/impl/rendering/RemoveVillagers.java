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

package thecudster.sre.features.impl.rendering;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.sbutil.CurrentLoc;
import thecudster.sre.util.sbutil.Utils;

public class RemoveVillagers {
    public static final String[] villagerNames = {
            "Andrew",
            "Jack",
            "Jamie",
            "Tom",
            "Leo",
            "Felix",
            "Ryu",
            "Duke",
            "Lynn",
            "Stella",
            "Vex",
            "Liam",
            "Jerry"
    };
    @SubscribeEvent(receiveCanceled = true, priority = EventPriority.HIGHEST)
    public void onCheckRender(RenderLivingEvent.Pre event) {
        if (!SkyblockReinvented.config.renderVillagers || !Utils.inSkyblock) { return; }
        boolean villager = false;
        if (event.entity.getCustomNameTag() != null) {
            if (CurrentLoc.currentLoc.equals("Jerry's Workshop") || CurrentLoc.currentLoc.equals("Jerry Pond")) { return; }
            for (String s : villagerNames) {
                if (event.entity.getCustomNameTag().contains(s)) {
                    villager = true;
                }
            }
            if (villager) {
                event.setCanceled(true);
            }
            if (event.entity.getCustomNameTag().contains("NEW UPDATE")) {
                event.setCanceled(true);
            }
            if (event.entity.getCustomNameTag().contains("CLICK")) {
                for (Entity entity : Minecraft.getMinecraft().theWorld.loadedEntityList) {
                    if (entity instanceof EntityVillager) {
                        if (entity.getDistanceToEntity(event.entity) <= 2) {
                            event.setCanceled(true);
                        }
                    }

                }
            }
        }
        if (!(event.entity instanceof EntityVillager)) { return; }
        EntityVillager villagerEntity = (EntityVillager) event.entity;

        for (Entity entity : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            if (entity instanceof EntityArmorStand) {
                for (String s : villagerNames) {
                    if (entity.getCustomNameTag().contains(s)) {
                        villager = true;
                    }
                }
                if (villager) {
                    if (entity.getDistanceToEntity(villagerEntity) <= 2) {
                        event.setCanceled(true);
                    }
                }
            }

        }
    }
}
