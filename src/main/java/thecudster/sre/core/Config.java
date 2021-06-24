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

package thecudster.sre.core;

import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyType;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.features.impl.filter.CustomPlayersFilter;

import java.io.File;
import java.util.ArrayList;


public class Config extends Vigilant {
	@Property(
			type = PropertyType.SLIDER,
			name = "Amt Clicks to Stop From Opening Chest",
			description = "The amount of clicks you need to stop you from opening chests at the end of dungeons.",
			category = "Dungeons",
			subcategory = "Misc",
			min = 0,
			max = 10
	)
	public int chestStop = 0;
	@Property(
			type = PropertyType.SWITCH,
			name = "Ghost Loot Tracker",
			description = "Tracks the loot you get from ghosts.",
			category = "Dwarven Mines",
			subcategory = "Combat"
	)
	public boolean ghostTracker = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Pickup Stash Messages and Keybind",
			description = "Creates a title and plays a sound when you need to pickupstash.",
			category = "General",
			subcategory = "Misc"
	)
	public boolean stash = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Dark Auction Reminder",
			description = "Plays a loud sound and creates title when the dark auction is starting soon.",
			category = "General",
			subcategory = "Misc"
	)
	public boolean darkAuction = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "First Load",
			description = "Detects whether it is the user's first time using SRE.",
			category = "General",
			subcategory = "Misc",
			hidden = true
	)
	public boolean firstLoad = true;
	@Property(
			type = PropertyType.SWITCH,
			name = "Hub Overlay",
			description = "Overlays hubs that you can join in green and hubs that you can't join in red.",
			category = "Hub",
			subcategory = "Rendering"
	)
	public boolean hubOverlay = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Creeper Sounds from Veil",
			description = "Removes Creeper Veil constant creeper/skeleton noises. \n§cThis feature removes all creeper & skeleton hurt noises while creeper veil is activated.",
			category="Items",
			subcategory="Sounds"
	)
	public boolean creeperSounds = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Slayer Info",
			description = "Tracks multiple things about slayers such as your RNGesus meter and your XP to next level.",
			category = "Skills",
			subcategory = "Slayer"
	)
	public boolean slayerInfo = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Dungeon Potion Messages",
			description = "§aYour active Potion Effects have been paused and stored. They will be restored when you leave Dungeons! You are not allowed to use existing Potion Effects while in Dungeons.",
			category = "Spam",
			subcategory = "Dungeons"
	)
	public boolean dungeonPotionMsg = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Disable Farm Breaking Particles",
			description = "Disables all particles from breaking farm blocks.",
			category = "Your Island",
			subcategory = "Misc"
	)
	public boolean disableFarmParticles = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Doubled Messages",
			description = "§aYour Mage stats are doubled because you are the only player using this class!§7 (and much more)",
			category = "Spam",
			subcategory = "Dungeons"
	)
	public boolean doubleMsg = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Opening Messages",
			description = "§cYou hear the sound of something opening...",
			category = "Spam",
			subcategory = "Dungeons"
	)
	public boolean openMsg = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Journal Messages",
			description = "§7You have found page §e1 §7of Karyelle's Diary!",
			category = "Spam",
			subcategory = "Dungeons"
	)
	public boolean journalMsg = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Crypt Wither Skull Messages",
			description = "§7A Crypt Wither Skull exploded, hitting you for §c27000.0§7 damage.",
			category = "Spam",
			subcategory = "Dungeons"
	)
	public boolean cryptWitherSkull = false;

	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Damage Milestone Messages",
			description = "§l§eTank Milestone 2§7: You have tanked and dealt §c180000§7 Total Damage so far! §a00m 31s",
			category = "Spam",
			subcategory = "Dungeons"
	)
	public boolean damageMilestones = false;

	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Lost Adventurer Messages",
			description = "§cLost Adventurer §aused §6Dragon's Breath§a on you!",
			category = "Spam",
			subcategory = "Dungeons"
	)
	public boolean dragonsBreath = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Auto /garry",
			description = "Automatically teleports you to garry.",
			category = "Dwarven Mines",
			subcategory = "General"
	)
	public boolean autoGarry = false;

	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Wither Essence Messages",
			description = "§btheCudster§f found a §dWither Essence§f! Everyone Gains an extra essence!",
			category = "Spam",
			subcategory = "Dungeons"
	)

	public boolean essenceMessages = false;


	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Wither Door Opened Messages",
			description = "§btheCudster§a opened a §l§8WITHER§a door!\n§cRecommended to not use this with don't have key message remover.",
			category = "Spam",
			subcategory = "Dungeons"
	)
	public boolean openWither = false;

	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Can't Open Wither Door Messages", // could be causing errors
			description = "§cYou do not have the key for this door!\n§cRecommended to not use this with opened wither door message remover.",
			category = "Spam",
			subcategory = "Dungeons"
	)
	public boolean cantOpenWither = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Bone Plating Messages",
			description = "§eYour bone plating reduced the damage you took by §c4,944§e!",
			category = "Spam",
			subcategory = "Dungeons"
	)
	public boolean bonePlating = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Clean Ending of Dungeon",
			description = "Gives a clean end to the dungeon (chat).",
			category = "Dungeons",
			subcategory = "Misc"
	)
	public boolean cleanEnd = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Hide Travel To Your Island",
			description = "Hides the armour stand in the hub with this nametag.",
			category = "Hub",
			subcategory = "General"
	)
	public boolean travelIsland = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Bazaar Messages",
			description = "§7Putting coins in escrow...\n§7Executing instant buy...§7 (and more)",
			category = "Spam",
			subcategory = "Hub"
	)
	public boolean bazaarMsg = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Salvage Messages",
			description = "§aYou salvaged a §1Soulstealer Bow §afor §d+5§a Undead Essence!§7 (and more)",
			category = "Spam",
			subcategory = "Dungeons"
	)
	public boolean salvage = false;

	@Property(
			type = PropertyType.SWITCH,
			name = "Bonzo Mask Alert",
			description = "Alerts you when your Bonzo Mask is triggered & cancels the chat message.",
			category = "Dungeons",
			subcategory = "Misc"
	)
	public boolean bongoAlert = false;

	@Property(
			type = PropertyType.SWITCH,
			name = "Boost Ready Alert",
			description = "Alerts you when your Mining Speed Boost is ready to be used.\nOnly triggers when in the Dwarven Mines.",
			category = "Dwarven Mines",
			subcategory = "Alerts"
	)
	public boolean readyAlert = false;

	@Property(
			type = PropertyType.SWITCH,
			name = "Boost Expired Alert",
			description = "Alerts you when your Mining Speed Boost has expired.\nOnly triggers when in the Dwarven Mines.",
			category = "Dwarven Mines",
			subcategory = "Alerts"
	)
	public boolean expiredAlert = false;

	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Mining Speed Boost Ready",
			description = "§6Mining Speed Boost §ais now available!",
			category = "Dwarven Mines",
			subcategory = "Alerts"
	)
	public boolean removeReady = false;

	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Mining Speed Boost Used",
			description = "§aYou used your §6Mining Speed Boost§a Pickaxe Ability!",
			category = "Dwarven Mines",
			subcategory = "Alerts"
	)
	public boolean removeUsed = false;

	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Mining Speed Boost Expired",
			description = "§cYour Mining Speed Boost has expired!",
			category = "Dwarven Mines",
			subcategory = "Alerts"
	)
	public boolean removeExpired = false;

	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Orb Messages",
			description = "§btheCudster§e picked up your Defense Orb!§7 (and more)",
			category = "Spam",
			subcategory = "Dungeons"
	)
	public boolean damageOrbs = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Healer Messages",
			description = "§eYou formed a tether with §atheCudster§e!§7 (and more)",
			category = "Spam",
			subcategory = "Dungeons"
	)
	public boolean healerMsg = false;
	@Property(
			type = PropertyType.SELECTOR,
			name = "Parse Teleport Pad Names",
			description = "Changes Teleport Pad names.",
			category = "Your Island",
			subcategory = "General",
			options = {"Normal", "Shortened", "Hidden"}
	)
	public int teleportPad = 0;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Dungeon Finder Messages",
			description = "§aRefreshing...§7 (and more)",
			category = "Spam",
			subcategory = "Dungeons"
	)
	public boolean dungeonFinder;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Watcher Messages",
			description = "§c[BOSS] The Watcher§f: That will be enough for now.§7 (and more)",
			category = "Spam",
			subcategory = "Dungeons"
	)
	public boolean removeWatcher = false;
	@Property(
			type = PropertyType.SELECTOR,
			name = "Remove Incorrect Three Weirdos Messages",
			description = "Removes incorrect or all Three Weirdos messages. Solvers still work.",
			category = "Spam",
			subcategory = "Dungeons",
			options = {"Off", "Incorrect Answers", "All"}
	)
	public int threeWeirdosIncorrect = 0;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Wither Skulls Messages",
			description = "§c[SKULL]§7 Wither Skull: §fMonsters with a star next to their name have Wither Keys...sometimes. §7(and more)",
			category = "Spam",
			subcategory = "Dungeons"
	)
	public boolean witherSkulls = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Useless Dungeon Sells",
			description = "§aYou sold §5Clean Zombie Soldier Chestplate §8x1 §afor §637 Coins§a!§7 (and more)",
			category = "Spam",
			subcategory = "Dungeons"
	)
	public boolean sellable = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Struck Messages",
			description = "§cThe Stormy Skeleton Master struck you for 1,235.1 damage!§7 (and more)",
			category = "Spam",
			subcategory = "Dungeons"
	)
	public boolean struckYou = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Raffle Messages",
			description = "§eYou registered §a69 tickets§e in the raffle event!§7 (and more)",
			category = "Spam",
			subcategory = "Dwarven Mines"
	)
	public boolean ticketMsgs = false;
	@Property(
			type = PropertyType.SELECTOR,
			name = "Fetchur Solver or Spam",
			description = "Can be configured as either a Spam (hides unimportant messages) or a solver (hides unimportant messages AND solves).",
			category = "Dwarven Mines",
			subcategory = "General",
			options = {"Off", "Spam", "Solver"}
	)
	public int showFetchur = 0;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Puzzler Messages",
			description = "§e[NPC] §dPuzzler§f: Nice! Here, have some Mithril Powder!§7 (and more)",
			category = "Spam",
			subcategory = "Dwarven Mines"
	)
	public boolean removePuzzler = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Golden Goblin Messages",
			description = "§6§lA Golden Goblin has spawned from the earth!§7 (and more)",
			category = "Spam",
			subcategory = "Dwarven Mines"
	)
	public boolean goldenGoblin = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Golden Goblin Alert",
			description = "Plays a sound and alert when a golden goblin spawns.",
			category = "Dwarven Mines",
			subcategory = "General"
	)
	public boolean goblinAlert = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Messages about Bank",
			description = "§aYou have withdrawn §669,420 Coins§a! You now have §60 Coins§a in your account!§7 (and more)",
			category = "Spam",
			subcategory = "Hub"
	)
	public boolean bankMsgs = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove GEXP Messages",
			description = "§aYou earned §213 GEXP§a from playing SkyBlock!",
			category = "Spam",
			subcategory = "Hypixel"
	)
	public boolean gexpMsg = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove RNG Messages from Dicer",
			description = "§5§lPRAY TO RNGESUS DROP! §eDicer dropped §a64x Enchanted Pumpkin§e!",
			category = "Spam",
			subcategory = "Farming"
	)
	public boolean removeRNGChat = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Minion Inventory Warnings",
			description = "§cYour inventory does not have enough free space to add all items!",
			category = "Spam",
			subcategory = "Your Island"
	)
	public boolean removeMinionWarnings = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Chat from Jerries",
			description = "§eYour §c1§e extra Jerry packed his stuff and left!",
			category = "Spam",
			subcategory = "Your Island"
	)
	public boolean jerryMsg = false;
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Too Fast Messages",
		description = "§cYou're clicking too fast!§7 (and more)",
		category = "Spam",
		subcategory = "General"
	)
	public boolean removeTooFast;
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Creeper Veil Messages",
		description = "§dCreeper Veil §aActivated!\n§dCreeper Veil §cDe-activated!",
		category = "Spam",
		subcategory = "Items"
	)
	public boolean removeCreeperVeil;
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Skeleton Hat Messages",
		description = "§cYour Bone Shield was destroyed! §7(and more)",
		category = "Spam",
		subcategory = "Items"
	)
	public boolean skeletonHat = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Welcome Messages",
			description = "§eWelcome to §aHypixel Skyblock§e!",
			category = "Spam",
			subcategory = "General"
	)
	public boolean welcomeMsg = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Warp Messages",
			description = "§7Sending to server mini986R... §7(and more)",
			category = "Spam",
			subcategory = "General"
	)
	public boolean warps;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Headless Horseman Spawn or Death Messages",
			description = "§6§lHORSEMAN HORSE DOWN! §7(and more)",
			category = "Spam",
			subcategory = "Your Island"
	)
	public boolean headlessHorseman = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Inventory Full Messages",
			description = "§c§lYour Inventory is Full!",
			category = "Spam",
			subcategory = "General"
	)
	public boolean inventoryFull = false;

	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Guardian Chestplate Messages",
			description = "§aYour Guardian Chestplate nullified the damage you just took!",
			category = "Spam",
			subcategory = "Items"
	)
	public boolean guardianChestplate = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Potion Buff Messages",
			description = "§a§lBUFF!§f You have gained §cHealing V§f!§7 (and more)",
			category = "Spam",
			subcategory = "Items"
	)
	public boolean potionMsg = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Watchdog Announcements",
			description = "§fWatchdog has banned §c§l69,420 §fplayers in the last 5 days. §7(and more)",
			category = "Spam",
			subcategory = "Hypixel"
	)
	public boolean watchdogAnnouncement = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Powder Ghast Messages",
			description = "§6The sound of pickaxes clashing against the rock has attracted the attention of the §lPOWDER §6§lGHAST! §7(and more)",
			category = "Spam",
			subcategory = "Dwarven Mines"
	)
	public boolean powderGhast = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Sven Pups",
			description = "Stops sven pups from rendering.",
			category = "Skills",
			subcategory = "Slayer"
	)
	public boolean svenPups = false;
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Slow Down Messages",
		description = "§cWhow! Slow down there. §7(and more)",
		category = "Spam",
		subcategory = "General"
	)
	public boolean slowDown = false;


	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Hub Warp Warnings/Messages",
			description = "§cThis ability is disabled while guesting! §7(and much more)",
			category = "Spam",
			subcategory = "Hub"
	)
	public boolean hubWarnings = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Redo Jacob Messages",
			description = "Fun little feature that has custom responses for what you got in a Jacob's farming contest. A bit snarky (:<",
			category = "Your Island",
			subcategory = "Chat",
			hidden = true
	)
	public boolean reskinJacob = false; // unedited because it's now a legacy feature
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Useless Jacob's Contest Messages",
			description = "§e[NPC] Jacob§f: The Farming Contest is over!§7 (and more)",
			category = "Spam",
			subcategory = "Your Island"
	)
	public boolean uselessJacob = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Useless Jerry's Workshop Messages",
			description = "§cThe Snow Cannon is reloading! §7(and much more)",
			category = "Spam",
			subcategory = "General"
	)
	public boolean cleanJerry = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Combat Warp Messages",
			description = "§cYou can't fast travel while in combat! §7(and more)",
			category = "Spam",
			subcategory = "General"
	)
	public boolean warpCombat = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove all Chat if Ironman",
			description = "Removes all chat if you are on an ironman profile.",
			category = "Spam",
			subcategory = "General"
	)
	public boolean hideIronman = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Hide Mithril Powder in Scoreboard",
			description = "Hides the mithril powder display on your scoreboard. Mainly a fix for Apec. \n§cThis feature causes many compatibility issues and should only be used if you really want it.",
			category = "Dwarven Mines",
			subcategory = "General"
	)
	public boolean hideMithrilPowder = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Hide Ironman in Scoreboard",
			description = "Hides the mithril powder display on your scoreboard. Mainly a fix for Apec.\n§cThis feature causes many compatibility issues and should only be used if you really want it.",
			category = "General",
			subcategory = "General"
	)
	public boolean removeIronmanScoreboard = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Dumb Slayer Drops",
			description = "§d§lCRAZY RARE DROP! §8(§5Beheaded Horror§8) §b(+69% Magic Find!) §7(and much more)",
			category = "Spam",
			subcategory = "Slayer"
	)
	public boolean dumbSlayerDrops = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Minion XP",
			description = "§aYou gained §b17,311.5 Mining §aexperience from minions!",
			category="Spam",
			subcategory = "Your Island"
	)
	public boolean minionXP = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Maddox Messages",
			description = "§a§lSLAYER QUEST COMPLETE! §7(and much more)",
			category = "Spam",
			subcategory = "Slayer"
	)
	public boolean maddoxMsg = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Reforge Messages",
			description = "§aYou reforged your §5Clean Zombie Soldier Chestplate §ainto a §5Titanic Zimbie Soldier Chestplate§a!",
			category = "Spam",
			subcategory = "General"
	)
	public boolean reforge = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Experimentation Table Messages",
			description = "§7You removed a §eExperimentation Table§7. (0/1)\n§7You placed a §eExperimentation Table§7. (1/1)",
			category = "Spam",
			subcategory = "Enchanting"
	)
	public boolean experimentationTable = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Warnings about Hubs",
			description = "§cYou are trying to do that too fast. Try again in a moment. §7(and much more)",
			category = "Spam",
			subcategory = "Hypixel"
	)
	public boolean serverFull = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Messages from Kat",
			description = "§aI'm currently taking care of your §5Mithril Golem§a! You can pick it up in 42 days 00 minutes 69 seconds. §7(and more)",
			category = "Spam",
			subcategory = "Hub"
	)
	public boolean katMsg = false;

	@Property(
			type = PropertyType.SWITCH,
			name = "Hide Villagers",
			description = "Hides all annoying villagers in the hub. Exceptions might be added later on.",
			category = "Hub",
			subcategory = "Rendering"
	)
	public boolean renderVillagers = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Maddox Clickable Message",
			description = "Makes a clickable message to open maddox batphone. Should not be used with DSM! Taken from DSM, download DSM.",
			category = "Skills",
			subcategory = "Slayer"
	)
	public boolean maddoxClickable = false;

	@Property(
			type = PropertyType.SWITCH,
			name = "Is Using DSM",
			description = "If not using DSM -> true, else -> false",
			category = "Hidden",
			subcategory = "Hidden",
			hidden = true
	)
	public boolean isUsingDSM;

	@Property(
		type = PropertyType.SWITCH,
		name = "Auto Join SB",
		description = "Automatically join Skyblock when joining Hypixel.",
		category = "General",
		subcategory = "QOL"
	)
	public boolean joinSB = false;
	@Property(
			type = PropertyType.NUMBER,
			name = "Ignore",
			category = "Ignore",
			hidden = true,
			max = 1000
	)
	public int dragsSinceSup;
	@Property(
			type = PropertyType.NUMBER,
			name = "Ignore2",
			category = "Ignore",
			hidden = true,
			max = 1000
	)
	public int dragsSinceAotd;
	@Property(
			type = PropertyType.NUMBER,
			name = "Ignore3",
			category = "Ignore",
			hidden = true,
			max = 1000
	)
	public int dragsSincePet;
	@Property(
			type = PropertyType.SWITCH,
			name = "Dragon Tracker",
			description = "Displays info about recent dragons, dragons since a drop, and more.",
			category = "General",
			subcategory = "Misc"
	)
	public boolean dragTracker = false;
	public ArrayList<String> toSearch = new ArrayList<String>();
	@Property(
			type = PropertyType.SWITCH,
			name = "Overlay Uncollected Jacob's Contests",
			description = "Creates overlay on uncollected jacob's contests. Other contests are overlay'd as red.",
			category = "Hub",
			subcategory = "Rendering"
	)
	public boolean jacobRender = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remind to Start New Slayer",
			description = "Reminds you to start a new slayer quest if you haven't started a new one after 5 seconds.",
			category = "Skills",
			subcategory = "Slayer"
	)
	public boolean remindSlayer = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Show Gift Compass Waypoints",
			description = "Shows waypoints for where to go when Gift Compass is held in hand.",
			category = "General",
			subcategory = "Rendering"
	)
	public boolean giftCompassWaypoints = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Stop Rendering Players",
			description = "Stop rendering all players.",
			category = "General",
			subcategory = "Rendering"
	)
	public boolean renderPlayers = false;
	@Property(
			type = PropertyType.SELECTOR,
			name = "Stop Rendering Player Armour",
			description = "Stops rendering all armour on other players except in dungeons.",
			category = "General",
			subcategory = "Rendering",
			options = {"None, Skulls, All"}
	)
	public int renderPlayerArmor = 0;
	@Property(
			type = PropertyType.SELECTOR,
			name = "Skill XP Tracker",
			description = "Tracks all of your skill XP to level 50 or level 60.",
			category = "General",
			subcategory = "Rendering",
			options = {"Off", "Level 50", "Level 60"},
			hidden = true
	)
	public int skillXPTracker = 0;
	@Property(
			type = PropertyType.SWITCH,
			name = "Discord Rich Presence",
			description = "Adds discord rich presence for skyblock. Working on image implementation!",
			category = "General",
			subcategory = "Misc"
	)
	public boolean discordRP = false;
	@Property(
			type = PropertyType.SELECTOR,
			name = "Discord RP Second Line",
			description = "Sets the second line for discord rich presence.",
			category = "General",
			subcategory = "Misc",
			options = {"Profile", "Name", "Held Item", "Playing Skyblock","Custom"}
	)
	public int discordMode = 0;
	@Property(
			type = PropertyType.TEXT,
			name = "Discord RP Custom Text",
			description = "Custom text for Discord RP. If you don't have custom enabled this does nothing.",
			category = "General",
			subcategory = "Misc",
			hidden = true
	)
	public String discordCustomText = "";
	@Property(
			type = PropertyType.TEXT,
			name = "Hypixel API Key",
			description = "Your Hypixel API key.",
			category = "General",
			subcategory = "Misc"
	)
	public String apiKey = "";
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Item Frame Names",
			description = "Removes all item frame names.\n§cThis feature can conflict with some resource packs.",
			category = "Your Island",
			subcategory = "Misc"
	)
	public boolean itemFrameNames = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Create Hitboxes Around your Arrows",
			description = "Creates white hitboxes around your arrows.",
			category = "General",
			subcategory = "Rendering"
	)
	public boolean arrowHitboxes = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Hide Jerry Heads",
			description = "Hides your jerry heads.",
			category = "General",
			subcategory = "Rendering"
	)
	public boolean hideJerry = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Treasure Hunter Waypoints",
			description = "Shows a waypoint to the treasure hunter quest you are currently doing.",
			category = "General",
			subcategory = "Misc"
	)
	public boolean treasureWaypoints = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Hide Guided Sheeps",
			description = "Sets all guided sheeps invisible.",
			category = "Dungeons",
			subcategory = "Misc"
	)
	public boolean sheep = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Show Cake Year",
			description = "Shows the cake year as the stack size of the cake.",
			category = "General",
			subcategory = "Misc"
	)
	public boolean cakeStackSize = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Bestiary Info",
			description = "Tracks multiple things about Bestiary such as your kills until next level and your current level.",
			category = "Skills",
			subcategory = "Bestiary"
	)
	public boolean bestiaryInfo = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Wither Cloak Creepers",
			description = "Removes all Creeper Veil Creepers except your own.",
			category = "Items",
			subcategory = "Rendering"
	)
	public boolean renderCreepers = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Show Hitboxes of Special Zealots",
			description = "Shows the hitbox of a special zealot when it spawns.",
			category = "General",
			subcategory = "Rendering"
	)
	public boolean specialHitbox = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Show Hitboxes of Drags & Golems",
			description = "Shows the hitbox of a dragon / a golem when it spawns.",
			category = "General",
			subcategory = "Rendering"
	)
	public boolean endDragHitbox = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Hide Other People's Arrows",
			description = "Hides all the arrows of other people while in the end.",
			category = "General",
			subcategory = "Rendering"
	)
	public boolean hideOtherArrows;
	@Property(
			type = PropertyType.SWITCH,
			name = "Stop Reforge Sounds",
			description = "Removes all anvil noises when in reforge menu.",
			category = "Hub",
			subcategory = "Sounds"
	)
	public boolean reforgeSoundOff = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Overlay Mobs in Range of Hyperion",
			description = "Overlays mobs that are in range of your hyperion with a green box around them.",
			category = "Items",
			subcategory = "Rendering"
	)
	public boolean hyperionOverlay = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Watcher Title Reminder",
			description = "Plays a noise and shows text when the watcher is ready.",
			category = "Dungeons",
			subcategory = "Misc"
	)
	public boolean watcherTitle = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Stop Rendering Guardians",
			description = "Removes Guardian Beams in Creeper Room in Dungeons.",
			category = "Dungeons",
			subcategory = "QOL"
	)
	public boolean guardianBeams = false;
	@Property(
			type = PropertyType.SLIDER,
			name = "Dungeon Floor Lock",
			description = "Only allows you to enter a certain floor of dungeons.",
			category = "Dungeons",
			subcategory = "QOL",
			max = 7
	)
	public int floorLock = 0;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remind Skeleton Masters",
			description = "Plays a sound when you are near a skeleton master.",
			category = "Dungeons",
			subcategory = "Rendering"
	)
	public boolean warnSkeletonMasters = false;
	@Property(
			type = PropertyType.SLIDER,
			max = 10,
			min = 1,
			name = "Range to Remind Skeleton Masters",
			description = "Specifies the range to play a sound from when near a skeleton master.",
			category = "Dungeons",
			subcategory = "Rendering"
	)
	public int skeletonRange = 1;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remind Bat Secrets",
			description = "Plays a sound when you are near a bat secret.",
			category = "Dungeons",
			subcategory = "Rendering"
	)
	public boolean warnBatSecrets = false;
	@Property(
			type = PropertyType.SLIDER,
			max = 10,
			min = 1,
			name = "Range to Remind Secret Bats",
			description = "Specifies the range to play a sound from when near a bat secret.",
			category = "Dungeons",
			subcategory = "Rendering"
	)
	public int batRange = 1;
	@Property(
			type = PropertyType.SWITCH,
			name = "Outline Hitboxes of Starred Mobs",
			description = "Shows the hitboxes of all starred mobs that you haven't killed yet in Dungeons.",
			category = "Dungeons",
			subcategory = "Rendering"
	)
	public boolean outlineMobs = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Hide your Spirit Bats",
			description = "Hides your spirit bats.",
			category = "Dungeons",
			subcategory = "Rendering"
	)
	public boolean spiritBats = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Ghost Titles",
			description = "Removes all titles on screen about how ghosts are dangerous.",
			category = "Dwarven Mines",
			subcategory = "Rendering"
	)
	public boolean dangerGhosts = false;

	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Titles from Raffles",
			description = "Removes the annoying \"WON 3X EVENT REWARDS!\" titles.",
			category = "Dwarven Mines",
			subcategory = "Rendering"
	)
	public boolean removeRaffleTitles = false;
	public ArrayList<CustomPlayersFilter> listToRender = new ArrayList<>();
	@Property(
			type = PropertyType.SWITCH,
			name = "Stop Power Orbs from Rendering",
			description = "Stops power orb nametags from rendering to improve visibility and FPS.",
			category = "General",
			subcategory = "Rendering"
	)
	public boolean renderPowerOrb = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Skyblock Updates",
			description = "Sends a message in chat when skyblock updates.",
			category = "General",
			subcategory = "Misc"
	)
	public boolean skyblockUpdates = false;
	@Property(
			type = PropertyType.TEXT,
			name = "Latest Version",
			description = "Helper for feature. Ignore!",
			category = "Hidden",
			subcategory = "Hidden",
			hidden = true
	)
	public String version = "0";


	@Property(
			type = PropertyType.TEXT,
			name = "My Version",
			description = "Helper for changelog.",
			category = "Hidden",
			subcategory = "Hidden",
			hidden = true
	)
	public String myVersion = "1.0-pre1-neverInit";
	@Property(
			type = PropertyType.SELECTOR,
			name = "Mode for Slayer Tracker",
			description = "Mode to render in for Slayer Tracker.",
			category = "Skills",
			subcategory = "Slayer",
			options = {"Text", "Progress Bar"}
	)
	public int slayerMode = 0;
	@Property(
			type = PropertyType.SELECTOR,
			name = "SRE Command Behavior",
			description = "How to handle the SRE command.\nNote: you can still access the main menu via /sre main.",
			category = "General",
			subcategory = "Meta Settings",
			options = {"Main GUI", "Config"}
	)
	public int openConfig = 0;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove First Mithril Powder",
			description = "You've earned §2614 Mithril Powder §ffrom mining your first Mithril Ore of the day!",
			category = "Spam",
			subcategory = "Dwarven Mines"
	)
	public boolean firstMithrilPowder = false;
	public Config() {
		super(new File("./config/sre/config.toml"), "SRE Config");
		initialize();
		/*
		try {
			super.addDependency("batRange", "warnBatSecrets");
			super.addDependency("skeletonRange", "warnSkeletonMasters");
			super.addDependency("slayerMode", "slayerInfo");
			super.addDependency("readyAlert", "miningSpeedBoost");
			super.addDependency("expiredAlert", "miningSpeedBoost");
			super.addDependency("removeReady", "miningSpeedBoost");
			super.addDependency("removeUsed", "miningSpeedBoost");
			super.addDependency("removeExpired", "miningSpeedBoost");
			super.addDependency("maddoxClickable", "isUsingDSM");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		 */
	}
}
