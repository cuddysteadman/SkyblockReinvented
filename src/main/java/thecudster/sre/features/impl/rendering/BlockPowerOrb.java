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
import thecudster.sre.util.CancelParticle;
import thecudster.sre.util.CancelParticleHelper;
import thecudster.sre.util.sbutil.Utils;

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
					event.setCanceled(true);
					CancelParticle.cancel();
				}
			}
		}
	}
}
