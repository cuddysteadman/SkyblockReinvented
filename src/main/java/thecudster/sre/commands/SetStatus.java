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

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import org.jetbrains.annotations.NotNull;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.sbutil.Utils;

import java.util.Arrays;
import java.util.List;

public class SetStatus implements ICommand {
    @Override
    public String getCommandName() {
        return "discord";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/discord";
    }

    @Override
    public List<String> getCommandAliases() {
        return Arrays.asList("da", "disc", "dset", "discset", "rp", "rpset");
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (!Utils.inSkyblock) { Utils.sendMsg(EnumChatFormatting.RED + "Use this command in Skyblock!"); return; }
        if (!SkyblockReinvented.config.discordRP) { Utils.sendMsg(EnumChatFormatting.RED + "You do not have discord rich presence on, so this command will do nothing."); return; }
        if (!(SkyblockReinvented.config.discordMode == 4)) { Utils.sendMsg(EnumChatFormatting.RED + "You do not have the custom discord rich presence setting on, so this command will do nothing."); return; }
        if (args.length > 0) {
            if (!(args[0].equals("current"))) {
                String toReturn = "";
                toReturn += args[0];
                args[0] = "";
                for (String s : args) {
                    toReturn += " ";
                    toReturn += s;
                }
                SkyblockReinvented.config.discordCustomText = toReturn;
                SkyblockReinvented.config.writeData();
                SkyblockReinvented.config.markDirty();
                Utils.sendMsg(EnumChatFormatting.GREEN + "You set your custom status to: " + EnumChatFormatting.LIGHT_PURPLE + toReturn);
            } else {
                Utils.sendMsg(EnumChatFormatting.GREEN + "Your current discord status is: " + EnumChatFormatting.LIGHT_PURPLE + SkyblockReinvented.config.discordCustomText);
            }
        } else {
            Utils.sendMsg(EnumChatFormatting.RED + "Improper usage! /discord {custom status}, /discord current");
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
