package thecudster.sre.commands;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import thecudster.sre.SkyblockReinvented;

public class SBToggle implements ICommand {
	private final ArrayList<String> aliases = new ArrayList<String>();
	public SBToggle() {
		aliases.add("togglesb");
		aliases.add("sbt");
	}
	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		EntityPlayerSP player = (EntityPlayerSP) sender;
		
		
		SkyblockReinvented.config.joinSB = !SkyblockReinvented.config.joinSB;
		
	}

	@Override
	public int compareTo(ICommand o) {
		return 0;
	}

	@Override
	public String getCommandName() {
		return "sbtoggle";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return "Toggle whether to auto-join skyblock if in a different server.";
	}

	@Override
	public List<String> getCommandAliases() {
		// TODO Auto-generated method stub
		return aliases;
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		// TODO Auto-generated method stub
		return false;
	}
}