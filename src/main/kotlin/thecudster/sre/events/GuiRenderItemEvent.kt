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
package thecudster.sre.events

import net.minecraft.client.gui.FontRenderer
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.common.eventhandler.Event

/**
 * Taken from Skytils under GNU Affero General Public license.
 * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
 * @author My-Name-Is-Jeff
 * @author Sychic
 */
open class GuiRenderItemEvent : Event() {
    open class RenderOverlayEvent(
        var fr: FontRenderer?,
        var stack: ItemStack?,
        var x: Int,
        var y: Int,
        var text: String?
    ) : GuiRenderItemEvent() {
        class Post(fr: FontRenderer?, stack: ItemStack?, xPosition: Int, yPosition: Int, text: String?) :
            RenderOverlayEvent(fr, stack, xPosition, yPosition, text)
    }
}