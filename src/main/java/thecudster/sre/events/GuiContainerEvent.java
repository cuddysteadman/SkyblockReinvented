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

package thecudster.sre.events;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

/*
 * Taken from Skytils under GNU Affero Public license.
 * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
 * @author My-Name-Is-Jeff
 * @author Sychic
 */
public class GuiContainerEvent extends Event {

    public GuiContainer gui;
    public Container container;
    public GuiContainerEvent(GuiContainer gui, Container container) {
        this.gui = gui;
        this.container = container;
    }

    public static class BackgroundDrawnEvent extends GuiContainerEvent {
        public int mouseX, mouseY;
        public float partialTicks;

        public BackgroundDrawnEvent(GuiContainer gui, Container container, int mouseX, int mouseY, float partialTicks) {
            super(gui, container);
            this.mouseX = mouseX;
            this.mouseY = mouseY;
            this.partialTicks = partialTicks;
        }
    }

    public static class CloseWindowEvent extends GuiContainerEvent {

        public CloseWindowEvent(GuiContainer gui, Container container) {
            super(gui, container);
        }
    }

    public static class DrawSlotEvent extends GuiContainerEvent {

        public Slot slot;

        public DrawSlotEvent(GuiContainer gui, Container container, Slot slot) {
            super(gui, container);
            this.slot = slot;
        }

        @Cancelable
        public static class Pre extends DrawSlotEvent {
            public Pre(GuiContainer gui, Container container, Slot slot) {
                super(gui, container, slot);
            }
        }

        public static class Post extends DrawSlotEvent {
            public Post(GuiContainer gui, Container container, Slot slot) {
                super(gui, container, slot);
            }
        }
    }

    @Cancelable
    public static class SlotClickEvent extends GuiContainerEvent {

        public Slot slot;
        public int slotId, clickedButton, clickType;
        public SlotClickEvent(GuiContainer gui, Container container, Slot slot, int slotId, int clickedButton, int clickType) {
            super(gui, container);
            this.slot = slot;
            this.slotId = slotId;
            this.clickedButton = clickedButton;
            this.clickType = clickType;
        }
    }

}
