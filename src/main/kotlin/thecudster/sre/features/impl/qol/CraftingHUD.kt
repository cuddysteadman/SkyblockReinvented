package thecudster.sre.features.impl.qol

import gg.essential.elementa.WindowScreen
import gg.essential.elementa.components.UIBlock
import gg.essential.elementa.components.input.UITextInput
import gg.essential.elementa.constraints.ChildBasedSizeConstraint
import gg.essential.elementa.dsl.childOf
import gg.essential.elementa.dsl.constrain
import gg.essential.elementa.dsl.percent
import net.minecraft.client.gui.GuiScreen
import net.minecraft.util.ResourceLocation
import kotlin.math.abs

class CraftingHUD {

}
enum class item(name: String) {
    TITA_DRILL_1("Titanium Drill DR-X355");
    fun getName(): String {
        return name
    }
}
/* i am so fucking horrible at kotlin and elementa lmaoooo
init {
        val searchButton = UITextInput("Search for an item...", shadow = false).childOf(window).constrain {
            x = 90.percent()
            y = 90.percent()
            width = ChildBasedSizeConstraint()
            height = ChildBasedSizeConstraint()
        }.onKeyType { typedChar, _ ->
            refreshSearch(typedChar)
        }
    }
    var searchText = ""
    fun refreshSearch(typedChar: Char) {
        searchText += typedChar

    }
    fun getClosestMatch(vals: Array<item>, searchText: String): String {
        var closest = Integer.MAX_VALUE
        var closestVal = ""
        vals.forEach {
            if (abs(it.getName().compareTo(searchText)) < closest) {
                closest = abs(it.getName().compareTo(searchText))
                closestVal = it.getName()
            }
        }
        return closestVal
    }
 */