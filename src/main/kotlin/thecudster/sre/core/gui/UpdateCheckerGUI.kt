package thecudster.sre.core.gui

import net.minecraft.client.gui.GuiScreen
import thecudster.sre.core.UpdateChecker
import thecudster.sre.core.gui.SimpleButton
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.GuiMainMenu
import net.minecraft.client.renderer.GlStateManager
import thecudster.sre.SkyblockReinvented
import thecudster.sre.core.gui.ScreenRenderer
import thecudster.sre.core.gui.colours.CommonColors
import thecudster.sre.core.gui.SmartFontRenderer

class UpdateCheckerGUI : GuiScreen() {
    var checker = UpdateChecker()
    var update: SimpleButton? = null
    var mainMenu: SimpleButton? = null
    override fun initGui() {
        super.initGui()
        if (checker.newVersionExists() != null) {
        }
    }

    public override fun actionPerformed(button: GuiButton) {
        if (button === update) {
        } else if (button === mainMenu) {
            SkyblockReinvented.currentGui = GuiMainMenu()
        }
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawDefaultBackground()
        super.drawScreen(mouseX, mouseY, partialTicks)
        GlStateManager.disableLighting()
        GlStateManager.disableDepth()
        GlStateManager.pushMatrix()
        GlStateManager.translate(
            (width / -1f).toDouble(),
            height * -0.2,
            0.0
        ) // other option: GlStateManager.translate(width / -2f, height * -0.2f, 0.0f); GlStateManager.scale(2.0f, 2.0f, 1.0f);
        GlStateManager.scale(3.0f, 3.0f, 1.0f)
        ScreenRenderer.fontRenderer.drawString(
            "Version" + checker.newVersionExists() + " is available!",
            (width / 2).toFloat(),
            (height * 0.1).toInt().toFloat(),
            CommonColors.RAINBOW,
            SmartFontRenderer.TextAlignment.MIDDLE,
            SmartFontRenderer.TextShadow.NORMAL
        )
        GlStateManager.popMatrix()
        ScreenRenderer.fontRenderer.drawString(
            "by theCudster",
            (width / 2).toFloat(),
            (height * 0.6).toInt().toFloat(),
            CommonColors.LIGHT_GRAY,
            SmartFontRenderer.TextAlignment.MIDDLE,
            SmartFontRenderer.TextShadow.NORMAL
        )
        GlStateManager.enableLighting()
        GlStateManager.enableDepth()
    }
}