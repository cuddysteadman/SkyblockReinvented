package thecudster.sre.features.impl.rendering;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SlayerOverlays {
    /*
    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent event) {
        if (!event.isCancelable()) {
            Minecraft mc = Minecraft.getMinecraft();
            int posX = event.resolution.getScaledHeight() / 2 + 10;
            int posY = event.resolution.getScaledHeight() - 48;
            mc.renderEngine.bindTexture(new ResourceLocation("sre:mana.png"));
            mc.ingameGUI.drawTexturedModalRect(posX, posY, 0, 0, 74, 6);
            mc.ingameGUI.drawTexturedModalRect(posX + 1, posY + 1, 0, 6, 50, 6);
        }
    }*/
}
