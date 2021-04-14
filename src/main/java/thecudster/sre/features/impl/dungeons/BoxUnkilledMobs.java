package thecudster.sre.features.impl.dungeons;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.gui.RenderUtil;

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
        // if (!event.entity.canEntityBeSeen(Minecraft.getMinecraft().thePlayer)) { return; }
        if (SkyblockReinvented.config.outlineMobs) {
            String name = StringUtils.stripControlCodes(event.entity.getCustomNameTag());
            if (name.startsWith("✯ ") && name.contains("❤")) {
                if (name.contains("Lurker") || name.contains("Dreadlord") || name.contains("Souleater") || name.contains("Zombie") || name.contains("Skeleton") || name.contains("Skeletor") || name.contains("Sniper") || name.contains("Super Archer") || name.contains("Spider") || name.contains("Fels") || name.contains("Withermancer")) {
                    for (Entity e : Minecraft.getMinecraft().theWorld.loadedEntityList) {
                        if (e instanceof EntityPlayer) { return; }
                        if (e.getDistanceToEntity(event.entity) <= 4) {
                            RenderUtil.drawOutlinedBoundingBox(e.getEntityBoundingBox(), new Color(255, 0, 0, 255), 3f, 1f);
                        }
                    }
                }
            }
        }
    }
}
