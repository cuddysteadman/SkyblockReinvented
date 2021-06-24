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

package thecudster.sre.features.impl.qol;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.events.GuiRenderItemEvent;
import thecudster.sre.util.sbutil.ItemUtil;
import thecudster.sre.util.Utils;

import java.util.List;

/**
 * Modified from Skytils under GNU Affero General Public license.
 * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
 * @author My-Name-Is-Jeff
 * @author Sychic
 */
public class CakeStackSize {
    @SubscribeEvent
    public void onRenderItemOverlayPos(GuiRenderItemEvent.RenderOverlayEvent.Post event) {
        if (!Utils.inSkyblock) return;
        ItemStack item = event.stack;
        if (item == null || item.stackSize != 1 || !SkyblockReinvented.config.cakeStackSize) return;
        String stackTip = "";
        if (item.hasDisplayName()) {
            if (item.getDisplayName().contains("New Year Cake")) {
                List<String> lore = ItemUtil.getItemLore(item);
                for (String s : lore) {
                    if (s.contains("celebration for the ")) {
                        s = s.substring(22);
                        if (s.contains("th")) {
                            s = s.substring(0, s.indexOf("th"));
                        }
                        else if (s.contains("st")) {
                            s = s.substring(0, s.indexOf("st"));
                        }
                        else if (s.contains("rd")) {
                            s = s.substring(0, s.indexOf("rd"));
                        }
                        else {
                            s = s.substring(0, s.indexOf("nd"));
                        }
                        stackTip = s;
                    }
                }
            }
        }
        if (stackTip.length() > 0) {
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            GlStateManager.disableBlend();
            event.fr.drawStringWithShadow(stackTip, (float) (event.x + 17 - event.fr.getStringWidth(stackTip)), (float) (event.y + 9), 16777215);
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
        }
    }
}
