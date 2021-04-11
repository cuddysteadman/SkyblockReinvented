package thecudster.sre.util.sbutil;

import java.util.List;

public class CurrentLoc {
    public static String currentLoc = null;
    public static final String[] locList = {
            "Dungeon Hub",
            "None",
            "The Catacombs (F1)", // TODO: Rest of Dungeon Floors / Mastery Floors
            "Your Island",
            "Coal Mine",
            "Village",
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
            "Farm",
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
            "Barracks of Heroes"
    };
    public static void checkLoc() {
        List<String> scoreboard = ScoreboardUtil.getSidebarLines();
        for (String s : scoreboard) {
            s = ScoreboardUtil.cleanSB(s);
            for (String loc : locList) {
                if (s.contains(loc)) {
                    currentLoc = loc;
                }
            }
        }
    }
}
