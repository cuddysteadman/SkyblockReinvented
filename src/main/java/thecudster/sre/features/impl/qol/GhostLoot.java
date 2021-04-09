package thecudster.sre.features.impl.qol;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.LootTracker;
import thecudster.sre.util.ScoreboardUtil;
import thecudster.sre.util.Utils;
import thecudster.sre.util.gui.TextRenderer;

import java.util.List;

public class GhostLoot {

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Pre event) {
        Utils.checkForSkyblock();
        if (SkyblockReinvented.config.ghostTracker && Utils.inSkyblock) {
            new TextRenderer(Minecraft.getMinecraft(), LootTracker.display, SkyblockReinvented.config.xVal, SkyblockReinvented.config.yVal, 1);
        }
    }
    @SubscribeEvent
    public void onDeath(LivingDeathEvent event) {
        if (event.entity instanceof EntityCreeper) {
            List<String> scoreboard = ScoreboardUtil.getSidebarLines();
            for (String s : scoreboard) {
                ScoreboardUtil.cleanSB(s);
                if (s.contains("The Mist")) {
                    EntityCreeper creeper = (EntityCreeper) event.entity;
                    if (creeper.getPowered()) {
                        LootTracker.ghostsSinceSorrow++;
                    }
                }
            }

        }
    }
}
