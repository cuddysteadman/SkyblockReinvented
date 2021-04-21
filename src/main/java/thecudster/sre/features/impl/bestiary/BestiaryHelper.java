package thecudster.sre.features.impl.bestiary;

import net.minecraft.util.EnumChatFormatting;

public class BestiaryHelper {
    private static final int[] killsLvling = {10, 25, 75, 150, 250, 500, 1500, 2500, 5000, 15000, 25000, 50000, 100000};
    public static String getCorrectName(String name) {
        switch (name) {
            case "Crypt Ghoul":
                return "unburried_zombie";
            case "Zombie":
                return "zombie";
            case "Skeleton":
                return "skeleton";
            case "Spider":
                return "spider";
            case "Enderman":
                return "enderman";
            case "Witch":
                return "witch";
            case "Zombie Villager":
                return "zombie_villager";
            case "Wolf":
                return "ruin_wolf";
            case "Old Wolf":
                return "old_wolf";
            case "Dasher Spider":
                return "dasher_spider";
            case "Spider Jockey":
                return "spider_jockey";
            case "Weaver Spider":
                return "weaver_spider";
            case "Voracious Spider":
                return "voracious_spider";
            case "Splitter Spider":
                return "splitter_spider";
            case "Rain Slime":
                return "random_slime";
            case "Magma Cube":
                return "magma_cube";
            case "Blaze":
                return "blaze";
            case "Wither Skeleton":
                return "wither_skeleton";
            case "Pigman":
                return "pigman";
            case "Ghast":
                return "ghast";
            case "Obsidian Defender":
                return "obsidian_wither";
            case "Zealot":
                return "zealot_enderman";
            case "Endermite":
                return "endermite";
            case "Sneaky Creeper":
                return "invisible_creeper";
            case "Lapis Zombie":
                return "lapis_zombie";
            case "Redstone Pigman":
                return "redstone_pigman";
            case "Emerald Slime":
                return "emerald_slime";
            case "Miner Zombie":
                return "diamond_zombie";
            case "miner Skeleton":
                return "diamond_skeleton";
            case "Ghost":
                return "creeper"; // i think?
            case "Goblin":
                return "goblin";
            case "Treasure Hoarder":
                return "treasure_hoarder";
            case "Ice Walker":
                return "ice_walker";
            case "Pack Spirit":
                return "pack_spirit";
            case "Howling Spirit":
                return "howling_spirit";
            case "Soul of the Alpha":
                return "soul_of_the_alpha";
            case "Trick or Treater":
                return "trick_or_treater";
            case "Wither Gourd":
                return "wither_gourd";
            case "Phantom Spirit":
                return "phantom_spirit";
            case "Scary Jerry":
                return "scary_jerry";
            case "Wraith":
                return "wraith";
            case "Crazy Witch":
                return "batty_witch";
            case "watcher":
                return "watcher";
            default:
                return null;
        }
    }
    public static String updateKills(int kills) {
        String toReturn = "";
        for (int i : killsLvling) {
            if (!(kills - i >= 0)) {
                toReturn += EnumChatFormatting.RED + "Kills to next milestone: " + EnumChatFormatting.GOLD + "" + (i - kills);
                return toReturn;
            }
            kills -= i;
        }
        return toReturn;
    }
    public static String updateCurrent(String current) {
        return EnumChatFormatting.RED + "Current Bestiary: " + EnumChatFormatting.GOLD + current;
    }
}
