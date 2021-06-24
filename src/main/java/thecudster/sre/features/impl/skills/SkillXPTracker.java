package thecudster.sre.features.impl.skills;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.time.StopWatch;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.events.SecondPassedEvent;
import thecudster.sre.core.gui.FloatPair;
import thecudster.sre.core.gui.GuiElement;
import thecudster.sre.core.gui.ScreenRenderer;
import thecudster.sre.core.gui.SmartFontRenderer;
import thecudster.sre.core.gui.colours.CommonColors;
import thecudster.sre.util.sbutil.ArrStorage;
import thecudster.sre.util.Utils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SkillXPTracker {
    public static String currentSkill = EnumChatFormatting.GRAY + "Not detected yet!";
    public static StopWatch timeCountedExclude = new StopWatch();
    public static StopWatch timeCountedInclude = new StopWatch();
    public static int xpPerHr = 0;
    public static double currentXPGained = 0.0;
    public static double lastXP = 0.0;
    public static double miningXP = 0.0;
    public static double alchemyXP = 0.0;
    public static double combatXP = 0.0;
    public static double farmingXP = 0.0;
    public static double foragingXP = 0.0;
    public static double fishingXP = 0.0;
    public static double enchantingXP = 0.0;
    public static double xpPerBlock = 0.0;
    public static int currentXP = 0;
    public static int currentLvl = 0;
    public static String[] displayText1 = {"Not detected yet!", "Not detected yet!", "Not detected yet!", "Not detected yet!", "Not detected yet!", "Not detected yet!", "Not detected yet!"};
    public static String[] displayText2 = {"Not detected yet!", "Not detected yet!", "Not detected yet!", "Not detected yet!", "Not detected yet!", "Not detected yet!", "Not detected yet!", "Not detected yet!", "Not detected yet!", "Not detected yet!"};

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChat(ClientChatReceivedEvent event) {
        if (!Utils.inSkyblock || event.type != 2) return;

        String[] actionBarSections = event.message.getUnformattedText().split(" {3,}");

        for (String section : actionBarSections) {
            if (section.contains("+") && section.contains("/") && section.contains("(")) {
                if (!section.contains("Runecrafting") && !section.contains("Carpentry")) {
                    if (!timeCountedExclude.isStarted()) { timeCountedExclude.start(); }
                    if (!timeCountedInclude.isStarted()) { timeCountedInclude.start(); }

                    String skill = section.substring(section.indexOf(" ") + 1, section.lastIndexOf(" "));
                    double currentXPInGame = Double.parseDouble(section.substring(section.indexOf("(") + 1, section.indexOf("/")).replace(",", ""));
                    switch (skill) {
                        case "Farming":
                            currentSkill = "Farming";
                            if (timeCountedExclude.isStarted() && !timeCountedExclude.isSuspended()) {
                                currentXP += currentXPInGame - lastXP;
                            }
                            farmingXP = currentXPInGame;
                            lastXP = currentXPInGame;
                            break;
                        case "Mining":
                            currentSkill = "Mining";
                            if (timeCountedExclude.isStarted() && !timeCountedExclude.isSuspended()) {
                                currentXP += currentXPInGame - lastXP;
                            }
                            miningXP = currentXPInGame;
                            lastXP = currentXPInGame;
                            break;
                        case "Combat":
                            currentSkill = "Combat";
                            if (timeCountedExclude.isStarted() && !timeCountedExclude.isSuspended()) {
                                currentXP += currentXPInGame - lastXP;
                            }
                            combatXP = currentXPInGame;
                            lastXP = currentXPInGame;
                            break;
                        case "Foraging":
                            currentSkill = "Foraging";
                            if (timeCountedExclude.isStarted() && !timeCountedExclude.isSuspended()) {
                                currentXP += currentXPInGame - lastXP;
                            }
                            foragingXP = currentXPInGame;
                            lastXP = currentXPInGame;
                            break;
                        case "Fishing":
                            currentSkill = "Fishing";
                            if (timeCountedExclude.isStarted() && !timeCountedExclude.isSuspended()) {
                                currentXP += currentXPInGame - lastXP;
                            }
                            fishingXP = currentXPInGame;
                            lastXP = currentXPInGame;
                            break;
                        case "Enchanting":
                            currentSkill = "Enchanting";
                            if (timeCountedExclude.isStarted() && !timeCountedExclude.isSuspended()) {
                                currentXP += currentXPInGame - lastXP;
                            }
                            enchantingXP = currentXPInGame;
                            lastXP = currentXPInGame;
                            break;
                        case "Alchemy":
                            currentSkill = "Alchemy";
                            if (timeCountedExclude.isStarted() && !timeCountedExclude.isSuspended()) {
                                currentXP += currentXPInGame - lastXP;
                            }
                            alchemyXP = currentXPInGame;
                            lastXP = currentXPInGame;
                            break;
                        default:
                            Utils.sendMsg(EnumChatFormatting.RED + "SkyblockReinvented caught and logged an exception at SkillXPTracker. Please report this!");

                            break;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onSecondPass(SecondPassedEvent event) {
        refresh();
    }

    public static void refresh() {
        if (SkyblockReinvented.config.skillXPTracker == 0) return;
        if (!timeCountedExclude.isStarted()) { timeCountedExclude.start(); }
        if (!timeCountedInclude.isStarted()) { timeCountedInclude.start(); }
        double[] time = getTime(timeCountedExclude);
        xpPerHr = (int) ((currentXPGained) / (time[1]));
        if (Minecraft.getMinecraft().currentScreen != null) {
            if (!timeCountedExclude.isSuspended()) {
                timeCountedExclude.suspend();
            }
        } else {
            if (timeCountedExclude.isSuspended()) {
                timeCountedExclude.resume();
            }
        }
        updateText(displayText1, time[0], time[1], time[2], time[3]);
        updateText(displayText2, time[0], time[1], time[2], time[3]);
    }
    public static double[] getTime(StopWatch stopWatch) {
        long seconds = stopWatch.getTime();
        Utils.sendMsg("" + seconds);
        // Code to convert to days, hrs, mins, and secs taken from https://stackoverflow.com/questions/11357945/java-convert-seconds-into-day-hour-minute-and-seconds-using-timeunit
        double day = (double) TimeUnit.SECONDS.toDays(seconds);
        double hours = (double)(TimeUnit.SECONDS.toHours(seconds) - TimeUnit.DAYS.toHours((int)day));
        double minute = (double)(TimeUnit.SECONDS.toMinutes(seconds) - TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(seconds)));
        double second = (double) (TimeUnit.SECONDS.toSeconds(seconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(seconds)));
        return new double[]{day, hours, minute, second};
    }
    public static String convertToReadable(double hours) {
        double temp = hours;
        int days = 0;
        int hrs = 0;
        int mins = 0;
        int secs = 0;
        if (temp >= 24) {
            while (temp >= 24) {
                days++;
                temp -= 24;
            }
            while (temp >= 1) {
                hrs++;
                temp--;
            }
            return days + "d" + hrs + "hr";
        } else if (temp > 1) {
            while (temp >= 1) {
                hrs++;
                temp--;
            }
            temp *= 60;
            while (temp >= 1) {
                mins++;
                temp--;
            }
            temp *= 60;
            while (temp >= 1) {
                secs++;
                temp--;
            }
            return hrs + "hr" + mins + "m";
        } else {
            temp *= 60;
            if (temp > 1) {
                while (temp >= 1) {
                    mins++;
                    temp--;
                }
                return mins + "m" + (int) (temp * 60) + "s";
            } else {
                return "0m" + (int) (temp * 60) + "s";
            }
        }
    }
    public static void updateText(String[] arr, double days, double hrs, double mins, double secs) {
        String[] temp = arr.clone();
        if (maxLvlOfSkill(currentSkill) == currentLvl) return;
        if (arr.length == 7) {
            temp[0] = currentSkill + " - " + !timeCountedExclude.isSuspended();
            temp[1] = "XP / hr: " + xpPerHr;
            temp[2] = "XP Gained: " + currentXPGained;
            temp[3] = timeCounted("Time Counted: ", days, hrs, mins, secs);
            temp[4] = timeCounted("Total Time Elapsed: ", days, hrs, mins, secs);
            if (ArrStorage.skillXPPerLevel.containsKey(currentLvl + 1)) {
                int xpToNextLvl = ArrStorage.skillXPPerLevel.get(currentLvl + 1) - currentXP;
                temp[5] = "Time Needed: " + convertToReadable((double)xpToNextLvl / (double)xpPerHr);
                temp[6] = "Actions Needed: " + (int)(xpToNextLvl / xpPerBlock);
            } else {
                temp[5] = "MAXED OUT!";
                temp[6] = "MAXED OUT!";
            }
            displayText1 = temp;
            return;
        } else if (arr.length == 10) {
            temp[0] = currentSkill + " - " + !timeCountedExclude.isSuspended();
            temp[1] = "XP / hr: " + xpPerHr;
            temp[2] = "XP Gained: " + currentXPGained;
            temp[3] = timeCounted("Time Counted: ", days, hrs, mins, secs);
            temp[4] = timeCounted("Total Time Elapsed: ", days, hrs, mins, secs);
            if (ArrStorage.skillXPPerLevel.containsKey(currentLvl + 1)) {
                int xpToNextLvl = ArrStorage.skillXPPerLevel.get(currentLvl + 1) - currentXP;
                temp[5] = "Time Needed: " + convertToReadable((double)xpToNextLvl / (double)xpPerHr);
                temp[6] = "Actions Needed: " + (int)(xpToNextLvl / xpPerBlock);
            } else {
                temp[5] = "MAXED OUT!";
                temp[6] = "MAXED OUT!";
            }
            temp[7] = "Time Until Lvl 60:";
            temp[8] = "Time Needed: " + convertToReadable(getTotalXPToLvl(currentLvl, maxLvlOfSkill(currentSkill)) / xpPerHr);
            temp[9] = convertToReadable(getTotalXPToLvl(currentLvl, maxLvlOfSkill(currentSkill)) / (double) xpPerHr);
            displayText2 = temp;
        } else {
            Utils.sendMsg(EnumChatFormatting.RED + "SkyblockReinvented caught and logged an exception at SkillXPTracker. Please report this!");
            System.out.println("Array of Strings:");
            for (String s : arr) {
                System.out.println(s);
            }
            System.out.print("Config: " + SkyblockReinvented.config.skillXPTracker);
            return;
        }
    }
    public static String timeCounted(String prefix, double days, double hrs, double mins, double secs) {
        if (days >= 1) {
            return prefix + (int)days + "d" + (int)hrs + "hr";
        } else if (hrs > 0) {
            return prefix + (int)hrs + "hr" + (int)mins + "m";
        } else if (mins > 0) {
            return prefix + (int)mins + "m" + (int)secs + "s";
        } else {
            return prefix + "0m" + (int)secs + "s";
        }
    }
    public static int getTotalXPToLvl(int currentLvl, int maxLvl) {
        int sum = ArrStorage.skillXPPerLevel.get(currentLvl) - currentXP;
        for (Map.Entry<Integer, Integer> entry : ArrStorage.skillXPPerLevel.entrySet()) {
            if (entry.getKey() > currentLvl && entry.getKey() <= maxLvl) {
                sum += entry.getValue();
            }
        }
        return sum;
    }
    public static int maxLvlOfSkill(String skill) {
        switch(skill) {
            case "Alchemy":
            case "Fishing":
            case "Foraging":
                return 50;
            case "Enchanting":
            case "Combat":
            case "Mining":
            case "Farming":
                return 60;
            default:
                return 0;
        }
    }
    static {
        new SkillXPElement();
    }
    public static class SkillXPElement extends GuiElement {
        private static final Minecraft mc = Minecraft.getMinecraft();
        public SkillXPElement() {
            super("Skill XP", new FloatPair(0.00625f, 0.14626351f));
            SkyblockReinvented.GUIMANAGER.registerElement(this);
        }
        @Override
        public void render() {
            EntityPlayerSP player = mc.thePlayer;
            ScaledResolution sr = new ScaledResolution(mc);
            if (this.getToggled() && player != null && mc.theWorld != null) {
                boolean leftAlign = getActualX() < sr.getScaledWidth() / 2f;
                if (SkyblockReinvented.config.skillXPTracker == 1) {
                    for (int i = 0; i < 10; i++) {
                        SmartFontRenderer.TextAlignment alignment = leftAlign ? SmartFontRenderer.TextAlignment.LEFT_RIGHT : SmartFontRenderer.TextAlignment.RIGHT_LEFT;
                        ScreenRenderer.fontRenderer.drawString(displayText1[i], leftAlign ? this.getActualX() : this.getActualX() + this.getWidth(), this.getActualY() + i * ScreenRenderer.fontRenderer.FONT_HEIGHT, CommonColors.WHITE, alignment, SmartFontRenderer.TextShadow.NORMAL);
                    }
                } else if (SkyblockReinvented.config.skillXPTracker == 2) {
                    for (int i = 0; i < 10; i++) {
                        SmartFontRenderer.TextAlignment alignment = leftAlign ? SmartFontRenderer.TextAlignment.LEFT_RIGHT : SmartFontRenderer.TextAlignment.RIGHT_LEFT;
                        ScreenRenderer.fontRenderer.drawString(displayText2[i], leftAlign ? this.getActualX() : this.getActualX() + this.getWidth(), this.getActualY() + i * ScreenRenderer.fontRenderer.FONT_HEIGHT, CommonColors.WHITE, alignment, SmartFontRenderer.TextShadow.NORMAL);
                    }
                }
            }
        }

        @Override
        public void demoRender() {
            ScaledResolution sr = new ScaledResolution(mc);
            if (Utils.inSkyblock && !Utils.inDungeons && SkyblockReinvented.config.skillXPTracker != 0) {
                boolean leftAlign = getActualX() < sr.getScaledWidth() / 2f;
                for (int i = 0; i < 10; i++) {
                    SmartFontRenderer.TextAlignment alignment = leftAlign ? SmartFontRenderer.TextAlignment.LEFT_RIGHT : SmartFontRenderer.TextAlignment.RIGHT_LEFT;
                    ScreenRenderer.fontRenderer.drawString(displayText2[i], leftAlign ? 0 : this.getActualWidth(), i * ScreenRenderer.fontRenderer.FONT_HEIGHT, CommonColors.WHITE, alignment, SmartFontRenderer.TextShadow.NORMAL);
                }
            }
        }

        @Override
        public boolean getToggled() {
            return SkyblockReinvented.config.skillXPTracker != 0;
        }

        @Override
        public int getHeight() {
            return ScreenRenderer.fontRenderer.FONT_HEIGHT * 3;
        }

        @Override
        public int getWidth() {
            return ScreenRenderer.fontRenderer.getStringWidth(displayText1[4]);
        }
    }
}
