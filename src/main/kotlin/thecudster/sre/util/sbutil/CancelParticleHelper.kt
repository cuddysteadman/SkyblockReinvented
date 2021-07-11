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
package thecudster.sre.util.sbutil

import net.minecraft.client.Minecraft
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.BlockPos
import net.minecraft.world.IWorldAccess

class CancelParticleHelper : IWorldAccess {
    override fun markBlockForUpdate(pos: BlockPos) {
        Minecraft.getMinecraft().renderGlobal.markBlockForUpdate(pos)
    }

    override fun notifyLightSet(pos: BlockPos) {
        Minecraft.getMinecraft().renderGlobal.notifyLightSet(pos)
    }

    override fun markBlockRangeForRenderUpdate(x1: Int, y1: Int, z1: Int, x2: Int, y2: Int, z2: Int) {
        Minecraft.getMinecraft().renderGlobal.markBlockRangeForRenderUpdate(x1, y1, z1, x2, y2, z2)
    }

    override fun playSound(soundName: String, x: Double, y: Double, z: Double, volume: Float, pitch: Float) {
        Minecraft.getMinecraft().renderGlobal.playSound(soundName, x, y, z, volume, pitch)
    }

    override fun playSoundToNearExcept(
        except: EntityPlayer, soundName: String, x: Double, y: Double, z: Double, volume: Float,
        pitch: Float
    ) {
        Minecraft.getMinecraft().renderGlobal.playSoundToNearExcept(except, soundName, x, y, z, volume, pitch)
    }

    override fun spawnParticle(
        particleID: Int, ignoreRange: Boolean, xCoord: Double, yCoord: Double, zCoord: Double,
        xOffset: Double, yOffset: Double, zOffset: Double, vararg p_180442_15_: Int
    ) {
    }

    override fun onEntityAdded(entityIn: Entity) {
        Minecraft.getMinecraft().renderGlobal.onEntityAdded(entityIn)
    }

    override fun onEntityRemoved(entityIn: Entity) {
        Minecraft.getMinecraft().renderGlobal.onEntityRemoved(entityIn)
    }

    override fun playRecord(recordName: String, blockPosIn: BlockPos) {
        Minecraft.getMinecraft().renderGlobal.playRecord(recordName, blockPosIn)
    }

    override fun broadcastSound(p_180440_1_: Int, p_180440_2_: BlockPos, p_180440_3_: Int) {
        Minecraft.getMinecraft().renderGlobal.broadcastSound(p_180440_1_, p_180440_2_, p_180440_3_)
    }

    override fun playAuxSFX(player: EntityPlayer, sfxType: Int, blockPosIn: BlockPos, p_180439_4_: Int) {
        Minecraft.getMinecraft().renderGlobal.playAuxSFX(player, sfxType, blockPosIn, p_180439_4_)
    }

    override fun sendBlockBreakProgress(breakerId: Int, pos: BlockPos, progress: Int) {
        Minecraft.getMinecraft().renderGlobal.sendBlockBreakProgress(breakerId, pos, progress)
    }
}