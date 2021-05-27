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

package thecudster.sre.features.impl.dungeons;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.events.ReceivePacketEvent;
import thecudster.sre.util.gui.DrawWaypoint;
import thecudster.sre.util.sbutil.Utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/*
* Modified from Danker's Skyblock Mod under GPL 3.0 license.
* https://github.com/bowser0000/SkyblockMod/blob/master/LICENSE
* @author bowser0000
 */
public class CreeperSolver {
    public static int solved = 0;
    public static boolean finishedPuzzle = false;
    public static Color[] colourList = {new Color(117, 219, 244), new Color(255, 222, 42), new Color(242, 23, 23),
            new Color(80, 239, 57), new Color(239, 125, 21), new Color(214, 224, 240)};
    static boolean drawCreeperLines = false;
    static Vec3 creeperLocation = new Vec3(0, 0, 0);
    static List<Vec3[]> creeperLines = new ArrayList<>();

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        Minecraft mc = Minecraft.getMinecraft();
        World world = mc.theWorld;
        EntityPlayerSP player = mc.thePlayer;
        if (SkyblockReinvented.ticks % 20 == 0) {
            if (SkyblockReinvented.config.betterCreeper && Utils.inDungeons && world != null && player != null) {
                double x = player.posX;
                double y = player.posY;
                double z = player.posZ;
                // Find creepers nearby
                AxisAlignedBB creeperScan = new AxisAlignedBB(x - 14, y - 8, z - 13, x + 14, y + 8, z + 13); // 28x16x26 cube
                List<EntityCreeper> creepers = world.getEntitiesWithinAABB(EntityCreeper.class, creeperScan);
                // Check if creeper is nearby
                if (creepers.size() > 0 && !creepers.get(0).isInvisible()) { // Don't show Wither Cloak creepers
                    EntityCreeper creeper = creepers.get(0);
                    // Start creeper line drawings
                    creeperLines.clear();
                    if (!drawCreeperLines) creeperLocation = new Vec3(creeper.posX, creeper.posY + 1, creeper.posZ);
                    drawCreeperLines = true;
                    // Search for nearby sea lanterns and prismarine blocks
                    BlockPos point1 = new BlockPos(creeper.posX - 14, creeper.posY - 7, creeper.posZ - 13);
                    BlockPos point2 = new BlockPos(creeper.posX + 14, creeper.posY + 10, creeper.posZ + 13);
                    Iterable<BlockPos> blocks = BlockPos.getAllInBox(point1, point2);
                    for (BlockPos blockPos : blocks) {
                        Block block = world.getBlockState(blockPos).getBlock();
                        if (block == Blocks.sea_lantern || block == Blocks.prismarine) {
                            // Connect block to nearest block on opposite side
                            Vec3 startBlock = new Vec3(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
                            BlockPos oppositeBlock = Utils.getFirstBlockPosAfterVectors(mc, startBlock, creeperLocation, 10, 20);
                            BlockPos endBlock = Utils.getNearbyBlock(mc, oppositeBlock, Blocks.sea_lantern, Blocks.prismarine);
                            if (endBlock != null && startBlock.yCoord > 68 && endBlock.getY() > 68) { // Don't create line underground
                                // Add to list for drawing
                                Vec3[] insertArray = {startBlock, new Vec3(endBlock.getX() + 0.5, endBlock.getY() + 0.5, endBlock.getZ() + 0.5)};
                                creeperLines.add(insertArray);
                            }
                        }
                    }
                } else {
                    drawCreeperLines = false;
                }
            }
        }
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if (SkyblockReinvented.config.betterCreeper && drawCreeperLines && !creeperLines.isEmpty()) {
            if (finishedPuzzle) { return; }
            if (solved > creeperLines.size() - 1) { return; }
            BlockPos blockOne = new BlockPos(creeperLines.get(solved)[0].xCoord, creeperLines.get(solved)[0].yCoord, creeperLines.get(solved)[0].zCoord);
            BlockPos blockTwo = new BlockPos(creeperLines.get(solved)[1].xCoord, creeperLines.get(solved)[1].yCoord, creeperLines.get(solved)[1].zCoord);
            DrawWaypoint.drawWaypointExtras(event.partialTicks, blockOne, null, colourList[solved], false);
            DrawWaypoint.drawWaypointExtras(event.partialTicks, blockTwo, null, colourList[solved], false);
            for (Entity e : Minecraft.getMinecraft().theWorld.loadedEntityList) {
                if (e instanceof EntityGuardian) {
                    Minecraft.getMinecraft().theWorld.removeEntity(e);
                    return;
                }
            }

        }
    }
    @SubscribeEvent
    public void onReceivePacket (ReceivePacketEvent event) {
        if (!Utils.inSkyblock || !Utils.inDungeons) { return; }
        if (event.packet instanceof S29PacketSoundEffect) {
            S29PacketSoundEffect packet = (S29PacketSoundEffect) event.packet;
            if (packet.getSoundName().equals("mob.creeper.say")) {
                if (packet.getPitch() == packet.getVolume() && packet.getVolume() == 1.0f) {
                    this.solved++;
                }
            } else if (packet.getSoundName().equals("creeper.primed")) {
                if (packet.getPitch() == 0.4920635f && packet.getVolume() == 1.0f) {
                    this.finishedPuzzle = true;
                }
            }
        }
    }

}
