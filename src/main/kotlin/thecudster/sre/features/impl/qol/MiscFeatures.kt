package thecudster.sre.features.impl.qol

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import thecudster.sre.util.Utils.inLoc
import thecudster.sre.util.sbutil.ItemUtil.getItemLore
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.client.event.RenderItemInFrameEvent
import thecudster.sre.util.sbutil.CurrentLoc
import net.minecraftforge.client.event.RenderLivingEvent
import net.minecraftforge.client.event.ClientChatReceivedEvent
import thecudster.sre.core.gui.GuiManager
import thecudster.sre.features.impl.qol.MiscFeatures
import net.minecraft.entity.item.EntityArmorStand
import net.minecraft.item.ItemStack
import thecudster.sre.util.sbutil.ItemUtil
import net.minecraft.client.renderer.GlStateManager
import thecudster.sre.events.SecondPassedEvent
import java.time.LocalDateTime
import net.minecraftforge.client.event.GuiScreenEvent
import net.minecraft.client.gui.inventory.GuiChest
import net.minecraft.inventory.ContainerChest
import thecudster.sre.core.gui.SimpleButton
import net.minecraft.client.Minecraft
import net.minecraft.util.StringUtils
import net.minecraftforge.event.entity.player.ItemTooltipEvent
import org.lwjgl.input.Keyboard
import thecudster.sre.SkyblockReinvented
import thecudster.sre.events.GuiRenderItemEvent
import thecudster.sre.features.impl.qol.CoopMember
import thecudster.sre.util.Utils
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.lang.Exception
import java.util.ArrayList
import java.util.HashMap

class MiscFeatures {
    @SubscribeEvent
    fun onRender(event: RenderItemInFrameEvent) {
        if (!Utils.inSkyblock) {
            return
        }
        if (CurrentLoc.currentLoc != "Your Island") {
            return
        }
        if (!SkyblockReinvented.config.itemFrameNames) {
            return
        }
        event.entityItemFrame.alwaysRenderNameTag = false
        event.entityItemFrame.displayedItem.setStackDisplayName("")
    }

