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

package thecudster.sre.features.impl.rendering;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.monster.*;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.core.gui.RenderUtils;
import thecudster.sre.util.sbutil.ItemUtil;
import thecudster.sre.util.Utils;

import java.awt.*;

public class HyperionOverlay {
    public String name = "";
    @SubscribeEvent
    public void hyperionOverlay(RenderLivingEvent.Pre event) {
        if (event.entity instanceof EntityOtherPlayerMP) {
            return;
        }
        if (!Utils.inSkyblock) return;
        if (SkyblockReinvented.config.hyperionOverlay) {
            EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
            Vec3 rot = player.getLookVec();
            if (Minecraft.getMinecraft().thePlayer.getHeldItem() != null) {
                if (ItemUtil.getSkyBlockItemID(Minecraft.getMinecraft().thePlayer.getHeldItem()) != null)
                    name = ItemUtil.getSkyBlockItemID(Minecraft.getMinecraft().thePlayer.getHeldItem());
                if (name.equals("HYPERION") || name.equals("VALKYRIE") || name.equals("SCYLLA") || name.equals("ASTREA")) { // guessing on scylla because no metadata on wiki but who uses scylla lol
                    boolean distance = event.entity.getDistance(rot.xCoord * 6 + player.posX, player.posY + rot.yCoord * 6, player.posZ + rot.zCoord * 6) <= 6;
                    if (distance) {
                        if (event.entity instanceof EntityZombie || event.entity instanceof EntitySilverfish ||
                                event.entity instanceof EntitySkeleton || event.entity instanceof EntityEnderman ||
                                event.entity instanceof EntitySpider) {
                            if (player.canEntityBeSeen(event.entity)) {
                                RenderUtils.drawFilledBoundingBox(event.entity.getEntityBoundingBox(), new Color(27, 255, 11, 255), 3);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
}
