package thecudster.sre.features.impl.skills.mining

import net.minecraft.util.BlockPos
import java.util.ArrayList

class CommissionOverlay {
    val activeCommissions = ArrayList<Commission>()
    private val completedCommissions = ArrayList<Commission>()
    fun parseFromString(entered: String?): Commission? {
        return when (entered) {
            else -> null
        }
    }

    fun hasActiveCommission(): Boolean {
        return activeCommissions.size > 0
    }

    fun updateActiveCommissions() {
        completedCommissions.clear()
        for (commission in activeCommissions) {
            if (commission.completed) {
                completedCommissions.add(commission)
            }
        }
    }

    fun getCompletedCommissions(): ArrayList<Commission> {
        updateActiveCommissions()
        return completedCommissions
    }

    companion object {
        @JvmField
        var instance = CommissionOverlay()
        var commissionListeners = Listeners()
    }
}

class Commission private constructor(completed: Boolean, myCommission: CommissionType) {
    var completed = false
    private val commission: CommissionType

    fun getCommission(): CommissionType {
        return commission
    }

    init {
        this.completed = completed
        this.commission = myCommission
    }
}

enum class CommissionType(val required: Int, val location: DwarvenLocation?, val commissionName: String) {
    MITHRIL_MINER(500, null, "Mithril Miner"), TITANIUM_MINER(15, null, "Titanium Miner"), ROYAL_MINES_MITHRIL(
        350,
        DwarvenLocation.ROYAL_MINES,
        "Royal Mines Mithril"
    ),
    ROYAL_MINES_TITANIUM(10, DwarvenLocation.ROYAL_MINES, "Royal Mines Titanium"), RAMPARTS_QUARRY_MITHRIL(
        350,
        DwarvenLocation.RAMPARTS_QUARRY,
        "Rampart's Quarry Mithril"
    ),
    RAMPARTS_QUARRY_TITANIUM(10, DwarvenLocation.RAMPARTS_QUARRY, "Rampart's Quarry Titanium"), UPPER_MINES_MITHRIL(
        350,
        DwarvenLocation.UPPER_MINES,
        "Upper Mines Mithril"
    ),
    UPPER_MINES_TITANIUM(10, DwarvenLocation.UPPER_MINES, "Upper Mines Titanium"), CLIFFSIDE_VEINS_MITHRIL(
        350,
        DwarvenLocation.CLIFFSIDE_VEINS,
        "Cliffside Veins Mithril"
    ),
    CLIFFSIDE_VEINS_TITANIUM(10, DwarvenLocation.CLIFFSIDE_VEINS, "Cliffside Veins Titanium"), LAVA_SPRINGS_MITHRIL(
        350,
        DwarvenLocation.LAVA_SPRINGS,
        "Lava Springs Mithril"
    ),
    LAVA_SPRINGS_TITANIUM(10, DwarvenLocation.LAVA_SPRINGS, "Lava Springs Titanium"), ICE_WALKERS(
        100,
        DwarvenLocation.GREAT_ICE_WALL,
        "Ice Walker Slayer"
    ),
    GOBLINS(200, DwarvenLocation.GOBLIN_BURROWS, "Goblin Slayer");

}

enum class DwarvenLocation(x: Int, y: Int, z: Int, val myName: String) {
    GOBLIN_BURROWS(-113, 142, 110, "Goblin Burrows"), ROYAL_MINES(140, 152, 35, "Royal Mines"), RAMPARTS_QUARRY(
        -23,
        175,
        -55,
        "Rampart's Quarry"
    ),
    UPPER_MINES(-126, 175, -55, "Upper Mines"), CLIFFSIDE_VEINS(20, 128, 53, "Cliffside Veins"), GREAT_ICE_WALL(
        0,
        128,
        160,
        "Great Ice Wall"
    ),
    LAVA_SPRINGS(0, 0, 0, "Lava Springs");

    val loc: BlockPos

    init {
        loc = BlockPos(x, y, z)
    }
}

class Listeners 