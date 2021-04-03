package thecudster.sre.features.impl.qol;

import java.util.HashMap;
import java.util.Map;

public class BitsAvg {
    public static HashMap<String, Integer> listItems = new HashMap<String, Integer>();
    public static void addItems() {
        listItems.put("God Potion", 1500);
        listItems.put("Kat Flower", 500);
        listItems.put("Heat Core", 3000);
        listItems.put("Hyper Catalyst Upgrade", 300);
        listItems.put("Ultimate Carrot Candy Upgrade", 8000);
        listItems.put("Colossal Experience Bottle Upgrade", 4000);
        listItems.put("Jumbo Backpack Upgrade", 4000);
        listItems.put("Minion Storage X-pender", 1500);

    }
    public static void listBestItems() {
        for (Map.Entry<String, Integer> entry : listItems.entrySet()) {
            String item = entry.getKey();
            Integer value = entry.getValue();
        }
    }

}
