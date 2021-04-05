package thecudster.sre.features.impl.rendering;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S05PacketSpawnPosition;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.gui.DrawWaypoint;
import thecudster.sre.events.ReceivePacketEvent;
import thecudster.sre.util.ItemUtil;
import thecudster.sre.util.Utils;

public class MiscWaypoints {
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
        Utils.checkForSkyblock();
        Utils.checkForDungeons();
        if (Utils.inDungeons && SkyblockReinvented.config.secretFinder) {
            for (TileEntity o : Minecraft.getMinecraft().theWorld.loadedTileEntityList) {
                if (o instanceof TileEntityChest) {
                    TileEntityChest chest = ((TileEntityChest) o);
                    if (chest.lidAngle == 0.0) {
                        DrawWaypoint.drawWaypoint(0.05f, ((TileEntityChest) o).getPos().down(), "Secret: Chest");
                    }
                }
            }
            for (Entity o : Minecraft.getMinecraft().theWorld.loadedEntityList) {
                if (o instanceof EntityItem) {
                    ItemStack stack = new ItemStack(((EntityItem) o).getEntityItem().getItem());
                    String name = stack.getDisplayName();
                    if (name.contains("Trap") || name.contains("Decoy") || name.contains("Training Weights") || name.contains("Spirit Leap") || name.contains("Inflatable Jerry")
                    || name.contains("Healing VIII") || name.contains("Dungeon Chest Key") || name.contains("Treasure Talisman")) {
                        DrawWaypoint.drawWaypoint(0.05f, o.getPosition().down(), "Secret: Drop");
                    }

                }
            }
        }
        if (Utils.inSkyblock && SkyblockReinvented.config.endCrystalWaypoint) {
            for (Entity o : Minecraft.getMinecraft().theWorld.loadedEntityList) {
                if (o instanceof EntityEnderCrystal) {
                    DrawWaypoint.drawWaypoint(0.05f, o.getPosition().down(), "End Crystal");
                }
            }
        }
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
