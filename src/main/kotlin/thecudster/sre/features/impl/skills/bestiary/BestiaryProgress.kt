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
package thecudster.sre.features.impl.skills.bestiary

import net.minecraft.client.Minecraft
import net.minecraft.entity.item.EntityArmorStand
import net.minecraft.util.ChatComponentText
import net.minecraft.util.EnumChatFormatting
import net.minecraftforge.event.entity.living.LivingDeathEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import thecudster.sre.SkyblockReinvented
import thecudster.sre.events.SecondPassedEvent
import thecudster.sre.util.api.APIUtil.getJSONResponse
import thecudster.sre.util.api.APIUtil.getLatestProfileID
import thecudster.sre.util.api.APIUtil.getUUID
import thecudster.sre.util.sbutil.stripControlCodes

class BestiaryProgress {
    val stats: Map<String, Double>
        get() = things

    @SubscribeEvent
    fun onEntityDeath(event: LivingDeathEvent) {
        if (Minecraft.getMinecraft().theWorld == null) return
        for (e in Minecraft.getMinecraft().theWorld.loadedEntityList) {
            if (e is EntityArmorStand && e.getDistanceToEntity(event.entity) < 3) {
                try {
                    if (!e.getCustomNameTag().contains("§f/§a")) {
                        return
                    }
                    if (e.getDistanceToEntity(Minecraft.getMinecraft().thePlayer) > 20) {
                        return
                    }
                    secondsSinceKill = 0
                    mobName = e.getCustomNameTag().substring(e.getCustomNameTag().indexOf(" §c") + 1)
                    mobName = mobName.substring(0, mobName.indexOf(" §a")).stripControlCodes()
                    val currentKills = "kills_" + getCorrectName(mobName)
                    if (!things.containsKey(currentKills) || things[currentKills]!! == 0.0) {
                        current[0] =
                            EnumChatFormatting.RED.toString() + "Current Bestiary: " + EnumChatFormatting.GOLD + mobName
                        current[1] =
                            EnumChatFormatting.RED.toString() + "Current Bestiary: " + EnumChatFormatting.GOLD + "Undetected / first kill."
                        return
                    }
                    things[currentKills] = things[currentKills]!! + 1
                    val numKills = things[currentKills]!!.toInt()
                    current[0] =
                        EnumChatFormatting.RED.toString() + "Current Bestiary: " + EnumChatFormatting.GOLD + mobName
                    current[1] = updateKills(numKills)
                } catch (ex: NullPointerException) {
                    ex.printStackTrace()
                }
            }
        }
    }

    @SubscribeEvent
    fun second(event: SecondPassedEvent?) {
        val hasInitialized = false
        if (!hasInitialized) placeItems()
        secondsSinceKill++
    }

