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

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.sbutil.CurrentLoc;
import thecudster.sre.util.sbutil.Utils;

import java.util.ArrayList;
import java.util.List;

public class HideIncorrectLivids {
    public static boolean foundLivid = false;
    public static Entity livid = null;
    List<Entity> loadedLivids = new ArrayList<Entity>();
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        if (SkyblockReinvented.ticks % 20 != 0) { return; }
        if (Utils.inDungeons && !foundLivid && Minecraft.getMinecraft().theWorld != null && SkyblockReinvented.config.hideLivids) {
            if (CurrentLoc.currentLoc.equals("The Catacombs (F5)") || CurrentLoc.currentLoc.equals("The Catacombs (M5)")) {
                for (Entity entity : Minecraft.getMinecraft().theWorld.loadedEntityList) {
                    String name = entity.getName();
                    if (name.contains("Livid") && name.length() > 5) {
                        if (!loadedLivids.contains(entity)) {
                            loadedLivids.add(entity);
                        } else if (!entity.equals(livid)) {
                            entity.setInvisible(true);
                        }
                    }
                }
                if (loadedLivids.size() > 8) {
                    livid = loadedLivids.get(0);
                    foundLivid = true;
                }
            }
        }
    }
}
