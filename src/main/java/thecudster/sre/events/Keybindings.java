package thecudster.sre.events;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import thecudster.sre.SkyblockReinvented;
public class Keybindings {
    @SubscribeEvent
    public void onKey(KeyInputEvent event) {
        if (SkyblockReinvented.keyBindings[0].isPressed()) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/bz");
        }
        if (SkyblockReinvented.keyBindings[1].isPressed()) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/ah");
        }
        if (SkyblockReinvented.keyBindings[2].isPressed()) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/visit prtl");
        }
    }
}
