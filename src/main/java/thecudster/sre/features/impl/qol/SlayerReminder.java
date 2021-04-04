package thecudster.sre.features.impl.qol;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import thecudster.sre.util.Utils;

public class SlayerReminder {
    public static void remindRevenant() throws InterruptedException {
        Thread.sleep(5000);
        Utils.playLoudSound("random.orb", 0.5);
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("§cStart your next revenant slayer!"));
    }
    public static void remindTara() throws InterruptedException {
        Thread.sleep(5000);
        Utils.playLoudSound("random.orb", 0.5);
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("§cStart your next tarantula slayer!"));
    }
    public static void remindSven() throws InterruptedException {
        Thread.sleep(5000);
        Utils.playLoudSound("random.orb", 0.5);
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("§cStart your next sven slayer!"));
    }
}
