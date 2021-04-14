package thecudster.sre.features.impl.qol;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.kbrewster.API;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import scala.util.parsing.json.JSON;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.api.APIUtil;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

public class BestiaryProgress {
    LinkedHashMap<String, Double> things = new LinkedHashMap<String, Double>();
    public void placeItems() {
        things.put("kills_invisible_creeper", new Double(0.0));
        things.put("kills_diamond_zombie", new Double(0.0));
        things.put("kills_diamond_skeleton", new Double(0.0));
        things.put("kills_lapis_zombie", new Double(0.0));
        things.put("kills_zombie", new Double(0.0));
        things.put("kills_skeleton", new Double(0.0));
        things.put("kills_spider", new Double(0.0));
        things.put("kills_redstone_pigman",new Double(0.0));
        things.put("kills_enderman",new Double(0.0)); // i thought this would work but then realized it didn't matter, too lazy to revert
        things.put("kills_zombie_villager",0.0);
        things.put("kills_wither_skeleton",0.0);
        things.put("kills_blaze",0.0);
        things.put("kills_emerald_slime",0.0);
        things.put("kills_cow",0.0);
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
    public void getThings() {
        /**
         * Modified from Skytils under GNU Affero General Public license.
         * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
         * @author My-Name-Is-Jeff
         * @author Sychic
         */
        try {
            String uuid = "still working on it!";
            String apiKey = "still working on it!";
            String latestProfile = APIUtil.getLatestProfileID(uuid, apiKey);
            JsonObject profileResponse = APIUtil.getJSONResponse("https://api.hypixel.net/skyblock/profile?profile=" + latestProfile + "&key=" + apiKey);
            JsonObject stats = (JsonObject) profileResponse.get("stats");
            for (Map.Entry<String, Double> alsdkjf : things.entrySet()) {
                things.replace(alsdkjf.getKey(), Double.parseDouble(stats.get(alsdkjf.getKey()).toString().substring(stats.get(alsdkjf.getKey()).toString().indexOf(":") + 1)));
            }
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    public Map<String, Double> getStats() {
        return things;
    }
}
