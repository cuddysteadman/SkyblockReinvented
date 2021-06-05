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

package thecudster.sre.settings;

import club.sk1er.vigilance.Vigilant;
import club.sk1er.vigilance.data.Property;
import club.sk1er.vigilance.data.PropertyType;

import java.io.File;
import java.util.ArrayList;


public class Config extends Vigilant {
	@Property(
			type = PropertyType.SWITCH,
			name = "Set Livids Invisible",
			description = "Sets all incorrect invisible to make visibility better in livid fights.",
			category = "Dungeons",
			subcategory = "Misc"
	)
	public boolean hideLivids = false;
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
			description = "Removes messages about potions when entering dungeons.",
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
			description = "Removes messages about how your stats are doubled in dungeons.",
			category = "Spam",
			subcategory = "Dungeons"
	)
	public boolean doubleMsg = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Opening Messages",
			description = "Removes all \"You hear the sound of something opening\" messages.",
			category = "Spam",
			subcategory = "Dungeons"
	)
	public boolean openMsg = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Journal Messages",
			description = "Removes messages about you picking up a journal.",
			category = "Spam",
			subcategory = "Dungeons"
	)
	public boolean journalMsg = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Crypt Wither Skull Messages",
			description = "Removes all \"A crypt wither skull exploded\" messages from chat.",
			category = "Spam",
			subcategory = "Dungeons"
	)
	public boolean cryptWitherSkull = false;

	@Property(
			type = PropertyType.SWITCH,
			name = "Change Damage Milestone Messages",
			description = "Changes Damage Milestone Messages to a HUD element. \n�cUnimplemented!",
			category = "Spam",
			subcategory = "Dungeons",
			hidden = true
	)
	public boolean damageMilestones = false; // TODO damage milestone Spam

	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Lost Adventurer Messages",
			description = "Removes Lost Adventurer messages like \"Lost Adventurer used Dragon's Breath on you!\"",
			category = "Spam",
			subcategory = "Dungeons"
	)
	public boolean dragonsBreath = false;


	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Wither Essence Messages",
			description = "Removes \"x found a Wither Essence! Everyone Gains an extra essence!\" messages.",
			category = "Spam",
			subcategory = "Dungeons"
	)

	public boolean essenceMessages = false;


	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Wither Door Opened Messages",
			description = "Removes \"x opened a WITHER door!\" messages. \n§cRecommended to not use this with don't have key message remover.",
			category = "Spam",
			subcategory = "Dungeons"
	)
	public boolean openWither = false;

	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Can't Open Wither Door Messages", // could be causing errors
			description = "Removes \"x opened a WITHER door!\" messages. \n§cRecommended to not use this with opened wither door message remover.",
			category = "Spam",
			subcategory = "Dungeons"
	)
	public boolean cantOpenWither = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Bone Plating Messages",
			description = "Removes bone plating messages that say \"Your bone plating reduced the damage you took by\".",
			category = "Spam",
			subcategory = "Dungeons"
	)
	public boolean bonePlating = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Clean Ending of Dungeon",
			description = "Gives a clean end to the dungeon (chat).",
			category = "Spam",
			subcategory = "Dungeons"
	)
	public boolean cleanEnd = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Hide Travel To Your Island",
			description = "Hides the armour stand in the hub with this nametag.",
			category = "Hub",
			subcategory = "General"
	)
	public boolean travelIsland = false; // TODO travel to your island
	@Property(
			type = PropertyType.SWITCH,
			name = "Bazaar Messages",
			description = "Removes messages about putting coins in escrow, etc.",
			category = "Spam",
			subcategory = "Hub"
	)
	public boolean bazaarMsg = false; // TODO bazaar Spam
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Orb Messages",
			description = "Removes messages about picking up orbs from players.",
			category = "Spam",
			subcategory = "Dungeons"
	)
	public boolean damageOrbs = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Healer Messages",
			description = "Removes all useless healer messages.",
			category = "Spam",
			subcategory = "Dungeons"
	)
	public boolean healerMsg = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Dungeon Finder Messages",
			description = "Removes useless dungeon finder messages.",
			category = "Spam",
			subcategory = "Dungeons"
	)
	public boolean dungeonFinder;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Watcher Messages",
			description = "Removes all watcher messages except Blood Door open messages.",
			category = "Spam",
			subcategory = "Dungeons"
	)
	public boolean removeWatcher = false;
	@Property(
			type = PropertyType.SELECTOR,
			name = "Remove Incorrect Three Weirdos Messages",
			description = "Removes all incorrect or all Three Weirdos messages. Solvers still work.",
			category = "Spam",
			subcategory = "Dungeons",
			options = {"Off", "Incorrect Answers", "All"}
	)
	public int threeWeirdosIncorrect = 0;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Wither Skulls Messages",
			description = "Removes messages from wither skulls.",
			category = "Spam",
			subcategory = "Dungeons"
	)
	public boolean witherSkulls = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Hide Sellable Names",
			description = "Hides all messages about selling x for x coins in Dungeons.",
			category = "Spam",
			subcategory = "Dungeons"
	)
	public boolean sellable = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Hide Struck Messages",
			description = "Hides all messages about \"X struck you for x damage!\"",
			category = "Spam",
			subcategory = "Dungeons"
	)
	public boolean struckYou = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Raffle Messages",
			description = "Removes all messages about registering tickets in the raffle event.",
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
			description = "Removes puzzler messages.",
			category = "Spam",
			subcategory = "Dwarven Mines"
	)
	public boolean removePuzzler = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Golden Goblin Messages",
			description = "Removes all messages from golden goblins.",
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
			description = "Removes all messages about bank withdrawals.",
			category = "Spam",
			subcategory = "Hub"
	)
	public boolean bankMsgs = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove GEXP Messages",
			description = "Removes messages that say \"You earned x GEXP from playing SkyBlock!\".",
			category = "Spam",
			subcategory = "Hypixel"
	)
	public boolean gexpMsg = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove RNG Titles from Dicer",
			description = "Removes all titles from dicer that say \"Dicer dropped 64x Pumpkin\", for example.",
			category = "Spam",
			subcategory = "Farming"
	)
	public boolean removeRNGChat = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Minion Inventory Warnings",
			description = "Removes chat messages from minions and inventory warnings.",
			category = "Spam",
			subcategory = "Your Island"
	)
	public boolean removeMinionWarnings = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Chat from Jerries",
			description = "Removes chat messages like \"Your 1 extra Jerry packed his stuff and left!\".",
			category = "Spam",
			subcategory = "Your Island"
	)
	public boolean jerryMsg = false;
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Too Fast Messages",
		description = "Removes chat messages such as \"You're clicking too fast!\".",
		category = "Spam",
		subcategory = "General"
	)
	public boolean removeTooFast;
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Creeper Veil Messages",
		description = "Removes Creeper Veil activation/deactivation messages.",
		category = "Spam",
		subcategory = "Items"
	)
	public boolean removeCreeperVeil;
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Skeleton Hat Messages",
		description = "Removes all messages from the Skeleton Hat.",
		category = "Spam",
		subcategory = "Items"
	)
	public boolean skeletonHat = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Welcome Messages",
			description = "Removes all messages that say \"Welcome to Hypixel Skyblock!\".",
			category = "Spam",
			subcategory = "General"
	)
	public boolean welcomeMsg = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Profile Messages",
			description = "Removes all messages that say \"You are playing on profile\" or changing profile messages.",
			category = "Spam",
			subcategory = "General"
	)
	public boolean profileMsg = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Warp Messages",
			description = "Removes all messages that say \"Warping you to the hub island\" etc.",
			category = "Spam",
			subcategory = "General"
	)
	public boolean warps;




	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Headless Horseman Spawn Messages",
			description = "Removes all messages about players killing the headless horseman boss.",
			category = "Spam",
			subcategory = "Your Island"
	)
	public boolean headlessHorseman = false; // TODO headless horseman alert








	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Inventory Full Messages",
			description = "Removes messages that say \"Your inventory is full!\" Should only be used with SBA.",
			category = "Spam",
			subcategory = "General"
	)
	public boolean inventoryFull = false;

	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Guardian Chestplate Messages",
			description = "Removes all messages from the Guardian Chestplate.",
			category = "Spam",
			subcategory = "Items"
	)
	public boolean guardianChestplate = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Potion Buff Messages",
			description = "Removes all buff messages from potions.",
			category = "Spam",
			subcategory = "Items"
	)
	public boolean potionMsg = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Watchdog Announcements",
			description = "Removes announcements about how many players have been banned.",
			category = "Spam",
			subcategory = "Hypixel"
	)
	public boolean watchdogAnnouncement = false;
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Sea Creature Messages",
		description = "Removes all messages from sea creatures.",
		category = "Spam",
		subcategory = "Fishing"
	)
	public boolean seaCreatures = false;
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
		description = "Removes all messages that say, \"Whow! Slow down there.\" or \"Slow down!\".",
		category = "Spam",
		subcategory = "General"
	)
	public boolean slowDown = false;
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Island Visit Messages",
		description = "Removes all messages that say, \"X is visiting your island!\".",
		category = "Spam",
		subcategory = "Your Island"
	)
	public boolean visitIsland = false;


	@Property(
			type = PropertyType.SWITCH,
			name = "Hide Hub Warp Warnings/Messages",
			description = "Hides all annoying warp warnings/messages.",
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
			name = "Hide Useless Jacob's Messages",
			description = "Hides all useless Jacob's Farming Event messages.",
			category = "Spam",
			subcategory = "Your Island"
	)
	public boolean uselessJacob = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Clean Jerry's Workshop",
			description = "Cleans many useless Jerry's Workshop messages.",
			category = "Spam",
			subcategory = "General"
	)
	public boolean cleanJerry = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Combat Warp Messages",
			description = "Removes all messages about how you can't be in combat.",
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
			description = "Hides the mithril powder display on your scoreboard. Mainly a fix for Apec. \n§cShould only be used with mods such as NEU that display your Mithril Powder!",
			category = "Dwarven Mines",
			subcategory = "General"
	)
	public boolean hideMithrilPowder = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Hide Ironman in Scoreboard",
			description = "Hides the mithril powder display on your scoreboard. Mainly a fix for Apec.",
			category = "General",
			subcategory = "General"
	)
	public boolean removeIronmanScoreboard = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Hide Dumb Slayer Drops",
			description = "Hides useless/worthless slayer drops.",
			category = "Spam",
			subcategory = "Slayer"
	)
	public boolean dumbSlayerDrops = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Minion XP",
			description = "Removes chat from Minion XP (coop members collecting minions).",
			category="Spam",
			subcategory = "Your Island"
	)
	public boolean minionXP = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Maddox Messages",
			description = "Hides all annoying Maddox messages when you use Maddox batphone.",
			category = "Spam",
			subcategory = "Slayer"
	)
	public boolean maddoxMsg = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Reforge Chat",
			description = "Removes \"You reforged x into x\" from chat.",
			category = "Spam",
			subcategory = "General"
	)
	public boolean reforge = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Generally Useless Messages",
			description = "Removes generally useless messages. If your favorite chat messages are missing, just turn this off and see if that fixes it.",
			category = "Spam",
			subcategory = "General"
	)
	public boolean useless = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Compact Messages",
			description = "Removes all messages from compact.",
			category = "Spam",
			subcategory = "Mining"
	)
	public boolean compactMsg = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Experimentation Table Messages",
			description = "Removes all messages from placing/removing the experimentation table.",
			category = "Spam",
			subcategory = "Enchanting"
	)
	public boolean experimentationTable = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Warnings about Hubs",
			description = "Removes all warnings about joining Skyblock and others.",
			category = "Spam",
			subcategory = "Hypixel"
	)
	public boolean serverFull = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Messages from Kat",
			description = "Removes all messages from Kat.",
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
		name = "Auto Join SB",
		description = "Automatically join Skyblock when joining Hypixel.",
		category = "QOL",
		subcategory = "Skyblock QOL"
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
			name = "Stop Rendering Players",
			description = "Stop rendering all players.",
			category = "General",
			subcategory = "Rendering"
	)
	public boolean renderPlayers = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Stop Rendering XP While in Dwarven Mines",
			description = "Stops all XP orbs from rendering while in Dwarven Mines areas.",
			category = "Dwarven Mines",
			subcategory = "General"
	)
	public boolean stopRenderXP = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Stop Rendering Player Armour",
			description = "Stops rendering all armour on other players except in dungeons.",
			category = "General",
			subcategory = "Rendering"
	)
	public boolean renderPlayerArmor = false;
	@Property(
			type = PropertyType.SELECTOR,
			name = "Skill XP Tracker",
			description = "Tracks all of your skill XP to level 50 or level 60.",
			category = "General",
			subcategory = "Rendering",
			options = {"Off", "Level 50", "Level 60"}
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
			description = "Removes all item frame names.",
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
			name = "Show Your Current Pet",
			description = "Overlays your current pet as green in the Pet Menu.",
			category = "General",
			subcategory = "Rendering"
	)
	public boolean currentPet = false;
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
			name = "Better Creeper Solver",
			description = "Edit to Danker's Skyblock Mod creeper solver to make it more intuitive and to only show one combo at a time.",
			category = "Dungeons",
			subcategory = "QOL"
	)
	public boolean betterCreeper = false;
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
			subcategory = "Rendering",
			hidden = true
	)
	public boolean dangerGhosts = false;

	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Titles from Raffles",
			description = "Removes the annoying \"WON 3X EVENT REWARDS!\" titles.",
			category = "Dwarven Mines",
			subcategory = "Rendering",
			hidden = true
	)
	public boolean removeRaffleTitles = false;
	public ArrayList<String> listToRender = new ArrayList<String>();
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Power Orbs from Rendering",
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
			type = PropertyType.SELECTOR,
			name = "Mode for Slayer Tracker",
			description = "Mode to render in for Slayer Tracker.",
			category = "Skills",
			subcategory = "Slayer",
			options = {"Text", "Progress Bar"}
	)
	public int slayerMode = 0;
	public Config() {
        super(new File("./config/sre/config.toml"));
        initialize();
    }
	
}
