package thecudster.sre.features.impl.skills.mining

import thecudster.sre.util.Utils.inLoc
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.client.event.RenderWorldLastEvent
import thecudster.sre.util.sbutil.ArrStorage
import thecudster.sre.core.gui.RenderUtils
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.client.Minecraft
import net.minecraft.util.BlockPos
import thecudster.sre.util.Utils
import java.awt.Color
import java.util.ArrayList

class CommissionWaypoints {
    @SubscribeEvent
    fun onRender(event: RenderWorldLastEvent) {
        if (inLoc(ArrStorage.dwarvenLocs) && Utils.inSkyblock) {
            for (loc in commissionsToRender) {
                RenderUtils.drawWaypoint(
                    event.partialTicks,
                    loc!!.loc,
                    loc.name,
                    Color(50, 50, 50),
                    true
                ) // TODO dwarven colour config
            }
            for (emissary in emissaryWaypointsToRender) {
                RenderUtils.drawWaypoint(
                    event.partialTicks,
                    emissary.location,
                    emissary.emissaryName,
                    Color(50, 50, 50),
                    true
                ) // TODO dwarven emissary colour config
            }
        }
    }

    val commissionsToRender: ArrayList<DwarvenLocation?>
        get() {
            if (CommissionOverlay.instance.getCompletedCommissions().size > 0 && true || true) {
                if (true) {
                    val locations = ArrayList<DwarvenLocation?>()
                    for (loc in DwarvenLocation.values()) {
                        locations.add(loc)
                    }
                    return locations
                }
                val activeCommissionLocations = ArrayList<DwarvenLocation?>()
                for (commission in CommissionOverlay.instance.activeCommissions) {
                    activeCommissionLocations.add(commission.getCommission().location)
                }
                return activeCommissionLocations
            }
            return ArrayList()
        }// TODO config for whether to render them all

    // TODO config for whether to always render them
    val emissaryWaypointsToRender: ArrayList<Emissary>
        get() {
            val playerPos: EntityPlayer = Minecraft.getMinecraft().thePlayer
            return if (CommissionOverlay.instance.getCompletedCommissions().size > 0 && true || true) { // TODO config for whether to always render them
                if (true) { // TODO config for whether to render them all
                    val emissaries = ArrayList<Emissary>()
                    for (emissary in Emissary.values()) {
                        emissaries.add(emissary)
                    }
                    return emissaries
                }
                var closest = Emissary.values()[0]
                for (emissary in Emissary.values()) {
                    if (playerPos.getDistanceSq(emissary.location) < playerPos.getDistanceSq(closest.location)) {
                        closest = emissary
                    }
                }
                val emissary = ArrayList<Emissary>()
                emissary.add(closest)
                emissary
            } else {
                ArrayList()
            }
        }
}

enum class Emissary(x: Int, y: Int, z: Int, emissaryName: String) {
    CARLTON(-73, 153, -11, "Carlton"), CEANNA(42, 134, 22, "Ceanna"), WILSON(171, 150, 31, "Wilson"), LILITH(
        58,
        198,
        -9,
        "Lilith"
    ),
    FRAISER(-132, 174, -50, "Fraiser"), ELIZA(-37, 200, -131, "Eliza");

    val location: BlockPos
    val emissaryName: String

    init {
        location = BlockPos(x, y, z)
        this.emissaryName = emissaryName
    }
}