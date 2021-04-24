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

package thecudster.sre.util.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONReader {


  public static JSONObject urlToJson(URL urlString) {
    StringBuilder sb = null;
    URL url;
    URLConnection urlCon;
    try {
      url = urlString;
      urlCon = url.openConnection();
      BufferedReader in = null;
      if (urlCon.getHeaderField("Content-Encoding") != null
          && urlCon.getHeaderField("Content-Encoding").equals("gzip")) {
        in = new BufferedReader(new InputStreamReader(new GZIPInputStream(urlCon.getInputStream())));
      } else {
        in = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
      }
      String inputLine;
      sb = new StringBuilder();

      while ((inputLine = in.readLine()) != null) {
        sb.append(inputLine);
      }
      in.close();
    } catch (IOException e) {
    }
    if (sb != null) {
      try {
        System.out.println(sb.toString());
        return new JSONObject(sb.toString());
      } catch (JSONException e) {
        e.printStackTrace();
      }
    } else {
      try {
        return new JSONObject("");
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
    return null;
  }
}