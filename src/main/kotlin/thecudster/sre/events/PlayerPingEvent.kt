package thecudster.sre.events

import net.minecraftforge.fml.common.eventhandler.Event

class PlayerPingEvent : Event {
    @JvmField
    var sender: String
    @JvmField
    var receiver: String
    @JvmField
    var reason: String?

    constructor(theSender: String, theReceiver: String) {
        receiver = theReceiver
        sender = theSender
        reason = null
    }

    constructor(theSender: String, theReceiver: String, theReason: String?) {
        sender = theSender
        receiver = theReceiver
        reason = theReason
    }
}