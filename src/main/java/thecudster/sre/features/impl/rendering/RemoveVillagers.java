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
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.sbutil.CurrentLoc;
import thecudster.sre.util.sbutil.Utils;

import java.util.ArrayList;
import java.util.List;

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
            "Liam"
    };
    ArrayList<EntityVillager> found = new ArrayList<EntityVillager>();
    ArrayList<EntityArmorStand> foundNames = new ArrayList<EntityArmorStand>();
    public static final String[] whitelist = {
            "Blacksmith",
            "St. Jerry"
    };
    public static final String[] hubLocs = {
            "Coal Mine",
            "Private Island",
            "Forest",
            "Bank",
            "Library",
            "Auction House",
            "Flower House",
            "Bazaar Alley",
            "Community Center",
            "Builder's House",
            "Village"
    };
    @SubscribeEvent(receiveCanceled = true, priority = EventPriority.HIGHEST)
    public void onCheckRender(RenderLivingEvent.Pre event) {
        if (!SkyblockReinvented.config.renderVillagers || !Utils.inSkyblock || CurrentLoc.currentLoc.equals("Jerry's Workshop") || CurrentLoc.currentLoc.equals("Jerry Pond")) {
            return;
        }
        if (!(event.entity instanceof EntityVillager || event.entity instanceof EntityArmorStand)) { return; }
        boolean villager = false;
        if (event.entity.getCustomNameTag() != null) {
            List<EntityArmorStand> entitiesWithinRange = Minecraft.getMinecraft().theWorld.getEntitiesWithinAABB(EntityArmorStand.class, AxisAlignedBB.fromBounds(event.entity.posX, event.entity.posY, event.entity.posZ,
                    (event.entity.posX + 1),(event.entity.posY + 1),(event.entity.posZ + 1)));
            for (String s : whitelist) {
                if (event.entity.getCustomNameTag().contains(s)) {
                    return;
                }
            }
            for (String s : whitelist) {
                for (Entity e : entitiesWithinRange) {
                    if (event.entity.getDistanceToEntity(e) < 2 && event.entity instanceof EntityVillager && e.getCustomNameTag().contains(s)) {
                        return;
                    }
                }
            }
            if (!Utils.inLoc(hubLocs)) {
                if (!CurrentLoc.currentLoc.equals("Your Island")) { return; }
                if (event.entity.getCustomNameTag().contains("NEW UPDATE")) {
                    event.entity.setAlwaysRenderNameTag(false);
                }
                if (event.entity.getCustomNameTag().contains("CLICK")) {
                    event.entity.setAlwaysRenderNameTag(false);
                }
                if (event.entity.getCustomNameTag().contains("Jerry")) {
                    villager = true;
                }
                if (event.entity instanceof EntityVillager){
                    for (Entity e : entitiesWithinRange) {
                        if (e instanceof EntityArmorStand) {
                            if (e.getCustomNameTag().contains("Jerry")) {
                                villager = true;
                            }
                            if (villager) {
                                    event.entity.setInvisible(true);
                                    event.entity.setAlwaysRenderNameTag(false);
                                    return;
                            }
                        }
                    }
                } else if (event.entity instanceof EntityArmorStand) {
                    if (event.entity.getCustomNameTag().contains("Jerry")) {
                        event.entity.setInvisible(true);
                        event.entity.setAlwaysRenderNameTag(false);
                        return;
                    }
                }
                return;
            }
            if (event.entity instanceof EntityArmorStand) {
                for (String s : villagerNames) {
                    if (event.entity.getCustomNameTag().contains(s)) {
                        villager = true;
                    }
                }
                if (villager) {
                    Minecraft.getMinecraft().theWorld.removeEntity(event.entity);
                    event.setCanceled(true);
                    event.entity.setAlwaysRenderNameTag(false);
                    if (!foundNames.contains((EntityArmorStand) event.entity)) {
                        foundNames.add((EntityArmorStand) event.entity);
                    }
                }
            }

            if (!(event.entity instanceof EntityVillager)) {
                return;
            }
            EntityVillager villagerEntity = (EntityVillager) event.entity;
            for (EntityArmorStand e : entitiesWithinRange) {
                for (String s : villagerNames) {
                    if (e.getCustomNameTag().contains(s)) {
                        villager = true;
                    }
                    if (e.getCustomNameTag().contains("CLICK") ) {
                        villager = true;
                    }
                    if (villager) {
                        e.setAlwaysRenderNameTag(false);
                        Minecraft.getMinecraft().theWorld.removeEntity(e);
                        Minecraft.getMinecraft().theWorld.removeEntity(villagerEntity);
                        event.setCanceled(true);
                        if (!found.contains(villagerEntity)) {
                            found.add(villagerEntity);
                        }
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public void onChange(WorldEvent.Load event) {
        found.clear();
    }
}
