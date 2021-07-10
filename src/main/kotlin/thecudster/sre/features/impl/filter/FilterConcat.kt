package thecudster.sre.features.impl.filter

import net.minecraft.util.StringUtils
import net.minecraftforge.client.event.ClientChatReceivedEvent
import thecudster.sre.util.Utils

class FilterConcat {
    var concatCheck: Array<String>
        private set
    var shouldCancel: Boolean
        private set
    var needsDungeons: Boolean
        private set

    constructor(concatCheck: Array<String>, shouldCancel: Boolean) {
        this.concatCheck = concatCheck
        this.shouldCancel = shouldCancel
        needsDungeons = false
    }

    constructor(concatCheck: Array<String?>, shouldCancel: Boolean, needsDungeons: Boolean) {
        this.concatCheck = concatCheck as Array<String>
        this.shouldCancel = shouldCancel
        this.needsDungeons = needsDungeons
    }

    fun shouldCancel(event: ClientChatReceivedEvent): Boolean {
        return checkConcat(event) && checkDungeons() && shouldCancel
    }

    fun checkConcat(event: ClientChatReceivedEvent): Boolean {
        for (s in concatCheck) {
            if (!StringUtils.stripControlCodes(event.message.unformattedText).contains(s)) return false
        }
        return true
    }

    fun checkDungeons(): Boolean {
        return if (!needsDungeons) true else Utils.inDungeons
    }
}