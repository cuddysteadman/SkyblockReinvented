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

package thecudster.sre.features.impl.qol;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.entities.User;
import net.minecraft.client.Minecraft;
import net.minecraft.util.StringUtils;
import org.json.JSONObject;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.sbutil.CurrentLoc;
import thecudster.sre.util.sbutil.ItemUtil;
import thecudster.sre.util.sbutil.Utils;

import java.time.OffsetDateTime;
import java.util.Timer;
import java.util.TimerTask;
/**
 * Modified from SkyblockAddons under MIT License
 * https://github.com/BiscuitDevelopment/SkyblockAddons/blob/master/LICENSE
 * @author BiscuitDevelopment
 */
public class DiscordRPC implements IPCListener {
    public static final long APPLICATION_ID = 830851684318183466L;
    private static final long UPDATE_PERIOD = 4200L;
    private IPCClient client;
    private OffsetDateTime startTimestamp;
    private Timer updateTimer;
    public static String profile = "";
    public static String state = "Playing Skyblock";
    public static int partySize = 1;
    public static int partyMax = 5;

    private boolean connected;
    public void start() {
        try {
            if (isActive()) {
                return;
            }
            startTimestamp = OffsetDateTime.now();
            client = new IPCClient(APPLICATION_ID);
            client.setListener(this);
            try {
                client.connect();
            } catch (Exception e) {
                System.out.println("Error while connecting to Discord RPC: " + e.getMessage());
            }
        }
        catch (Throwable ex) {
            System.out.println("Unexpected error with Discord RP.");
            ex.printStackTrace();
        }
    }

    public void stop() {
        if (isActive()) {
            client.close();
            connected = false;
        }
    }
    public void updatePresence() {
        String location = CurrentLoc.currentLoc;
        if (location == null) {
            return;
        }
        switch (SkyblockReinvented.config.discordMode) {
            case 0:
                break;
            case 1:
                state = Minecraft.getMinecraft().thePlayer.getName();
                break;
            case 2:
                if (Minecraft.getMinecraft().thePlayer.getHeldItem() != null) {
                    if (ItemUtil.getDisplayName(Minecraft.getMinecraft().thePlayer.getHeldItem()) != null) {
                        state = StringUtils.stripControlCodes(ItemUtil.getDisplayName(Minecraft.getMinecraft().thePlayer.getHeldItem()));
                        break;
                    }
                }
                state = "Not Holding an Item!";
                break;
            case 3:
                state = "Playing Skyblock";
                break;
            case 4:
                state = SkyblockReinvented.config.discordCustomText;
                break;
        }
        RichPresence presence = new RichPresence.Builder()
                .setState(state)
                .setDetails("In " + location)
                .setLargeImage("logodiscord")
                .setStartTimestamp(startTimestamp)
//                .setParty("Skyblock Party", partySize, partyMax)
  //              .setInstance(true)
    //            .setMatchSecret("SRE")
      //          .setJoinSecret("SRE")
                .build();
        client.sendRichPresence(presence);
    }
    public boolean isActive() {
        return client != null;
    }
    @Override
    public void onActivityJoinRequest(IPCClient client, String secret, User user) {
        Utils.sendMsg(user.getName() + " joined the party!");
    }
    @Override
    public void onReady(IPCClient client) {
        connected = true;
        updateTimer = new Timer();
        updateTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                updatePresence();
            }
        }, 0, UPDATE_PERIOD);
    }

    @Override
    public void onClose(IPCClient client, JSONObject json) {
        this.client = null;
        connected = false;
        cancelTimer();
    }

    @Override
    public void onDisconnect(IPCClient client, Throwable t) {
        this.client = null;
        connected = false;
        cancelTimer();
    }

    private void cancelTimer() {
        if(updateTimer != null) {
            updateTimer.cancel();
            updateTimer = null;
        }
    }
}
