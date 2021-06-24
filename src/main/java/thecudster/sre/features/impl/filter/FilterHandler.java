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
import net.minecraft.command.ICommandSender;
import net.minecraft.event.ClickEvent;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.util.*;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.core.gui.RenderUtils;
import thecudster.sre.events.PacketEvent;
import thecudster.sre.events.SecondPassedEvent;
import thecudster.sre.features.impl.qol.GhostLoot;
import thecudster.sre.features.impl.qol.GiftCompassWaypoints;
import thecudster.sre.util.Utils;
import thecudster.sre.core.gui.GuiManager;
import thecudster.sre.util.sbutil.ArrStorage;
import thecudster.sre.util.sbutil.CurrentLoc;
import thecudster.sre.util.sbutil.SimpleCommand;

import java.util.ArrayList;

import static sun.security.x509.ReasonFlags.UNUSED;
import static thecudster.sre.util.sbutil.ArrStorage.SpamArrays.*;
import static thecudster.sre.SkyblockReinvented.config;

public class FilterHandler {
	public static boolean witherCloak;
	public ArrayList<Filter> filters = new ArrayList<>();
	private ArrayList<FilterConcat> filterConcats = new ArrayList<>();
	private boolean hasInit = false;
	private boolean needsToClick = true;
	public static int secondsPassed = 0;

	public ArrayList<Filter> getFilters() {
		return this.filters;
	}


	public ArrayList<FilterConcat> getFilterConcats() {
		return this.filterConcats;
	}

