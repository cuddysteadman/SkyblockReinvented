package thecudster.sre.features.impl.qol

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.inventory.GuiChest
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.entity.item.EntityArmorStand
import net.minecraft.inventory.ContainerChest
import net.minecraft.network.play.server.S05PacketSpawnPosition
import net.minecraft.network.play.server.S45PacketTitle
import net.minecraft.util.BlockPos
import net.minecraft.util.ChatComponentText
import net.minecraft.util.EnumChatFormatting
import net.minecraftforge.client.event.*
import net.minecraftforge.event.entity.player.ItemTooltipEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import org.lwjgl.input.Keyboard
import thecudster.sre.SkyblockReinvented
import thecudster.sre.core.gui.structure.GuiManager
import thecudster.sre.util.RenderUtils
import thecudster.sre.core.gui.structure.SimpleButton
import thecudster.sre.events.GuiRenderItemEvent
import thecudster.sre.events.PacketEvent
import thecudster.sre.events.SecondPassedEvent
import thecudster.sre.util.Utils
import thecudster.sre.util.Utils.inLoc
import thecudster.sre.util.api.APIUtil
import thecudster.sre.util.sbutil.CurrentLoc
import thecudster.sre.util.sbutil.ItemUtil
import thecudster.sre.util.sbutil.ItemUtil.getItemLore
import thecudster.sre.util.sbutil.stripControlCodes
import java.awt.Color
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.time.LocalDateTime

class MiscFeatures() {

