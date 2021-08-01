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

import net.minecraft.entity.item.EntityArmorStand
import net.minecraftforge.client.event.RenderLivingEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import thecudster.sre.SkyblockReinvented
import thecudster.sre.core.gui.structure.GuiManager
import thecudster.sre.util.Utils
import thecudster.sre.util.Utils.cancelParticles
import thecudster.sre.util.sbutil.stripControlCodes

class PowerOrbFeatures {
    @SubscribeEvent
    fun onRender(event: RenderLivingEvent.Pre<EntityArmorStand?>) {
        //if (!Utils.inSkyblock) {
         //    return
        //}
        if (SkyblockReinvented.config.renderPowerOrb || SkyblockReinvented.config.powerOrbTimeout) {
            if (event.entity is EntityArmorStand) {
                val entity = event.entity as EntityArmorStand
                var name = entity.customNameTag
                if (name != null) {
                    name = name.stripControlCodes()
                    isOrb =
                        name.contains("Radiant") || name.contains("Mana Flux") || name.contains("Overflux") || name.contains(
                            "Plasmaflux"
                        )
                    if (isOrb && event.entity.maxHealth.toDouble() == 20.0) {
                        if (SkyblockReinvented.config.powerOrbTimeout) {
                            var timeLeft = name.substring(name.indexOf(" ") + 1, name.length - 1).toInt()
                            if (timeLeft < 1) {
                                if (name.contains("Radiant") && SkyblockReinvented.config.radiantTimeout) {
                                    GuiManager.createTitle("Power Orb Expired!", 20)
                                } else if (name.contains("Mana Flux") && SkyblockReinvented.config.manaTimeout) {
                                    GuiManager.createTitle("Power Orb Expired!", 20)
                                } else if (name.contains("Overflux") && SkyblockReinvented.config.overfluxTimeout) {
                                    GuiManager.createTitle("Power Orb Expired!", 20)
                                } else if (name.contains("Plasmaflux") && SkyblockReinvented.config.plasmaTimeout) {
                                    GuiManager.createTitle("Power Orb Expired!", 20)
                                }
                            }
                        }
                        if (SkyblockReinvented.config.renderPowerOrb) {
                            event.entity.alwaysRenderNameTag = false
                            cancelParticles()
                        }
                    }
                }
            }
        }
    }

    companion object {
        var isOrb = false
    }
}