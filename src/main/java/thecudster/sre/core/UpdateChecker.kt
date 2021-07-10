package thecudster.sre.core;

import com.google.gson.JsonArray;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.core.gui.UpdateCheckerGUI;
import thecudster.sre.util.api.APIUtil;

public class UpdateChecker {
    public String newVersionExists() {
        JsonArray apiObj = APIUtil.getJSONResponse("https://api.github.com/repos/theCudster/SkyblockReinvented/releases").getAsJsonArray();
        String version = apiObj.get(0).getAsJsonObject().get("tag_name").getAsString();
        if (!version.contains("v")) return null;
        version = version.substring(version.indexOf("v") + 1);
        if (version.contains("pre")) {
            double numVersion = Double.parseDouble(version.substring(0, version.indexOf("pre")));
            double preVersion = Double.parseDouble(version.substring(version.indexOf("pre") + 3));
            if (numVersion == myNumVersion() && preVersion == myPreVersion()) {
                return null;
            }
        } else {
            double numVersion = Double.parseDouble(version);
            if (numVersion == myNumVersion()) {
                return null;
            }
        }
        return version;
    }
    public double myNumVersion() {
        if (SkyblockReinvented.VERSION.contains("pre")) {
            return Double.parseDouble(SkyblockReinvented.VERSION.substring(0, SkyblockReinvented.VERSION.indexOf("-")));
        }
        return Double.parseDouble(SkyblockReinvented.VERSION);
    }
    public double myPreVersion() {
        if (!SkyblockReinvented.VERSION.contains("pre")) {
            return 0;
        }
        return Double.parseDouble(SkyblockReinvented.VERSION.substring(SkyblockReinvented.VERSION.indexOf("pre") + 3));
    }
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void mainMenuOpen(GuiOpenEvent event) {
        if (event.gui instanceof GuiMainMenu) {
            if (newVersionExists() != null) {
                SkyblockReinvented.currentGui = new UpdateCheckerGUI();
            }
        }
    }
}