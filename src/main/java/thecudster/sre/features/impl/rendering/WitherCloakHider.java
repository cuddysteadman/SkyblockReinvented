package thecudster.sre.features.impl.rendering;

import java.util.Currency;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.sbutil.CurrentLoc;
import thecudster.sre.util.sbutil.ScoreboardUtil;

public class WitherCloakHider {
	/**
     * Modified from Skytils under GNU Affero General Public license.
     * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
     */
	@SubscribeEvent(receiveCanceled = true, priority = EventPriority.HIGHEST)
	public void onCheckRender(RenderLivingEvent.Pre<EntityCreeper> event) {
		
		if (!(event.entity instanceof EntityCreeper)) { return; }
		EntityCreeper creeperEntity = (EntityCreeper) event.entity;
		EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
		if (CurrentLoc.currentLoc == null) {return;}
		if (CurrentLoc.currentLoc.equals("The Mist")) {
			return;
		}
		if (event.entity.getDistance(player.posX, player.posY, player.posZ) <= 5) { return; }
		
			if (creeperEntity instanceof EntityCreeper && SkyblockReinvented.config.renderCreepers && creeperEntity.getPowered()) {
				event.setCanceled(true);
			}
		
		
	}
}
