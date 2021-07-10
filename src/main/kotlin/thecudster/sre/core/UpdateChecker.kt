package thecudster.sre.core

import com.google.gson.JsonArray
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.client.event.GuiOpenEvent
import net.minecraft.client.gui.GuiMainMenu
import thecudster.sre.SkyblockReinvented
import thecudster.sre.core.gui.UpdateCheckerGUI
import thecudster.sre.util.api.APIUtil

class UpdateChecker {
    fun newVersionExists(): String? {
        val apiObj: JsonArray =
            APIUtil.getJSONResponse("https://api.github.com/repos/theCudster/SkyblockReinvented/releases")
                .getAsJsonArray()
        var version = apiObj[0].asJsonObject["tag_name"].asString
        if (!version.contains("v")) return null
        version = version.substring(version.indexOf("v") + 1)
        if (version.contains("pre")) {
            val numVersion = version.substring(0, version.indexOf("pre")).toDouble()
            val preVersion = version.substring(version.indexOf("pre") + 3).toDouble()
            if (numVersion == myNumVersion() && preVersion == myPreVersion()) {
                return null
            }
        } else {
            val numVersion = version.toDouble()
            if (numVersion == myNumVersion()) {
                return null
            }
        }
        return version
    }

    fun myNumVersion(): Double {
        return if (SkyblockReinvented.VERSION.contains("pre")) {
            SkyblockReinvented.VERSION.substring(
                0,
                SkyblockReinvented.VERSION.indexOf("-")
            ).toDouble()
        } else SkyblockReinvented.VERSION.toDouble()
    }

    fun myPreVersion(): Double {
        return if (!SkyblockReinvented.VERSION.contains("pre")) {
            0.0
        } else SkyblockReinvented.VERSION.substring(
            SkyblockReinvented.VERSION.indexOf(
                "pre"
            ) + 3
        ).toDouble()
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    fun mainMenuOpen(event: GuiOpenEvent) {
        if (event.gui is GuiMainMenu) {
            if (newVersionExists() != null) {
                SkyblockReinvented.currentGui = UpdateCheckerGUI()
            }
        }
    }
}