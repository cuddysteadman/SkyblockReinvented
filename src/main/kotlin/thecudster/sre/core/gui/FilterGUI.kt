package thecudster.sre.core.gui

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.gui.GuiTextField
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import thecudster.sre.core.gui.colours.CommonColors
import net.minecraft.util.EnumChatFormatting
import thecudster.sre.features.impl.filter.FilterConcat
import net.minecraft.client.gui.GuiButton
import thecudster.sre.SkyblockReinvented
import thecudster.sre.features.impl.filter.Filter
import java.io.File
import java.io.IOException
import java.io.FileReader
import java.io.FileWriter
import java.lang.Exception
import java.util.ArrayList
import java.util.HashMap

class FilterGUI : GuiScreen() {
    private var type: SimpleButton? = null
    private var add: SimpleButton? = null
    private var needsDungeons: SimpleButton? = null
    private var close: SimpleButton? = null
    private val customFitlersJson = File(SkyblockReinvented.modDir, "customFilters.json")
    private val customConcatFiltersJson = File(SkyblockReinvented.modDir, "customConcatFilters.json")
    private var input: GuiTextField? = null
    private var typeToggle = false
    private var needsDungeonsToggle = false
    override fun doesGuiPauseGame(): Boolean {
        return false
    }

    override fun initGui() {
        super.initGui()
        try {
            if (!customFitlersJson.exists()) {
                customFitlersJson.createNewFile()
            }
            if (!customConcatFiltersJson.exists()) {
                customConcatFiltersJson.createNewFile()
            }
        } catch (ex: Exception) {
        }
        val sr = ScaledResolution(Minecraft.getMinecraft())
        val height = sr.scaledHeight
        val width = sr.scaledWidth
        type = SimpleButton(0, width / 2 - 100, (height * 0.3).toInt(), "Current Type: " + getType())
        needsDungeons = SimpleButton(1, width / 2 - 100, (height * 0.35).toInt(), "Needs Dungeons: $needsDugeons")
        input = GuiTextField(0, fontRendererObj, width / 2 - 98, (height * 0.4).toInt(), 196, 20)
        add = SimpleButton(2, width / 2 - 100, (height * 0.45).toInt(), "Add Filter")
        close = SimpleButton(1, width / 2 - 100, (height * 0.5).toInt(), "Close")
        input!!.visible = true
        input!!.setEnabled(true)
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
            "Custom Filters",
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
            (height * 0.57).toInt().toFloat(),
            CommonColors.LIGHT_GRAY,
            SmartFontRenderer.TextAlignment.MIDDLE,
            SmartFontRenderer.TextShadow.NORMAL
        )
        GlStateManager.enableLighting()
        GlStateManager.enableDepth()
        readConfig()
        refresh()
    }

    private val needsDugeons: String
        private get() = if (needsDungeonsToggle) {
            EnumChatFormatting.GREEN.toString() + "YES"
        } else {
            EnumChatFormatting.RED.toString() + "NO"
        }

    private fun getType(): String {
        return if (!typeToggle) {
            EnumChatFormatting.GREEN.toString() + "Simple"
        } else {
            EnumChatFormatting.GREEN.toString() + "Concatenation"
        }
    }

    private fun refresh() {
        buttonList.clear()
        type = SimpleButton(0, width / 2 - 100, (height * 0.3).toInt(), "Current Type: " + getType())
        needsDungeons = SimpleButton(1, width / 2 - 100, (height * 0.35).toInt(), "Needs Dungeons: $needsDugeons")
        buttonList.add(add)
        buttonList.add(type)
        buttonList.add(needsDungeons)
        buttonList.add(close)
        val sr = ScaledResolution(Minecraft.getMinecraft())
        var y = sr.scaledHeight / 5
        var x = sr.scaledHeight / 30
        var longest = 0
        for ((key, value) in customFilters) {
            if (value.messageArr.size > 0) {
                var s = value.messageArr[0]
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
                if (!buttons.contains(thisButton) && (customFilters.containsKey(thisButton.filterName) || customConcatFilters.containsKey(
                        thisButton.filterName
                    ))
                ) {
                    buttonList.add(thisButton)
                }
                y += (0.05f * ScreenRenderer.screen!!.scaledHeight).toInt()
                if (mc.fontRendererObj.getStringWidth(s) > longest) {
                    longest = mc.fontRendererObj.getStringWidth(s)
                }
            }
        }
        for ((key, value) in customConcatFilters) {
            if (value.concatCheck.size > 0) {
                var s = value.concatCheck.toString()
                if (s.length > 19) {
                    s = s.substring(0, 17) + "..."
                }
                if (y > sr.scaledHeight - sr.scaledHeight / 4) {
                    y = sr.scaledHeight / 5
                    x += longest + 1 + sr.scaledWidth / 20
                    longest = 0
                }
                if (x > sr.scaledWidth / 3.5 && x < sr.scaledWidth / 3.5 + sr.scaledWidth / 3) {
                    x += sr.scaledWidth / 4
                }
                GlStateManager.pushMatrix()
                GlStateManager.disableDepth()
                GlStateManager.disableLighting()
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
                val thisButton =
                    SimpleButton(-1 + y + x, x, y - (sr.scaledHeight * 0.05f).toInt(), 50, 20, "Remove", key)
                if (!buttons.contains(thisButton) && (customFilters.containsKey(thisButton.filterName) || customConcatFilters.containsKey(
                        thisButton.filterName
                    ))
                ) {
                    buttons.add(thisButton)
                }
                y += (0.05f * ScreenRenderer.screen!!.scaledHeight).toInt()
                if (mc.fontRendererObj.getStringWidth(s) > longest) {
                    longest = mc.fontRendererObj.getStringWidth(s)
                }
            }
        }
        for (button in buttons) {
            if (customConcatFilters.containsKey(button.filterName) || customFilters.containsKey(button.filterName)) {
                buttonList.add(button)
            }
        }
    }

    private val buttons = ArrayList<SimpleButton>()
    public override fun actionPerformed(button: GuiButton) {
        if (button.id > 5 && button is SimpleButton) {
            val simpleButton = button
            if (simpleButton.filterName != null) {
                if (customFilters.containsKey(button.filterName)) {
                    customFilters.remove(button.filterName)
                    buttons.remove(button)
                    buttonList.remove(button)
                    writeRemoveConfig(simpleButton.filterName as String)
                } else if (customConcatFilters.containsKey(button.filterName)) {
                    customConcatFilters.remove(button.filterName)
                    buttons.remove(button)
                    buttonList.remove(button)
                    writeRemoveConfig(simpleButton.filterName as String)
                }
            }
        }
        if (button === add) {
            SkyblockReinvented.filter!!.add(typeToggle, add(input!!.text), true)
            writeConfig(typeToggle, add(input!!.text) as Array<String?>, needsDungeonsToggle)
            input!!.text = ""
            input!!.maxStringLength = 100
        } else if (button === type) {
            typeToggle = !typeToggle
        } else if (button === needsDungeons) {
            needsDungeonsToggle = !needsDungeonsToggle
        } else if (button === close) {
            Minecraft.getMinecraft().displayGuiScreen(MainGUI())
            return
        }
        refresh()
    }

    private fun add(total: String): Array<String> {
        var tempTotal = total
        val temp = ArrayList<String>()
        if (tempTotal.length != 0) {
            if (!tempTotal.contains(",")) {
                return arrayOf(tempTotal)
            }
            while (tempTotal.contains(", ")) {
                temp.add(tempTotal.substring(0, tempTotal.indexOf(", ")))
                tempTotal = tempTotal.substring(tempTotal.indexOf(", ") + 2)
            }
            if (tempTotal.length > 0) {
                temp.add(tempTotal)
            }
        }
        val toArray = ArrayList<String>()
        for (i in temp.indices) {
            toArray.add(i.toString())
        }
        return toArray.toTypedArray()
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

    private val customFilters = HashMap<String, Filter>()
    private val customConcatFilters = HashMap<String, FilterConcat>()
    private val gson = GsonBuilder().setPrettyPrinting().create()
    fun readConfig() {
        var file: JsonObject
        try {
            FileReader(File(SkyblockReinvented.modDir, "customFilters.json")).use { `in` ->
                file = gson.fromJson(`in`, JsonObject::class.java)
                for (i in file.entrySet().indices) {
                    if (file["customFilter$i"] != null) {
                        val obj = file["customFilter$i"].asJsonObject["messageArr"].asJsonArray
                        val filterVal = arrayOfNulls<String>(obj.size())
                        for (j in 0 until obj.size()) {
                            filterVal[j] = obj[j].asString
                        }
                        val myFilter = Filter(
                            filterVal!!, file["customFilter$i"].asJsonObject["shouldCancel"].asBoolean,
                            file["customFilter$i"].asJsonObject["needsDungeons"].asBoolean
                        )
                        SkyblockReinvented.filter!!.add(
                            true,
                            myFilter.messageArr,
                            myFilter.shouldCancel,
                            myFilter.needsDungeons
                        )
                        customFilters["customFilter$i"] = myFilter
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        try {
            FileReader(File(SkyblockReinvented.modDir, "customConcatFilters.json")).use { `in` ->
                file = gson.fromJson(`in`, JsonObject::class.java)
                for (i in file.entrySet().indices) {
                    if (file["customFilter$i"] != null) {
                        val obj = file["customFilter$i"].asJsonObject["concatCheck"].asJsonArray
                        val filterVal = arrayOfNulls<String>(obj.size())
                        for (j in 0 until obj.size()) {
                            filterVal[j] = obj[j].asString
                        }
                        val myFilter = FilterConcat(
                            filterVal!!, file["customFilter$i"].asJsonObject["shouldCancel"].asBoolean,
                            file["customFilter$i"].asJsonObject["needsDungeons"].asBoolean
                        )
                        SkyblockReinvented.filter!!.add(
                            true,
                            myFilter.concatCheck,
                            myFilter.shouldCancel,
                            myFilter.needsDungeons
                        )
                        customConcatFilters["customFilter$i"] = myFilter
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun writeConfig(typeToggle: Boolean, check: Array<String?>, needsDungeonsToggle: Boolean) {
        readConfig()
        if (!typeToggle) {
            customFilters["customFilter" + customFilters.size] = Filter(check, true, needsDungeonsToggle)
            SkyblockReinvented.filter!!.add(false, check as Array<String>, true, needsDungeonsToggle)
        } else {
            customConcatFilters["customFilter" + customConcatFilters.size] =
                FilterConcat(check, true, needsDungeonsToggle)
            SkyblockReinvented.filter!!.add(true, check as Array<String>, true, needsDungeonsToggle)
        }
        try {
            FileWriter(File(SkyblockReinvented.modDir, "customFilters.json")).use { writer ->
                gson.toJson(
                    customFilters,
                    writer
                )
            }
        } catch (ex: Exception) {
        }
        try {
            FileWriter(File(SkyblockReinvented.modDir, "customConcatFilters.json")).use { writer ->
                gson.toJson(
                    customConcatFilters,
                    writer
                )
            }
        } catch (ex: Exception) {
        }
    }

    fun writeRemoveConfig(id: String) {
        if (customFilters.containsKey(id)) {
            customFilters.remove(customFilters.get(id) as String)
            SkyblockReinvented.filter!!.init()
            for ((_, value) in customFilters) {
                SkyblockReinvented.filter!!.filters.add(value)
            }
        } else if (customConcatFilters.containsKey(id)) {
            customConcatFilters.remove(customConcatFilters.get(id) as String)
            SkyblockReinvented.filter!!.init()
            for ((_, value) in customConcatFilters) {
                SkyblockReinvented.filter!!.filterConcats.add(value)
            }
        }
        try {
            FileWriter(File(SkyblockReinvented.modDir, "customFilters.json")).use { writer ->
                gson.toJson(
                    customFilters,
                    writer
                )
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}