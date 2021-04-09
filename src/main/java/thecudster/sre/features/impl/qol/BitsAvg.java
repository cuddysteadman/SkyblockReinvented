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
