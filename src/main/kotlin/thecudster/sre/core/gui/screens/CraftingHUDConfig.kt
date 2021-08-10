package thecudster.sre.core.gui.screens

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.gui.GuiTextField
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.RenderHelper
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumChatFormatting
import org.lwjgl.input.Keyboard
import thecudster.sre.SkyblockReinvented
import thecudster.sre.core.gui.structure.ScreenRenderer
import thecudster.sre.core.gui.structure.SimpleButton
import thecudster.sre.core.gui.structure.SmartFontRenderer
import thecudster.sre.core.gui.structure.colours.CommonColors
import thecudster.sre.core.gui.structure.colours.CustomColor
import thecudster.sre.util.RenderUtils
import thecudster.sre.util.Utils
import thecudster.sre.util.Utils.getItem
import thecudster.sre.util.sbutil.ItemRarity
import thecudster.sre.util.sbutil.stripControlCodes
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import net.minecraft.init.Items as MinecraftItem

class CraftingHUDConfig : GuiScreen() {
    override fun doesGuiPauseGame(): Boolean {
        return false
    }

    private var close: SimpleButton? = null
    private var input: GuiTextField? = null
    private var test: DropdownButton? = null

    override fun initGui() {
        super.initGui()
        close = SimpleButton(1, width / 2 - 100, (height * 0.9).toInt(), "Close")
        input = GuiTextField(0, fontRendererObj, width - 250, (height * 0.26 - 1).toInt(), 200, 20)
        test = DropdownButton(Item.TITA_DRILL_3, width / 10, (height * 0.27).toInt(), false, null)

        input!!.setEnabled(true)
        input!!.visible = true
        input!!.enableBackgroundDrawing = true

        this.buttonList.add(close)
        this.buttonList.add(test)
        RenderUtils.renderItem(test!!.item.minecraftItem, 0, 0)
        Keyboard.enableRepeatEvents(true)
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawDefaultBackground()
        input!!.drawTextBox()
        // am i overlooking a method to do this automatically? i coulda sworn dsm does this automatically maybe i'm just dumb lol
        if (!Utils.drawHoveringText(input!!, "Search for an item...")) {
            if (input!!.text.isNotEmpty() && input!!.text.isNotBlank()) {
                GlStateManager.pushMatrix()
                GlStateManager.disableLighting()
                GlStateManager.disableDepth()
                GlStateManager.scale(2f, 2f, 2f)
                GlStateManager.translate(
                    (input!!.xPosition + input!!.width.toDouble()),
                    input!!.yPosition + 6.0,
                    0.0
                )
                ScreenRenderer.fontRenderer.drawString(
                    "▼",
                    input!!.xPosition + input!!.width.toFloat(),
                    input!!.yPosition + 6f,
                    CommonColors.GRAY,
                    SmartFontRenderer.TextAlignment.RIGHT_LEFT,
                    SmartFontRenderer.TextShadow.NORMAL
                )
                GlStateManager.popMatrix()
                if (buttonList.filterIsInstance<SearchOptionButton>().isNotEmpty()) {
                    GlStateManager.pushMatrix()
                    RenderUtils.drawFloatingRectDark(
                        input!!.xPosition, input!!.yPosition + 25, input!!.width + 1, 20 *
                                buttonList.filterIsInstance<SearchOptionButton>().size
                    )
                    GlStateManager.popMatrix()
                } else {
                    GlStateManager.pushMatrix()
                    RenderUtils.drawFloatingRectDark(
                        input!!.xPosition, input!!.yPosition + 25, input!!.width + 1, 20
                    )
                    GlStateManager.popMatrix()
                    ScreenRenderer.fontRenderer.drawString(
                        "No options found ):",
                        input!!.xPosition.toFloat() + 4,
                        input!!.yPosition.toFloat() + 30,
                        CommonColors.WHITE,
                        SmartFontRenderer.TextAlignment.LEFT_RIGHT,
                        SmartFontRenderer.TextShadow.NORMAL
                    )
                }
                buttonList.filterIsInstance<SearchOptionButton>().forEach {
                    if (it.item.minecraftItem != null) {
                        GlStateManager.pushMatrix()
                        GlStateManager.translate(it.xPosition.toFloat() - 33, it.yPosition.toFloat() - 14, 0.0f)
                        RenderHelper.enableGUIStandardItemLighting()
                        RenderUtils.renderItem(it.item.minecraftItem!!, 8, 8)
                        GlStateManager.disableLighting()
                        GlStateManager.popMatrix()
                    }
                }
                GlStateManager.enableLighting()
                GlStateManager.enableDepth()
            } else {
                GlStateManager.pushMatrix()
                RenderUtils.drawFloatingRectDark(
                    input!!.xPosition, input!!.yPosition + 25, input!!.width + 1, 20
                )
                GlStateManager.popMatrix()
                ScreenRenderer.fontRenderer.drawString(
                    "Search for a forgeable item!",
                    input!!.xPosition.toFloat() + 4,
                    input!!.yPosition.toFloat() + 30,
                    CommonColors.WHITE,
                    SmartFontRenderer.TextAlignment.LEFT_RIGHT,
                    SmartFontRenderer.TextShadow.NORMAL
                )
            }
        }

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
            "Forge Helper",
            (width / 2).toFloat(),
            (height * 0.2).toInt().toFloat(),
            CommonColors.LIGHT_GRAY,
            SmartFontRenderer.TextAlignment.MIDDLE,
            SmartFontRenderer.TextShadow.NORMAL
        )
        GlStateManager.popMatrix()
        GlStateManager.pushMatrix()
        ScreenRenderer.fontRenderer.drawString(
            "by theCudster",
            (width / 2).toFloat(),
            (height * 0.96).toInt().toFloat(),
            CommonColors.LIGHT_GRAY,
            SmartFontRenderer.TextAlignment.MIDDLE,
            SmartFontRenderer.TextShadow.NORMAL
        )
        GlStateManager.pushMatrix()
        GlStateManager.popMatrix()
        super.drawScreen(mouseX, mouseY, partialTicks)
        GlStateManager.enableLighting()
        GlStateManager.enableDepth()
        GlStateManager.popMatrix()
    }

    private var linesDrawn: Int? = null
    override fun actionPerformed(button: GuiButton) {
        if (linesDrawn == null) linesDrawn = test!!.yPosition
        if (button is DropdownButton) {
            button.toggled = !button.toggled
        }
        if (button === close) {
            Minecraft.getMinecraft().displayGuiScreen(MainGUI())
            return
        } else if (button is DropdownButton || button is nonDropdownButton) {
            if (button is DropdownButton && button.item.subcategory != null) {
                if (button.toggled) {
                    val x = if (button.xPosition + 200 > input!!.xPosition) width / 10 else button.xPosition + 160
                    var lowest = 0
                    if (button != test && button.xPosition + 200 > input!!.xPosition) {
                        buttonList.filter { it is DropdownButton || it is nonDropdownButton }.forEach {
                            if (it is DropdownButton) {
                                if (it.yPosition > lowest) {
                                    lowest = it.yPosition
                                }
                            } else if (it is nonDropdownButton) {
                                if (it.yPosition > lowest) {
                                    lowest = it.yPosition
                                }
                            }
                        }
                        linesDrawn = test!!.yPosition + lowest / 2 + 20
                    }
                    var y = linesDrawn!!
                    button.item.subcategory!!.forEach { entry ->
                        if (entry.key.subcategory != null) {
                            buttonList.add(DropdownButton(entry.key, x, y, false, button.item))
                            y += 18
                        } else {
                            buttonList.add(nonDropdownButton(entry.key, x, y, false, button.item))
                            y += 18
                        }
                    }
                    button.displayString = EnumChatFormatting.YELLOW.toString() + button.displayString
                } else {
                    button.displayString = button.displayString.stripControlCodes()
                    buttonList.removeIf { it is DropdownButton && it.parent == button.item || it is nonDropdownButton && it.parent == button.item }
                    if (button != test && button.xPosition + 200 > input!!.xPosition) {
                        linesDrawn = test!!.yPosition
                    }
                }
                // refresh()
            }
        } else if (button is SearchOptionButton) {
            buttonList.remove(test)
            test = DropdownButton(button.item, width / 10, (height * 0.27).toInt(), false, null)
            buttonList.add(test)
            buttonList.removeIf { it is SearchOptionButton }
            input!!.isFocused = false
        }
    }

    private fun refresh() {
        buttonList.remove(test)
        val temp = buttonList.toList()
        buttonList.clear()
        var y = (height * 0.2).toInt()
        temp.forEach {
            if (it is DropdownButton) {
                y += 18
                buttonList.add(DropdownButton(it.item, y, it.xPosition, it.toggled, it.parent))
            } else if (it is nonDropdownButton) {
                y += 18
                buttonList.add(nonDropdownButton(it.item, y, it.xPosition, it.toggled, it.parent))
            }
        }
        buttonList.add(test)
        buttonList.add(close)
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
        if (input!!.isFocused) {
            buttonList.removeIf { it is SearchOptionButton }
            if (input!!.text.isEmpty()) {
                return
            }
            val closestMatches = arrayListOf<Item>()
            Item.values().forEach {
                if (it.itemName.lowercase()
                        .contains(input!!.text.lowercase()) && closestMatches.size < 6 && it.subcategory != null
                ) {
                    closestMatches.add(it)
                }
            }
            var y = input!!.yPosition + 30
            closestMatches.forEach {
                if (it.minecraftItem != null) {
                    buttonList.add(SearchOptionButton(it, input!!.xPosition + 30, y))
                } else {
                    buttonList.add(SearchOptionButton(it, input!!.xPosition + 5, y))
                }
                y += 20
            }
        }
    }

    override fun onGuiClosed() {
        if (!File(SkyblockReinvented.modDir, "forgeGUI.json").exists()) File(
            SkyblockReinvented.modDir,
            "ForgeGUI.json"
        ).createNewFile()
        writeConfig()
    }

    private var openGUI = hashMapOf<String, GuiButton>()

    fun readConfig() {
        if (!File(SkyblockReinvented.modDir, "forgeGUI.json").exists()) File(
            SkyblockReinvented.modDir,
            "ForgeGUI.json"
        ).createNewFile()
        var file: JsonObject
        try {
            FileReader(File(SkyblockReinvented.modDir, "forgeGUI.json")).use {
                file = gson.fromJson(it, JsonObject::class.java)
                for (i in file.entrySet().indices) {
                    if (file["customPlayersFilter$i"] != null) {

                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    private val gson = GsonBuilder().setPrettyPrinting().create()
    fun writeConfig() {
        if (!File(SkyblockReinvented.modDir, "forgeGUI.json").exists()) File(
            SkyblockReinvented.modDir,
            "ForgeGUI.json"
        ).createNewFile()
        for (i in 0 until buttonList.size) {
            openGUI[i.toString()] = buttonList[i]
        }
        try {
            FileWriter(File(SkyblockReinvented.modDir, "forgeGUI.json")).use { writer ->
                gson.toJson(
                    openGUI,
                    writer
                )
            }
        } catch (ex: Exception) {
        }
        readConfig()
    }

    companion object {
        val drillItem = getItem(MinecraftItem.prismarine_shard, true)
    }

    init {
        if (!File(SkyblockReinvented.modDir, "forgeGUI.json").exists()) File(
            SkyblockReinvented.modDir,
            "ForgeGUI.json"
        ).createNewFile()
        readConfig()
    }
}

enum class Item(
    var itemName: String,
    var resourceLoc: String,
    var subcategory: HashMap<Item, Int>?,
    var rarity: ItemRarity,
    var minecraftItem: ItemStack? = null,
    var toggled: Boolean = false
) {
    ENCHANTED_MITHRIL("Enchanted Mithril", "", null, ItemRarity.RARE, getItem(MinecraftItem.prismarine_crystals, true)),
    REFINED_MITHRIL(
        "Refined Mithril",
        "sre:forge/refmithril.png",
        hashMapOf(ENCHANTED_MITHRIL to 160),
        ItemRarity.EPIC
    ),
    ENCHANTED_TITANIUM("Enchanted Titanium", "sre:forge/etita.png", null, ItemRarity.EPIC),
    REFINED_TITANIUM(
        "Refined Titanium",
        "sre:forge/reftita.png",
        hashMapOf(ENCHANTED_TITANIUM to 16),
        ItemRarity.LEGENDARY
    ),
    GOLD("Gold", "", null, ItemRarity.COMMON, getItem(MinecraftItem.gold_ingot, true)),
    ENCHANTED_GOLD(
        "Enchanted Gold",
        "",
        hashMapOf(GOLD to 160),
        ItemRarity.UNCOMMON,
        getItem(MinecraftItem.gold_ingot, true)
    ),
    ENCHANTED_GOLD_BLOCK(
        "Enchanted Gold Block",
        "",
        hashMapOf(ENCHANTED_GOLD to 160),
        ItemRarity.RARE,
        getItem(Blocks.gold_block, true)
    ),
    GLACITE_JEWEL("Glacite Jewel", "sre:forge/gjewel.png", null, ItemRarity.RARE),
    DIAMOND("Diamond", "", null, ItemRarity.COMMON, getItem(MinecraftItem.diamond, false)),
    ENCHANTED_DIAMOND(
        "Enchanted Diamond",
        "",
        hashMapOf(DIAMOND to 160),
        ItemRarity.UNCOMMON,
        getItem(MinecraftItem.diamond, true)
    ),
    ENCHANTED_DIAMOND_BLOCK(
        "Enchanted Diamond Block",
        "",
        hashMapOf(ENCHANTED_DIAMOND to 160),
        ItemRarity.RARE,
        getItem(Blocks.diamond_block, true)
    ),
    REFINED_DIAMOND(
        "Refined Diamond",
        "",
        hashMapOf(ENCHANTED_DIAMOND_BLOCK to 2),
        ItemRarity.EPIC,
        getItem(MinecraftItem.diamond, true)
    ),
    COAL("Coal", "", null, ItemRarity.COMMON, getItem(MinecraftItem.coal, false)),
    ENCHANTED_COAL(
        "Enchanted Coal",
        "",
        hashMapOf(COAL to 160),
        ItemRarity.UNCOMMON,
        getItem(MinecraftItem.coal, true)
    ),
    ENCHANTED_COAL_BLOCK(
        "Enchanted Coal Block",
        "",
        hashMapOf(ENCHANTED_COAL to 160),
        ItemRarity.RARE,
        getItem(Blocks.coal_block, true)
    ),
    IRON("Iron", "", null, ItemRarity.COMMON, getItem(MinecraftItem.iron_ingot, false)),
    ENCHANTED_IRON(
        "Enchanted Iron",
        "",
        hashMapOf(IRON to 160),
        ItemRarity.UNCOMMON,
        getItem(MinecraftItem.iron_ingot, true)
    ),
    ENCHANTED_IRON_BLOCK(
        "Enchanted Iron Block",
        "",
        hashMapOf(ENCHANTED_IRON to 160),
        ItemRarity.RARE,
        getItem(Blocks.iron_block, true)
    ),
    REDSTONE("Redstone", "", null, ItemRarity.COMMON, getItem(MinecraftItem.redstone, false)),
    ENCHANTED_REDSTONE(
        "Enchanted Redstone",
        "",
        hashMapOf(REDSTONE to 160),
        ItemRarity.UNCOMMON,
        getItem(MinecraftItem.redstone, true)
    ),
    ENCHANTED_REDSTONE_BLOCK(
        "Enchanted Redstone Block",
        "",
        hashMapOf(ENCHANTED_REDSTONE to 160),
        ItemRarity.RARE,
        getItem(Blocks.redstone_block, true)
    ),
    COBBLESTONE("Cobblestone", "", null, ItemRarity.COMMON, getItem(Blocks.cobblestone, false)),
    ENCHANTED_COBBLESTONE("Enchanted Cobblestone", "", null, ItemRarity.UNCOMMON, getItem(Blocks.cobblestone, true)),
    TREASURITE("Treasurite", "sre:forge/treasurite.png", null, ItemRarity.EPIC),
    STARFALL("Starfall", "", null, ItemRarity.RARE, getItem(MinecraftItem.nether_star, true)),

    GOBLIN_EGG("Goblin Egg", "", null, ItemRarity.RARE, getItem(MinecraftItem.egg, false)),
    GOLDEN_PLATE(
        "Golden Plate",
        "sre:forge/gplate.png",
        hashMapOf(ENCHANTED_GOLD_BLOCK to 2, GLACITE_JEWEL to 5, REFINED_DIAMOND to 1),
        ItemRarity.RARE
    ),
    FUEL_TANK("Fuel Tank", "sre:forge/ftank.png", hashMapOf(ENCHANTED_COAL_BLOCK to 2), ItemRarity.RARE),
    DRILL_ENGINE(
        "Drill Engine", "sre:forge/dengine.png", hashMapOf(
            ENCHANTED_IRON_BLOCK to 1,
            ENCHANTED_REDSTONE_BLOCK to 3, TREASURITE to 10, REFINED_DIAMOND to 1, GOLDEN_PLATE to 1
        ), ItemRarity.RARE
    ),
    MITHRIL_PLATE(
        "Mithril Plate",
        "sre:forge/mplate.png",
        hashMapOf(REFINED_MITHRIL to 5, ENCHANTED_IRON_BLOCK to 1, REFINED_TITANIUM to 1, GOLDEN_PLATE to 1),
        ItemRarity.RARE
    ),
    PLASMA("Plasma", "sre:forge/plasma.png", null, ItemRarity.RARE),
    BEJEWELED_HANDLE(
        "Bejeweled Handle",
        "",
        hashMapOf(GLACITE_JEWEL to 3),
        ItemRarity.RARE,
        getItem(MinecraftItem.stick, true)
    ),

    TITA_DRILL_1(
        "Titanium Drill DR-X355",
        "",
        hashMapOf(REFINED_MITHRIL to 10, REFINED_TITANIUM to 10, GOLDEN_PLATE to 6, FUEL_TANK to 1, DRILL_ENGINE to 1),
        ItemRarity.EPIC,
        CraftingHUDConfig.drillItem
    ),
    TITA_DRILL_2(
        "Titanium Drill DR-X455",
        "",
        hashMapOf(TITA_DRILL_1 to 1, REFINED_DIAMOND to 10, REFINED_TITANIUM to 16, MITHRIL_PLATE to 6),
        ItemRarity.EPIC,
        CraftingHUDConfig.drillItem
    ),
    TITA_DRILL_3(
        "Titanium Drill DR-X555",
        "",
        hashMapOf(
            TITA_DRILL_2 to 1,
            PLASMA to 20,
            MITHRIL_PLATE to 15,
            ENCHANTED_IRON_BLOCK to 2,
            REFINED_TITANIUM to 32,
            REFINED_DIAMOND to 20
        ),
        ItemRarity.EPIC,
        CraftingHUDConfig.drillItem
    ),

    MITHRIL_PICK_1(
        "Mithril Pickaxe",
        "",
        hashMapOf(ENCHANTED_MITHRIL to 30, BEJEWELED_HANDLE to 1, ENCHANTED_GOLD to 10),
        ItemRarity.UNCOMMON,
        getItem(MinecraftItem.iron_pickaxe, true)
    ),
    BEACON_II("Beacon II", "", hashMapOf(REFINED_MITHRIL to 5), ItemRarity.RARE, getItem(Blocks.beacon, false)),
    TITANIUM_TALISMAN(
        "Titanium Talisman",
        "sre:forge/titatali.png",
        hashMapOf(REFINED_TITANIUM to 2),
        ItemRarity.UNCOMMON
    ),
    DIAMONITE("Diamonite", "sre:forge/diamonite.png", hashMapOf(REFINED_DIAMOND to 3), ItemRarity.RARE),
    POWER_CRYSTAL(
        "Power Crystal",
        "",
        hashMapOf(STARFALL to 256),
        ItemRarity.EPIC,
        getItem(MinecraftItem.nether_star, true)
    ),
    MITHRIL_PICK_2(
        "Refined Mithril Pickaxe",
        "",
        hashMapOf(REFINED_MITHRIL to 3, BEJEWELED_HANDLE to 2, REFINED_DIAMOND to 1, ENCHANTED_GOLD to 30),
        ItemRarity.RARE,
        getItem(MinecraftItem.iron_pickaxe, true)
    ),
    MITHRIL_DRILL_1(
        "Mithril Drill SX-R226",
        "",
        hashMapOf(DRILL_ENGINE to 1, REFINED_MITHRIL to 3, FUEL_TANK to 1),
        ItemRarity.RARE,
        CraftingHUDConfig.drillItem
    ),
    MITHRIL_DRILL_2(
        "Mithril Drill SX-R326",
        "",
        hashMapOf(MITHRIL_DRILL_1 to 1, GOLDEN_PLATE to 10, MITHRIL_PLATE to 2),
        ItemRarity.EPIC,
        CraftingHUDConfig.drillItem
    ),
    MITHRIL_FUEL_TANK("Mithril-Infused Fuel Tank", "", hashMapOf(MITHRIL_PLATE to 3, FUEL_TANK to 5), ItemRarity.RARE),
    MITHRIL_DRILL_ENGINE(
        "Mithril-Plated Drill Engine",
        "",
        hashMapOf(DRILL_ENGINE to 2, MITHRIL_PLATE to 3),
        ItemRarity.RARE
    ),
    BEACON_III("Beacon III", "", hashMapOf(BEACON_II to 1, REFINED_MITHRIL to 10), ItemRarity.EPIC),
    TITANIUM_RING("Titanium Ring", "", hashMapOf(REFINED_TITANIUM to 6, TITANIUM_TALISMAN to 1), ItemRarity.RARE),
    PURE_MITHRIL("Pure Mithril", "", hashMapOf(REFINED_MITHRIL to 2), ItemRarity.RARE),
    ROCK_GEMSTONE("Rock Gemstone", "", hashMapOf(ENCHANTED_COBBLESTONE to 128, TREASURITE to 64), ItemRarity.RARE),
    PETRIFIED_STARFALL("Petrified Starfall", "", hashMapOf(STARFALL to 512), ItemRarity.EPIC),
    GOBLIN_OMELETTE("Goblin Omelette", "", hashMapOf(GOBLIN_EGG to 99), ItemRarity.RARE),
    BEACON_IV("Beacon IV", "", hashMapOf(BEACON_III to 1, REFINED_MITHRIL to 20, PLASMA to 1), ItemRarity.EPIC),
    TITANIUM_ARTIFACT("Titanium Artifact", "", hashMapOf(REFINED_TITANIUM to 12, TITANIUM_RING to 1), ItemRarity.EPIC),
    TITANIUM_INFUSED_FUEL_TANK(
        "Titanium-Infused Fuel Tank",
        "",
        hashMapOf(REFINED_TITANIUM to 10, REFINED_DIAMOND to 10, REFINED_MITHRIL to 10, FUEL_TANK to 10),
        ItemRarity.RARE
    ),
    BEACON_V("Beacon V", "", hashMapOf(BEACON_IV to 1, REFINED_MITHRIL to 40, PLASMA to 5), ItemRarity.EPIC),
    TITANIUM_RELIC(
        "Titanium Relic",
        "",
        hashMapOf(REFINED_TITANIUM to 20, TITANIUM_ARTIFACT to 1),
        ItemRarity.LEGENDARY
    ),

    // crystal hollows update

    // mats
    FINE_JADE_GEMSTONE("Fine Jade Gemstone", "", null, ItemRarity.UNCOMMON),
    FINE_AMBER_GEMSTONE("Fine Amber Gemstone", "", null, ItemRarity.UNCOMMON),
    FINE_AMETHYST_GEMSTONE("Fine Amethyst Gemstone", "", null, ItemRarity.UNCOMMON),
    FINE_SAPPHIRE_GEMSTONE("Fine Sapphire Gemstone", "", null, ItemRarity.UNCOMMON),
    FINE_RUBY_GEMSTONE("Fine Ruby Gemstone", "", null, ItemRarity.UNCOMMON),
    FINE_TOPAZ_GEMSTONE("Fine Topaz Gemstone", "", null, ItemRarity.UNCOMMON),
    FINE_JASPER_GEMSTONE("Fine Jasper Gemstone", "", null, ItemRarity.UNCOMMON),
    SLUDGE_JUICE("Slude Juice", "", null, ItemRarity.UNCOMMON),
    RED_GOBLIN_EGG("Red Goblin Egg", "", null, ItemRarity.UNCOMMON, getItem(MinecraftItem.egg, false)),
    HARD_STONE("Hard Stone", "", null, ItemRarity.COMMON),
    MAGMA_CORE("Magma Core", "", null, ItemRarity.RARE, getItem(MinecraftItem.magma_cream, true)),
    SUPERLITE_MOTOR("Superlite Motor", "", null, ItemRarity.RARE),
    CONTROL_SWITCH("Control Switch", "", null, ItemRarity.RARE),
    YELLOW_GOBLIN_EGG("Yellow Goblin Egg", "", null, ItemRarity.LEGENDARY, getItem(MinecraftItem.egg, false)),
    BLUE_GOBLIN_EGG("Blue Goblin Egg", "", null, ItemRarity.RARE),

    GEMSTONE_MIXTURE(
        "Gemstone Mixture", "", hashMapOf(
            FINE_JADE_GEMSTONE to 2, FINE_AMBER_GEMSTONE to 2, FINE_AMETHYST_GEMSTONE to 2, FINE_SAPPHIRE_GEMSTONE to 2,
            FINE_TOPAZ_GEMSTONE to 2, FINE_JASPER_GEMSTONE to 2, SLUDGE_JUICE to 500
        ), ItemRarity.RARE
    ),
    HOT_STUFF("Hot Stuff", "", hashMapOf(HARD_STONE to 128), ItemRarity.LEGENDARY),
    SPICY_GOBLIN_OMLETTE("Spicy Goblin Omelette", "", hashMapOf(RED_GOBLIN_EGG to 99), ItemRarity.RARE),
    SUNNY_SIDE_GOBLIN_OMLETTE(
        "Sunny Side Goblin Omelette",
        "",
        hashMapOf(YELLOW_GOBLIN_EGG to 99, FINE_TOPAZ_GEMSTONE to 1),
        ItemRarity.UNCOMMON
    ),
    RUBY_DRILL_1(
        "Ruby Drill Model TX-15",
        "",
        hashMapOf(DRILL_ENGINE to 1, FUEL_TANK to 1, FINE_RUBY_GEMSTONE to 6),
        ItemRarity.RARE,
        CraftingHUDConfig.drillItem
    ),
    RUBY_DRILL_2(
        "Topaz Drill KGR-12",
        "",
        hashMapOf(RUBY_DRILL_1 to 1, GEMSTONE_MIXTURE to 3, MAGMA_CORE to 5),
        ItemRarity.EPIC,
        CraftingHUDConfig.drillItem
    ),
    RUBY_DRILL_ENGINE(
        "Ruby-polished Drill Engine",
        "",
        hashMapOf(MITHRIL_DRILL_ENGINE to 1, SUPERLITE_MOTOR to 10, FINE_RUBY_GEMSTONE to 10),
        ItemRarity.RARE
    ),
    GEMSTONE_FUEL_TANK(
        "Gemstone Fuel Tank",
        "",
        hashMapOf(TITANIUM_INFUSED_FUEL_TANK to 1, CONTROL_SWITCH to 30, GEMSTONE_MIXTURE to 10),
        ItemRarity.RARE
    ),
    CORLEONITE("Corleonite", "", null, ItemRarity.EPIC),
    TITA_DRILL_4(
        "Titanium Drill DR-X655",
        "",
        hashMapOf(
            CORLEONITE to 30,
            TITA_DRILL_3 to 1,
            REFINED_DIAMOND to 5,
            GEMSTONE_MIXTURE to 16,
            REFINED_TITANIUM to 12,
            MITHRIL_PLATE to 5
        ),
        ItemRarity.LEGENDARY,
        CraftingHUDConfig.drillItem
    ),
}

class nonDropdownButton(
    val item: Item,
    x: Int,
    y: Int,
    var toggled: Boolean,
    val parent: Item?
) : GuiButton(
    69, x, y,
    ScreenRenderer.fontRenderer.getStringWidth(
        "${item.itemName} x${
            if (parent != null && parent.subcategory!!.containsKey(
                    item
                )
            ) parent.subcategory!![item] else 1
        } ⇦"
    ),
    ScreenRenderer.fontRenderer.FONT_HEIGHT,
    "${item.itemName} x${if (parent != null && parent.subcategory!!.containsKey(item)) parent.subcategory!!.get(item) else 1}"
) {
    override fun drawButton(mc: Minecraft?, mouseX: Int, mouseY: Int) {
        if (visible) {
            mc!!.textureManager.bindTexture(buttonTextures)
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)
            hovered =
                mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height
            ScreenRenderer.fontRenderer.drawString(
                displayString,
                xPosition.toFloat(),
                yPosition + (height - 8) / 2f,
                CustomColor.SetBase(item.rarity.color.rgb),
                SmartFontRenderer.TextAlignment.LEFT_RIGHT,
                SmartFontRenderer.TextShadow.NORMAL
            )
        }
        if (this.item.minecraftItem != null) {
            GlStateManager.pushMatrix()
            GlStateManager.translate(this.xPosition.toFloat() - 27, this.yPosition.toFloat() - 13, 0.0f)
            RenderHelper.enableGUIStandardItemLighting()
            RenderUtils.renderItem(this.item.minecraftItem!!, 8, 8)
            GlStateManager.disableLighting()
            GlStateManager.popMatrix()
        }
    }
}

class DropdownButton(
    val item: Item,
    x: Int,
    y: Int,
    var toggled: Boolean,
    val parent: Item?
) : GuiButton(
    69, x, y,
    ScreenRenderer.fontRenderer.getStringWidth(
        "${item.itemName} x${
            if (parent != null && parent.subcategory!!.containsKey(
                    item
                )
            ) parent.subcategory!![item] else 1
        } ⇨"
    ),
    ScreenRenderer.fontRenderer.FONT_HEIGHT,
    "${item.itemName} x${if (parent != null && parent.subcategory!!.containsKey(item)) parent.subcategory!!.get(item) else 1} ⇨"
) {
    override fun drawButton(mc: Minecraft?, mouseX: Int, mouseY: Int) {
        if (visible) {
            mc!!.textureManager.bindTexture(buttonTextures)
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)
            hovered =
                mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height
            val colour = if (hovered) CommonColors.RAINBOW else CustomColor.SetBase(item.rarity.color.rgb)
            val withControlCodes = displayString.toString()
            if (hovered) displayString = displayString.stripControlCodes()
            if (this.item.minecraftItem != null) {
                GlStateManager.pushMatrix()
                GlStateManager.translate(this.xPosition.toFloat() - 27, this.yPosition.toFloat() - 13, 0.0f)
                RenderHelper.enableGUIStandardItemLighting()
                RenderUtils.renderItem(this.item.minecraftItem!!, 8, 8)
                GlStateManager.disableLighting()
                GlStateManager.popMatrix()
            }
            ScreenRenderer.fontRenderer.drawString(
                displayString,
                xPosition.toFloat(),
                yPosition + (height - 8) / 2f,
                colour,
                SmartFontRenderer.TextAlignment.LEFT_RIGHT,
                SmartFontRenderer.TextShadow.NORMAL
            )
            displayString = withControlCodes
        }
    }
}

class SearchOptionButton(
    val item: Item, x: Int, y: Int
) : GuiButton(
    69, x, y,
    ScreenRenderer.fontRenderer.getStringWidth(item.itemName),
    ScreenRenderer.fontRenderer.FONT_HEIGHT,
    item.itemName
) {
    override fun drawButton(mc: Minecraft?, mouseX: Int, mouseY: Int) {
        if (visible) {
            mc!!.textureManager.bindTexture(buttonTextures)
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)
            hovered =
                mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height
            val colour = if (hovered && !enabled) CommonColors.RAINBOW else CustomColor.SetBase(item.rarity.color.rgb)
            ScreenRenderer.fontRenderer.drawString(
                displayString,
                xPosition.toFloat(),
                yPosition + (height - 8) / 2f,
                colour,
                SmartFontRenderer.TextAlignment.LEFT_RIGHT,
                SmartFontRenderer.TextShadow.NORMAL
            )
        }
    }
}