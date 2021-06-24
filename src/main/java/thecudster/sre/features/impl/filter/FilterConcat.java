package thecudster.sre.features.impl.filter;

import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import thecudster.sre.util.Utils;

public class FilterConcat {
    private String[] concatCheck;
    private boolean shouldCancel;
    private boolean needsDungeons;

    public String[] getConcatCheck() {
        return this.concatCheck;
    }
    public boolean getNeedsDungeons() {
        return this.needsDungeons;
    }

    public FilterConcat(String[] concatCheck, boolean shouldCancel) {
        this.concatCheck = concatCheck;
        this.shouldCancel = shouldCancel;
        this.needsDungeons = false;
    }
    public FilterConcat(String[] concatCheck, boolean shouldCancel, boolean needsDungeons) {
        this.concatCheck = concatCheck;
        this.shouldCancel = shouldCancel;
        this.needsDungeons = needsDungeons;
    }

    public boolean shouldCancel(ClientChatReceivedEvent event) {
        return this.checkConcat(event) && checkDungeons() && this.getShouldCancel();
    }
    public boolean checkConcat(ClientChatReceivedEvent event) {
        for (String s : this.concatCheck) {
            if (!StringUtils.stripControlCodes(event.message.getUnformattedText()).contains(s)) return false;
        }
        return true;
    }
    public boolean checkDungeons() {
        if (!this.needsDungeons) return true;
        return Utils.inDungeons;
    }
    public boolean getShouldCancel() {
        return this.shouldCancel;
    }
}
