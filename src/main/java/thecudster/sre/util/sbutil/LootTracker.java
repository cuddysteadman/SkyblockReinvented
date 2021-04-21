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
    public static String[] display = {EnumChatFormatting.GREEN + "Sorrow: " + sorrow, EnumChatFormatting.GREEN + "Bag of Cash: " + bagCash, EnumChatFormatting.GREEN +
            "Plasma: " + plasma, EnumChatFormatting.GREEN + "Volta: "
        + volta, EnumChatFormatting.GREEN + "Ghostly Boots: " + ghostlyBoots, EnumChatFormatting.GREEN + "Ghosts Since Sorrow: " + ghostsSinceSorrow};
    @SubscribeEvent
    public void onRender(RenderLivingEvent.Pre event) {
        display[0] = EnumChatFormatting.GREEN + "Sorrow: " + EnumChatFormatting.LIGHT_PURPLE + sorrow;
        display[1] = EnumChatFormatting.GREEN + "Bag of Cash: " + EnumChatFormatting.LIGHT_PURPLE + bagCash;
        display[2] = EnumChatFormatting.GREEN + "Plasma: " + EnumChatFormatting.LIGHT_PURPLE + plasma;
        display[3] = EnumChatFormatting.GREEN + "Volta: " + EnumChatFormatting.LIGHT_PURPLE + volta;
        display[4] = EnumChatFormatting.GREEN + "Ghostly Boots: " + EnumChatFormatting.LIGHT_PURPLE + ghostlyBoots;
        display[5] = EnumChatFormatting.GREEN + "Ghosts Since Sorrow: " + EnumChatFormatting.LIGHT_PURPLE + ghostsSinceSorrow;
    }
}
