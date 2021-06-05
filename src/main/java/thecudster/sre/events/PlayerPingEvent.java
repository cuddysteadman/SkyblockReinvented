package thecudster.sre.events;

import net.minecraftforge.fml.common.eventhandler.Event;

public class PlayerPingEvent extends Event {
    public String sender;
    public String receiver;
    public String reason;
    public PlayerPingEvent(String theSender, String theReceiver) {
        this.receiver = theReceiver;
        this.sender = theSender;
        this.reason = null;
    }
    public PlayerPingEvent(String theSender, String theReceiver, String theReason) {
        this.sender = theSender;
        this.receiver = theReceiver;
        this.reason = theReason;
    }
}
