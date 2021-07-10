package thecudster.sre.features.impl.skills

import thecudster.sre.util.Utils.getIntFromString
import thecudster.sre.util.Utils.containsAllOf
import thecudster.sre.util.Utils.containsAnyOf
import thecudster.sre.util.Utils.sendMsg
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.client.event.ClientChatReceivedEvent
import thecudster.sre.features.impl.skills.SkillXPTracker
import java.time.LocalDateTime
import thecudster.sre.util.sbutil.ArrStorage
import thecudster.sre.events.SecondPassedEvent
import java.time.temporal.ChronoUnit
import net.minecraftforge.client.event.GuiScreenEvent.DrawScreenEvent
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.inventory.GuiChest
import net.minecraft.inventory.ContainerChest
import java.lang.NullPointerException
import java.lang.ArrayIndexOutOfBoundsException
import net.minecraft.util.EnumChatFormatting
import thecudster.sre.features.impl.skills.SkillXPTracker.SkillXPElement
import thecudster.sre.core.gui.GuiElement
import thecudster.sre.core.gui.FloatPair
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.util.StringUtils
import org.apache.commons.lang3.time.StopWatch
import thecudster.sre.SkyblockReinvented
import thecudster.sre.core.gui.SmartFontRenderer.TextAlignment
import thecudster.sre.core.gui.SmartFontRenderer
import thecudster.sre.core.gui.ScreenRenderer
import thecudster.sre.core.gui.colours.CommonColors
import thecudster.sre.util.Utils
import java.lang.Exception
import java.util.concurrent.TimeUnit

class SkillXPTracker {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    fun onChat(event: ClientChatReceivedEvent) {
        val message = StringUtils.stripControlCodes(event.message.unformattedText)
        if (Utils.inSkyblock && !message.contains(":") && message.contains("  SKILL LEVEL UP ")) {
            // Handle skill level ups
            val skill = message.substring(message.indexOf("UP") + 3, message.lastIndexOf(" "))
            val level = getIntFromString(message.substring(message.indexOf("➜") + 1), true)
            when (skill) {
                "Farming" -> SkyblockReinvented.config.farmingLvl = level
                "Mining" -> SkyblockReinvented.config.miningLvl = level
                "Combat" -> SkyblockReinvented.config.combatLvl = level
                "Foraging" -> SkyblockReinvented.config.foragingLvl = level
                "Fishing" -> SkyblockReinvented.config.fishingLvl = level
                "Enchanting" -> SkyblockReinvented.config.enchantingLvl = level
                "Alchemy" -> SkyblockReinvented.config.alchemyLvl = level
                else -> System.err.println("Unknown skill leveled up.")
            }
        }
        if (!Utils.inSkyblock || event.type.toInt() != 2) return
        var unformatted = StringUtils.stripControlCodes(event.message.unformattedText)
        if (containsAllOf(unformatted, arrayOf("+", "(", "%)")) && containsAnyOf(
                unformatted,
                arrayOf("Farming", "Mining", "Combat", "Foraging", "Fishing", "Enchanting", "Alchemy")
            )
        ) {
            if (!timeCountedExclude.isStarted || timeCountedExclude.isStopped) timeCountedExclude.start()
            if (timeCountedExclude.isSuspended) timeCountedExclude.resume()
            if (!timeCountedInclude.isStarted || timeCountedInclude.isStopped) timeCountedInclude.start()
            if (timeCountedInclude.isSuspended) timeCountedInclude.resume()
            if (isFirst) {
                isFirst = false
            }
            latestXPGain = LocalDateTime.now()
            unformatted = unformatted.substring(unformatted.indexOf("+") + 1)
            xpPerBlock = unformatted.substring(0, unformatted.indexOf(" ")).toDouble()
            currentXPGained += xpPerBlock
            unformatted = unformatted.substring(unformatted.indexOf(" ") + 1)
            val prevSkill = currentSkill
            currentSkill = unformatted.substring(0, unformatted.indexOf(" "))
            if (prevSkill != currentSkill) {
                timeCountedExclude.reset()
                timeCountedInclude.reset()
                currentXPGained = 0.0
            }
            unformatted = unformatted.substring(unformatted.indexOf(" (") + 2)
            val currentPercent = unformatted.substring(0, unformatted.indexOf("%)")).toDouble() / 100.0
            when (currentSkill) {
                "Mining" -> {
                    currentXP = ArrStorage.skillXPPerLevel[SkyblockReinvented.config.miningLvl]!! * currentPercent
                    currentLvl = SkyblockReinvented.config.miningLvl
                }
                "Farming" -> {
                    currentXP = ArrStorage.skillXPPerLevel[SkyblockReinvented.config.farmingLvl]!! * currentPercent
                    currentLvl = SkyblockReinvented.config.farmingLvl
                }
                "Combat" -> {
                    currentXP = ArrStorage.skillXPPerLevel[SkyblockReinvented.config.combatLvl]!! * currentPercent
                    currentLvl = SkyblockReinvented.config.combatLvl
                }
                "Foraging" -> {
                    currentXP = ArrStorage.skillXPPerLevel[SkyblockReinvented.config.foragingLvl]!! * currentPercent
                    currentLvl = SkyblockReinvented.config.foragingLvl
                }
                "Fishing" -> {
                    currentXP = ArrStorage.skillXPPerLevel[SkyblockReinvented.config.fishingLvl]!! * currentPercent
                    currentLvl = SkyblockReinvented.config.fishingLvl
                }
                "Enchanting" -> {
                    currentXP = ArrStorage.skillXPPerLevel[SkyblockReinvented.config.enchantingLvl]!! * currentPercent
                    currentLvl = SkyblockReinvented.config.enchantingLvl
                }
                "Alchemy" -> {
                    currentXP = ArrStorage.skillXPPerLevel[SkyblockReinvented.config.alchemyLvl]!! * currentPercent
                    currentLvl = SkyblockReinvented.config.alchemyLvl
                }
                else -> println("bruh moment! skill: " + currentSkill)
            }
        }
    }

