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
import thecudster.sre.features.impl.skills.slayer.SlayerReminder;
import thecudster.sre.util.gui.GuiManager;
import thecudster.sre.util.sbutil.*;

import java.util.List;

public class FilterHandler {

	public static boolean witherCloak;
	static String lastMaddoxCommand = "/cb placeholder";
	static double lastMaddoxTime = 0;
	@SubscribeEvent(receiveCanceled = true, priority = EventPriority.HIGHEST)
	public void onChat(ClientChatReceivedEvent event) {
		if (event.type == 0x2) {
			return;
		}
		if (!Utils.inSkyblock) { return; }
		String unformatted = Utils.getUnformattedChat(event);
		String message = event.message.getUnformattedText();
		if (SkyblockReinvented.config.removeWatcher) {
			for (String watcherMsg : ArrStorage.watcherQuotes) {
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
			} if (message.contains("No tickets to put in the box...")) {
				event.setCanceled(true);
				return;
			}
		}
		if (SkyblockReinvented.config.bankMsgs) {
			if (message.contains("Deposited") && message.contains("There's now ") && message.contains("coins in the account!"))  {
				event.setCanceled(true);
				return;
			}
			if (message.contains("Withdrew") && message.contains("There's now ") && message.contains("coins left in the account!"))  {
				event.setCanceled(true);
				return;
			}
			if (message.contains("Withdrawing coins...") || message.contains("Depositing coins...")) {
				event.setCanceled(true);
				return;
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
			for (String s : ArrStorage.warningServers) {
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
			for (String s : ArrStorage.maddoxFails) {
				if (message.contains(s)) {
					Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("§cMaddox batphone on cooldown!"));
					event.setCanceled(true);
					return;
				}
			}

			for (String s : ArrStorage.cleanSlayer) {
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
			for (String s : ArrStorage.cleanJacob) {
				if (message.contains(s)) {
					event.setCanceled(true);
					return;
				}
			}
		}
		if (SkyblockReinvented.config.reskinJacob) {
			if (message.contains("[NPC] Jacob: You earned a GOLD medal in the ")) {
				event.setCanceled(true);
				Utils.sendMsg(EnumChatFormatting.GOLD + "You got gold, you actual sweat.");
				return;
			}
			if (message.contains("[NPC] Jacob: You earned a SILVER medal in the ")) {
				event.setCanceled(true);
				Utils.sendMsg(EnumChatFormatting.GRAY + "You got silver! Not bad.");
				return;
			}
			if (message.contains("[NPC] Jacob: You earned a BRONZE medal in the ")) {
				event.setCanceled(true);
				Utils.sendMsg(EnumChatFormatting.YELLOW + "You got bronze! What are you doing m8.");
				return;
			}
			if (message.contains("[NPC] Jacob: You didn't earn a medal, but you may claim a participation reward in the ")) {
				event.setCanceled(true);
				Utils.sendMsg(EnumChatFormatting.RED + "bruh. f in the chat.");
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
			for (String s : ArrStorage.cleanJerry) {
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
			for (String s : ArrStorage.cleanEndDungeon) {
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
			for (String s : ArrStorage.hubWarnings) {
				if (message.contains(s)) {
					event.setCanceled(true);
					return;
				}
			}
		}
		if (SkyblockReinvented.config.dumbSlayerDrops) {
			for (String s : ArrStorage.dumbSlayerDrops) {
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
		if (SkyblockReinvented.config.threeWeirdosIncorrect == 1 || SkyblockReinvented.config.threeWeirdosIncorrect == 2) {
			for (String wrongAnswer : ArrStorage.threeWeirdosIncorrect) {
				if (message.contains(wrongAnswer)) {
					event.setCanceled(true);
					return;
				}
			}
			if (SkyblockReinvented.config.threeWeirdosIncorrect == 2) {
				for (String solution : ArrStorage.threeWeirdosSolutions) {
					if (message.contains(solution)) {
						event.setCanceled(true);
						return;
					}
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
				for (String msg : ArrStorage.witherSkullList) {
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
			for (String s : ArrStorage.doubleMsg) {
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
			for (String s : ArrStorage.profileMsg) {
				if (message.contains(s)) {
					event.setCanceled(true);
					return;
				}
			}
		}
		if (SkyblockReinvented.config.seaCreatures) {
			for (String fishingMsg : ArrStorage.seaCreatureList) {
				if (message.contains(fishingMsg)) {
					event.setCanceled(true);
					return;
				}
			}
		}
		if (SkyblockReinvented.config.visitIsland) {
			if (message.contains("is visiting Your Island!")) {
				event.setCanceled(true);
				return;
			}
		}
		if (SkyblockReinvented.config.skeletonHat) {
			for (String skelMsg : ArrStorage.boneShieldList) {
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
			for (String s : ArrStorage.slowDown) {
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
		if (message.contains("A Golden Goblin has spawned from the earth!")) {
			if (SkyblockReinvented.config.goblinAlert && SkyblockReinvented.config.goldenGoblin) {
				event.setCanceled(true);
				GuiManager.createTitle("Golden Goblin!", 20);
				return;
			} else if (SkyblockReinvented.config.goldenGoblin){
				event.setCanceled(true);
				return;
			} else if (SkyblockReinvented.config.goblinAlert) {
				GuiManager.createTitle("Golden Goblin!", 20);
			}
		}
		if (SkyblockReinvented.config.goldenGoblin) {
			if ((message.contains("You received 400") || message.contains("You received 200")) && message.contains("from killing a ") && message.contains("Golden Goblin")) {
				event.setCanceled(true);
				return;
			}
		}
		if (SkyblockReinvented.config.hideIronman) {
			if (Utils.checkIronman()) {
				if (unformatted.contains(":")) {
					String playerName = unformatted.substring(0, unformatted.indexOf(":"));
					if (playerName.contains("[MVP+]") || playerName.contains("[VIP+]")) {
						playerName = playerName.substring(6);
					} else if (playerName.contains("[VIP]") || playerName.contains("[MVP]")) {
						playerName = playerName.substring(5);
					}
					if (Utils.isInTablist(playerName)) {
						event.setCanceled(true);
						return;
					}
				}
			}
		}
		if (SkyblockReinvented.config.experimentationTable) {
			for (String s : ArrStorage.experimentationTable) {
				if (message.contains(s)) {
					event.setCanceled(true);
					return;
				}
			}
		}
		for (String s : ScoreboardUtil.getSidebarLines()) {

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
			for (String s : ArrStorage.tooFast) {
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
			for (String s : ArrStorage.healerMsg) {
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
			for (String s : ArrStorage.dungeonFinder) {
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
			if (unformatted.contains("You sold ") && unformatted.contains("for ") && unformatted.contains("Coins!") && Utils.inSkyblock) {
				for (String s : ArrStorage.blockedNames) {
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
