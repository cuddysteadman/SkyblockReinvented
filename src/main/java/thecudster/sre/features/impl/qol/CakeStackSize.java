package thecudster.sre.features.impl.qol;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.events.GuiRenderItemEvent;
import thecudster.sre.util.sbutil.ItemUtil;
import thecudster.sre.util.sbutil.Utils;

import java.util.List;

/**
 * Modified from Skytils under GNU Affero General Public license.
 * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
 * @author My-Name-Is-Jeff
 * @author Sychic
 */
public class CakeStackSize {
    @SubscribeEvent
    public void onRenderItemOverlayPos(GuiRenderItemEvent.RenderOverlayEvent.Post event) {
        ItemStack item = event.stack;
        if (item == null || item.stackSize != 1 || !SkyblockReinvented.config.cakeStackSize) return;
        String stackTip = "";
        if (item.hasDisplayName()) {
            if (item.getDisplayName().contains("New Year Cake")) {
                List<String> lore = ItemUtil.getItemLore(item);
                for (String s : lore) {
                    if (s.contains("celebration for the ")) {
                        s = s.substring(22);
                        if (s.contains("th")) {
                            s = s.substring(0, s.indexOf("th"));
                        }
                        else if (s.contains("st")) {
                            s = s.substring(0, s.indexOf("st"));
                        }
                        else if (s.contains("rd")) {
                            s = s.substring(0, s.indexOf("rd"));
                        }
                        else {
                            s = s.substring(0, s.indexOf("nd"));
                        }
                        stackTip = s;
                    }
                }
            }
        }
        if (stackTip.length() > 0) {
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            GlStateManager.disableBlend();
            event.fr.drawStringWithShadow(stackTip, (float) (event.x + 17 - event.fr.getStringWidth(stackTip)), (float) (event.y + 9), 16777215);
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
        }
    }
}
