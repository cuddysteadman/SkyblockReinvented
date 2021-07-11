package thecudster.sre.core

import net.minecraft.util.EnumChatFormatting

class ChangelogEntry(private val change: String, val version: String, private val type: ChangelogType) {
    val formattedString: String
        get() = " • " + type.prefix + " " + change
}

enum class ChangelogType(val prefix: String) {
    CHANGE(EnumChatFormatting.GRAY.toString() + "--- "), ADDITION(EnumChatFormatting.GREEN.toString() + "+ "), REMOVED(
        EnumChatFormatting.RED.toString() + "- "
    );

}