package thecudster.sre.features.impl.qol;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.sbutil.CurrentLoc;
import thecudster.sre.util.sbutil.LootTracker;
import thecudster.sre.util.sbutil.ScoreboardUtil;
import thecudster.sre.util.sbutil.Utils;
import thecudster.sre.util.gui.TextRenderer;

import java.util.List;

public class GhostLoot {

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Pre event) {
        if (SkyblockReinvented.config.ghostTracker && Utils.inSkyblock) {
            new TextRenderer(Minecraft.getMinecraft(), LootTracker.display, SkyblockReinvented.config.xVal, SkyblockReinvented.config.yVal, 1);
        }
    }
    @SubscribeEvent
    public void onDeath(LivingDeathEvent event) {
        if (event.entity instanceof EntityCreeper) {
            if (CurrentLoc.currentLoc.equals("The Mist")) {
                EntityCreeper creeper = (EntityCreeper) event.entity;
                if (creeper.getPowered()) {
                    LootTracker.ghostsSinceSorrow++;
                }
            }
        }
    }
}
