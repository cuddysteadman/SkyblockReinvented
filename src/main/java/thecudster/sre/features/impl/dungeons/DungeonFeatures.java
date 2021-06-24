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

package thecudster.sre.features.impl.dungeons;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.core.gui.GuiManager;
import thecudster.sre.core.gui.RenderUtils;
import thecudster.sre.events.PlayerPingEvent;
import thecudster.sre.util.sbutil.ItemUtil;
import thecudster.sre.util.Utils;

import java.awt.*;
import java.util.Objects;

public class DungeonFeatures {
    public static boolean isSpiritBat(Entity e) {
        if (!Utils.inSkyblock || !Utils.inDungeons || !(e instanceof EntityBat)) { return false; }
        return ((EntityBat) e).getMaxHealth() == 100;
    }
    @SubscribeEvent(receiveCanceled = true)
    public void onRender(RenderLivingEvent.Pre event) {
        if (!Utils.inSkyblock || !Utils.inDungeons) return;
        if (event.entity instanceof EntityBat) {
            if (SkyblockReinvented.config.spiritBats) {
                if (!isSpiritBat(event.entity)) {
                    event.setCanceled(true);
                    return;
                }
            }
        }
    }
    int stopDestroyingMyFuckingEars = 300;
    @SubscribeEvent
    public void onRenderLivingPre(RenderLivingEvent.Pre event) {
        // warn skeleton masters / bats
        if (!Utils.inSkyblock || !Utils.inDungeons) return;
        if (event.entity.getDistanceToEntity(Minecraft.getMinecraft().thePlayer) <= SkyblockReinvented.config.skeletonRange && isSkeletonMaster(event.entity)) {
            if (stopDestroyingMyFuckingEars > 300) {
                GuiManager.createTitle("Skeleton Master Nearby!", 20);
                stopDestroyingMyFuckingEars = 0;
            }
            else {
                stopDestroyingMyFuckingEars++;
            }
        }
        if (event.entity.getDistanceToEntity(Minecraft.getMinecraft().thePlayer) <= SkyblockReinvented.config.batRange && SkyblockReinvented.config.warnBatSecrets && isSpiritBat(event.entity)) {
            if (stopDestroyingMyFuckingEars > 300) {
                GuiManager.createTitle("Bat Secret Nearby!", 20);
                stopDestroyingMyFuckingEars = 0;
            }
            else {
                stopDestroyingMyFuckingEars++;
            }
        }
        // box starred mobs
        /*
         * Code to detect starred mobs taken from Skytils under GNU Affero General Public License.
         * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
         * @author My-Name-Is-Jeff
         * @author Sychic
         */
        if (!Minecraft.getMinecraft().thePlayer.canEntityBeSeen(event.entity)) return;
        if (SkyblockReinvented.config.outlineMobs) {
            String name = StringUtils.stripControlCodes(event.entity.getCustomNameTag());
            if (name.startsWith("✯ ") && name.contains("❤")) {
                if (name.contains("Lost Adventurer") || name.contains("Angry Archeologist") || name.contains("Lurker") || name.contains("Dreadlord") || name.contains("Souleater") || name.contains("Zombie") || name.contains("Skeleton") || name.contains("Skeletor") || name.contains("Sniper") || name.contains("Super Archer") || name.contains("Spider") || name.contains("Fels") || name.contains("Withermancer")) {
                    for (Entity e : Minecraft.getMinecraft().theWorld.loadedEntityList) {
                        if (!(e.equals(Minecraft.getMinecraft().thePlayer)) && !e.isInvisible()) {
                            if (e.getDistanceToEntity(event.entity) <= 3) {
                                RenderUtils.drawOutlinedBoundingBox(e.getEntityBoundingBox(), new Color(255, 0, 0, 255), 3f, 1f);
                            }
                        }
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public void onEntityDeath(LivingDeathEvent event) {
        if (event.entity instanceof EntityBat) {
            if (isSpiritBat(event.entity)) {
                stopDestroyingMyFuckingEars = 290;
            }
        }
        if (isSkeletonMaster(event.entity)) {
            stopDestroyingMyFuckingEars = 250;
        }
    }
    public static boolean isSkeletonMaster(Entity e) {
        if (!(e instanceof EntitySkeleton)) return false;
        return Utils.inDungeons && SkyblockReinvented.config.warnSkeletonMasters && !e.isInvisible() && Objects.equals(ItemUtil.getSkyBlockItemID(((EntitySkeleton)e).getCurrentArmor(0)), "SKELETON_MASTER_BOOTS");
    }
    @SubscribeEvent
    public void onEnter(EntityJoinWorldEvent event) {
        if (event.entity instanceof EntitySheep && SkyblockReinvented.config.sheep) {
            if (Utils.inDungeons && ((EntitySheep)event.entity).getMaxHealth() == 8.0) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onPing(PlayerPingEvent event) {
        if (event.sender.equals(Minecraft.getMinecraft().thePlayer.getName())) { return; }
        if (!event.receiver.equals(Minecraft.getMinecraft().thePlayer.getName())) { return; }
        if (event.reason != null) {
            Utils.sendMsg("Reason: " + event.reason);
            GuiManager.createTitle("Spirit Leap to " + event.sender + " for " + event.reason, 20);
        } else {
            GuiManager.createTitle("Spirit Leap to" + event.sender, 20);
        }
    }
    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        // if (!Utils.inDungeons) return; TODO uncomment, just for testing purposes
        String unformatted = StringUtils.stripControlCodes(event.message.getUnformattedText());
        if (unformatted.contains("Party > " + Minecraft.getMinecraft().thePlayer.getName() + ":")) { return; }
        if (unformatted.contains("Party") && unformatted.contains(":") && unformatted.contains(Minecraft.getMinecraft().thePlayer.getName())) {
            if (unformatted.contains("SREPingCommand")) {
                String pingedBy;
                if (unformatted.contains("[MVP+]") || unformatted.contains("[VIP+]")) {
                    pingedBy = unformatted.substring(12, unformatted.indexOf(":"));
                } else if (unformatted.contains("[VIP]") || unformatted.contains("[MVP]")) {
                    pingedBy = unformatted.substring(11, unformatted.indexOf(":"));
                } else {
                    pingedBy = unformatted.substring(7, unformatted.indexOf(":"));
                }
                Utils.sendMsg("Pinged by: " + pingedBy);
                if (unformatted.contains(" REASON: ")) {
                    String reason = unformatted.substring(unformatted.indexOf("REASON: ") + 8);
                    MinecraftForge.EVENT_BUS.post(new PlayerPingEvent(pingedBy, Minecraft.getMinecraft().thePlayer.getName(), reason));
                } else {
                    MinecraftForge.EVENT_BUS.post(new PlayerPingEvent(pingedBy, Minecraft.getMinecraft().thePlayer.getName()));
                    GuiManager.createTitle("Spirit Leap to" + pingedBy, 20);
                }
            }
        }
    }
}
