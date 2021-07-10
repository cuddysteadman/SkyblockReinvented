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
package thecudster.sre.features.impl.dungeons

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.client.event.RenderLivingEvent
import net.minecraft.entity.monster.EntityCreeper
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.client.Minecraft
import thecudster.sre.SkyblockReinvented
import thecudster.sre.util.sbutil.CurrentLoc

class WitherCloakHider {
    /**
     * Modified from Skytils under GNU Affero General Public license.
     * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
     * @author Sychic
     * @author My-Name-Is-Jeff
     */
    @SubscribeEvent(receiveCanceled = true, priority = EventPriority.HIGHEST)
    fun onCheckRender(event: RenderLivingEvent.Pre<EntityCreeper?>) {
        if (event.entity !is EntityCreeper) {
            return
        }
        val creeperEntity = event.entity as EntityCreeper
        val player = Minecraft.getMinecraft().thePlayer
        if (CurrentLoc.currentLoc == "The Mist") {
            return
        }
        if (event.entity.getDistance(player.posX, player.posY, player.posZ) <= 5) {
            return
        }
        if (creeperEntity is EntityCreeper && SkyblockReinvented.config.renderCreepers && creeperEntity.powered) {
            event.isCanceled = true // TODO max health
        }
    }
}