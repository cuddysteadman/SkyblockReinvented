package thecudster.sre.core.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.core.UpdateChecker;
import thecudster.sre.core.gui.colours.CommonColors;

public class UpdateCheckerGUI extends GuiScreen {
    UpdateChecker checker = new UpdateChecker();
    SimpleButton update;
    SimpleButton mainMenu;

    @Override
    public void initGui() {
        super.initGui();
        if (checker.newVersionExists() != null) {

        }
    }
    @Override
    public void actionPerformed(GuiButton button) {
        if (button == update) {

        } else if (button == mainMenu) {
            SkyblockReinvented.currentGui = new GuiMainMenu();
        }
    }
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.pushMatrix();
        GlStateManager.translate(width / -1f, height * -0.2, 0.0f); // other option: GlStateManager.translate(width / -2f, height * -0.2f, 0.0f); GlStateManager.scale(2.0f, 2.0f, 1.0f);
        GlStateManager.scale(3.0f, 3.0f, 1.0f);
        ScreenRenderer.fontRenderer.drawString("Version" + checker.newVersionExists() + " is available!", width / 2, (int) (height * 0.1), CommonColors.RAINBOW, SmartFontRenderer.TextAlignment.MIDDLE, SmartFontRenderer.TextShadow.NORMAL);
        GlStateManager.popMatrix();
        ScreenRenderer.fontRenderer.drawString("by theCudster", width / 2, (int) (height * 0.6), CommonColors.LIGHT_GRAY, SmartFontRenderer.TextAlignment.MIDDLE, SmartFontRenderer.TextShadow.NORMAL);
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
    }
}
