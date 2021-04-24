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

import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.sbutil.Utils;

public class DeleteOwnSpiritBats {
    @SubscribeEvent(receiveCanceled = true)
    public void onRender(RenderLivingEvent.Pre event) {
        if (!Utils.inSkyblock || !Utils.inDungeons) { return; }
            if (event.entity instanceof EntityBat) {
                if (SkyblockReinvented.config.spiritBats) {
                    if (isSpiritBat((EntityBat) event.entity)) {
                        event.entity.setInvisible(true);
                        return;
                    }
                }
            }

        if (SkyblockReinvented.config.svenPups) {
            if (event.entity instanceof EntityWolf) {
                EntityWolf wolf = ((EntityWolf) event.entity);
                if (wolf.isChild()) {
                    wolf.setInvisible(true);
                    return;
                }

            }
        }
    }

    public static boolean isSpiritBat(EntityBat e) {
        if (!Utils.inSkyblock) { return false; }
        if (Utils.inDungeons && e.getMaxHealth() == 100) { return false; }
        return true;
    }
}
