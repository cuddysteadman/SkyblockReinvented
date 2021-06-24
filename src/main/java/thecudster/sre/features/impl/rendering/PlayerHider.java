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

import net.minecraft.block.BlockSkull;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSkull;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.features.impl.filter.CustomPlayersFilter;
import thecudster.sre.util.sbutil.ArrStorage;
import thecudster.sre.util.Utils;

public class PlayerHider {
	@SubscribeEvent(receiveCanceled = true, priority = EventPriority.HIGHEST)
	public void onEntityRender(RenderLivingEvent.Pre event) {
		if (!Utils.inSkyblock) return;
		if (event.entity instanceof EntityPlayerSP) return;
        if (!(event.entity instanceof EntityOtherPlayerMP)) return;
        if (this.isNPC(event.entity)) return;
		String str = ((EntityOtherPlayerMP) event.entity).getDisplayNameString();
		if (Utils.inDungeons) return;
		for (String s : ArrStorage.renderWhitelist) {
			if (str.contains(s)) {
				return;
			}
		}
		for (CustomPlayersFilter s : SkyblockReinvented.config.listToRender) {
			if (s.getShouldCancel(((EntityOtherPlayerMP) event.entity).getDisplayNameString())) {
				if (SkyblockReinvented.config.renderPlayerArmor > 0) {
					setNoArmor((EntityOtherPlayerMP) event.entity);
				}
				return;
			}
		}
		if (SkyblockReinvented.config.renderPlayers) {
			event.setCanceled(true);
		}
		if (SkyblockReinvented.config.renderPlayerArmor > 0) {
			setNoArmor((EntityOtherPlayerMP) event.entity);
		}
	}
	/**
	 * Taken from SkyblockAddons under MIT License
	 * https://github.com/BiscuitDevelopment/SkyblockAddons/blob/master/LICENSE
	 * @author BiscuitDevelopment
	 */
	public boolean isNPC(Entity entity) {
        if (!(entity instanceof EntityOtherPlayerMP)) {
            return false;
        }
        EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
        return entity.getUniqueID().version() == 2 && entityLivingBase.getHealth() == 20.0F && !entityLivingBase.isPlayerSleeping();
    }
    public void setNoArmor(EntityOtherPlayerMP player) {
		if (SkyblockReinvented.config.renderPlayerArmor == 1) {
			if (player.getCurrentArmor(1).getItem() instanceof ItemSkull) {
				player.setCurrentItemOrArmor(1, null);
			}
			return;
		}
		player.setCurrentItemOrArmor(1, null);
		player.setCurrentItemOrArmor(2, null);
		player.setCurrentItemOrArmor(3, null);
		player.setCurrentItemOrArmor(4, null);
	}
}
