package thecudster.sre.features.impl.filter

class CustomPlayersFilter(val playerName: String) {
    fun getShouldCancel(nametag: String): Boolean {
        return nametag.contains(playerName)
    }
}