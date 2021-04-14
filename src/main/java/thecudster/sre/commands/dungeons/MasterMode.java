package thecudster.sre.commands.dungeons;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import java.util.Arrays;
import java.util.List;

public class MasterMode extends CommandBase {
  @Override
  public String getCommandName() {
    return "master";
  }

  @Override
  public String getCommandUsage(ICommandSender iCommandSender) {
    return "/master";
  }

  @Override
  public void processCommand(ICommandSender iCommandSender, String[] args) throws CommandException {
      if(args.length != 1){
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Usage: /master <floor>"));
        return;
      }
      String floor = args[0];
      Minecraft.getMinecraft().thePlayer.sendChatMessage(String.format("/joindungeon master_catacombs %s", floor));

  }
  @Override
  public List<String> getCommandAliases() {
    return Arrays.asList("m", "mcata", "mastermode");
  }

  @Override
  public boolean canCommandSenderUseCommand(ICommandSender sender) {
    return true;
  }
}
