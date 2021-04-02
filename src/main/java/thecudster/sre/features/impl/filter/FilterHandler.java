package thecudster.sre.features.impl.filter;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.ItemUtil;
import thecudster.sre.util.Utils;

import java.nio.charset.StandardCharsets;

public class FilterHandler {
	public static final String[] seaCreatureList = {"A squid appeared.", 
			"You caught a Sea Walker.",
			"Pitch Darkness reveals you've caught a Night Squid.",
			"Frozen Steve fell into the pond long ago, never to resurface... until now!",
			"A tiny fin emerges from the water, you've caught a Nurse Shark.",
			"You've stumbled upon a Sea Guardian.",
			"Its a Snowman! It looks harmless.",
			"Phew! It's only a scarecrow.",
			"It looks like you've disrupted the Sea Witch's brewing session. Watch out, she's furious!",
			"You spot a fin as blue as the water it came from, it's a Blue Shark.",
			"You reeled in a Sea Archer.",
			"The Monster of The Deep emerges from the dark depths...",
			"The Grinch stole Jerry's Gifts...get them back!",
			"Huh? A Catfish!",
			"You hear trotting from beneath the waves, you caught a Nightmare",
			"Is this even a Fish? It's the Carrot King!",
			"Gross! A Sea Leech!",
			"You've discovered a Guardian Defender of the sea.",
			"It must be a full moon, it's a Werewolf!",
			"A striped beast bounds from the depths, the wild Tiger Shark!",
			"You have awoken the Deep Sea Protector, prepare for a battle!",
			"The Water Hydra has come to test your Strength.",
			"The Sea Emperor arises from the depths...",
			"The spirit of a long lost Phantom Fisher has come to haunt you.",
			"Hide no longer, a Great White Shark has tracked your scent and thirsts for your blood!",
			"What is this creature!?",
			"This can't be! The manifestation of death himself!"};
	public static final String[] boneShieldList = {"Bone Shield Health: 66%",
			"Bone Shield Health: 33%",
			"Your Bone Shield gained an extra bone!",
			"Your Bone Shield gained an extra bone! It cannot gain anymore bones.",
			"Your Bone Shield was destroyed!"};
	public static boolean witherCloak;
	public static final String[] witherSkullList = {"You need a Wither Key to open this door!",
			"That's a door, and I keep the door...there.",
			"If you didn't find the Wither Key yet, it probably means you need to go back!",
			"Death awaits you on this path, and everywhere else to be fair.",
			"Monsters with a star next to their name have Wither Keys...sometimes."
	};
	public static final String[] maddoxQuotes = {"Hello?",
			"Someone answers!",
			"How does a lobster answer? Shello!",
			"ey what do you need",
			"You hear the line pick up...",
			"You again? What do you want this time?"
	};
	public static final String[] maddoxFails = {
			"Please leave your message after the beep.",
			"How can you tell if a bee is on the phone? You get a buzzy signal!",
			"The phone keeps ringing, is it broken?",
			"The phone picks up but it immediately hangs up!",
			"What did the cat say on the phone? Can you hear meow?",
			"No answer.",
			"Seems like it's not picking up!",
			"\"Your call is important to us, please stay on the line\", so you hang up.",
			"HEY IT'S NOT PICKING UP STOP TRYING!"
	};
	public static final String[] watcherQuotes = {"You have failed to prove yourself, "
		+ "and have paid with your lives. You will make an excellent addition to my collection.",
		"You have proven yourself. You may pass.",
		"I am not your enemy",
		"Stop Attacking me",
		"Don't make me zap you",
		"Ouch, just kidding",
		"We're always watching. Come down from there!",
		"Don't try to sneak anything past my Watchful Eyes. They see you up there!",
		"My Watchful Eyes see you up there! Come down and fight!",
		"A Wandering Soul? Poor Guy.",
		"A Wandering Soul? Don't you guys have a Healer?",
		"Oof.",
		"Oops. Wasn't meant to revive that one.",
		"This one looks like a fighter.",
		"You'll do",
		"Let's see how you can handle this",
		"Go, fight!",
		"Go and live again!",
		"Hmm... This one!",
		"That one was weak anyway.",
		"I'm Impressed.",
		"Not bad.",
		"Very nice.",
		"Aw, I liked that one.",
		"Welcome back to the realm of the living, ",
		"So your name is Sadan, I see...",
		"Ah, you've finally arrived. I have watching you closely since we last met. "
		+ "I don't know if you are ready for what's behind this door. So I will decide if you are strong enough.",
		"Ah, we meet again... I have done some experiments to develop new abilities for my Skulls. Let's see how you handle this!",
		"So you made it this far... interesting. You are much stronger than I was expecting. Not to worry, "
		+ "I recently added a very fine piece to my collection!",
		"You've managed to scratch and claw your way here, eh? Don't even think about trying to outwit me this time! "
		+ "My Watchful Eyes are keeping their ...eyes... on you!",
		"I'm starting to get tired of seeing you around here... This time I've imbued my minions with special properties! These will",
		"Oh... hello? You've arrived too early, I haven't even set up... Anyway, let's fight... I guess",
		"Things feel a little more roomy now, eh? I've knocked down those pillars to go for a more... open concept. "
		+ "Plus I needed to give my new friends some space to roam..."
	};
	public static final String[] threeWeirdosSolutions = {"One of us is telling the truth!",
			"They are both telling the truth. The reward isn't in",
			"We are all telling the truth!",
			"is telling the truth and the reward is",
			"My chest doesn't have the reward. At least one of the others is telling the truth!",
			"One of the others is lying.",
			"They are both telling the truth, the reward is in",
			"They are both lying, the reward is in my chest!",
			"The reward is in my chest.",
			"The reward is not in my chest. They are both lying.",
			"My chest has the reward.",
			"is telling the truth.",
			"My chest has the reward."
	}; // i got these from an old forum post, might be outdated lmk
	public static final String[] dumbSlayerDrops = {
			"Pestilence Rune I",
			"Undead Catalyst",
			"Smite VI",
			"Beheaded Horror",
			"Revenant Catalyst",
			"Snake Rune",
			"Bite Rune I",
			"Spider Catalyst",
			"Bane of Arthropods VI",
			"Fly Swatter",
			"Spirit Rune I",
			"Critical VI",
			"Red Claw Egg",
			"Couture Rune I",
			"Grizzly Bait"
	};
	