    var timeSinceRefresh = 0
    var isFirst = true
    @SubscribeEvent
    fun onSecondPass(event: SecondPassedEvent?) {
        if (SkyblockReinvented.config.skillXPTracker) {
            if (ChronoUnit.SECONDS.between(
                    latestXPGain,
                    LocalDateTime.now()
                ) > SkyblockReinvented.config.xpTrackerTimeout
            ) {
                timeCountedExclude.reset()
                timeCountedInclude.reset()
                currentXPGained = 0.0
            } else {
                timeSinceRefresh++
                refreshTime(displayText1)
                refreshTime(displayText2)
                if (timeSinceRefresh == SkyblockReinvented.config.xpTrackerRefreshRate || isFirst) {
                    refresh()
                    timeSinceRefresh = 0
                }
            }
        }
    }

    fun refreshTime(arr: Array<String>) {
        val temp = arr.clone()
        temp[3] = timeCounted(
            "Time Counted: ", getTime(timeCountedExclude)[0], getTime(timeCountedExclude)[1], getTime(
                timeCountedExclude
            )[2], getTime(timeCountedExclude)[3]
        )
        temp[4] = timeCounted(
            "Total Time Elapsed: ", getTime(timeCountedInclude)[0], getTime(timeCountedInclude)[1], getTime(
                timeCountedInclude
            )[2], getTime(timeCountedInclude)[3]
        )
        if (arr.size == 7) {
            displayText1 = temp
        } else if (arr.size == 10) {
            displayText2 = temp
        }
        addColours()
    }

