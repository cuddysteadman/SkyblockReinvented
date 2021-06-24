package thecudster.sre.core.gui;

import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;

public class SimpleButton extends GuiButton {
    public boolean hasPlayedSound = false;
    public String filterName = null;

    public SimpleButton(int buttonId, int x, int y, String buttonText) {
        super(buttonId, x, y, buttonText);
    }

    public SimpleButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
    }

    public SimpleButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, String buttonName) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.filterName = buttonName;
    }
    @Override
    public void playPressSound(SoundHandler soundHandlerIn) {
        //if (!hasPlayedSound) {
          //  super.playPressSound(soundHandlerIn);
          //  hasPlayedSound = true;
        //}
    }
}
