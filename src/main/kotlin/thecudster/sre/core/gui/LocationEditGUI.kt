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
package thecudster.sre.core.gui

import net.minecraft.client.gui.GuiScreen
import thecudster.sre.core.gui.ResizeButton.Corner
import thecudster.sre.core.gui.GuiElement
import thecudster.sre.core.gui.LocationButton
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.renderer.GlStateManager
import thecudster.sre.core.gui.ResizeButton
import net.minecraft.client.gui.ScaledResolution
import org.lwjgl.input.Mouse
import org.lwjgl.opengl.Display
import thecudster.sre.SkyblockReinvented
import thecudster.sre.core.PersistentSave
import java.io.IOException
import thecudster.sre.core.gui.GuiManager
import java.awt.Color
import java.util.HashMap

/**
 * Taken from Skytils under GNU Affero General Public license.
 * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
 * @author My-Name-Is-Jeff
 * @author Sychic
 */
class LocationEditGUI : GuiScreen() {
    private var xOffset = 0f
    private var yOffset = 0f
    private var resizing = false
    private var resizingCorner: Corner? = null
    private var dragging: GuiElement? = null
    private val locationButtons: MutableMap<GuiElement?, LocationButton> = HashMap()
    private var scaleCache = 0f
    override fun doesGuiPauseGame(): Boolean {
        return false
    }

    override fun initGui() {
        super.initGui()
        for ((_, value) in SkyblockReinvented.GUIMANAGER!!.elements) {
            val lb = LocationButton(value)
            buttonList.add(lb)
            locationButtons[value] = lb
        }
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        onMouseMove(mouseX, mouseY)
        recalculateResizeButtons()
        drawGradientRect(0, 0, width, height, Color(0, 0, 0, 50).rgb, Color(0, 0, 0, 200).rgb)
        for (button in buttonList) {
            if (button is LocationButton) {
                if (button.element.toggled) {
                    val locationButton = button
                    GlStateManager.pushMatrix()
                    val scale = locationButton.element.scale
                    GlStateManager.translate(locationButton.x, locationButton.y, 0f)
                    GlStateManager.scale(scale.toDouble(), scale.toDouble(), 1.0)
                    button.drawButton(mc, mouseX, mouseY)
                    GlStateManager.popMatrix()
                }
            } else if (button is ResizeButton) {
                val resizeButton = button
                val element = button.element
                GlStateManager.pushMatrix()
                val scale = element.scale
                GlStateManager.translate(resizeButton.x, resizeButton.y, 0f)
                GlStateManager.scale(scale.toDouble(), scale.toDouble(), 1.0)
                button.drawButton(mc, mouseX, mouseY)
                GlStateManager.popMatrix()
            } else {
                button.drawButton(mc, mouseX, mouseY)
            }
        }
    }

    public override fun actionPerformed(button: GuiButton) {
        if (button is LocationButton) {
            dragging = button.element
            val sr = ScaledResolution(mc)
            val minecraftScale = sr.scaleFactor.toFloat()
            val floatMouseX = Mouse.getX() / minecraftScale
            val floatMouseY = (mc.displayHeight - Mouse.getY()) / minecraftScale
            xOffset = floatMouseX - dragging!!.actualX
            yOffset = floatMouseY - dragging!!.actualY
        } else if (button is ResizeButton) {
            val resizeButton = button
            dragging = resizeButton.element
            resizing = true
            scaleCache = resizeButton.element.scale
            val sr = ScaledResolution(mc)
            val minecraftScale = sr.scaleFactor.toFloat()
            val floatMouseX = Mouse.getX() / minecraftScale
            val floatMouseY = (mc.displayHeight - Mouse.getY()) / minecraftScale
            xOffset = floatMouseX - resizeButton.x
            yOffset = floatMouseY - resizeButton.y
            resizingCorner = resizeButton.corner
        }
    }

