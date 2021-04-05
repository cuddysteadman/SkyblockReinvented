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
import thecudster.sre.util.Utils;

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
        // if (event.entity.hasCustomName()) { return; }
		// System.out.println("not rendering!");
		if (SkyblockReinvented.config.renderPlayers) {
			event.setCanceled(true);

		}
		Utils.checkForSkyblock();
		Utils.checkForDungeons();
		String str = ((EntityOtherPlayerMP) event.entity).getDisplayNameString();
		if (str.contains("Crypt Dreadlord") || str.contains("Crypt Souleater") || str.contains("Lost Adventurer") || str.contains("Angry Archaeologist") ||
		str.contains("Putrid") || str.contains("Cannibal") || str.contains("Tear") || str.contains("Freak") || str.contains("Flamer") || str.contains("Undead") ||
		str.contains("Bonzo") || str.contains("Livid") || str.contains("The Professor") || str.contains("Scarf")) {
			return;
		}
		if (SkyblockReinvented.config.renderWaypointDungeons && Utils.inDungeons) {
			DrawWaypoint.drawWaypoint(0.01f, event.entity.getPosition().down(), ((EntityOtherPlayerMP) event.entity).getDisplayNameString());
		}
		/*boolean done = false;
		 * WHY ISN'T THIS WORKING REEE
		ArrayList<String> listPlayers = new ArrayList<String>();
		String s = new String(SkyblockReinvented.config.listToRender.toString());
		if (s == "") {event.setCanceled(true); return; }
		if (!s.contains(",")) {
			done = true;
		}
		boolean solo = false;
		if (s.length() > 0 && !(s.contains(","))) {
			solo = true;
		}
		while (!done && !solo) {
			
			listPlayers.add(s.substring(0, s.indexOf(",")));
			s = s.substring(s.indexOf("," + 1));
			if (s.substring(0, 1) == " ") {
				s = s.substring(1);
			}
			if (!s.contains(",")) {
				done = true;
			}
		}
		boolean found = false;
		Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(listPlayers.toString()));
		for (String player : listPlayers) {
			if (event.entity.getName().contains(player)){ found = true; }
			if (!found) {
			event.setCanceled(true);
			}
		}*/
		
		
		/**
		 * Taken from SkyblockAddons under MIT License
		 * https://github.com/BiscuitDevelopment/SkyblockAddons/blob/master/LICENSE
		 * @author BiscuitDevelopment
		 */
	}
	public boolean isNPC(Entity entity) {
        if (!(entity instanceof EntityOtherPlayerMP)) {
            return false;
        }
        EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
        return entity.getUniqueID().version() == 2 && entityLivingBase.getHealth() == 20.0F && !entityLivingBase.isPlayerSleeping();
    }
}
