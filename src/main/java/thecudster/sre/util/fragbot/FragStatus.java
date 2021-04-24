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

package thecudster.sre.util.fragbot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import thecudster.sre.util.json.JSONReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class FragStatus {

  public static List<FragBot> getBots() throws MalformedURLException, JSONException {

    List<FragBot> bots = new ArrayList<>();

    JSONObject object = JSONReader.urlToJson(new URL("https://fragrunner.me:3001/fragbots"));
    System.out.println(object);

    JSONArray jsonArray = object.getJSONArray("bots");
    for(int i = 0; i < jsonArray.length(); i++){
      JSONObject bot = jsonArray.getJSONObject(i);
      JSONArray array = bot.getJSONArray("queue");
      List<String> queue = new ArrayList<>();
      for(int a = 0; a < array.length(); a++) {
        queue.add(array.toString());
      }
      bots.add(
          new FragBot().setType("group").setStatus(bot.getString("status")).setUsername(bot.getString("name")).setUuid(bot.getString("uuid")).setQueue(queue)
      );
      System.out.println("Bots List: ");
      bots.forEach((botObj) -> System.out.println(botObj.getUsername()  + " : " + botObj.getType() + " : " + botObj.getStatus()));
    }
    return bots;
  }

  public static List<FragBot> getBestBot(int amount) throws MalformedURLException, JSONException {
    List<FragBot> bots = getBots();
    bots = bots.stream().filter(bot -> bot.getStatus().equals("online")).collect(Collectors.toList());
    if(bots.size() == 0) return new ArrayList<>();
    if(amount > bots.size()){
      amount = bots.size();
    }
    List<FragBot> out = new ArrayList<>();
    for(int i = 0; i < amount; i++){
      int random = new Random().nextInt(bots.size());
      FragBot bot = bots.get(random);
      if(out.contains(bot)){
        i--;
        continue;
      }
      out.add(bot);
    }
    return out;
  }
}