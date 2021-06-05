package thecudster.sre.commands.dungeons;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.EnumChatFormatting;
import thecudster.sre.util.sbutil.Utils;

import java.util.Arrays;
import java.util.List;

public class PingCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "ping";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/ping {player}";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        //if (!Utils.inDungeons || !Utils.inSkyblock) {
          //  Utils.sendMsg(EnumChatFormatting.RED + "You aren't in dungeons! You may only use this command in dungeons.");
            //return;
        //} else if (args.length == 0) {
          //  Utils.sendMsg(EnumChatFormatting.RED + "Improper usage! Proper usage: /ping {player} [message]");
            //return;
        //} else {
            if (args.length == 1) {
                Utils.sendCommand("pc SREPingCommmand: " + args[0]);
            } else {
                Utils.sendCommand("pc SREPingCommand: " + args[0] + " REASON: " + args[1]);
            }
        //}
    }
    @Override
    public List<String> getCommandAliases() {
        return Arrays.asList("pi", "pingplayer", "playerping", "sreping");
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
}
