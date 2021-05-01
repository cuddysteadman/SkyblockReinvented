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

import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.events.ReceivePacketEvent;
import thecudster.sre.util.sbutil.Utils;

public class SolvedCreeperSounds {
    @SubscribeEvent
    public void onReceivePacket (ReceivePacketEvent event) {
        if (!Utils.inSkyblock || !Utils.inDungeons) { return; }
        if (event.packet instanceof S29PacketSoundEffect) {
            S29PacketSoundEffect packet = (S29PacketSoundEffect) event.packet;
            if (packet.getSoundName().equals("mob.creeper.say")) {
                if (packet.getPitch() == packet.getVolume() && packet.getVolume() == 1.0f) {
                    CreeperSolver.solved++;
                }
            } else if (packet.getSoundName().equals("creeper.primed")) {
                if (packet.getPitch() == 0.4920635f && packet.getVolume() == 1.0f) {
                    CreeperSolver.finishedPuzzle = true;
                }
            }
        }
    }
}
