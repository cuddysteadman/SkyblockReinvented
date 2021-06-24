package thecudster.sre.features.impl.qol;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.events.WorldChangeEvent;

public class MiscInputs {
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (SkyblockReinvented.config.joinSB) {
            if (WorldChangeEvent.notInSB) {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play skyblock");
                WorldChangeEvent.notInSB = false;
            }
        }
    }
    @SubscribeEvent
    public void onMouseInput(InputEvent.MouseInputEvent event) {
        if (SkyblockReinvented.config.joinSB) {
            if (WorldChangeEvent.notInSB) {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play skyblock");
                WorldChangeEvent.notInSB = false;
            }
        }
    }
}
