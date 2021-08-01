package thecudster.sre.core.gui.screens

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.gui.GuiTextField
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.renderer.GlStateManager
import thecudster.sre.SkyblockReinvented
import thecudster.sre.core.gui.structure.ScreenRenderer
import thecudster.sre.core.gui.structure.SimpleButton
import thecudster.sre.core.gui.structure.SmartFontRenderer
import thecudster.sre.core.gui.structure.colours.CommonColors
import thecudster.sre.features.impl.filter.CustomPlayersFilter
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException

class RenderPlayersGUI : GuiScreen {

    constructor() {
        readConfig()
    }

    private var add: SimpleButton? = null
    private var close: SimpleButton? = null
    private val customPlayerFiltersJson = File(SkyblockReinvented.modDir, "customPlayersFilter.json")
    private var input: GuiTextField? = null
    private var addFriends: SimpleButton? = null
    override fun doesGuiPauseGame(): Boolean {
        return false
    }

    override fun initGui() {
        super.initGui()
        try {
            if (!customPlayerFiltersJson.exists()) {
                customPlayerFiltersJson.createNewFile()
            }
        } catch (ex: Exception) {
        }
        val sr = ScaledResolution(Minecraft.getMinecraft())
        val height = sr.scaledHeight
        val width = sr.scaledWidth
        add = SimpleButton(2, width / 2 - 100, (height * 0.3).toInt(), "Add Whitelisted Player")
        close = SimpleButton(1, width / 2 - 100, (height * 0.4).toInt(), "Close")
        input = GuiTextField(0, fontRendererObj, width / 2 - 98, (height * 0.35).toInt(), 196, 20)
        addFriends = SimpleButton(3, width / 2 - 100,(height * 0.45).toInt(), "Add Friends")
        input!!.visible = true
        input!!.setEnabled(true)
        customPlayersFilter.clear()
        readConfig()
        refresh()
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawDefaultBackground()
        super.drawScreen(mouseX, mouseY, partialTicks)
        input!!.drawTextBox()
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
            (height * 0.1).toInt().toFloat(),
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
            "Player Whitelist",
            (width / 2).toFloat(),
            (height * 0.2).toInt().toFloat(),
            CommonColors.LIGHT_GRAY,
            SmartFontRenderer.TextAlignment.MIDDLE,
            SmartFontRenderer.TextShadow.NORMAL
        )
        GlStateManager.popMatrix()
        ScreenRenderer.fontRenderer.drawString(
            "by theCudster",
            (width / 2).toFloat(),
            (height * 0.47).toInt().toFloat(),
            CommonColors.LIGHT_GRAY,
            SmartFontRenderer.TextAlignment.MIDDLE,
            SmartFontRenderer.TextShadow.NORMAL
        )
        GlStateManager.enableLighting()
        GlStateManager.enableDepth()
        readConfig()
        refresh()
    }

    private fun refresh() {
        buttonList.clear()
        buttonList.add(add)
        buttonList.add(close)
        val sr = ScaledResolution(Minecraft.getMinecraft())
        var y = sr.scaledHeight / 5
        var x = sr.scaledHeight / 30
        var longest = 0
        readConfig()
        for ((key, value) in customPlayersFilter) {
            if (value.playerName.length > 0) {
                var s = value.playerName
                if (s.length > 19) {
                    s = s.substring(0, 17) + "..."
                }
                if (y > sr.scaledHeight - sr.scaledHeight / 4) {
                    y = sr.scaledHeight / 5
                    x += longest + 1 + sr.scaledWidth / 20
                    longest = 0
                }
                if (x > sr.scaledWidth / 3.5 && x < sr.scaledWidth / 3.5 + sr.scaledWidth / 3) {
                    x += sr.scaledWidth / 3
                }
                GlStateManager.pushMatrix()
                GlStateManager.disableLighting()
                GlStateManager.disableDepth()
                ScreenRenderer.fontRenderer.drawString(
                    s,
                    (x + sr.scaledWidth / 18).toFloat(),
                    y.toFloat(),
                    CommonColors.LIGHT_GRAY,
                    SmartFontRenderer.TextAlignment.LEFT_RIGHT,
                    SmartFontRenderer.TextShadow.NORMAL
                )
                GlStateManager.enableLighting()
                GlStateManager.enableDepth()
                GlStateManager.popMatrix()
                val thisButton = SimpleButton(
                    -1 + x + y,
                    x,
                    y - (ScreenRenderer.screen!!.scaledHeight * 0.012).toInt(),
                    50,
                    20,
                    "Remove",
                    key
                )
                if (!buttons.contains(thisButton) && customPlayersFilter.containsKey(thisButton.filterName)) {
                    buttonList.add(thisButton)
                }
                y += (0.05f * ScreenRenderer.screen!!.scaledHeight).toInt()
                if (mc.fontRendererObj.getStringWidth(s) > longest) {
                    longest = mc.fontRendererObj.getStringWidth(s)
                }
            }
        }
        for (button in buttons) {
            buttonList.add(button)
        }
    }

    private val buttons = ArrayList<SimpleButton>()
    public override fun actionPerformed(button: GuiButton) {
        if (button.id > 5 && button is SimpleButton) {
            val simpleButton = button
            if (simpleButton.filterName != null) {
                if (customPlayersFilter.containsKey(button.filterName)) {
                    customPlayersFilter.remove(button.filterName)
                    buttons.remove(button)
                    buttonList.remove(button)
                    writeRemoveConfig(simpleButton.filterName as String)
                }
            }
        }
        if (button === add) {
            if (input!!.text != null) {
                SkyblockReinvented.config.listToRender.add(CustomPlayersFilter(input!!.text))
                customPlayersFilter["customPlayersFilter" + customPlayersFilter.size] = CustomPlayersFilter(
                    input!!.text
                )
                writeConfig()
                input!!.text = ""
                input!!.maxStringLength = 100
            }
        } else if (button === close) {
            Minecraft.getMinecraft().displayGuiScreen(MainGUI())
            return
        }
        refresh()
    }

    @Throws(IOException::class)
    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        super.mouseClicked(mouseX, mouseY, mouseButton)
        input!!.mouseClicked(mouseX, mouseY, mouseButton)
    }

    @Throws(IOException::class)
    override fun keyTyped(typedChar: Char, keyCode: Int) {
        super.keyTyped(typedChar, keyCode)
        input!!.textboxKeyTyped(typedChar, keyCode)
    }

    private val customPlayersFilter = HashMap<String, CustomPlayersFilter>()
    private val gson = GsonBuilder().setPrettyPrinting().create()
    fun readConfig() {
        var file: JsonObject
        SkyblockReinvented.config.listToRender.clear()
        try {
            FileReader(File(SkyblockReinvented.modDir, "customPlayersFilter.json")).use { `in` ->
                file = gson.fromJson(`in`, JsonObject::class.java)
                for (i in 0..file.entrySet().size) {
                    if (file["customPlayersFilter$i"] != null) {
                        val name = file["customPlayersFilter$i"].asJsonObject["playerName"].asString
                        SkyblockReinvented.config.listToRender.add(CustomPlayersFilter(name))
                        customPlayersFilter["customPlayersFilter$i"] = CustomPlayersFilter(name)
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun writeConfig() {
        try {
            FileWriter(File(SkyblockReinvented.modDir, "customPlayersFilter.json")).use { writer ->
                gson.toJson(
                    customPlayersFilter,
                    writer
                )
            }
        } catch (ex: Exception) {
        }
        readConfig()
    }

    fun writeRemoveConfig(id: String) {
        if (customPlayersFilter.containsKey(id)) {
            SkyblockReinvented.config.listToRender.remove(customPlayersFilter[id])
            customPlayersFilter.remove(customPlayersFilter[id].toString())
        }
        try {
            FileWriter(File(SkyblockReinvented.modDir, "customPlayersFilter.json")).use { writer ->
                gson.toJson(
                    customPlayersFilter,
                    writer
                )
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}