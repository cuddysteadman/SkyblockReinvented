package thecudster.sre.features.impl.filter

import net.minecraftforge.client.event.ClientChatReceivedEvent
import thecudster.sre.util.Utils
import thecudster.sre.util.sbutil.stripControlCodes

class Filter {
    /**
     * Please see
     * @link(https://github.com/theCudster/SkyblockReinvented/docs/SPAM_FILTER.md)
     * for more info!
     */
    val messageArr: Array<String>
    var shouldCancel: Boolean
        private set
    val needsDungeons: Boolean
    var name: String?
        private set

    constructor(message: Array<String?>, shouldCancel: Boolean, dungeons: Boolean) {
        messageArr = message as Array<String>
        needsDungeons = dungeons
        this.shouldCancel = shouldCancel
        name = null
    }

    constructor(message: Array<String>, shouldCancel: Boolean, dungeons: Boolean, name: String?) {
        messageArr = message
        needsDungeons = dungeons
        this.shouldCancel = shouldCancel
        this.name = name
    }

    fun shouldCancel(event: ClientChatReceivedEvent): Boolean {
        return this.check(event) && shouldCancel && checkDungeons()
    }

    fun check(event: ClientChatReceivedEvent): Boolean {
        for (s in messageArr) {
            if (event.message.unformattedText.stripControlCodes().contains(s)) {
                return true
            }
        }
        return false
    }

    fun checkDungeons(): Boolean {
        return if (!needsDungeons) true else Utils.inDungeons
    }
}