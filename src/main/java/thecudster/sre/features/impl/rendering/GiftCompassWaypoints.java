package thecudster.sre.features.impl.rendering;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.S05PacketSpawnPosition;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.DrawWaypoint;
import thecudster.sre.events.ReceivePacketEvent;
import thecudster.sre.util.ItemUtil;

public class GiftCompassWaypoints {
    public BlockPos pos;
    @SubscribeEvent(receiveCanceled=true)
    public void onReceivePacket(ReceivePacketEvent event) {
        if (event.packet instanceof S05PacketSpawnPosition) {
            S05PacketSpawnPosition spawnPosition = (S05PacketSpawnPosition) event.packet;
            pos = spawnPosition.getSpawnPos();
            pos.add(0, 4, 0);
        }
    }
    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if (Minecraft.getMinecraft().thePlayer.getHeldItem() != null && ItemUtil.getSkyBlockItemID(Minecraft.getMinecraft().thePlayer.getHeldItem()) != null) {
                if (ItemUtil.getSkyBlockItemID(Minecraft.getMinecraft().thePlayer.getHeldItem()).equals("GIFT_COMPASS")) {
                    if (pos != null) {
                        if (SkyblockReinvented.config.giftCompassWaypoints) {
                            DrawWaypoint.drawWaypoint(event.partialTicks, pos, "Gift");
                        }
                    }
                }

        }
    }
}
