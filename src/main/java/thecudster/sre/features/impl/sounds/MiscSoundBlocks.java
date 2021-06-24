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

package thecudster.sre.features.impl.sounds;

import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.events.PacketEvent;
import thecudster.sre.features.impl.filter.FilterHandler;
import thecudster.sre.features.impl.qol.MiscGUIs;
import thecudster.sre.util.Utils;

/*
 * Modified from Skytils under GNU Affero Public License.
 * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
 * @author My-Name-Is-Jeff
 * @author Sychic
 */
public class MiscSoundBlocks {
	@SubscribeEvent(receiveCanceled=true)
	public void onReceivePacket(PacketEvent.ReceiveEvent event) {
		if (!Utils.inSkyblock) return;
		if (event.packet instanceof S29PacketSoundEffect) {
			S29PacketSoundEffect packet = (S29PacketSoundEffect) event.packet;
			if (SkyblockReinvented.config.creeperSounds) {
				if (packet.getSoundName().equals("mob.creeper.say") && FilterHandler.witherCloak) {
					event.setCanceled(true);
				}
				if (packet.getSoundName().equals("mob.skeleton.hurt") && FilterHandler.witherCloak) {
					event.setCanceled(true);
				}
			}
			if (MiscGUIs.inReforge) {
				if (packet.getSoundName().equals("random.anvil_use")) {
					event.setCanceled(true);
					return;
				}
			}
		}
	}
}
