package thecudster.sre.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FragRun implements ICommand {
    private ArrayList<String> aliases = new ArrayList<String>();
    public FragRun() {
        aliases.add("fr");
        aliases.add("fragruns");
        aliases.add("frag");
    }
    @Override
    public String getCommandName() {
        return "fragrun";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "Parties an available frag run bot.";
    }

    @Override
    public List<String> getCommandAliases() {
        return aliases;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        Minecraft.getMinecraft().thePlayer.sendChatMessage("/p Fragrunners");
        Minecraft.getMinecraft().thePlayer.sendChatMessage("/p FraggleRockz");
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
