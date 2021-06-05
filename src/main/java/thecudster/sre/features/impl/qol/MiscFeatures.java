package thecudster.sre.features.impl.qol;

import net.minecraft.client.renderer.entity.RenderXPOrb;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderItemInFrameEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.gui.GuiManager;
import thecudster.sre.util.sbutil.CurrentLoc;
import thecudster.sre.util.sbutil.Utils;

public class MiscFeatures {
    @SubscribeEvent
    public void onRender(RenderItemInFrameEvent event) {
        if (!Utils.inSkyblock) { return; }
        if (!CurrentLoc.currentLoc.equals("Your Island"))
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
}