    // hide item frame items on island
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
        event.entityItemFrame.displayedItem.setStackDisplayName("")
    }

    // hide travel to your island hider
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

    // pickup stash keybind + title
    @SubscribeEvent
    fun onChat(event: ClientChatReceivedEvent) {
        if (!Utils.inSkyblock) {
            return
        }
        var unformatted = event.message.unformattedText.stripControlCodes()
        if (SkyblockReinvented.config.stash) {
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
        // auto scam-checker
        /*
        * Modified from ScammerChecker ChatTriggers Module.
        * Unlicensed
        * https://www.chattriggers.com/modules/v/ScammerChecker
        * @author quartzexpress
         */
        if (SkyblockReinvented.config.autoScam) {
            if (unformatted.endsWith("has sent you a trade request. Click here to accept!")) {
                var name = unformatted.substring(0, unformatted.indexOf("has sent you a trade request.") - 1)
                if (checkForScammers(name, false)) {
                    event.isCanceled = true
                    var textMsg = ChatComponentText(EnumChatFormatting.RED.toString() + EnumChatFormatting.BOLD.toString() + name + EnumChatFormatting.RED.toString() + " is a known scammer! Click on this message to still trade with them.")
                    textMsg.chatStyle.chatClickEvent = event.message.chatStyle.chatClickEvent
                    Utils.sendMsg(textMsg)
                }
            } else if (unformatted.startsWith("You have sent a trade request to ")) {
                var name = unformatted.substring(33)
                name = name.substring(0, name.length - 1)
                if (checkForScammers(name, false)) {
                    Utils.sendMsg(EnumChatFormatting.RED.toString() + EnumChatFormatting.BOLD.toString() + name + EnumChatFormatting.RED.toString() + " is a known scammer! It is advisable to not trade with them. Your trade request is still outgoing.")
                    event.isCanceled = true
                }
            }
        }
    }

    // teleport pad parsing
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

    // new year cake names
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
        if (stackTip.isNotEmpty()) {
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

    // gift compass waypoints
    var pos: BlockPos? = null

    @SubscribeEvent
    fun onWorldRender(event: RenderWorldLastEvent) {
        if (!Utils.inSkyblock) return
        if (!(CurrentLoc.currentLoc == "Jerry's Workshop" || CurrentLoc.currentLoc == "Jerry Pond")) return
        if (Minecraft.getMinecraft().thePlayer.heldItem != null && ItemUtil.getSkyBlockItemID(Minecraft.getMinecraft().thePlayer.heldItem) != null) {
            if (ItemUtil.getSkyBlockItemID(Minecraft.getMinecraft().thePlayer.heldItem) == "GIFT_COMPASS") {
                if (pos != null) {
                    if (SkyblockReinvented.config.giftCompassWaypoints && !found) {
                        RenderUtils.drawWaypoint(event.partialTicks, pos as BlockPos, "Gift", Color(2, 250, 39), true, 1f, 2.0)
                    }
                }
            }
        }
    }

    @SubscribeEvent
    fun onReceivePacket(event: PacketEvent.ReceiveEvent) {
        if (!Utils.inSkyblock) return
        if (event.packet is S05PacketSpawnPosition) {
            val spawnPosition = event.packet as S05PacketSpawnPosition?
            pos = spawnPosition!!.spawnPos
        }
        if (SkyblockReinvented.config.removeRaffleTitles) {
            // remove raffle titles
            if (event.packet is S45PacketTitle) {
                val packet = event.packet as S45PacketTitle?
                if (packet!!.message != null) {
                    val unformatted = packet.message.unformattedText.stripControlCodes()
                    if (Utils.tabEntries.any { it.displayName != null && it.displayName.unformattedText != null && unformatted.contains(it.displayName.unformattedText) }) {
                        event.isCanceled = true
                    }
                    if (Utils.containsAnyOf(unformatted, arrayOf("WINNER #1 (for 3x rewards!)", "WINNER #2 (for 3x rewards!", "WINNER #3 (for 3x rewards"))
                            && Utils.containsAnyOf(unformatted, arrayOf("MVP", "VIP", "WINNER"))) {
                        event.isCanceled = true
                    }
                }
            }
        }
        // remove danger in the mist
        if (SkyblockReinvented.config.dangerGhosts) {
            if (event.packet is S45PacketTitle) {
                val packet = event.packet as S45PacketTitle?
                if (packet!!.message != null) {
                    val unformatted = packet.message.unformattedText.stripControlCodes()
                    if (Utils.containsAllOf(unformatted, arrayOf("DANGER", "Powerful creatures reside in the Mist"))) {
                        event.isCanceled = true
                    }
                }
            }
        }
    }

    // Dark Auction reminders
    @SubscribeEvent
    fun onSecondPassed(event: SecondPassedEvent?) {
        if (SkyblockReinvented.config.darkAuction) {
            if (LocalDateTime.now().minute == 53 && CurrentLoc.currentLoc != "Wilderness" && LocalDateTime.now().second == 0) {
                GuiManager.createTitle("Dark Auction", 20)
            }
        }
    }

    // exit mode experimentation
    @SubscribeEvent
    fun onGuiScreen(event: GuiScreenEvent) {
        if (!Utils.inSkyblock) return

        if (event.gui is GuiChest && SkyblockReinvented.config.exitMode) {
            val container = (event.gui as GuiChest).inventorySlots as ContainerChest
            if (container.lowerChestInventory.name.startsWith("Ultrasequencer (") || container.lowerChestInventory.name.startsWith("Chronomatron (") || container.lowerChestInventory.name.startsWith("Superpairs (")) {
                if (event is GuiScreenEvent.InitGuiEvent) {
                    event.buttonList.add(SimpleButton(0, 569, event.gui.height / 2 - 50, 80, 20, "Exit Mode"))
                } else if (event is GuiScreenEvent.ActionPerformedEvent.Post) {
                    if (event.button.id == 0) {
                        Minecraft.getMinecraft().thePlayer.closeScreen()
                    }
                }
            }
        }
    }

    // hide coop members
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

    // helper for hide coop members
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
        var found = false
        // scammer checker
        fun checkForScammers(name: String, isCommand: Boolean): Boolean {
            var isScammer = false
            Thread(Runnable {
                val uuid =
                    APIUtil.getJSONResponse("https://api.mojang.com/users/profiles/minecraft/$name").asJsonObject.get("id").asString
                val scammerDatabase =
                    APIUtil.getJSONResponse("https://raw.githubusercontent.com/skyblockz/pricecheckbot/master/scammer.json")
                if (scammerDatabase.has(uuid)) {
                    isScammer = true
                     if (isCommand) {
                         var reason = scammerDatabase.get(uuid).asJsonObject.get("reason").asString
                         Utils.sendMsg("\n§4§lWARNING!\n\n§r§6This user is a §l§4scammer!\n§r§4§lReason:§r§4 $reason")
                     }
                } else {
                    if (isCommand) {
                        Utils.sendMsg(EnumChatFormatting.GREEN.toString() + "This user is safe, according to the database. Please still be careful!")
                    }
                }
            }).start()
            return isScammer
        }
    }

    init {
        readConfig()
    }
}

class CoopMember(val memberName: String?, val disabled: Boolean)