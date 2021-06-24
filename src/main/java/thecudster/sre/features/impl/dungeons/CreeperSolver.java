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
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.events.PacketEvent;
import thecudster.sre.core.gui.RenderUtils;
import thecudster.sre.util.Utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CreeperSolver {
    public static int solved = 0;
    public static boolean finishedPuzzle = false;
    public static boolean startedPuzzle = false;

    @SubscribeEvent
    public void onCreeperJoin(EntityJoinWorldEvent event) {
        if (event.entity instanceof EntityCreeper && Utils.inDungeons) {
            startedPuzzle = true;
        }
    }

    @SubscribeEvent
    public void onRender(RenderLivingEvent.Pre event) {
        if (SkyblockReinvented.config.guardianBeams && Utils.inDungeons && startedPuzzle && !finishedPuzzle && event.entity instanceof EntityGuardian) {
            event.setCanceled(true);
        }
    }
    @SubscribeEvent
    public void onReceivePacket (PacketEvent.ReceiveEvent event) {
        if (!Utils.inSkyblock || !Utils.inDungeons) { return; }
        if (event.packet instanceof S29PacketSoundEffect) {
            S29PacketSoundEffect packet = (S29PacketSoundEffect) event.packet;
            if (packet.getSoundName().equals("mob.creeper.say")) {
                if (packet.getPitch() == packet.getVolume() && packet.getVolume() == 1.0f) {
                    solved++;
                }
            } else if (packet.getSoundName().equals("creeper.primed")) {
                if (packet.getPitch() == 0.4920635f && packet.getVolume() == 1.0f) {
                    finishedPuzzle = true;
                }
            }
        }
    }

}
