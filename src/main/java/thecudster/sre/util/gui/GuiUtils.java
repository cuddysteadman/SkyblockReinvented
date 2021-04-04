package thecudster.sre.util.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiButton;
import sun.java2d.loops.FillRect;

public class GuiUtils {
    public static void DrawMeter(int num, int max, int left, int top, int right, int bottom, double scale) {
        Gui.drawRect(left + 10, top - 10, right + 10, bottom + 10, 255);
        Gui.drawRect(left, top, right, bottom, 100);


    }
}
