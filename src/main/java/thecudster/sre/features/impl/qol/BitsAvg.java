/*
 * SkyblockReinvented - Hypixel Skyblock Improvement Modification for Minecraft
 * Copyright (C) 2021 theCudster
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package thecudster.sre.features.impl.qol;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import thecudster.sre.util.JSONReader;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
/*
* API taken from Skytils under GNU Affero General Public License.
* https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
* @author My-Name-Is-Jeff
* @author Sychic
* Do I include Angry-Pineapple3121 and AzuredBlue? Who TF knows?
 */
public class BitsAvg {
    public static HashMap<String, Integer> listItems = new HashMap<String, Integer>();
    public static void addItems() {
        listItems.put("GOD_POTION_2", 1500);
        listItems.put("KAT_FLOWER", 500);
        listItems.put("HEAT_CORE", 3000);
        listItems.put("HYPER_CATALYST_UPGRADE", 300);
        listItems.put("ULTIMATE_CARROT_CANDY_UPGRADE", 8000);
        listItems.put("COLOSSAL_EXP_BOTTLE_UPGRADE", 4000);
        listItems.put("JUMBO_BACKPACK_UPGRADE", 4000);
        listItems.put("MINION_STORAGE_EXPANDER", 1500);
        listItems.put("HOLOGRAM", 2000);
        listItems.put("BUILDERS_WAND", 12000);
        listItems.put("BLOCK_ZAPPER", 5000);
        listItems.put("BITS_TALISMAN", 15000);
        listItems.put("AUTOPET_RULES_2", 21000);
        listItems.put("KISMET_FEATHER", 1350);
    }
    public static void listBestItems() throws IOException {
        URL url = new URL("https://sbe-stole-skytils.design/api/auctions/lowestbins");
        // Yes, I'm using Skytils' API. I have no idea how to use the API, but I do know how to add this feature.
            // If you have a problem, take it up with me in DM's before saying in public chat how I steal code and how I'm such a horrible person.
        JSONObject j = readJsonFromUrl(url);

            if (j == null) {
                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("asdfljk"));
            }
            else {
                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(j.toString()));
                for (Map.Entry<String, Integer> m : listItems.entrySet()) {
                    String item = m.getKey();
                    Integer bitsCost = m.getValue();
                    Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(j.get(item).toString()));
                }
            }
    }
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(URL url) throws IOException, JSONException {
        InputStream is = url.openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

}
