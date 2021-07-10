package thecudster.sre.features.impl.qol

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent
import thecudster.sre.events.WorldChangeEvent
import net.minecraft.client.Minecraft
import net.minecraftforge.fml.common.gameevent.InputEvent
import thecudster.sre.SkyblockReinvented

class MiscInputs {
    @SubscribeEvent
    fun onKeyInput(event: KeyInputEvent?) {
        if (SkyblockReinvented.config.joinSB) {
            if (WorldChangeEvent.notInSB) {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play skyblock")
                WorldChangeEvent.notInSB = false
            }
        }
    }

    @SubscribeEvent
    fun onMouseInput(event: InputEvent.MouseInputEvent?) {
        if (SkyblockReinvented.config.joinSB) {
            if (WorldChangeEvent.notInSB) {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play skyblock")
                WorldChangeEvent.notInSB = false
            }
        }
    }
}