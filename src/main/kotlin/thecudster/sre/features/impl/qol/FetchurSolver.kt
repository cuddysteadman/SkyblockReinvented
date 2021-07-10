package thecudster.sre.features.impl.qol

import thecudster.sre.util.Utils.getUnformattedChat
import thecudster.sre.util.Utils.containsAnyOf
import thecudster.sre.util.Utils.sendMsg
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.client.event.ClientChatReceivedEvent
import thecudster.sre.util.sbutil.ArrStorage
import net.minecraft.util.EnumChatFormatting
import thecudster.sre.SkyblockReinvented
import java.util.HashMap

class FetchurSolver {
    var fetchurSolutions: HashMap<String?, String> = hashMapOf("theyre red and soft" to "fifty red wool",
            "theyre yellow and see through" to "twenty yellow stained glass",
            "its circlular and sometimes moves" to "a compass",
            "theyre expensive minerals" to "twenty mithril",
            "its useful during celebrations" to "a firework rocket",
            "its hot and gives energy" to "a cheap coffee or a decent coffee",
            "its tall and can be opened" to "a wooden door",
            "theyre brown and fluffy" to "three rabbit foots",
            "its explosive but more than usual" to "a superboom tnt",
            "its wearable and grows" to "a pumpkin",
            "its shiny and makes sparks" to "a flint and steel",
            "theyre red and white and you can mine it" to "fifty nether quartz ore",
            "theyre round and green, or purple" to "sixteen epearls")

    @SubscribeEvent
    fun onChat(event: ClientChatReceivedEvent) {
        if (SkyblockReinvented.config.showFetchur == 0) {
            return
        }
        val message = getUnformattedChat(event)
        if (!message.contains("[NPC] Fetchur: ")) {
            if (containsAnyOf(message, arrayOf("You received these rewards", "20,000 Coins, 1,000 Mithril Powder"))) {
                event.isCanceled = true
                return
            }
        }
        for (s in ArrStorage.SpamArrays.fetchurQuotes) {
            if (message.contains(s)) {
                event.isCanceled = true
            }
        }
        if (SkyblockReinvented.config.showFetchur == 1) {
            return
        }
        if (message.contains("i need ") && message.contains(" pls")) {
            event.isCanceled = true
            return
        }
        for ((key, value) in fetchurSolutions) {
            if (message.contains(key!!)) {
                event.isCanceled = true
                sendMsg(EnumChatFormatting.YELLOW.toString() + "[NPC] Fetchur" + EnumChatFormatting.WHITE + ": hey can you get me " + value)
            }
        }
    }
}