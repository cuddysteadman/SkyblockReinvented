package thecudster.sre.features.impl.qol;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.gui.*;
import thecudster.sre.util.gui.colours.CommonColors;
import thecudster.sre.util.sbutil.CurrentLoc;
import thecudster.sre.util.sbutil.LootTracker;
import thecudster.sre.util.sbutil.ScoreboardUtil;
import thecudster.sre.util.sbutil.Utils;

import java.util.List;

public class GhostLoot {
    static {
        new GhostGUI();
    }
    public static class GhostGUI extends GuiElement {
        public GhostGUI() {
            super("Ghost GUI", new FloatPair(0, 0));
            SkyblockReinvented.GUIMANAGER.registerElement(this);
        }
        @Override
        public void render() {
            ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
            boolean leftAlign = getActualX() < sr.getScaledWidth() / 2f;
            for (int i = 0; i < LootTracker.display.length; i++) {
                SmartFontRenderer.TextAlignment alignment = leftAlign ? SmartFontRenderer.TextAlignment.LEFT_RIGHT : SmartFontRenderer.TextAlignment.RIGHT_LEFT;
                ScreenRenderer.fontRenderer.drawString(LootTracker.display[i], leftAlign ? this.getActualX() : this.getActualX() + this.getWidth(), this.getActualY() + i * ScreenRenderer.fontRenderer.FONT_HEIGHT, CommonColors.WHITE, alignment, SmartFontRenderer.TextShadow.NORMAL);
            }
        }

        @Override
        public void demoRender() {
            ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
            boolean leftAlign = getActualX() < sr.getScaledWidth() / 2f;
            for (int i = 0; i < LootTracker.display.length; i++) {
                SmartFontRenderer.TextAlignment alignment = leftAlign ? SmartFontRenderer.TextAlignment.LEFT_RIGHT : SmartFontRenderer.TextAlignment.RIGHT_LEFT;
                ScreenRenderer.fontRenderer.drawString(LootTracker.display[i], leftAlign ? this.getActualX() : this.getActualX() + this.getWidth(), this.getActualY() + i * ScreenRenderer.fontRenderer.FONT_HEIGHT, CommonColors.WHITE, alignment, SmartFontRenderer.TextShadow.NORMAL);
            }
        }

        @Override
        public boolean getToggled() {
            return SkyblockReinvented.config.ghostTracker;
        }

        @Override
        public int getHeight() {
            return ScreenRenderer.fontRenderer.FONT_HEIGHT * 6;
        }

        @Override
        public int getWidth() {
            return ScreenRenderer.fontRenderer.getStringWidth(LootTracker.display[5]);
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
