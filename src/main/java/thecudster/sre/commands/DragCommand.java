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

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.EnumChatFormatting;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.features.impl.dragons.DragTracker;
import thecudster.sre.util.sbutil.Utils;

import java.util.Arrays;
import java.util.List;

public class DragCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "drag";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/drag";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            SkyblockReinvented.config.dragTracker = !SkyblockReinvented.config.dragTracker;
        } else if (args[0].equals("clear")) {
            SkyblockReinvented.config.dragsSinceAotd = 0;
            SkyblockReinvented.config.dragsSincePet = 0;
            SkyblockReinvented.config.dragsSinceSup = 0;
            DragTracker.recentDrags.clear();
            SkyblockReinvented.config.writeData();
            SkyblockReinvented.config.markDirty();
        } else {
            Utils.sendMsg(EnumChatFormatting.RED + "Invalid usage! Use either /drag or /drag clear.");
        }
    }

    @Override
    public List<String> getCommandAliases() {
        return Arrays.asList("dragon", "drags", "dr");
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

}