	@SubscribeEvent(receiveCanceled = true, priority = EventPriority.HIGHEST)
	public void onChat(ClientChatReceivedEvent event) {
		if (event.type == 0x2) {
			return;
		}

		// if (!Utils.inSkyblock) { return; }
		String message = event.message.getUnformattedText();
		System.out.println(message);
		boolean fromFriend;
		if (message.contains("Guild > ")) { return; }
		if (message.contains("Party >")) { return; }
		/*for (String player : PlayersToRender.playersToRender) {
			if (message.contains(player)) { return; }
		}*/
		if (SkyblockReinvented.config.removeWatcher) {
			for (String watcherMsg : watcherQuotes) {
				if (message.contains(watcherMsg)) {
					event.setCanceled(true);
					return;
				}
			}
		}
		if (SkyblockReinvented.config.warps) {
			if (message.contains("Warping you to the") || message.contains("Sending to server")) {
				event.setCanceled(true);
				return;
			}
		}
		if (SkyblockReinvented.config.dungeonPotionMsg) {
			if (message.contains("Your active Potion Effects have been paused and stored. They will be restored when you leave Dungeons! You are not allowed to use existing Potion Effects while in Dungeons.") ||
			message.contains("You can no longer consume or splash any potions during the remainder of this Dungeon run!")) {
				event.setCanceled(true);
				return;
			}
		}
		if (SkyblockReinvented.config.maddoxMsg) {
			for (String s : maddoxFails) {
				if (message.contains(s)) {
					Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("§cMaddox batphone on cooldown!"));
					event.setCanceled(true);
					return;
				}
			}
			for (String s : maddoxQuotes) {
				if (message.contains(s)) {
					event.setCanceled(true);
					return;
				}
			}
			if (message.contains("Open chat then click anywhere on-screen to open Maddox")) {
				event.setCanceled(true);
				return;
			}
			if (message.contains("SLAYER QUEST STARTED!")) {
				event.setCanceled(true);
				return;
			}
			if (message.contains("Slay") && (message.contains("worth of Zombies") || message.contains("worth of Spiders") || message.contains("worth of Wolves"))) {
				event.setCanceled(true);
				return;
			}
			if (message.contains("Ring...")) {
				event.setCanceled(true);
				return;
			}
			if (message.contains("NICE! SLAYER BOSS SLAIN!")) {
				event.setCanceled(true);
				return;
			}
			if (message.contains("Talk to Maddox to claim your")) {
				event.setCanceled(true);
				return;
			}
			if (message.contains("SLAYER QUEST COMPLETE")) {
				event.setCanceled(true);
				return;
			}
		}
		if (SkyblockReinvented.config.hubWarnings) {
			if (message.contains("Couldn't warp you!")) {
				event.setCanceled(true);
				return;
			}
			if (message.contains("This island has had too many recent visits!")) {
				event.setCanceled(true);
				return;
			}
			if (message.contains("This ability is disabled while guesting!")) {
				event.setCanceled(true);
				return;
			}
			if (message.contains("Finding player...")) {
				event.setCanceled(true);
				return;
			}
			if (message.contains("Sending a visit request...")) {
				event.setCanceled(true);
				return;
			}
			if (message.contains("You can't fast travel while in combat!")) {
				event.setCanceled(true);
				return;
			}
			if (message.contains("Warping...")) {
				event.setCanceled(true);
				return;
			}
			if (message.contains("Request join for")) {
				event.setCanceled(true);
				return;
			}
			if (message.contains("Warping you to")) {
				event.setCanceled(true);
				return;
			}
		}
		if (SkyblockReinvented.config.dumbSlayerDrops) {
			for (String s : dumbSlayerDrops) {
				if (message.contains(s)) {
					event.setCanceled(true);
					return;
				}
			}
		}
		if (SkyblockReinvented.config.minionXP) {
			if (message.contains("You gained") && message.contains("experience from minions!")) {
				event.setCanceled(true);
				return;
			}
		}
		if (SkyblockReinvented.config.watchdogAnnouncement) {
			if (message.contains("WATCHDOG ANNOUNCEMENT") || (message.contains("Watchdog has banned ") && message.contains("players in the last 7 days."))) {
				event.setCanceled(true);
				return;
			}
			if (message.contains("Staff have banned an additional ") && message.contains("in the last 7 days.")) {
				event.setCanceled(true);
				return;
			}
			if (message.contains("Blacklisted modifications are a bannable offense!")) {
				event.setCanceled(true);
				return;
			}
		}
		if (message.equals("")) {
			return;
		}

