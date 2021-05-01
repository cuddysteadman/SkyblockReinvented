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

package thecudster.sre.commands.dungeons;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import java.util.Arrays;
import java.util.List;

public class Catacombs extends CommandBase {
  @Override
  public String getCommandName() {
    return "floor";
  }

  @Override
  public String getCommandUsage(ICommandSender iCommandSender) {
    return "/floor";
  }

  @Override
  public void processCommand(ICommandSender iCommandSender, String[] args) throws CommandException {
    if(args.length != 1){
      Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Usage: /floor <floor>"));
      return;
    }
    String floor = args[0];
    Minecraft.getMinecraft().thePlayer.sendChatMessage(String.format("/joindungeon catacombs %s", floor));

  }
  @Override
  public List<String> getCommandAliases() {
    return Arrays.asList("fl", "catafloor");
  }

  @Override
  public boolean canCommandSenderUseCommand(ICommandSender sender) {
    return true;
  }
}
