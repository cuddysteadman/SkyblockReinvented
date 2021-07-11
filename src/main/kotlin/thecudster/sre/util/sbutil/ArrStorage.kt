package thecudster.sre.util.sbutil

class ArrStorage {
    companion object {
        @JvmField
        val revLeveling = doubleArrayOf(15.0, 200.0, 1000.0, 5000.0, 20000.0, 100000.0, 400000.0, 1000000.0)

        @JvmField
        val taraLeveling = doubleArrayOf(25.0, 200.0, 1000.0, 5000.0, 20000.0, 100000.0, 400000.0, 1000000.0)

        @JvmField
        val svenLeveling = doubleArrayOf(30.0, 250.0, 1500.0, 5000.0, 20000.0, 100000.0, 400000.0, 1000000.0)

        @JvmField
        val hubLocs = arrayOf(
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
        )

        @JvmField
        val svenLocs = arrayOf("Ruins", "Howling Cave")
        val taraLocs = arrayOf("Spider's Den")
        val revLocs = arrayOf("Graveyard", "Coal Mine")

        @JvmField
        val slayerLocs = arrayOf("Graveyard", "Coal Mine", "Spider's Den", "Ruins", "Howling Cave")

        @JvmField
        val dwarvenLocs = arrayOf(
            "The Forge",
            "Royal Mines",
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
            "Dwarven Mines",
            "The Lift",
            "Royal Quarters",
            "Lava Springs",
            "Cliffside Veins",
            "Rampart's Quarry",
            "Upper Mines"
        )
        val dungeonLocs = arrayOf(
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
            "The Catacombs (M7)"
        )

        @JvmField
        val renderWhitelist = arrayOf(
            "Goblin",
            "Ice Walker",
            "Weakling",
            "Fireslinger",
            "Creeperlobber",
            "Pitfighter",
            "Murderlover",
            "Liquid Hot Magma",
            "Minotaur",
            "Star Sentry"
        )

        /**
         * Taken & transformed to HashMap from Danker's Skyblock Mod under GPL 3.0 license
         * https://github.com/bowser0000/SkyblockMod/blob/master/LICENSE
         * @author bowser0000
         */
        @JvmField
        var skillXPPerLevel = hashMapOf(0 to 0, 1 to 50, 2 to 125, 3 to 200, 4 to 300, 5 to 500, 6 to 750, 7 to 1000, 8 to 1500, 9 to 2000, 10 to 3500, 11 to 5000, 12 to 7500,
            13 to 10000, 14 to 15000, 15 to 20000, 16 to 30000, 17 to 50000, 18 to 75000, 19 to 100000, 20 to 200000, 21 to 300000, 22 to 400000, 23 to 500000, 24 to 600000,
            25 to 700000, 26 to 800000, 27 to 900000, 28 to 1000000, 29 to 1100000, 30 to 1200000, 31 to 1300000, 32 to 1400000, 33 to 1500000, 34 to 1600000, 35 to 1700000,
            36 to 1800000, 37 to 1900000, 38 to 2000000, 39 to 2100000, 40 to 2200000, 41 to 2300000, 42 to 2400000, 43 to 2500000, 44 to 2600000, 45 to 2750000, 46 to 2900000,
            47 to 3100000, 48 to 3400000, 49 to 3700000, 50 to 4000000, 51 to 4300000, 52 to 4600000, 53 to 4900000, 54 to 5200000, 55 to 5500000, 56 to 5800000, 57 to 6100000,
            58 to 6400000, 59 to 6700000, 60 to 7000000
        )
    }
    class SpamArrays {
        companion object {
            @JvmField
            val blockedNames = arrayOf(
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
            )

            @JvmField
            val dumbSlayerDrops = arrayOf(
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
            )

            @JvmField
            val cleanEndDungeon = arrayOf(
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
            )

            @JvmField
            val tooFast = arrayOf(
                "You are sending commands too fast",
                "You're clicking too fast"
            )

            @JvmField
            val doubleMsg = arrayOf(
                "stats are doubled because you are the only player using this class!",
                "[Archer]",
                "[Mage]",
                "[Tank]",
                "[Berserk]",
                "[Healer]"
            )

            @JvmField
            val healerMsg = arrayOf(
                "You formed a tether with",
                "Your fairy healed yourself for"
            )

            @JvmField
            val dungeonFinder = arrayOf(
                "This group is full and has been de-listed!",
                "Attempting to add you to the party...",
                "Please wait a few seconds between refreshing!",
                "Refreshing...",
                "You are already in a party!",
                "This group is full!"
            )

            @JvmField
            val experimentationTable = arrayOf(
                "You removed a Experimentation Table. (",
                "You placed a Experimentation Table. ("
            )

            @JvmField
            val profileMsg = arrayOf(
                "You are playing on profile",
                "Switching to profile",
                "our profile was changed to: ",
                "is the profile you are playing on!",
                "f you want to delete this profile, switch to another one first!"
            )

            @JvmField
            val slowDown = arrayOf(
                "Whow! Slow down there!",
                "Slow down"
            )

            @JvmField
            val fetchurQuotes = arrayOf(
                "come back another time, maybe tmrw",
                "hi i need your help maybe",
                "im looking for some stuff, dont remember the name tbh",
                "you didnt find my things yet?",
                "thanks thats probably what i needed",
                "You received these rewards",
                "20,000 Coins",
                "1,000 Mithril Powder",
                "take some gifts!"
            )

            @JvmField
            val witherSkullList = arrayOf(
                "You need a Wither Key to open this door!",
                "That's a door, and I keep the door...there.",
                "If you didn't find the Wither Key yet, it probably means you need to go back!",
                "Death awaits you on this path, and everywhere else to be fair.",
                "Monsters with a star next to their name have Wither Keys...sometimes."
            )

            @JvmField
            val maddoxFails = arrayOf(
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
            )

            @JvmField
            val watcherQuotes = arrayOf(
                "You have failed to prove yourself, and have paid with your lives. You will make an excellent addition to my collection.",
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
            )

            @JvmField
            val cleanSlayer = arrayOf(
                "Open chat then click anywhere on-screen to open Maddox",
                "SLAYER QUEST STARTED!",
                "Ring...",
                "NICE! SLAYER BOSS SLAIN!",
                "SLAYER QUEST COMPLETE"
            )

            @JvmField
            val cleanJacob = arrayOf(
                "[NPC] Jacob: The Farming Contest is over!",
                "[NPC] Jacob: Let me count the final results eh?",
                "[NPC] Jacob: Come see me in the Hub to claim your reward!",
                "[NPC] Jacob: You scored"
            )

            @JvmField
            val hubWarnings = arrayOf(
                "Couldn't warp you!",
                "This island has had too many recent visits!",
                "This ability is disabled while guesting!",
                "Finding player...",
                "Sending a visit request...",
                "You can't fast travel while in combat!",
                "arping...",
                "Request join for",
                "Warping you to"
            )

            @JvmField
            val cleanJerry = arrayOf(
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
            )

            @JvmField
            val warningServers = arrayOf(
                "You are trying to do that too fast. Try again in a moment.",
                "There was a problem joining SkyBlock, try again in a moment!",
                "Oops! Couldn't find a SkyBlock server for you! Try again later!",
                "There was an error queuing into SkyBlock!",
                "Try again in a moment!",
                "Please don't spam the command!",
                "Oops! Couldn't find a SkyBlock server for you! Try again later!"
            )

            @JvmField
            val threeWeirdosIncorrect = arrayOf(
                "One of us is telling the truth!",
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
            )

            @JvmField
            val threeWeirdosSolutions = arrayOf(
                "My chest doesn't have the reward. We are all telling the truth",
                "At least one of them is lying, and the reward is not in",
                "My chest has the reward and I'm telling the truth",
                "The reward is not in my chest!",
                "Both of them are telling the truth.",
                "The reward isn't in any of our chests"
            )

            @JvmField
            val boneShieldList = arrayOf(
                "Bone Shield Health: 66%",
                "Bone Shield Health: 33%",
                "Your Bone Shield gained an extra bone!",
                "Your Bone Shield gained an extra bone! It cannot gain anymore bones.",
                "Your Bone Shield was destroyed!"
            )
        }
    }
}