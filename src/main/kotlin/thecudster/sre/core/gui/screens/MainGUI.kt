package thecudster.sre.core.gui.screens

import gg.essential.vigilance.VigilanceConfig
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.renderer.GlStateManager
import net.minecraftforge.client.event.GuiScreenEvent.DrawScreenEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import thecudster.sre.SkyblockReinvented
import thecudster.sre.core.gui.structure.ScreenRenderer
import thecudster.sre.core.gui.structure.SimpleButton
import thecudster.sre.core.gui.structure.SmartFontRenderer
import thecudster.sre.core.gui.structure.colours.CommonColors
import java.awt.Desktop
import java.io.IOException
import java.net.URI
import java.net.URISyntaxException

class MainGUI : GuiScreen() {
    private var configButton: SimpleButton? = null
    private var locationEditButton: SimpleButton? = null
    private var vigilanceEditButton: SimpleButton? = null
    private var githubButton: SimpleButton? = null
    private var discordButton: SimpleButton? = null
    private var closeGUI: SimpleButton? = null
    private var filter: SimpleButton? = null
    private var playerFilter: SimpleButton? = null
    private var craftingHUDConfig: SimpleButton? = null

    private var hasOpenedDiscord = false
    private var hasOpenedGithub = false
    override fun doesGuiPauseGame(): Boolean {
        return false
    }

    override fun initGui() {
        super.initGui()
        val sr = ScaledResolution(Minecraft.getMinecraft())
        val height = sr.scaledHeight
        val width = sr.scaledWidth
        configButton = SimpleButton(0, width / 2 - 100, (height * 0.3).toInt(), "Config")
        filter = SimpleButton(0, width / 2 - 100, (height * 0.35).toInt(), "Edit Filter")
        playerFilter = SimpleButton(0, width / 2 - 100, (height * 0.4).toInt(), "Edit Player Whitelist")
        locationEditButton = SimpleButton(2, width / 2 - 100, (height * 0.45).toInt(), "Edit Locations")
        vigilanceEditButton = SimpleButton(3, width / 2 - 100, (height * 0.5).toInt(), "Edit Vigilance")
        craftingHUDConfig = SimpleButton(69, width / 2 - 100, (height * 0.55).toInt(), "Edit Forge Crafting")

        githubButton = SimpleButton(4, width / 2 + 5, (height * 0.6).toInt(), 95, 20, "GitHub")
        discordButton = SimpleButton(5, width / 2 - 100, (height * 0.6).toInt(), 95, 20, "Discord")
        closeGUI = SimpleButton(1, width / 2 - 100, (height * 0.7).toInt(), "Close")
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
            "SkyblockReinvented",
            (width / 2).toFloat(),
            ((height * 0.1).toInt()).toFloat(),
            CommonColors.RAINBOW,
            SmartFontRenderer.TextAlignment.MIDDLE,
            SmartFontRenderer.TextShadow.NORMAL
        )
        GlStateManager.popMatrix()
        GlStateManager.pushMatrix()
        GlStateManager.translate(
            (width / -2f).toDouble(),
            height * -0.2,
            0.0
        ) // other option: GlStateManager.translate(width / -2f, height * -0.2f, 0.0f); GlStateManager.scale(2.0f, 2.0f, 1.0f);
        GlStateManager.scale(2.0f, 2.0f, 1.0f)
        ScreenRenderer.fontRenderer.drawString(
            "Main GUI",
            (width / 2).toFloat(),
            ((height * 0.2).toInt()).toFloat(),
            CommonColors.LIGHT_GRAY,
            SmartFontRenderer.TextAlignment.MIDDLE,
            SmartFontRenderer.TextShadow.NORMAL
        )
        GlStateManager.popMatrix()
        ScreenRenderer.fontRenderer.drawString(
            "by theCudster",
            (width / 2).toFloat(),
            ((height * 0.77).toInt()).toFloat(),
            CommonColors.LIGHT_GRAY,
            SmartFontRenderer.TextAlignment.MIDDLE,
            SmartFontRenderer.TextShadow.NORMAL
        )
        GlStateManager.enableLighting()
        GlStateManager.enableDepth()
        buttonList.add(configButton)
        buttonList.add(closeGUI)
        buttonList.add(locationEditButton)
        buttonList.add(vigilanceEditButton)
        buttonList.add(githubButton)
        buttonList.add(discordButton)
        buttonList.add(filter)
        buttonList.add(playerFilter)
        buttonList.add(craftingHUDConfig)
    }

    public override fun actionPerformed(button: GuiButton) {
        if (button is SimpleButton) {
            button.hasPlayedSound = false
        }
        if (button === configButton) {
            SkyblockReinvented.currentGui = SkyblockReinvented.config.gui()
        } else if (button === discordButton && !hasOpenedDiscord) {
            try {
                Desktop.getDesktop().browse(URI("https://discord.com/invite/xkeYgZrRbN"))
                hasOpenedDiscord = true
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: URISyntaxException) {
                e.printStackTrace()
            }
        } else if (button === githubButton && !hasOpenedGithub) {
            try {
                Desktop.getDesktop().browse(URI("https://github.com/theCudster/SkyblockReinvented"))
                hasOpenedGithub = true
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: URISyntaxException) {
                e.printStackTrace()
            }
        } else if (button === locationEditButton) {
            SkyblockReinvented.currentGui = LocationEditGUI()
        } else if (button === vigilanceEditButton) {
            SkyblockReinvented.currentGui = VigilanceConfig.gui()
        } else if (button === closeGUI) {
            Minecraft.getMinecraft().thePlayer.closeScreen()
        } else if (button === filter) {
            SkyblockReinvented.currentGui = FilterGUI()
        } else if (button === playerFilter) {
            SkyblockReinvented.currentGui = RenderPlayersGUI()
        } else if (button === craftingHUDConfig) {
            SkyblockReinvented.currentGui = CraftingHUDConfig()
        }
    }

    @Throws(IOException::class)
    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        super.mouseClicked(mouseX, mouseY, mouseButton)
    }

    @Throws(IOException::class)
    override fun keyTyped(typedChar: Char, keyCode: Int) {
        super.keyTyped(typedChar, keyCode)
    }

    @SubscribeEvent
    fun onGuiDraw(event: DrawScreenEvent.Pre) {
        if (Minecraft.getMinecraft().currentScreen !is MainGUI) return
        val tooltipToDisplay = ArrayList<String>()
        val mouseX = event.mouseX
        val mouseY = event.mouseY
        if (getShouldDraw(mouseX, mouseY, filter)) {
        } else if (getShouldDraw(mouseX, mouseY, playerFilter)) {
        }
    }

    private fun getShouldDraw(mouseX: Int, mouseY: Int, button: GuiButton?): Boolean {
        return mouseX > button!!.xPosition && mouseY < button.yPosition && mouseX < button.xPosition + button.buttonWidth && mouseY < button.yPosition + button.height
    }

    private val filterTooltips = arrayOf<String>()
    private val playerFilterTooltips = arrayOf<String>()
}