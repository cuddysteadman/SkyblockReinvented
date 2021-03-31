package thecudster.sre.settings;

import java.io.File;

import club.sk1er.vigilance.Vigilant;
import club.sk1er.vigilance.data.*;


public class Config extends Vigilant {
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Wither Cloak Creepers",
		description = "Removes all Creeper Veil Creepers except your own.",
		category = "Rendering",
		subcategory = "Other Players"
	)
	public boolean renderCreepers = false;
	@Property(
		type = PropertyType.TEXT,
		name = "List of Players to Render",
		description = "List players that you want to render while not rendering others here separated by commas.",
		category = "Rendering",
		hidden=true,
		subcategory = "FPS Improvement"
	)
	public String listToRender= "";
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Power Orbs from Rendering",
		description = "Stops power orb nametags from rendering to improve visibility and FPS.",
		category = "Rendering",
		subcategory = "FPS Improvement"
	)

	public boolean renderPowerOrb = false;
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Chat from Skytils",
		description = "Removes chat messages like \"Skytils has prevented you from\", etc.",
		category = "Chat",
		subcategory = "Warnings"
	)
	public boolean removeSkytils = false;
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Minion Inventory Warnings",
		description = "Removes chat messages from minions and inventory warnings.",
		category = "Chat",
		subcategory = "Warnings"
	)
	public boolean removeMinionWarnings = false;
	
	@Property(
		type = PropertyType.SLIDER,
        name = "Dungeon Floor Lock",
        description = "Only allows you to enter a certain floor of dungeons.",
        category = "QOL",
        subcategory = "Skyblock QOL",
        hidden = true,
        max = 7
	)
	public int floorLock = 0;
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Chat from Jerries",
		description = "Removes chat messages like \"Your 1 extra Jerry packed his stuff and left!\".",
		category = "Chat",
		subcategory = "Warnings"
	)
	public boolean jerryMsg = false;
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove GEXP Messages",
		description = "Removes messages that say \"You earned x GEXP from playing SkyBlock!\".",
		category = "Chat",
		subcategory = "Items"
	)
	public boolean gexpMsg = false;
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Chat from SBA",
		description = "Removes chat messages like \"A feature has prevented you from\", etc.",
		category = "Chat",
		subcategory = "Warnings"
	)
	public boolean removeSBA = false;
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Too Fast Messages",
		description = "Removes chat messages such as \"You're clicking too fast!\".",
		category = "Chat",
		subcategory = "Warnings"
	)
	public boolean removeTooFast;
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Creeper Veil Messages",
		description = "Removes Creeper Veil activation/deactivation messages.",
		category = "Chat",
		subcategory = "Collections"
	)
	public boolean removeCreeperVeil;
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Skeleton Hat Messages",
		description = "Removes all messages from the Skeleton Hat.",
		category = "Chat",
		subcategory = "Items"
	)
	public boolean skeletonHat = false;	
	@Property(
		type = PropertyType.SWITCH,
		name = "Stop Rendering Players",
		description = "Stop rendering all players.",
		category = "Rendering",
		subcategory = "Other Players"
	)
	public boolean renderPlayers = false;
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Welcome Messages",
		description = "Removes all messages that say \"Welcome to Hypixel Skyblock!\".",
		category = "Chat",
		subcategory = "Warnings"
	)
	public boolean welcomeMsg;
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Profile Messages",
		description = "Removes all messages that say \"You are playing on profile\" or changing profile messages.",
		category = "Chat",
		subcategory = "Warnings"
	)
	public boolean profileMsg;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Warp Messages",
			description = "Removes all messages that say \"Warping you to the hub island\" etc.",
			category = "Chat",
			subcategory = "Warnings"
	)
	public boolean warps;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Inventory Full Messages",
			description = "Removes messages that say \"Your inventory is full!\" Should only be used with SBA.",
			category = "Chat",
			subcategory = "Warnings"
	)
	public boolean inventoryFull = false;

	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Guardian Chestplate Messages",
		description = "Removes all messages from the Guardian Chestplate.",
		category = "Chat",
		subcategory = "Items"
	)
	public boolean guardianChestplate = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Remove Potion Buff Messages",
			description = "Removes all buff messages from potions.",
			category = "Chat",
			subcategory = "Items"
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
			category = "Chat",
			subcategory = "Warnings"
	)
	public boolean openMsg = false;
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Sea Creature Messages",
		description = "Removes all messages from sea creatures.",
		category = "Chat",
		subcategory = "Collections"
	)
	public boolean seaCreatures = false;
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Slow Down Messages",
		description = "Removes all messages that say, \"Whow! Slow down there.\" or \"Slow down!\".",
		category = "Chat",
		subcategory = "Warnings"
	)
	public boolean slowDown = false;
	
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Island Visit Messages",
		description = "Removes all messages that say, \"X is visiting your island!\".",
		category = "Chat",
		subcategory = "Warnings"
	)
	public boolean visitIsland = false;
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove Creeper Sounds from Veil",
		description = "Removes Creeper Veil constant creeper/skeleton noises. \n§cThis feature removes all creeper & skeleton hurt noises while creeper veil is activated.",
		category="Sounds",
		subcategory="Items"
	)
	public boolean creeperSounds = false;
	@Property(
		type = PropertyType.SWITCH,
		name = "Remove all Chat if Ironman",
		description = "Removes all chat if you are on an ironman profile. (Not working)",
		category = "Chat",
		subcategory = "Other Players",
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
		category="Chat",
		subcategory = "Collections"
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
		category = "Chat",
		subcategory = "Warnings"
	)
	public boolean reforge = false;
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
