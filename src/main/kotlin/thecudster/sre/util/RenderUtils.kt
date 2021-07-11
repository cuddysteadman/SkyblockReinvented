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
package thecudster.sre.util

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.renderer.*
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.*
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL14
import thecudster.sre.util.sbutil.ItemRarity
import java.awt.Color

/**
 * Taken from NotEnoughUpdates under Creative Commons Attribution-NonCommercial 3.0
 * https://github.com/Moulberry/NotEnoughUpdates/blob/master/LICENSE
 * @author Moulberry
 */
object RenderUtils {
    @JvmOverloads
    fun drawFloatingRectDark(x: Int, y: Int, width: Int, height: Int, shadow: Boolean = true) {
        var alpha = -0x10000000
        if (OpenGlHelper.isFramebufferEnabled()) {
            val scaledResolution = ScaledResolution(Minecraft.getMinecraft())
        } else {
            alpha = -0x1000000
        }
        val main = alpha or 0x202026
        val light = -0xcfcfca
        val dark = -0xefefea
        Gui.drawRect(x, y, x + 1, y + height, light) //Left
        Gui.drawRect(x + 1, y, x + width, y + 1, light) //Top
        Gui.drawRect(x + width - 1, y + 1, x + width, y + height, dark) //Right
        Gui.drawRect(x + 1, y + height - 1, x + width - 1, y + height, dark) //Bottom
        Gui.drawRect(x + 1, y + 1, x + width - 1, y + height - 1, main) //Middle
        if (shadow) {
            Gui.drawRect(x + width, y + 2, x + width + 2, y + height + 2, 0x70000000) //Right shadow
            Gui.drawRect(x + 2, y + height, x + width, y + height + 2, 0x70000000) //Bottom shadow
        }
    }

    fun drawFloatingRect(x: Int, y: Int, width: Int, height: Int) {
        drawFloatingRectWithAlpha(x, y, width, height, 0xFF, true)
    }

    fun drawFloatingRectWithAlpha(x: Int, y: Int, width: Int, height: Int, alpha: Int, shadow: Boolean) {
        val main = alpha shl 24 or 0xc0c0c0
        val light = alpha shl 24 or 0xf0f0f0
        val dark = alpha shl 24 or 0x909090
        Gui.drawRect(x, y, x + 1, y + height, light) //Left
        Gui.drawRect(x + 1, y, x + width, y + 1, light) //Top
        Gui.drawRect(x + width - 1, y + 1, x + width, y + height, dark) //Right
        Gui.drawRect(x + 1, y + height - 1, x + width - 1, y + height, dark) //Bottom
        Gui.drawRect(x + 1, y + 1, x + width - 1, y + height - 1, main) //Middle
        if (shadow) {
            Gui.drawRect(x + width, y + 2, x + width + 2, y + height + 2, alpha * 3 / 5 shl 24) //Right shadow
            Gui.drawRect(x + 2, y + height, x + width, y + height + 2, alpha * 3 / 5 shl 24) //Bottom shadow
        }
    }

    fun drawTexturedRect(x: Float, y: Float, width: Float, height: Float, filter: Int) {
        drawTexturedRect(x, y, width, height, 0f, 1f, 0f, 1f, filter)
    }

    @JvmOverloads
    fun drawTexturedRect(
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        uMin: Float = 0f,
        uMax: Float = 1f,
        vMin: Float = 0f,
        vMax: Float = 1f,
        filter: Int = GL11.GL_NEAREST
    ) {
        GlStateManager.enableBlend()
        GL14.glBlendFuncSeparate(
            GL11.GL_SRC_ALPHA,
            GL11.GL_ONE_MINUS_SRC_ALPHA,
            GL11.GL_ONE,
            GL11.GL_ONE_MINUS_SRC_ALPHA
        )
        drawTexturedRectNoBlend(x, y, width, height, uMin, uMax, vMin, vMax, filter)
        GlStateManager.disableBlend()
    }

