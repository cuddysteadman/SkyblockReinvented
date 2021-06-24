package thecudster.sre.features.impl.qol;


import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.sbutil.ArrStorage;
import thecudster.sre.util.Utils;

import java.util.HashMap;
import java.util.Map;
public class FetchurSolver {

    public static HashMap<String, String> fetchurSolutions = new HashMap<>();
    public static void init() {
        // fetchur solutions taken from Skytils' repo and reformatted to seem more fetchur-like :P
        fetchurSolutions.put("theyre red and soft", "fifty red wool");
        fetchurSolutions.put("theyre yellow and see through", "twenty yellow stained glass");
        fetchurSolutions.put("its circlular and sometimes moves", "a compass");
        fetchurSolutions.put("theyre expensive minerals", "twenty mithril");
        fetchurSolutions.put("its useful during celebrations", "a firework rocket");
        fetchurSolutions.put("its hot and gives energy", "a cheap coffee or a decent coffee");
        fetchurSolutions.put("its tall and can be opened", "a wooden door");
        fetchurSolutions.put("theyre brown and fluffy", "three rabbit foots");
        fetchurSolutions.put("its explosive but more than usual", "a superboom tnt");
        fetchurSolutions.put("its wearable and grows", "a pumpkin");
        fetchurSolutions.put("its shiny and makes sparks", "a flint and steel");
        fetchurSolutions.put("theyre red and white and you can mine it", "fifty nether quartz ore");
        fetchurSolutions.put("theyre round and green, or purple", "sixteen epearls");
    }
    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if (SkyblockReinvented.config.showFetchur == 0) { return; }
        String message = Utils.getUnformattedChat(event);
        if (!message.contains("[NPC] Fetchur: ")) {
            if (Utils.containsAnyOf(message, new String[]{"You received these rewards", "20,000 Coins, 1,000 Mithril Powder"})) {
                event.setCanceled(true);
                return;
            }
        }
        for (String s : ArrStorage.SpamArrays.fetchurQuotes) {
            if (message.contains(s)) {
                event.setCanceled(true);
            }
        }
        if (SkyblockReinvented.config.showFetchur == 1) { return; }
        if (message.contains("i need ") && message.contains(" pls")) {
            event.setCanceled(true);
            return;
        }
        for (Map.Entry<String, String> entry : fetchurSolutions.entrySet()) {
            if (message.contains(entry.getKey())) {
                event.setCanceled(true);
                Utils.sendMsg(EnumChatFormatting.YELLOW + "[NPC] Fetchur" + EnumChatFormatting.WHITE + ": hey can you get me " + entry.getValue());
            }
        }
    }
}
