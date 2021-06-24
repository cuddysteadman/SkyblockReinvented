package thecudster.sre.features.impl.filter;

public class CustomPlayersFilter {
    private String name;
    public CustomPlayersFilter(String name) {
        this.name = name;
    }
    public boolean getShouldCancel(String nametag) {
        return nametag.contains(this.name);
    }
    public String getPlayerName() {
        return this.name;
    }
}
