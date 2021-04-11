package thecudster.sre.features.impl.rendering;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.network.play.server.S45PacketTitle;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StringUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.events.ReceivePacketEvent;
import thecudster.sre.util.TabListUtils;

/**
 * Modified from Skytils under GNU Affero General Public license.
 * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
 * @author Sychic
 * @author My-Name-Is-Jeff
 * @author Angry-Pineapple3121
 * @author AzuredBlue
 */
public class RemoveRaffleTitles {
    @SubscribeEvent
    public void onReceivePacket(ReceivePacketEvent event) {
        // if (!Utils.inSkyblock) return;
        if (SkyblockReinvented.config.removeRaffleTitles) {
            if (event.packet instanceof S45PacketTitle) {
                S45PacketTitle packet = (S45PacketTitle) event.packet;
                if (packet.getMessage() != null) {
                    String unformatted = StringUtils.stripControlCodes(packet.getMessage().getUnformattedText());
                    for (NetworkPlayerInfo i : TabListUtils.getTabEntries()) {
                        if (i != null) {
                            if (unformatted.contains(i.getDisplayName().getUnformattedText())) {
                                event.setCanceled(true);
                            }
                        }
                    }
                    if (unformatted.contains("WINNER #1 (for 3x rewards!)") || unformatted.contains("WINNER #2 (for 3x rewards!") || unformatted.contains("WINNER #3 (for 3x rewards")) {
                        event.setCanceled(true);
                    }
                    if (unformatted.contains("MVP")) {
                        event.setCanceled(true);
                    }
                    if (unformatted.contains("VIP")) {
                        event.setCanceled(true);
                    }
                    if (unformatted.contains("WINNER")) {
                        event.setCanceled(true);
                    }
                }
            }
        }
        if (SkyblockReinvented.config.dangerGhosts) {
            if (event.packet instanceof S45PacketTitle) {
                S45PacketTitle packet = (S45PacketTitle) event.packet;
                if (packet.getMessage() != null) {
                    String unformatted = StringUtils.stripControlCodes(packet.getMessage().getUnformattedText());
                    Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(unformatted));
                    if (unformatted.contains("Powerful creatures reside in the Mist")) {
                        event.setCanceled(true);
                    }
                    if (unformatted.contains("DANGER")) {
                        event.setCanceled(true);
                    }
                }
            }
        }
    }
}
