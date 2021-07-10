package thecudster.sre.events

import net.minecraft.network.Packet
import net.minecraftforge.fml.common.eventhandler.Cancelable
import net.minecraftforge.fml.common.eventhandler.Event
import thecudster.sre.events.PacketEvent

/**
 * Taken from Skytils under GNU Affero General Public license.
 * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
 * @author Sychic
 * @author My-Name-Is-Jeff
 */
@Cancelable
open class PacketEvent(var packet: Packet<*>?) : Event() {
    var direction: Direction? = null

    class ReceiveEvent(packet: Packet<*>?) : PacketEvent(packet) {
        init {
            direction = Direction.INBOUND
        }
    }

    class SendEvent(packet: Packet<*>?) : PacketEvent(packet) {
        init {
            direction = Direction.OUTBOUND
        }
    }

    enum class Direction {
        INBOUND, OUTBOUND
    }
}