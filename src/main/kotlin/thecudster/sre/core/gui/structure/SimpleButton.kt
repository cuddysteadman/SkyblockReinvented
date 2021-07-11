package thecudster.sre.core.gui.structure

import net.minecraft.client.audio.SoundHandler
import net.minecraft.client.gui.GuiButton

class SimpleButton : GuiButton {
    var hasPlayedSound = false
    var filterName: String? = null

    constructor(buttonId: Int, x: Int, y: Int, buttonText: String?) : super(buttonId, x, y, buttonText) {}
    constructor(buttonId: Int, x: Int, y: Int, widthIn: Int, heightIn: Int, buttonText: String?) : super(
        buttonId,
        x,
        y,
        widthIn,
        heightIn,
        buttonText
    ) {
    }

    constructor(
        buttonId: Int,
        x: Int,
        y: Int,
        widthIn: Int,
        heightIn: Int,
        buttonText: String?,
        buttonName: String?
    ) : super(buttonId, x, y, widthIn, heightIn, buttonText) {
        filterName = buttonName
    }

    override fun playPressSound(soundHandlerIn: SoundHandler) {
        //if (!hasPlayedSound) {
        //  super.playPressSound(soundHandlerIn);
        //  hasPlayedSound = true;
        //}
    }
}