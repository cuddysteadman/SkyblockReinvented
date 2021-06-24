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
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.sbutil.CurrentLoc;

public class WitherCloakHider {
	/**
     * Modified from Skytils under GNU Affero General Public license.
     * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
	 * @author Sychic
	 * @author My-Name-Is-Jeff
     */
	@SubscribeEvent(receiveCanceled = true, priority = EventPriority.HIGHEST)
	public void onCheckRender(RenderLivingEvent.Pre<EntityCreeper> event) {
		
		if (!(event.entity instanceof EntityCreeper)) { return; }
		EntityCreeper creeperEntity = (EntityCreeper) event.entity;
		EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
		if (CurrentLoc.currentLoc.equals("The Mist")) {
			return;
		}
		if (event.entity.getDistance(player.posX, player.posY, player.posZ) <= 5) { return; }
		
			if (creeperEntity instanceof EntityCreeper && SkyblockReinvented.config.renderCreepers && creeperEntity.getPowered()) {
				event.setCanceled(true); // TODO max health
			}
		
		
	}
}
