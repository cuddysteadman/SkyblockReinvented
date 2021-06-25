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

package thecudster.sre;

import gg.essential.vigilance.VigilanceConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.command.ICommandSender;
import net.minecraft.event.ClickEvent;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.lwjgl.input.Keyboard;
import thecudster.sre.core.Changelog;
import thecudster.sre.core.gui.*;
import thecudster.sre.events.*;
import thecudster.sre.features.impl.filter.CustomPlayersFilter;
import thecudster.sre.features.impl.filter.RemoveRaffleTitles;
import thecudster.sre.features.impl.skills.bestiary.BestiaryGUI;
import thecudster.sre.features.impl.skills.bestiary.BestiaryProgress;
import thecudster.sre.features.impl.dragons.DragTracker;
import thecudster.sre.features.impl.dragons.DragonArrowHitbox;
import thecudster.sre.features.impl.dungeons.*;
import thecudster.sre.features.impl.filter.BlockPowerOrb;
import thecudster.sre.features.impl.filter.FilterHandler;
import thecudster.sre.features.impl.qol.*;
import thecudster.sre.features.impl.rendering.HyperionOverlay;
import thecudster.sre.features.impl.rendering.PlayerHider;
import thecudster.sre.features.impl.rendering.RemoveVillagers;
import thecudster.sre.features.impl.skills.slayer.SlayerFeatures;
import thecudster.sre.features.impl.skills.slayer.SlayerReminder;
import thecudster.sre.features.impl.skills.slayer.SlayerTracker;
import thecudster.sre.features.impl.sounds.MiscSoundBlocks;
import thecudster.sre.core.Config;
import thecudster.sre.core.Keybindings;
import thecudster.sre.features.impl.dungeons.FragBot;
import thecudster.sre.features.impl.dungeons.FragStatus;
import thecudster.sre.util.sbutil.ArrStorage;
import thecudster.sre.util.sbutil.CurrentLoc;
import thecudster.sre.util.sbutil.SimpleCommand;
import thecudster.sre.util.Utils;

import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static sun.security.x509.ReasonFlags.UNUSED;

@SuppressWarnings(UNUSED)
@Mod(modid = SkyblockReinvented.MODID, name = SkyblockReinvented.MOD_NAME, version = SkyblockReinvented.VERSION, acceptedMinecraftVersions = "[1.8.9]", clientSideOnly = true)
public class SkyblockReinvented {
	public static Config config = new Config();
	public static FilterGUI customFilters;
	public static final String MODID = "sre";
	public static final String MOD_NAME = "SkyblockReinvented";
	public static final String VERSION = "1.0-pre1";
	public static KeyBinding[] keyBindings = new KeyBinding[2];
	public static File modDir = new File(new File(Minecraft.getMinecraft().mcDataDir, "config"), "SRE");
	public static DiscordRPC discordRPC;
	public static final Minecraft mc = Minecraft.getMinecraft();
	public static GuiManager GUIMANAGER;
	public static GuiScreen currentGui;
	public static FilterHandler filter;
	public boolean notUsingDSM;
	private RenderPlayersGUI renderWhitelist = new RenderPlayersGUI();
	public static MiscFeatures coopHelper;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
        if (!modDir.exists()) modDir.mkdirs();