    @SubscribeEvent
    fun onRenderEntity(event: RenderLivingEvent.Pre<*>) {
        try {
            if (SkyblockReinvented.config.travelIsland && Utils.inSkyblock) {
                if (event.entity.customNameTag != null) {
                    if (inLoc(arrayOf("Village"))) {
                        if (event.entity.customNameTag.contains("Travel to:") || event.entity.customNameTag.contains("Your Island")) {
                            event.isCanceled = true
                            event.entity.alwaysRenderNameTag = false
                        }
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    @SubscribeEvent
    fun onChat(event: ClientChatReceivedEvent) {
        if (!Utils.inSkyblock) {
            return
        }
        if (!SkyblockReinvented.config.stash) {
            return
        }
        var unformatted = event.message.unformattedText
        unformatted = StringUtils.stripControlCodes(unformatted)
        if (unformatted.contains("You have ") && unformatted.contains("item stashed away!!")) {
            event.isCanceled = true
            GuiManager.createTitle(
                "Pick up your stash using " + Keyboard.getKeyName(
                    SkyblockReinvented.keyBindings[1]!!.keyCode
                ), 20
            )
            needsToPickup = true
        }
        if (unformatted.contains("You picked up all items from your item stash!")) {
            event.isCanceled = true
            needsToPickup = false
        } else if (unformatted.contains("You picked up") && unformatted.contains("items from your item stash")) {
            event.isCanceled = true
            needsToPickup = true
        }
        if (unformatted.contains("From stash: ")) {
            event.isCanceled = true
        }
        if (unformatted.contains("An item didn't fit in your inventory and was added to your item")) {
            event.isCanceled = true
        }
    }

    @SubscribeEvent
    fun onRender(event: RenderLivingEvent.Pre<*>) {
        if (!Utils.inSkyblock || CurrentLoc.currentLoc != "Your Island" || SkyblockReinvented.config.teleportPad == 0) {
            return
        }
        if (event.entity is EntityArmorStand) {
            if (event.entity.name != null) {
                if (event.entity.name.indexOf("Warp To") != -1) {
                    if (SkyblockReinvented.config.teleportPad == 1) {
                        event.entity.customNameTag = event.entity.name.substring(event.entity.name.indexOf("To") + 3)
                    } else {
                        event.entity.alwaysRenderNameTag = false
                    }
                }
            }
        }
    }

    /**
     * Modified from Skytils under GNU Affero General Public license.
     * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
     * @author My-Name-Is-Jeff
     * @author Sychic
     */
    @SubscribeEvent
    fun onRenderItemOverlayPos(event: GuiRenderItemEvent.RenderOverlayEvent.Post) {
        if (!Utils.inSkyblock) return
        val item = event.stack
        if (item == null || item.stackSize != 1 || !SkyblockReinvented.config.cakeStackSize) return
        var stackTip = ""
        if (item.hasDisplayName()) {
            if (item.displayName.contains("New Year Cake")) {
                val lore = getItemLore(item)
                for (loreval in lore) {
                    var s = loreval
                    if (s.contains("celebration for the ")) {
                        s = s.substring(22)
                        s = if (s.contains("th")) {
                            s.substring(0, s.indexOf("th"))
                        } else if (s.contains("st")) {
                            s.substring(0, s.indexOf("st"))
                        } else if (s.contains("rd")) {
                            s.substring(0, s.indexOf("rd"))
                        } else {
                            s.substring(0, s.indexOf("nd"))
                        }
                        stackTip = s
                    }
                }
            }
        }
        if (stackTip.length > 0) {
            GlStateManager.disableLighting()
            GlStateManager.disableDepth()
            GlStateManager.disableBlend()
            event.fr!!.drawStringWithShadow(
                stackTip,
                (event.x + 17 - event.fr!!.getStringWidth(stackTip)).toFloat(),
                (event.y + 9).toFloat(),
                16777215
            )
            GlStateManager.enableLighting()
            GlStateManager.enableDepth()
        }
    }

    @SubscribeEvent
    fun onSecondPassed(event: SecondPassedEvent?) {
        if (SkyblockReinvented.config.darkAuction) {
            if (LocalDateTime.now().minute == 53 && CurrentLoc.currentLoc != "Wilderness" && LocalDateTime.now().second == 0) {
                GuiManager.createTitle("Dark Auction", 20)
            }
        }
    }

    @SubscribeEvent
    fun onGuiScreen(event: GuiScreenEvent) {
        if (!Utils.inSkyblock) return
        if (event.gui is GuiChest) {
            val container = (event.gui as GuiChest).inventorySlots as ContainerChest
            if (container.lowerChestInventory.name.startsWith("Ultrasequencer") || container.lowerChestInventory.name.startsWith(
                    "Chronomatron"
                ) || container.lowerChestInventory.name.startsWith("Superpairs")
            ) {
                if (event is GuiScreenEvent.InitGuiEvent) {
                    event.buttonList.add(SimpleButton(0, 50, event.gui.height / 2 + 100, 80, 20, "Exit Mode"))
                } else if (event is GuiScreenEvent.ActionPerformedEvent.Post) {
                    if (event.button.id == 0) {
                        Minecraft.getMinecraft().thePlayer.closeScreen()
                    }
                }
            }
        }
    }

    var coopMembers: ArrayList<String>? = null
    @SubscribeEvent
    fun onTooltip(event: ItemTooltipEvent) {
        if (Minecraft.getMinecraft().currentScreen is GuiChest) {
            val chest = Minecraft.getMinecraft().currentScreen as GuiChest
            val inventory = chest.inventorySlots as ContainerChest
            val inventoryTitle = inventory.lowerChestInventory.displayName.unformattedText
            if (isCollectionMenu(inventoryTitle)) {
                event.toolTip.removeIf { i: String ->
                    for ((_, value) in coop) {
                        if (i.contains(value.memberName!!)) {
                            return@removeIf true
                        }
                    }
                    false
                }
            }
        }
    }

    fun isCollectionMenu(inventoryTitle: String): Boolean {
        return inventoryTitle == "Mining Collection" || inventoryTitle == "Farming Collection" || inventoryTitle == "Combat Collection" || inventoryTitle == "Foraging Collection" || inventoryTitle == "Fishing Collection"
    }

    private val gson = GsonBuilder().setPrettyPrinting().create()
    var coop = HashMap<String?, CoopMember>()
    fun readConfig() {
        val coopMembersJson = File(SkyblockReinvented.modDir, "coopMembers.json")
        if (!coopMembersJson.exists()) {
            try {
                coopMembersJson.createNewFile()
            } catch (ex: Exception) {
            }
        }
        var file: JsonObject
        try {
            FileReader(File(SkyblockReinvented.modDir, "coopMembers.json")).use { `in` ->
                file = gson.fromJson(`in`, JsonObject::class.java)
                for (i in file.entrySet().indices) {
                    if (file["coopMember$i"] != null) {
                        val member = CoopMember(
                            file["coopMember$i"].asJsonObject["name"].asString,
                            file["coopMember$i"].asJsonObject["disabled"].asBoolean
                        )
                        coop["customFilter$i"] = member
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun addDisabledMember(name: String?) {
        val size = coop.size
        coop["coopMember$size"] = CoopMember(name, true)
        try {
            FileWriter(File(SkyblockReinvented.modDir, "coopMembers.json")).use { writer -> gson.toJson(coop, writer) }
        } catch (ex: Exception) {
        }
    }

    fun addEnabledMember(name: String?) {
        val size = coop.size
        coop["coopMember$size"] = CoopMember(name, false)
        try {
            FileWriter(File(SkyblockReinvented.modDir, "coopMembers.json")).use { writer -> gson.toJson(coop, writer) }
        } catch (ex: Exception) {
        }
    }

    companion object {
        var needsToPickup = false
    }
}

class CoopMember(val memberName: String?, val disabled: Boolean)