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
import thecudster.sre.util.Utils;
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
        if (!Utils.inSkyblock) return;
        if (SkyblockReinvented.config.removeRaffleTitles) {
            if (event.packet instanceof S45PacketTitle) {
                S45PacketTitle packet = (S45PacketTitle) event.packet;
                if (packet.getMessage() != null) {
                    String unformatted = StringUtils.stripControlCodes(packet.getMessage().getUnformattedText());
                    Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(unformatted));
                    for (NetworkPlayerInfo p : TabListUtils.getTabEntries()) {
                        if (unformatted.contains(p.getDisplayName().getUnformattedText())) {
                            event.setCanceled(true);
                        }
                    }
                    if (unformatted.contains("WON 3X EVENT REWARDS")) {
                        event.setCanceled(true);
                    }
                }
            }
        }
    }
}
