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

package thecudster.sre.features.impl.bestiary;

import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.events.SecondPassedEvent;
import thecudster.sre.util.api.APIUtil;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class BestiaryProgress {
    public static String mobName = EnumChatFormatting.RED + "Current Bestiary: " + EnumChatFormatting.GOLD + "Not detected yet!";
    public static HashMap<String, Double> things = new LinkedHashMap<String, Double>();
    public static String[] current = {EnumChatFormatting.RED + "Current Bestiary: " + EnumChatFormatting.GOLD + "Not detected yet!", EnumChatFormatting.RED + "Kills to next level: " + EnumChatFormatting.GOLD + "Not detected yet!"};
    public static int secondsSinceKill = 10;
    public static void placeItems() {
        things.put("kills_invisible_creeper", new Double(0.0));
        things.put("kills_diamond_zombie", new Double(0.0));
        things.put("kills_diamond_skeleton", new Double(0.0));
        things.put("kills_lapis_zombie", new Double(0.0));
        things.put("kills_zombie", 0.0);
        things.put("kills_skeleton", new Double(0.0));
        things.put("kills_spider", new Double(0.0));
        things.put("kills_redstone_pigman",new Double(0.0));
        things.put("kills_enderman",new Double(0.0)); // i thought this would work but then realized it didn't matter, too lazy to revert
        things.put("kills_zombie_villager",0.0);
        things.put("kills_wither_skeleton",0.0);
        things.put("kills_blaze",0.0);
        things.put("kills_emerald_slime",0.0);
        things.put("kills_witch",0.0);
        things.put("kills_ruin_wolf",0.0);
        things.put("kills_respawning_skeleton",0.0);
        things.put("kills_jockey_shot_silverfish",0.0);
        things.put("kills_magma_cube",0.0);
        things.put("kills_fireball_magma_cube",0.0);
        things.put("kills_pond_squid",0.0);
        things.put("kills_pigman",0.0);
        things.put("kills_night_respawning_skeleton",0.0);
        things.put("kills_sea_guardian",0.0);
        things.put("kills_goblin_weakling_melee",0.0);
        things.put("kills_goblin_creeper",0.0);
        things.put("kills_goblin_weakling_bow",0.0);
        things.put("kills_ice_walker",0.0);
        things.put("kills_chicken",0.0);
        things.put("kills_old_wolf",0.0);
        things.put("kills_wither_gourd",0.0);
        things.put("kills_phantom_spirit",0.0);
        things.put("kills_trick_or_treater",0.0);
        things.put("kills_batty_witch",0.0);
        things.put("kills_wraith",0.0);
        things.put("kills_scary_jerry",0.0);
        things.put("kills_weaver_spider",0.0);
        things.put("kills_splitter_spider",0.0);
        things.put("kills_splitter_spider_silverfish",0.0);
        things.put("kills_dasher_spider",0.0);
        things.put("kills_jockey_skeleton",0.0);
        things.put("kills_spider_jockey",0.0);
        things.put("kills_voracious_spider",0.0);
        things.put("kills_unburried_zombie",0.0);
        things.put("kills_random_slime",0.0);
        things.put("kills_sheep",0.0);
        things.put("kills_rabbit",0.0);
        things.put("kills_sea_walker",0.0);
        things.put("kills_minos_hunter",0.0);
        things.put("kills_siamese_lynx",0.0);
        things.put("kills_endermite",0.0);
        things.put("kills_watcher",0.0);
        things.put("kills_zealot_enderman",0.0);
        things.put("kills_obsidian_wither",0.0);
        things.put("kills_ghast",0.0);
        things.put("kills_sea_witch",0.0);
        things.put("kills_pack_spirit",0.0);
        things.put("kills_howling_spirit",0.0);
        things.put("kills_soul_of_the_alpha",0.0);
        things.put("kills_zombie_deep",0.0);
        things.put("kills_sea_archer",0.0);
        things.put("kills_water_hydra",0.0);
        things.put("kills_goblin_knife_thrower",0.0);
        things.put("kills_treasure_hoarder",0.0);
        things.put("kills_goblin",0.0);
        things.put("kills_crystal_sentry",0.0);
        things.put("kills_goblin_battler",0.0);
        things.put("kills_goblin_murderlover",0.0);
        things.put("kills_goblin_creepertamer",0.0);
        things.put("kills_goblin_golem",0.0);
        things.put("kills_creeper",0.0);
        things.put("kills_scarecrow",0.0);
        things.put("kills_catfish",0.0);
        things.put("kills_frosty_the_snowman",0.0);
        things.put("kills_frozen_steve",0.0);
        things.put("kills_liquid_hot_magma",0.0);
        things.put("kills_zombie_grunt",0.0);
        things.put("kills_crypt_lurker",0.0);
        things.put("kills_crypt_tank_zombie",0.0);
        things.put("kills_diamond_guy",0.0);
        things.put("kills_scared_skeleton",0.0);
        things.put("kills_dungeon_respawning_skeleton",0.0);
        things.put("kills_crypt_souleater",0.0);
        things.put("kills_sniper_skeleton",0.0);
        things.put("kills_lost_adventurer",0.0);
        things.put("kills_skeleton_grunt",0.0);
        things.put("kills_crypt_dreadlord",0.0);
        things.put("kills_watcher_summon_undead",0.0);
        things.put("kills_cellar_spider",0.0);
        things.put("kills_skeleton_soldier",0.0);
        things.put("kills_bonzo_summon_undead",0.0);
        things.put("kills_brood_mother_spider",0.0);
        things.put("kills_brood_mother_cave_spider",0.0);
        things.put("kills_arachne_brood",0.0);
        things.put("kills_generator_magma_cube",0.0);
        things.put("kills_powder_ghast",0.0);
        things.put("kills_arachne",0.0);
    }
    public static void getThings() {
        /**
         * Modified from Skytils under GNU Affero General Public license.
         * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
         * @author My-Name-Is-Jeff
         * @author Sychic
         */
        new Thread(() ->{
            try {
                String uuid = APIUtil.getUUID(Minecraft.getMinecraft().thePlayer.getName());
                String apiKey = SkyblockReinvented.config.apiKey;
                if (uuid == null || apiKey == null) {
                    return;
                }
                String latestProfile = APIUtil.getLatestProfileID(uuid, apiKey);
                if (latestProfile == null) {
                    return;
                }
                JsonObject profileResponse = APIUtil.getJSONResponse("https://api.hypixel.net/skyblock/profile?profile=" + latestProfile + "&key=" + apiKey);
                if (profileResponse == null) {
                    return;
                }
                if (!profileResponse.get("success").getAsBoolean()) {
                    String reason = profileResponse.get("cause").getAsString();
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Failed getting bestiary info with reason: " + reason));
                    return;
                }
                JsonObject playerObject = profileResponse.get("profile").getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject();
                if (!playerObject.has("stats")) {
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Failed getting stats with reason: No stats object."));
                    return;
                }
                JsonObject statsObject = playerObject.get("stats").getAsJsonObject();
                if (statsObject == null) {
                    return;
                }
                for (Map.Entry<String, Double> alsdkjf : things.entrySet()) {
                    if (things.containsKey(alsdkjf.getKey())) {
                        things.replace(alsdkjf.getKey(), Double.parseDouble(statsObject.get(alsdkjf.getKey()).toString().substring(statsObject.get(alsdkjf.getKey()).toString().indexOf(":") + 1)));
                    }
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }).start();
    }
    public Map<String, Double> getStats() {
        return things;
    }
    private static final int[] killsLvling = {10, 25, 75, 150, 250, 500, 1500, 2500, 5000, 15000, 25000, 50000, 100000};
    public static String getCorrectName(String name) {
        switch (name) {
            case "Crypt Ghoul":
                return "unburried_zombie";
            case "Zombie":
                return "zombie";
            case "Skeleton":
                return "skeleton";
            case "Spider":
                return "spider";
            case "Enderman":
                return "enderman";
            case "Witch":
                return "witch";
            case "Zombie Villager":
                return "zombie_villager";
            case "Wolf":
                return "ruin_wolf";
            case "Old Wolf":
                return "old_wolf";
            case "Dasher Spider":
                return "dasher_spider";
            case "Spider Jockey":
                return "spider_jockey";
            case "Weaver Spider":
                return "weaver_spider";
            case "Voracious Spider":
                return "voracious_spider";
            case "Splitter Spider":
                return "splitter_spider";
            case "Rain Slime":
                return "random_slime";
            case "Magma Cube":
                return "magma_cube";
            case "Blaze":
                return "blaze";
            case "Wither Skeleton":
                return "wither_skeleton";
            case "Pigman":
                return "pigman";
            case "Ghast":
                return "ghast";
            case "Obsidian Defender":
                return "obsidian_wither";
            case "Zealot":
                return "zealot_enderman";
            case "Endermite":
                return "endermite";
            case "Sneaky Creeper":
                return "invisible_creeper";
            case "Lapis Zombie":
                return "lapis_zombie";
            case "Redstone Pigman":
                return "redstone_pigman";
            case "Emerald Slime":
                return "emerald_slime";
            case "Miner Zombie":
                return "diamond_zombie";
            case "miner Skeleton":
                return "diamond_skeleton";
            case "Ghost":
                return "creeper"; // i think?
            case "Goblin":
                return "goblin";
            case "Treasure Hoarder":
                return "treasure_hoarder";
            case "Ice Walker":
                return "ice_walker";
            case "Pack Spirit":
                return "pack_spirit";
            case "Howling Spirit":
                return "howling_spirit";
            case "Soul of the Alpha":
                return "soul_of_the_alpha";
            case "Trick or Treater":
                return "trick_or_treater";
            case "Wither Gourd":
                return "wither_gourd";
            case "Phantom Spirit":
                return "phantom_spirit";
            case "Scary Jerry":
                return "scary_jerry";
            case "Wraith":
                return "wraith";
            case "Crazy Witch":
                return "batty_witch";
            case "watcher":
                return "watcher";
            default:
                return null;
        }
    }
    public static String updateKills(int kills) {
        String toReturn = "";
        for (int i : killsLvling) {
            if (!(kills - i >= 0)) {
                toReturn += EnumChatFormatting.RED + "Kills to next milestone: " + EnumChatFormatting.GOLD + "" + (i - kills);
                return toReturn;
            }
            kills -= i;
        }
        return toReturn;
    }
    @SubscribeEvent
    public void onEntityDeath(LivingDeathEvent event) {
        for (Entity e : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            if (e instanceof EntityArmorStand && e.getDistanceToEntity(event.entity) < 3) {
                try {
                    if (!e.getCustomNameTag().contains("§f/§a")) { return; }
                    if (e.getDistanceToEntity(Minecraft.getMinecraft().thePlayer) > 20) { return; }
                    secondsSinceKill = 0;
                    mobName = e.getCustomNameTag().substring(e.getCustomNameTag().indexOf(" §c") + 1);
                    mobName = mobName.substring(0, mobName.indexOf(" §a"));
                    mobName = StringUtils.stripControlCodes(mobName);
                    String currentKills = "kills_" + getCorrectName(mobName);
                    if (!things.containsKey(currentKills) || things.get(currentKills) == 0) {
                        current[0] = EnumChatFormatting.RED + "Current Bestiary: " + EnumChatFormatting.GOLD + mobName;
                        current[1] = EnumChatFormatting.RED + "Current Bestiary: " + EnumChatFormatting.GOLD + "Undetected or this is your first kill of the mob.";
                        return;
                    }
                    this.things.put(currentKills, this.things.get(currentKills) + 1);
                    int numKills = this.things.get(currentKills).intValue();
                    current[0] = EnumChatFormatting.RED + "Current Bestiary: " + EnumChatFormatting.GOLD + mobName;
                    current[1] = updateKills(numKills);
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    @SubscribeEvent
    public void second(SecondPassedEvent event) {
        secondsSinceKill++;
    }
}
