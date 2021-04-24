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
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.gui.RenderUtil;
import thecudster.sre.util.sbutil.Utils;

import java.awt.*;

public class BoxUnkilledMobs {
    @SubscribeEvent
    public void onRender(RenderLivingEvent.Pre event) {
        /*
        * Code to detect starred mobs taken from Skytils under GNU Affero General Public License.
        * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
        * @author My-Name-Is-Jeff
        * @author Sychic
         */
        if (!Minecraft.getMinecraft().thePlayer.canEntityBeSeen(event.entity)) { return; }
        if (!Utils.inDungeons || !Utils.inSkyblock) { return; }
        if (SkyblockReinvented.config.outlineMobs) {
            String name = StringUtils.stripControlCodes(event.entity.getCustomNameTag());
            if (name.startsWith("✯ ") && name.contains("❤")) {
                if (name.contains("Lost Adventurer") || name.contains("Angry Archaeologist") || name.contains("Lurker") || name.contains("Dreadlord") || name.contains("Souleater") || name.contains("Zombie") || name.contains("Skeleton") || name.contains("Skeletor") || name.contains("Sniper") || name.contains("Super Archer") || name.contains("Spider") || name.contains("Fels") || name.contains("Withermancer")) {
                    for (Entity e : Minecraft.getMinecraft().theWorld.loadedEntityList) {
                        if (!(e instanceof EntityPlayerSP)) {
                            if (e.getDistanceToEntity(event.entity) <= 3) {
                                RenderUtil.drawOutlinedBoundingBox(e.getEntityBoundingBox(), new Color(255, 0, 0, 255), 3f, 1f);
                            }
                        }
                    }
                }
            }
        }
    }
}
