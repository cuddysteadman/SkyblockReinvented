package thecudster.sre.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import org.json.JSONException;
import thecudster.sre.util.fragbot.FragBot;
import thecudster.sre.util.fragbot.FragStatus;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;

public class FragRun extends CommandBase {
    private String PREFIX = EnumChatFormatting.DARK_RED + "[FragRunner] " + EnumChatFormatting.GRAY;
    @Override
    public String getCommandName() {
        return "fragrun";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/frags";
    }

    @Override
    public List<String> getCommandAliases() {
        return Arrays.asList("fragbot", "frag", "invitefrag", "fragrun", "fragbots", "fr");
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        int amount;
        if(args.length == 1){
            amount = Integer.parseInt(args[0]);
        }else{
            amount = 1;
        }
        new Thread(() -> {
            try{
                List<FragBot> fragBots = FragStatus.getBestBot(amount);
                if(fragBots.size() == 0){
                    sendMessage("Could not find any Bots. Open a Ticket on our partner's  " +  EnumChatFormatting.UNDERLINE + EnumChatFormatting.AQUA +" Discord");
                    return;
                }
                fragBots.forEach((fragBot) -> {
                    String username = fragBot.getUsername();
                    Minecraft.getMinecraft().thePlayer.sendChatMessage("/p " + username);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                sendMessage("These bots are provided by SkyblockReinvented's partner, Fragrunners. You can support them by joining their "
                        + EnumChatFormatting.UNDERLINE + EnumChatFormatting.AQUA+"Discord!");
            } catch (MalformedURLException | JSONException e) {
                sendMessage("There was an Error fetching the Data. Open a Ticket on our Fragrunner's (our partner) "
                        +EnumChatFormatting.UNDERLINE + EnumChatFormatting.AQUA + "Discord!");
                e.printStackTrace();
            }
        }).start();

    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        return null;
    }

    void sendMessage(String text ){
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(PREFIX + text)
                .setChatStyle(new ChatStyle()
                        .setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/Tfd6E92MGP"))));
    }
}