	@SubscribeEvent(receiveCanceled = true, priority = EventPriority.LOWEST)
	public void onChat(ClientChatReceivedEvent event) {
		if (!hasInit) { init(); hasInit = true; }
		String unformatted = StringUtils.stripControlCodes(event.message.getUnformattedText());
		String message = event.message.getUnformattedText();
		if (unformatted.contains("Welcome to Hypixel") && config.welcomeMsg)  { event.setCanceled(true); return; }
		if (Utils.containsAnyOf(unformatted, profileMsg)) {
			event.setCanceled(true);
			return;
		}
		if (!Utils.inSkyblock) { return; }
		if (config.ghostTracker) {
			if (message.contains("RARE DROP!")) {
				if (message.contains("Bag of Cash")) {
					GhostLoot.bagCash++;
					return;
				}
				if (message.contains("Sorrow")) {
					GhostLoot.sorrow++;
					GhostLoot.ghostsSinceSorrow = 0;
					return;
				}
				if (message.contains("Plasma")) {
					GhostLoot.plasma++;
					return;
				}
				if (message.contains("Volta")) {
					GhostLoot.volta++;
					return;
				}
				if (message.contains("Ghostly Boots")) {
					GhostLoot.ghostlyBoots++;
					return;
				}
			}
		}
		for (Filter filter : filters) {
			if (filter.shouldCancel(event)) {
				event.setCanceled(true);
				return;
			}
		}
		for (FilterConcat filterConcat : filterConcats) {
			if (filterConcat.shouldCancel(event)) {
				event.setCanceled(true);
				return;
			}
		}
		if (config.maddoxMsg) {
			if (Utils.containsAnyOf(unformatted, maddoxFails)) {
				event.setCanceled(true);
				Utils.sendMsg(EnumChatFormatting.RED + "Maddox batphone on cooldown!");
				return;
			}
		}
		if (config.autoGarry) {
			if (unformatted.contains("Click here to teleport to Garry")) {
				needsToClick = true;
				secondsPassed = 1;
				ChatComponentText garryText = new ChatComponentText(EnumChatFormatting.GREEN + "Looks like an event is starting soon! SRE will automatically send you to garry in 15 seconds (assuming you click at least once). Click on this message to stop this automatic action from happening.");
				garryText.setChatStyle(garryText.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/garrytoggle")));
				Minecraft.getMinecraft().thePlayer.addChatComponentMessage(garryText);
			}
		}
		if (config.reskinJacob) {
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
				Utils.sendMsg(EnumChatFormatting.RED + "f in the chat.");
				return;
			}
		}
		if (config.giftCompassWaypoints) {
			if (unformatted.contains("[NPC] St. Jerry: You found all of the Gifts!")) {
				GiftCompassWaypoints.found = true;
				return;
			}
		}
		if (config.watcherTitle) {
			if (unformatted.contains("The Watcher: That will be enough for now.")) {
				GuiManager.createTitle("Watcher Ready!", 20);
				return;
			}
		}
			if (config.readyAlert && Utils.inLoc(ArrStorage.dwarvenLocs)) {
				if (unformatted.equals("Mining Speed Boost is now available!")) {
					GuiManager.createTitle(EnumChatFormatting.GREEN, "Speed Boost Ready!", 20);
				}
			}
			if (config.expiredAlert && Utils.inLoc(ArrStorage.dwarvenLocs)) {
				if (unformatted.equals("Your Mining Speed Boost has expired!")) {
					GuiManager.createTitle("Speed Boost Expired!", 20);
				}
			}
			if (config.removeExpired) {
				if (unformatted.equals("Your Mining Speed Boost has expired!")) {
					event.setCanceled(true);
					return;
				}
			}
			if (config.removeReady) {
				if (unformatted.equals("Mining Speed Boost is now available!")) {
					event.setCanceled(true);
					return;
				}
			}
			if (config.removeUsed) {
				if (unformatted.equals("You used your Mining Speed Boost Pickaxe Ability!")) {
					event.setCanceled(true);
					return;
				}
			}
		/*
		  Taken from Skytils under GNU Affero General Public license.
		  https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
		  @author My-Name-Is-Jeff
		 * @author Sychic
		 */
		if (message.startsWith("Your new API key is ")) {
			String apiKey = event.message.getSiblings().get(0).getChatStyle().getChatClickEvent().getValue();
			config.apiKey = apiKey;
			config.markDirty();
			config.writeData();
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "SkyblockReinvented updated your set Hypixel API key to " + EnumChatFormatting.DARK_GREEN + apiKey));
		}
		if (message.contains("A Golden Goblin has spawned from the earth!")) {
			if (config.goblinAlert && config.goldenGoblin) {
				event.setCanceled(true);
				GuiManager.createTitle("Golden Goblin!", 20);
				return;
			} else if (config.goldenGoblin){
				event.setCanceled(true);
				return;
			} else if (config.goblinAlert) {
				GuiManager.createTitle("Golden Goblin!", 20);
			}
		}
		if (config.goldenGoblin) {
			if (Utils.containsAnyOf(unformatted, new String[]{"You received 400", "You received 200"}) && Utils.containsAllOf(unformatted, new String[]{"from killing a ", "Golden Goblin"})) {
				event.setCanceled(true);
				return;
			}
		}
		if (config.hideIronman) {
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
		if (config.removeCreeperVeil) {
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
		if (config.bongoAlert) {
			if (Utils.containsAllOf(unformatted, new String[]{"Your ", "Bonzo's Mask", "saved your life!"})) {
				event.setCanceled(true);
				GuiManager.createTitle("Bonzo Mask Triggered!", 20);
				return;
			}
		}
		if (config.sellable) {
			if (Utils.containsAllOf(unformatted, new String[]{"for ", "You sold ", "Coins!"})) {
				if (Utils.containsAnyOf(unformatted, blockedNames)) {
					event.setCanceled(true);
					return;
				}
			}
		}
		if (config.damageMilestones && Utils.inDungeons && unformatted.contains(" Milestone")) {
			if (Utils.containsAnyOf(unformatted, new String[]{"Mage", "Berserk", "Healer", "Tank", "Archer"})) { // TODO check if this works
				event.setCanceled(true);
				return;
			}
		}

		if (config.headlessHorseman) {
			if (Utils.inLoc(ArrStorage.hubLocs)) {
				if (Utils.containsAnyOf(unformatted, new String[]{"HORSEMAN HORSE DOWN!", "dealt the final blow.", "1st Damager - ", "2nd Damager - ", "3rd Damager - ",
						"-------------------------"})) {
					event.setCanceled(true);
					return;
				}
				if (Utils.containsAllOf(unformatted, new String[]{"Your Damage: ", "(Position #"})) {
					event.setCanceled(true);
					return;
				}
				if (Utils.containsAllOf(unformatted, new String[]{"Runecrafting: +", " XP"})) {
					event.setCanceled(true);
					return;
				}
			}
		}
	}
	@SuppressWarnings(UNUSED)
	@SubscribeEvent
	public void onSecond(SecondPassedEvent event) {
		if (secondsPassed > 0) {
			secondsPassed++;
			if (secondsPassed > 16 && !needsToClick) {
				Utils.sendCommand("garry");
				secondsPassed = 0;
			}
		}
	}

	@SuppressWarnings(UNUSED)
	@SubscribeEvent
	public void onInput(InputEvent.MouseInputEvent event) {
		if (needsToClick) {
			needsToClick = false;
		}
	}
	public void add(boolean concat, String[] messages, boolean shouldCancel) {
		add(concat, messages, shouldCancel, false);
	}
	public void add(boolean concat, String[] messages, boolean shouldCancel, boolean dungeons) {
		if (!concat) {
			this.filters.add(new Filter(messages, shouldCancel, dungeons));
			return;
		}
		this.filterConcats.add(new FilterConcat(messages, shouldCancel, dungeons));
	}
	public void init() {
		this.filters.clear();
		add(false, watcherQuotes, config.removeWatcher, true);
		add(false, new String[]{"You have found page "}, config.journalMsg, true);
		add(false, new String[]{"You need to be out of combat for 5 seconds before opening the SkyBlock Menu!"}, config.warpCombat);
		add(false, new String[]{"No tickets to put in the box..."}, config.ticketMsgs);
		add(false, new String[]{"Withdrawing coins...", "Depositing coins..."}, config.bankMsgs);
		add(false, new String[]{"Warping you to", "Sending to server"}, config.warps);
		add(false, new String[]{"Your active Potion Effects have been paused and stored. They will be restored when you leave Dungeons! You are not allowed to use existing Potion Effects while in Dungeons.", "You can no longer consume or splash any potions during the remainder of this Dungeon run!"}, config.dungeonPotionMsg);
		add(false, warningServers, config.serverFull);
		add(false, cleanSlayer, config.maddoxMsg);
		add(false, new String[]{"worth of Zombies", "worth of Spiders", "worth of Wolves"}, config.maddoxMsg);
		add(false, cleanJacob, config.uselessJacob);
		add(false, new String[]{"A Crypt Wither Skull exploded, hitting you for "}, config.cryptWitherSkull, true);
		add(false, cleanJerry, config.cleanJerry);
		add(false, new String[]{"Your bone plating reduced the damage you took by"}, config.bonePlating, true);
		add(false, cleanEndDungeon, config.cleanEnd, true);
		add(false, hubWarnings, config.hubWarnings);
		add(false, dumbSlayerDrops, config.dumbSlayerDrops);
		add(false, new String[]{"Blacklisted modifications are a bannable offense!"}, config.watchdogAnnouncement);
		add(false, new String[]{"WATCHDOG ANNOUNCEMENT"}, config.watchdogAnnouncement);
		add(false, threeWeirdosIncorrect, config.threeWeirdosIncorrect == 1 || config.threeWeirdosIncorrect == 2, true);
		add(false, threeWeirdosSolutions, config.threeWeirdosIncorrect == 2, true);
		add(false, new String[]{"You hear the sound of something opening"}, config.openMsg, true);
		add(false, new String[]{"BUFF! You have gained", "Press TAB to view your active effects!"}, config.potionMsg);
		add(false, new String[]{"Your bone plating reduced the damage you took by"}, config.bonePlating, true);
		add(false, new String[]{"[NPC] Puzzler:"}, config.removePuzzler);
		add(false, witherSkullList, config.witherSkulls, true);
		add(false, new String[]{"Your inventory is full!"}, config.inventoryFull);
		add(false, dungeonFinder, config.dungeonFinder);
		add(false, healerMsg, config.healerMsg);
		add(false, new String[]{"extra Jerry packed his stuff and left!"}, config.jerryMsg);
		add(false, new String[]{"inventory does not have enough free space to add all items"}, config.removeMinionWarnings);
		add(false, new String[]{"Dicer dropped "}, config.removeRNGChat);
		add(false, tooFast, config.removeTooFast);
		add(false, experimentationTable, config.experimentationTable);
		add(false, new String[]{"Wait a moment before reforging again!"}, config.reforge);
		add(false, new String[]{"Your Guardian Chestplate nullified the damage you just took!"}, config.guardianChestplate);
		add(false, slowDown, config.slowDown);
		add(false, boneShieldList, config.skeletonHat);
		add(false, doubleMsg, config.doubleMsg);
		add(false, new String[]{"opened a WITHER door!"}, config.openWither, true);
		add(false, new String[]{"You do not have the key for this door!"}, config.cantOpenWither, true);
		add(false, new String[]{"Lost Adventurer used Dragon's Breath on you!"}, config.dragonsBreath);
		add(false, new String[]{"Putting coins in escrow...", "Executing instant buy...", "Executing instant sell..."}, config.bazaarMsg);
		add(false, new String[]{"The sound of pickaxes clashing against the rock has attracted the attention of the POWDER GHAST!", "Find the Powder Ghast near the "}, config.powderGhast);
		add(false, new String[]{"found a Wither Essence! Everyone gains an extra essence!"}, config.essenceMessages);

		add(true, new String[]{"Bazaar! Sold", "x ", " for ", "coins!"}, config.bazaarMsg);
		add(true, new String[]{"You salvaged a ", "for +", "Undead Essence!"}, config.salvage);
		add(true, new String[]{"You've earned ", "from mining your first Mithril Ore of the day!"}, config.firstMithrilPowder);
		add(true, new String[]{"Added", "to your dungeon journal collection!"}, config.journalMsg, true);
		add(true, new String[]{"You registered", "in the raffle event!"}, config.ticketMsgs);
		add(true, new String[]{"Withdrew", "There's now", "coins left in the account!"}, config.bankMsgs);
		add(true, new String[]{"Withdrew", "There's now ", "coins in the account!"}, config.bankMsgs);
		add(true, new String[]{"+", "Bits"}, config.cleanEnd, true);
		add(true, new String[]{"You received", "from minions"}, config.minionXP);
		add(true, new String[]{"You gained", "experience from minions!"}, config.minionXP);
		add(true, new String[]{"I'm currently taking care of your ", "You can pick it up in"}, config.katMsg);
		add(true, new String[]{"Staff have banned an additional ", "in the last 7 days."}, config.watchdogAnnouncement);
		add(true, new String[]{"Watchdog has banned ", "players in the last 7 days."}, config.watchdogAnnouncement);
		add(true, new String[]{"struck you for ", "damage!"}, config.struckYou, true);
		add(true, new String[]{"You need to have a class at level", "or higher to join this group!"}, config.dungeonFinder);
		add(true, new String[]{"Lost Adventurer", "struck you for ", "damage!"}, config.dragonsBreath);
		add(true, new String[]{"picked up your ", "Orb!"}, config.damageOrbs);
		add(true, new String[]{"You picked up a", " from ", " healing you for "}, config.damageOrbs);
		add(true, new String[]{"You applied the", "reforge to ", "accessories in your Accessory Bag!"}, config.reforge);
		add(true, new String[]{"You reforged", "into"}, config.reforge);
		add(true, new String[]{"You earned", "from playing SkyBlock!"}, config.gexpMsg);
		add(true, new String[]{"Your tether with ", "healed you for"}, config.healerMsg);
	}
}
