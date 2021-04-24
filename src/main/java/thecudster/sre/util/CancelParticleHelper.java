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

package thecudster.sre.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IWorldAccess;

public class CancelParticleHelper implements IWorldAccess {

	@Override
	public void markBlockForUpdate(BlockPos pos) {
		Minecraft.getMinecraft().renderGlobal.markBlockForUpdate(pos);
	}

	@Override
	public void notifyLightSet(BlockPos pos) {
		Minecraft.getMinecraft().renderGlobal.notifyLightSet(pos);
	}

	@Override
	public void markBlockRangeForRenderUpdate(int x1, int y1, int z1, int x2, int y2, int z2) {
		Minecraft.getMinecraft().renderGlobal.markBlockRangeForRenderUpdate(x1, y1, z1, x2, y2, z2);
	}

	@Override
	public void playSound(String soundName, double x, double y, double z, float volume, float pitch) {
		Minecraft.getMinecraft().renderGlobal.playSound(soundName, x, y, z, volume, pitch);
	}

	@Override
	public void playSoundToNearExcept(EntityPlayer except, String soundName, double x, double y, double z, float volume,
			float pitch) {
		Minecraft.getMinecraft().renderGlobal.playSoundToNearExcept(except, soundName, x, y, z, volume, pitch);
		
	}

	@Override
	public void spawnParticle(int particleID, boolean ignoreRange, double xCoord, double yCoord, double zCoord,
			double xOffset, double yOffset, double zOffset, int... p_180442_15_) {
	}

	@Override
	public void onEntityAdded(Entity entityIn) {
		Minecraft.getMinecraft().renderGlobal.onEntityAdded(entityIn);
	}

	@Override
	public void onEntityRemoved(Entity entityIn) {
		Minecraft.getMinecraft().renderGlobal.onEntityRemoved(entityIn);
	}

	@Override
	public void playRecord(String recordName, BlockPos blockPosIn) {
		Minecraft.getMinecraft().renderGlobal.playRecord(recordName, blockPosIn);
		
	}
	@Override
	public void broadcastSound(int p_180440_1_, BlockPos p_180440_2_, int p_180440_3_) {
		Minecraft.getMinecraft().renderGlobal.broadcastSound(p_180440_1_, p_180440_2_, p_180440_3_);
	}

	@Override
	public void playAuxSFX(EntityPlayer player, int sfxType, BlockPos blockPosIn, int p_180439_4_) {
		Minecraft.getMinecraft().renderGlobal.playAuxSFX(player, sfxType, blockPosIn, p_180439_4_);
	}

	@Override
	public void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress) {
		Minecraft.getMinecraft().renderGlobal.sendBlockBreakProgress(breakerId, pos, progress);
	}
	
}
