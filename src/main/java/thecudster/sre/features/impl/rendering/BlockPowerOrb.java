package thecudster.sre.features.impl.rendering;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.CancelParticleHelper;

public class BlockPowerOrb {
	
	@SubscribeEvent
	public void onRender(RenderLivingEvent.Pre event) {
	if (!SkyblockReinvented.config.renderPowerOrb) { return; }	
	
	if (!event.entity.canBeCollidedWith() && event.entity instanceof EntityArmorStand && !event.entity.hasCustomName()) {
		
		Minecraft.getMinecraft().theWorld.removeEntity(event.entity);
		Minecraft.getMinecraft().theWorld.removeWorldAccess(Minecraft.getMinecraft().renderGlobal);
		Minecraft.getMinecraft().theWorld.addWorldAccess(new CancelParticleHelper());
	}
	
	/*if (event.entity instanceof EntityArmorStand) {
		EntityArmorStand stand = (EntityArmorStand) event.entity;
		if (stand.getDisplayName().toString().contains("Radiant") || stand.getDisplayName().toString().contains("Mana Flux") 
				|| stand.getDisplayName().toString().contains("Overflux") || stand.getDisplayName().toString().contains("Plasmaflux")) {
			Minecraft.getMinecraft().theWorld.removeEntity(event.entity);
		}
	}*/
		String name = event.entity.getCustomNameTag();	
		boolean isOrb = name.contains("Radiant") || name.contains("Mana Flux") || name.contains("Overflux") || name.contains("Plasmaflux");
		if (isOrb) {
			Minecraft.getMinecraft().theWorld.removeEntity(event.entity);
		}		
	}
	
}
