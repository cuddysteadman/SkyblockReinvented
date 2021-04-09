package thecudster.sre.util;

/**
 * Modified from Skytils under GNU Affero General Public license.
 * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
 * @author Sychic
 * @author My-Name-Is-Jeff
 * @author Angry-Pineapple3121
 * @author AzuredBlue
 */
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuiManager {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static File positionFile;
    public static Map<String, FloatPair> GUIPOSITIONS;

    private static final Map<Integer, GuiElement> elements = new HashMap<>();
    private int counter = 0;
    private static final Map<String, GuiElement> names = new HashMap<>();

    public static String title = null;
    public static String subtitle = null;
    public static int titleDisplayTicks = 0;
    public static int subtitleDisplayTicks = 0;
    @SubscribeEvent
    public void renderPlayerInfo(final RenderGameOverlayEvent.Post event) {
        if (event.type != RenderGameOverlayEvent.ElementType.EXPERIENCE && event.type != RenderGameOverlayEvent.ElementType.JUMPBAR)
            return;
        for(Map.Entry<Integer, GuiElement> e : elements.entrySet()) {
            try {
                e.getValue().render();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        renderTitles(event.resolution);
    }
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        if (titleDisplayTicks > 0) {
            titleDisplayTicks--;
        } else {
            titleDisplayTicks = 0;
            GuiManager.title = null;
        }

        if (subtitleDisplayTicks > 0) {
            subtitleDisplayTicks--;
        } else {
            subtitleDisplayTicks = 0;
            GuiManager.subtitle = null;
        }
    }

    public static void createTitle(String title, int ticks) {
        Utils.playLoudSound("random.orb", 0.5);
        GuiManager.title = title;
        GuiManager.titleDisplayTicks = ticks;
    }

    private void renderTitles(ScaledResolution scaledResolution) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.theWorld == null || mc.thePlayer == null || !Utils.inSkyblock) {
            return;
        }

        int scaledWidth = scaledResolution.getScaledWidth();
        int scaledHeight = scaledResolution.getScaledHeight();
        if (title != null) {
            int stringWidth = mc.fontRendererObj.getStringWidth(title);

            float scale = 4; // Scale is normally 4, but if its larger than the screen, scale it down...
            if (stringWidth * scale > (scaledWidth * 0.9F)) {
                scale = (scaledWidth * 0.9F) / (float) stringWidth;
            }

            GlStateManager.pushMatrix();
            GlStateManager.translate((float) (scaledWidth / 2), (float) (scaledHeight / 2), 0.0F);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.pushMatrix();
            GlStateManager.scale(scale, scale, scale); // TODO Check if changing this scale breaks anything...

            mc.fontRendererObj.drawString(title, (float) (-mc.fontRendererObj.getStringWidth(title) / 2), -20.0F, 0xFF0000, true);

            GlStateManager.popMatrix();
            GlStateManager.popMatrix();
        }
        if (subtitle != null) {
            int stringWidth = mc.fontRendererObj.getStringWidth(subtitle);

            float scale = 2; // Scale is normally 2, but if its larger than the screen, scale it down...
            if (stringWidth * scale > (scaledWidth * 0.9F)) {
                scale = (scaledWidth * 0.9F) / (float) stringWidth;
            }

            GlStateManager.pushMatrix();
            GlStateManager.translate((float) (scaledWidth / 2), (float) (scaledHeight / 2), 0.0F);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.pushMatrix();
            GlStateManager.scale(scale, scale, scale);  // TODO Check if changing this scale breaks anything...

            mc.fontRendererObj.drawString(subtitle, -mc.fontRendererObj.getStringWidth(subtitle) / 2F, -23.0F,
                    0xFF0000, true);

            GlStateManager.popMatrix();
            GlStateManager.popMatrix();
        }
    }

}
