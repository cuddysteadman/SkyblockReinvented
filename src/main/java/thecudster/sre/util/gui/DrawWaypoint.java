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

package thecudster.sre.util.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

import java.awt.*;

/*
* Taken from Skytils under GNU Affero Public License.
* https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
* @author Sychic
* @author My-Name-Is-Jeff
 */
public class DrawWaypoint {
    public static void drawWaypoint(float partialTicks, BlockPos pos, String text) {

        Entity viewer = Minecraft.getMinecraft().getRenderViewEntity();
        double viewerX = viewer.lastTickPosX + (viewer.posX - viewer.lastTickPosX) * partialTicks;
        double viewerY = viewer.lastTickPosY + (viewer.posY - viewer.lastTickPosY) * partialTicks;
        double viewerZ = viewer.lastTickPosZ + (viewer.posZ - viewer.lastTickPosZ) * partialTicks;


        double x = pos.getX() - viewerX;
        double y = pos.getY() - viewerY;
        double z = pos.getZ() - viewerZ;
        double distSq = x*x + y*y + z*z;

        GlStateManager.disableDepth();
        GlStateManager.disableCull();
        RenderUtil.drawFilledBoundingBox(new AxisAlignedBB(x, y + 2, z, x + 1, y + 3, z + 1), new Color(2, 250, 39), 1f);
        GlStateManager.disableTexture2D();
        if (distSq > 5*5) RenderUtil.renderBeaconBeam(x, y + 2, z, new Color(2, 250, 39).getRGB(), 1.0f, partialTicks);
        RenderUtil.renderWaypointText(text, pos.up(5), partialTicks);
        GlStateManager.disableLighting();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.enableCull();
    }

}
