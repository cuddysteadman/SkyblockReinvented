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

import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.inventory.Container
import net.minecraft.inventory.Slot
import net.minecraftforge.fml.common.eventhandler.Cancelable
import net.minecraftforge.fml.common.eventhandler.Event
import thecudster.sre.events.GuiContainerEvent
import thecudster.sre.events.GuiContainerEvent.DrawSlotEvent

/*
 * Taken from Skytils under GNU Affero Public license.
 * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
 * @author My-Name-Is-Jeff
 * @author Sychic
 */
open class GuiContainerEvent(var gui: GuiContainer?, var container: Container?) : Event() {
    class BackgroundDrawnEvent(
        gui: GuiContainer?,
        container: Container?,
        var mouseX: Int,
        var mouseY: Int,
        var partialTicks: Float
    ) : GuiContainerEvent(gui, container)

    class CloseWindowEvent(gui: GuiContainer?, container: Container?) : GuiContainerEvent(gui, container)
    open class DrawSlotEvent(gui: GuiContainer?, container: Container?, var slot: Slot?) :
        GuiContainerEvent(gui, container) {
        @Cancelable
        class Pre(gui: GuiContainer?, container: Container?, slot: Slot?) : DrawSlotEvent(gui, container, slot)
        class Post(gui: GuiContainer?, container: Container?, slot: Slot?) : DrawSlotEvent(gui, container, slot)
    }

    @Cancelable
    class SlotClickEvent(
        gui: GuiContainer?,
        container: Container?,
        var slot: Slot,
        var slotId: Int,
        var clickedButton: Int,
        var clickType: Int
    ) : GuiContainerEvent(gui, container)
}