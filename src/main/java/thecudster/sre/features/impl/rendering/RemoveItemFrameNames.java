package thecudster.sre.features.impl.rendering;

import net.minecraftforge.client.event.RenderItemInFrameEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.sbutil.Utils;

public class RemoveItemFrameNames {
    @SubscribeEvent
    public void onRender(RenderItemInFrameEvent event) {
        if (!Utils.inSkyblock) { return; }
        if (!SkyblockReinvented.config.itemFrameNames) { return; }
        event.item.setStackDisplayName("");
    }
}
