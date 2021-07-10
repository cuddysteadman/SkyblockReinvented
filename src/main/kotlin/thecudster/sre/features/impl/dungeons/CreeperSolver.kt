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
import net.minecraftforge.event.entity.EntityJoinWorldEvent
import net.minecraft.entity.monster.EntityCreeper
import thecudster.sre.features.impl.dungeons.CreeperSolver
import net.minecraftforge.client.event.RenderLivingEvent
import net.minecraft.entity.monster.EntityGuardian
import thecudster.sre.events.PacketEvent.ReceiveEvent
import net.minecraft.network.play.server.S29PacketSoundEffect
import thecudster.sre.SkyblockReinvented
import thecudster.sre.util.Utils

class CreeperSolver {
    @SubscribeEvent
    fun onCreeperJoin(event: EntityJoinWorldEvent) {
        if (event.entity is EntityCreeper && Utils.inDungeons) {
            startedPuzzle = true
        }
    }

    @SubscribeEvent
    fun onRender(event: RenderLivingEvent.Pre<*>) {
        if (SkyblockReinvented.config.guardianBeams && Utils.inDungeons && startedPuzzle && !finishedPuzzle && event.entity is EntityGuardian) {
            event.isCanceled = true
        }
    }

    @SubscribeEvent
    fun onReceivePacket(event: ReceiveEvent) {
        if (!Utils.inSkyblock || !Utils.inDungeons) {
            return
        }
        if (event.packet is S29PacketSoundEffect) {
            val packet = event.packet as S29PacketSoundEffect?
            if (packet!!.soundName == "mob.creeper.say") {
                if (packet.pitch == packet.volume && packet.volume == 1.0f) {
                    solved++
                }
            } else if (packet.soundName == "creeper.primed") {
                if (packet.pitch == 0.4920635f && packet.volume == 1.0f) {
                    finishedPuzzle = true
                }
            }
        }
    }

    companion object {
        var solved = 0
        var finishedPuzzle = false
        var startedPuzzle = false
    }
}