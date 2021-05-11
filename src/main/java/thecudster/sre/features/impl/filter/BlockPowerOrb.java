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

package thecudster.sre.features.impl.filter;

import net.minecraft.entity.item.EntityArmorStand;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.CancelParticle;
import thecudster.sre.util.sbutil.Utils;

public class BlockPowerOrb {
	public static boolean isOrb = false;
	@SubscribeEvent()
	public void onRender(RenderLivingEvent.Pre<EntityArmorStand> event) {
	if (!SkyblockReinvented.config.renderPowerOrb || !Utils.inSkyblock) { return; }
		if (event.entity instanceof EntityArmorStand) {
			EntityArmorStand entity = (EntityArmorStand) event.entity;
			String name = entity.getCustomNameTag();
			if (name != null) {
				isOrb = (name.contains("Radiant") || name.contains("Mana Flux") || name.contains("Overflux") || name.contains("Plasmaflux"));
				if (isOrb && event.entity.getMaxHealth() == 20.0) {
					event.entity.setAlwaysRenderNameTag(false);
					CancelParticle.cancel();
				}
			}
		}
	}
}
