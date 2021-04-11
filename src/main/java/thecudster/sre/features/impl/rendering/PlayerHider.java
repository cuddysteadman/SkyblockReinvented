package thecudster.sre.features.impl.rendering;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.gui.DrawWaypoint;
import thecudster.sre.util.sbutil.Utils;

public class PlayerHider {
	/**
     * Modified from SkyblockBonus under GNU General Public license.
     * https://github.com/TGMDevelopment/SkyBlockBonus/blob/main/LICENSE
     * @author MatthewTGM
     */

	@SubscribeEvent(receiveCanceled = true, priority = EventPriority.HIGHEST)
	public void onEntityRender(RenderLivingEvent.Pre event) {
		if (event.entity instanceof EntityPlayerSP) return;
        if (!(event.entity instanceof EntityOtherPlayerMP)) return;
        if (this.isNPC(event.entity)) { return; }
        boolean found = false;
		String str = ((EntityOtherPlayerMP) event.entity).getDisplayNameString();
		if (Utils.inDungeons) { return; }
		if (str.contains("Goblin") || str.contains("Ice Walker")) {
			return;
		}
		for (String s : SkyblockReinvented.config.listToRender) {
			if (((EntityOtherPlayerMP) event.entity).getDisplayNameString().contains(s)) { found = true; }
		}
		if (!found && SkyblockReinvented.config.renderPlayers) {
			event.setCanceled(true);
		}

		if (SkyblockReinvented.config.renderWaypointDungeons && Utils.inDungeons) {
			DrawWaypoint.drawWaypoint(0.01f, event.entity.getPosition().down(), ((EntityOtherPlayerMP) event.entity).getDisplayNameString());
		}
	}
	/**
	 * Taken from SkyblockAddons under MIT License
	 * https://github.com/BiscuitDevelopment/SkyblockAddons/blob/master/LICENSE
	 * @author BiscuitDevelopment
	 */
	public boolean isNPC(Entity entity) {
        if (!(entity instanceof EntityOtherPlayerMP)) {
            return false;
        }
        EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
        return entity.getUniqueID().version() == 2 && entityLivingBase.getHealth() == 20.0F && !entityLivingBase.isPlayerSleeping();
    }
}
