package thecudster.sre.features.impl.rendering;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.CancelParticleHelper;

public class BlockPowerOrb {
	public boolean isOrb = false;
	@SubscribeEvent()
	public void onRender(RenderLivingEvent.Pre<EntityArmorStand> event) {
	if (!SkyblockReinvented.config.renderPowerOrb) { return; }
	if (event.entity instanceof EntityArmorStand) {
		EntityArmorStand entity = (EntityArmorStand) event.entity;
		String name = entity.getCustomNameTag();
		if (name != null) {
			isOrb = name.contains("Radiant") || name.contains("Mana Flux") || name.contains("Overflux") || name.contains("Plasmaflux");
			if (isOrb) {
				event.entity.setInvisible(true);
			}
		}
		if (entity.getCurrentArmor(3) == null) { return; }
		if (entity.getCurrentArmor(3).getTagCompound() == null) {return;}
		if (entity.getCurrentArmor(3).getTagCompound().getCompoundTag("SkullOwner") == null) { return; }
		if (entity.getCurrentArmor(3).getTagCompound().getCompoundTag("SkullOwner").getString("Id") == null) {return;}
		String skullID = entity.getCurrentArmor(3).getTagCompound().getCompoundTag("SkullOwner").getString("Id");
	}
	/*

		for (Entity e : Minecraft.getMinecraft().theWorld.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(event.entity.posX, event.entity.posY, event.entity.posZ, event.entity.posX + 3, event.entity.posY + 3, event.entity.posZ + 3))) {
			String name = e.getCustomNameTag();
			Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(name));
			if (name != null) {
				isOrb = name.contains("Radiant") || name.contains("Mana Flux") || name.contains("Overflux") || name.contains("Plasmaflux");
				if (isOrb) {
					event.setCanceled(true);
					Minecraft.getMinecraft().theWorld.removeEntity(event.entity);
					Minecraft.getMinecraft().theWorld.removeEntity(e);
				}
			}

	}*/

	}
	
}
