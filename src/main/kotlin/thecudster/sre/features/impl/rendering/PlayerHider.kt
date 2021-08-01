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
package thecudster.sre.features.impl.rendering

import net.minecraft.client.entity.EntityOtherPlayerMP
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.ItemSkull
import net.minecraftforge.client.event.RenderLivingEvent
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import thecudster.sre.SkyblockReinvented
import thecudster.sre.util.Utils
import thecudster.sre.util.sbutil.ArrStorage

class PlayerHider {
    @SubscribeEvent(receiveCanceled = true, priority = EventPriority.HIGHEST)
    fun onEntityRender(event: RenderLivingEvent.Pre<*>) {
        if (!Utils.inSkyblock) return
        if (event.entity is EntityPlayerSP) return
        if (event.entity !is EntityOtherPlayerMP) return
        if (isNPC(event.entity)) return
        val str = (event.entity as EntityOtherPlayerMP).displayNameString
        if (Utils.inDungeons) return
        for (s in ArrStorage.renderWhitelist) {
            if (str.contains(s)) {
                return
            }
        }
        for (s in SkyblockReinvented.config.listToRender) {
            if (s.getShouldCancel((event.entity as EntityOtherPlayerMP).displayNameString)) {
                if (SkyblockReinvented.config.renderPlayerArmor > 0) {
                    setNoArmor(event.entity as EntityOtherPlayerMP)
                }
                return
            }
        }
        if (SkyblockReinvented.config.renderPlayers) {
            event.isCanceled = true
        }
        if (SkyblockReinvented.config.renderPlayerArmor > 0) {
            setNoArmor(event.entity as EntityOtherPlayerMP)
        }
    }

    /**
     * Taken from SkyblockAddons under MIT License
     * https://github.com/BiscuitDevelopment/SkyblockAddons/blob/master/LICENSE
     * @author BiscuitDevelopment
     */
    fun isNPC(entity: Entity): Boolean {
        if (entity !is EntityOtherPlayerMP) {
            return false
        }
        val entityLivingBase = entity as EntityLivingBase
        return entity.getUniqueID()
            .version() == 2 && entityLivingBase.health == 20.0f && !entityLivingBase.isPlayerSleeping
    }

    fun setNoArmor(player: EntityOtherPlayerMP) {
        if (SkyblockReinvented.config.renderPlayerArmor == 1) {
            if (player.getCurrentArmor(1) != null && player.getCurrentArmor(1).item != null) {
                if (player.getCurrentArmor(1).item is ItemSkull) {
                    player.setCurrentItemOrArmor(1, null)
                }
            }
            return
        }
        player.setCurrentItemOrArmor(1, null)
        player.setCurrentItemOrArmor(2, null)
        player.setCurrentItemOrArmor(3, null)
        player.setCurrentItemOrArmor(4, null)
    }
}