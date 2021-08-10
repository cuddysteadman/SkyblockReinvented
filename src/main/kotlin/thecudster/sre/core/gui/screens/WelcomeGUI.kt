package thecudster.sre.core.gui.screens

import gg.essential.elementa.WindowScreen
import gg.essential.elementa.components.ScrollComponent
import gg.essential.elementa.components.UIBlock
import gg.essential.elementa.constraints.CenterConstraint
import gg.essential.elementa.constraints.RelativeConstraint
import gg.essential.elementa.constraints.SiblingConstraint
import gg.essential.elementa.dsl.*
import gg.essential.elementa.markdown.MarkdownComponent
import gg.essential.elementa.utils.roundToRealPixels
import java.awt.Color

class WelcomeGUI : WindowScreen() {
    init {
        MarkdownComponent("""
            # Welcome to SkyblockReinvented!
            Thanks for downloading the mod! Here's a quick guide to the commands, features, and main GUI:
            ## Commands
            ### Settings
            * /sre: open the main GUI
              * /sre gui: open GUI editing screen (alias: editlocations)
              * /sre config: open config
              * /sre vigilance: open Vigilance (config settings) editing screen
              * /sre discord: join our discord!
              * /sre github: view the source code for SRE on GitHub!
            ### Features
            * /discord {status}: sets your custom status in discord (if you are using that setting)
              * (Aliases: /da, /disc, /discset, /dset, /rp, /rpset)
            * /re: toggle whether to render players
              * (Aliases: /render)
            * /drag: toggles the dragon tracker GUI element
              * (Aliases: /dr, /drags)
            * /drag clear: clears current dragon tracker info
              * (Aliases: /dr clear, /drags clear)
            ### Dungeons
            * /fragrun: parties an online fragrunning bot
              * (Aliases: /fr, /frags)
            ## Features
            ### Items
            * Remove Wither Cloak Creepers
            * Overlay Mobs in Range of Hyperion
            #### Sounds
            * Remove Creeper Sounds from Veil
           
            ### General
            #### Misc
            * Hide Ironman in Scoreboard
            * Pickup Stash Keybind / Reminder - reminds you to pickup your stash (and customizable keybind)
            * Dark Auction Reminder
            * Dragon Tracker
            * Discord Rich Presence (Support for Dwarven Mines, dungeons, and custom messages!)
            * Treasure Hunter Waypoints / Solver
            * Show Cake Year as stack size
            * Skyblock Updates - sends message in chat when skyblock updates
            #### QOL
            * Automatically join Skyblock (after mouse input - use at your own risk but should be ok according to newish rules)
            #### Rendering
            * Stop Rendering Players w/ customizable whitelist
            * Stop Rendering Player Armour - either just skulls or all armour
            * Create Hitboxes Around your Arrows
            * Hide Jerry-Chine Heads
            * Show Hitboxes of Special Zealots
            * Show Hitboxes of Drags & golems
            * Hide Other People’s Arrows (while in the End)
            * Stop Power Orb Nametags / particles from rendering
            ### Your Island
            #### General
            * Shorten / Remove Teleport Pad Names
            #### Misc
            * Disable Farm Block Breaking Particles
            * Remove Item Frame Names
            ### Dungeons
            #### Misc
            * Stop Opening Chests - Stops you from opening a chest unless you click a certain amount of times.
            * Clean Ending of Dungeons - Cleans chat at the end of a dungeon.
            * Bonzo Mask Alert
            * Hide Guided Sheeps
            * Watcher Ready Reminder
            #### QOL
            * Remove Guardians in Creeper Solver
            * Dungeon Floor Lock - Only allow you to join a certain floor of dungeons
            #### Rendering
            * Remind Skeleton Masters - plays a sound when you are near skeleton masters
            * Remind Bat Secrets - plays a sound when you are near a bat secret
            * Outline Hitboxes of Starred Mobs
            * Hide Spirit Bats
            ### Skills
            #### Slayer
            * Slayer Info
              * XP Until Next Level
              * RNGesus Meter
              * Current Slayer
              * 2 modes: progress bars or text
            * Remove Sven Pups
            * Maddox Clickable Message (substitute for DSM to work with slayer spam hider)
            * Reminder to Start New Slayer
            #### Enchanting
            * Exit Mode Button on Experiments
            #### Farming
            * Disable Farm Block Particles
            #### Bestiary
            * Bestiary Info & Tracker
            ### Dwarven Mines
            #### Alerts
            * Boost Ready Alert
            * Boost Expired Alert
            * Remove Mining Speed Boost Ready
            * Remove Mining Speed Boost Used
            * Remove Mining Speed Boost Expired
            #### Combat
            * Ghost Loot Tracker
            #### General
            * Auto /garry
            * Fetchur Solver or Spam Hider
            * Golden Goblin Alert
            * Hide Mithril Powder in Scoreboard
            #### Rendering
            * Remove Ghost Titles
            * Remove Titles from Raffles
            ### Hub
            #### General
            * Hide Travel to Your Island nametag
            #### Rendering
            * Hub Overlay - Overlays hubs that you can join in green and hubs that you can’t join in red
            * Hide villagers in Hub
            * Overlay Uncollected Jacob’s Contests
            ### Sounds
            * Stop Reforge Sounds
            ### Spam
            #### Dungeons
            * Remove Dungeon Potion Messages
            * Remove Doubled Messages
            * Remove Opening Messages
            * Remove Journal Messages
            * Remove Crypt Wither Skull Messages
            * Remove Damage Milestone Messages
            * Remove Lost Adventurer Messages
            * Remove Wither Essence Messages
            * Remove Wither Door Opened Messages
            * Remove Can’t Open Wither Door Messages
            * Remove Bone Plating Messages
            * Remove Orb Messages
            * Remove Healer Messages
            * Remove Dungeon Finder Messages
            * Remove Watcher Messages
            * Remove Incorrect Three Weirdos Messages (Solver-Compatible)
              * Two modes: All messages or just Incorrect Answers)
            * Remove Dungeon Sell Messages
            * Remove “Struck” Messages
            * Remove Salvage Messages
            #### Dwarven Mines
            * Remove Raffle Messages
            * Remove Puzzler Messages
            * Remove Golden Goblin Messages
            * Remove Powder Ghast Messages
            * Remove First Mithril Powder Messages
            #### General
            * Remove Too Fast Messages
            * Remove Welcome Messages
            * Remove Warp Messages
            * Remove Inventory Full Messages 
            * Remove Slow Down Messages
            * Remove Useless Jerry’s Workshop Messages
            * Remove Combat Warp Messages
            * Remove all Chat if Ironman
            #### Hub
            * Remove Reforge Messages
            * Remove Bazaar Messages
            * Remove Messages about Bank
            * Remove Hub Warp Warnings / Messages
            * Remove Messages from Kat
            #### Hypixel
            * Remove GEXP Messages
            * Remove Watchdog Announcements
            * Remove Warnings about Hubs
            #### Items
            * Remove Creeper Veil Messages
            * Remove Skeleton Hat Messages
            * Remove Guardian Chestplate Messages
            * Remove Potion Buff Messages
            #### Slayer
            * Remove Dumb Slayer Drops
            * Remove Maddox Messages
            #### Your Island
            * Remove Minion Inventory Warnings
            * Remove Chat from Jerries
            * Remove Headless Horseman Spawn/Death Messages
            * Remove Useless Jacob’s Contest Messages
            * Remove Minion XP Messages
            """.trimIndent())
            .constrain {
                x = 1.percent()
                y = 1.percent()
                width = 98.percent()
                height = (window.getHeight() - 90).pixels()
            } childOf window
    }
}