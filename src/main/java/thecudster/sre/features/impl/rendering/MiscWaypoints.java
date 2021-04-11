package thecudster.sre.features.impl.rendering;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.S05PacketSpawnPosition;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.gui.DrawWaypoint;
import thecudster.sre.events.ReceivePacketEvent;
import thecudster.sre.util.sbutil.ItemUtil;

public class MiscWaypoints {
    public BlockPos pos;
    @SubscribeEvent(receiveCanceled=true)
    public void onReceivePacket(ReceivePacketEvent event) {
        if (event.packet instanceof S05PacketSpawnPosition) {
            S05PacketSpawnPosition spawnPosition = (S05PacketSpawnPosition) event.packet;
            pos = spawnPosition.getSpawnPos();
        }

    }
    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        /*
        if (Utils.inDungeons && SkyblockReinvented.config.secretFinder) {
            for (TileEntity o : Minecraft.getMinecraft().theWorld.loadedTileEntityList) {
                if (o instanceof TileEntityChest) {
                    TileEntityChest chest = ((TileEntityChest) o);
                    if (chest.lidAngle == 0.0) {
                        DrawWaypoint.drawWaypoint(0.05f, ((TileEntityChest) o).getPos().down(), "Secret: Chest");
                    }
                }
            }

        }
        for (Entity o : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            if (o instanceof EntityItem) {
                ItemStack stack = new ItemStack(((EntityItem) o).getEntityItem().getItem());
                String name = null;
                name = stack.getDisplayName();

                if (name.contains("Trap") || name.contains("Decoy") || name.contains("Training Weights") || name.contains("Spirit Leap") || name.contains("Inflatable Jerry")
                || name.contains("Healing VIII") || name.contains("Dungeon Chest Key") || name.contains("Treasure Talisman")) {
                DrawWaypoint.drawWaypoint(0.05f, o.getPosition().down(), name); // "Secret: Drop"
                }

            }
        }
        if (Utils.inDungeons && SkyblockReinvented.config.secretFinder) {
            for (Entity e : Minecraft.getMinecraft().theWorld.loadedEntityList) {
                if (e instanceof EntityBat) {
                    DrawWaypoint.drawWaypoint(0.05f, e.getPosition().down(), "Secret: Bat");
                }
            }
        }
        for (TileEntity te : Minecraft.getMinecraft().theWorld.loadedTileEntityList) {
            if (te instanceof TileEntitySkull) {
                DrawWaypoint.drawWaypoint(0.05f, te.getPos().down(), "Secret: Skull");
            }
        }
        if (Utils.inSkyblock && SkyblockReinvented.config.endCrystalWaypoint) {
            for (Entity en : Minecraft.getMinecraft().theWorld.loadedEntityList) {
                if (en instanceof EntityEnderCrystal) {
                    DrawWaypoint.drawWaypoint(0.05f, en.getPosition().down(), "End Crystal");
                }
            }
        }
        if (Utils.inSkyblock && SkyblockReinvented.config.raffleWaypoint) {
            boolean found = false;
            boolean inDwarven = false;
            for (String s : ScoreboardUtil.getSidebarLines()) {
                if (s.contains("Dwarven Mines")) {
                    inDwarven = true;
                }
            }
            if (inDwarven) {
                for (TileEntity o : Minecraft.getMinecraft().theWorld.loadedTileEntityList) {
                    if (o instanceof BlockJukebox.TileEntityJukebox) {
                        if (found) {
                            return;
                        }
                        found = true;
                        BlockJukebox.TileEntityJukebox chest = ((BlockJukebox.TileEntityJukebox) o);
                        DrawWaypoint.drawWaypoint(0.05f, ((BlockJukebox.TileEntityJukebox) o).getPos().down(), "Raffle Box");
                    }
                }
            }
        }*/
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