		customFilters = new FilterGUI();
		GUIMANAGER = new GuiManager();
		discordRPC = new DiscordRPC();
		filter = new FilterHandler();
		FetchurSolver.init();
		TreasureLocs.init();
		ArrStorage.init();
		renderWhitelist.readConfig();
		coopHelper = new MiscFeatures();
		coopHelper.readConfig();
	}
	@EventHandler
	public void init(FMLInitializationEvent event) {
        customFilters.readConfig();
        notUsingDSM = !Loader.isModLoaded("Danker's Skyblock Mod");
		config.preload();
		ClientCommandHandler.instance.registerCommand(SRECommand);
		ClientCommandHandler.instance.registerCommand(RenderingCommand);
		ClientCommandHandler.instance.registerCommand(DiscordCommand);
		ClientCommandHandler.instance.registerCommand(FragRunCommand);
		ClientCommandHandler.instance.registerCommand(DragCommand);
		ClientCommandHandler.instance.registerCommand(PingCommand);
		ClientCommandHandler.instance.registerCommand(GarryCommand);
		ClientCommandHandler.instance.registerCommand(SwaphubCommand);
		ClientCommandHandler.instance.registerCommand(CoopCommand);

		MinecraftForge.EVENT_BUS.register(new RemoveRaffleTitles());
		MinecraftForge.EVENT_BUS.register(this);
		// MinecraftForge.EVENT_BUS.register(new SkillXPTracker());
		MinecraftForge.EVENT_BUS.register(new SlayerReminder());
		MinecraftForge.EVENT_BUS.register(new PlayerHider());
		MinecraftForge.EVENT_BUS.register(new GiftCompassWaypoints());
		MinecraftForge.EVENT_BUS.register(new BlockPowerOrb());
		MinecraftForge.EVENT_BUS.register(new WitherCloakHider());
		MinecraftForge.EVENT_BUS.register(new RemoveVillagers());
		MinecraftForge.EVENT_BUS.register(new HyperionOverlay());
		MinecraftForge.EVENT_BUS.register(new MiscSoundBlocks());
		MinecraftForge.EVENT_BUS.register(new Keybindings());
		MinecraftForge.EVENT_BUS.register(new MiscClickBlocks());
		MinecraftForge.EVENT_BUS.register(new MiscGUIs());
		MinecraftForge.EVENT_BUS.register(new GhostLoot());
		MinecraftForge.EVENT_BUS.register(new MiscFeatures());
		MinecraftForge.EVENT_BUS.register(GUIMANAGER);
		MinecraftForge.EVENT_BUS.register(new DragonArrowHitbox());
		MinecraftForge.EVENT_BUS.register(new SlayerTracker());
		MinecraftForge.EVENT_BUS.register(new BestiaryProgress());
		MinecraftForge.EVENT_BUS.register(new BestiaryGUI());
		MinecraftForge.EVENT_BUS.register(new JerrychineHider());
		MinecraftForge.EVENT_BUS.register(new CakeStackSize());
		MinecraftForge.EVENT_BUS.register(new DragTracker());
		MinecraftForge.EVENT_BUS.register(new CreeperSolver());
		MinecraftForge.EVENT_BUS.register(new WorldChangeEvent());
		MinecraftForge.EVENT_BUS.register(new TreasureLocs());
		MinecraftForge.EVENT_BUS.register(new DungeonFeatures());
		MinecraftForge.EVENT_BUS.register(new SlayerFeatures());
		MinecraftForge.EVENT_BUS.register(new FetchurSolver());
		MinecraftForge.EVENT_BUS.register(filter);
		MinecraftForge.EVENT_BUS.register(new MainGUI());

		if (Minecraft.getMinecraft().gameSettings.language != null) {
			ScreenRenderer.fontRenderer.setUnicodeFlag(Minecraft.getMinecraft().isUnicode());
			ScreenRenderer.fontRenderer.setBidiFlag(Minecraft.getMinecraft().getLanguageManager().isCurrentLanguageBidirectional());
		}
		IReloadableResourceManager mgr = (IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager();
		mgr.registerReloadListener(ScreenRenderer.fontRenderer);
		keyBindings[0] = new KeyBinding("Refresh Location", Keyboard.KEY_H, "SkyblockReinvented");
		keyBindings[1] = new KeyBinding("Pickup Stash", Keyboard.KEY_G, "SkyblockReinvented");
		for (KeyBinding keyBinding : keyBindings) {
			ClientRegistry.registerKeyBinding(keyBinding);
		}
	}
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
	}
	@SubscribeEvent
	public void onLogout(PlayerEvent.PlayerLoggedOutEvent event) {
		MinecraftForge.EVENT_BUS.post(new LeaveSkyblockEvent());
	}
	public static int ticks = 0;
	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event) {
		if (event.phase != TickEvent.Phase.START) return;
		if (currentGui != null) {
			mc.displayGuiScreen(currentGui);
			currentGui = null;
		}
		if (ticks % 20 == 0) {
			if (mc.thePlayer == null) { return; }
			ScreenRenderer.refresh();
			CurrentLoc.checkLoc();
			DragTracker.updateGui();
			MinecraftForge.EVENT_BUS.post(new SecondPassedEvent());
			ticks = 0;
			if (secondsPassed == -1) {
				secondsPassed += 2;
			} else if (secondsPassed < 3) {
				secondsPassed++;
			}
			if (needsToClick == 0 && secondsPassed == 3) {
				Utils.sendCommand("warp hub");
				needsToClick = 2;
				secondsPassed = 4;
			}
			try {
				if (mc.theWorld == null) { return; }
				if (mc.theWorld.getScoreboard() == null) { return; }
				Scoreboard scoreboard = mc.theWorld.getScoreboard();
				for (Score score : scoreboard.getSortedScores(scoreboard.getObjectiveInDisplaySlot(1))) {
					String name = StringUtils.stripControlCodes(ScorePlayerTeam.formatPlayerName(scoreboard.getPlayersTeam(score.getPlayerName()), score.getPlayerName()));
					if (name.contains("Ironman") && SkyblockReinvented.config.removeIronmanScoreboard) {
						scoreboard.removeTeam(scoreboard.getPlayersTeam(score.getPlayerName()));
						Utils.ironmanProfile = true;
					} else if (name.contains("Mithril") && SkyblockReinvented.config.hideMithrilPowder) {
						scoreboard.removeTeam(scoreboard.getPlayersTeam(score.getPlayerName()));
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		ticks++;
	}
	@SubscribeEvent
	public void onLeave(LeaveSkyblockEvent event) {
		if (discordRPC.isActive()) {
			discordRPC.stop();
		}
	}
	@SubscribeEvent
	public void onJoin(JoinSkyblockEvent event) {
		/*
		if (config.myVersion == null || this.VERSION != config.myVersion) {
			config.myVersion = this.VERSION;
			config.markDirty();
			config.writeData();
			Changelog.printChangelog(this.VERSION);
		}
		 */
		if (config.discordRP) {
			discordRPC.start();
		} else if (discordRPC.isActive()) {
			discordRPC.stop();
		}
	}


	// begin commands
	public SimpleCommand SRECommand = new SimpleCommand("sre", Arrays.asList("SRE", "SkyblockReinvented", "skyblockreinvented", "sbr", "SBR"), new SimpleCommand.ProcessCommandRunnable() {
		@Override
		public void processCommand(ICommandSender sender, String[] args) {
			if (args.length > 0) {
				switch (args[0]) {
					case "gui":
					case "editlocations":
						currentGui = new LocationEditGUI();
						break;
					case "help":
						Utils.sendHelp();
						break;
					case "config":
						currentGui = config.gui();
						break;
					case "vigilance":
						currentGui = VigilanceConfig.INSTANCE.gui();
						break;
					case "github":
						try {
							Desktop.getDesktop().browse(new URI("https://github.com/theCudster/SkyblockReinvented"));
						} catch (IOException | URISyntaxException ex) {
							ex.printStackTrace();
						}
						break;
					case "discord":
						try {
							Desktop.getDesktop().browse(new URI("https://discord.com/invite/xkeYgZrRbN"));
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					case "main":
						currentGui = new MainGUI();
						return;
					case "filter":
						currentGui = new FilterGUI();
						return;
					case "playerfilter":
						currentGui = new RenderPlayersGUI();
						return;
					default:
						Utils.sendMsg(EnumChatFormatting.RED + "Unknown argument! Acceptable parameters are config, help, main, filter, playerfilter, editlocations, vigilance, github, discord, and gui.");
						break;
				}
			} else {
				if (SkyblockReinvented.config.openConfig == 1) {
					currentGui = config.gui();
				} else {
					currentGui = new MainGUI();
				}
			}
		}
	});
	public SimpleCommand CoopCommand = new SimpleCommand("addcoopmember", Arrays.asList("cadd", "addcmem"), new SimpleCommand.ProcessCommandRunnable() {
		@Override
		public void processCommand(ICommandSender sender, String[] args) {
			if (args.length == 0) {
				Utils.sendMsg(EnumChatFormatting.RED + "Invalid usage! Proper usage is /addcoopmember {name} [disabled/enabled]");
			}
			else if (args.length == 1) {
				SkyblockReinvented.coopHelper.addDisabledMember(args[0]);
			} else if (args.length >= 2) {
				if (args[1].toLowerCase().equals("true") || args[1].toLowerCase().equals("enabled")) {
					SkyblockReinvented.coopHelper.addEnabledMember(args[0]);
				} else {
					SkyblockReinvented.coopHelper.addDisabledMember(args[0]);
				}
			}
		}
	});
	public SimpleCommand DiscordCommand = new SimpleCommand("dset", Arrays.asList("da", "disc", "dset", "discset", "rp", "rpset"), new SimpleCommand.ProcessCommandRunnable() {
		@Override
		public void processCommand(ICommandSender sender, String[] args) {
			if (!Utils.inSkyblock) { Utils.sendMsg(EnumChatFormatting.RED + "Use this command in Skyblock!"); return; }
			if (!SkyblockReinvented.config.discordRP) { Utils.sendMsg(EnumChatFormatting.RED + "You do not have discord rich presence on, so this command will do nothing."); return; }
			if (!(SkyblockReinvented.config.discordMode == 4)) { Utils.sendMsg(EnumChatFormatting.RED + "You do not have the custom discord rich presence setting on, so this command will do nothing."); return; }
			if (args.length > 0) {
				if (!(args[0].equals("current"))) {
					String toReturn = "";
					toReturn += args[0];
					args[0] = "";
					for (String s : args) {
						toReturn += " ";
						toReturn += s;
					}
					SkyblockReinvented.config.discordCustomText = toReturn;
					SkyblockReinvented.config.writeData();
					SkyblockReinvented.config.markDirty();
					Utils.sendMsg(EnumChatFormatting.GREEN + "You set your custom status to: " + EnumChatFormatting.LIGHT_PURPLE + toReturn);
				} else {
					Utils.sendMsg(EnumChatFormatting.GREEN + "Your current discord status is: " + EnumChatFormatting.LIGHT_PURPLE + SkyblockReinvented.config.discordCustomText);
				}
			} else {
				Utils.sendMsg(EnumChatFormatting.RED + "Improper usage! /discord {custom status}, /discord current");
			}
		}
	});
	public SimpleCommand RenderingCommand = new SimpleCommand("re", Arrays.asList("render", "rendering", "re"), new SimpleCommand.ProcessCommandRunnable() {
		@Override
		public void processCommand(ICommandSender sender, String[] args) {
			if (args.length == 0) {
				SkyblockReinvented.config.renderPlayers = !SkyblockReinvented.config.renderPlayers;
				SkyblockReinvented.config.markDirty();
				SkyblockReinvented.config.writeData();
				if (SkyblockReinvented.config.renderPlayers) {
					Utils.sendMsg(EnumChatFormatting.GRAY + "Stop rendering players set to " + EnumChatFormatting.GREEN + EnumChatFormatting.BOLD + "ON" + EnumChatFormatting.GRAY + "!");
				} else {
					Utils.sendMsg(EnumChatFormatting.GRAY + "Stop rendering players set to " + EnumChatFormatting.RED + EnumChatFormatting.BOLD + "OFF" + EnumChatFormatting.GRAY + "!");
				}
			}
			else {
				ChatComponentText text = new ChatComponentText(EnumChatFormatting.RED + "SkyblockReinvented now uses an interactive GUI to add players to the whitelist. Click this message or do /sre playerfilter to access this whitelist!");
				text.setChatStyle(text.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/sre playerfilter")));
				Minecraft.getMinecraft().thePlayer.addChatComponentMessage(text);
			}
		}
	});
	SimpleCommand FragRunCommand = new SimpleCommand("frags", Arrays.asList("fragbot", "frag", "invitefrag", "fragrun", "fragbots", "fr"), new SimpleCommand.ProcessCommandRunnable() {
		@Override
		public void processCommand(ICommandSender sender, String[] args) {
			int amount;
			if(args.length == 1){
				amount = Integer.parseInt(args[0]);
			}else{
				amount = 1;
			}
			new Thread(() -> {
				try{
					List<FragBot> fragBots = FragStatus.getBestBot(amount);
					if(fragBots.size() == 0){
						Utils.sendMsg("Could not find any Bots. Open a Ticket on our partner's " +  EnumChatFormatting.UNDERLINE + EnumChatFormatting.AQUA +" Discord");
						return;
					}
					fragBots.forEach((fragBot) -> {
						String username = fragBot.getUsername();
						Minecraft.getMinecraft().thePlayer.sendChatMessage("/p " + username);
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					});
					Utils.sendMsg("These bots are provided by SkyblockReinvented's partner, Fragrunners. You can support them by joining their "
							+ EnumChatFormatting.UNDERLINE + EnumChatFormatting.AQUA+"Discord!");
				} catch (MalformedURLException | JSONException e) {
					Utils.sendMsg("There was an Error fetching the Data. Open a Ticket on Fragrunner's (our partner) "
							+EnumChatFormatting.UNDERLINE + EnumChatFormatting.AQUA + "Discord!");
					e.printStackTrace();
				}
			}).start();
		}
	});
	SimpleCommand PingCommand = new SimpleCommand("ping", Arrays.asList("pi", "pingplayer", "playerping", "sreping"), new SimpleCommand.ProcessCommandRunnable() {
		@Override
		public void processCommand(ICommandSender sender, String[] args) {
			if (!Utils.inDungeons || !Utils.inSkyblock) {
				Utils.sendMsg(EnumChatFormatting.RED + "You aren't in dungeons! You may only use this command in dungeons.");
				return;
			} else if (args.length == 0) {
				Utils.sendMsg(EnumChatFormatting.RED + "Improper usage! Proper usage: /ping {player} [message (optional)]");
				return;
			} else {
				if (args.length == 1) {
					Utils.sendCommand("pc SREPingCommand: " + args[0]);
				} else if (args.length > 1) {
					Utils.sendCommand("pc SREPingCommand: " + args[0] + " REASON: " + args[1]);
				}
			}
		}
	});
	SimpleCommand DragCommand = new SimpleCommand("drags", Arrays.asList("dragon", "drags", "dr"), new SimpleCommand.ProcessCommandRunnable() {
		@Override
		public void processCommand(ICommandSender sender, String[] args) {
			if (args.length == 0) {
				SkyblockReinvented.config.dragTracker = !SkyblockReinvented.config.dragTracker;
			} else if (args[0].equals("clear")) {
				SkyblockReinvented.config.dragsSinceAotd = 0;
				SkyblockReinvented.config.dragsSincePet = 0;
				SkyblockReinvented.config.dragsSinceSup = 0;
				DragTracker.recentDrags.clear();
				SkyblockReinvented.config.writeData();
				SkyblockReinvented.config.markDirty();
			} else {
				Utils.sendMsg(EnumChatFormatting.RED + "Invalid usage! Use either /drag or /drag clear.");
			}
		}
	});
	private int secondsPassed = 4;
	private int needsToClick = 2;
	SimpleCommand SwaphubCommand = new SimpleCommand("swaphub", Arrays.asList("swh", "sw", "shub", "swap"), new SimpleCommand.ProcessCommandRunnable() {
		@Override
		public void processCommand(ICommandSender sender, String[] args) {
			needsToClick = 1;
			secondsPassed = -1;
			Utils.sendCommand("warpforge");
		}
	});
	SimpleCommand GarryCommand = new SimpleCommand("garrytoggle", Arrays.asList("gtoggle"), new SimpleCommand.ProcessCommandRunnable() {
		@Override
		public void processCommand(ICommandSender sender, String[] args) {
			FilterHandler.secondsPassed = 0;
		}
	});

	@SubscribeEvent(receiveCanceled = true)
	public void onInput(MouseEvent event) {
		if (SkyblockReinvented.config.joinSB && !Utils.inSkyblock && mc.thePlayer != null && mc.theWorld != null && !mc.isSingleplayer()) {
			event.setCanceled(true);
			Utils.sendCommand("play skyblock");
			Utils.inSkyblock = true;
			return;
		}
		if (needsToClick == 1) {
			event.setCanceled(true);
			needsToClick = 0;
		}
	}
}
