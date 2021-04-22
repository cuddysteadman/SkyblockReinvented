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