    fun drawTexturedRectNoBlend(
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        uMin: Float,
        uMax: Float,
        vMin: Float,
        vMax: Float,
        filter: Int
    ) {
        GlStateManager.enableTexture2D()
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, filter)
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, filter)
        val tessellator = Tessellator.getInstance()
        val worldrenderer = tessellator.worldRenderer
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX)
        worldrenderer
            .pos(x.toDouble(), (y + height).toDouble(), 0.0)
            .tex(uMin.toDouble(), vMax.toDouble()).endVertex()
        worldrenderer
            .pos((x + width).toDouble(), (y + height).toDouble(), 0.0)
            .tex(uMax.toDouble(), vMax.toDouble()).endVertex()
        worldrenderer
            .pos((x + width).toDouble(), y.toDouble(), 0.0)
            .tex(uMax.toDouble(), vMin.toDouble()).endVertex()
        worldrenderer
            .pos(x.toDouble(), y.toDouble(), 0.0)
            .tex(uMin.toDouble(), vMin.toDouble()).endVertex()
        tessellator.draw()
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST)
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST)
    }

    fun drawGradientRect(zLevel: Int, left: Int, top: Int, right: Int, bottom: Int, startColor: Int, endColor: Int) {
        val startAlpha = (startColor shr 24 and 255).toFloat() / 255.0f
        val startRed = (startColor shr 16 and 255).toFloat() / 255.0f
        val startGreen = (startColor shr 8 and 255).toFloat() / 255.0f
        val startBlue = (startColor and 255).toFloat() / 255.0f
        val endAlpha = (endColor shr 24 and 255).toFloat() / 255.0f
        val endRed = (endColor shr 16 and 255).toFloat() / 255.0f
        val endGreen = (endColor shr 8 and 255).toFloat() / 255.0f
        val endBlue = (endColor and 255).toFloat() / 255.0f
        GlStateManager.disableTexture2D()
        GlStateManager.enableBlend()
        GlStateManager.disableAlpha()
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0)
        GlStateManager.shadeModel(7425)
        val tessellator = Tessellator.getInstance()
        val worldrenderer = tessellator.worldRenderer
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR)
        worldrenderer.pos(right.toDouble(), top.toDouble(), zLevel.toDouble())
            .color(startRed, startGreen, startBlue, startAlpha).endVertex()
        worldrenderer.pos(left.toDouble(), top.toDouble(), zLevel.toDouble())
            .color(startRed, startGreen, startBlue, startAlpha).endVertex()
        worldrenderer.pos(left.toDouble(), bottom.toDouble(), zLevel.toDouble())
            .color(endRed, endGreen, endBlue, endAlpha).endVertex()
        worldrenderer.pos(right.toDouble(), bottom.toDouble(), zLevel.toDouble())
            .color(endRed, endGreen, endBlue, endAlpha).endVertex()
        tessellator.draw()
        GlStateManager.shadeModel(7424)
        GlStateManager.disableBlend()
        GlStateManager.enableAlpha()
        GlStateManager.enableTexture2D()
    }

    /**
     * Taken from Skyblockcatia under MIT License
     * Modified
     * https://github.com/SteveKunG/SkyBlockcatia/blob/1.8.9/LICENSE.md
     *
     * @author SteveKunG
     */
    fun renderRarity(xPos: Int, yPos: Int, rarity: ItemRarity?) {
        if (rarity != null) {
            val alpha = 0.8f
            GlStateManager.disableLighting()
            GlStateManager.disableDepth()
            GlStateManager.enableBlend()
            GlStateManager.enableAlpha()
            Minecraft.getMinecraft().textureManager.bindTexture(ResourceLocation("sre:gui/rarity.png"))
            GlStateManager.color(
                rarity.color.red / 255.0f,
                rarity.color.green / 255.0f,
                rarity.color.blue / 255.0f,
                alpha
            )
            GlStateManager.blendFunc(770, 771)
            GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_BLEND)
            Gui.drawModalRectWithCustomSizedTexture(xPos, yPos, 0f, 0f, 16, 16, 16f, 16f)
            GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE)
            GlStateManager.enableLighting()
            GlStateManager.enableDepth()
            GlStateManager.disableAlpha()
        }
    }

    fun progressBar(size: Int, progress: Double, chatFormatting: EnumChatFormatting): String {
        if (size < 5) {
            return "ERROR"
        }
        var toReturn =
            EnumChatFormatting.AQUA.toString() + "[" + EnumChatFormatting.func_175744_a(chatFormatting.colorIndex)
        var temp = size
        temp -= 2
        val total = (temp * progress).toInt()
        for (i in 0 until total) {
            toReturn += "|"
        }
        toReturn += EnumChatFormatting.GRAY
        for (i in 0 until temp - total) {
            toReturn += "|"
        }
        toReturn += EnumChatFormatting.AQUA.toString() + "] " + EnumChatFormatting.GOLD
        return toReturn
    }

    private val RARITY = ResourceLocation("sre:gui/rarity.png")
    private val beaconBeam = ResourceLocation("textures/entity/beacon_beam.png")

    /**
     * Taken from NotEnoughUpdates under Creative Commons Attribution-NonCommercial 3.0
     * https://github.com/Moulberry/NotEnoughUpdates/blob/master/LICENSE
     * @author Moulberry
     */
    fun renderBeaconBeam(x: Double, y: Double, z: Double, rgb: Int, alphaMultiplier: Float, partialTicks: Float) {
        val height = 300
        val bottomOffset = 0
        val topOffset = bottomOffset + height
        val tessellator = Tessellator.getInstance()
        val worldrenderer = tessellator.worldRenderer
        Minecraft.getMinecraft().textureManager.bindTexture(beaconBeam)
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, 10497.0f)
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, 10497.0f)
        GlStateManager.disableLighting()
        GlStateManager.enableCull()
        GlStateManager.enableTexture2D()
        GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0)
        GlStateManager.enableBlend()
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0)
        val time = Minecraft.getMinecraft().theWorld.totalWorldTime + partialTicks.toDouble()
        val d1 = MathHelper.func_181162_h(
            -time * 0.2 - MathHelper.floor_double(-time * 0.1)
                .toDouble()
        )
        val r = (rgb shr 16 and 0xFF) / 255f
        val g = (rgb shr 8 and 0xFF) / 255f
        val b = (rgb and 0xFF) / 255f
        val d2 = time * 0.025 * -1.5
        val d4 = 0.5 + Math.cos(d2 + 2.356194490192345) * 0.2
        val d5 = 0.5 + Math.sin(d2 + 2.356194490192345) * 0.2
        val d6 = 0.5 + Math.cos(d2 + Math.PI / 4.0) * 0.2
        val d7 = 0.5 + Math.sin(d2 + Math.PI / 4.0) * 0.2
        val d8 = 0.5 + Math.cos(d2 + 3.9269908169872414) * 0.2
        val d9 = 0.5 + Math.sin(d2 + 3.9269908169872414) * 0.2
        val d10 = 0.5 + Math.cos(d2 + 5.497787143782138) * 0.2
        val d11 = 0.5 + Math.sin(d2 + 5.497787143782138) * 0.2
        val d14 = -1.0 + d1
        val d15 = height.toDouble() * 2.5 + d14
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR)
        worldrenderer.pos(x + d4, y + topOffset, z + d5).tex(1.0, d15).color(r, g, b, 1.0f * alphaMultiplier)
            .endVertex()
        worldrenderer.pos(x + d4, y + bottomOffset, z + d5).tex(1.0, d14).color(r, g, b, 1.0f).endVertex()
        worldrenderer.pos(x + d6, y + bottomOffset, z + d7).tex(0.0, d14).color(r, g, b, 1.0f).endVertex()
        worldrenderer.pos(x + d6, y + topOffset, z + d7).tex(0.0, d15).color(r, g, b, 1.0f * alphaMultiplier)
            .endVertex()
        worldrenderer.pos(x + d10, y + topOffset, z + d11).tex(1.0, d15).color(r, g, b, 1.0f * alphaMultiplier)
            .endVertex()
        worldrenderer.pos(x + d10, y + bottomOffset, z + d11).tex(1.0, d14).color(r, g, b, 1.0f).endVertex()
        worldrenderer.pos(x + d8, y + bottomOffset, z + d9).tex(0.0, d14).color(r, g, b, 1.0f).endVertex()
        worldrenderer.pos(x + d8, y + topOffset, z + d9).tex(0.0, d15).color(r, g, b, 1.0f * alphaMultiplier)
            .endVertex()
        worldrenderer.pos(x + d6, y + topOffset, z + d7).tex(1.0, d15).color(r, g, b, 1.0f * alphaMultiplier)
            .endVertex()
        worldrenderer.pos(x + d6, y + bottomOffset, z + d7).tex(1.0, d14).color(r, g, b, 1.0f).endVertex()
        worldrenderer.pos(x + d10, y + bottomOffset, z + d11).tex(0.0, d14).color(r, g, b, 1.0f).endVertex()
        worldrenderer.pos(x + d10, y + topOffset, z + d11).tex(0.0, d15).color(r, g, b, 1.0f * alphaMultiplier)
            .endVertex()
        worldrenderer.pos(x + d8, y + topOffset, z + d9).tex(1.0, d15).color(r, g, b, 1.0f * alphaMultiplier)
            .endVertex()
        worldrenderer.pos(x + d8, y + bottomOffset, z + d9).tex(1.0, d14).color(r, g, b, 1.0f).endVertex()
        worldrenderer.pos(x + d4, y + bottomOffset, z + d5).tex(0.0, d14).color(r, g, b, 1.0f).endVertex()
        worldrenderer.pos(x + d4, y + topOffset, z + d5).tex(0.0, d15).color(r, g, b, 1.0f * alphaMultiplier)
            .endVertex()
        tessellator.draw()
        GlStateManager.disableCull()
        val d12 = -1.0 + d1
        val d13 = height + d12
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR)
        worldrenderer.pos(x + 0.2, y + topOffset, z + 0.2).tex(1.0, d13).color(r, g, b, 0.25f * alphaMultiplier)
            .endVertex()
        worldrenderer.pos(x + 0.2, y + bottomOffset, z + 0.2).tex(1.0, d12).color(r, g, b, 0.25f).endVertex()
        worldrenderer.pos(x + 0.8, y + bottomOffset, z + 0.2).tex(0.0, d12).color(r, g, b, 0.25f).endVertex()
        worldrenderer.pos(x + 0.8, y + topOffset, z + 0.2).tex(0.0, d13).color(r, g, b, 0.25f * alphaMultiplier)
            .endVertex()
        worldrenderer.pos(x + 0.8, y + topOffset, z + 0.8).tex(1.0, d13).color(r, g, b, 0.25f * alphaMultiplier)
            .endVertex()
        worldrenderer.pos(x + 0.8, y + bottomOffset, z + 0.8).tex(1.0, d12).color(r, g, b, 0.25f).endVertex()
        worldrenderer.pos(x + 0.2, y + bottomOffset, z + 0.8).tex(0.0, d12).color(r, g, b, 0.25f).endVertex()
        worldrenderer.pos(x + 0.2, y + topOffset, z + 0.8).tex(0.0, d13).color(r, g, b, 0.25f * alphaMultiplier)
            .endVertex()
        worldrenderer.pos(x + 0.8, y + topOffset, z + 0.2).tex(1.0, d13).color(r, g, b, 0.25f * alphaMultiplier)
            .endVertex()
        worldrenderer.pos(x + 0.8, y + bottomOffset, z + 0.2).tex(1.0, d12).color(r, g, b, 0.25f).endVertex()
        worldrenderer.pos(x + 0.8, y + bottomOffset, z + 0.8).tex(0.0, d12).color(r, g, b, 0.25f).endVertex()
        worldrenderer.pos(x + 0.8, y + topOffset, z + 0.8).tex(0.0, d13).color(r, g, b, 0.25f * alphaMultiplier)
            .endVertex()
        worldrenderer.pos(x + 0.2, y + topOffset, z + 0.8).tex(1.0, d13).color(r, g, b, 0.25f * alphaMultiplier)
            .endVertex()
        worldrenderer.pos(x + 0.2, y + bottomOffset, z + 0.8).tex(1.0, d12).color(r, g, b, 0.25f).endVertex()
        worldrenderer.pos(x + 0.2, y + bottomOffset, z + 0.2).tex(0.0, d12).color(r, g, b, 0.25f).endVertex()
        worldrenderer.pos(x + 0.2, y + topOffset, z + 0.2).tex(0.0, d13).color(r, g, b, 0.25f * alphaMultiplier)
            .endVertex()
        tessellator.draw()
    }

    /**
     * Taken from NotEnoughUpdates under Creative Commons Attribution-NonCommercial 3.0
     * https://github.com/Moulberry/NotEnoughUpdates/blob/master/LICENSE
     * @author Moulberry
     */
    fun drawFilledBoundingBox(aabb: AxisAlignedBB, c: Color, alphaMultiplier: Float) {
        GlStateManager.enableBlend()
        GlStateManager.disableLighting()
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0)
        GlStateManager.disableTexture2D()
        val tessellator = Tessellator.getInstance()
        val worldrenderer = tessellator.worldRenderer
        GlStateManager.color(c.red / 255f, c.green / 255f, c.blue / 255f, c.alpha / 255f * alphaMultiplier)

        //vertical
        worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION)
        worldrenderer.pos(aabb.minX, aabb.minY, aabb.minZ).endVertex()
        worldrenderer.pos(aabb.maxX, aabb.minY, aabb.minZ).endVertex()
        worldrenderer.pos(aabb.maxX, aabb.minY, aabb.maxZ).endVertex()
        worldrenderer.pos(aabb.minX, aabb.minY, aabb.maxZ).endVertex()
        tessellator.draw()
        worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION)
        worldrenderer.pos(aabb.minX, aabb.maxY, aabb.maxZ).endVertex()
        worldrenderer.pos(aabb.maxX, aabb.maxY, aabb.maxZ).endVertex()
        worldrenderer.pos(aabb.maxX, aabb.maxY, aabb.minZ).endVertex()
        worldrenderer.pos(aabb.minX, aabb.maxY, aabb.minZ).endVertex()
        tessellator.draw()
        GlStateManager.color(
            c.red / 255f * 0.8f,
            c.green / 255f * 0.8f,
            c.blue / 255f * 0.8f,
            c.alpha / 255f * alphaMultiplier
        )

        //x
        worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION)
        worldrenderer.pos(aabb.minX, aabb.minY, aabb.maxZ).endVertex()
        worldrenderer.pos(aabb.minX, aabb.maxY, aabb.maxZ).endVertex()
        worldrenderer.pos(aabb.minX, aabb.maxY, aabb.minZ).endVertex()
        worldrenderer.pos(aabb.minX, aabb.minY, aabb.minZ).endVertex()
        tessellator.draw()
        worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION)
        worldrenderer.pos(aabb.maxX, aabb.minY, aabb.minZ).endVertex()
        worldrenderer.pos(aabb.maxX, aabb.maxY, aabb.minZ).endVertex()
        worldrenderer.pos(aabb.maxX, aabb.maxY, aabb.maxZ).endVertex()
        worldrenderer.pos(aabb.maxX, aabb.minY, aabb.maxZ).endVertex()
        tessellator.draw()
        GlStateManager.color(
            c.red / 255f * 0.9f,
            c.green / 255f * 0.9f,
            c.blue / 255f * 0.9f,
            c.alpha / 255f * alphaMultiplier
        )
        //z
        worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION)
        worldrenderer.pos(aabb.minX, aabb.maxY, aabb.minZ).endVertex()
        worldrenderer.pos(aabb.maxX, aabb.maxY, aabb.minZ).endVertex()
        worldrenderer.pos(aabb.maxX, aabb.minY, aabb.minZ).endVertex()
        worldrenderer.pos(aabb.minX, aabb.minY, aabb.minZ).endVertex()
        tessellator.draw()
        worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION)
        worldrenderer.pos(aabb.minX, aabb.minY, aabb.maxZ).endVertex()
        worldrenderer.pos(aabb.maxX, aabb.minY, aabb.maxZ).endVertex()
        worldrenderer.pos(aabb.maxX, aabb.maxY, aabb.maxZ).endVertex()
        worldrenderer.pos(aabb.minX, aabb.maxY, aabb.maxZ).endVertex()
        tessellator.draw()
        GlStateManager.enableTexture2D()
        GlStateManager.disableBlend()
    }

    /**
     * Taken from Danker's Skyblock Mod under GPL 3.0 license
     * https://github.com/bowser0000/SkyblockMod/blob/master/LICENSE
     * @author bowser0000
     */
    fun drawOutlinedBoundingBox(aabb: AxisAlignedBB?, color: Color, width: Float, partialTicks: Float) {
        val render = Minecraft.getMinecraft().renderViewEntity
        val realX = render.lastTickPosX + (render.posX - render.lastTickPosX) * partialTicks
        val realY = render.lastTickPosY + (render.posY - render.lastTickPosY) * partialTicks
        val realZ = render.lastTickPosZ + (render.posZ - render.lastTickPosZ) * partialTicks
        GlStateManager.pushMatrix()
        GlStateManager.translate(-realX, -realY, -realZ)
        GlStateManager.disableTexture2D()
        GlStateManager.enableBlend()
        GlStateManager.disableLighting()
        GlStateManager.disableAlpha()
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0)
        GL11.glLineWidth(width)
        RenderGlobal.drawOutlinedBoundingBox(aabb, color.red, color.green, color.blue, color.alpha)
        GlStateManager.translate(realX, realY, realZ)
        GlStateManager.disableBlend()
        GlStateManager.enableAlpha()
        GlStateManager.enableTexture2D()
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)
        GlStateManager.popMatrix()
    }

    /**
     * Taken from Skyblockcatia under MIT License
     * Modified
     * https://github.com/SteveKunG/SkyBlockcatia/blob/1.8.9/LICENSE.md
     *
     * @author SteveKunG
     */
    fun renderItem(itemStack: ItemStack?, x: Int, y: Int) {
        RenderHelper.enableGUIStandardItemLighting()
        GlStateManager.enableRescaleNormal()
        GlStateManager.enableBlend()
        GlStateManager.enableDepth()
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0)
        Minecraft.getMinecraft().renderItem.renderItemAndEffectIntoGUI(itemStack, x, y)
    }

    /**
     * Taken from Skyblockcatia under MIT License
     * Modified
     * https://github.com/SteveKunG/SkyBlockcatia/blob/1.8.9/LICENSE.md
     *
     * @author SteveKunG
     */
    fun renderTexture(texture: ResourceLocation?, x: Int, y: Int) {
        RenderHelper.enableGUIStandardItemLighting()
        GlStateManager.enableRescaleNormal()
        GlStateManager.enableBlend()
        GlStateManager.enableDepth()
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0)
        GlStateManager.pushMatrix()
        Minecraft.getMinecraft().textureManager.bindTexture(texture)
        GlStateManager.enableRescaleNormal()
        GlStateManager.enableAlpha()
        GlStateManager.alphaFunc(516, 0.1f)
        GlStateManager.enableBlend()
        GlStateManager.blendFunc(770, 771)
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0f, 0f, 16, 16, 16f, 16f)
        GlStateManager.disableAlpha()
        GlStateManager.disableRescaleNormal()
        GlStateManager.disableLighting()
        GlStateManager.popMatrix()
    }

    /**
     * Taken from Danker's Skyblock Mod under GPL 3.0 license
     * https://github.com/bowser0000/SkyblockMod/blob/master/LICENSE
     * @author bowser0000
     */
    fun drawOnSlot(size: Int, xSlotPos: Int, ySlotPos: Int, color: Int) {
        val sr = ScaledResolution(Minecraft.getMinecraft())
        val guiLeft = (sr.scaledWidth - 176) / 2
        val guiTop = (sr.scaledHeight - 222) / 2
        val x = guiLeft + xSlotPos
        var y = guiTop + ySlotPos
        // Move down when chest isn't 6 rows
        if (size != 90) y += (6 - (size - 36) / 9) * 9
        GL11.glTranslated(0.0, 0.0, 10.0)
        Gui.drawRect(x, y, x + 16, y + 16, color)
        GL11.glTranslated(0.0, 0.0, -10.0)
    }

    /**
     * Taken from SkyblockAddons under MIT License
     * Modified
     * https://github.com/BiscuitDevelopment/SkyblockAddons/blob/master/LICENSE
     * @author BiscuitDevelopment
     *
     * Draws a solid color rectangle with the specified coordinates and color (ARGB format). Args: x1, y1, x2, y2, color
     */
    fun drawRect(left: Double, top: Double, right: Double, bottom: Double, color: Int) {
        var left = left
        var top = top
        var right = right
        var bottom = bottom
        if (left < right) {
            val i = left
            left = right
            right = i
        }
        if (top < bottom) {
            val j = top
            top = bottom
            bottom = j
        }
        val f3 = (color shr 24 and 255).toFloat() / 255.0f
        val f = (color shr 16 and 255).toFloat() / 255.0f
        val f1 = (color shr 8 and 255).toFloat() / 255.0f
        val f2 = (color and 255).toFloat() / 255.0f
        GlStateManager.color(f, f1, f2, f3)
        val tessellator = Tessellator.getInstance()
        val worldrenderer = tessellator.worldRenderer
        GlStateManager.enableBlend()
        GlStateManager.disableTexture2D()
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0)
        worldrenderer.begin(7, DefaultVertexFormats.POSITION)
        worldrenderer.pos(left, bottom, 0.0).endVertex()
        worldrenderer.pos(right, bottom, 0.0).endVertex()
        worldrenderer.pos(right, top, 0.0).endVertex()
        worldrenderer.pos(left, top, 0.0).endVertex()
        tessellator.draw()
        GlStateManager.enableTexture2D()
        GlStateManager.disableBlend()
    }

    /**
     * Taken from Danker's Skyblock Mod under GPL 3.0 license
     * https://github.com/bowser0000/SkyblockMod/blob/master/LICENSE
     * @author bowser0000
     */
    fun draw3DLine(pos1: Vec3, pos2: Vec3, width: Int, color: Color, partialTicks: Float) {
        val render = Minecraft.getMinecraft().renderViewEntity
        val worldRenderer = Tessellator.getInstance().worldRenderer
        val realX = render.lastTickPosX + (render.posX - render.lastTickPosX) * partialTicks
        val realY = render.lastTickPosY + (render.posY - render.lastTickPosY) * partialTicks
        val realZ = render.lastTickPosZ + (render.posZ - render.lastTickPosZ) * partialTicks
        GlStateManager.pushMatrix()
        GlStateManager.translate(-realX, -realY, -realZ)
        GlStateManager.disableTexture2D()
        GlStateManager.enableBlend()
        GlStateManager.disableAlpha()
        GlStateManager.disableLighting()
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0)
        GL11.glLineWidth(width.toFloat())
        GlStateManager.color(color.red / 255f, color.green / 255f, color.blue / 255f, color.alpha / 255f)
        worldRenderer.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION)
        worldRenderer.pos(pos1.xCoord, pos1.yCoord, pos1.zCoord).endVertex()
        worldRenderer.pos(pos2.xCoord, pos2.yCoord, pos2.zCoord).endVertex()
        Tessellator.getInstance().draw()
        GlStateManager.translate(realX, realY, realZ)
        GlStateManager.disableBlend()
        GlStateManager.enableAlpha()
        GlStateManager.enableTexture2D()
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)
        GlStateManager.popMatrix()
    }

    /**
     * Taken from Danker's Skyblock Mod under GPL 3.0 license
     * https://github.com/bowser0000/SkyblockMod/blob/master/LICENSE
     * @author bowser0000
     */
    fun draw3DString(pos: Vec3, text: String?, color: Color, partialTicks: Float) {
        val mc = Minecraft.getMinecraft()
        val player: EntityPlayer = mc.thePlayer
        val x =
            pos.xCoord - player.lastTickPosX + (pos.xCoord - player.posX - (pos.xCoord - player.lastTickPosX)) * partialTicks
        val y =
            pos.yCoord - player.lastTickPosY + (pos.yCoord - player.posY - (pos.yCoord - player.lastTickPosY)) * partialTicks
        val z =
            pos.zCoord - player.lastTickPosZ + (pos.zCoord - player.posZ - (pos.zCoord - player.lastTickPosZ)) * partialTicks
        val renderManager = mc.renderManager
        val f = 1.6f
        val f1 = 0.016666668f * f
        val width = mc.fontRendererObj.getStringWidth(text) / 2
        GlStateManager.pushMatrix()
        GlStateManager.translate(x, y, z)
        GL11.glNormal3f(0f, 1f, 0f)
        GlStateManager.rotate(-renderManager.playerViewY, 0f, 1f, 0f)
        GlStateManager.rotate(renderManager.playerViewX, 1f, 0f, 0f)
        GlStateManager.scale(-f1, -f1, -f1)
        GlStateManager.enableBlend()
        GlStateManager.disableLighting()
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0)
        mc.fontRendererObj.drawString(text, -width, 0, color.rgb)
        GlStateManager.disableBlend()
        GlStateManager.popMatrix()
    }

    /**
     * Taken from NotEnoughUpdates under Creative Commons Attribution-NonCommercial 3.0
     * https://github.com/Moulberry/NotEnoughUpdates/blob/master/LICENSE
     * @author Moulberry
     */
    fun renderWaypointText(str: String?, loc: BlockPos, partialTicks: Float) {
        GlStateManager.alphaFunc(516, 0.1f)
        GlStateManager.pushMatrix()
        val viewer = Minecraft.getMinecraft().renderViewEntity
        val viewerX = viewer.lastTickPosX + (viewer.posX - viewer.lastTickPosX) * partialTicks
        val viewerY = viewer.lastTickPosY + (viewer.posY - viewer.lastTickPosY) * partialTicks
        val viewerZ = viewer.lastTickPosZ + (viewer.posZ - viewer.lastTickPosZ) * partialTicks
        var x = loc.x - viewerX
        var y = loc.y - viewerY - viewer.eyeHeight
        var z = loc.z - viewerZ
        val distSq = x * x + y * y + z * z
        val dist = Math.sqrt(distSq)
        if (distSq > 144) {
            x *= 12 / dist
            y *= 12 / dist
            z *= 12 / dist
        }
        GlStateManager.translate(x, y, z)
        GlStateManager.translate(0f, viewer.eyeHeight, 0f)
        drawNametag(str)
        GlStateManager.rotate(-Minecraft.getMinecraft().renderManager.playerViewY, 0.0f, 1.0f, 0.0f)
        GlStateManager.rotate(Minecraft.getMinecraft().renderManager.playerViewX, 1.0f, 0.0f, 0.0f)
        GlStateManager.translate(0f, -0.25f, 0f)
        GlStateManager.rotate(-Minecraft.getMinecraft().renderManager.playerViewX, 1.0f, 0.0f, 0.0f)
        GlStateManager.rotate(Minecraft.getMinecraft().renderManager.playerViewY, 0.0f, 1.0f, 0.0f)
        drawNametag(EnumChatFormatting.YELLOW.toString() + Math.round(dist) + "m")
        GlStateManager.popMatrix()
        GlStateManager.disableLighting()
    }

    /**
     * Taken from NotEnoughUpdates under Creative Commons Attribution-NonCommercial 3.0
     * https://github.com/Moulberry/NotEnoughUpdates/blob/master/LICENSE
     * @author Moulberry
     */
    fun drawNametag(str: String?) {
        val fontrenderer = Minecraft.getMinecraft().fontRendererObj
        val f = 1.6f
        val f1 = 0.016666668f * f
        GlStateManager.pushMatrix()
        GL11.glNormal3f(0.0f, 1.0f, 0.0f)
        GlStateManager.rotate(-Minecraft.getMinecraft().renderManager.playerViewY, 0.0f, 1.0f, 0.0f)
        GlStateManager.rotate(Minecraft.getMinecraft().renderManager.playerViewX, 1.0f, 0.0f, 0.0f)
        GlStateManager.scale(-f1, -f1, f1)
        GlStateManager.disableLighting()
        GlStateManager.depthMask(false)
        GlStateManager.disableDepth()
        GlStateManager.enableBlend()
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0)
        val tessellator = Tessellator.getInstance()
        val worldrenderer = tessellator.worldRenderer
        val i = 0
        val j = fontrenderer.getStringWidth(str) / 2
        GlStateManager.disableTexture2D()
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR)
        worldrenderer.pos((-j - 1).toDouble(), (-1 + i).toDouble(), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex()
        worldrenderer.pos((-j - 1).toDouble(), (8 + i).toDouble(), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex()
        worldrenderer.pos((j + 1).toDouble(), (8 + i).toDouble(), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex()
        worldrenderer.pos((j + 1).toDouble(), (-1 + i).toDouble(), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex()
        tessellator.draw()
        GlStateManager.enableTexture2D()
        fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, i, 553648127)
        GlStateManager.depthMask(true)
        fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, i, -1)
        GlStateManager.enableDepth()
        GlStateManager.enableBlend()
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)
        GlStateManager.popMatrix()
    }

    /*
     * Modified from Skytils under GNU Affero Public License.
     * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
     * @author Sychic
     * @author My-Name-Is-Jeff
     */
    @JvmOverloads
    fun drawWaypoint(
        partialTicks: Float,
        pos: BlockPos,
        text: String?,
        color: Color = Color(2, 250, 39),
        drawBeam: Boolean = true,
        alpha: Float = 1f,
        offset: Double = 0.0
    ) {
        val viewer = Minecraft.getMinecraft().renderViewEntity
        val viewerX = viewer.lastTickPosX + (viewer.posX - viewer.lastTickPosX) * partialTicks
        val viewerY = viewer.lastTickPosY + (viewer.posY - viewer.lastTickPosY) * partialTicks
        val viewerZ = viewer.lastTickPosZ + (viewer.posZ - viewer.lastTickPosZ) * partialTicks
        val x = pos.x - viewerX
        val y = pos.y - viewerY
        val z = pos.z - viewerZ
        val distSq = x * x + y * y + z * z
        GlStateManager.disableDepth()
        GlStateManager.disableCull()
        drawFilledBoundingBox(AxisAlignedBB(x, y + offset, z, x + 1, y + 1 + offset, z + 1), color, alpha)
        if (!drawBeam || text == null) {
            GlStateManager.enableDepth()
            GlStateManager.enableCull()
            return
        }
        GlStateManager.disableTexture2D()
        if (distSq > 5 * 5) renderBeaconBeam(x, y + 2, z, color.rgb, alpha, partialTicks)
        renderWaypointText(text, pos.up(5), partialTicks)
        GlStateManager.disableLighting()
        GlStateManager.enableTexture2D()
        GlStateManager.enableDepth()
        GlStateManager.enableCull()
    }
}