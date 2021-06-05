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

import java.util.Arrays;
import java.util.List;

public class Rendering implements ICommand {
    @Override
    public String getCommandName() {
        return "rend";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "Add/remove/clear a player from the list.";
    }

    @Override
    public List<String> getCommandAliases() {
        return Arrays.asList("render", "rendering", "re");
    }
    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            SkyblockReinvented.config.renderPlayers = !SkyblockReinvented.config.renderPlayers;
        }
        else if (args[0].equals("add")) {
            SkyblockReinvented.config.listToRender.add(args[1]);
        }
        else if (args[0].equals("remove")) {
            for (int i = 0; i < SkyblockReinvented.config.listToRender.size(); i++) {
                if (SkyblockReinvented.config.listToRender.get(i).equals(args[1])) {
                    SkyblockReinvented.config.listToRender.remove(i);
                }
            }
        }
        else if (args[0].equals("clear")) {
            SkyblockReinvented.config.listToRender.clear();
        }
        else if (args[0].equals("current")) {
           Utils.sendMsg(EnumChatFormatting.GOLD + "Your current list is as follows:");
            for (String s : SkyblockReinvented.config.listToRender) {
                Utils.sendMsg(EnumChatFormatting.GOLD + "" + s);
            }
        }
        else if (args[0].equals("search")) {
            SkyblockReinvented.config.listToRender.clear();
            SkyblockReinvented.config.listToRender.add(args[1]);
        }
        else {
            Utils.sendMsg(EnumChatFormatting.RED + "Invalid arguments! Valid arguments are search, current, clear, add, and remove.");
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