    @SubscribeEvent
    fun onGuiScreen(event: DrawScreenEvent.Pre?) {
        try {
            if (Minecraft.getMinecraft().currentScreen is GuiChest) {
                val chest = Minecraft.getMinecraft().currentScreen as GuiChest
                val inventory = chest.inventorySlots as ContainerChest
                val inventoryTitle = inventory.lowerChestInventory.displayName.unformattedText
                if (inventoryTitle == "Your Skills") {
                    SkyblockReinvented.config.farmingLvl = parseName(inventory, 20)
                    SkyblockReinvented.config.miningLvl = parseName(inventory, 21)
                    SkyblockReinvented.config.combatLvl = parseName(inventory, 22)
                    SkyblockReinvented.config.foragingLvl = parseName(inventory, 23)
                    SkyblockReinvented.config.fishingLvl = parseName(inventory, 24)
                    SkyblockReinvented.config.enchantingLvl = parseName(inventory, 25)
                    SkyblockReinvented.config.alchemyLvl = parseName(inventory, 26)
                    SkyblockReinvented.config.markDirty()
                    SkyblockReinvented.config.writeData()
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    @Throws(NullPointerException::class)
    private fun parseName(inventory: ContainerChest, slot: Int): Int {
        var displayName =
            StringUtils.stripControlCodes(inventory.lowerChestInventory.getStackInSlot(slot - 1).displayName)
        displayName = displayName.substring(displayName.indexOf(" ") + 1)
        return toArabic(displayName)
    }

    /**
     * Taken from StackOverflow answer https://stackoverflow.com/a/17534350 under CC BY-SA 3.0 license
     * @author Shaltiel Shmidman
     */
    private fun toArabic(number: String): Int {
        if (number.isEmpty()) return 0
        if (number.startsWith("M")) return 1000 + toArabic(number.substring(1))
        if (number.startsWith("CM")) return 900 + toArabic(number.substring(2))
        if (number.startsWith("D")) return 500 + toArabic(number.substring(1))
        if (number.startsWith("CD")) return 400 + toArabic(number.substring(2))
        if (number.startsWith("C")) return 100 + toArabic(number.substring(1))
        if (number.startsWith("XC")) return 90 + toArabic(number.substring(2))
        if (number.startsWith("L")) return 50 + toArabic(number.substring(1))
        if (number.startsWith("XL")) return 40 + toArabic(number.substring(2))
        if (number.startsWith("X")) return 10 + toArabic(number.substring(1))
        if (number.startsWith("IX")) return 9 + toArabic(number.substring(2))
        if (number.startsWith("V")) return 5 + toArabic(number.substring(1))
        if (number.startsWith("IV")) return 4 + toArabic(number.substring(2))
        if (number.startsWith("I")) return 1 + toArabic(number.substring(1))
        throw ArrayIndexOutOfBoundsException()
    }

    companion object {
        var currentSkill = "Not detected yet!"
        var timeCountedExclude = StopWatch()
        var timeCountedInclude = StopWatch()
        var xpPerHr = 0.0
        var currentXPGained = 0.0
        var xpPerBlock = 0.0
        var latestXPGain = LocalDateTime.MIN
        var currentXP = 0.0
        var currentLvl = 0
        var displayText1 = arrayOf(
            "Not detected yet!",
            "Not detected yet!",
            "Not detected yet!",
            "Not detected yet!",
            "Not detected yet!",
            "Not detected yet!",
            "Not detected yet!"
        )
        var displayText2 = arrayOf(
            "Not detected yet!",
            "Not detected yet!",
            "Not detected yet!",
            "Not detected yet!",
            "Not detected yet!",
            "Not detected yet!",
            "Not detected yet!",
            "Not detected yet!",
            "Not detected yet!",
            "Not detected yet!"
        )

        fun refresh() {
            if (!SkyblockReinvented.config.skillXPTracker) return
            if (!timeCountedExclude.isStarted) {
                timeCountedExclude.start()
            }
            if (!timeCountedInclude.isStarted) {
                timeCountedInclude.start()
            }
            val time = getTime(timeCountedExclude)
            val totalSecs = time[0] * 24 * 60 * 60 + time[1] * 60 * 60 + time[2] * 60 + time[3]
            xpPerHr = ((currentXPGained / (totalSecs / (60 * 60))) as Int) as Double
            if (SkyblockReinvented.config.autoPauseXPTracker) {
                if (Minecraft.getMinecraft().currentScreen != null) {
                    if (!timeCountedExclude.isSuspended) {
                        timeCountedExclude.suspend()
                    }
                }
            }
            if (!SkyblockReinvented.config.showExtraData) {
                updateText(displayText1)
            } else {
                updateText(displayText2)
            }
        }

        fun getTime(stopWatch: StopWatch): DoubleArray {
            val seconds = stopWatch.time / 1000
            // Code to convert to days, hrs, mins, and secs taken from https://stackoverflow.com/questions/11357945/java-convert-seconds-into-day-hour-minute-and-seconds-using-timeunit
            val day = TimeUnit.SECONDS.toDays(seconds).toDouble()
            val hours = (TimeUnit.SECONDS.toHours(seconds) - TimeUnit.DAYS.toHours(
                day as Int as Long
            )).toDouble()
            val minute = (TimeUnit.SECONDS.toMinutes(seconds) - TimeUnit.HOURS.toMinutes(
                TimeUnit.SECONDS.toHours(
                    seconds
                )
            )).toDouble()
            val second = (TimeUnit.SECONDS.toSeconds(seconds) - TimeUnit.MINUTES.toSeconds(
                TimeUnit.SECONDS.toMinutes(
                    seconds
                )
            )).toDouble()
            return doubleArrayOf(day, hours, minute, second)
        }

        private fun addColours() {
            when (currentSkill) {
                "Mining" -> addColours(EnumChatFormatting.GRAY)
                "Farming" -> addColours(EnumChatFormatting.GOLD)
                "Combat" -> addColours(EnumChatFormatting.RED)
                "Foraging" -> addColours(EnumChatFormatting.BLUE)
                "Fishing" -> addColours(EnumChatFormatting.AQUA)
                "Enchanting" -> addColours(EnumChatFormatting.LIGHT_PURPLE)
                "Alchemy" -> addColours(EnumChatFormatting.DARK_PURPLE)
            }
        }

        private fun addColours(colour: EnumChatFormatting) {
            for (i in displayText1.indices) {
                displayText1[i] = colour.toString() + displayText1[i]
            }
            for (i in displayText2.indices) {
                displayText2[i] = colour.toString() + displayText2[i]
            }
        }

        fun convertToReadable(hours: Double): String {
            var temp = hours
            var days = 0
            var hrs = 0
            var mins = 0
            var secs = 0
            return if (temp >= 24) {
                while (temp >= 24) {
                    days++
                    temp -= 24.0
                }
                while (temp >= 1) {
                    hrs++
                    temp--
                }
                days.toString() + "d" + hrs + "hr"
            } else if (temp > 1) {
                while (temp >= 1) {
                    hrs++
                    temp--
                }
                temp *= 60.0
                while (temp >= 1) {
                    mins++
                    temp--
                }
                temp *= 60.0
                while (temp >= 1) {
                    secs++
                    temp--
                }
                hrs.toString() + "hr" + mins + "m"
            } else {
                temp *= 60.0
                if (temp > 1) {
                    while (temp >= 1) {
                        mins++
                        temp--
                    }
                    mins.toString() + "m" + (temp * 60).toInt() + "s"
                } else {
                    "0m" + (temp * 60).toInt() + "s"
                }
            }
        }

        private fun getActive(active: Boolean): String {
            return if (active) {
                "Active"
            } else "Inactive"
        }

        fun updateText(arr: Array<String>) {
            val temp = arr.clone()
            if (maxLvlOfSkill(currentSkill) == currentLvl) return
            if (arr.size == 7) {
                temp[0] = currentSkill + " - " + getActive(!timeCountedExclude.isSuspended)
                temp[1] = "XP / hr: " + String.format("%,.1f", xpPerHr)
                temp[2] = "XP Gained: " + String.format("%,.1f", currentXPGained)
                if (ArrStorage.skillXPPerLevel.containsKey(currentLvl + 1)) {
                    val xpToNextLvl = ArrStorage.skillXPPerLevel[currentLvl + 1]!! - currentXP
                    temp[5] = "Time Needed: " + convertToReadable(xpToNextLvl / xpPerHr)
                    temp[6] = "Actions Needed: " + String.format("%,.0f", xpToNextLvl / xpPerBlock)
                } else {
                    temp[5] = "MAXED OUT!"
                    temp[6] = "MAXED OUT!"
                }
                displayText1 = temp
                addColours()
                return
            } else if (arr.size == 10) {
                temp[0] = currentSkill + " - " + getActive(!timeCountedExclude.isSuspended)
                temp[1] = "XP / hr: " + String.format("%,.1f", xpPerHr)
                temp[2] = "XP Gained: " + String.format("%,.1f", currentXPGained)
                if (ArrStorage.skillXPPerLevel.containsKey(currentLvl + 1)) {
                    val xpToNextLvl = ArrStorage.skillXPPerLevel[currentLvl + 1]!! - currentXP
                    temp[5] = "Time Needed: " + convertToReadable(xpToNextLvl / xpPerHr)
                    temp[6] = "Actions Needed: " + String.format("%,.0f", xpToNextLvl / xpPerBlock)
                } else {
                    temp[5] = "MAXED OUT!"
                    temp[6] = "MAXED OUT!"
                }
                temp[7] = "Time Until Lvl 60:"
                temp[8] = "Time Needed: " + convertToReadable(
                    getTotalXPToLvl(
                        currentLvl,
                        maxLvlOfSkill(currentSkill)
                    ) / xpPerHr
                )
                temp[9] = "Actions Needed: " + String.format(
                    "%,.0f", (getTotalXPToLvl(
                        currentLvl, maxLvlOfSkill(
                            currentSkill
                        )
                    ) - currentXP) / xpPerBlock
                )
                displayText2 = temp
                addColours()
                return
            } else {
                sendMsg(EnumChatFormatting.RED.toString() + "SkyblockReinvented caught and logged an exception at SkillXPTracker. Please report this!")
                println("Array of Strings:")
                for (s in arr) {
                    println(s)
                }
                print("Config: " + SkyblockReinvented.config.skillXPTracker)
                return
            }
        }

        fun timeCounted(prefix: String, days: Double, hrs: Double, mins: Double, secs: Double): String {
            return if (days >= 1) {
                prefix + days.toInt() + "d" + hrs.toInt() + "hr"
            } else if (hrs > 0) {
                prefix + hrs.toInt() + "hr" + mins.toInt() + "m"
            } else if (mins > 0) {
                prefix + mins.toInt() + "m" + secs.toInt() + "s"
            } else {
                prefix + "0m" + secs.toInt() + "s"
            }
        }

        fun getTotalXPToLvl(currentLvl: Int, maxLvl: Int): Double {
            var sum = ArrStorage.skillXPPerLevel[currentLvl]!! - currentXP
            for ((key, value) in ArrStorage.skillXPPerLevel) {
                if (key > currentLvl && key <= maxLvl) {
                    sum += value
                }
            }
            return sum
        }

        fun maxLvlOfSkill(skill: String?): Int {
            return when (skill) {
                "Alchemy", "Fishing", "Foraging" -> 50
                "Enchanting", "Combat", "Mining", "Farming" -> 60
                else -> 0
            }
        }

        init {
            SkillXPElement()
        }
    }

    class SkillXPElement : GuiElement("Skill XP", FloatPair(0.00625f, 0.14626351f)) {
        override fun render() {
            val player = mc.thePlayer
            val sr = ScaledResolution(mc)
            if (latestXPGain == null) {
                latestXPGain = LocalDateTime.MIN
            }
            if (this.toggled && player != null && mc.theWorld != null && Utils.inSkyblock && ChronoUnit.SECONDS.between(
                    latestXPGain, LocalDateTime.now()
                ) < SkyblockReinvented.config.xpTrackerTimeout && ChronoUnit.MILLIS.between(
                    latestXPGain, LocalDateTime.now()
                ) > 0
            ) {
                val leftAlign = actualX < sr.scaledWidth / 2f
                if (!SkyblockReinvented.config.showExtraData) {
                    for (i in 0..9) {
                        val alignment = if (leftAlign) TextAlignment.LEFT_RIGHT else TextAlignment.RIGHT_LEFT
                        ScreenRenderer.fontRenderer.drawString(
                            displayText1[i],
                            if (leftAlign) this.actualX else this.actualX + this.width,
                            this.actualY + i * ScreenRenderer.fontRenderer.FONT_HEIGHT,
                            CommonColors.WHITE,
                            alignment,
                            SmartFontRenderer.TextShadow.NORMAL
                        )
                    }
                } else if (SkyblockReinvented.config.showExtraData) {
                    for (i in 0..9) {
                        val alignment = if (leftAlign) TextAlignment.LEFT_RIGHT else TextAlignment.RIGHT_LEFT
                        ScreenRenderer.fontRenderer.drawString(
                            displayText2[i],
                            if (leftAlign) this.actualX else this.actualX + this.width,
                            this.actualY + i * ScreenRenderer.fontRenderer.FONT_HEIGHT,
                            CommonColors.WHITE,
                            alignment,
                            SmartFontRenderer.TextShadow.NORMAL
                        )
                    }
                }
            }
        }

        override fun demoRender() {
            val sr = ScaledResolution(mc)
            if (Utils.inSkyblock && !Utils.inDungeons && SkyblockReinvented.config.skillXPTracker) {
                val leftAlign = actualX < sr.scaledWidth / 2f
                for (i in 0..9) {
                    val alignment = if (leftAlign) TextAlignment.LEFT_RIGHT else TextAlignment.RIGHT_LEFT
                    ScreenRenderer.fontRenderer.drawString(
                        displayText2[i],
                        if (leftAlign) 0.0f else this.actualWidth,
                        (i * ScreenRenderer.fontRenderer.FONT_HEIGHT).toFloat(),
                        CommonColors.WHITE,
                        alignment,
                        SmartFontRenderer.TextShadow.NORMAL
                    )
                }
            }
        }

        override val toggled: Boolean
            get() = SkyblockReinvented.config.skillXPTracker

        override val height: Int
            get() = if (!SkyblockReinvented.config.showExtraData) { ScreenRenderer.fontRenderer.FONT_HEIGHT * displayText1.size } else ScreenRenderer.fontRenderer.FONT_HEIGHT * displayText2.size
        override val width: Int
            get() = ScreenRenderer.fontRenderer.getStringWidth(displayText1[4])
        companion object {
            private val mc = Minecraft.getMinecraft()
        }

        init {
            SkyblockReinvented.GUIMANAGER!!.registerElement(this)
            addColours()
        }
    }
}