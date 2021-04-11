package thecudster.sre.util.sbutil;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LootTracker {
    public static int sorrow;
    public static int bagCash;
    public static int plasma;
    public static int volta;
    public static int ghostlyBoots;
    public static int ghostsSinceSorrow;
    public static String display = EnumChatFormatting.GREEN + "Sorrow: " + sorrow + "\nBag of Cash: " + bagCash + "\nPlasma: " + plasma + "\nVolta: "
        + volta + "\nGhostly Boots: " + ghostlyBoots + "\nGhosts Since Sorrow: " + ghostsSinceSorrow;
    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent event) {
        display = EnumChatFormatting.GREEN + "Sorrow: " + sorrow + "\n" + EnumChatFormatting.GREEN + "Bag of Cash: " + bagCash +
                "\n" + EnumChatFormatting.GREEN + "Plasma: " + plasma + "\n" + EnumChatFormatting.GREEN + "Volta: " +
                volta + "\n" + EnumChatFormatting.GREEN + "Ghostly Boots: " + ghostlyBoots + "\n" + EnumChatFormatting.GREEN +
                "Ghosts Since Sorrow: " + ghostsSinceSorrow;
    }
}
