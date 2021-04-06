package thecudster.sre.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import org.jetbrains.annotations.NotNull;
import thecudster.sre.SkyblockReinvented;

import java.util.ArrayList;
import java.util.List;

public class Rendering implements ICommand {
    private final ArrayList<String> aliases = new ArrayList<String>();
    public Rendering() {
        aliases.add("render");
        aliases.add("rendering");
        aliases.add("re");
        aliases.add("re");
    }
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
        return aliases;
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
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("Your current list is as follows:"));
            for (String s : SkyblockReinvented.config.listToRender) {
                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(s));
            }
        }
        else if (args[0].equals("search")) {
            SkyblockReinvented.config.listToRender.clear();
            SkyblockReinvented.config.listToRender.add(args[1]);
        }
        else {
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("Invalid arguments! The valid arguments are current, clear, search, remove, and add."));

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
