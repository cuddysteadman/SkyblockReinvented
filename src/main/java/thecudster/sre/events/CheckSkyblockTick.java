package thecudster.sre.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import thecudster.sre.util.SmartFontRenderer;
import thecudster.sre.util.Utils;
/* Modified from Wynntils under GNU Affero General Public License v3.0
* https://github.com/Wynntils/Wynntils/blob/development/LICENSE
* @author Wynntils
*/
public class CheckSkyblockTick {
    public static int ticks = 0;
    public static ScaledResolution screen = null;
    public static SmartFontRenderer fontRenderer = null;
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        refresh();

        if (ticks % 20 == 0) {
            if (Minecraft.getMinecraft().thePlayer != null) {
                Utils.checkForSkyblock();
                Utils.checkForDungeons();
            }
            ticks = 0;
        }

        ticks++;
    }
    public static void refresh() {
        Minecraft mc = Minecraft.getMinecraft();
        screen = new ScaledResolution(mc);
        if (fontRenderer == null) {
            try {
                fontRenderer = new SmartFontRenderer();
            } catch (Throwable ignored) {
            } finally {
                if (fontRenderer != null) {
                    if (mc.gameSettings.language != null) {
                        fontRenderer.setUnicodeFlag(mc.isUnicode());
                        fontRenderer.setBidiFlag(mc.getLanguageManager().isCurrentLanguageBidirectional());
                    }
                    ((IReloadableResourceManager)mc.getResourceManager()).registerReloadListener(fontRenderer);
                }
            }
        }
    }

}
