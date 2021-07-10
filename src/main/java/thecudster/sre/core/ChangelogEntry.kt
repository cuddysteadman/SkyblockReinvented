package thecudster.sre.core;

import net.minecraft.util.EnumChatFormatting;

public class ChangelogEntry {
    private String change;
    private ChangelogType type;
    private String version;

    public ChangelogEntry(String change, String version, ChangelogType type) {
        this.change = change;
        this.version = version;
        this.type = type;
    }

    public String getFormattedString() {
        return " • " + this.type.getPrefix() + " " + this.change;
    }
    public String getVersion() {
        return this.version;
    }
}
enum ChangelogType {
    CHANGE(EnumChatFormatting.GRAY + "--- "),
    ADDITION(EnumChatFormatting.GREEN + "+ "),
    REMOVED(EnumChatFormatting.RED + "- ");

    private String prefix;

    ChangelogType(String prefix) {
        this.prefix = prefix;
    }
    public String getPrefix() {
        return this.prefix;
    }
}