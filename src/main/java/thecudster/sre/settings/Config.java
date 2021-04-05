package thecudster.sre.settings;

import java.io.File;
import java.util.ArrayList;

import club.sk1er.vigilance.Vigilant;
import club.sk1er.vigilance.data.*;
import org.checkerframework.checker.units.qual.A;
import scala.sys.Prop;


public class Config extends Vigilant {
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Wither Cloak Creepers",
		description = "Removes all Creeper Veil Creepers except your own.",
		category = "Items",
		subcategory = "Rendering"
	)
	public boolean renderCreepers = false;
	@Property(
		type = PropertyType.TEXT,
		name = "List of Players to Render",
		description = "List players that you want to render while not rendering others here separated by commas.",
		category = "General",
		hidden=true,
		subcategory = "Rendering"
	)
	public String listToRender= "";
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Power Orbs from Rendering",
		description = "Stops power orb nametags from rendering to improve visibility and FPS.",
		category = "Rendering",
		subcategory = "FPS Improvement",
			hidden = true
	)

	public boolean renderPowerOrb = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Overlay Mobs in Range of Hyperion",
			description = "Overlays mobs that are in range of your hyperion with a green box around them.",
			category = "General",
			subcategory = "Rendering"
	)
	public boolean hyperionOverlay;

	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Chat from Skytils",
		description = "Removes chat messages like \"Skytils has prevented you from\", etc.",
		category = "Chat",
		hidden = true,
		subcategory = "Warnings"
	)
	public boolean removeSkytils = false;
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Minion Inventory Warnings",
		description = "Removes chat messages from minions and inventory warnings.",
		category = "Your Island",
		subcategory = "Chat"
	)
	public boolean removeMinionWarnings = false;
	
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
		name = "Remove Chat from Jerries",
		description = "Removes chat messages like \"Your 1 extra Jerry packed his stuff and left!\".",
		category = "Your Island",
		subcategory = "Chat"
	)
	public boolean jerryMsg = false;
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove GEXP Messages",
		description = "Removes messages that say \"You earned x GEXP from playing SkyBlock!\".",
		category = "QOL",
		subcategory = "Hypixel QOL"
	)
	public boolean gexpMsg = false;
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Chat from SBA",
		description = "Removes chat messages like \"A feature has prevented you from\", etc.",
		category = "Chat",
		hidden = true,
		subcategory = "Warnings"
	)
	public boolean removeSBA = false;
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Too Fast Messages",
		description = "Removes chat messages such as \"You're clicking too fast!\".",
		category = "General",
		subcategory = "Chat"
	)
	public boolean removeTooFast;
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Creeper Veil Messages",
		description = "Removes Creeper Veil activation/deactivation messages.",
		category = "Items",
		subcategory = "Chat"
	)
	public boolean removeCreeperVeil;
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Skeleton Hat Messages",
		description = "Removes all messages from the Skeleton Hat.",
		category = "Items",
		subcategory = "Chat"
	)
	public boolean skeletonHat = false;	
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
		name = "Remove Welcome Messages",
		description = "Removes all messages that say \"Welcome to Hypixel Skyblock!\".",
		category = "General",
		subcategory = "Chat"
	)
	public boolean welcomeMsg;
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Profile Messages",
		description = "Removes all messages that say \"You are playing on profile\" or changing profile messages.",
		category = "General",
		subcategory = "Chat"
	)
	public boolean profileMsg;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Warp Messages",
			description = "Removes all messages that say \"Warping you to the hub island\" etc.",
			category = "General",
			subcategory = "Chat"
	)
	public boolean warps;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Inventory Full Messages",
			description = "Removes messages that say \"Your inventory is full!\" Should only be used with SBA.",
			category = "General",
			subcategory = "Chat"
	)
	public boolean inventoryFull = false;

	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Guardian Chestplate Messages",
		description = "Removes all messages from the Guardian Chestplate.",
		category = "Items",
		subcategory = "Chat"
	)
	public boolean guardianChestplate = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Potion Buff Messages",
			description = "Removes all buff messages from potions.",
			category = "General",
			subcategory = "Chat"
	)
	public boolean potionMsg = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Wither Skulls Messages",
			description = "Removes messages from wither skulls.",
			category = "Dungeons",
			subcategory = "Chat"
	)
	public boolean witherSkulls = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Dungeon Potion Messages",
			description = "Removes messages about potions when entering dungeons.",
			category = "Dungeons",
			subcategory = "Chat"
	)
	public boolean dungeonPotionMsg = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Watchdog Announcements",
			description = "Removes announcements about how many players have been banned.",
			category = "QOL",
			subcategory = "Hypixel QOL"
	)
	public boolean watchdogAnnouncement = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Doubled Messages",
			description = "Removes messages about how your stats are doubled in dungeons.",
			category = "Dungeons",
			subcategory = "Chat"
	)
	public boolean doubleMsg = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Journal Messages",
			description = "Removes messages about you picking up a journal.",
			category = "Dungeons",
			subcategory = "Chat"
	)
	public boolean journalMsg = false;

	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Opening Messages",
			description = "Removes all \"You hear the sound of something opening\" messages.",
			category = "Dungeons",
			subcategory = "Chat"
	)
	public boolean openMsg = false;
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Sea Creature Messages",
		description = "Removes all messages from sea creatures.",
		category = "Skills",
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
		category = "General",
		subcategory = "Chat"
	)
	public boolean slowDown = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Unimportant Fetchur Messages",
			description = "Removes all unimportant fetchur messages.",
			category = "Dwarven Mines",
			subcategory = "Chat"
	)
	public boolean showFetchur = false;
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Island Visit Messages",
		description = "Removes all messages that say, \"X is visiting your island!\".",
		category = "Your Island",
		subcategory = "Chat"
	)
	public boolean visitIsland = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Render Waypoints to Teammates",
			description = "Render a waypoint at teammates while in a dungeon.",
			category = "Dungeons",
			subcategory = "Rendering"
	)
	public boolean renderWaypointDungeons = false;

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
		name = "Remove Creeper Sounds from Veil",
		description = "Removes Creeper Veil constant creeper/skeleton noises. \n§cThis feature removes all creeper & skeleton hurt noises while creeper veil is activated.",
		category="Items",
		subcategory="Sounds"
	)
	public boolean creeperSounds = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Show Gift Compass Waypoints",
			description = "Shows waypoints for where to go when Gift Compass is held in hand.",
			category = "Jerry Island",
			subcategory = "Rendering"
	)
	public boolean giftCompassWaypoints = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Hide Hub Warp Warnings/Messages",
			description = "Hides all annoying warp warnings/messages.",
			category = "Hub",
			subcategory = "Chat"
	)
	public boolean hubWarnings = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Redo Jacob Messages",
			description = "Fun little feature that has custom responses for what you got in a Jacob's farming contest. A bit snarky (:<",
			category = "Your Island",
			subcategory = "Chat"
	)
	public boolean reskinJacob = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Crypt Wither Skull Messages",
			description = "Removes all \"A crypt wither skull exploded\" messages from chat.",
			category = "Dungeons",
			subcategory = "Chat"
	)
	public boolean cryptWitherSkull = false;
	// public String APIKey = "don't steal my api key <3";
	@Property(
			type = PropertyType.SWITCH,
			name = "Hide Useless Jacob's Messages",
			description = "Hides all useless Jacob's Farming Event messages.",
			category = "Your Island",
			subcategory = "Chat"
	)
	public boolean uselessJacob = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Clean Jerry's Workshop",
			description = "Cleans many useless Jerry's Workshop messages.",
			category = "Jerry Island",
			subcategory = "Chat"
	)
	public boolean cleanJerry = false;
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
			name = "Remove JerryChine Gun Sounds",
			description = "Removes your jerrychine gun sounds.",
			category = "Items",
			subcategory = "Sounds"
	)
	public boolean jerryChine;
	@Property(
			type = PropertyType.SWITCH,
			name = "Hide Dumb Slayer Drops",
			description = "Hides useless/worthless slayer drops.",
			category = "Skills",
			subcategory = "Slayer"
	)
	public boolean dumbSlayerDrops = false;
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
			name = "Remove Maddox Messages",
			description = "Hides all annoying Maddox messages when you use Maddox batphone.",
			category = "Skills",
			subcategory = "Slayer"
	)
	public boolean maddoxMsg = false;
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove all Chat if Ironman",
		description = "Removes all chat if you are on an ironman profile. (Not working)",
		category = "General",
		subcategory = "Chat",
		hidden=true
	)
	public boolean hideIronman = false;
	@Property(
		type = PropertyType.SWITCH,
		name = "Auto Join SB",
		description = "Automatically join Skyblock when joining Hypixel.",
		category = "QOL",
		subcategory = "Skyblock QOL",
			hidden = true
	)
	public boolean joinSB = false;
	
	
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Minion XP",
		description = "Removes chat from Minion XP (coop members collecting minions).",
		category="Your Island",
		subcategory = "Chat"
		)
	public boolean minionXP = false;
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Nons",
		description = "Removes all chat from Nons. (Meme, nons are cool <3)",
		category="Chat",
		hidden=true,
		subcategory = "Other Players"
	)
	public boolean hideNons = false; 
	
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Incorrect Three Weirdos Messages",
		description = "Removes all incorrect Three Weirdos messages. Solvers should still work. \n§cUntested!",
		category = "Dungeons",
		subcategory = "Chat"
	)
	
	public boolean threeWeirdosIncorrect = false;
	
	
	
	
	@Property(
		type = PropertyType.SWITCH,
		name = "Change Damage Milestone Messages",
		description = "Changes Damage Milestone Messages to a HUD element. \n�cUnimplemented!",
		category = "Dungeons",
		subcategory = "Chat",
			hidden = true
	)
	public boolean damageMilestones = false;
	
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Lost Adventurer Messages",
		description = "Removes Lost Adventurer messages like \"Lost Adventurer used Dragon's Breath on you!\"",
		category = "Dungeons",
		subcategory = "Chat"
	)
	public boolean dragonsBreath = false;
	
	
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Wither Essence Messages",
		description = "Removes \"x found a Wither Essence! Everyone Gains an extra essence!\" messages.",
		category = "Dungeons",
		subcategory = "Chat"
	)

	public boolean essenceMessages = false;
	
	
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Wither Door Opened Messages",
		description = "Removes \"x opened a WITHER door!\" messages. \n§cRecommended to not use this with don't have key message remover.",
		category = "Dungeons",
		subcategory = "Chat"
	)
	public boolean openWither = false;
	
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Can't Open Wither Door Messages", // could be causing errors
		description = "Removes \"x opened a WITHER door!\" messages. \n§cRecommended to not use this with opened wither door message remover.",
		category = "Dungeons",
		subcategory = "Chat"
	)
	public boolean cantOpenWither = false;
	
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Revive Stone Messages", 
		description = "Removes revive stone that you pick up messages.",
		category = "Dungeons",
		subcategory = "Chat"
	)
	public boolean reviveStone = false;
	
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Watcher Messages", 
		description = "Removes all watcher messages except Blood Door open messages. \n§cCould cause errors with other timers in mods, hasn't been tested!",
		category = "Dungeons",
		subcategory = "Chat"
	)
	public boolean removeWatcher = false;
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Reforge Chat",
		description = "Removes \"You reforged x into x\" from chat.",
		category = "Hub",
		subcategory = "Chat"
	)
	public boolean reforge = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Generally Useless Messages",
			description = "Removes generally useless messages. If your favorite chat messages are missing, just turn this off and see if that fixes it.",
			category = "General",
			subcategory = "Chat"
	)
	public boolean useless;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Puzzler Messages",
			description = "Removes puzzler messages.",
			category = "Dwarven Mines",
			subcategory = "Chat"
	)
	public boolean removePuzzler;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Bone Plating Messages",
			description = "Removes bone plating messages that say \"Your bone plating reduced the damage you took by\".",
			category = "Dungeons",
			subcategory = "Chat"
	)
	public boolean bonePlating = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Clean Ending of Dungeon",
			description = "Gives a clean end to the dungeon (chat). \n§cMay break mods that shows summary of score!",
			category = "Dungeons",
			subcategory = "Chat"
	)
	public boolean cleanEnd = false;
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
			name = "Secret Waypoints",
			description = "Creates a waypoint to secrets like drops, bats, and chests. §cUSE AT YOUR OWN RISK!",
			category = "Dungeons",
			subcategory = "Secrets"
	)
	public boolean secretFinder = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Raffle Waypoint",
			description = "Renders a waypoint where you have to deposit your tickets in the raffle event. §cUSE AT YOUR OWN RISK!",
			category = "Dwarven Mines",
			subcategory = "Rendering"
	)
	public boolean raffleWaypoint = false;
	public ArrayList<String> toSearch = new ArrayList<String>();
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Healer Messages",
			description = "Removes all useless healer messages.",
			category = "Dungeons",
			subcategory = "Chat"
	)
	public boolean healerMsg = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Dungeon Finder Messages",
			description = "Removes useless dungeon finder messages.",
			category = "Dungeons",
			subcategory = "Chat"
	)
	public boolean dungeonFinder;
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Bazaar Chat",
		description = "Removes chat when you click Bazaar on an ironman profile. (Not working)",
		hidden = true,
		category = "Chat",
		subcategory = "Warnings"
	)
	public boolean bazaarMsg = false;
	public Config() {
        super(new File("./config/sre/config.toml"));
        initialize();
    }
	
}
