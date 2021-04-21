package thecudster.sre.features.impl.bestiary;

import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.gui.MinecraftServerGui;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.features.impl.slayer.SlayerTracker;
import thecudster.sre.util.api.APIUtil;
import thecudster.sre.util.gui.*;
import thecudster.sre.util.gui.colours.CommonColors;
import thecudster.sre.util.sbutil.Utils;

import java.util.*;

public class BestiaryProgress {
    public static HashMap<String, Double> things = new LinkedHashMap<String, Double>();
    public static String display = EnumChatFormatting.RED + "Current Bestiary: " + EnumChatFormatting.GOLD + "Not detected yet!\n" + EnumChatFormatting.RED +
            "Kills to next level: " + EnumChatFormatting.GOLD + "Not detected yet!";
    public static String kills = EnumChatFormatting.RED +
            "Kills to next level: " + EnumChatFormatting.GOLD + "Not detected yet!";
    public static String current = EnumChatFormatting.RED + "Current Bestiary: " + EnumChatFormatting.GOLD + "Not detected yet!";
    private static final Minecraft mc = Minecraft.getMinecraft();
    public static void placeItems() {
        things.put("kills_invisible_creeper", new Double(0.0));
        things.put("kills_diamond_zombie", new Double(0.0));
        things.put("kills_diamond_skeleton", new Double(0.0));
        things.put("kills_lapis_zombie", new Double(0.0));
        things.put("kills_zombie", 0.0);
        things.put("kills_skeleton", new Double(0.0));
        things.put("kills_spider", new Double(0.0));
        things.put("kills_redstone_pigman",new Double(0.0));
        things.put("kills_enderman",new Double(0.0)); // i thought this would work but then realized it didn't matter, too lazy to revert
        things.put("kills_zombie_villager",0.0);
        things.put("kills_wither_skeleton",0.0);
        things.put("kills_blaze",0.0);
        things.put("kills_emerald_slime",0.0);
        things.put("kills_witch",0.0);
        things.put("kills_ruin_wolf",0.0);
        things.put("kills_respawning_skeleton",0.0);
        things.put("kills_jockey_shot_silverfish",0.0);
        things.put("kills_magma_cube",0.0);
        things.put("kills_fireball_magma_cube",0.0);
        things.put("kills_pond_squid",0.0);
        things.put("kills_pigman",0.0);
        things.put("kills_night_respawning_skeleton",0.0);
        things.put("kills_sea_guardian",0.0);
        things.put("kills_goblin_weakling_melee",0.0);
        things.put("kills_goblin_creeper",0.0);
        things.put("kills_goblin_weakling_bow",0.0);
        things.put("kills_ice_walker",0.0);
        things.put("kills_chicken",0.0);
        things.put("kills_old_wolf",0.0);
        things.put("kills_wither_gourd",0.0);
        things.put("kills_phantom_spirit",0.0);
        things.put("kills_trick_or_treater",0.0);
        things.put("kills_batty_witch",0.0);
        things.put("kills_wraith",0.0);
        things.put("kills_scary_jerry",0.0);
        things.put("kills_weaver_spider",0.0);
        things.put("kills_splitter_spider",0.0);
        things.put("kills_splitter_spider_silverfish",0.0);
        things.put("kills_dasher_spider",0.0);
        things.put("kills_jockey_skeleton",0.0);
        things.put("kills_spider_jockey",0.0);
        things.put("kills_voracious_spider",0.0);
        things.put("kills_unburried_zombie",0.0);
        things.put("kills_random_slime",0.0);
        things.put("kills_sheep",0.0);
        things.put("kills_rabbit",0.0);
        things.put("kills_sea_walker",0.0);
        things.put("kills_minos_hunter",0.0);
        things.put("kills_siamese_lynx",0.0);
        things.put("kills_endermite",0.0);
        things.put("kills_watcher",0.0);
        things.put("kills_zealot_enderman",0.0);
        things.put("kills_obsidian_wither",0.0);
        things.put("kills_ghast",0.0);
        things.put("kills_sea_witch",0.0);
        things.put("kills_pack_spirit",0.0);
        things.put("kills_howling_spirit",0.0);
        things.put("kills_soul_of_the_alpha",0.0);
        things.put("kills_zombie_deep",0.0);
        things.put("kills_sea_archer",0.0);
        things.put("kills_water_hydra",0.0);
        things.put("kills_goblin_knife_thrower",0.0);
        things.put("kills_treasure_hoarder",0.0);
        things.put("kills_goblin",0.0);
        things.put("kills_crystal_sentry",0.0);
        things.put("kills_goblin_battler",0.0);
        things.put("kills_goblin_murderlover",0.0);
        things.put("kills_goblin_creepertamer",0.0);
        things.put("kills_goblin_golem",0.0);
        things.put("kills_creeper",0.0);
        things.put("kills_scarecrow",0.0);
        things.put("kills_catfish",0.0);
        things.put("kills_frosty_the_snowman",0.0);
        things.put("kills_frozen_steve",0.0);
        things.put("kills_liquid_hot_magma",0.0);
        things.put("kills_zombie_grunt",0.0);
        things.put("kills_crypt_lurker",0.0);
        things.put("kills_crypt_tank_zombie",0.0);
        things.put("kills_diamond_guy",0.0);
        things.put("kills_scared_skeleton",0.0);
        things.put("kills_dungeon_respawning_skeleton",0.0);
        things.put("kills_crypt_souleater",0.0);
        things.put("kills_sniper_skeleton",0.0);
        things.put("kills_lost_adventurer",0.0);
        things.put("kills_skeleton_grunt",0.0);
        things.put("kills_crypt_dreadlord",0.0);
        things.put("kills_watcher_summon_undead",0.0);
        things.put("kills_cellar_spider",0.0);
        things.put("kills_skeleton_soldier",0.0);
        things.put("kills_bonzo_summon_undead",0.0);
        things.put("kills_brood_mother_spider",0.0);
        things.put("kills_brood_mother_cave_spider",0.0);
        things.put("kills_arachne_brood",0.0);
        things.put("kills_generator_magma_cube",0.0);
        things.put("kills_powder_ghast",0.0);
        things.put("kills_arachne",0.0);
    }
    public static void getThings() {
        /**
         * Modified from Skytils under GNU Affero General Public license.
         * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
         * @author My-Name-Is-Jeff
         * @author Sychic
         */
        try {
            String uuid = APIUtil.getUUID(Minecraft.getMinecraft().thePlayer.getName());
            String apiKey = SkyblockReinvented.config.apiKey;
            String latestProfile = APIUtil.getLatestProfileID(uuid, apiKey);
            JsonObject profileResponse = APIUtil.getJSONResponse("https://api.hypixel.net/skyblock/profile?profile=" + latestProfile + "&key=" + apiKey);
            if (!profileResponse.get("success").getAsBoolean()) {
                String reason = profileResponse.get("cause").getAsString();
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Failed getting bestiary info with reason: " + reason));
                return;
            }
            JsonObject playerObject = profileResponse.get("profile").getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject();
            if (!playerObject.has("stats")) {
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Failed getting stats with reason: No stats object."));
                return;
            }
            JsonObject statsObject = playerObject.get("stats").getAsJsonObject();
            for (Map.Entry<String, Double> alsdkjf : things.entrySet()) {
                things.replace(alsdkjf.getKey(), Double.parseDouble(statsObject.get(alsdkjf.getKey()).toString().substring(statsObject.get(alsdkjf.getKey()).toString().indexOf(":") + 1)));
            }
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    public Map<String, Double> getStats() {
        return things;
    }
    @SubscribeEvent
    public void onEntityDeath(LivingDeathEvent event) {
        for (Entity e : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            if (e instanceof EntityArmorStand && e.getDistanceToEntity(event.entity) < 3) {
                try {
                    if (!e.getCustomNameTag().contains("§f/§a")) { return; }
                    if (e.getDistanceToEntity(Minecraft.getMinecraft().thePlayer) > 20) { return; }
                    System.out.println(e.getCustomNameTag());
                    String mobName = e.getCustomNameTag().substring(e.getCustomNameTag().indexOf(" §c") + 1);
                    mobName = mobName.substring(0, mobName.indexOf(" §a"));
                    mobName = StringUtils.stripControlCodes(mobName);
                    String current = "kills_" + BestiaryHelper.getCorrectName(mobName);
                    this.things.put(current, this.things.get(current) + 1);
                    int numKills = this.things.get(current).intValue();
                    kills = BestiaryHelper.updateKills(numKills);
                    current = BestiaryHelper.updateCurrent(mobName);
                    } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }/*
    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Text event) {
        if (SkyblockReinvented.config.bestiaryInfo) {
            new TextRenderer(Minecraft.getMinecraft(), this.display, SkyblockReinvented.config.bestiaryInfoX, SkyblockReinvented.config.bestiaryInfoY, 1);
            Minecraft.getMinecraft().getTextureManager().bindTexture(Gui.icons);
        }
    }*/
    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        if (SkyblockReinvented.config.bestiaryInfo) {
            this.getThings();
        }
    }
    static {
        new BestiaryKills();
        new BestiaryCurrent();
    }
    public static class BestiaryKills extends GuiElement {
        public BestiaryKills() {
            super("Bestiary Kills", new FloatPair(0.004687f, 0.25639135f));
            SkyblockReinvented.GUIMANAGER.registerElement(this);
        }
        @Override
        public void render() {
            EntityPlayerSP player = mc.thePlayer;
            ScaledResolution sr = new ScaledResolution(mc);
            if (this.getToggled() && player != null && mc.theWorld != null) {
                boolean leftAlign = getActualX() < sr.getScaledWidth() / 2f;
                ScreenRenderer.fontRenderer.drawString(kills, leftAlign ? this.getActualX() : this.getActualX() + this.getWidth(), this.getActualY(), CommonColors.WHITE, SmartFontRenderer.TextAlignment.LEFT_RIGHT, SmartFontRenderer.TextShadow.NORMAL);
            }
        }

        @Override
        public void demoRender() {
            ScreenRenderer.fontRenderer.drawString(kills, 0.004687f, 0.25639135f, CommonColors.WHITE, SmartFontRenderer.TextAlignment.LEFT_RIGHT, SmartFontRenderer.TextShadow.NORMAL);
        }

        @Override
        public boolean getToggled() {
            return SkyblockReinvented.config.bestiaryInfo;
        }

        @Override
        public int getHeight() {
            return ScreenRenderer.fontRenderer.FONT_HEIGHT;
        }

        @Override
        public int getWidth() {
            return ScreenRenderer.fontRenderer.getStringWidth(kills);
        }
    }
    public static class BestiaryCurrent extends GuiElement {
        public BestiaryCurrent() {
            super("Bestiary Current", new FloatPair(0.0026041507f, 0.21804328f));
            SkyblockReinvented.GUIMANAGER.registerElement(this);
        }
        @Override
        public void render() {
            EntityPlayerSP player = mc.thePlayer;
            ScaledResolution sr = new ScaledResolution(mc);
            if (this.getToggled() && player != null && mc.theWorld != null) {
                boolean leftAlign = getActualX() < sr.getScaledWidth() / 2f;
                ScreenRenderer.fontRenderer.drawString(current, leftAlign ? this.getActualX() : this.getActualX() + this.getWidth(), this.getActualY(), CommonColors.WHITE, SmartFontRenderer.TextAlignment.LEFT_RIGHT, SmartFontRenderer.TextShadow.NORMAL);
            }
        }

        @Override
        public void demoRender() {
            ScreenRenderer.fontRenderer.drawString(current, 0.0026041507f, 0.21804328f, CommonColors.WHITE, SmartFontRenderer.TextAlignment.LEFT_RIGHT, SmartFontRenderer.TextShadow.NORMAL);
        }

        @Override
        public boolean getToggled() {
            return SkyblockReinvented.config.bestiaryInfo;
        }

        @Override
        public int getHeight() {
            return ScreenRenderer.fontRenderer.FONT_HEIGHT;
        }

        @Override
        public int getWidth() {
            return ScreenRenderer.fontRenderer.getStringWidth(current);
        }
    }
}
