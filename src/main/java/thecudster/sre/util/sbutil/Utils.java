package thecudster.sre.util.sbutil;

import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import thecudster.sre.events.JoinSkyblockEvent;
import thecudster.sre.events.LeaveSkyblockEvent;

public class Utils {

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static boolean inSkyblock = false;
    public static boolean inDungeons = false;
    public static boolean shouldBypassVolume = false;

    static Random random = new Random();
    /**
     * Taken from Skytils under GNU Affero General Public license.
     * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
     */
    public static boolean isOnHypixel() {
        try {
            if (mc != null && mc.theWorld != null && !mc.isSingleplayer()) {
                if (mc.thePlayer != null && mc.thePlayer.getClientBrand() != null) {
                    if (mc.thePlayer.getClientBrand().toLowerCase().contains("hypixel")) return true;
                }
                if (mc.getCurrentServerData() != null) return mc.getCurrentServerData().serverIP.toLowerCase().contains("hypixel");
            }
            return false;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Taken from Danker's Skyblock Mod under GPL 3.0 license
     * https://github.com/bowser0000/SkyblockMod/blob/master/LICENSE
     * @author bowser0000
     */
    public static void checkForSkyblock() {
        if (isOnHypixel()) {
            ScoreObjective scoreboardObj = mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(1);
            if (scoreboardObj != null) {
                String scObjName = scoreboardObj.getDisplayName();
                if (scObjName.contains("SKYBLOCK")) {
                    if (!inSkyblock) {
                        MinecraftForge.EVENT_BUS.post(new JoinSkyblockEvent());
                    }
                    inSkyblock = true;
                    return;
                }
            }
            MinecraftForge.EVENT_BUS.post(new LeaveSkyblockEvent());
        }
        inSkyblock = false;
    }
    /**
     * Modified from Skytils under GNU Affero General Public license.
     * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
     */
    public static void checkForDungeons() {
        if (inSkyblock) {
            List<String> scoreboard = ScoreboardUtil.getSidebarLines();
            for (String s : scoreboard) {
                String sCleaned = ScoreboardUtil.cleanSB(s);
                if (sCleaned.contains("The Catacombs") || sCleaned.contains("Dungeon Cleared:")) {
                    inDungeons = true;
                    return;
                }
            }
        }
        inDungeons = false;
    }
    
    public static Slot getSlotUnderMouse(GuiContainer gui) {
        return ObfuscationReflectionHelper.getPrivateValue(GuiContainer.class, gui, "theSlot", "field_147006_u");
    }

    public static Iterable<BlockPos> getBlocksWithinRangeAtSameY(BlockPos center, int radius, int y) {
        BlockPos corner1 = new BlockPos(center.getX() - radius, y, center.getZ() - radius);
        BlockPos corner2 = new BlockPos(center.getX() + radius, y, center.getZ() + radius);
        return BlockPos.getAllInBox(corner1, corner2);
    }

    public static Random getRandom() {
        return random;
    }

    public static boolean isInTablist(EntityPlayer player){
        if (mc.isSingleplayer()) {
            return true;
        }
        for (NetworkPlayerInfo pi : mc.getNetHandler().getPlayerInfoMap()) {
            if (pi.getGameProfile().getName().equalsIgnoreCase(player.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Taken from SkyblockAddons under MIT License
     * https://github.com/BiscuitDevelopment/SkyblockAddons/blob/master/LICENSE
     * @author BiscuitDevelopment
     */
    public static void playLoudSound(String sound, double pitch) {
        shouldBypassVolume = true;
        mc.thePlayer.playSound(sound, 2, (float) pitch);
        shouldBypassVolume = false;
    }
    // original
    public static void sendMsg(String msg) {
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(msg));
    }
    public static boolean checkIronman() {
        if (inSkyblock) {
            List<String> scoreboard = ScoreboardUtil.getSidebarLines();
            for (String s : scoreboard) {
                String sCleaned = ScoreboardUtil.cleanSB(s);
                if (sCleaned.contains("Ironman")) {
                    return true;
                }
            }
        }
        return false;
    }

}