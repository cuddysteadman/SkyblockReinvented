package thecudster.sre.features.impl.filter;

import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import thecudster.sre.util.Utils;

public class Filter {
    /**
     * Please see
     * @link(https://github.com/theCudster/SkyblockReinvented/docs/SPAM_FILTER.md)
     * for more info!
     */
    private final String[] messageArr;
    private boolean shouldCancel;
    private final boolean needsDungeons;
    private String name;

    public String getName() {
        return this.name;
    }

    public Filter(String[] message, boolean shouldCancel, boolean dungeons) {
        this.messageArr = message;
        this.needsDungeons = dungeons;
        this.shouldCancel = shouldCancel;
        this.name = null;
    }

    public Filter(String[] message, boolean shouldCancel, boolean dungeons, String name) {
        this.messageArr = message;
        this.needsDungeons = dungeons;
        this.shouldCancel = shouldCancel;
        this.name = name;
    }

    public boolean shouldCancel(ClientChatReceivedEvent event) {
        return this.check(event) && this.getShouldCancel() && this.checkDungeons();
    }
    public boolean check(ClientChatReceivedEvent event) {
        for (String s : this.messageArr) {
            if (StringUtils.stripControlCodes(event.message.getUnformattedText()).contains(s)) {
                return true;
            }
        }
        return false;
    }
    public boolean checkDungeons() {
        if (!this.needsDungeons) return true;
        return Utils.inDungeons;
    }
    public boolean getShouldCancel() {
        return this.shouldCancel;
    }
    public String[] getMessageArr() {
        return this.messageArr;
    }
    public boolean getNeedsDungeons() {
        return this.needsDungeons;
    }
}
