/*
 * SkyblockReinvented - Hypixel Skyblock Improvement Modification for Minecraft
 *  Copyright (C) 2021 theCudster
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published
 *  by the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package thecudster.sre.util.sbutil;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Slot;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import thecudster.sre.events.JoinSkyblockEvent;
import thecudster.sre.events.LeaveSkyblockEvent;
import thecudster.sre.events.PacketEvent;

import java.util.List;
import java.util.Random;

public class Utils {

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static boolean inSkyblock = false;
    public static boolean inDungeons = false;
    public static boolean shouldBypassVolume = false;

    static Random random = new Random();
    /**
     * Taken from Skytils under GNU Affero General Public license.
     * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
     * @author Sychic
     * @author My-Name-Is-Jeff
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
     * Modified from Danker's Skyblock Mod under GPL 3.0 license
     * https://github.com/bowser0000/SkyblockMod/blob/master/LICENSE
     * @author bowser0000
     */
    public static void checkForSkyblock() {
        if (isOnHypixel()) {
            ScoreObjective scoreboardObj = mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(1);
            if (scoreboardObj != null) {
                String scObjName = scoreboardObj.getDisplayName();
                if (scObjName.contains("SKYBLOCK")) {
                    MinecraftForge.EVENT_BUS.post(new JoinSkyblockEvent());
                    inSkyblock = true;
                    return;
                }
            }
        }
        MinecraftForge.EVENT_BUS.post(new LeaveSkyblockEvent());
        inSkyblock = false;
    }
    /**
     * Modified from Skytils under GNU Affero General Public license.
     * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
     * @author Sychic
     * @author My-Name-Is-Jeff
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
    public static boolean isInTablist(String playerName){
        if (mc.isSingleplayer()) {
            return true;
        }
        for (NetworkPlayerInfo pi : mc.getNetHandler().getPlayerInfoMap()) {
            if (pi.getGameProfile().getName().equalsIgnoreCase(playerName)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Taken from Danker's Skyblock Mod under GPL 3.0 license
     * https://github.com/bowser0000/SkyblockMod/blob/master/LICENSE
     * @author bowser0000
     */
    public static BlockPos getNearbyBlock(Minecraft mc, BlockPos pos, Block... blockTypes) {
        if (pos == null) return null;
        BlockPos pos1 = new BlockPos(pos.getX() - 2, pos.getY() - 3, pos.getZ() - 2);
        BlockPos pos2 = new BlockPos(pos.getX() + 2, pos.getY() + 3, pos.getZ() + 2);

        BlockPos closestBlock = null;
        double closestBlockDistance = 99;
        Iterable<BlockPos> blocks = BlockPos.getAllInBox(pos1, pos2);

        for (BlockPos block : blocks) {
            for (Block blockType : blockTypes) {
                if (mc.theWorld.getBlockState(block).getBlock() == blockType && block.distanceSq(pos) < closestBlockDistance) {
                    closestBlock = block;
                    closestBlockDistance = block.distanceSq(pos);
                }
            }
        }

        return closestBlock;
    }
    /**
     * Taken from Danker's Skyblock Mod under GPL 3.0 license
     * https://github.com/bowser0000/SkyblockMod/blob/master/LICENSE
     * @author bowser0000
     */
    public static BlockPos getFirstBlockPosAfterVectors(Minecraft mc, Vec3 pos1, Vec3 pos2, int strength, int distance) {
        double x = pos2.xCoord - pos1.xCoord;
        double y = pos2.yCoord - pos1.yCoord;
        double z = pos2.zCoord - pos1.zCoord;

        for (int i = strength; i < distance * strength; i++) { // Start at least 1 strength away
            double newX = pos1.xCoord + ((x / strength) * i);
            double newY = pos1.yCoord + ((y / strength) * i);
            double newZ = pos1.zCoord + ((z / strength) * i);

            BlockPos newBlock = new BlockPos(newX, newY, newZ);
            if (mc.theWorld.getBlockState(newBlock).getBlock() != Blocks.air) {
                return newBlock;
            }
        }

        return null;
    }
    /**
     * Taken from Skytils under GNU Affero General Public license.
     * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
     * @author Sychic
     * @author My-Name-Is-Jeff
     */
    public static void cancelChatPacket(PacketEvent.ReceiveEvent ReceivePacketEvent) {
        if (!(ReceivePacketEvent.packet instanceof S02PacketChat)) return;
        ReceivePacketEvent.setCanceled(true);
        S02PacketChat packet = ((S02PacketChat) ReceivePacketEvent.packet);
        MinecraftForge.EVENT_BUS.post(new ClientChatReceivedEvent(packet.getType(), packet.getChatComponent()));
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
    public static boolean ironmanProfile = false;
    public static void sendMsg(String msg) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(msg));
    }
    public static void sendCommand(String command) {
        Minecraft.getMinecraft().thePlayer.sendChatMessage("/" + command);
    }
    public static boolean checkIronman() {
        if (inSkyblock) {
            List<String> scoreboard = ScoreboardUtil.getSidebarLines();
            for (String s : scoreboard) {
                String sCleaned = ScoreboardUtil.cleanSB(s);
                if (sCleaned.contains("Ironman")) {
                    ironmanProfile = true;
                    return ironmanProfile;
                }
            }
        }
        return false;
    }
    public static boolean inLoc(String[] locs) {
        for (String s : locs) {
            if (CurrentLoc.currentLoc.equals(s)) {
                return true;
            }
        }
        return false;
    }
    public static String getUnformattedChat(ClientChatReceivedEvent event) {
        return StringUtils.stripControlCodes(event.message.getUnformattedTextForChat());
    }
}