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

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import org.jetbrains.annotations.NotNull;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.sbutil.Utils;

import java.util.ArrayList;
import java.util.List;

public class AddItem implements ICommand {
    private final ArrayList<String> aliases = new ArrayList<String>();
    public AddItem() {
        aliases.add("s");
        aliases.add("searchitem");
    }
    @Override
    public String getCommandName() {
        return "search";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/search";
    }

    @Override
    public List<String> getCommandAliases() {
        return aliases;
    }
    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            Utils.sendMsg(EnumChatFormatting.RED + "Invalid arguments! Valid arguments are search, current, clear, add, and remove.");
            return;
        }
        switch (args[0]) {
            case "add":
                SkyblockReinvented.config.toSearch.add(args[1]);
                break;
            case "remove":
                for (int i = 0; i < SkyblockReinvented.config.toSearch.size(); i++) {
                    if (SkyblockReinvented.config.toSearch.get(i).equals(args[1])) {
                        SkyblockReinvented.config.toSearch.remove(i);
                    }
                }
                break;
            case "clear":
                SkyblockReinvented.config.toSearch.clear();
                break;
            case "current":
                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("Your current list is as follows:"));
                for (String s : SkyblockReinvented.config.toSearch) {
                    Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(s));
                }
                break;
            case "search":
                SkyblockReinvented.config.toSearch.clear();
                SkyblockReinvented.config.toSearch.add(args[1]);
                break;
            default:
                Utils.sendMsg(EnumChatFormatting.RED + "Invalid arguments! Valid arguments are search, current, clear, add, and remove.");
                break;
        }
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

    @Override
    public int compareTo(@NotNull ICommand o) {
        return 0;
    }
}
