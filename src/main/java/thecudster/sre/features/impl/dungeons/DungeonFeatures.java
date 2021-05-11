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
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.gui.GuiManager;
import thecudster.sre.util.sbutil.ItemUtil;
import thecudster.sre.util.sbutil.Utils;

import java.util.Objects;

public class DungeonFeatures {
    public static boolean isSpiritBat(Entity e) {
        if (!Utils.inSkyblock || !Utils.inDungeons || !(e instanceof EntityBat)) { return false; }
        return ((EntityBat) e).getMaxHealth() == 100;
    }
    @SubscribeEvent(receiveCanceled = true)
    public void onRender(RenderLivingEvent.Pre event) {
        if (!Utils.inSkyblock || !Utils.inDungeons) { return; }
        if (event.entity instanceof EntityBat) {
            if (SkyblockReinvented.config.spiritBats) {
                if (!isSpiritBat(event.entity)) {
                    event.setCanceled(true);
                    return;
                }
            }
        }
    }
    int stopDestroyingMyFuckingEars = 300;
    @SubscribeEvent
    public void onRenderLivingPre(RenderLivingEvent.Pre event) {
        if (!Utils.inSkyblock || !Utils.inDungeons) { return; }
        if (event.entity.getDistanceToEntity(Minecraft.getMinecraft().thePlayer) <= SkyblockReinvented.config.skeletonRange && isSkeletonMaster(event.entity)) {
            if (stopDestroyingMyFuckingEars > 300) {
                GuiManager.createTitle("Skeleton Master Nearby!", 20);
                stopDestroyingMyFuckingEars = 0;
            }
            else {
                stopDestroyingMyFuckingEars++;
            }
        }
        if (event.entity.getDistanceToEntity(Minecraft.getMinecraft().thePlayer) <= SkyblockReinvented.config.batRange && SkyblockReinvented.config.warnBatSecrets && isSpiritBat(event.entity)) {
            if (stopDestroyingMyFuckingEars > 300) {
                GuiManager.createTitle("Bat Secret Nearby!", 20);
                stopDestroyingMyFuckingEars = 0;
            }
            else {
                stopDestroyingMyFuckingEars++;
            }
        }
    }
    @SubscribeEvent
    public void onEntityDeath(LivingDeathEvent event) {
        if (event.entity instanceof EntityBat) {
            if (isSpiritBat(event.entity)) {
                stopDestroyingMyFuckingEars = 290;
            }
        }
        if (isSkeletonMaster(event.entity)) {
            stopDestroyingMyFuckingEars = 250;
        }
    }
    public static boolean isSkeletonMaster(Entity e) {
        if (!(e instanceof EntitySkeleton)) { return false; }
        return Utils.inDungeons && SkyblockReinvented.config.warnSkeletonMasters && !e.isInvisible() && Objects.equals(ItemUtil.getSkyBlockItemID(((EntitySkeleton)e).getCurrentArmor(0)), "SKELETON_MASTER_BOOTS");
    }
    @SubscribeEvent
    public void onEnter(EntityJoinWorldEvent event) {
        if (event.entity instanceof EntitySheep && SkyblockReinvented.config.sheep) {
            if (Utils.inDungeons && ((EntitySheep)event.entity).getMaxHealth() == 8.0) {
                event.setCanceled(true);
            }
        }
    }
}
