package thecudster.sre.core;

import net.minecraft.util.EnumChatFormatting;
import thecudster.sre.util.Utils;

import java.util.ArrayList;

public class Changelog {
    private static ArrayList<ChangelogEntry> types;
    public static void init() {
        // v1.0-pre1
        types.add(new ChangelogEntry("", "1.0-pre1", ChangelogType.CHANGE));
        types.add(new ChangelogEntry("", "1.0-pre1", ChangelogType.ADDITION));
        types.add(new ChangelogEntry("", "1.0-pre1", ChangelogType.REMOVED));
    }
    public static void printChangelog(String version) {
        Utils.sendMsg(EnumChatFormatting.GREEN + "You just got the new version " + version + "! Here's what's new:");
        for (ChangelogEntry entry : types) {
            if (entry.getVersion().equals(version)) {
                Utils.sendMsg(entry.getFormattedString());
            }
        }
        Utils.sendMsg(EnumChatFormatting.RED + "❤ " + EnumChatFormatting.GOLD + "Thanks for using SRE!");
    }
}