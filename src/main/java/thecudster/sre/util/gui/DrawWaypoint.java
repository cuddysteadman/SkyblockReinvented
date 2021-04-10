package thecudster.sre.util.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import thecudster.sre.util.gui.RenderUtil;

import java.awt.*;

/*
* Taken from Skytils under GNU Affero Public License.
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