		if (SkyblockReinvented.config.journalMsg) {
			if (message.contains("Added") && message.contains("to your dungeon journal collection!")) {
				event.setCanceled(true);
				return;
			}
		}
		if (SkyblockReinvented.config.threeWeirdosIncorrect) {
			for (String wrongAnswer : threeWeirdosSolutions) {
				if (message.contains(wrongAnswer)) {
					event.setCanceled(true);
					return;
				}
			}
		}
		if (SkyblockReinvented.config.openMsg) {
			if (message.contains("You hear the sound of something opening")) {
				event.setCanceled(true);
				return;
			}
		}
		if (SkyblockReinvented.config.potionMsg) {
			if (message.contains("BUFF! You have gained") && message.contains("Press TAB to view your active effects!")) {
				event.setCanceled(true);
				return;
			}
		}
		if (SkyblockReinvented.config.witherSkulls) {
				for (String msg : witherSkullList) {
					if (message.contains(msg)) {
						event.setCanceled(true);
						return;
					}
				}
		}
		if (SkyblockReinvented.config.inventoryFull) {
			if (message.contains("Your inventory is full!")) {
				event.setCanceled(true);
				return;
			}
		}
		if (SkyblockReinvented.config.dragonsBreath) {
			if (message.contains("Lost Adventurer used Dragon's Breath on you!")) {
				event.setCanceled(true);
				return;
			}
		}
		if (SkyblockReinvented.config.essenceMessages) {
		if (message.contains("found a Wither Essence! Everyone gains an extra essence!")) {
			event.setCanceled(true);
			return;
		}
		}
		if (SkyblockReinvented.config.openWither) {
			if (message.contains("opened a WITHER door!")) {
				event.setCanceled(true);
				return;
			}
		}
		if (SkyblockReinvented.config.doubleMsg) {
			if (message.contains("stats are doubled because you are the only player using this class!")) {
				event.setCanceled(true);
				return;
			}
			if (message.contains("[Archer]")) {
				event.setCanceled(true);
				return;
			}
			if (message.contains("[Mage]")) {
				event.setCanceled(true);
				return;
			}
			if (message.contains("[Tank]")) { // who uses tank amirite gamers
				event.setCanceled(true);
				return;
			}
			if (message.contains("[Berserker]")) { // is it beserker or beserk? idfk, i'll test it at some point
				event.setCanceled(true);
				return;
			}
			if (message.contains("[Healer]")) {
				event.setCanceled(true);
				return;
			}
		}
		if (SkyblockReinvented.config.cantOpenWither) {
			if (message.contains("You do not have the key for this door!")) {
				event.setCanceled(true);
				return;
			}
		}
		if (SkyblockReinvented.config.reviveStone) {
			if (message.contains("has obtained Revive Stone!")) {
				event.setCanceled(true);
				return;
			}
		}
		if (SkyblockReinvented.config.welcomeMsg) {
			if (message.contains("Welcome to Hypixel")) {
				event.setCanceled(true);
				return;
			}
		}
		if (SkyblockReinvented.config.profileMsg) {
			if (message.contains("You are playing on profile")) {
				event.setCanceled(true);
				return;
			}
			if (message.contains("witching to profile")) {
				event.setCanceled(true);
				return;
			}
			if (message.contains("our profile was changed to: ")) {
				event.setCanceled(true);
				return;
			}
			if (message.contains("is the profile you are playing on!")) {
				event.setCanceled(true);
				return;
			}
			if (message.contains("f you want to delete this profile, switch to another one first!")) {
				event.setCanceled(true);
				return;
			}
		}
		// if (message.contains(":") && SkyblockReinvented.config.hideIronman && Utils.checkIronman()) { event.setCanceled(true); }
		
