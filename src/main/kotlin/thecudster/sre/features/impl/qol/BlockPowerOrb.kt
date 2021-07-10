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
package thecudster.sre.features.impl.qol

import thecudster.sre.util.Utils.cancelParticles
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.client.event.RenderLivingEvent
import net.minecraft.entity.item.EntityArmorStand
import thecudster.sre.SkyblockReinvented
import thecudster.sre.util.Utils

class BlockPowerOrb {
    @SubscribeEvent
    fun onRender(event: RenderLivingEvent.Pre<EntityArmorStand?>) {
        if (!SkyblockReinvented.config.renderPowerOrb || !Utils.inSkyblock) {
            return
        }
        if (event.entity is EntityArmorStand) {
            val entity = event.entity as EntityArmorStand
            val name = entity.customNameTag
            if (name != null) {
                isOrb =
                    name.contains("Radiant") || name.contains("Mana Flux") || name.contains("Overflux") || name.contains(
                        "Plasmaflux"
                    )
                if (isOrb && event.entity.maxHealth.toDouble() == 20.0) {
                    event.entity.alwaysRenderNameTag = false
                    cancelParticles()
                }
            }
        }
    }

    companion object {
        var isOrb = false
    }
}