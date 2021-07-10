package thecudster.sre.util.sbutil

/**
 * Taken from Skytils under GNU Affero General Public license.
 * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
 * @author Sychic
 * @author My-Name-Is-Jeff
 */
import net.minecraft.util.StringUtils as MinecraftStringUtils
import org.apache.commons.lang3.StringUtils as ApacheStringUtils

fun String?.stripControlCodes(): String = MinecraftStringUtils.stripControlCodes(this)

fun String?.startsWithAny(vararg sequences: CharSequence?) = ApacheStringUtils.startsWithAny(this, *sequences)
fun String?.containsAny(vararg sequences: CharSequence?): Boolean {
    if (this == null) return false
    return sequences.any { it != null && this.contains(it) }
}