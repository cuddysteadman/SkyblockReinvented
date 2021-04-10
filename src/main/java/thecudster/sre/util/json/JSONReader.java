package thecudster.sre.util.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSONReader {

  private static final Logger LOGGER = LoggerFactory.getLogger(JSONReader.class);

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
        LOGGER.info("reading data from URL as GZIP Stream");
        in = new BufferedReader(new InputStreamReader(new GZIPInputStream(urlCon.getInputStream())));
      } else {
        LOGGER.info("reading data from URL as InputStream");
        in = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
      }
      String inputLine;
      sb = new StringBuilder();

      while ((inputLine = in.readLine()) != null) {
        sb.append(inputLine);
      }
      in.close();
    } catch (IOException e) {
      LOGGER.info("Exception while reading JSON from URL - {}", e);
    }
    if (sb != null) {
      try {
        System.out.println(sb.toString());
        return new JSONObject(sb.toString());
      } catch (JSONException e) {
        e.printStackTrace();
      }
    } else {
      LOGGER.warn("No JSON Found in given URL");
      try {
        return new JSONObject("");
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
    return null;
  }
}