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

package thecudster.sre.util.sbutil;

import java.util.List;

public class CurrentLoc {
    public static String currentLoc = null;
    public static final String[] locList = {
            "Dungeon Hub",
            "None",
            "The Catacombs (F1)",
            "The Catacombs (F2)",
            "The Catacombs (F3)",
            "The Catacombs (F4)",
            "The Catacombs (F5)",
            "The Catacombs (F6)",
            "The Catacombs (F7)",
            "The Catacombs (M1)",
            "The Catacombs (M2)",
            "The Catacombs (M3)",
            "The Catacombs (M4)",
            "The Catacombs (M5)",
            "The Catacombs (M6)",
            "The Catacombs (M7)",
            "Your Island",
            "Coal Mine",
            "Private Island",
            "Forest",
            "Bank",
            "Library",
            "Auction House",
            "Flower House",
            "Bazaar Alley",
            "Community Center",
            "Builder's House",
            "The Park",
            "Birch Park",
            "Howling Cave",
            "Spruce Woods",
            "Dark Thicket",
            "Savanna Woodland",
            "Jungle Island",
            "Graveyard",
            "Spider's Den",
            "Blazing Fortress",
            "The End",
            "Gold Mine",
            "Deep Caverns",
            "Gunpowder Mines",
            "Lapis Quarry",
            "Pigmen's Den",
            "Slimehill",
            "Diamond Reserve",
            "Obsidian Sanctuary",
            "Dwarven Mines",
            "Dwarven Village",
            "Village",
            "Farm",
            "The Lift",
            "The Barn",
            "Mushroom Desert",
            "Mountain",
            "Wilderness",
            "Ruins",
            "High Level",
            "Dark Auction",
            "Winter Island",
            "Limbo",
            "Jerry's Workshop",
            "Jerry Pond",
            "Snowball Fight Cave",
            "The Forge",
            "Forge Basin",
            "Lava Springs",
            "Palace Bridge",
            "Royal Palace",
            "Aristocrat Passage",
            "Hanging Court",
            "Cliffside Veins",
            "Rampart's Quarry",
            "Divan's Gateway",
            "Far Reserve",
            "Goblins' Burrows",
            "Upper Mines",
            "Miner's Guild",
            "Great Ice Wall",
            "The Mist",
            "C&C Minecarts Co.",
            "Grand Library",
            "Barracks of Heroes",
            "Dragon's Nest"
    };
    public static void checkLoc() {
        List<String> scoreboard = ScoreboardUtil.getSidebarLines();
        boolean found = false;
        for (String s : scoreboard) {
            String cleaned = ScoreboardUtil.cleanSB(s);
            for (String loc : locList) {
                if (cleaned.contains(loc)) {
                    currentLoc = loc;
                    found = true;
                }
            }
            if (!found) {
                currentLoc = "Unknown";
            }
        }
    }
}
