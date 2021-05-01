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
package thecudster.sre.commands;

import club.sk1er.mods.core.ModCore;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.settings.LocationEditGUI;
import thecudster.sre.util.sbutil.Utils;

import java.util.Arrays;
import java.util.List;

public class SRECommand implements ICommand {
	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if (args.length > 0) {
			if (args[0].equals("gui")) {
				ModCore.getInstance().getGuiHandler().open(new LocationEditGUI());
			} else if (args[0].equals("help")) {
				Utils.sendMsg(EnumChatFormatting.GOLD + "✰ SkyblockReinvented Commands & Help");
				Utils.sendMsg("");
				Utils.sendMsg(EnumChatFormatting.GOLD + "✰ Settings");
				Utils.sendMsg(EnumChatFormatting.AQUA + "   • /sre: open the main config GUI");
				Utils.sendMsg(EnumChatFormatting.AQUA + "   • /sre gui: open GUI editing screen");
				Utils.sendMsg(EnumChatFormatting.AQUA + "   • /discord {status}: sets your custom status in discord (if you are using that setting)");
				Utils.sendMsg(EnumChatFormatting.GRAY + "     (Aliases: /da, /disc, /discset, /dset, /rp, /rpset)");
				Utils.sendMsg(EnumChatFormatting.GOLD + "✰ General");
				Utils.sendMsg(EnumChatFormatting.AQUA + "   • /re: toggle whether to render players " + EnumChatFormatting.GRAY + "(Aliases: /render)");
				Utils.sendMsg(EnumChatFormatting.AQUA + "   • /re add: add a player to the whitelist of rendered players");
				Utils.sendMsg(EnumChatFormatting.AQUA + "   • /re search: removes all players from the whitelist then adds the player you specify");
				Utils.sendMsg(EnumChatFormatting.AQUA + "   • /re remove: removes a certain player from the whitelist");
				Utils.sendMsg(EnumChatFormatting.AQUA + "   • /re clear: clears the whitelist");
				Utils.sendMsg(EnumChatFormatting.AQUA + "   • /s add: adds an item name to the whitelist of what to search for");
				Utils.sendMsg(EnumChatFormatting.AQUA + "   • /s search: removes all item names from the whitelist then adds the item name you specify");
				Utils.sendMsg(EnumChatFormatting.AQUA + "   • /s clear: clears the whitelist");
				Utils.sendMsg(EnumChatFormatting.AQUA + "   • /sb: joins skyblock");
				Utils.sendMsg(EnumChatFormatting.GOLD + "✰ Dungeons");
				Utils.sendMsg(EnumChatFormatting.AQUA + "   • /floor 1, /floor 2, /floor 3, etc: join a dungeon floor" +
						EnumChatFormatting.GRAY + " (Aliases: /fl, /catafloor)");
				Utils.sendMsg(EnumChatFormatting.AQUA + "   • /master 1, /master 2, /master 3, etc: joins a master dungeon floor " +
						EnumChatFormatting.GRAY + "(Aliases: /m, /mcata)");
				Utils.sendMsg(EnumChatFormatting.AQUA + "   • /fragrun: parties an online fragrunning bot " +
						EnumChatFormatting.GRAY + "(Aliases: /fr, /frags)");
				Utils.sendMsg(EnumChatFormatting.GOLD + "✰ Features:");
				Utils.sendMsg(EnumChatFormatting.AQUA + "   • /drag: toggles the dragon tracker GUI element " +
						EnumChatFormatting.GRAY + "(Aliases: /dr, /drags)");
				Utils.sendMsg(EnumChatFormatting.AQUA + "   • /drag clear: clears current dragon tracker info " +
						EnumChatFormatting.GRAY + "(Aliases: /dr clear, /drags clear)");

				Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GOLD +
						"Join the" + EnumChatFormatting.AQUA + " discord!").setChatStyle(new ChatStyle()
						.setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,
								"https://discord.gg/xkeYgZrRbN"))));
			}
		} else {
			ModCore.getInstance().getGuiHandler().open(SkyblockReinvented.config.gui());
		}
	}

	@Override
	public int compareTo(ICommand o) {
		return 0;
	}

	@Override
	public String getCommandName() {
		return "sre";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/sre";
	}

	@Override
	public List<String> getCommandAliases() {
		return Arrays.asList("SRE", "SkyblockReinvented", "skyblockreinvented", "sbr", "SBR");
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true;
	}

	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return false;
	}
}
