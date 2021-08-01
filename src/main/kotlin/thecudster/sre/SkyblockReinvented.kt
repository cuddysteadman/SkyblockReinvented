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
package thecudster.sre

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import gg.essential.vigilance.VigilanceConfig
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.resources.IReloadableResourceManager
import net.minecraft.client.settings.KeyBinding
import net.minecraft.command.ICommandSender
import net.minecraft.event.ClickEvent
import net.minecraft.scoreboard.ScorePlayerTeam
import net.minecraft.util.ChatComponentText
import net.minecraft.util.EnumChatFormatting
import net.minecraftforge.client.ClientCommandHandler
import net.minecraftforge.client.event.MouseEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.common.Loader
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import org.json.JSONException
import org.lwjgl.input.Keyboard
import thecudster.sre.core.Config
import thecudster.sre.core.Keybindings
import thecudster.sre.core.gui.screens.*
import thecudster.sre.core.gui.structure.GuiManager
import thecudster.sre.core.gui.structure.ScreenRenderer
import thecudster.sre.events.JoinSkyblockEvent
import thecudster.sre.events.LeaveSkyblockEvent
import thecudster.sre.events.SecondPassedEvent
import thecudster.sre.events.WorldChangeEvent
import thecudster.sre.features.impl.dungeons.*
import thecudster.sre.features.impl.filter.FilterHandler
import thecudster.sre.features.impl.qol.*
import thecudster.sre.features.impl.qol.dragons.DragTracker
import thecudster.sre.features.impl.qol.dragons.DragonArrowHitbox
import thecudster.sre.features.impl.rendering.HyperionOverlay
import thecudster.sre.features.impl.rendering.PlayerHider
import thecudster.sre.features.impl.rendering.RemoveVillagers
import thecudster.sre.features.impl.skills.SkillXPTracker
import thecudster.sre.features.impl.skills.bestiary.BestiaryGUI
import thecudster.sre.features.impl.skills.bestiary.BestiaryProgress
import thecudster.sre.features.impl.skills.mining.GhostLoot
import thecudster.sre.features.impl.skills.slayer.SlayerFeatures
import thecudster.sre.features.impl.skills.slayer.SlayerReminder
import thecudster.sre.features.impl.skills.slayer.SlayerTracker
import thecudster.sre.features.impl.sounds.MiscSoundBlocks
import thecudster.sre.util.Utils
import thecudster.sre.util.sbutil.CurrentLoc
import thecudster.sre.util.sbutil.SimpleCommand
import thecudster.sre.util.sbutil.SimpleCommand.ProcessCommandRunnable
import thecudster.sre.util.sbutil.stripControlCodes
import java.awt.Desktop
import java.io.File
import java.io.IOException
import java.net.MalformedURLException
import java.net.URI
import java.net.URISyntaxException
import java.util.*
import java.util.function.Consumer

