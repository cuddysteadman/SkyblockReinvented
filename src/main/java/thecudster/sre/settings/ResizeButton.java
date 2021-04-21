package thecudster.sre.settings;
/**
 * Taken from Skytils under GNU Affero General Public license.
 * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
 * @author My-Name-Is-Jeff
 * @author Sychic
 */
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;
import thecudster.sre.util.gui.GuiElement;
import thecudster.sre.util.gui.RenderUtil;
import thecudster.sre.util.gui.colours.CommonColors;

public class ResizeButton extends GuiButton {
    public static final int SIZE = 2;

    public float x;
    public float y;

    private Corner corner;

    private float cornerOffsetX;
    private float cornerOffsetY;

    public GuiElement element;

    public ResizeButton(float x, float y, GuiElement element, Corner corner) {
        super(-1, 0, 0, null);
        this.element = element;
        this.corner = corner;
        this.x = x;
        this.y = y;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        float scale = element.getScale();
        hovered = mouseX >= x && mouseY >= y && mouseX < x + SIZE * 2 * scale && mouseY < y + SIZE * 2 * scale;
        int color = hovered ? CommonColors.WHITE.toInt() : CommonColors.WHITE.toInt(70);
        RenderUtil.drawRect(0,0, SIZE * 2, SIZE * 2, color);
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        ScaledResolution sr = new ScaledResolution(mc);
        float minecraftScale = sr.getScaleFactor();
        float floatMouseX = Mouse.getX() / minecraftScale;
        float floatMouseY = (mc.displayHeight - Mouse.getY()) / minecraftScale;

        cornerOffsetX = floatMouseX;
        cornerOffsetY = floatMouseY;

        return hovered;
    }

    public Corner getCorner() {
        return this.corner;
    }

    public enum Corner {
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_RIGHT,
        BOTTOM_LEFT
    }
}
