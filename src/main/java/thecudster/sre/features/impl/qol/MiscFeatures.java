package thecudster.sre.features.impl.qol;

import com.google.gson.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderItemInFrameEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.core.gui.GuiManager;
import thecudster.sre.events.SecondPassedEvent;
import thecudster.sre.features.impl.filter.Filter;
import thecudster.sre.util.sbutil.CurrentLoc;
import thecudster.sre.util.Utils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MiscFeatures {
    @SubscribeEvent
    public void onRender(RenderItemInFrameEvent event) {
        if (!Utils.inSkyblock) { return; }
        if (!CurrentLoc.currentLoc.equals("Your Island")) { return; }
        if (!SkyblockReinvented.config.itemFrameNames) { return; }
        event.entityItemFrame.setAlwaysRenderNameTag(false);
        event.entityItemFrame.getDisplayedItem().setStackDisplayName("");
    }
    @SubscribeEvent
    public void onRenderEntity(RenderLivingEvent.Pre event) {
        try {
            if (SkyblockReinvented.config.travelIsland && Utils.inSkyblock) {
                if (event.entity.getCustomNameTag() != null) {
                    if (Utils.inLoc(new String[]{"Village"})) {
                        if (event.entity.getCustomNameTag().contains("Travel to:") || event.entity.getCustomNameTag().contains("Your Island")) {
                            event.setCanceled(true);
                            event.entity.setAlwaysRenderNameTag(false);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static boolean needsToPickup = false;

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if (!Utils.inSkyblock) { return; }
        if (!SkyblockReinvented.config.stash) { return; }
        String unformatted = event.message.getUnformattedText();
        unformatted = StringUtils.stripControlCodes(unformatted);
        if (unformatted.contains("You have ") && unformatted.contains("item stashed away!!")) {
            event.setCanceled(true);
            GuiManager.createTitle("Pick up your stash using " + Keyboard.getKeyName(SkyblockReinvented.keyBindings[1].getKeyCode()), 20);
            needsToPickup = true;
        }
        if (unformatted.contains("You picked up all items from your item stash!")) {
            event.setCanceled(true);
            needsToPickup = false;
        }
        else if (unformatted.contains("You picked up") && unformatted.contains("items from your item stash")) {
            event.setCanceled(true);
            needsToPickup = true;
        }
        if (unformatted.contains("From stash: ")) {
            event.setCanceled(true);
        }
        if (unformatted.contains("An item didn't fit in your inventory and was added to your item")) {
            event.setCanceled(true);
        }
    }
    @SubscribeEvent
    public void onRender(RenderLivingEvent.Pre event) {
        if (!Utils.inSkyblock || !CurrentLoc.currentLoc.equals("Your Island") || SkyblockReinvented.config.teleportPad == 0) { return; }
        if (event.entity instanceof EntityArmorStand) {
            if (event.entity.getName() != null) {
                if (event.entity.getName().indexOf("Warp To") != -1) {
                    if (SkyblockReinvented.config.teleportPad == 1) {
                        event.entity.setCustomNameTag(event.entity.getName().substring(event.entity.getName().indexOf("To") + 3));
                    } else {
                        event.entity.setAlwaysRenderNameTag(false);
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public void onSeccondPassed(SecondPassedEvent event) {
        if (SkyblockReinvented.config.darkAuction) {
            if (LocalDateTime.now().getMinute() == 53 && !CurrentLoc.currentLoc.equals("Wilderness") && LocalDateTime.now().getSecond() == 0) {
                GuiManager.createTitle("Dark Auction", 20);
            }
        }
    }
    public ArrayList<String> coopMembers;
    @SubscribeEvent
    public void onTooltip(ItemTooltipEvent event) {
        if (Minecraft.getMinecraft().currentScreen instanceof GuiChest) {
            GuiChest chest = (GuiChest) Minecraft.getMinecraft().currentScreen;
            ContainerChest inventory = (ContainerChest) chest.inventorySlots;
            String inventoryTitle = inventory.getLowerChestInventory().getDisplayName().getUnformattedText();
            if (isCollectionMenu(inventoryTitle)) {
                for (String s : event.toolTip) {
                    for (Map.Entry<String, CoopMember> member : this.coop.entrySet()) {
                        if (s.contains(member.getValue().getMemberName()) && member.getValue().getDisabled()) {
                            event.toolTip.remove(s);
                        }
                    }
                }
            }
        }
    }
    public boolean isCollectionMenu(String inventoryTitle) {
        return inventoryTitle.equals("Mining Collection") || inventoryTitle.equals("Farming Collection") || inventoryTitle.equals("Combat Collection") || inventoryTitle.equals("Foraging Collection") || inventoryTitle.equals("Fishing Collection");
    }
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public HashMap<String, CoopMember> coop;
    public void readConfig() {
        File coopMembersJson = new File(SkyblockReinvented.modDir, "coopMembers.json");
        if (!coopMembersJson.exists()) {
            try {
                coopMembersJson.createNewFile();
            } catch (Exception ex) {

            }
        }
        JsonObject file;
        try (FileReader in = new FileReader(new File(SkyblockReinvented.modDir, "coopMembers.json"))) {
            file = gson.fromJson(in, JsonObject.class);
            for (int i = 0; i < file.entrySet().size(); i++) {
                if (file.get("coopMember" + i) != null) {
                    CoopMember member = new CoopMember(file.get("coopMember" + i).getAsJsonObject().get("name").getAsString(), file.get("coopMember" + i).getAsJsonObject().get("disabled").getAsBoolean());
                    this.coop.put("customFilter" + i, member);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void addDisabledMember(String name) {
        int size = coop.size();
        coop.put("coopMember" + size, new CoopMember(name, true));
        try (FileWriter writer = new FileWriter(new File(SkyblockReinvented.modDir, "coopMembers.json"))) {
            gson.toJson(coop, writer);
        } catch (Exception ex) {

        }
    }
    public void addEnabledMember(String name) {
        int size = coop.size();
        coop.put("coopMember" + size, new CoopMember(name, false));
        try (FileWriter writer = new FileWriter(new File(SkyblockReinvented.modDir, "coopMembers.json"))) {
            gson.toJson(coop, writer);
        } catch (Exception ex) {

        }
    }
}
class CoopMember {
    private String name;
    private boolean disabled;
    public CoopMember(String name, boolean disabled) {
        this.name = name;
        this.disabled = disabled;
    }
    public String getMemberName() {
        return this.name;
    }
    public boolean getDisabled() {
        return this.disabled;
    }
}
