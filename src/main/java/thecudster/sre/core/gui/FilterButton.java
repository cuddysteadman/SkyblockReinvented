package thecudster.sre.core.gui;

import net.minecraft.client.gui.GuiButton;

public class FilterButton extends GuiButton {
    public FilterButton(int buttonId, int x, int y, String buttonText) {
        super(buttonId, x, y, buttonText);
    }

    public FilterButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
    }
}
