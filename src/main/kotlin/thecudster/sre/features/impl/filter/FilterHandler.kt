/*
 * SkyblockReinvented - Hypixel Skyblock Improvement Modification for Minecraft
 *  Copyright (C) 2021 theCudster
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published
 *  by the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */
package thecudster.sre.features.impl.filter

import net.minecraftforge.fml.common.eventhandler.*
import net.minecraftforge.client.event.ClientChatReceivedEvent
import thecudster.sre.features.impl.qol.GhostLoot
import net.minecraft.util.EnumChatFormatting
import net.minecraft.util.ChatComponentText
import net.minecraft.event.ClickEvent
import net.minecraft.client.Minecraft
import net.minecraft.util.StringUtils
import net.minecraftforge.fml.common.gameevent.InputEvent
import thecudster.sre.SkyblockReinvented
import thecudster.sre.features.impl.qol.GiftCompassWaypoints
import thecudster.sre.core.gui.GuiManager
import thecudster.sre.events.SecondPassedEvent
import thecudster.sre.util.sbutil.ArrStorage
import thecudster.sre.util.Utils
import java.util.ArrayList

class FilterHandler {
    var filters = ArrayList<Filter>()
    val filterConcats = ArrayList<FilterConcat>()
    private var hasInit = false
    private var needsToClick = true
    @SubscribeEvent(receiveCanceled = true, priority = EventPriority.LOWEST)
    fun onChat(event: ClientChatReceivedEvent) {
        if (!hasInit) {
            init()
            hasInit = true
        }
        val unformatted = StringUtils.stripControlCodes(event.message.unformattedText)
        val message = event.message.unformattedText
        if (unformatted.contains("Welcome to Hypixel") && SkyblockReinvented.config.welcomeMsg) {
            event.isCanceled = true
            return
        }
        if (Utils.containsAnyOf(unformatted, ArrStorage.SpamArrays.profileMsg)) {
            event.isCanceled = true
            return
        }
        if (!Utils.inSkyblock) {
            return
        }
        if (SkyblockReinvented.config.ghostTracker) {
            if (message.contains("RARE DROP!")) {
                if (message.contains("Bag of Cash")) {
                    GhostLoot.bagCash++
                    return
                }
                if (message.contains("Sorrow")) {
                    GhostLoot.sorrow++
                    GhostLoot.ghostsSinceSorrow = 0
                    return
                }
                if (message.contains("Plasma")) {
                    GhostLoot.plasma++
                    return
                }
                if (message.contains("Volta")) {
                    GhostLoot.volta++
                    return
                }
                if (message.contains("Ghostly Boots")) {
                    GhostLoot.ghostlyBoots++
                    return
                }
            }
        }
        for (filter in filters) {
            if (filter.shouldCancel(event)) {
                event.isCanceled = true
                return
            }
        }
        for (filterConcat in filterConcats) {
            if (filterConcat.shouldCancel(event)) {
                event.isCanceled = true
                return
            }
        }
        if (SkyblockReinvented.config.maddoxMsg) {
            if (Utils.containsAnyOf(unformatted, ArrStorage.SpamArrays.maddoxFails)) {
                event.isCanceled = true
                Utils.sendMsg(EnumChatFormatting.RED.toString() + "Maddox batphone on cooldown!")
                return
            }
        }
        if (SkyblockReinvented.config.autoGarry) {
            if (unformatted.contains("Click here to teleport to Garry")) {
                needsToClick = true
                secondsPassed = 1
                val garryText =
                    ChatComponentText(EnumChatFormatting.GREEN.toString() + "Looks like an event is starting soon! SRE will automatically send you to garry in 15 seconds (assuming you click at least once). Click on this message to stop this automatic action from happening.")
                garryText.chatStyle =
                    garryText.chatStyle.setChatClickEvent(ClickEvent(ClickEvent.Action.RUN_COMMAND, "/garrytoggle"))
                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(garryText)
            }
        }
        if (SkyblockReinvented.config.reskinJacob) {
            if (message.contains("[NPC] Jacob: You earned a GOLD medal in the ")) {
                event.isCanceled = true
                Utils.sendMsg(EnumChatFormatting.GOLD.toString() + "You got gold, you actual sweat.")
                return
            }
            if (message.contains("[NPC] Jacob: You earned a SILVER medal in the ")) {
                event.isCanceled = true
                Utils.sendMsg(EnumChatFormatting.GRAY.toString() + "You got silver! Not bad.")
                return
            }
            if (message.contains("[NPC] Jacob: You earned a BRONZE medal in the ")) {
                event.isCanceled = true
                Utils.sendMsg(EnumChatFormatting.YELLOW.toString() + "You got bronze! What are you doing m8.")
                return
            }
            if (message.contains("[NPC] Jacob: You didn't earn a medal, but you may claim a participation reward in the ")) {
                event.isCanceled = true
                Utils.sendMsg(EnumChatFormatting.RED.toString() + "f in the chat.")
                return
            }
        }
        if (SkyblockReinvented.config.giftCompassWaypoints) {
            if (unformatted.contains("[NPC] St. Jerry: You found all of the Gifts!")) {
                GiftCompassWaypoints.found = true
                return
            }
        }
        if (SkyblockReinvented.config.watcherTitle) {
            if (unformatted.contains("The Watcher: That will be enough for now.")) {
                GuiManager.createTitle("Watcher Ready!", 20)
                return
            }
        }
        if (SkyblockReinvented.config.readyAlert && Utils.inLoc(ArrStorage.dwarvenLocs)) {
            if (unformatted == "Mining Speed Boost is now available!") {
                GuiManager.createTitle(EnumChatFormatting.GREEN.toString() +"Speed Boost Ready!", 20)
            }
        }
        if (SkyblockReinvented.config.expiredAlert && Utils.inLoc(ArrStorage.dwarvenLocs)) {
            if (unformatted == "Your Mining Speed Boost has expired!") {
                GuiManager.createTitle("Speed Boost Expired!", 20)
            }
        }
        if (SkyblockReinvented.config.removeExpired) {
            if (unformatted == "Your Mining Speed Boost has expired!") {
                event.isCanceled = true
                return
            }
        }
        if (SkyblockReinvented.config.removeReady) {
            if (unformatted == "Mining Speed Boost is now available!") {
                event.isCanceled = true
                return
            }
        }
        if (SkyblockReinvented.config.removeUsed) {
            if (unformatted == "You used your Mining Speed Boost Pickaxe Ability!") {
                event.isCanceled = true
                return
            }
        }
        /*
		  Taken from Skytils under GNU Affero General Public license.
		  https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
		  @author My-Name-Is-Jeff
		 * @author Sychic
		 */if (message.startsWith("Your new API key is ")) {
            val apiKey = event.message.siblings[0].chatStyle.chatClickEvent.value
            SkyblockReinvented.config.apiKey = apiKey
            SkyblockReinvented.config.markDirty()
            SkyblockReinvented.config.writeData()
            Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText(EnumChatFormatting.GREEN.toString() + "SkyblockReinvented updated your set Hypixel API key to " + EnumChatFormatting.DARK_GREEN + apiKey))
        }
        if (message.contains("A Golden Goblin has spawned from the earth!")) {
            if (SkyblockReinvented.config.goblinAlert && SkyblockReinvented.config.goldenGoblin) {
                event.isCanceled = true
                GuiManager.createTitle("Golden Goblin!", 20)
                return
            } else if (SkyblockReinvented.config.goldenGoblin) {
                event.isCanceled = true
                return
            } else if (SkyblockReinvented.config.goblinAlert) {
                GuiManager.createTitle("Golden Goblin!", 20)
            }
        }
        if (SkyblockReinvented.config.goldenGoblin) {
            if (Utils.containsAnyOf(
                    unformatted,
                    arrayOf("You received 400", "You received 200")
                ) && Utils.containsAllOf(unformatted, arrayOf("from killing a ", "Golden Goblin"))
            ) {
                event.isCanceled = true
                return
            }
        }
        if (SkyblockReinvented.config.hideIronman) {
            if (Utils.checkIronman()) {
                if (unformatted.contains(":")) {
                    var playerName = unformatted.substring(0, unformatted.indexOf(":"))
                    if (playerName.contains("[MVP+]") || playerName.contains("[VIP+]")) {
                        playerName = playerName.substring(6)
                    } else if (playerName.contains("[VIP]") || playerName.contains("[MVP]")) {
                        playerName = playerName.substring(5)
                    }
                    if (Utils.isInTablist(playerName)) {
                        event.isCanceled = true
                        return
                    }
                }
            }
        }
        if (SkyblockReinvented.config.removeCreeperVeil) {
            if (message.contains("Creeper Veil De-activated")) {
                event.isCanceled = true
                witherCloak = false
                return
            }
            if (message.contains("Creeper Veil Activated")) {
                event.isCanceled = true
                witherCloak = true
                return
            }
        }
        if (SkyblockReinvented.config.bongoAlert) {
            if (Utils.containsAllOf(unformatted, arrayOf("Your ", "Bonzo's Mask", "saved your life!"))) {
                event.isCanceled = true
                GuiManager.createTitle("Bonzo Mask Triggered!", 20)
                return
            }
        }
        if (SkyblockReinvented.config.sellable) {
            if (Utils.containsAllOf(unformatted, arrayOf("for ", "You sold ", "Coins!"))) {
                if (Utils.containsAnyOf(unformatted, ArrStorage.SpamArrays.blockedNames)) {
                    event.isCanceled = true
                    return
                }
            }
        }
        if (SkyblockReinvented.config.damageMilestones && Utils.inDungeons && unformatted.contains(" Milestone")) {
            if (Utils.containsAnyOf(
                    unformatted,
                    arrayOf("Mage", "Berserk", "Healer", "Tank", "Archer")
                )
            ) { // TODO check if this works
                event.isCanceled = true
                return
            }
        }
        if (SkyblockReinvented.config.headlessHorseman) {
            if (Utils.inLoc(ArrStorage.hubLocs)) {
                if (Utils.containsAnyOf(
                        unformatted, arrayOf(
                            "HORSEMAN HORSE DOWN!",
                            "dealt the final blow.",
                            "1st Damager - ",
                            "2nd Damager - ",
                            "3rd Damager - ",
                            "-------------------------"
                        )
                    )
                ) {
                    event.isCanceled = true
                    return
                }
                if (Utils.containsAllOf(unformatted, arrayOf("Your Damage: ", "(Position #"))) {
                    event.isCanceled = true
                    return
                }
                if (Utils.containsAllOf(unformatted, arrayOf("Runecrafting: +", " XP"))) {
                    event.isCanceled = true
                    return
                }
            }
        }
    }

    @SubscribeEvent
    fun onSecond(event: SecondPassedEvent?) {
        if (secondsPassed > 0) {
            secondsPassed++
            if (secondsPassed > 16 && !needsToClick) {
                Utils.sendCommand("garry")
                secondsPassed = 0
            }
        }
    }

    @SubscribeEvent
    fun onInput(event: InputEvent.MouseInputEvent?) {
        if (needsToClick) {
            needsToClick = false
        }
    }

    @JvmOverloads
    fun add(concat: Boolean, messages: Array<String>, shouldCancel: Boolean, dungeons: Boolean = false) {
        if (!concat) {
            filters.add(Filter(messages!! as Array<String?>, shouldCancel, dungeons))
            return
        }
        filterConcats.add(FilterConcat(messages!! as Array<String?>, shouldCancel, dungeons))
    }

    fun init() {
        filters.clear()
        add(false, ArrStorage.SpamArrays.watcherQuotes, SkyblockReinvented.config.removeWatcher, true)
        add(false, arrayOf("You have found page "), SkyblockReinvented.config.journalMsg, true)
        add(
            false,
            arrayOf("You need to be out of combat for 5 seconds before opening the SkyBlock Menu!"),
            SkyblockReinvented.config.warpCombat
        )
        add(false, arrayOf("No tickets to put in the box..."), SkyblockReinvented.config.ticketMsgs)
        add(false, arrayOf("Withdrawing coins...", "Depositing coins..."), SkyblockReinvented.config.bankMsgs)
        add(false, arrayOf("Warping you to", "Sending to server"), SkyblockReinvented.config.warps)
        add(
            false,
            arrayOf(
                "Your active Potion Effects have been paused and stored. They will be restored when you leave Dungeons! You are not allowed to use existing Potion Effects while in Dungeons.",
                "You can no longer consume or splash any potions during the remainder of this Dungeon run!"
            ),
            SkyblockReinvented.config.dungeonPotionMsg
        )
        add(false, ArrStorage.SpamArrays.warningServers, SkyblockReinvented.config.serverFull)
        add(false, ArrStorage.SpamArrays.cleanSlayer, SkyblockReinvented.config.maddoxMsg)
        add(
            false,
            arrayOf("worth of Zombies", "worth of Spiders", "worth of Wolves"),
            SkyblockReinvented.config.maddoxMsg
        )
        add(false, ArrStorage.SpamArrays.cleanJacob, SkyblockReinvented.config.uselessJacob)
        add(
            false,
            arrayOf("A Crypt Wither Skull exploded, hitting you for "),
            SkyblockReinvented.config.cryptWitherSkull,
            true
        )
        add(false, ArrStorage.SpamArrays.cleanJerry, SkyblockReinvented.config.cleanJerry)
        add(
            false,
            arrayOf("Your bone plating reduced the damage you took by"),
            SkyblockReinvented.config.bonePlating,
            true
        )
        add(false, ArrStorage.SpamArrays.cleanEndDungeon, SkyblockReinvented.config.cleanEnd, true)
        add(false, ArrStorage.SpamArrays.hubWarnings, SkyblockReinvented.config.hubWarnings)
        add(false, ArrStorage.SpamArrays.dumbSlayerDrops, SkyblockReinvented.config.dumbSlayerDrops)
        add(
            false,
            arrayOf("Blacklisted modifications are a bannable offense!"),
            SkyblockReinvented.config.watchdogAnnouncement
        )
        add(false, arrayOf("WATCHDOG ANNOUNCEMENT"), SkyblockReinvented.config.watchdogAnnouncement)
        add(
            false,
            ArrStorage.SpamArrays.threeWeirdosIncorrect,
            SkyblockReinvented.config.threeWeirdosIncorrect == 1 || SkyblockReinvented.config.threeWeirdosIncorrect == 2,
            true
        )
        add(false, ArrStorage.SpamArrays.threeWeirdosSolutions, SkyblockReinvented.config.threeWeirdosIncorrect == 2, true)
        add(false, arrayOf("You hear the sound of something opening"), SkyblockReinvented.config.openMsg, true)
        add(
            false,
            arrayOf("BUFF! You have gained", "Press TAB to view your active effects!"),
            SkyblockReinvented.config.potionMsg
        )
        add(
            false,
            arrayOf("Your bone plating reduced the damage you took by"),
            SkyblockReinvented.config.bonePlating,
            true
        )
        add(false, arrayOf("[NPC] Puzzler:"), SkyblockReinvented.config.removePuzzler)
        add(false, ArrStorage.SpamArrays.witherSkullList, SkyblockReinvented.config.witherSkulls, true)
        add(false, arrayOf("Your inventory is full!"), SkyblockReinvented.config.inventoryFull)
        add(false, ArrStorage.SpamArrays.dungeonFinder, SkyblockReinvented.config.dungeonFinder)
        add(false, ArrStorage.SpamArrays.healerMsg, SkyblockReinvented.config.healerMsg)
        add(false, arrayOf("extra Jerry packed his stuff and left!"), SkyblockReinvented.config.jerryMsg)
        add(
            false,
            arrayOf("inventory does not have enough free space to add all items"),
            SkyblockReinvented.config.removeMinionWarnings
        )
        add(false, arrayOf("Dicer dropped "), SkyblockReinvented.config.removeRNGChat)
        add(false, ArrStorage.SpamArrays.tooFast, SkyblockReinvented.config.removeTooFast)
        add(false, ArrStorage.SpamArrays.experimentationTable, SkyblockReinvented.config.experimentationTable)
        add(false, arrayOf("Wait a moment before reforging again!"), SkyblockReinvented.config.reforge)
        add(
            false,
            arrayOf("Your Guardian Chestplate nullified the damage you just took!"),
            SkyblockReinvented.config.guardianChestplate
        )
        add(false, ArrStorage.SpamArrays.slowDown, SkyblockReinvented.config.slowDown)
        add(false, ArrStorage.SpamArrays.boneShieldList, SkyblockReinvented.config.skeletonHat)
        add(false, ArrStorage.SpamArrays.doubleMsg, SkyblockReinvented.config.doubleMsg)
        add(false, arrayOf("opened a WITHER door!"), SkyblockReinvented.config.openWither, true)
        add(false, arrayOf("You do not have the key for this door!"), SkyblockReinvented.config.cantOpenWither, true)
        add(false, arrayOf("Lost Adventurer used Dragon's Breath on you!"), SkyblockReinvented.config.dragonsBreath)
        add(
            false,
            arrayOf("Putting coins in escrow...", "Executing instant buy...", "Executing instant sell..."),
            SkyblockReinvented.config.bazaarMsg
        )
        add(
            false,
            arrayOf(
                "The sound of pickaxes clashing against the rock has attracted the attention of the POWDER GHAST!",
                "Find the Powder Ghast near the "
            ),
            SkyblockReinvented.config.powderGhast
        )
        add(
            false,
            arrayOf("found a Wither Essence! Everyone gains an extra essence!"),
            SkyblockReinvented.config.essenceMessages
        )
        add(true, arrayOf("Bazaar! Sold", "x ", " for ", "coins!"), SkyblockReinvented.config.bazaarMsg)
        add(true, arrayOf("You salvaged a ", "for +", "Undead Essence!"), SkyblockReinvented.config.salvage)
        add(
            true,
            arrayOf("You've earned ", "from mining your first Mithril Ore of the day!"),
            SkyblockReinvented.config.firstMithrilPowder
        )
        add(true, arrayOf("Added", "to your dungeon journal collection!"), SkyblockReinvented.config.journalMsg, true)
        add(true, arrayOf("You registered", "in the raffle event!"), SkyblockReinvented.config.ticketMsgs)
        add(true, arrayOf("Withdrew", "There's now", "coins left in the account!"), SkyblockReinvented.config.bankMsgs)
        add(true, arrayOf("Withdrew", "There's now ", "coins in the account!"), SkyblockReinvented.config.bankMsgs)
        add(true, arrayOf("+", "Bits"), SkyblockReinvented.config.cleanEnd, true)
        add(true, arrayOf("You received", "from minions"), SkyblockReinvented.config.minionXP)
        add(true, arrayOf("You gained", "experience from minions!"), SkyblockReinvented.config.minionXP)
        add(
            true,
            arrayOf("I'm currently taking care of your ", "You can pick it up in"),
            SkyblockReinvented.config.katMsg
        )
        add(
            true,
            arrayOf("Staff have banned an additional ", "in the last 7 days."),
            SkyblockReinvented.config.watchdogAnnouncement
        )
        add(
            true,
            arrayOf("Watchdog has banned ", "players in the last 7 days."),
            SkyblockReinvented.config.watchdogAnnouncement
        )
        add(true, arrayOf("struck you for ", "damage!"), SkyblockReinvented.config.struckYou, true)
        add(
            true,
            arrayOf("You need to have a class at level", "or higher to join this group!"),
            SkyblockReinvented.config.dungeonFinder
        )
        add(true, arrayOf("Lost Adventurer", "struck you for ", "damage!"), SkyblockReinvented.config.dragonsBreath)
        add(true, arrayOf("picked up your ", "Orb!"), SkyblockReinvented.config.damageOrbs)
        add(true, arrayOf("You picked up a", " from ", " healing you for "), SkyblockReinvented.config.damageOrbs)
        add(
            true,
            arrayOf("You applied the", "reforge to ", "accessories in your Accessory Bag!"),
            SkyblockReinvented.config.reforge
        )
        add(true, arrayOf("You reforged", "into"), SkyblockReinvented.config.reforge)
        add(true, arrayOf("You earned", "from playing SkyBlock!"), SkyblockReinvented.config.gexpMsg)
        add(true, arrayOf("Your tether with ", "healed you for"), SkyblockReinvented.config.healerMsg)
    }

    companion object {
        @JvmField
		var witherCloak = false
        @JvmField
		var secondsPassed = 0
    }
}