		// if (event.message.getChatStyle().getColor() == EnumChatFormatting.GRAY) { if (SkyblockReinvented.config.hideNons) { event.setCanceled(true); return; }}
		if (SkyblockReinvented.config.seaCreatures) {
			for (String fishingMsg : seaCreatureList) { if (message.contains(fishingMsg)) { event.setCanceled(true); return; } }
		}
		if (SkyblockReinvented.config.visitIsland) {
			if (message.contains("is visiting Your Island!")) {
				event.setCanceled(true);
				return;
			}
		}
		if (SkyblockReinvented.config.skeletonHat) {
			for (String skelMsg : boneShieldList) {
				if (message.contains(skelMsg)) {
					event.setCanceled(true);
				}
			}
		}
		if (SkyblockReinvented.config.slowDown) {
			if (message.contains("Whow! Slow down there!")) {
				event.setCanceled(true);
				return;
			}
			if (message.contains("Slow down")) {
				event.setCanceled(true);
				return;
			}
		}
		if (SkyblockReinvented.config.guardianChestplate) {
			if (message.contains("Your Guardian Chestplate nullified the damage you just took!")) {
				event.setCanceled(true);
				return;
			}
		}
		if (SkyblockReinvented.config.minionXP) {
			if (message.contains("You received") && message.contains("from minions")) {
				event.setCanceled(true);
				return;
			}
		}
		if (SkyblockReinvented.config.removeSkytils) {
			if (message.contains("§cSkytils has prevented you from breaking that block!")) {
				event.setCanceled(true);
				return;
			}
		}
		if (SkyblockReinvented.config.removeSBA) {
			if (message.contains("A feature has stopped you from breaking this stem")) {
				event.setCanceled(true);
				return;
			}
			if (message.contains("A feature has stopped you from dropping this") && message.contains("item or tool from your hotbar")) {
				event.setCanceled(true);
				return;
			}
		}
		if (SkyblockReinvented.config.reforge) {
			if (message.contains("You reforged") && message.contains("into")) {
				event.setCanceled(true);
				return;
			}
			if (message.contains("Wait a moment before reforging again!")) {
				event.setCanceled(true);
				return;
			}
		}
		if (SkyblockReinvented.config.removeCreeperVeil) {
			if ((message.contains("Creeper Veil De-activated"))) {
				event.setCanceled(true);
				witherCloak = false;
				return;
			}
			if (message.contains("Creeper Veil Activated")) {
				event.setCanceled(true);
				witherCloak = true;
				return;
			}
		}/*
		if (message.contains("You can't use the bazaar when playing on this gamemode!") && SkyblockReinvented.config.bazaarMsg) {
			event.setCanceled(true);
			return;
		}
		*/
		if (SkyblockReinvented.config.removeTooFast) {
			if (message.contains("You are sending commands too fast")) {
				event.setCanceled(true);
				return;
			}
			if (message.contains("You're clicking too fast")) {
				event.setCanceled(true);
				return;
			}
		}
		if (SkyblockReinvented.config.removeMinionWarnings) {
			if (message.contains("inventory does not have enough space to add all items")) {
				event.setCanceled(true);
				return;
			}
		}
		if (SkyblockReinvented.config.jerryMsg) {
			if (message.contains("extra Jerry packed his stuff and left")) {
				event.setCanceled(true);
				return;
			}
		}
		if (SkyblockReinvented.config.gexpMsg) {
			if (message.contains("from playing SkyBlock!") && message.contains("You earned")) {
				event.setCanceled(true);
				return;
			}
		}
		
	}
}
