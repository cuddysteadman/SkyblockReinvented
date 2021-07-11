package thecudster.sre.core.gui.screens

import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.GuiMainMenu
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.renderer.GlStateManager
import thecudster.sre.SkyblockReinvented
import thecudster.sre.core.UpdateChecker
import thecudster.sre.core.gui.structure.ScreenRenderer
import thecudster.sre.core.gui.structure.SimpleButton
import thecudster.sre.core.gui.structure.SmartFontRenderer
import thecudster.sre.core.gui.structure.colours.CommonColors

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
        )
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