@Mod(
    modid = SkyblockReinvented.MODID,
    name = SkyblockReinvented.MOD_NAME,
    version = SkyblockReinvented.VERSION,
    acceptedMinecraftVersions = "[1.8.9]",
    clientSideOnly = true
)
class SkyblockReinvented {
    var notUsingDSM = false

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent?) {
        if (!modDir.exists()) modDir.mkdirs()
    }
    @Mod.EventHandler
    fun init(event: FMLInitializationEvent?) {
        config = Config()
        config.preload()

        GUIMANAGER = GuiManager()
        discordRPC = DiscordRPC()
        filter = FilterHandler()
        customFilters = FilterGUI()
        coopHelper = MiscFeatures()
        val renderPlayersGUI = RenderPlayersGUI()

        ClientCommandHandler.instance.registerCommand(SRECommand)
        ClientCommandHandler.instance.registerCommand(RenderingCommand)
        ClientCommandHandler.instance.registerCommand(DiscordCommand)
        ClientCommandHandler.instance.registerCommand(FragRunCommand)
        ClientCommandHandler.instance.registerCommand(DragCommand)
        ClientCommandHandler.instance.registerCommand(PingCommand)
        ClientCommandHandler.instance.registerCommand(GarryCommand)
        ClientCommandHandler.instance.registerCommand(SwaphubCommand)
        ClientCommandHandler.instance.registerCommand(CoopCommand)
        ClientCommandHandler.instance.registerCommand(ScammerCommand)

        MinecraftForge.EVENT_BUS.register(this)
        MinecraftForge.EVENT_BUS.register(SkillXPTracker())
        // MinecraftForge.EVENT_BUS.register(new CommissionWaypoints()); <- I worked on this for an afternoon will I work on it again the world may never know
        MinecraftForge.EVENT_BUS.register(SlayerReminder())
        MinecraftForge.EVENT_BUS.register(PlayerHider())
        MinecraftForge.EVENT_BUS.register(PowerOrbFeatures())
        MinecraftForge.EVENT_BUS.register(WitherCloakHider())
        MinecraftForge.EVENT_BUS.register(RemoveVillagers())
        MinecraftForge.EVENT_BUS.register(HyperionOverlay())
        MinecraftForge.EVENT_BUS.register(MiscSoundBlocks())
        MinecraftForge.EVENT_BUS.register(Keybindings())
        MinecraftForge.EVENT_BUS.register(MiscClickBlocks())
        MinecraftForge.EVENT_BUS.register(MiscGUIs())
        MinecraftForge.EVENT_BUS.register(GhostLoot())
        MinecraftForge.EVENT_BUS.register(coopHelper)
        MinecraftForge.EVENT_BUS.register(GUIMANAGER)
        MinecraftForge.EVENT_BUS.register(DragonArrowHitbox())
        MinecraftForge.EVENT_BUS.register(SlayerTracker())
        MinecraftForge.EVENT_BUS.register(BestiaryProgress())
        MinecraftForge.EVENT_BUS.register(BestiaryGUI())
        MinecraftForge.EVENT_BUS.register(DragTracker())
        MinecraftForge.EVENT_BUS.register(CreeperSolver())
        MinecraftForge.EVENT_BUS.register(WorldChangeEvent())
        MinecraftForge.EVENT_BUS.register(TreasureLocs())
        MinecraftForge.EVENT_BUS.register(DungeonFeatures())
        MinecraftForge.EVENT_BUS.register(SlayerFeatures())
        MinecraftForge.EVENT_BUS.register(FetchurSolver())
        MinecraftForge.EVENT_BUS.register(filter)

        if (Minecraft.getMinecraft().gameSettings.language != null) {
            ScreenRenderer.fontRenderer.unicodeFlag = Minecraft.getMinecraft().isUnicode
            ScreenRenderer.fontRenderer.bidiFlag =
                Minecraft.getMinecraft().languageManager.isCurrentLanguageBidirectional
        }
        val mgr = Minecraft.getMinecraft().resourceManager as IReloadableResourceManager
        mgr.registerReloadListener(ScreenRenderer.fontRenderer)
        keyBindings[0] = KeyBinding("Refresh Location", Keyboard.KEY_H, "SkyblockReinvented")
        keyBindings[1] = KeyBinding("Pickup Stash", Keyboard.KEY_G, "SkyblockReinvented")
        for (keyBinding in keyBindings) {
            ClientRegistry.registerKeyBinding(keyBinding)
        }
    }

    @Mod.EventHandler
    fun postInit(event: FMLPostInitializationEvent?) {
        usingLabymod = Loader.isModLoaded("labymod")
        notUsingDSM = !Loader.isModLoaded("Danker's Skyblock Mod")
        config.addDSM()
    }

    @SubscribeEvent
    fun onLogout(event: PlayerLoggedOutEvent?) {
        MinecraftForge.EVENT_BUS.post(LeaveSkyblockEvent())
    }

    @SubscribeEvent
    fun onTick(event: TickEvent.ClientTickEvent) {
        if (event.phase != TickEvent.Phase.START) return

        ScreenRenderer.refresh()

        if (currentGui != null) {
            mc.displayGuiScreen(currentGui)
            currentGui = null
        }
        if (ticks % 20 == 0) {
            if (mc.thePlayer != null) {
                Utils.checkForHypixel()
                Utils.checkForSkyblock()
                Utils.checkForDungeons()
                Utils.checkIronman()

                CurrentLoc.checkLoc()
                DragTracker.updateGui()
                MinecraftForge.EVENT_BUS.post(SecondPassedEvent())

                if (secondsPassed == -1) {
                    secondsPassed += 2
                } else if (secondsPassed < 3) {
                    secondsPassed++
                }
                if (needsToClick == 0 && secondsPassed == 3) {
                    Utils.sendCommand("warp hub")
                    needsToClick = 2
                    secondsPassed = 4
                }
                try {
                    if (mc.thePlayer == null) {
                        return
                    }
                    if (mc.theWorld == null) {
                        return
                    }
                    if (mc.theWorld.scoreboard == null) {
                        return
                    }
                    val scoreboard = mc.theWorld.scoreboard
                    for (score in scoreboard.getSortedScores(scoreboard.getObjectiveInDisplaySlot(1))) {
                        val name = ScorePlayerTeam.formatPlayerName(scoreboard.getPlayersTeam(score.playerName), score.playerName).stripControlCodes()
                        if (name.contains("Ironman") && config.removeIronmanScoreboard) {
                            scoreboard.removeTeam(scoreboard.getPlayersTeam(score.playerName))
                            Utils.ironmanProfile = true
                        } else if (name.contains("Mithril") && config.hideMithrilPowder) {
                            scoreboard.removeTeam(scoreboard.getPlayersTeam(score.playerName))
                        }
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
            ticks = 0
        }
        ticks++
    }

    @SubscribeEvent
    fun onLeave(event: LeaveSkyblockEvent?) {
        if (discordRPC!!.isActive) {
            discordRPC!!.stop()
        }
        if (!Minecraft.getMinecraft().gameSettings.viewBobbing && config.smartViewBobbing) {
            Minecraft.getMinecraft().gameSettings.viewBobbing = true
        }
    }

    @SubscribeEvent
    fun onJoin(event: JoinSkyblockEvent?) {
        /*
		if (config.myVersion == null || this.VERSION != config.myVersion) {
			config.myVersion = this.VERSION;
			config.markDirty();
			config.writeData();
			Changelog.printChangelog(this.VERSION);
		}
		 */
        if (config.discordRP) {
            discordRPC!!.start()
        } else if (discordRPC!!.isActive) {
            discordRPC!!.stop()
        }
        if (Minecraft.getMinecraft().gameSettings.viewBobbing && config.smartViewBobbing) {
            Minecraft.getMinecraft().gameSettings.viewBobbing = false
        }
    }

    // begin commands
    var SRECommand = SimpleCommand(
        "sre",
        Arrays.asList("SRE", "SkyblockReinvented", "skyblockreinvented", "sbr", "SBR"),
        object : ProcessCommandRunnable() {
            override fun processCommand(sender: ICommandSender?, args: Array<String>) {
                if (args.size > 0) {
                    when (args[0]) {
                        "gui", "editlocations" -> currentGui = LocationEditGUI()
                        "help" -> Utils.sendHelp()
                        "config" -> currentGui = config.gui()
                        "vigilance" -> currentGui = VigilanceConfig.gui()
                        "github" -> try {
                            Desktop.getDesktop().browse(URI("https://github.com/theCudster/SkyblockReinvented"))
                        } catch (ex: IOException) {
                            ex.printStackTrace()
                        } catch (ex: URISyntaxException) {
                            ex.printStackTrace()
                        }
                        "discord" -> try {
                            Desktop.getDesktop().browse(URI("https://discord.com/invite/xkeYgZrRbN"))
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        "main" -> {
                            currentGui = MainGUI()
                            return
                        }
                        "filter" -> {
                            currentGui = FilterGUI()
                            return
                        }
                        "playerfilter" -> {
                            currentGui = RenderPlayersGUI()
                            return
                        }
                        "crafting" -> {
                            currentGui = CraftingHUDConfig()
                            return
                        }
                        else -> Utils.sendMsg(EnumChatFormatting.RED.toString() + "Unknown argument! Acceptable parameters are config, help, main, filter, playerfilter, editlocations, vigilance, github, discord, and gui.")
                    }
                } else {
                    if (config.openConfig == 1) {
                        currentGui = config.gui()
                    } else {
                        currentGui = MainGUI()
                    }
                }
            }
        })
    var ScammerCommand = SimpleCommand("isscammer", listOf("issc", "sc", "isuserscammer"), object : ProcessCommandRunnable() {
        override fun processCommand(sender: ICommandSender?, args: Array<String>) {
            MiscFeatures.checkForScammers(args[0], true)
        }
    })
    var CoopCommand =
        SimpleCommand("addcoopmember", Arrays.asList("cadd", "addcmem"), object : ProcessCommandRunnable() {
            override fun processCommand(sender: ICommandSender?, args: Array<String>) {
                if (args.size == 0) {
                    Utils.sendMsg(EnumChatFormatting.RED.toString() + "Invalid usage! Proper usage is /addcoopmember {name} [disabled/enabled]")
                } else if (args.size == 1) {
                    coopHelper!!.addDisabledMember(args[0])
                    coopHelper!!.addDisabledMember(args[0])
                } else if (args.size >= 2) {
                    if (args[1].lowercase() == "true" || args[1].lowercase() == "enabled") {
                        coopHelper!!.addEnabledMember(args[0])
                    } else {
                        coopHelper!!.addDisabledMember(args[0])
                    }
                }
                coopHelper!!.readConfig()
            }
        })
    var DiscordCommand = SimpleCommand(
        "dset",
        Arrays.asList("da", "disc", "dset", "discset", "rp", "rpset"),
        object : ProcessCommandRunnable() {
            override fun processCommand(sender: ICommandSender?, args: Array<String>) {
                if (!Utils.inSkyblock) {
                    Utils.sendMsg(EnumChatFormatting.RED.toString() + "Use this command in Skyblock!")
                    return
                }
                if (!config.discordRP) {
                    Utils.sendMsg(EnumChatFormatting.RED.toString() + "You do not have discord rich presence on, so this command will do nothing.")
                    return
                }
                if (config.discordMode != 4) {
                    Utils.sendMsg(EnumChatFormatting.RED.toString() + "You do not have the custom discord rich presence setting on, so this command will do nothing.")
                    return
                }
                if (args.isNotEmpty()) {
                    if (args[0] != "current") {
                        var toReturn = ""
                        toReturn += args[0]
                        args[0] = ""
                        for (s in args) {
                            toReturn += " "
                            toReturn += s
                        }
                        config.discordCustomText = toReturn
                        config.writeData()
                        config.markDirty()
                        Utils.sendMsg(EnumChatFormatting.GREEN.toString() + "You set your custom status to: " + EnumChatFormatting.LIGHT_PURPLE + toReturn)
                    } else {
                        Utils.sendMsg(EnumChatFormatting.GREEN.toString() + "Your current discord status is: " + EnumChatFormatting.LIGHT_PURPLE + config.discordCustomText)
                    }
                } else {
                    Utils.sendMsg(EnumChatFormatting.RED.toString() + "Improper usage! /discord {custom status}, /discord current")
                }
            }
        })
    var RenderingCommand =
        SimpleCommand("re", Arrays.asList("render", "rendering", "re"), object : ProcessCommandRunnable() {
            override fun processCommand(sender: ICommandSender?, args: Array<String>) {
                if (args.size == 0) {
                    config.renderPlayers = !config.renderPlayers
                    config.markDirty()
                    config.writeData()
                    if (config.renderPlayers) {
                        Utils.sendMsg(EnumChatFormatting.GRAY.toString() + "Stop rendering players set to " + EnumChatFormatting.GREEN + EnumChatFormatting.BOLD + "ON" + EnumChatFormatting.GRAY + "!")
                    } else {
                        Utils.sendMsg(EnumChatFormatting.GRAY.toString() + "Stop rendering players set to " + EnumChatFormatting.RED + EnumChatFormatting.BOLD + "OFF" + EnumChatFormatting.GRAY + "!")
                    }
                } else {
                    val text =
                        ChatComponentText(EnumChatFormatting.RED.toString() + "SkyblockReinvented now uses an interactive GUI to add players to the whitelist. Click this message or do /sre playerfilter to access this whitelist!")
                    text.chatStyle =
                        text.chatStyle.setChatClickEvent(ClickEvent(ClickEvent.Action.RUN_COMMAND, "/sre playerfilter"))
                    Minecraft.getMinecraft().thePlayer.addChatComponentMessage(text)
                }
            }
        })
    var FragRunCommand = SimpleCommand(
        "frags",
        Arrays.asList("fragbot", "frag", "invitefrag", "fragrun", "fragbots", "fr"),
        object : ProcessCommandRunnable() {
            override fun processCommand(sender: ICommandSender?, args: Array<String>) {
                val amount: Int
                amount = if (args.size == 1) {
                    args[0].toInt()
                } else {
                    1
                }
                Thread(Runnable {
                    try {
                        val fragBots = FragStatus.getBestBot(amount)
                        if (fragBots.size == 0) {
                            Utils.sendMsg("Could not find any Bots. Open a Ticket on our partner's " + EnumChatFormatting.UNDERLINE + EnumChatFormatting.AQUA + " Discord")
                        }
                        fragBots.forEach(Consumer { fragBot: FragBot ->
                            val username = fragBot.username
                            Minecraft.getMinecraft().thePlayer.sendChatMessage("/p $username")
                            try {
                                Thread.sleep(1000)
                            } catch (e: InterruptedException) {
                                e.printStackTrace()
                            }
                        })
                        Utils.sendMsg(
                            "These bots are provided by SkyblockReinvented's partner, Fragrunners. You can support them by joining their "
                                    + EnumChatFormatting.UNDERLINE + EnumChatFormatting.AQUA + "Discord!"
                        )
                    } catch (e: MalformedURLException) {
                        Utils.sendMsg(
                            "There was an Error fetching the Data. Open a Ticket on Fragrunner's (our partner) "
                                    + EnumChatFormatting.UNDERLINE + EnumChatFormatting.AQUA + "Discord!"
                        )
                        e.printStackTrace()
                    } catch (e: JSONException) {
                        Utils.sendMsg(
                            "There was an Error fetching the Data. Open a Ticket on Fragrunner's (our partner) "
                                    + EnumChatFormatting.UNDERLINE + EnumChatFormatting.AQUA + "Discord!"
                        )
                        e.printStackTrace()
                    }
                }).start()
            }
        })
    var PingCommand = SimpleCommand(
        "ping",
        Arrays.asList("pi", "pingplayer", "playerping", "sreping"),
        object : ProcessCommandRunnable() {
            override fun processCommand(sender: ICommandSender?, args: Array<String>) {
                if (!Utils.inDungeons || !Utils.inSkyblock) {
                    Utils.sendMsg(EnumChatFormatting.RED.toString() + "You aren't in dungeons! You may only use this command in dungeons.")
                    return
                } else if (args.size == 0) {
                    Utils.sendMsg(EnumChatFormatting.RED.toString() + "Improper usage! Proper usage: /ping {player} [message (optional)]")
                    return
                } else {
                    if (args.size == 1) {
                        Utils.sendCommand("pc SREPingCommand: " + args[0])
                    } else if (args.size > 1) {
                        Utils.sendCommand("pc SREPingCommand: " + args[0] + " REASON: " + args[1])
                    }
                }
            }
        })
    var DragCommand = SimpleCommand("drags", Arrays.asList("dragon", "drags", "dr"), object : ProcessCommandRunnable() {
        override fun processCommand(sender: ICommandSender?, args: Array<String>) {
            if (args.size == 0) {
                config.dragTracker = !config.dragTracker
            } else if (args[0] == "clear") {
                config.dragsSinceAotd = 0
                config.dragsSincePet = 0
                config.dragsSinceSup = 0
                DragTracker.recentDrags.clear()
                config.writeData()
                config.markDirty()
            } else {
                Utils.sendMsg(EnumChatFormatting.RED.toString() + "Invalid usage! Use either /drag or /drag clear.")
            }
        }
    })
    private var secondsPassed = 4
    private var needsToClick = 2
    var SwaphubCommand =
        SimpleCommand("swaphub", Arrays.asList("swh", "sw", "shub", "swap"), object : ProcessCommandRunnable() {
            override fun processCommand(sender: ICommandSender?, args: Array<String>) {
                needsToClick = 1
                secondsPassed = -1
                Utils.sendCommand("warpforge")
            }
        })
    var GarryCommand = SimpleCommand("garrytoggle", Arrays.asList("gtoggle"), object : ProcessCommandRunnable() {
        override fun processCommand(sender: ICommandSender?, args: Array<String>) {
            FilterHandler.secondsPassed = 0
        }
    })

    @SubscribeEvent(receiveCanceled = true)
    fun onInput(event: MouseEvent) {
        if (config.joinSB && !Utils.inSkyblock && mc.thePlayer != null && mc.theWorld != null && !mc.isSingleplayer) {
            event.isCanceled = true
            Utils.sendCommand("play skyblock")
            Utils.inSkyblock = true
            return
        }
        if (needsToClick == 1) {
            event.isCanceled = true
            needsToClick = 0
        }
    }

    companion object {
        @JvmStatic
        lateinit var config: Config
        var customFilters: FilterGUI? = null
        const val MODID = "sre"
        const val MOD_NAME = "SkyblockReinvented"
        const val VERSION = "1.0-pre2"
        var keyBindings = arrayOfNulls<KeyBinding>(2)
        var modDir = File(File(Minecraft.getMinecraft().mcDataDir, "config"), "SRE")
        var discordRPC: DiscordRPC? = null
        val mc = Minecraft.getMinecraft()
        var GUIMANAGER: GuiManager? = null
        var currentGui: GuiScreen? = null
        var filter: FilterHandler? = null
        var coopHelper: MiscFeatures? = null
        var ticks = 0
        @JvmField
        var usingLabymod = false
        @JvmField
        val gson: Gson = GsonBuilder().setPrettyPrinting().create()
    }
}