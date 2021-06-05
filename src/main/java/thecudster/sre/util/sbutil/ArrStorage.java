package thecudster.sre.util.sbutil;

import java.util.HashMap;

public class ArrStorage {

    public static final String[] seaCreatureList = {"A squid appeared.",
            "You caught a Sea Walker.",
            "Pitch Darkness reveals you've caught a Night Squid.",
            "Frozen Steve fell into the pond long ago, never to resurface... until now!",
            "A tiny fin emerges from the water, you've caught a Nurse Shark.",
            "You stumbled upon a Sea Guardian.",
            "Its a Snowman! It looks harmless.",
            "Phew! It's only a scarecrow.",
            "It looks like you've disrupted the Sea Witch's brewing session. Watch out, she's furious!",
            "You spot a fin as blue as the water it came from, it's a Blue Shark.",
            "You reeled in a Sea Archer.",
            "The Monster of The Deep emerges from the dark depths...",
            "The Grinch stole Jerry's Gifts...get them back!",
            "Huh? A Catfish!",
            "You hear trotting from beneath the waves, you caught a Nightmare",
            "Is this even a Fish? It's the Carrot King!",
            "Gross! A Sea Leech!",
            "You've discovered a Guardian Defender of the sea.",
            "It must be a full moon, it's a Werewolf!",
            "A striped beast bounds from the depths, the wild Tiger Shark!",
            "You have awoken the Deep Sea Protector, prepare for a battle!",
            "The Water Hydra has come to test your Strength.",
            "The Sea Emperor arises from the depths...",
            "The spirit of a long lost Phantom Fisher has come to haunt you.",
            "Hide no longer, a Great White Shark has tracked your scent and thirsts for your blood!",
            "What is this creature!?",
            "This can't be! The manifestation of death himself!"};
    public static final String[] boneShieldList = {"Bone Shield Health: 66%",
            "Bone Shield Health: 33%",
            "Your Bone Shield gained an extra bone!",
            "Your Bone Shield gained an extra bone! It cannot gain anymore bones.",
            "Your Bone Shield was destroyed!"};
    public static final double[] revLeveling = {15.0, 200.0, 1000.0, 5000.0, 20000.0, 100000.0, 400000.0, 1000000.0};
    public static final double[] taraLeveling = {25.0, 200.0, 1000.0, 5000.0, 20000.0, 100000.0, 400000.0, 1000000.0};
    public static final double[] svenLeveling = {30.0, 250.0, 1500.0, 5000.0, 20000.0, 100000.0, 400000.0, 1000000.0};
    public static final String[] witherSkullList = {"You need a Wither Key to open this door!",
            "That's a door, and I keep the door...there.",
            "If you didn't find the Wither Key yet, it probably means you need to go back!",
            "Death awaits you on this path, and everywhere else to be fair.",
            "Monsters with a star next to their name have Wither Keys...sometimes."
    };
    public static final String[] maddoxFails = {
            "Please leave your message after the beep.",
            "How can you tell if a bee is on the phone? You get a buzzy signal!",
            "The phone keeps ringing, is it broken?",
            "The phone picks up but it immediately hangs up!",
            "What did the cat say on the phone? Can you hear meow?",
            "No answer.",
            "Seems like it's not picking up!",
            "\"Your call is important to us, please stay on the line\", so you hang up.",
            "HEY IT'S NOT PICKING UP STOP TRYING!",
            "The line is dead."
    };
    public static final String[] watcherQuotes = {"You have failed to prove yourself, and have paid with your lives. You will make an excellent addition to my collection.",
            "You have proven yourself. You may pass.",
            "A shiver runs down your spine...",
            "I am not your enemy",
            "Stop Attacking me",
            "Don't make me zap you",
            "Ouch, just kidding",
            "We're always watching. Come down from there!",
            "Don't try to sneak anything past my Watchful Eyes. They see you up there!",
            "My Watchful Eyes see you up there! Come down and fight!",
            "A Wandering Soul? Poor Guy.",
            "A Wandering Soul? Don't you guys have a Healer?",
            "Oof.",
            "Oops. Wasn't meant to revive that one.",
            "This one looks like a fighter.",
            "You'll do",
            "Let's see how you can handle this",
            "Go, fight!",
            "Go and live again!",
            "Hmm... This one!",
            "That one was weak anyway.",
            "I'm Impressed.",
            "Not bad.",
            "Very nice.",
            "Aw, I liked that one.",
            "Welcome back to the realm of the living, ",
            "So your name is Sadan, I see...",
            "Ah, you've finally arrived. I have watching you closely since we last met. ",
            "I don't know if you are ready for what's behind this door. So I will decide if you are strong enough.",
            "Ah, we meet again... I have done some experiments to develop new abilities for my Skulls. Let's see how you handle this!",
            "So you made it this far... interesting. You are much stronger than I was expecting. Not to worry,",
            "I recently added a very fine piece to my collection!",
            "You've managed to scratch and claw your way here, eh? Don't even think about trying to outwit me this time!",
            "My Watchful Eyes are keeping their ...eyes... on you!",
            "I'm starting to get tired of seeing you around here... This time I've imbued my minions with special properties! These will",
            "Oh... hello? You've arrived too early, I haven't even set up... Anyway, let's fight... I guess",
            "Things feel a little more roomy now, eh? I've knocked down those pillars to go for a more... open concept.",
            "Plus I needed to give my new friends some space to roam..."
    };
    public static final String[] cleanSlayer = {
            "Open chat then click anywhere on-screen to open Maddox",
            "SLAYER QUEST STARTED!",
            "Ring...",
            "NICE! SLAYER BOSS SLAIN!",
            "SLAYER QUEST COMPLETE"
    };
    public static final String[] cleanJacob = {
            "[NPC] Jacob: The Farming Contest is over!",
            "[NPC] Jacob: Let me count the final results eh?",
            "[NPC] Jacob: Come see me in the Hub to claim your reward!",
            "[NPC] Jacob: You scored"
    };
    public static final String[] hubWarnings = {
            "Couldn't warp you!",
            "This island has had too many recent visits!",
            "This ability is disabled while guesting!",
            "Finding player...",
            "Sending a visit request...",
            "You can't fast travel while in combat!",
            "Warping...",
            "Request join for",
            "Warping you to"
    };
    public static final String[] cleanJerry = {
            "[NPC] Baker:",
            "You claimed New Year Cake!",
            "You feel your Gift Compass pull towards a new location...",
            "You have already found this Gift this year!",
            "mounted a Snow Cannon!",
            "GIFT! You found a White Gift!",
            "It doesn't seem there are any unopened presents nearby...",
            "§e[NPC] §cSt. Jerry§f: Take this §aGreen Gift§r! You'll hopefully find something nicer than what was in those White Gifts!",
            "§e[NPC] §cSt. Jerry§f: If you haven't already, be sure to give your other Gifts away to others. Giving Gifts benefits both you and the receiver!",
            "You claimed Green Gift!",
            "You found all of the Gifts!",
            "to shoot. Move cursor to aim.",
            "The Snow Cannon is reloading!",
            "You dismounted the Snow Cannon!"
    };
    public static final String[] warningServers = {
            "You are trying to do that too fast. Try again in a moment.",
            "There was a problem joining SkyBlock, try again in a moment!",
            "Oops! Couldn't find a SkyBlock server for you! Try again later!",
            "There was an error queuing into SkyBlock!",
            "Try again in a moment!",
            "Please don't spam the command!",
            "Oops! Couldn't find a SkyBlock server for you! Try again later!"
    };
    public static final String[] threeWeirdosIncorrect = {"One of us is telling the truth!",
            "They are both telling the truth. The reward isn't in",
            "We are all telling the truth!",
            "is telling the truth and the reward is",
            "The reward isn't in any of our chests.",
            "My chest doesn't have the reward. At least one of the others is telling the truth!",
            "One of the others is lying!",
            "They are both telling the truth, the reward is in",
            "They are both lying, the reward is in my chest!",
            "The reward is in my chest!",
            "The reward is not in my chest. They are both lying.",
            "My chest has the reward!",
            "is telling the truth."
    };
    public static final String[] threeWeirdosSolutions = {
            "My chest doesn't have the reward. We are all telling the truth",
            "At least one of them is lying, and the reward is not in",
            "My chest has the reward and I'm telling the truth",
            "The reward is not in my chest!",
            "Both of them are telling the truth.",
            "The reward isn't in any of our chests"
    };
    public static final String[] dumbSlayerDrops = {
            "Pestilence Rune I",
            "Undead Catalyst",
            "Smite VI",
            "Beheaded Horror",
            "Revenant Catalyst",
            "Snake Rune",
            "Bite Rune I",
            "Spider Catalyst",
            "Bane of Arthropods VI",
            "Fly Swatter",
            "Spirit Rune I",
            "Critical VI",
            "Red Claw Egg",
            "Couture Rune I",
            "Grizzly Bait"
    };
    public static final String[] cleanEndDungeon = {
            "                          The Catacombs - Floor I",
            "                          The Catacombs - Floor II",
            "                          The Catacombs - Floor III",
            "                          The Catacombs - Floor IV",
            "                          The Catacombs - Floor V",
            "                          The Catacombs - Floor VI",
            "                          The Catacombs - Floor VII",
            "                          Master Mode Catacombs - Floor I",
            "                          Master Mode Catacombs - Floor II",
            "                          Master Mode Catacombs - Floor III",
            "                          Master Mode Catacombs - Floor IV",
            "                          Master Mode Catacombs - Floor V",
            "                          Master Mode Catacombs - Floor VI",
            "                          Master Mode Catacombs - Floor VII",
            "Experience (Team Bonus)",
            "§6> §e§lEXTRA STATS §6<",
            "Defeated Bonzo in ",
            "Defeated Scarf in ",
            "Defeated The Professor in ",
            "Defeated Thorn in ",
            "Defeated Livid in ",
            "Defeated Sadan in ",
            "Defeated Necron in "
    };
    public static final String[] tooFast = {
            "You are sending commands too fast",
            "You're clicking too fast"
    };
    public static final String[] doubleMsg = {
            "stats are doubled because you are the only player using this class!",
            "[Archer]",
            "[Mage]",
            "[Tank]",
            "[Berserk]",
            "[Healer]"
    };
    public static final String[] healerMsg = {
            "You formed a tether with",
            "Your fairy healed yourself for"
    };
    public static final String[] dungeonFinder = {
            "This group is full and has been de-listed!",
            "Attempting to add you to the party...",
            "Please wait a few seconds between refreshing!",
            "Refreshing...",
            "You are already in a party!",
            "This group is full!"
    };
    public static final String[] experimentationTable = {
            "You removed a Experimentation Table. (",
            "You placed a Experimentation Table. ("
    };
    public static final String[] profileMsg = {
            "You are playing on profile",
            "witching to profile",
            "our profile was changed to: ",
            "is the profile you are playing on!",
            "f you want to delete this profile, switch to another one first!"
    };
    public static final String[] slowDown = {
            "Whow! Slow down there!",
            "Slow down"
    };
    public static final String[] fetchurQuotes = {
            "come back another time, maybe tmrw",
            "hi i need your help maybe",
            "im looking for some stuff, dont remember the name tbh",
            "you didnt find my things yet?",
            "thanks thats probably what i needed",
            "You received these rewards",
            "20,000 Coins",
            "1,000 Mithril Powder",
            "take some gifts!"
    };
    public static final String[] villagerNames = {
            "Andrew",
            "Jack",
            "Jamie",
            "Tom",
            "Leo",
            "Felix",
            "Ryu",
            "Duke",
            "Lynn",
            "Stella",
            "Vex",
            "Liam"
    };
    public static final String[] whitelist = {
            "Blacksmith",
            "St. Jerry"
    };
    public static final String[] hubLocs = {
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
            "Village"
    };
    public static final String[] svenLocs = {"Ruins", "Howling Cave"};
    public static final String[] taraLocs = {"Spider's Den"};
    public static final String[] revLocs = {"Graveyard", "Coal Mine"};
    public static final String[] slayerLocs = {"Graveyard", "Coal Mine", "Spider's Den", "Ruins", "Howling Cave"};
    public static final String[] blockedNames = {
            "Revive Stone",
            "Zombie Soldier Cutlass",
            "Skeleton Grunt",
            "Water Bottle",
            "Enchanted Rotten Flesh",
            "Spirit Leap",
            "Machine Gun Bow",
            "Dreadlord Sword",
            "Rotten ",
            "Journal Entry",
            "Training Weights",
            "Heavy"
    };
    public static final String[] dwarvenLocs = {
            "The Forge",
            "Forge Basin",
            "Palace Bridge",
            "Royal Palace",
            "Dwarven Mines",
            "Aristocrat Passage",
            "Hanging Court",
            "Divan's Gateway",
            "Far Reserve",
            "Goblin Burrows",
            "Miner's Guild",
            "Great Ice Wall",
            "The Mist",
            "C&C Minecarts Co.",
            "Grand Library",
            "Barracks of Heroes",
            "Dwarven Village",
            "The Lift",
            "Royal Quarters",
            "Lava Springs",
            "Cliffside Veins",
            "Rampart's Quarry",
            "Upper Mines",
            "Royal Mines"
    };
    public static String[] renderWhitelist = {
            "Goblin",
            "Ice Walker",
            "Weakling",
            "Fireslinger",
            "Creeperlobber",
            "Pitfighter",
            "Murderlover",
            "Liquid Hot Magma"
    };
    /**
     * Taken & transformed to HashMap from Danker's Skyblock Mod under GPL 3.0 license
     * https://github.com/bowser0000/SkyblockMod/blob/master/LICENSE
     * @author bowser0000
     */
    public static HashMap<Integer, Integer> skillXPPerLevel = new HashMap<>();
    public static void init() {
        skillXPPerLevel.put(0, 0);
        skillXPPerLevel.put(1, 50);
        skillXPPerLevel.put(2, 125);
        skillXPPerLevel.put(3, 200);
        skillXPPerLevel.put(4, 300);
        skillXPPerLevel.put(5, 500);
        skillXPPerLevel.put(6, 750);
        skillXPPerLevel.put(7, 1000);
        skillXPPerLevel.put(8, 1500);
        skillXPPerLevel.put(9, 2000);
        skillXPPerLevel.put(10, 3500);
        skillXPPerLevel.put(11, 5000);
        skillXPPerLevel.put(12, 7500);
        skillXPPerLevel.put(13, 10000);
        skillXPPerLevel.put(14, 15000);
        skillXPPerLevel.put(15, 20000);
        skillXPPerLevel.put(16, 30000);
        skillXPPerLevel.put(17, 50000);
        skillXPPerLevel.put(18, 75000);
        skillXPPerLevel.put(19, 100000);
        skillXPPerLevel.put(20, 200000);
        skillXPPerLevel.put(21, 300000);
        skillXPPerLevel.put(22, 400000);
        skillXPPerLevel.put(23,  500000);
        skillXPPerLevel.put(24, 600000);
        skillXPPerLevel.put(25, 700000);
        skillXPPerLevel.put(26, 800000);
        skillXPPerLevel.put(27, 900000);
        skillXPPerLevel.put(28, 1000000);
        skillXPPerLevel.put(29, 1100000);
        skillXPPerLevel.put(30, 1200000);
        skillXPPerLevel.put(31, 1300000);
        skillXPPerLevel.put(32, 1400000);
        skillXPPerLevel.put(33, 1500000);
        skillXPPerLevel.put(34, 1600000);
        skillXPPerLevel.put(35, 1700000);
        skillXPPerLevel.put(36, 1800000);
        skillXPPerLevel.put(37, 1900000);
        skillXPPerLevel.put(38, 2000000);
        skillXPPerLevel.put(39, 2100000);
        skillXPPerLevel.put(40, 2200000);
        skillXPPerLevel.put(41, 2300000);
        skillXPPerLevel.put(42, 2400000);
        skillXPPerLevel.put(43, 2500000);
        skillXPPerLevel.put(44, 2600000);
        skillXPPerLevel.put(45, 2750000);
        skillXPPerLevel.put(46, 2900000);
        skillXPPerLevel.put(47, 3100000);
        skillXPPerLevel.put(48, 3400000);
        skillXPPerLevel.put(49, 3700000);
        skillXPPerLevel.put(50, 4000000);
        skillXPPerLevel.put(51, 4300000);
        skillXPPerLevel.put(52, 4600000);
        skillXPPerLevel.put(53, 4900000);
        skillXPPerLevel.put(54, 5200000);
        skillXPPerLevel.put(55, 5500000);
        skillXPPerLevel.put(56, 5800000);
        skillXPPerLevel.put(57, 6100000);
        skillXPPerLevel.put(58, 6400000);
        skillXPPerLevel.put(59, 6700000);
        skillXPPerLevel.put(60, 7000000);
    }
}
