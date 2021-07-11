package thecudster.sre.features.impl.qol

import net.minecraft.client.Minecraft
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.InputEvent
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent
import thecudster.sre.SkyblockReinvented
import thecudster.sre.events.WorldChangeEvent
import thecudster.sre.util.Utils

class MiscInputs {
    @SubscribeEvent
    fun onKeyInput(event: KeyInputEvent?) {
        if (SkyblockReinvented.config.joinSB) {
            if (!Utils.inSkyblock) {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play skyblock")
                Utils.inSkyblock = true
                event!!.isCanceled = true
            }
        }
    }

    @SubscribeEvent
    fun onMouseInput(event: InputEvent.MouseInputEvent?) {
        if (SkyblockReinvented.config.joinSB) {
            if (!Utils.inSkyblock) {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play skyblock")
                Utils.inSkyblock = true
                event!!.isCanceled = true
            }
        }
    }
}