/*
 * SkyblockReinvented - Hypixel Skyblock Improvement Modification for Minecraft
 * Copyright (C) 2021 theCudster
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package thecudster.sre.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import org.jetbrains.annotations.NotNull;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class JoinDung implements ICommand {
    private final ArrayList<String> aliases = new ArrayList<String>();
    public JoinDung() {
        aliases.add("dh");
        aliases.add("dungeonhub");
        aliases.add("dunghub");
    }
    @Override
    public String getCommandName() {
        return "dhub";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "Warp to dungeon hub.";
    }

    @Override
    public List<String> getCommandAliases() {
        return aliases;
    }
    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        Utils.checkForSkyblock();
        if (Utils.inSkyblock) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/warp dungeon_hub");
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