    /**
     * Set the coordinates when the mouse moves.
     */
    protected fun onMouseMove(mouseX: Int, mouseY: Int) {
        val sr = ScaledResolution(mc)
        val minecraftScale = sr.scaleFactor.toFloat()
        val floatMouseX = Mouse.getX() / minecraftScale
        val floatMouseY = (Display.getHeight() - Mouse.getY()) / minecraftScale
        if (resizing) {
            val locationButton = locationButtons[dragging] ?: return
            val scale = scaleCache
            val scaledX1 = locationButton.x * scale
            val scaledY1 = locationButton.y * scale
            val scaledX2 = locationButton.x2 * scale
            val scaledY2 = locationButton.y2 * scale
            val scaledWidth = scaledX2 - scaledX1
            val scaledHeight = scaledY2 - scaledY1
            val width = locationButton.x2 - locationButton.x
            val height = locationButton.y2 - locationButton.y
            val middleX = scaledX1 + scaledWidth / 2f
            val middleY = scaledY1 + scaledHeight / 2f
            var xOffset = floatMouseX - xOffset * scale - middleX
            var yOffset = floatMouseY - yOffset * scale - middleY
            if (resizingCorner == Corner.TOP_LEFT) {
                xOffset *= -1f
                yOffset *= -1f
            } else if (resizingCorner == Corner.TOP_RIGHT) {
                yOffset *= -1f
            } else if (resizingCorner == Corner.BOTTOM_LEFT) {
                xOffset *= -1f
            }
            val newWidth = xOffset * 2f
            val newHeight = yOffset * 2f
            val scaleX = newWidth / width
            val scaleY = newHeight / height
            val newScale = Math.max(scaleX, scaleY)
            locationButton.element.scale = scaleCache + newScale
            locationButton.drawButton(mc, mouseX, mouseY)
            recalculateResizeButtons()
        } else if (dragging != null) {
            val lb = locationButtons[dragging] ?: return
            val x = (floatMouseX - xOffset) / sr.scaledWidth.toFloat()
            val y = (floatMouseY - yOffset) / sr.scaledHeight.toFloat()
            dragging!!.setPos(x, y)
            addResizeCorners(dragging!!)
        }
    }

    private fun addResizeCorners(element: GuiElement) {
        buttonList.removeIf { button: GuiButton? -> button is ResizeButton && button.element === element }
        buttonList.removeIf { button: GuiButton? -> button is ResizeButton && button.element !== element }
        val locationButton = locationButtons[element] ?: return
        val boxXOne = locationButton.x - ResizeButton.SIZE * element.scale
        val boxXTwo = locationButton.x + element.actualWidth + ResizeButton.SIZE * 2 * element.scale
        val boxYOne = locationButton.y - ResizeButton.SIZE * element.scale
        val boxYTwo = locationButton.y + element.actualHeight + ResizeButton.SIZE * 2 * element.scale
        buttonList.add(ResizeButton(boxXOne, boxYOne, element, Corner.TOP_LEFT))
        buttonList.add(ResizeButton(boxXTwo, boxYOne, element, Corner.TOP_RIGHT))
        buttonList.add(ResizeButton(boxXOne, boxYTwo, element, Corner.BOTTOM_LEFT))
        buttonList.add(ResizeButton(boxXTwo, boxYTwo, element, Corner.BOTTOM_RIGHT))
    }

    private fun recalculateResizeButtons() {
        for (button in buttonList) {
            if (button is ResizeButton) {
                val resizeButton = button
                val corner = resizeButton.corner
                val element = resizeButton.element
                val locationButton = locationButtons[element] ?: continue
                val boxXOne = locationButton.x - ResizeButton.SIZE * element.scale
                val boxXTwo = locationButton.x + element.actualWidth + ResizeButton.SIZE * element.scale
                val boxYOne = locationButton.y - ResizeButton.SIZE * element.scale
                val boxYTwo = locationButton.y + element.actualHeight + ResizeButton.SIZE * element.scale
                if (corner == Corner.TOP_LEFT) {
                    resizeButton.x = boxXOne
                    resizeButton.y = boxYOne
                } else if (corner == Corner.TOP_RIGHT) {
                    resizeButton.x = boxXTwo
                    resizeButton.y = boxYOne
                } else if (corner == Corner.BOTTOM_LEFT) {
                    resizeButton.x = boxXOne
                    resizeButton.y = boxYTwo
                } else if (corner == Corner.BOTTOM_RIGHT) {
                    resizeButton.x = boxXTwo
                    resizeButton.y = boxYTwo
                }
            }
        }
    }

    @Throws(IOException::class)
    override fun handleMouseInput() {
        super.handleMouseInput()
        val hovered = LocationButton.lastHoveredElement
        if (hovered != null) {
            hovered.scale = hovered.scale + Mouse.getEventDWheel() / 1000f
        }
    }

    /**
     * Reset the dragged feature when the mouse is released.
     */
    override fun mouseReleased(mouseX: Int, mouseY: Int, state: Int) {
        super.mouseReleased(mouseX, mouseY, state)
        dragging = null
        resizing = false
        scaleCache = 0f
    }

    /**
     * Saves the positions when the gui is closed
     */
    override fun onGuiClosed() {
        PersistentSave.markDirty<GuiManager>()
    }
}