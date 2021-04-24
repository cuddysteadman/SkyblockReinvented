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
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityBat;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.gui.GuiManager;
import thecudster.sre.util.sbutil.ItemUtil;
import thecudster.sre.util.sbutil.Utils;

import java.util.Objects;

/**
 * Modified from NotEnoughUpdates under Creative Commons Attribution-NonCommercial 3.0
 * https://github.com/Moulberry/NotEnoughUpdates/blob/master/LICENSE
 * @author Moulberry
 */
public class SkeletonMasterReminder {
    int stopDestroyingMyFuckingEars = 300;
    @SubscribeEvent
    public void onRenderLivingPre(RenderLivingEvent.Pre event) {
        if (!Utils.inSkyblock || !Utils.inDungeons) { return; }
        if (event.entity.getDistanceToEntity(Minecraft.getMinecraft().thePlayer) <= SkyblockReinvented.config.skeletonRange && Utils.inDungeons && event.entity instanceof EntitySkeleton && SkyblockReinvented.config.warnSkeletonMasters && !event.entity.isInvisible() && Objects.equals(ItemUtil.getSkyBlockItemID(event.entity.getCurrentArmor(0)), "SKELETON_MASTER_BOOTS")) {
            if (stopDestroyingMyFuckingEars > 300) {
                GuiManager.createTitle("Skeleton Master Nearby!", 20);
                stopDestroyingMyFuckingEars = 0;
            }
            else {
                stopDestroyingMyFuckingEars++;
            }
        }
        if (event.entity.getDistanceToEntity(Minecraft.getMinecraft().thePlayer) <= SkyblockReinvented.config.batRange && SkyblockReinvented.config.warnBatSecrets && Utils.inDungeons && event.entity instanceof EntityBat && event.entity.getMaxHealth() == 100) {
            if (DeleteOwnSpiritBats.isSpiritBat((EntityBat) event.entity)) { return; }
            if (stopDestroyingMyFuckingEars > 300) {
                GuiManager.createTitle("Bat Secret Nearby!", 20);
                stopDestroyingMyFuckingEars = 0;
            }
            else {
                stopDestroyingMyFuckingEars++;
            }
        }
    }
}
