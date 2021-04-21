package thecudster.sre.features.impl.discord;
import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.RichPresence;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.sbutil.CurrentLoc;
import org.json.JSONObject;
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
        RichPresence presence = new RichPresence.Builder()
                .setState("Playing Skyblock")
                .setDetails("In " + location)
                .setStartTimestamp(startTimestamp)
                .build();
        client.sendRichPresence(presence);
    }
    public boolean isActive() {
        return client != null && connected;
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
