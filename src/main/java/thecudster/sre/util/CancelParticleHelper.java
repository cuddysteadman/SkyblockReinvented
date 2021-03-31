package thecudster.sre.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IWorldAccess;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CancelParticleHelper implements IWorldAccess {

	@Override
	public void markBlockForUpdate(BlockPos pos) {
		// TODO Auto-generated method stub
		Minecraft.getMinecraft().renderGlobal.markBlockForUpdate(pos);
	}

	@Override
	public void notifyLightSet(BlockPos pos) {
		// TODO Auto-generated method stub
		Minecraft.getMinecraft().renderGlobal.notifyLightSet(pos);
	}

	@Override
	public void markBlockRangeForRenderUpdate(int x1, int y1, int z1, int x2, int y2, int z2) {
		// TODO Auto-generated method stub
		Minecraft.getMinecraft().renderGlobal.markBlockRangeForRenderUpdate(x1, y1, z1, x2, y2, z2);
	}

	@Override
	public void playSound(String soundName, double x, double y, double z, float volume, float pitch) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEntityAdded(Entity entityIn) {
		// TODO Auto-generated method stub
		Minecraft.getMinecraft().renderGlobal.onEntityAdded(entityIn);
	}

	@Override
	public void onEntityRemoved(Entity entityIn) {
		// TODO Auto-generated method stub
		Minecraft.getMinecraft().renderGlobal.onEntityRemoved(entityIn);
	}

	@Override
	public void playRecord(String recordName, BlockPos blockPosIn) {
		// TODO Auto-generated method stub
		Minecraft.getMinecraft().renderGlobal.playRecord(recordName, blockPosIn);
		
	}
	@Override
	public void broadcastSound(int p_180440_1_, BlockPos p_180440_2_, int p_180440_3_) {
		// TODO Auto-generated method stub
		Minecraft.getMinecraft().renderGlobal.broadcastSound(p_180440_1_, p_180440_2_, p_180440_3_);
	}

	@Override
	public void playAuxSFX(EntityPlayer player, int sfxType, BlockPos blockPosIn, int p_180439_4_) {
		// TODO Auto-generated method stub
		Minecraft.getMinecraft().renderGlobal.playAuxSFX(player, sfxType, blockPosIn, p_180439_4_);
	}

	@Override
	public void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress) {
		// TODO Auto-generated method stub
		Minecraft.getMinecraft().renderGlobal.sendBlockBreakProgress(breakerId, pos, progress);
	}
	
}