    companion object {
        var mobName =
            EnumChatFormatting.RED.toString() + "Current Bestiary: " + EnumChatFormatting.GOLD + "Not detected yet!"
        var things: HashMap<String, Double> = LinkedHashMap()
        var current = arrayOf(
            EnumChatFormatting.RED.toString() + "Current Bestiary: " + EnumChatFormatting.GOLD + "Not detected yet!",
            EnumChatFormatting.RED.toString() + "Kills to next level: " + EnumChatFormatting.GOLD + "Not detected yet!"
        )
        var secondsSinceKill = 10
        fun placeItems() {
            things["kills_invisible_creeper"] = 0.0
            things["kills_diamond_zombie"] = 0.0
            things["kills_diamond_skeleton"] = 0.0
            things["kills_lapis_zombie"] = 0.0
            things["kills_zombie"] = 0.0
            things["kills_skeleton"] = 0.0
            things["kills_spider"] = 0.0
            things["kills_redstone_pigman"] = 0.0
            things["kills_enderman"] =
                0.0 // i thought this would work but then realized it didn't matter, too lazy to revert
            things["kills_zombie_villager"] = 0.0
            things["kills_wither_skeleton"] = 0.0
            things["kills_blaze"] = 0.0
            things["kills_emerald_slime"] = 0.0
            things["kills_witch"] = 0.0
            things["kills_ruin_wolf"] = 0.0
            things["kills_respawning_skeleton"] = 0.0
            things["kills_jockey_shot_silverfish"] = 0.0
            things["kills_magma_cube"] = 0.0
            things["kills_fireball_magma_cube"] = 0.0
            things["kills_pond_squid"] = 0.0
            things["kills_pigman"] = 0.0
            things["kills_night_respawning_skeleton"] = 0.0
            things["kills_sea_guardian"] = 0.0
            things["kills_goblin_weakling_melee"] = 0.0
            things["kills_goblin_creeper"] = 0.0
            things["kills_goblin_weakling_bow"] = 0.0
            things["kills_ice_walker"] = 0.0
            things["kills_chicken"] = 0.0
            things["kills_old_wolf"] = 0.0
            things["kills_wither_gourd"] = 0.0
            things["kills_phantom_spirit"] = 0.0
            things["kills_trick_or_treater"] = 0.0
            things["kills_batty_witch"] = 0.0
            things["kills_wraith"] = 0.0
            things["kills_scary_jerry"] = 0.0
            things["kills_weaver_spider"] = 0.0
            things["kills_splitter_spider"] = 0.0
            things["kills_splitter_spider_silverfish"] = 0.0
            things["kills_dasher_spider"] = 0.0
            things["kills_jockey_skeleton"] = 0.0
            things["kills_spider_jockey"] = 0.0
            things["kills_voracious_spider"] = 0.0
            things["kills_unburried_zombie"] = 0.0
            things["kills_random_slime"] = 0.0
            things["kills_sheep"] = 0.0
            things["kills_rabbit"] = 0.0
            things["kills_sea_walker"] = 0.0
            things["kills_minos_hunter"] = 0.0
            things["kills_siamese_lynx"] = 0.0
            things["kills_endermite"] = 0.0
            things["kills_watcher"] = 0.0
            things["kills_zealot_enderman"] = 0.0
            things["kills_obsidian_wither"] = 0.0
            things["kills_ghast"] = 0.0
            things["kills_sea_witch"] = 0.0
            things["kills_pack_spirit"] = 0.0
            things["kills_howling_spirit"] = 0.0
            things["kills_soul_of_the_alpha"] = 0.0
            things["kills_zombie_deep"] = 0.0
            things["kills_sea_archer"] = 0.0
            things["kills_water_hydra"] = 0.0
            things["kills_goblin_knife_thrower"] = 0.0
            things["kills_treasure_hoarder"] = 0.0
            things["kills_goblin"] = 0.0
            things["kills_crystal_sentry"] = 0.0
            things["kills_goblin_battler"] = 0.0
            things["kills_goblin_murderlover"] = 0.0
            things["kills_goblin_creepertamer"] = 0.0
            things["kills_goblin_golem"] = 0.0
            things["kills_creeper"] = 0.0
            things["kills_scarecrow"] = 0.0
            things["kills_catfish"] = 0.0
            things["kills_frosty_the_snowman"] = 0.0
            things["kills_frozen_steve"] = 0.0
            things["kills_liquid_hot_magma"] = 0.0
            things["kills_zombie_grunt"] = 0.0
            things["kills_crypt_lurker"] = 0.0
            things["kills_crypt_tank_zombie"] = 0.0
            things["kills_diamond_guy"] = 0.0
            things["kills_scared_skeleton"] = 0.0
            things["kills_dungeon_respawning_skeleton"] = 0.0
            things["kills_crypt_souleater"] = 0.0
            things["kills_sniper_skeleton"] = 0.0
            things["kills_lost_adventurer"] = 0.0
            things["kills_skeleton_grunt"] = 0.0
            things["kills_crypt_dreadlord"] = 0.0
            things["kills_watcher_summon_undead"] = 0.0
            things["kills_cellar_spider"] = 0.0
            things["kills_skeleton_soldier"] = 0.0
            things["kills_bonzo_summon_undead"] = 0.0
            things["kills_brood_mother_spider"] = 0.0
            things["kills_brood_mother_cave_spider"] = 0.0
            things["kills_arachne_brood"] = 0.0
            things["kills_generator_magma_cube"] = 0.0
            things["kills_powder_ghast"] = 0.0
            things["kills_arachne"] = 0.0
        }

        fun getThings() {
            /**
             * Modified from Skytils under GNU Affero General Public license.
             * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
             * @author My-Name-Is-Jeff
             * @author Sychic
             */
            Thread( Runnable {
                try {
                    val uuid = getUUID(Minecraft.getMinecraft().thePlayer.name)
                    val apiKey = SkyblockReinvented.config.apiKey
                    val latestProfile = getLatestProfileID(uuid, apiKey)
                    val profileResponse =
                        getJSONResponse("https://api.hypixel.net/skyblock/profile?profile=$latestProfile&key=$apiKey")
                    if (!profileResponse["success"].asBoolean) {
                        val reason = profileResponse["cause"].asString
                        Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText(EnumChatFormatting.RED.toString() + "Failed getting bestiary info with reason: " + reason))
                    }
                    val playerObject =
                        profileResponse["profile"].asJsonObject["members"].asJsonObject[uuid].asJsonObject
                    if (!playerObject.has("stats")) {
                        Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText(EnumChatFormatting.RED.toString() + "Failed getting stats with reason: No stats object."))
                    }
                    val statsObject = playerObject["stats"].asJsonObject
                    for ((key) in things) {
                        if (things.containsKey(key)) {
                            things.replace(
                                key,
                                statsObject[key].toString().substring(statsObject[key].toString().indexOf(":") + 1)
                                    .toDouble()
                            )
                        }
                    }
                } catch (e: NullPointerException) {
                    e.printStackTrace()
                }
            }).start()
        }

        private val killsLvling = intArrayOf(10, 25, 75, 150, 250, 500, 1500, 2500, 5000, 15000, 25000, 50000, 100000)
        fun getCorrectName(name: String?): String? {
            return when (name) {
                "Crypt Ghoul" -> "unburried_zombie"
                "Zombie" -> "zombie"
                "Skeleton" -> "skeleton"
                "Spider" -> "spider"
                "Enderman" -> "enderman"
                "Witch" -> "witch"
                "Zombie Villager" -> "zombie_villager"
                "Wolf" -> "ruin_wolf"
                "Old Wolf" -> "old_wolf"
                "Dasher Spider" -> "dasher_spider"
                "Spider Jockey" -> "spider_jockey"
                "Weaver Spider" -> "weaver_spider"
                "Voracious Spider" -> "voracious_spider"
                "Splitter Spider" -> "splitter_spider"
                "Rain Slime" -> "random_slime"
                "Magma Cube" -> "magma_cube"
                "Blaze" -> "blaze"
                "Wither Skeleton" -> "wither_skeleton"
                "Pigman" -> "pigman"
                "Ghast" -> "ghast"
                "Obsidian Defender" -> "obsidian_wither"
                "Zealot" -> "zealot_enderman"
                "Endermite" -> "endermite"
                "Sneaky Creeper" -> "invisible_creeper"
                "Lapis Zombie" -> "lapis_zombie"
                "Redstone Pigman" -> "redstone_pigman"
                "Emerald Slime" -> "emerald_slime"
                "Miner Zombie" -> "diamond_zombie"
                "miner Skeleton" -> "diamond_skeleton"
                "Ghost" -> "creeper" // i think?
                "Goblin" -> "goblin"
                "Treasure Hoarder" -> "treasure_hoarder"
                "Ice Walker" -> "ice_walker"
                "Pack Spirit" -> "pack_spirit"
                "Howling Spirit" -> "howling_spirit"
                "Soul of the Alpha" -> "soul_of_the_alpha"
                "Trick or Treater" -> "trick_or_treater"
                "Wither Gourd" -> "wither_gourd"
                "Phantom Spirit" -> "phantom_spirit"
                "Scary Jerry" -> "scary_jerry"
                "Wraith" -> "wraith"
                "Crazy Witch" -> "batty_witch"
                "watcher" -> "watcher"
                else -> null
            }
        }

        fun updateKills(kills: Int): String {
            var kills = kills
            var toReturn = ""
            for (i in killsLvling) {
                if (kills - i < 0) {
                    toReturn += EnumChatFormatting.RED.toString() + "Kills to next milestone: " + EnumChatFormatting.GOLD + "" + (i - kills)
                    return toReturn
                }
                kills -= i
            }
            return toReturn
        }
    }
}