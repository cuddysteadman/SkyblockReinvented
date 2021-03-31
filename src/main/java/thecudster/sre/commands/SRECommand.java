package thecudster.sre.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import club.sk1er.mods.core.ModCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import thecudster.sre.SkyblockReinvented;

public class SRECommand implements ICommand {
	private final ArrayList<String> aliases = new ArrayList<String>();
	public SRECommand() {
		aliases.add("SRE");
		aliases.add("SkyblockReinvented");
		aliases.add("skyblockreinvented");
	}
	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		EntityPlayerSP player = (EntityPlayerSP) sender;
		
		
		ModCore.getInstance().getGuiHandler().open(SkyblockReinvented.config.gui());
		
	}

	@Override
	public int compareTo(ICommand o) {
		return 0;
	}

	@Override
	public String getCommandName() {
		return "sre";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return "Open SRE menu.";
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
