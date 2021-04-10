package thecudster.sre.util;

import java.net.URLConnection;

import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import org.json.JSONException;
import org.json.JSONObject;
import java.net.URL;
/*
* Taken from Fragrunner under some license (?)
* @author byBackfish
 */
public class JSONReader
{

    public static JSONObject urlToJson(final URL urlString) {
        StringBuilder sb = null;
        try {
            final URL url = urlString;
            final URLConnection urlCon = url.openConnection();
            BufferedReader in = null;
            if (urlCon.getHeaderField("Content-Encoding") != null && urlCon.getHeaderField("Content-Encoding").equals("gzip")) {
                in = new BufferedReader(new InputStreamReader(new GZIPInputStream(urlCon.getInputStream())));
            }
            else {
                in = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
            }
            sb = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
            in.close();
        }
        catch (IOException e) {
        }
        if (sb != null) {
            try {
                System.out.println(sb.toString());
                return new JSONObject(sb.toString());
            }
            catch (JSONException e2) {
                e2.printStackTrace();
                return null;
            }
        }
        try {
            return new JSONObject("");
        }
        catch (JSONException e2) {
            e2.printStackTrace();
        }
        return null;
    }

    static {
    }
}

