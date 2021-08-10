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
package thecudster.sre.features.impl.dungeons

import net.minecraft.client.Minecraft
import net.minecraft.entity.Entity
import net.minecraft.entity.monster.EntitySkeleton
import net.minecraft.entity.passive.EntityBat
import net.minecraft.entity.passive.EntitySheep
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.client.event.RenderLivingEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.entity.EntityJoinWorldEvent
import net.minecraftforge.event.entity.living.LivingDeathEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import thecudster.sre.SkyblockReinvented
import thecudster.sre.core.gui.structure.GuiManager
import thecudster.sre.util.RenderUtils
import thecudster.sre.events.PlayerPingEvent
import thecudster.sre.util.Utils
import thecudster.sre.util.Utils.sendMsg
import thecudster.sre.util.sbutil.ItemUtil.getSkyBlockItemID
import thecudster.sre.util.sbutil.stripControlCodes
import java.awt.Color

class DungeonFeatures {
    @SubscribeEvent(receiveCanceled = true)
    fun onRender(event: RenderLivingEvent.Pre<*>) {
        if (!Utils.inSkyblock || !Utils.inDungeons) return
        if (event.entity is EntityBat) {
            if (SkyblockReinvented.config.spiritBats) {
                if (!isSpiritBat(event.entity)) {
                    event.isCanceled = true
                    return
                }
            }
        }
    }

    var skeletonMasterTimer = 300
    var batSecretTimer = 300
    @SubscribeEvent
    fun onRenderLivingPre(event: RenderLivingEvent.Pre<*>) {
        // warn skeleton masters / bats
        if (!Utils.inSkyblock || !Utils.inDungeons) return
        if (event.entity.getDistanceToEntity(Minecraft.getMinecraft().thePlayer) <= SkyblockReinvented.config.skeletonRange && isSkeletonMaster(event.entity)) {
            if (skeletonMasterTimer > 300) {
                GuiManager.createTitle("Skeleton Master Nearby!", 20)
                skeletonMasterTimer = 0
            } else {
                skeletonMasterTimer++
            }
            return
        }
        if (event.entity.getDistanceToEntity(Minecraft.getMinecraft().thePlayer) <= SkyblockReinvented.config.batRange && SkyblockReinvented.config.warnBatSecrets && isSpiritBat(event.entity)) {
            if (batSecretTimer > 300) {
                GuiManager.createTitle("Bat Secret Nearby!", 20)
                batSecretTimer = 0
            } else {
                batSecretTimer++
            }
        }
        // box starred mobs
        /*
         * Code to detect starred mobs taken from Skytils under GNU Affero General Public License.
         * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
         * @author My-Name-Is-Jeff
         * @author Sychic
         */
        if (!Minecraft.getMinecraft().thePlayer.canEntityBeSeen(event.entity)) return
        if (SkyblockReinvented.config.outlineMobs) {
            val name = event.entity.customNameTag.stripControlCodes()
            if (name.startsWith("✯ ") && name.contains("❤")) {
                if (name.contains("Lost Adventurer") || name.contains("Angry Archeologist") || name.contains("Lurker") || name.contains(
                        "Dreadlord"
                    ) || name.contains("Souleater") || name.contains("Zombie") || name.contains("Skeleton") || name.contains(
                        "Skeletor"
                    ) || name.contains("Sniper") || name.contains("Super Archer") || name.contains("Spider") || name.contains(
                        "Fels"
                    ) || name.contains("Withermancer")
                ) {
                    Minecraft.getMinecraft().theWorld.loadedEntityList.filter { it != Minecraft.getMinecraft().thePlayer && !it.isInvisible && it.getDistanceToEntity(event.entity) <= 3 }.forEach {
                        RenderUtils.drawOutlinedBoundingBox(it.entityBoundingBox, Color(255, 0, 0, 255), 3f, 1f)
                    }
                }
            }
        }
    }

    @SubscribeEvent
    fun onEntityDeath(event: LivingDeathEvent) {
        if (event.entity is EntityBat) {
            if (isSpiritBat(event.entity)) {
                batSecretTimer = 290
            }
        }
        if (isSkeletonMaster(event.entity)) {
            skeletonMasterTimer = 250
        }
    }

    @SubscribeEvent
    fun onEnter(event: EntityJoinWorldEvent) {
        if (event.entity is EntitySheep && SkyblockReinvented.config.sheep) {
            if (Utils.inDungeons && (event.entity as EntitySheep).maxHealth.toDouble() == 8.0) {
                event.isCanceled = true
            }
        }
    }

    @SubscribeEvent
    fun onPing(event: PlayerPingEvent) {
        if (event.sender == Minecraft.getMinecraft().thePlayer.name) {
            return
        }
        if (event.receiver != Minecraft.getMinecraft().thePlayer.name) {
            return
        }
        if (event.reason != null) {
            sendMsg("Reason: " + event.reason)
            GuiManager.createTitle("Spirit Leap to " + event.sender + " for " + event.reason, 20)
        } else {
            GuiManager.createTitle("Spirit Leap to" + event.sender, 20)
        }
    }

    @SubscribeEvent
    fun onChat(event: ClientChatReceivedEvent) {
        // if (!Utils.inDungeons) return; TODO uncomment, just for testing purposes
        val unformatted = event.message.unformattedText.stripControlCodes()
        if (unformatted.contains("Party > " + Minecraft.getMinecraft().thePlayer.name + ":")) {
            return
        }
        if (unformatted.contains("Party") && unformatted.contains(":") && unformatted.contains(Minecraft.getMinecraft().thePlayer.name)) {
            if (unformatted.contains("SREPingCommand")) {
                val pingedBy: String
                pingedBy = if (unformatted.contains("[MVP+]") || unformatted.contains("[VIP+]")) {
                    unformatted.substring(12, unformatted.indexOf(":"))
                } else if (unformatted.contains("[VIP]") || unformatted.contains("[MVP]")) {
                    unformatted.substring(11, unformatted.indexOf(":"))
                } else {
                    unformatted.substring(7, unformatted.indexOf(":"))
                }
                sendMsg("Pinged by: $pingedBy")
                if (unformatted.contains(" REASON: ")) {
                    val reason = unformatted.substring(unformatted.indexOf("REASON: ") + 8)
                    MinecraftForge.EVENT_BUS.post(
                        PlayerPingEvent(
                            pingedBy,
                            Minecraft.getMinecraft().thePlayer.name,
                            reason
                        )
                    )
                } else {
                    MinecraftForge.EVENT_BUS.post(PlayerPingEvent(pingedBy, Minecraft.getMinecraft().thePlayer.name))
                    GuiManager.createTitle("Spirit Leap to$pingedBy", 20)
                }
            }
        }
    }

    companion object {
        fun isSpiritBat(e: Entity?): Boolean {
            return if (!Utils.inSkyblock || !Utils.inDungeons || e !is EntityBat) {
                false
            } else e.maxHealth == 100f
        }

        fun isSkeletonMaster(e: Entity): Boolean {
            return if (e !is EntitySkeleton) false else Utils.inDungeons && SkyblockReinvented.config.warnSkeletonMasters && !e.isInvisible() && getSkyBlockItemID(
                e.getCurrentArmor(0)
            ) == "SKELETON_MASTER_BOOTS"
        }
    }
}