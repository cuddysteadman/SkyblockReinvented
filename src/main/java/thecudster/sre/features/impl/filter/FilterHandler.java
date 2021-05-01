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
package thecudster.sre.features.impl.filter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.*;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.features.impl.qol.GiftCompassWaypoints;
import thecudster.sre.features.impl.slayer.SlayerReminder;
import thecudster.sre.util.gui.GuiManager;
import thecudster.sre.util.sbutil.DungeonUtils;
import thecudster.sre.util.sbutil.LootTracker;
import thecudster.sre.util.sbutil.ScoreboardUtil;
import thecudster.sre.util.sbutil.Utils;

import java.util.List;

public class FilterHandler {
	public static final String[] danteList = {
			"you need wear Happy Mask!",
			"go Dante in community center get Happy Mask!",
			"you not happy? go get Happy Mask!",
			"oi, where your Happy Mask?",
			"dante best"
	};
	public static final String[] seaCreatureList = {"A squid appeared.", 
			"You caught a Sea Walker.",
			"Pitch Darkness reveals you've caught a Night Squid.",
			"Frozen Steve fell into the pond long ago, never to resurface... until now!",
			"A tiny fin emerges from the water, you've caught a Nurse Shark.",
			"You stumbled upon a Sea Guardian.",
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
			"You again? What do you want this time?",
			"Hey what you do you need?"
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
	public static final String[] fetchurQuotes = {
			"hi i need your help maybe",
			"im looking for some stuff, dont remember the name tbh",
			"you didnt find my things yet?",
			"thanks thats probably what i needed",
			"You received these rewards",
			"20,000 Coins",
			"1,000 Mithril Powder",
			"take some gifts!"
	};
	public static final String[] watcherQuotes = {"You have failed to prove yourself, and have paid with your lives. You will make an excellent addition to my collection.",
		"You have proven yourself. You may pass.",
		"A shiver runs down your spine...",
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
		"Ah, you've finally arrived. I have watching you closely since we last met. ",
		"I don't know if you are ready for what's behind this door. So I will decide if you are strong enough.",
		"Ah, we meet again... I have done some experiments to develop new abilities for my Skulls. Let's see how you handle this!",
		"So you made it this far... interesting. You are much stronger than I was expecting. Not to worry,",
		"I recently added a very fine piece to my collection!",
		"You've managed to scratch and claw your way here, eh? Don't even think about trying to outwit me this time!",
		"My Watchful Eyes are keeping their ...eyes... on you!",
		"I'm starting to get tired of seeing you around here... This time I've imbued my minions with special properties! These will",
		"Oh... hello? You've arrived too early, I haven't even set up... Anyway, let's fight... I guess",
		"Things feel a little more roomy now, eh? I've knocked down those pillars to go for a more... open concept.",
		"Plus I needed to give my new friends some space to roam..."
	};
	public static final String[] cleanSlayer = {
			"Open chat then click anywhere on-screen to open Maddox",
			"SLAYER QUEST STARTED!",
			"Ring...",
			"NICE! SLAYER BOSS SLAIN!",
			"SLAYER QUEST COMPLETE"
	};
	public static final String[] cleanJacob = {
			"[NPC] Jacob: The Farming Contest is over!",
			"[NPC] Jacob: Let me count the final results eh?",
			"[NPC] Jacob: Come see me in the Hub to claim your reward!",
			"[NPC] Jacob: You scored"
	};
	public static final String[] hubWarnings = {
			"Couldn't warp you!",
			"This island has had too many recent visits!",
			"This ability is disabled while guesting!",
			"Finding player...",
			"Sending a visit request...",
			"You can't fast travel while in combat!",
			"Warping...",
			"Request join for",
			"Warping you to"
	};
	public static final String[] cleanJerry = {
			"[NPC] Baker:",
			"You claimed New Year Cake!",
			"You feel your Gift Compass pull towards a new location...",
			"You have already found this Gift this year!",
			"mounted a Snow Cannon!",
			"GIFT! You found a White Gift!",
			"It doesn't seem there are any unopened presents nearby...",
			"§e[NPC] §cSt. Jerry§f: Take this §aGreen Gift§r! You'll hopefully find something nicer than what was in those White Gifts!",
			"§e[NPC] §cSt. Jerry§f: If you haven't already, be sure to give your other Gifts away to others. Giving Gifts benefits both you and the receiver!",
			"You claimed Green Gift!",
			"You found all of the Gifts!",
			"to shoot. Move cursor to aim.",
			"The Snow Cannon is reloading!",
			"You dismounted the Snow Cannon!"
	};
	public static final String[] warningServers = {
			"You are trying to do that too fast. Try again in a moment.",
			"There was a problem joining SkyBlock, try again in a moment!",
			"Oops! Couldn't find a SkyBlock server for you! Try again later!",
			"There was an error queuing into SkyBlock!",
			"Try again in a moment!",
			"Please don't spam the command!",
			"Oops! Couldn't find a SkyBlock server for you! Try again later!"
	};
	public static final String[] threeWeirdosSolutions = {"One of us is telling the truth!",
			"They are both telling the truth. The reward isn't in",
			"We are all telling the truth!",
			"is telling the truth and the reward is",
			"My chest doesn't have the reward. At least one of the others is telling the truth!",
			"One of the others is lying!",
			"They are both telling the truth, the reward is in",
			"They are both lying, the reward is in my chest!",
			"The reward is in my chest.",
			"The reward is not in my chest. They are both lying.",
			"My chest has the reward!",
			"is telling the truth."
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
	public static final String[] cleanEndDungeon = {
			"                          The Catacombs - Floor I",
			"                          The Catacombs - Floor II",
			"                          The Catacombs - Floor III",
			"                          The Catacombs - Floor IV",
			"                          The Catacombs - Floor V",
			"                          The Catacombs - Floor VI",
			"                          The Catacombs - Floor VII",
			"                          Master Mode Catacombs - Floor I",
			"                          Master Mode Catacombs - Floor II",
			"                          Master Mode Catacombs - Floor III",
			"                          Master Mode Catacombs - Floor IV",
			"                          Master Mode Catacombs - Floor V",
			"                          Master Mode Catacombs - Floor VI",
			"                          Master Mode Catacombs - Floor VII",
			"Experience (Team Bonus)",
			"§6> §e§lEXTRA STATS §6<",
			"Defeated Bonzo in ",
			"Defeated Scarf in ",
			"Defeated The Professor in ",
			"Defeated Thorn in ",
			"Defeated Livid in ",
			"Defeated Sadan in ",
			"Defeated Necron in "
	};
	public static final String[] tooFast = {
			"You are sending commands too fast",
			"You're clicking too fast"
	};
	public static final String[] doubleMsg = {
			"stats are doubled because you are the only player using this class!",
			"[Archer]",
			"[Mage]",
			"[Tank]",
			"[Berserker]",
			"[Healer]"
	};
	public static final String[] healerMsg = {
			"You formed a tether with",
			"Your fairy healed yourself for"
	};
	public static final String[] dungeonFinder = {
			"This group is full and has been de-listed!",
			"Attempting to add you to the party...",
			"Please wait a few seconds between refreshing!",
			"Refreshing...",
			"You are already in a party!"
	};
	public static final String[] experimentationTable = {
			"You removed a Experimentation Table. (",
			"You placed a Experimentation Table. ("
	};
	public static final String[] profileMsg = {
			"You are playing on profile",
			"witching to profile",
			"our profile was changed to: ",
			"is the profile you are playing on!",
			"f you want to delete this profile, switch to another one first!"
	};
	public static final String[] slowDown = {
			"Whow! Slow down there!",
			"Slow down"
	};
	static String lastMaddoxCommand = "/cb placeholder";
	static double lastMaddoxTime = 0;
	@SubscribeEvent(receiveCanceled = true, priority = EventPriority.HIGHEST)
	public void onChat(ClientChatReceivedEvent event) {
		if (event.type == 0x2) {
			return;
		}
		if (!Utils.inSkyblock) { return; }
		String unformatted = StringUtils.stripControlCodes(event.message.getUnformattedText());
		System.out.println(unformatted);
		if (!Utils.inSkyblock) { return; }
		String message = event.message.getUnformattedText();
		if (SkyblockReinvented.config.removeWatcher) {
			for (String watcherMsg : watcherQuotes) {
				if (message.contains(watcherMsg)) {
					event.setCanceled(true);
					return;
				}
			}
		}
		if (SkyblockReinvented.config.journalMsg) {
			if (message.contains("You have found page ")) {
				event.setCanceled(true);
				return;
			}
		}
		if (SkyblockReinvented.config.warpCombat) {
			if (message.contains(("You need to be out of combat for 3 seconds before opening the SkyBlock Menu!"))) {
				event.setCanceled(true);
				return;
			}
		}
		if (SkyblockReinvented.config.ghostTracker) {
			if (message.contains("RARE DROP!")) {

				if (message.contains("Bag of Cash")) {
					LootTracker.bagCash++;
					return;
				}
				if (message.contains("Sorrow")) {
					LootTracker.sorrow++;
					LootTracker.ghostsSinceSorrow = 0;
					Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("adsf"));
					return;
				}
				if (message.contains("Plasma")) {
					LootTracker.plasma++;
					return;
				}
				if (message.contains("Volta")) {
					LootTracker.volta++;
					return;
				}
				if (message.contains("Ghostly Boots")) {
					LootTracker.ghostlyBoots++;
					return;
				}
			}
		}
		if (SkyblockReinvented.config.ticketMsgs) {
			if (message.contains("You registered") && message.contains("in the raffle event!")) {
				event.setCanceled(true);
				return;
			}
		}
		if (SkyblockReinvented.config.bankMsgs) {
			if (message.contains("Deposited") && message.contains("There's now ") && message.contains("coins in the account!"))  {
				event.setCanceled(true);
				return;
			}
			if (message.contains("Withdrew") && message.contains("There's now ") && message.contains("coins in the account!"))  {
				event.setCanceled(true);
				return;
			}
			if (message.contains("Withdrawing coins...") || message.contains("Depositing coins...")) {
				event.setCanceled(true);
				return;
			}
		}
		if (SkyblockReinvented.config.showFetchur) {
			for (String s : fetchurQuotes) {
				if (message.contains(s)) {
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
		if (SkyblockReinvented.config.serverFull) {
			for (String s : warningServers) {
				if (message.contains(s)) {
					event.setCanceled(true);
					return;
				}
			}
		}
		if (SkyblockReinvented.config.maddoxClickable) {
			/*
			 * Taken from Danker's Skyblock Mod under GPL 3.0 license.
			 * https://github.com/bowser0000/SkyblockMod/blob/master/LICENSE
			 * @author bowser0000
			 */

			if (unformatted.contains("[OPEN MENU]")) {
				event.setCanceled(true);
				List<IChatComponent> listOfSiblings = event.message.getSiblings();
				for (IChatComponent sibling : listOfSiblings) {
					if (sibling.getUnformattedText().contains("[OPEN MENU]")) {
						lastMaddoxCommand = sibling.getChatStyle().getChatClickEvent().getValue();
						lastMaddoxTime = System.currentTimeMillis() / 1000;
					}
				}
				if (SkyblockReinvented.config.maddoxMsg)
					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Open chat then click anywhere on-screen to open Maddox (this message is by SRE, taken from DSM.)"));
				Utils.sendMsg(EnumChatFormatting.GREEN + "Please download DSM to support them so that you don't have to use this feature anymore!");
				Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GRAY + "https://github.com/bowser0000/SkyblockMod/releases/").setChatStyle(new ChatStyle()
						.setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/bowser0000/SkyblockMod/releases/"))));
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

			for (String s : cleanSlayer) {
				if (message.contains(s)) {
					event.setCanceled(true);
					return;
				}
			}
			if (message.contains("Slay") && (message.contains("worth of Zombies") || message.contains("worth of Spiders") || message.contains("worth of Wolves"))) {
				event.setCanceled(true);
				return;
			}
			if (message.contains("Talk to Maddox to claim your")) {
				if (SkyblockReinvented.config.remindSlayer) {
					if (message.contains("Spider Slayer XP!")) {
						new java.util.Timer().schedule(

								new java.util.TimerTask() {
									@Override
									public void run() {
										try {
											boolean found = false;
											boolean notClaimed = false;
											List<String> scoreboard = ScoreboardUtil.getSidebarLines();
											for (String s : scoreboard) {
												String sCleaned = ScoreboardUtil.cleanSB(s);
												if (sCleaned.contains("Tarantula")) {
													found = true;
												}
												if (sCleaned.contains("Boss slain!")) {
													notClaimed = true;
												}
											}
											if (notClaimed || !found) {
												SlayerReminder.remindTara();
											}
										}
										catch (InterruptedException e) {

										}
									}
								},
								8000
						);
					}
					if (message.contains("Zombie Slayer XP!")) {
						new java.util.Timer().schedule(

								new java.util.TimerTask() {
									@Override
									public void run() {
										try {
											boolean found = false;
											boolean notClaimed = false;
											List<String> scoreboard = ScoreboardUtil.getSidebarLines();
											for (String s : scoreboard) {
												String sCleaned = ScoreboardUtil.cleanSB(s);
												if (sCleaned.contains("Revenant Horror")) {
													found = true;
												}
												if (sCleaned.contains("Boss slain!")) {
													notClaimed = true;
												}
											}
											if (notClaimed || !found) {
												SlayerReminder.remindRevenant();
											}
										}
										catch (InterruptedException e) {

										}
									}
								},
								8000
						);
					}
					if (message.contains("Wolf Slayer XP!")) {
						new java.util.Timer().schedule(

								new java.util.TimerTask() {
									@Override
									public void run() {
										try {
											boolean found = false;
											boolean notClaimed = false;
											List<String> scoreboard = ScoreboardUtil.getSidebarLines();
											for (String s : scoreboard) {
												String sCleaned = ScoreboardUtil.cleanSB(s);
												if (sCleaned.contains("Sven Packmaster")) {
													found = true;
												}
												if (sCleaned.contains("Boss slain!")) {
													notClaimed = true;
												}
											}
											if (notClaimed || !found) {
												SlayerReminder.remindSven();
											}
										}
										catch (InterruptedException e) {

										}
									}
								},
								8000
						);
					}
					event.setCanceled(true);
				}
				return;
			}
		}
		if (SkyblockReinvented.config.uselessJacob) {
			for (String s : cleanJacob) {
				if (message.contains(s)) {
					event.setCanceled(true);
					return;
				}
			}
		}
		if (SkyblockReinvented.config.reskinJacob) {
			if (message.contains("[NPC] Jacob: You earned a GOLD medal in the ")) {
				event.setCanceled(true);
				Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("You got gold, you actual sweat."));
				return;
			}
			if (message.contains("[NPC] Jacob: You earned a SILVER medal in the ")) {
				event.setCanceled(true);
				Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("You got silver! Not bad."));
				return;
			}
			if (message.contains("[NPC] Jacob: You earned a BRONZE medal in the ")) {
				event.setCanceled(true);
				Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("You got bronze! What are you doing m8."));
				return;
			}
			if (message.contains("[NPC] Jacob: You didn't earn a medal, but you may claim a participation reward in the ")) {
				event.setCanceled(true);
				Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("bruh. f in the chat."));
				return;
			}
		}
		if (SkyblockReinvented.config.cryptWitherSkull) {
			if (message.contains("A Crypt Wither Skull exploded, hitting you for ")) {
				event.setCanceled(true);
				return;
			}
		}
		if (SkyblockReinvented.config.giftCompassWaypoints) {
			if (message.contains("§e[NPC] §cSt. Jerry§f: You found all of the Gifts!")) {
				GiftCompassWaypoints.found = true;
				return;
			}
		}
		if (SkyblockReinvented.config.cleanJerry) {
			for (String s : cleanJerry) {
				if (unformatted.contains(s)) {
					event.setCanceled(true);
					return;
				}
			}
		}
		if (SkyblockReinvented.config.useless) {
			if (message.contains("joined SkyBlock.")) {
				event.setCanceled(true);
				return;
			}
			if (message.contains("OpenGL Error: 1281 (Invalid value)")) {
				event.setCanceled(true);
				return;
			}
			if (message.contains("Warped to Election Room!")) {
				event.setCanceled(true);
				return;
			}
			if (message.contains("Warped to Community Center!")) {
				event.setCanceled(true);
				return;
			}
			if (message.contains("You claimed Dungeon Orb!")) {
				event.setCanceled(true);
				return;
			}
			if (message.contains("You already have this class selected!")) {
				event.setCanceled(true);
				return;
			}
			if (message.contains("You can't put this item in there!")) {
				event.setCanceled(true);
				return;
			}
			if (message.contains("You have already opened a Dungeon chest for this run!")) {
				event.setCanceled(true);
				return;
			}
		}
		if (SkyblockReinvented.config.bonePlating) {
			if (message.contains("Your bone plating reduced the damage you took by")) {
				event.setCanceled(true);
				return;
			}
		}
		if (SkyblockReinvented.config.removePuzzler) {
			if (message.contains("§e[NPC] §dPuzzler§f:") && !message.contains("§d?")) {
				event.setCanceled(true);
				return;
			}
		}
		if (SkyblockReinvented.config.cleanEnd) {
			for (String s : cleanEndDungeon) {
				if (message.contains(s)) {
					event.setCanceled(true);
					return;
				}
			}
			if (message.contains("+") && message.contains("Bits")) {
				event.setCanceled(true);
				return;
			}
		}
		if (SkyblockReinvented.config.hubWarnings) {
			for (String s : hubWarnings) {
				if (message.contains(s)) {
					event.setCanceled(true);
					return;
				}
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
		if (message.equals(null)) {
			event.setCanceled(true);
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
			if (message.contains("Lost Adventurer") && message.contains("struck you for ") && message.contains("damage!")) {
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
			for (String s : doubleMsg) {
				if (message.contains(s)) {
					event.setCanceled(true);
					return;
				}
			}
		}
		if (SkyblockReinvented.config.cantOpenWither) {
			if (message.contains("You do not have the key for this door!")) {
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
			for (String s : profileMsg) {
				if (message.contains(s)) {
					event.setCanceled(true);
					return;
				}
			}
		}
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
		if (SkyblockReinvented.config.damageOrbs) {
			if (message.contains("You picked up a") && message.contains(" from ") && message.contains(" healing you for ")) {
				event.setCanceled(true);
				return;
			}
			if (message.contains("picked up your ") && message.contains("Orb!")) {
				event.setCanceled(true);
				return;
			}
		}
		if (SkyblockReinvented.config.watcherTitle) {
			if (StringUtils.stripControlCodes(message).contains("The Watcher: That will be enough for now.")) {
				GuiManager.createTitle("Wathcer Ready!", 20);
			}
		}
		/**
		 * Taken from Skytils under GNU Affero General Public license.
		 * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
		 * @author My-Name-Is-Jeff
		 * @author Sychic
		 */
		if (message.startsWith("Your new API key is ")) {
			String apiKey = event.message.getSiblings().get(0).getChatStyle().getChatClickEvent().getValue();
			SkyblockReinvented.config.apiKey = apiKey;
			SkyblockReinvented.config.markDirty();
			SkyblockReinvented.config.writeData();
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "SkyblockReinvented updated your set Hypixel API key to " + EnumChatFormatting.DARK_GREEN + apiKey));
		}
		if (SkyblockReinvented.config.katMsg) {
			if (message.contains("I'm currently taking care of your ") && message.contains("You can pick it up in")) {
				event.setCanceled(true);
				return;
			}
		}
		if (SkyblockReinvented.config.slowDown) {
			for (String s : slowDown) {
				if (message.contains(s)) {
					event.setCanceled(true);
					return;
				}
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
		if (SkyblockReinvented.config.goldenGoblin) {
			if (message.contains("A Golden Goblin has spawned from the earth!")) {
				event.setCanceled(true);
				return;
			}
			if (message.contains("You received 200") && message.contains("from killing a ") && message.contains("Golden Goblin")) {
				event.setCanceled(true);
				return;
			}
		}
		if (SkyblockReinvented.config.experimentationTable) {
			for (String s : experimentationTable) {
				if (message.contains(s)) {
					event.setCanceled(true);
					return;
				}
			}
		}
		if (SkyblockReinvented.config.compactMsg) {
			if (message.contains("COMPACT! You found a Enchanted")) {
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
			if (message.contains("You applied the ") && message.contains("reforge to ") && message.contains("accessories in your Accessory Bag!")) {
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
		}
		if (message.equals(" ")) {
			event.setCanceled(true);
			return;
		}
		if (SkyblockReinvented.config.removeTooFast) {
			for (String s : tooFast) {
				if (message.contains(s)) {
					event.setCanceled(true);
					return;
				}
			}
		}
		if (SkyblockReinvented.config.removeRNGChat) {
			if (message.contains("Dicer dropped ")) {
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
		if (SkyblockReinvented.config.healerMsg) {
			for (String s : healerMsg) {
				if (message.contains(s)) {
					event.setCanceled(true);
					return;
				}
			}
			if (message.contains("Your tether with ") && message.contains("healed you for")) {
				event.setCanceled(true);
				return;
			}
		}
		if (SkyblockReinvented.config.dungeonFinder) {
			for (String s : dungeonFinder) {
				if (message.contains(s)) {
					event.setCanceled(true);
					return;
				}
			}
			if (message.contains("You need to have a class at level") && message.contains("or higher to join this group!")) {
				event.setCanceled(true);
				return;
			}
		}
		if (SkyblockReinvented.config.sellable) {
			if (unformatted.contains("You sold ") && unformatted.contains("for ") && unformatted.contains("Coins!") && Utils.inSkyblock && Utils.inSkyblock) {
				for (String s : DungeonUtils.sellableNames) {
					if (unformatted.contains(s)) {
						event.setCanceled(true);
						return;
					}
				}
			}
		}
		if (SkyblockReinvented.config.struckYou) {
			if (unformatted.contains("struck you for ") && unformatted.contains("damage!") && Utils.inSkyblock && Utils.inDungeons) {
				event.setCanceled(true);
				return;
			}
		}
	}
	@SubscribeEvent
	public void onMouseInputPost(GuiScreenEvent.MouseInputEvent.Post event) {
		if (!Utils.inSkyblock) return;
		if (Mouse.getEventButton() == 0 && event.gui instanceof GuiChat) {
			if (SkyblockReinvented.config.maddoxClickable && System.currentTimeMillis() / 1000 - lastMaddoxTime < 10) {
				Minecraft.getMinecraft().thePlayer.sendChatMessage(lastMaddoxCommand);
			}
		}
	}
}
